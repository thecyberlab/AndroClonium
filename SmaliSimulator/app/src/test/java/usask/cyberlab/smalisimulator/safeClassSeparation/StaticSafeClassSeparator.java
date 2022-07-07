package usask.cyberlab.smalisimulator.safeClassSeparation;



import java.util.ArrayList;
import java.util.HashSet;

import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeStaticInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliParser;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;

public class StaticSafeClassSeparator {



    public static HashSet<String>[] start(int apiVersion,
                             HashSet<String> instanceSafeClass,
                             HashSet<String> instancePartialSafeClasses) throws Exception{

        // load all the initial safe classes and put them in a set to represent statically safe classes
        HashSet<String> s_safeClasses = Utils.getInitialSafeClasses();
        HashSet<String> s_partialSafeClasses = new HashSet<>();
        HashSet<String>[] result = new HashSet[2];
        result[0] = s_safeClasses;
        result[1] = s_partialSafeClasses;

        // Inorder to determine safety we parse the file to get a SmaliClass object.
        // In this set we keep all the SmaliClasses we have parsed so far
        HashSet<SmaliClass> allParsedSmaliClasses = new HashSet<>();
        ArrayList<String> allClassPaths = Utils.getListOfAllSdkClasses(apiVersion);
        // read java smali files one by one
        String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);
        for (String classPath: allClassPaths) {
            try {
                SmaliClass sc = SmaliParser.parse(classPath, basePath);
                allParsedSmaliClasses.add(sc);
            } catch (Exception e) {
                System.err.println("Error parsing " + classPath);
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("===========================");
        }

        HashSet<String>[] newStuff = getNewStaticallySafeAndPartialSafe(s_safeClasses,
                s_partialSafeClasses,
                allParsedSmaliClasses,
                instanceSafeClass,
                instancePartialSafeClasses);

        if(newStuff == null) return result;

        while (newStuff != null){
            System.out.println("size of new stuff added : " + newStuff[0].size() + newStuff[1].size());
            s_safeClasses.addAll(newStuff[0]);
            s_partialSafeClasses.addAll(newStuff[1]);
            System.out.println("all staticSafeClasses : " + s_safeClasses.size());
            System.out.println("all staticPartialSafeClasses : " + s_partialSafeClasses.size());
            System.out.println("-----------");
            newStuff = getNewStaticallySafeAndPartialSafe(s_safeClasses,
                    s_partialSafeClasses,
                    allParsedSmaliClasses,
                    instanceSafeClass,
                    instancePartialSafeClasses);
        }
        return result;
    }

    private static HashSet<String>[] getNewStaticallySafeAndPartialSafe(HashSet<String> s_safeClasses,
                                                        HashSet<String> s_partialSafeClasses,
                                                        HashSet<SmaliClass> allSmaliClasses,
                                                        HashSet<String> i_safeClasses,
                                                        HashSet<String> i_partialSafeClasses){
        HashSet<String> newStaticSafeClasses = new HashSet<>();
        HashSet<String> newStaticPartialSafeClasses = new HashSet<>();

        outerloop: for(SmaliClass sc: allSmaliClasses){

            // ignore if class is statically safe
            if(s_safeClasses.contains(sc.getClassPath())) continue;

            for (SmaliField field: sc.getAllStaticFields()){
                if(field.isStatic()) continue ; // Ignore
                if(field.getType().length() == 1) continue; //OK
                if(field.getType().equals(sc.getClassPath())) continue; //OK

                if(field.getType().startsWith("[")){
                    String baseType = field.getType().replace("[","");
                    if(baseType.equals(sc.getClassPath())) continue; //OK

                    // If only array base type is not safe then it is bad
                    // BAD , class not safe, jump to next class for checking
                    if(baseType.startsWith("L") && !s_safeClasses.contains( baseType )) continue outerloop;
                }

                // BAD , class not safe, jump to next class for checking
                else if(! s_safeClasses.contains(field.getType()) ) continue outerloop;
            }

            boolean allMethodsAreSafe = true;
            HashSet<String> safeStaticMethods = new HashSet<>();

            for(SmaliMethod sm : sc.getAllMethods()){
                if(sm.getClassMethodSignature().contains("<clinit>()V")){
                    // if class is not safe to load statically then
                    // all of it's methods are not safe
                    if(! isSmaliMethodSafe(sm,
                            s_safeClasses,
                            s_partialSafeClasses,
                            i_safeClasses,
                            i_partialSafeClasses,new HashSet<String>())){
                        continue outerloop;
                    }
                }
            }

            for(SmaliMethod sm : sc.getAllMethods()){
                if(! sm.isStatic()) continue; // Ignore
                if(sm.getClassMethodSignature().contains("<clinit>()V")) continue;
                // if class and method combination is in partial safe classes
                if(s_partialSafeClasses.contains(sm.getClassMethodSignature())) continue; // Ignore

                if(isSmaliMethodSafe(sm, s_safeClasses, s_partialSafeClasses,
                        i_safeClasses, i_partialSafeClasses, new HashSet<String>())){
                    safeStaticMethods.add(sm.getClassMethodSignature());
                }
                // if not safe then
                else allMethodsAreSafe = false;

            }
            if(allMethodsAreSafe) newStaticSafeClasses.add(sc.getClassPath());
            else if(safeStaticMethods.size() > 0){
                newStaticPartialSafeClasses.addAll(safeStaticMethods);
            }

        }
        if(newStaticSafeClasses.size()+ newStaticPartialSafeClasses.size() == 0) return null;

        HashSet<String>[] result = new HashSet[2];
        result[0] = newStaticSafeClasses;
        result[1] = newStaticPartialSafeClasses;
        return result;
    }



    private static boolean isSmaliMethodSafe(SmaliMethod sm,
                                             HashSet<String> s_safeClasses,
                                             HashSet<String> s_partiallySafeClasses,
                                             HashSet<String> instanceSafeClass,
                                             HashSet<String> instancePartialSafeClasses,
                                             HashSet<String> invokers){
        if(sm.isNative()){
            return false;
        }

        // check that all method arguments are statically safe
        for(String argType : sm.getArgumentTypes()){
            if(argType.startsWith("L")){
                // if the argument type is same as the method defining class
                if(argType.equals(sm.getContainingClass().getClassPath())){
                    continue;
                }
                if(!s_safeClasses.contains(argType)){
                    return false;
                }
            }
        }

        // check that method instructions do not invoke methods of not safe classes
        for (SmaliInstruction instruction:sm.getInstructionList()) {
            if(instruction.getOpCode().startsWith("invoke-")){
                InvokeInstruction invokeInstruction = (InvokeInstruction) instruction;
                String classPath = invokeInstruction.getClassPath();

                // If we are invoking an static method
                if(invokeInstruction instanceof InvokeStaticInstruction){
                    if(instanceSafeClass.contains(classPath)) return true;
                    return instancePartialSafeClasses.contains(invokeInstruction.getClassMethodSignature());
                }
                // if we are invoking invoke-* except static
                else{
                    // calling a method from the same class that sm has been defined in
                    if(classPath.equals(sm.getContainingClass().getClassPath())){
                        SmaliMethod sm2 = sm.getContainingClass().getMethod(invokeInstruction.getMethodOnlySignature());
                        if(sm2 == null){
                            // base Object methods re ok even if the type of invoke object
                            // is not Object
                            if(invokeInstruction.getMethodOnlySignature().equals("toString()Ljava/lang/String;")) continue;
                            if(invokeInstruction.getMethodOnlySignature().equals("getClass()Ljava/lang/Class;")) continue;
                            if(invokeInstruction.getMethodOnlySignature().equals("hashCode()I")) continue;
                            if(invokeInstruction.getMethodOnlySignature().equals("equals(Ljava/lang/Object;)Z")) continue;
                            return false;
                        }
                        else {
                            if(sm2.isNative()) return false;
                            if(sm2.isAbstract()) return false;

                            // This is to stop infinite loop on methods calling each other
                            if(invokers.contains(sm2.getClassMethodSignature())) return false;
                            // if method invoked in invoke-* instruction is know to be safe then continue
                            if(s_partiallySafeClasses.contains(sm2.getClassMethodSignature())) continue;
                            invokers.add(sm.getClassMethodSignature());
                            if (isSmaliMethodSafe(sm2, s_safeClasses, s_partiallySafeClasses,
                                    instanceSafeClass,
                                    instancePartialSafeClasses,
                                    invokers)) return false;
                        }
                    }
                    else if(!s_safeClasses.contains(classPath)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
