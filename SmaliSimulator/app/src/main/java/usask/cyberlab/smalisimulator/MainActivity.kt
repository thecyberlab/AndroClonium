package usask.cyberlab.smalisimulator

import android.app.Activity
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Looper
import android.text.format.Formatter
import android.widget.TextView
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.html.*
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliParser
import java.io.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun findNthOccurrence(phrase: String, str: String, n: Int): Int {
    var v = 0
    var loc = -1
    var i = 0
    while (i <= phrase.length - str.length && v < n) {
        if (str == phrase.substring(i, i + str.length)) {
            v++
            loc = i
        }
        i++
    }
    return if (v == n) loc else -1
}


fun getFiles(startDir: File): ArrayList<File> {
    var files = arrayListOf<File>()
    startDir.walkTopDown().forEach {
        if (it.isFile) {
            files.add(it)
        }
    }
    return files
}

fun getFilesWithExtension(startDir: File, ext: String): ArrayList<File> {
    var files = arrayListOf<File>()
    startDir.walkTopDown().forEach {
        if (it.isFile && it.name.endsWith(ext)) {
            files.add(it)
        }
    }
    return files
}


fun getTextInFile(filePath: String): String {
    val reader = BufferedReader(FileReader(filePath))
    val sb = StringBuilder()
    var line = reader.readLine()
    while (line != null) {
        sb.append(line.trim())
        sb.append("\n")
        line = reader.readLine()
    }
    return sb.toString()
}

fun listAllUploadedAppVersions(f: File): ArrayList<File> {
    if (!f.isDirectory) throw IllegalArgumentException()
    val res = ArrayList<File>()
    for (i in f.listFiles()) {
        if (i.isDirectory) {
            for (j in i.listFiles()) {
                if (j.isDirectory) {
                    res.add(j)
                }
            }
        }
    }
    return res
}

fun getUploadedSmaliFilesDir(context: Context): File {
    return File(context.filesDir, "uploadedSmaliFiles")
}

fun getUploadedResFilesDir(context: Context): File {
    return File(context.filesDir, "uploadedResFiles")
}

fun getAnalyzedTracesDir(context: Context): File {
    return File(context.filesDir, "traceAnalysisResults")
}

class MainActivity : Activity() {


    @Suppress("BlockingMethodInNonBlockingContext")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wifiManager: WifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddress: String =
            Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress())
        val t = findViewById<TextView>(R.id.TextView1)
        t.text = "ipAddress : $ipAddress"

        val uploadedSmaliDir = getUploadedSmaliFilesDir(applicationContext)
        val uploadedResDir = getUploadedResFilesDir(applicationContext)
        val analyzedTracesDir = getAnalyzedTracesDir(applicationContext)

        if (!uploadedSmaliDir.exists()) uploadedSmaliDir.mkdir()
        if (!analyzedTracesDir.exists()) analyzedTracesDir.mkdir()
        if (!uploadedResDir.exists()) uploadedResDir.mkdir()

        val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        executorService.submit {
            embeddedServer(Netty, 8080) {
                routing {
                    get("/") {
                        call.respondHtml {
                            head {

                            }
                            body {
                                h1 {
                                    +"Code Clone Detector"
                                }
                                ul {
                                    li {
                                        a("/smaliFiles") { +"See uploaded smali files" }
                                    }
                                }
                                br()
                                form(
                                    action = "/uploadSmaliFile",
                                    method = FormMethod.post,
                                    encType = FormEncType.multipartFormData
                                ) {
                                    h3 { +"upload smali folder" }
                                    div {
                                        label { +"file:" }
                                        fileInput(name = "smaliFiles") {
                                            accept = ".smali"
                                            multiple = true
                                            attributes["directory"] = ""
                                            attributes["webkitdirectory"] = ""
                                            attributes["mozdirectory"] = ""
                                        }
                                    }
                                    div {
                                        label { +"Application package name" }
                                        textInput(name = "appName")
                                    }
                                    div {
                                        label { +"Application version number" }
                                        numberInput(name = "appVersion")
                                    }
                                    div {
                                        submitInput {
                                            value = "upload"
                                        }
                                    }
                                }

                            }
                        }
                    }
                    post("/uploadSmaliFile") {
                        if (!call.request.isMultipart()) {
                            call.respondText("Not multipart request.", ContentType.Text.Html)
                        } else {
                            val multipart = call.receiveMultipart()

                            var appName: String? = null
                            var appVersion: String? = null

                            var uploadedFilesContents = arrayListOf<String>()
                            var filesNames = arrayListOf<String>()

                            multipart.forEachPart { part ->
                                when (part) {
                                    is PartData.FormItem -> {
                                        if ("appName".equals(part.name)) {
                                            appName = part.value
                                        } else if ("appVersion".equals(part.name)) {
                                            appVersion = part.value
                                        }
                                    }
                                    is PartData.FileItem -> {
                                        if ("smaliFiles".equals(part.name)) {
                                            val content = part.provider().readText()
                                            uploadedFilesContents.add(content)

                                            val fName = part.originalFileName
                                            if (fName != null) {
                                                val lastDot = fName.lastIndexOf('.')
                                                if (lastDot != -1) filesNames.add(
                                                    fName.substring(
                                                        0,
                                                        lastDot
                                                    )
                                                )
                                                else filesNames.add(fName)
                                            }
                                        }
                                    }
                                }
                                part.dispose()
                            }
                            if (filesNames.size != 0 &&
                                filesNames.size == uploadedFilesContents.size &&
                                appName != null &&
                                appVersion != null
                            ) {
                                for (i in 0..filesNames.size - 1) {
                                    val fileName = filesNames[i]
                                    val fileContent = uploadedFilesContents[i]
                                    val destPath =
                                        "${uploadedSmaliDir.absolutePath}/$appName/$appVersion/$fileName.smali"
                                    val destFile = File(destPath)
                                    destFile.parentFile.mkdirs()
                                    val writer = BufferedWriter(FileWriter(destFile))
                                    writer.write(fileContent)
                                    writer.close()
                                }
                            }
                            call.respondRedirect("/")
                        }
                    }
                    post("/uploadResFile") {
                        if (!call.request.isMultipart()) {
                            call.respondText("Not multipart request.", ContentType.Text.Html)
                        } else {
                            val multipart = call.receiveMultipart()

                            var appName: String? = null
                            var appVersion: String? = null

                            var uploadedFilesContents = arrayListOf<String>()
                            var filesNames = arrayListOf<String>()

                            multipart.forEachPart { part ->
                                when (part) {
                                    is PartData.FormItem -> {
                                        if ("appName".equals(part.name)) {
                                            appName = part.value
                                        } else if ("appVersion".equals(part.name)) {
                                            appVersion = part.value
                                        }
                                    }
                                    is PartData.FileItem -> {
                                        if ("smaliFiles".equals(part.name)) {
                                            val content = part.provider().readText()
                                            uploadedFilesContents.add(content)
                                            val fName = part.originalFileName
                                            if (fName != null) {
                                                filesNames.add(fName)
                                            }
                                        }
                                    }
                                }
                                part.dispose()
                            }


                            if (filesNames.size != 0 &&
                                filesNames.size == uploadedFilesContents.size &&
                                appName != null &&
                                appVersion != null
                            ) {
                                for (i in 0..filesNames.size - 1) {
                                    val fileName = filesNames[i]
                                    val fileContent = uploadedFilesContents[i]
                                    val destPath =
                                        "${uploadedResDir.absolutePath}/$appName/$appVersion/$fileName"
                                    val destFile = File(destPath)
                                    destFile.parentFile.mkdirs()
                                    val writer = BufferedWriter(FileWriter(destFile))
                                    writer.write(fileContent)
                                    writer.close()
                                }
                            }
                            call.respondRedirect("/")
                        }
                    }
                    get("/smaliFiles") {
                        val titleStr = "smali files list"
                        val appDirFiles = listAllUploadedAppVersions(uploadedSmaliDir)
                        call.respondHtml {
                            head {
                                title("Uploaded Smali Files")
                            }
                            body {
                                h1 {
                                    +titleStr
                                }
                                hr {}
                                h3 {
                                    +"Uploaded AppVersions"
                                }
                                ul {
                                    for (f in appDirFiles) {
                                        li {
                                            +f.path.substring(
                                                f.path.indexOf("/uploadedSmaliFiles/") +
                                                        "/uploadedSmaliFiles/".length
                                            )
                                        }
                                        form(
                                            action = "/simulateAppVersion",
                                            method = FormMethod.post
                                        ) {
                                            hiddenInput {
                                                name = "appName"
                                                value = f.parentFile.name
                                            }
                                            hiddenInput {
                                                name = "appVersion"
                                                value = f.name
                                            }
                                            submitInput {
                                                value = "simulate all methods in app"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    get("/smaliFiles/{...}") {
                        var titleStr = "smali files list"
                        val uri = call.request.uri
                        val s = findNthOccurrence(uri, "/", 2)
                        val resourcePath = uri.substring(s + 1)
                        val resourceFile = File(uploadedSmaliDir, resourcePath)

                        // Smali file path
                        if (resourceFile.exists() && resourceFile.isFile) {
                            val fileText = getTextInFile(resourceFile.absolutePath)
                            val sep = findNthOccurrence(resourcePath, "/", 2)
                            val basePath =
                                uploadedSmaliDir.absolutePath + "/" + resourcePath.substring(0, sep)
                            val classPath =
                                "L" + resourcePath.substring(sep + 1, resourcePath.length - 6) + ";"
                            val fpath = "uploadedSmaliFiles/" + resourcePath
                            try {
                                val sc = SmaliParser.parse(classPath, basePath)
                                val methods = sc.allMethods
                                call.respondHtml {
                                    head {
                                        title("File text")
                                    }
                                    body {
                                        div {
                                            ul {
                                                for (m in methods) {
                                                    li {
                                                        +m.classMethodSignature
                                                        form(
                                                            action = "/simulateMethod",
                                                            method = FormMethod.post
                                                        ) {
                                                            hiddenInput {
                                                                name = "filePath"
                                                                value = fpath

                                                            }
                                                            hiddenInput {
                                                                name = "methodSig"
                                                                value =
                                                                    m.classMethodSignature.split("->")[1]
                                                            }
                                                            submitInput {
                                                                value = "simulate method"
                                                            }
                                                        }
                                                        hr()
                                                    }
                                                }
                                            }

                                        }
                                        hr {}
                                        pre {
                                            +fileText
                                        }
                                    }
                                }
                            } catch (e: java.lang.Exception) {
                                call.respondHtml {
                                    head {
                                        title("Error Parsing Smali File")
                                    }
                                    body {
                                        div {
                                            +e.stackTraceToString()
                                        }
                                    }
                                }
                            }

                        }
                        // package path
                        else {
                            val files: ArrayList<File> = if (resourceFile.exists() &&
                                resourceFile.isDirectory
                            ) {
                                titleStr = "smali files list of $resourcePath"
                                getFilesWithExtension(resourceFile, ".smali")
                            } else {
                                getFilesWithExtension(uploadedSmaliDir, ".smali")
                            }
                            call.respondHtml {
                                head {
                                    title("Uploaded Smali Files")
                                }
                                body {
                                    h1 {
                                        +titleStr
                                    }
                                    ul {
                                        for (f in files) {
//                                            if (!isFrameworkClass(f, uploadedSmaliDir)) {
                                                li {
                                                    +f.path.substring(
                                                        f.path.indexOf("/uploadedSmaliFiles/") +
                                                                "/uploadedSmaliFiles/".length
                                                    )
                                                    form(
                                                        action = "/simulate",
                                                        method = FormMethod.post
                                                    ) {
                                                        hiddenInput {
                                                            name = "filePath"
                                                            value =
                                                                f.path.substring(f.path.indexOf("/files/") + 7)
                                                        }
                                                        submitInput {
                                                            value = "simulate methods"
                                                        }
                                                    }

                                                    form(
                                                        action = "/loadClazz",
                                                        method = FormMethod.post
                                                    ) {
                                                        hiddenInput {
                                                            name = "filePath"
                                                            value =
                                                                f.path.substring(f.path.indexOf("/files/") + 7)
                                                        }
                                                        submitInput {
                                                            value = "test load clazz"
                                                        }
                                                        hr()
                                                    }


                                                }
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    get("/analyzedTraces") {
                        val titleStr = "Analyzed traces"
                        val files = getFiles(analyzedTracesDir)
                        call.respondHtml {
                            head {
                                title("Anlyzed traces")
                            }
                            body {
                                h1 {
                                    +titleStr
                                }
                                ul {
                                    for (f in files) {
//                                        if (!isFrameworkClass(f, uploadedSmaliDir)) {
                                            li {
                                                +f.path.substring(
                                                    f.path.indexOf("/traceAnalysisResults/") +
                                                            "/traceAnalysisResults/".length
                                                )
                                            }
//                                        }
                                    }
                                }
                            }
                        }
                    }
                    get("/analyzedTraces/{...}") {
                        var titleStr = "Analyzed traces"
                        val uri = call.request.uri
                        val s = findNthOccurrence(uri, "/", 2)
                        val resourcePath = uri.substring(s + 1)
                        val resourceFile = File(analyzedTracesDir, resourcePath)

                        if (resourceFile.exists() && resourceFile.isFile) {
                            val fileText = getTextInFile(resourceFile.absolutePath)
                            call.respondHtml {
                                head {
                                    title("File text")
                                }
                                body {
                                    pre {
                                        +fileText
                                    }
                                }
                            }
                        } else {
                            val files: ArrayList<File> =
                                if (resourceFile.exists() && resourceFile.isDirectory) {
                                    titleStr = "Anlayzed Traces in : $resourcePath"
                                    getFiles(resourceFile)
                                } else {
                                    getFiles(uploadedSmaliDir)
                                }

                            call.respondHtml {
                                head {
                                    title("Anlyzed traces")
                                }
                                body {
                                    h1 {
                                        +titleStr
                                    }
                                    ul {
                                        for (f in files) {
//                                            if (!isFrameworkClass(f, uploadedSmaliDir)) {
                                                li {
                                                    +f.path.substring(
                                                        f.path.indexOf("/traceAnalysisResults/") +
                                                                "/traceAnalysisResults/".length
                                                    )
                                                }
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    post("/simulateMethod") {
                        val params = call.receiveParameters()
                        val filePath = params.get("filePath")
                        val methodSig = params.get("methodSig")

                        if (filePath == null || !filePath.endsWith(".smali")) {
                            call.respondText("filePath parameter not given")
                        } else if (methodSig == null) {
                            call.respondText("given method signature is null")
                        } else {
                            val appNameStart = findNthOccurrence(filePath, "/", 1) + 1
                            val versionNameStart = findNthOccurrence(filePath, "/", 2) + 1
                            val smaliFilePathStart = findNthOccurrence(filePath, "/", 3) + 1
                            if (appNameStart == -1 || versionNameStart == -1) {
                                call.respondText("filePath parameter is Invalid")
                                return@post
                            }

                            val appName = filePath.substring(appNameStart, versionNameStart - 1)
                            val appVersion =
                                filePath.substring(versionNameStart, smaliFilePathStart - 1)
                            val relativeFilePath = filePath.substring(smaliFilePathStart)
                            try {
                                Looper.prepare()
                            } catch (e: Exception) {

                            }

                            try {
                                SimulationManager.getInstance(
                                    applicationContext,
                                    appName,
                                    appVersion
                                ).analyzeSmaliMethod(relativeFilePath, methodSig)
                                System.gc();
                            } catch (e: Throwable) {
                                val writer = BufferedWriter(
                                    FileWriter(
                                        File(
                                            applicationContext.filesDir,
                                            "FailedServer.txt"
                                        ), true
                                    )
                                )
                                writer.write(appName + "_" + appVersion + "\n")
                                writer.write(filePath.substring(smaliFilePathStart) + "\n")
                                writer.write(e.stackTraceToString() + "\n")
                                writer.write("--------------------------" + "\n")
                                writer.flush()
                                writer.close()
                                if(e is OutOfMemoryError){
                                    call.respondText(e.stackTraceToString(), status = HttpStatusCode.InternalServerError)
                                    return@post
                                }
                                else {
                                    throw e
                                }
                            }

                            call.respondRedirect("/")
                        }
                    }
                    post("/simulate") {
                        val filePath = call.receiveParameters().get("filePath")

                        if (filePath == null || !filePath.endsWith(".smali")) {
                            call.respondText("filePath parameter not given")
                        } else {
                            val appNameStart = findNthOccurrence(filePath, "/", 1) + 1
                            val versionNameStart = findNthOccurrence(filePath, "/", 2) + 1
                            val smaliFilePathStart = findNthOccurrence(filePath, "/", 3) + 1
                            if (appNameStart == -1 || versionNameStart == -1) {
                                call.respondText("filePath parameter is Invalid")
                                return@post
                            }

                            val appName = filePath.substring(appNameStart, versionNameStart - 1)
                            val appVersion =
                                filePath.substring(versionNameStart, smaliFilePathStart - 1)
                            val relativeFilePath = filePath.substring(smaliFilePathStart)
                            try {
                                Looper.prepare()
                            } catch (e: Exception) {

                            }
                            try {
                                SimulationManager.getInstance(
                                    applicationContext,
                                    appName,
                                    appVersion
                                ).analyzeSmaliFile(relativeFilePath)
                            } catch (e: Exception) {
                                throw e
                            }

                            call.respondRedirect("/")
                        }
                    }
                    post("/simulateAppVersion") {
                        val params = call.receiveParameters().toMap()
                        val appName = params.get("appName")!!.get(0)
                        val appVersion = params.get("appVersion")!!.get(0)
                        try {
                            Looper.prepare()
                        } catch (e: Exception) {

                        }

                        try {
                            SimulationManager.getInstance(applicationContext, appName, appVersion)
                                .analyzeApp()
                        } catch (e: Exception) {
                            throw e
                        }


                        call.respondRedirect("/")
                    }
                    post("/loadClazz") {
                        val filePath = call.receiveParameters().get("filePath")

                        if (filePath == null || !filePath.endsWith(".smali")) {
                            call.respondText("filePath parameter not given")
                        } else {
                            val appNameStart = findNthOccurrence(filePath, "/", 1) + 1
                            val versionNameStart = findNthOccurrence(filePath, "/", 2) + 1
                            val smaliFilePathStart = findNthOccurrence(filePath, "/", 3) + 1
                            if (appNameStart == -1 || versionNameStart == -1) {
                                call.respondText("filePath parameter is Invalid")
                                return@post
                            }

                            val appName = filePath.substring(appNameStart, versionNameStart - 1)
                            val appVersion =
                                filePath.substring(versionNameStart, smaliFilePathStart - 1)
                            val relativeFilePath = filePath.substring(smaliFilePathStart)
                            val simulationManager = SimulationManager.getInstance(
                                applicationContext,
                                appName,
                                appVersion
                            )
                                val c = simulationManager.loadClazz(relativeFilePath)
                                val sb = java.lang.StringBuilder()
                                for(m in c.declaredMethods){
                                    sb.append(m).append("\n")
                                }
                                call.respondText(sb.toString())

                        }
                    }
                    get("/getAllEntryPointsOfApp/{appName}/{appVersion}") {
                        val appName = call.parameters["appName"]
                        val appVersion = call.parameters["appVersion"]
                        if (appName == null) {
                            call.respondText("appName is null!!!")
                        }
                        if (appVersion == null) {
                            call.respondText("appVersion is null!!!")
                        }

                        val i = SimulationManager.getInstance(
                            applicationContext,
                            appName!!,
                            appVersion!!
                        )
                        try {
                            val filePaths = i.getAllAppEntryPoints()
                            call.respondText(filePaths.joinToString("\n"))
                        } catch (e: Throwable) {
                            throw e
                        }
                        return@get
                    }
                    get("/getAllIndirectDependenciesOf/{appName}/{appVersion}/{smaliClassPath}"){
                        val appName = call.parameters["appName"]
                        val appVersion = call.parameters["appVersion"]
                        val smaliClassPath = call.parameters["smaliClassPath"]
                        if (appName == null) {
                            call.respondText("appName is null!!!")
                        }
                        if (appVersion == null) {
                            call.respondText("appVersion is null!!!")
                        }
                        if (smaliClassPath == null) {
                            call.respondText("smaliClassPath is null!!!")
                        }
                        val i = SimulationManager.getInstance(
                            applicationContext,
                            appName!!,
                            appVersion!!
                        )
                        try {
                            val sc = i.myClazzLoader.getSmaliClass(smaliClassPath)
                            val indirectDependencies = sc.getAllIndirectDependentSmaliTypes(i.myClazzLoader)
                            val sb = java.lang.StringBuilder()
                            for( i in indirectDependencies){
                                sb.append(i).append("\n")
                            }
                            call.respondText(sb.toString())
                        }
                        catch (e:java.lang.Exception){
                            call.respondText(e.stackTraceToString())
                        }
                    }
                    get("/getAllTypesReferencedInInstructionsOf/{appName}/{appVersion}/{smaliClassPath}"){
                        val appName = call.parameters["appName"]
                        val appVersion = call.parameters["appVersion"]
                        val smaliClassPath = call.parameters["smaliClassPath"]
                        if (appName == null) {
                            call.respondText("appName is null!!!")
                        }
                        if (appVersion == null) {
                            call.respondText("appVersion is null!!!")
                        }
                        if (smaliClassPath == null) {
                            call.respondText("smaliClassPath is null!!!")
                        }
                        val i = SimulationManager.getInstance(
                            applicationContext,
                            appName!!,
                            appVersion!!
                        )
                        try {
                            val sc = i.myClazzLoader.getSmaliClass(smaliClassPath)
                            val instructionDependencies = sc.getIndirectlyReferencedTypesInInstructions(i.myClazzLoader)
                            val sb = java.lang.StringBuilder()
                            for( i in instructionDependencies){
                                sb.append(i).append("\n")
                            }
                            call.respondText(sb.toString())
                        }
                        catch (e:java.lang.Exception){
                            call.respondText(e.stackTraceToString())
                        }
                    }
                }
            }.start(wait = true)
        }
    }
}