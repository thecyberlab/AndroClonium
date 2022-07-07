package usask.cyberlab.smalisimulator.safeClassSeparation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeStaticInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliParser;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;

public class InstanceSafeClassExtractor {


    public static HashSet<String>[] start(int apiVersion) throws Exception {

        HashSet<String> i_safeClasses = Utils.getInitialSafeClasses();
        HashSet<String> s_safeClasses = Utils.getStaticSafeClasses();
        HashSet<String> s_partialSafeClasses = Utils.getStaticPartialSafeClasses();


        // Inorder to determine safety we parse the file to get a SmaliClass object.
        // In this set we keep all the SmaliClasses we have parsed so far
        HashSet<SmaliClass> allParsedSmaliClasses = new HashSet<>();
        ArrayList<String> allClassPaths = Utils.getListOfAllSdkClasses(apiVersion);
        // read java smali files one by one
        String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);

        for (String classPath : allClassPaths) {
            try {
                SmaliClass sc = SmaliParser.parse(classPath, basePath);
                allParsedSmaliClasses.add(sc);
            } catch (Exception e) {
                System.err.println("Error parsing " + classPath);
            }
        }

        HashSet<String>[] r = getSemiSafeClassesAndPartialSemiSafeClasses(allParsedSmaliClasses,
                i_safeClasses, s_safeClasses, s_partialSafeClasses);

        HashSet<String> semiSafeClasses = r[0];
        HashSet<String> partiallySemiSafeClasses = r[1];

//        System.out.println("========####=======");
//        System.out.println(r[0].size());
//        System.out.println(r[1].size());
//        System.out.println("========####========");


        HashSet<String>[] r2 = createSafeAndPartialSafeClassSets(semiSafeClasses,
                partiallySemiSafeClasses);

//        System.out.println("========####=======");
//        System.out.println(r2[0].size());
//        System.out.println(r2[1].size());
//        System.out.println("========####========");

        HashMap<String, SmaliClass> allParsedSmaliClassesMap = new HashMap<>();
        for(SmaliClass sc: allParsedSmaliClasses){
            allParsedSmaliClassesMap.put(sc.getClassPath(), sc);
        }

        System.out.println("########################");
        boolean changed = removeSafeAndPartialSafeClassesUsingNewUnsafeMethods(allParsedSmaliClasses, r2[0], r2[1], allParsedSmaliClassesMap);
        while (changed){
            System.out.println(r2[0].size());
            System.out.println(r2[1].size());
            System.out.println("-----------");
            changed = removeSafeAndPartialSafeClassesUsingNewUnsafeMethods(allParsedSmaliClasses, r2[0], r2[1], allParsedSmaliClassesMap);
        }

        i_safeClasses.addAll(r2[0]);
        HashSet<String> i_partialSafeClasses = new HashSet<>(r2[1]);

        HashSet<String>[] res = new HashSet[2];
        res[0] = i_safeClasses;
        res[1] = i_partialSafeClasses;
        return res;

    }

    private static boolean removeSafeAndPartialSafeClassesUsingNewUnsafeMethods(HashSet<SmaliClass> allParsedSmaliClasses,
                                                                                HashSet<String> instanceSafeClasses,
                                                                                HashSet<String> instancePartialSafeClasses,
                                                                                HashMap<String, SmaliClass> allParsedSmaliClassesMap) {
        boolean changed = false;

        for (String className: (HashSet<String>) instanceSafeClasses.clone()) {
            SmaliClass sc = allParsedSmaliClassesMap.get(className);
            boolean methodRemovedFromSafeClass = false;
            HashSet<SmaliMethod> safeMethods = new HashSet<>();

            for (SmaliMethod sm:sc.getAllMethods()) {
                if(checkAgainSmaliMethodSafe(sm, instanceSafeClasses, instancePartialSafeClasses)){
                    safeMethods.add(sm);
                }
                else {
                    changed = true;
                    methodRemovedFromSafeClass = true;
                    instanceSafeClasses.remove(className);
                }
            }
            // if the safe type had some unsafe method then
            // we should move all the other safe methods left to partialSafeClasses set
            if(methodRemovedFromSafeClass){
                for(SmaliMethod sm:safeMethods){
                    String s = sm.getClassMethodSignature();
                    instancePartialSafeClasses.add(s);
                }
            }
        }

        for (String sig:(HashSet<String>) instancePartialSafeClasses.clone()){
            String className = sig.split("->")[0];
            String methodName = sig.split("->")[1];
            SmaliClass sc = allParsedSmaliClassesMap.get(className);
            SmaliMethod sm = sc.getMethod(methodName);
            if(!checkAgainSmaliMethodSafe(sm, instanceSafeClasses, instancePartialSafeClasses)){
               changed = true;
               instancePartialSafeClasses.remove(sig);
            }

        }

        return changed;
    }


    private static boolean checkAgainSmaliMethodSafe(SmaliMethod sm,
                                                     HashSet<String> instanceSafeClasses,
                                                     HashSet<String> instancePartialSafeClasses){
        String[] argTypes = sm.getArgumentTypes();
        // check method args are safe
        for (String argType: argTypes) {
            if(argType.startsWith("[")){
                argType = argType.replace("[", "");
            }
            if(argType.startsWith("L") && !instanceSafeClasses.contains(argType)){
                return false;
            }
        }
        // check method return type is safe
        String retType = sm.getReturnType();
        if(retType.contains("[")){
            retType = retType.replace("[", "");
        }
        if(retType.startsWith("L") && !instanceSafeClasses.contains(retType)){
            return false;
        }

        if(!sm.isAbstract()) {
            //check all instance method invocations in smali methods are also safe
            if (!checkAgainSmaliMethodBodySafe(sm, instanceSafeClasses, instancePartialSafeClasses)) {
                return false;
            }
        }
        return true;
    }



    private static boolean checkAgainSmaliMethodBodySafe(SmaliMethod sm, HashSet<String> instanceSafeClasses,
                                                HashSet<String> instancePartialSafeClasses) {
        ArrayList<SmaliInstruction> l = sm.getInstructionList();
        if(l == null){
            return true;
        }
        for(SmaliInstruction si: l){
            if(si instanceof InvokeInstruction && !(si instanceof InvokeStaticInstruction)){
                InvokeInstruction invokeInstruction = (InvokeInstruction) si;
                // if the method invocation is on a semi-safe class it is ok
                // and continue to check next instruction
                if(instanceSafeClasses.contains(invokeInstruction.getClassPath())){
                    continue;
                }
                // if the invoked method is also not in partial semi-safe classes then
                // the method containing the invoke instruction is not safe
                if(!instancePartialSafeClasses.contains(invokeInstruction.getClassMethodSignature())){
                    return false;
                }
            }

        }
        return true;
    }

    private static HashSet<String>[] createSafeAndPartialSafeClassSets(HashSet<String> semiSafeClasses,
                                                                       HashSet<String> partiallySemiSafeClasses) throws Exception {

        HashMap<String, String[]> typeHierarchy = new HashMap<>();
        FileReader fileReader = new FileReader(Config.classesSubClassesMapPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            String className = line.split(":")[0];
            String[] childClasses = line.split(":")[1].split("\\|");
            typeHierarchy.put(className, childClasses);
            line = bufferedReader.readLine();
        }

        fileReader = new FileReader(Config.interfaceImplementationsMapPath);
        bufferedReader = new BufferedReader(fileReader);
        line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            String className = line.split(":")[0];
            String[] childClasses = line.split(":")[1].split("\\|");
            typeHierarchy.put(className, childClasses);
            line = bufferedReader.readLine();
        }

        HashSet<String> safeClasses = new HashSet<>();
        HashSet<String> partialSafeClasses = new HashSet<>();
        HashSet[] res = new HashSet[2];
        res[0] = safeClasses;
        res[1] = partialSafeClasses;

        outerloop: for(String semiSafeClassName: semiSafeClasses){
            String[] childClasses = typeHierarchy.get(semiSafeClassName);
            if(childClasses != null){
                for(String child:childClasses){
                    if(!semiSafeClasses.contains(child)){
                        continue outerloop;
                    }
                }
            }
            safeClasses.add(semiSafeClassName);
        }


        for(String semiSafeMethod: partiallySemiSafeClasses){
            String className = semiSafeMethod.split("->")[0];
            String methodName = semiSafeMethod.split("->")[1];
            if(semiSafeClasses.contains(className)){
                continue;
            }
            partialSafeClasses.add(semiSafeMethod);
        }


        return res;
    }


    private static HashSet<String>[] getSemiSafeClassesAndPartialSemiSafeClasses(HashSet<SmaliClass> allParsedSmaliClasses,
                                                                                     HashSet<String> initialSafeClasses,
                                                                                     HashSet<String> s_safeClasses,
                                                                                     HashSet<String> s_partialSafeClasses) {
        HashSet<String> semiSafeClasses = new HashSet<>(initialSafeClasses);
        HashSet<String> partialSemiSafeClasses = new HashSet<>();
        int counter = 0;
        while(updateSemiSafeClassesAndMethodsOnce(allParsedSmaliClasses, semiSafeClasses,
                partialSemiSafeClasses, s_safeClasses, s_partialSafeClasses)){
            System.out.println("* " + counter);
            counter++;
        }

        HashSet<String>[] res = new HashSet[2];
        res[0] = semiSafeClasses;
        res[1] = partialSemiSafeClasses;
        return res;


    }

    private static boolean updateSemiSafeClassesAndMethodsOnce(HashSet<SmaliClass> allParsedSmaliClasses,
                                                           HashSet<String> semiSafeClasses,
                                                           HashSet<String> partialSemiSafeClasses,
                                                           HashSet<String> s_safeClasses,
                                                           HashSet<String> s_partialSafeClasses) {
        boolean updated = false;
        outerloop:for(SmaliClass sc: allParsedSmaliClasses) {
            if(!isSemiSafeExceptMethods(sc, semiSafeClasses)){
                continue;
            }
            List<SmaliMethod> allMethods = sc.getAllMethods();
            List<String> safeMethods = new ArrayList<>();

            // first check that the constructor is safe
            // if not then non of instance methods are safe
            for(SmaliMethod method:allMethods){
                if(method.getClassMethodSignature().contains("<init>")){
                    if(!isMethodSafe(method, sc,
                            semiSafeClasses, partialSemiSafeClasses,
                            s_safeClasses, s_partialSafeClasses)) {
                        continue outerloop;
                    }
                }
            }


            nextMethodToCheck: for(SmaliMethod method:allMethods){
                // skip method if it is abstract
                if(method.isAbstract()){
                    safeMethods.add(method.getClassMethodSignature());
                }
                else if(isMethodSafe(method, sc,
                        semiSafeClasses, partialSemiSafeClasses,
                        s_safeClasses, s_partialSafeClasses)){
                    safeMethods.add(method.getClassMethodSignature());
                }

            }

            if(allMethods.size() == safeMethods.size() &&
                    !semiSafeClasses.contains(sc.getClassPath())){
                    updated = true;
                    semiSafeClasses.add(sc.getClassPath());
            }
            else {
                if(safeMethods.size() > 0 &&
                        !partialSemiSafeClasses.containsAll(safeMethods)) {
                    partialSemiSafeClasses.addAll(safeMethods);
                    updated = true;
                }
            }
        }
        return updated;
    }

    private static boolean isSemiSafeExceptMethods(SmaliClass sc,
                                                   HashSet<String> semiSafeClasses) {
        // sc is already safe
        if (semiSafeClasses.contains(sc.getClassPath())) {
            return true;
        }
        // check that the parent of the class is also semi-safe
        if (!semiSafeClasses.contains(sc.getParentClassPath())) {
            return false;
        }
        // a new semi-safe class can only implement a interface
        // that is in semi-safe classes
        for (String s : sc.getInterfaces()) {
            if (!semiSafeClasses.contains(s)) {
                return false;
            }
        }

        List<SmaliField> fields = sc.getAllFields();
        // a semi-safe class can only use semi-safe classes for field types
        // the only exception is it can use point to an object with itself's type
        for (SmaliField f: fields) {
            String type = f.getType();
            if(type.startsWith("[")){
                type = type.replace("[", "");
            }
            if(!type.startsWith("L")){
                continue;
            }
            if(type.equals(sc.getClassPath())){
                continue;
            }
            if(!semiSafeClasses.contains(type)){
                return false;
            }
        }

        return true;
    }

    private static boolean isMethodSafe(SmaliMethod method, SmaliClass sc,
                                        HashSet<String> semiSafeClasses,
                                        HashSet<String> partialSemiSafeClasses,
                                        HashSet<String> s_safeClasses,
                                        HashSet<String> s_partialSafeClasses){

        // check that method is not native
        if(method.isNative()){
            return false;
        }

        // check that all method arguments are safe
        for(String argType : method.getArgumentTypes()){
            if(argType.contains("[")){
                argType = argType.replace("[", "");
            }
            if(argType.startsWith("L")){
                if(argType.equals(sc.getClassPath())){
                    continue;
                }
                if(!semiSafeClasses.contains(argType)){
                    return false;
                }
            }
        }
        // check that method instructions do not invoke unsafe methods
        for (SmaliInstruction instruction:method.getInstructionList()) {
            if(instruction.getOpCode().startsWith("invoke-static")){
                InvokeInstruction invokeInstruction = (InvokeInstruction) instruction;
                // if the invoked static method is not on a statically safe class
                // it is ok and continue to next instruction
                if(s_safeClasses.contains(invokeInstruction.getClassPath())){
                    continue;
                }
                // also if the invoked static method is not in statically partial safe classes
                // then the containing method is not safe and continue to next method
                if(!s_partialSafeClasses.contains(invokeInstruction.getClassMethodSignature())){
                    return false;
                }

            }
            else if(instruction.getOpCode().startsWith("invoke-")){
                InvokeInstruction invokeInstruction = (InvokeInstruction) instruction;

                // if the method invocation is on a semi-safe class it is ok
                // and continue to check next instruction
                if(semiSafeClasses.contains(invokeInstruction.getClassPath())){
                    continue;
                }
                // if the invoked method is also not in partial semi-safe classes then
                // the method containing the invoke instruction is not safe
                if(!partialSemiSafeClasses.contains(invokeInstruction.getClassMethodSignature())){
                    return false;
                }
            }
        }
        return true;
    }

}
