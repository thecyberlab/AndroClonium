//package usask.cyberlab.smalisimulator.safeClassSeparation;
//
//import org.afp.simsmali.instructions.InvokeInstruction;
//import org.afp.simsmali.instructions.SmaliInstruction;
//import org.afp.simsmali.preprocess.SmaliParser;
//import org.afp.simsmali.representations.SmaliClass;
//import org.afp.simsmali.representations.SmaliField;
//import org.afp.simsmali.representations.SmaliMethod;
//import java.io.*;
//import java.util.*;
//
//public class SafeClassExtractor {
//
//
//
//    private static boolean addNewSemiSmaliClasses(HashSet<String> semiSafeClasses, HashSet<SmaliClass> parsedSmaliClasses){
//        boolean newSemiClassHasBeenAdded = false;
//        nextClassToCheck : for(SmaliClass sc: parsedSmaliClasses){
//            if(semiSafeClasses.contains(sc.getClassPath())){
//                continue;
//            }
//            // check that the parent of the class is also semi-safe
//            if(!semiSafeClasses.contains(sc.getParentClassPath())){
//                continue;
//            }
//
//            // a derived safe class can only implement a interface that is in safe classes
//            for(String s : sc.getInterfaces() ){
//                if(!semiSafeClasses.contains(s)){
//                    continue nextClassToCheck;
//                }
//            }
//
//            List<SmaliField> fields = sc.getAllFields();
//            List<SmaliMethod> methods = sc.getAllMethods();
//
//            // it only can use semi-safe classes for field types
//            // the only exception is it can use point to an object with itself's type
//            for (SmaliField f: fields) {
//                String type = f.getType();
//                if(!type.startsWith("L")){
//                    continue;
//                }
//                if(type.equals(sc.getClassPath())){
//                    continue;
//                }
//                if(!semiSafeClasses.contains(type)){
//                    continue nextClassToCheck;
//                }
//            }
//
//            for(SmaliMethod method:methods){
//
//                // check that method is not native
//                if(method.isNative()){
//                    continue nextClassToCheck;
//                }
//
//                // check that all method arguments are safe
//                for(String argType : method.getArgumentTypes()){
//                    if(argType.startsWith("L")){
//                        if(argType.equals(sc.getClassPath())){
//                            continue;
//                        }
//                        if(!semiSafeClasses.contains(argType)){
//                            continue nextClassToCheck;
//                        }
//                    }
//                }
//
//                // skip method if it is abstract
//                if(method.isAbstract()){
//                    continue;
//                }
//
//                // check that method instructions do not invoke methods of not safe classes
//                for (SmaliInstruction instruction:method.getSmaliInstructionList()) {
//                    if(instruction.getOpCode().startsWith("invoke-")){
//                        InvokeInstruction invokeInstruction = (InvokeInstruction) instruction;
//                        String classPath = invokeInstruction.getClassPath();
//                        if(!semiSafeClasses.contains(classPath)){
//                            continue nextClassToCheck;
//                        }
//                    }
//                }
//            }
//
//            semiSafeClasses.add(sc.getClassPath());
////            System.out.println(sc.getClassPath() + " was added!!!!");
//            newSemiClassHasBeenAdded = true;
//
//
//        }
//
//        return newSemiClassHasBeenAdded;
//    }
//
//    private static HashSet getSemiSafeClasses(HashSet<String> initialSafeClasses, HashSet<SmaliClass> parsedSmaliClasses){
//        HashSet<String> semiSafeClasses = new HashSet<>(initialSafeClasses);
//        int counter = 0;
//        while (addNewSemiSmaliClasses(semiSafeClasses, parsedSmaliClasses)){
//            counter++;
//        }
//        System.out.println("semi-classes calculated after "+counter + " loops");
//        return semiSafeClasses;
//
//    }
//
//    private static HashSet<String> getSafeClasses(HashSet<String> semiSafeClasses) throws IOException {
//        HashMap<String, String[]> typeHierarchy = new HashMap<>();
//
//        FileReader fileReader = new FileReader(Config.classesSubClassesMapPath);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String line = bufferedReader.readLine();
//        while (line != null){
//            line = line.strip();
//            String className = line.split(":")[0];
//            String[] childClasses = line.split(":")[1].split("\\|");
//            typeHierarchy.put(className, childClasses);
//            line = bufferedReader.readLine();
//        }
//
//
//        fileReader = new FileReader(Config.interfaceImplementationsMapPath);
//        bufferedReader = new BufferedReader(fileReader);
//        line = bufferedReader.readLine();
//        while (line != null){
//            line = line.strip();
//            String className = line.split(":")[0];
//            String[] childClasses = line.split(":")[1].split("\\|");
//            typeHierarchy.put(className, childClasses);
//            line = bufferedReader.readLine();
//        }
//
//
//
//        HashSet<String> newSafeClasses = new HashSet<>();
//
//        outerloop: for(String semiSafeClassName: semiSafeClasses){
//            String[] semiSafeChildClasses = typeHierarchy.get(semiSafeClassName);
//            if(semiSafeChildClasses != null){
//                for(String child:semiSafeChildClasses){
//                    if(!semiSafeClasses.contains(child)){
//                        continue outerloop;
//                    }
//                }
//            }
//
//            newSafeClasses.add(semiSafeClassName);
//        }
//
//        return newSafeClasses;
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        HashSet<String> safeClasses = new HashSet<>();
//
//        FileReader fileReader = new FileReader(Config.initialSafeJavaClassesPath);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String line = bufferedReader.readLine();
//        while (line != null){
//            line = line.strip();
//            if(line.startsWith("L")){
//                safeClasses.add(line);
//            }
//            line = bufferedReader.readLine();
//        }
//
//        HashSet<SmaliClass> allParsedSmaliClasses = new HashSet<>();
//        // read java smali files one by one
//        fileReader = new FileReader("allJavaClasses.txt");
//        bufferedReader = new BufferedReader(fileReader);
//        line = bufferedReader.readLine();
//        while (line != null) {
//            line = line.strip();
//            if(!line.endsWith("package-info.smali")){
//                try {
//                    SmaliClass sc = SmaliParser.parse(line).get(0);
//                    allParsedSmaliClasses.add(sc);
//                }catch (Exception e){
//                }
//            }
//
//            line = bufferedReader.readLine();
//        }
//
//        System.out.println("---------------------------------");
//        HashSet semiSafeClasses = getSemiSafeClasses(safeClasses, allParsedSmaliClasses);
//
//        safeClasses.addAll(getSafeClasses(semiSafeClasses));
//
//        //----------------------------
//        // removing safe classes that are not public
//
//
//
//
//        File f = new File("javaPackageSafeClasses.txt");
//        if(!f.exists()) f.createNewFile();
//        FileWriter fileWriter = new FileWriter(f);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//        for(String s:safeClasses){
//            bufferedWriter.write(s+"\n");
//        }
//        bufferedWriter.close();
//    }
//
//
//}
