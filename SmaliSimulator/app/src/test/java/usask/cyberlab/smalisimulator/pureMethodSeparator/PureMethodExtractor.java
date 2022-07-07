package usask.cyberlab.smalisimulator.pureMethodSeparator;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import usask.cyberlab.smalisimulator.safeClassSeparation.Config;
import usask.cyberlab.smalisimulator.safeClassSeparation.Utils;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliParser;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;

public class PureMethodExtractor {

    public static HashSet<String> start(int apiVersion) throws Exception {

        HashSet<String> pureMethods = Utils.getPureMethods();
        ArrayList<String> allClassPaths = Utils.getListOfAllSdkClasses(apiVersion);
        HashSet<SmaliClass> allParsedSmaliClasses = new HashSet<>();
        HashMap<String, SmaliClass> allParsedSmaliClassesMap = new HashMap<>();


        String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);
        for (String classPath : allClassPaths) {
            try {
                SmaliClass sc = SmaliParser.parse(classPath, basePath);
                allParsedSmaliClasses.add(sc);
                allParsedSmaliClassesMap.put(classPath, sc);

            } catch (Exception e) {
                System.err.println("Error parsing " + classPath);
            }
        }

        int initialSetSize = -1;
        int setSizeAfterUpdate = -1;
        do {
            initialSetSize = pureMethods.size();
            updatePureMethods(pureMethods, allParsedSmaliClasses);
            setSizeAfterUpdate = pureMethods.size();
            System.out.println("--------");
            System.out.println(initialSetSize);
            System.out.println(setSizeAfterUpdate);
            System.out.println("--------");
        }
        while (initialSetSize != setSizeAfterUpdate);

        HashMap<String, String[]> typeHierarchy = new HashMap<>();
        FileReader fileReader = new FileReader(Config.allTypesHierarchyMapPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            String className = line.split(":")[0];
            String[] childClasses = line.split(":")[1].split("\\|");
            typeHierarchy.put(className, childClasses);
            line = bufferedReader.readLine();
        }


        HashSet<String> pureMethodsClone = (HashSet<String>) pureMethods.clone();
        for(String s: pureMethodsClone){
            String methodName = s.split("->")[1];
            if(methodName.startsWith("set") ||
                methodName.startsWith("put") ||
                methodName.startsWith("reset") ||
                methodName.startsWith("close") ||
                methodName.startsWith("add") ||
                methodName.startsWith("addAll") ||
                methodName.startsWith("remove") ||
                methodName.startsWith("delete") ||
                methodName.startsWith("update") ||
                methodName.startsWith("register") ||
                methodName.startsWith("unregister") ||
                methodName.startsWith("change")){
                pureMethods.remove(s);
            }

        }

        while (removeExtraPureMethods(typeHierarchy, pureMethods, allParsedSmaliClassesMap)){}
        return pureMethods;
    }


    private static void updatePureMethods(HashSet<String> pureMethods,
                                          HashSet<SmaliClass> allParsedClasses){
        for (SmaliClass sc: allParsedClasses){
            for(SmaliMethod sm: sc.getAllMethods()){
                String methodSig = sm.getClassMethodSignature();
                // if the smali method is present in the pureMethods set continue
                if(pureMethods.contains(methodSig)) continue;
                String s = methodSig.split("->")[0] + "->*";
                if(pureMethods.contains(s)) continue;
                s = "*->" + methodSig.split("->")[1];
                if(pureMethods.contains(s)) continue;
                // if the smali method is native continue
                if(sm.isNative()) continue;
                // if is classloader ignore
                if(methodSig.contains("<clinit>")) continue;


                boolean isPure = true;
                // every method is pure unless
                // it does sput, aput, iput and does not invoke methods that we know are not pure
                // if a method is invoked which belongs to an immutable type then it is fine


                if(!sm.isAbstract()){
                    for (SmaliInstruction si: sm.getInstructionList()) {
                        if(si.getOpCode().startsWith("sput")){
                            isPure = false;
                            break;
                        }
                        if(si.getOpCode().startsWith("aput")){
                            isPure = false;
                            break;
                        }
                        if(si.getOpCode().startsWith("iput")){
                            isPure = false;
                            break;
                        }
                        if(si instanceof InvokeInstruction){
                            InvokeInstruction ii = (InvokeInstruction) si;
                            String cm = ii.getClassMethodSignature();
                            String k = methodSig.split("->")[0] + "->*";
                            if(pureMethods.contains(k)) continue;
                            k = "*->" + methodSig.split("->")[1];
                            if(pureMethods.contains(k)) continue;
                            if(!pureMethods.contains(cm)){
                                isPure = false;
                                break;
                            }
                        }
                    }
                }


                if(isPure){
                    pureMethods.add(sm.getClassMethodSignature());
                }
            }
        }
    }

    private static boolean removeExtraPureMethods(HashMap<String, String[]> typeHierarchy,
                                               HashSet<String> pureMethods,
                                               HashMap<String, SmaliClass> allParsedSmaliClassesMap) throws Exception{

        System.out.println("===============");
        System.out.println(pureMethods.size());
        boolean changed = false;
        // for each method in pure methods
        HashSet<String> pureMethodsClone = (HashSet<String>) pureMethods.clone();
        outerLoop: for (String originalPureMethod: pureMethodsClone) {
            if(originalPureMethod.contains("*")) continue;
            String definingClass = originalPureMethod.split("->")[0];
            String methodSig = originalPureMethod.split("->")[1];

            String[] childs = typeHierarchy.get(definingClass);
            if(childs != null){
                // for all child classes of class in which the method is defined
                for(String childClass: childs){
                    // if the child class overwrites the method but the the overwritten versions is not pure
                    // then the original method is not pure and should be removed
                    SmaliClass sc = allParsedSmaliClassesMap.get(childClass);
                    if(sc.containsMethod(methodSig)){
                        String childMethodSig = childClass + "->" + methodSig;
                        if(!pureMethods.contains(childMethodSig)){
                            pureMethods.remove(originalPureMethod);
                            System.out.println("$$$$$" + originalPureMethod);
                            changed = true;
                            break outerLoop;
                        }
                    }
                }
            }

        }

        // with the set of new pure methods run the method analysis to remove methods
        // which invoke non pure methods
        pureMethodsClone = (HashSet<String>) pureMethods.clone();
        for (String pureMethod: pureMethodsClone) {
            if(pureMethod.contains("*")) continue;
            String definingClass = pureMethod.split("->")[0];
            String methodSig = pureMethod.split("->")[1];

            SmaliClass sc = allParsedSmaliClassesMap.get(definingClass);
            if(sc == null){
//                System.out.println("!!!" + pureMethod);
                continue;
            }
            SmaliMethod sm = sc.getMethod(methodSig);

            boolean isPure = true;
            if(sm == null){
//                System.out.println("!!!" + pureMethod);
                continue;
            }
            if(!sm.isAbstract()){
                for (SmaliInstruction si: sm.getInstructionList()) {

                    if(si.getOpCode().startsWith("sput") ||
                        si.getOpCode().startsWith("aput") ||
                            si.getOpCode().startsWith("iput")){
                        isPure = false;
                        break;
                    }

                    if(si instanceof InvokeInstruction){
                        InvokeInstruction ii = (InvokeInstruction) si;
                        String cm = ii.getClassMethodSignature();
                        String k = definingClass + "->*";
                        if(pureMethods.contains(k)) continue;
                        k = "*->" + methodSig;
                        if(pureMethods.contains(k)) continue;
                        if(!pureMethods.contains(cm)){
                            isPure = false;
                            break;
                        }
                    }
                }
            }
            if(!isPure){
                pureMethods.remove(pureMethod);
                System.out.println("#####" + pureMethod);
                changed = true;
            }
        }

        System.out.println(pureMethods.size());
        System.out.println("===============");
        return changed;
    }
}
