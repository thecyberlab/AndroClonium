package usask.cyberlab.smalisimulator

import android.annotation.SuppressLint
import android.content.Context
import com.google.common.io.Files
import usask.cyberlab.smalisimulator.simsmali.emulator.*
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader
import java.io.*
import java.lang.Exception
import java.lang.IllegalStateException
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils
import java.nio.charset.Charset
import android.util.Log


@SuppressLint("StaticFieldLeak")
class SimulationManager private constructor(
    val context: Context,
    val myClazzLoader: ClazzLoader,
    val appName: String,
    val appVersion: String
) {


    companion object {
        var self: SimulationManager? = null


        private fun purgeDirectory(dir: File) {
            for (file in dir.listFiles()) {
                if (file.isDirectory) purgeDirectory(file)
                file.delete()
            }
        }

        fun getInstance(
            context: Context,
            appName: String,
            appVersion: String
        ): SimulationManager {

            if (self == null) {
                // before initializing the ClazzLoader we purge the cache dir from old files
                purgeDirectory(context.cacheDir)
                val uploadedSmaliDir = getUploadedSmaliFilesDir(context)

                val basePath = uploadedSmaliDir.absolutePath + "/" + appName + "/" + appVersion
                self =
                    SimulationManager(context, ClazzLoader(basePath, context), appName, appVersion)
                return self as SimulationManager
            } else {
                if (!self!!.appName.equals(appName) || !self!!.appVersion.equals(appVersion)) {
                    val n = self!!.appName
                    val v = self!!.appVersion
                    throw IllegalStateException("expected $n:$v but got $appName:$appVersion!")
                }
                return self as SimulationManager
            }

        }
    }

    fun analyzeApp() {
        val uploadedSmaliDir = getUploadedSmaliFilesDir(context)
        val uploadSmaliFilesOfApp = File(uploadedSmaliDir, appName + "/" + appVersion)

        val smaliFilesOfApp = getFilesWithExtension(uploadSmaliFilesOfApp, ".smali")
        for (f in smaliFilesOfApp) {
            println("starting to analyze " + f.path)
            val fPath = f.absolutePath.substring(f.absolutePath.indexOf("uploadedSmaliFiles"))
            analyzeSmaliFile(fPath)
        }
        println("** simulation finished")
        System.gc()
    }


    fun analyzeSmaliFile(smaliRelativeFilePath: String) {

        val analyzedTracesDir = getAnalyzedTracesDir(context)

        val smaliStyleClassPath = "L" + smaliRelativeFilePath.replace(".smali", ";")


        val appTraceAnalysisDir = File(analyzedTracesDir, appName + "_" + appVersion)
        appTraceAnalysisDir.mkdirs()

        var sc: SmaliClass?
        try {
            sc = myClazzLoader!!.getSmaliClass(smaliStyleClassPath)
        } catch (e: Throwable) {
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToParseFile.txt"), true))
            writer.write(appName + ":" + appVersion + "\n")
            writer.write(smaliStyleClassPath + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error parsing smali Class " + smaliStyleClassPath)
            //TODO ==========================
            throw e
//            return
        }

        try {
            myClazzLoader!!.getClazz(smaliStyleClassPath)
        } catch (e: Throwable) {
            myClazzLoader.resetLoadingInProgressClassesSet()
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToLoadClass.txt"), true))
            writer.write(appName + ":" + appVersion + "\n")
            writer.write(smaliStyleClassPath + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error loading smali Class " + smaliStyleClassPath)
            //TODO ==========================
            throw e
//            return
        }

        for (smaliMethod in sc.allMethods) {
            analyzeSmaliMethod(smaliMethod)
        }

        println("** simulation finished")
        System.gc()
    }


    fun analyzeSmaliMethod(smaliRelativeFilePath: String, methodSig: String) {

        val smaliStyleClassPath = "L" + smaliRelativeFilePath.replace(".smali", ";")
            .replace("//", "/") // just for sanitizing dumb mistakes in path
        var sc: SmaliClass?
        try {
            sc = myClazzLoader!!.getSmaliClass(smaliStyleClassPath)
        } catch (e: Throwable) {
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToParseFile.txt"), true))
            writer.write(appName + ":" + appVersion + "\n")
            writer.write(smaliStyleClassPath + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error parsing smali Class " + smaliStyleClassPath)
            //TODO ==========================
            throw e
//            return
        }

        val smaliMethod: SmaliMethod
        try {
            smaliMethod = sc.getMethod(methodSig)
        } catch (e: Exception) {
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "SmaliMethodNotFound.txt"), true))
            writer.write("$smaliStyleClassPath->$methodSig\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error geting Smali Method " + smaliStyleClassPath + "->" + methodSig)
            throw e
//            return
        }

        try {
            Utils.writeTimeStampToTimeLogFile(
                smaliMethod,
                "start loading containing class of smali method"
            )
            myClazzLoader!!.getClazz(smaliStyleClassPath)
            Utils.writeTimeStampToTimeLogFile(
                smaliMethod,
                "finished loading containing class of smali method"
            )
        } catch (e: Throwable) {
            myClazzLoader.resetLoadingInProgressClassesSet()
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToLoadClass.txt"), true))
            writer.write(appName + ":" + appVersion + "\n")
            writer.write(smaliStyleClassPath + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error loading smali Class " + smaliStyleClassPath)
            //TODO ==========================
            throw e
//            return
        }



        analyzeSmaliMethod(smaliMethod)
        println("** simulation finished")
        System.gc()

    }

    private fun analyzeSmaliMethod(smaliMethod: SmaliMethod) {
        val appTraceAnalysisDir = File(getAnalyzedTracesDir(context), appName + "_" + appVersion)
        val simulator = Simulator(myClazzLoader, appTraceAnalysisDir)

        if (smaliMethod.isAbstract()) return
        if (smaliMethod.classMethodSignature.endsWith("<clinit>()V")) return
        if (smaliMethod.classMethodSignature.split("->")[1].startsWith("<init>")) return


        val argTypes = smaliMethod.argumentTypes
        val arguments = arrayOfNulls<Any>(argTypes.size)

        for (i in arguments.indices) {
            arguments[i] = AmbiguousValue(argTypes[i])
        }


        // static methods
        if (smaliMethod.isStatic) {
            simulateStaticMethod(smaliMethod, simulator, arguments)
        }
        // instance methods (but not constructors)
        else {
            simulateInstanceMethod(smaliMethod, simulator, arguments)
        }

//        writeExecutionResults(smaliMethod, executions)
//        analyzeWrittenExecutionTraces(smaliMethod)

    }


    private fun simulateStaticMethod(
        smaliMethod: SmaliMethod,
        simulator: Simulator,
        arguments: Array<Any?>
    ) {

        println("** simulating static method :" + smaliMethod.classMethodSignature)
        try {
            simulator.simulateStaticMethod(smaliMethod.classMethodSignature, arguments)
            return
        } catch (e: Throwable) {
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToSimulate.txt"), true))
            writer.write("Error at " + appName + "_" + appVersion + "\n")
            writer.write(smaliMethod.containingClass.classPath + "|" + smaliMethod.classMethodSignature + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            throw e
        }
    }


    private fun simulateInstanceMethod(
        smaliMethod: SmaliMethod,
        simulator: Simulator,
        arguments: Array<Any?>
    ) {

        println("** simulating instance method :" + smaliMethod.classMethodSignature)
        try {
            val ambiguousSelf = AmbiguousValue(smaliMethod.containingClass.classPath)
            simulator.simulateInstanceMethod(
                smaliMethod.classMethodSignature,
                ambiguousSelf,
                arguments
            )
            return
        } catch (e: Throwable) {
            val s = Utils.getStackTraceAsString(e)
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToSimulate.txt"), true))
            writer.write("Error at " + appName + "_" + appVersion + "\n")
            writer.write(smaliMethod.containingClass.classPath + "|" + smaliMethod.classMethodSignature + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            throw e
        }
    }


    fun loadClazz(smaliRelativeFilePath: String): Class<Any> {
        val smaliStyleClassPath = "L" + smaliRelativeFilePath.replace(".smali", ";")
        try {

//            val sc = myClazzLoader.getSmaliClass(smaliStyleClassPath);
//            val d = sc.getAllIndirectDependentTypes(myClazzLoader)
            val c = myClazzLoader!!.getClazz(smaliStyleClassPath).mirroringClass
            return c
        } catch (e: Throwable) {
//            myClazzLoader.resetLoadingInProgressClassesSet()
//            val stackTraceString = e.stackTraceToString()
            val writer =
                BufferedWriter(FileWriter(File(context.filesDir, "FailedToLoadClass.txt"), true))
            writer.write(appName + ":" + appVersion + "\n")
            writer.write(smaliStyleClassPath + "\n")
            writer.write(e.stackTraceToString() + "\n")
            writer.write("--------------------------" + "\n")
            writer.flush()
            writer.close()
            println("** error loading smali Class " + smaliStyleClassPath)
            throw e
        }
    }


    fun isSmaliMethodExecutionEntryPoint(classMethodSignature: String): Boolean {
        val classSig = classMethodSignature.split("->")[0]
        val methodSig = classMethodSignature.split("->")[1]
        val sc = myClazzLoader!!.getSmaliClass(classSig)
        val sm = sc.getMethod(methodSig)
        return isSmaliMethodExecutionEntryPoint(sm)
    }

    private fun isSmaliMethodExecutionEntryPoint(sm: SmaliMethod): Boolean {
        if (sm == null) return false
        return sm.isExecutionEntryPoint(myClazzLoader)
    }

    private fun getAllElementsWithOnClickAttr(node: Node, result: HashSet<Node>) {
        if (node.hasAttributes() && node.attributes.getNamedItem("android:onClick") != null) {
            result.add(node)
        }
        val childNodes = node.childNodes
        for (i in 0..childNodes.length) {
            val child = childNodes.item(i)
            if (child != null) {
                getAllElementsWithOnClickAttr(child, result)
            }
        }
    }

    private fun getComponentCorrespondingTypesFromManifest(node:Node,res: HashMap<String, String>){
        if(node.nodeName.equals("activity")){
            if(node.hasAttributes() && node.attributes.getNamedItem("android:name") != null){
                val v = node.attributes.getNamedItem("android:name").nodeValue!!
                res.put(v, "activity");
            }
        }
        else if(node.nodeName.equals("service")){
            if(node.hasAttributes() && node.attributes.getNamedItem("android:name") != null){
                val v = node.attributes.getNamedItem("android:name").nodeValue!!
                res.put(v, "service");
            }
        }
        else if(node.nodeName.equals("provider")){
            if(node.hasAttributes() && node.attributes.getNamedItem("android:name") != null){
                val v = node.attributes.getNamedItem("android:name").nodeValue!!
                res.put(v, "provider");
            }
        }

        val childNodes = node.childNodes
        for (i in 0..childNodes.length) {
            val child = childNodes.item(i)
            if (child != null) {
                getComponentCorrespondingTypesFromManifest(child, res)
            }
        }

    }

    fun getAllAppEntryPoints(): List<String> {
        val res = arrayListOf<String>()
        val appVersionUploadedSmaliDir =
            File(File(getUploadedSmaliFilesDir(context), appName), appVersion)
        val appVersionUploadedResDir =
            File(File(getUploadedResFilesDir(context), appName), appVersion)

        val foundOnClickMethods = HashMap<String, ArrayList<SmaliMethod>>()

        var componentsCorrespondingTypesInManifest: HashMap<String, String>? = null
        val appManifestXml = File(appVersionUploadedResDir, "AndroidManifest.xml")
        if(appManifestXml.exists() && appManifestXml.isFile){
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            var doc: Document? = null
            var isOkXML = false
            try {
                doc = db.parse(appManifestXml)
                isOkXML = true
            } catch (e: Exception) {
            }

            if(isOkXML){
                componentsCorrespondingTypesInManifest = HashMap<String, String>()
                getComponentCorrespondingTypesFromManifest(doc!!.documentElement, componentsCorrespondingTypesInManifest)
            }
        }

        // extracting onClickEntryPoints
        val appPublicXml = File(appVersionUploadedResDir, "values/public.xml")
        val appLayoutsDir = File(appVersionUploadedResDir, "layout")
        if (appPublicXml.exists() && appPublicXml.isFile &&
            appLayoutsDir.exists() && appLayoutsDir.isDirectory
        ) {


            for (f in appLayoutsDir.listFiles()) {
                // first for each layout we extract the set of elements with onClick
                if (f.name.endsWith(".xml")) {
                    val dbf = DocumentBuilderFactory.newInstance()
                    val db = dbf.newDocumentBuilder()
                    var doc: Document
                    try {
                        doc = db.parse(f)
                    } catch (e: Exception) {
                        continue
                    }
                    val onClickNodes = HashSet<Node>()
                    getAllElementsWithOnClickAttr(doc.documentElement, onClickNodes)
                    for (node in onClickNodes) {
                        val methodName = node.attributes.getNamedItem("android:onClick").nodeValue
                        foundOnClickMethods.put(methodName, ArrayList<SmaliMethod>())
                    }
                }
            }
        }

        val files = getFilesWithExtension(appVersionUploadedSmaliDir, ".smali")

        val android_library_classes_list = ArrayList<String>()
        val android_library_classes_assets_input_stream = context.assets.open("android_library_classes.txt")
        val reader = BufferedReader(InputStreamReader(android_library_classes_assets_input_stream))
        var l = reader.readLine()
        while ( l!= null){
            l = l.trim()
            if(l.isNotEmpty()){
                android_library_classes_list.add(l)
            }
            l = reader.readLine()
        }
        reader.close()

        // now for each smali file we check entry points
        // using AppEntryPoints.txt list and AppCallBackInterfaces.txt
        for (f in files) {

            // optimization trick : if is lambda expression we ignore it
            if (f.name.startsWith("-$")) continue

            // optimization trick : ignoring enums and abstract classes and interfaces
            // as valid entry points to an app without parsing the smali file
            val lines = Files.readLines(f, Charset.defaultCharset())
            val firstLine = lines[0]

            if (firstLine.contains(" enum ")) continue
            if (firstLine.contains(" abstract ")) continue

            // optimization trick : ignoring classes that extend Object class
            // and also do not implement any interface
            if (lines[1].equals(".super Ljava/lang/Object;")) {
                var hasInterface = false
                for (l in lines) {
                    if (l.startsWith(".implements ")) {
                        hasInterface = true
                        break
                    }
                }
                if (!hasInterface) {
                    continue
                }
            }

            var smaliClassPath = f.absolutePath.replace(
                context.filesDir.absolutePath + "/uploadedSmaliFiles/" + appName + "/" + appVersion + "/",
                ""
            )
            smaliClassPath = "L" + smaliClassPath.replace(".smali", "") + ";"

            var isAndroidLibrary = false
            for(i in android_library_classes_list){
                val ii = "L" + i.replace('.', '/')
                if(smaliClassPath.startsWith(ii)){
                    isAndroidLibrary = true
                    break
                }
            }

            if(isAndroidLibrary){
                continue
            }
            val sc = myClazzLoader.getSmaliClass(smaliClassPath)
            if(componentsCorrespondingTypesInManifest != null) {
                if (sc.extendsFrom("Landroid/app/Activity;", myClazzLoader)
                    || sc.extendsFrom("Landroid/app/Service;", myClazzLoader)
                    || sc.extendsFrom("Landroid/content/ContentProvider;", myClazzLoader)) {
                    val javaStyleName = SimulationUtils.makeJavaStyleClassPath(sc.classPath)
                    if(!componentsCorrespondingTypesInManifest.containsKey(javaStyleName)){
                        continue
                    }
                }
            }

            Log.i(this.context.packageName,"checking $sc for being an entry point.")
            for (sm in sc.allMethods) {
                if (sm.classMethodSignature.contains('<')) continue

                if (isSmaliMethodExecutionEntryPoint(sm)) {
                    res.add(sm.toString())
                }
            }

            // now for each smali class we look at it's methods and find all methods with matching name
            for(onClickMethodName in foundOnClickMethods.keys) {
                val methodSig = onClickMethodName + "(Landroid/view/View;)V"
                val sm = sc.getMethod(methodSig)
                if(sm != null){
                    val a:ArrayList<SmaliMethod> = foundOnClickMethods.get(onClickMethodName)!!
                    a.add(sm)
                }
            }

        }

        // if the matching methods contains only one method mark that method as entry point
        for(onClickMethodName in foundOnClickMethods.keys){
            val a:ArrayList<SmaliMethod> = foundOnClickMethods.get(onClickMethodName)!!
            if(a.size == 1){
                res.add(a.get(0).toString())
            }
        }

        return res
    }

}
