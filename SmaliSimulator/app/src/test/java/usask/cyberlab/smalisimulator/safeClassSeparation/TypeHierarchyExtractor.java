package usask.cyberlab.smalisimulator.safeClassSeparation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliParser;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;

public class TypeHierarchyExtractor {

    private static class ClassHierarchyExtractor {

        private ArrayList<String> allSmaliFilesList;
        private int apiVersion;

        ClassHierarchyExtractor(ArrayList<String> allSmaliFilePathList, int apiVersion){
            this.allSmaliFilesList = allSmaliFilePathList;
            this.apiVersion = apiVersion;
        }

        private HashMap<String, String> getRawClassesHierarchy() throws IOException {
            HashMap<String, String> rawClassHierarchy = new HashMap<>();
            String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);

            for(String classPath:allSmaliFilesList) {
                try {
                    SmaliClass sc = SmaliParser.parse(classPath, basePath);
                    if(!sc.isInterface()){
                        rawClassHierarchy.put(sc.getClassPath() ,sc.getParentClassPath());
                    }
                }catch (Exception e){
                }
            }
            return rawClassHierarchy;
        }

        private List<String> resolveParentClasses(String ClassName, HashMap<String, String> rawHierarchy){
            List<String> parentClasses = new ArrayList<>();
            String parent = rawHierarchy.get(ClassName);
            if(parent == null){
                return parentClasses;
            }
            parentClasses.add(parent);
            List<String> grandParents = resolveParentClasses(parent, rawHierarchy);
            for(String gp: grandParents){
                parentClasses.add(gp);
            }
            return parentClasses;
        }

        HashMap<String, List<String>> createClassesInheritanceMap() throws IOException {
            HashMap<String, String> rawClassesHierarchy = getRawClassesHierarchy();
            HashMap<String, List<String>> finalClassHierarchy = new HashMap<>();
            for(String k:rawClassesHierarchy.keySet()){
                finalClassHierarchy.put(k,resolveParentClasses(k,rawClassesHierarchy));
            }
            return finalClassHierarchy;
        }

        HashMap<String, HashSet<String>> createSubClassesMap(HashMap<String, List<String>> classInheritanceMap){
            HashMap<String, HashSet<String>> subClassMap = new HashMap<>();
            for(String s: classInheritanceMap.keySet()){
                List<String> subClassParentList = new ArrayList<>();
                subClassParentList.add(s);
                subClassParentList.addAll(classInheritanceMap.get(s));

                // starting from subClassParentList.size() - 2 to ignore the last element
                // which is the java/lang/Object; since we know all objects inherit from Object class
                for (int i = subClassParentList.size() - 2; i > 0 ; i--) {
                    String parent = subClassParentList.get(i);
                    for (int j = i - 1; j>= 0; j--){
                        String subclass = subClassParentList.get(j);
                        if(subClassMap.containsKey(parent)){
                            subClassMap.get(parent).add(subclass);
                        }
                        else {
                            HashSet<String> al = new HashSet<>();
                            al.add(subclass);
                            subClassMap.put(parent, al);
                        }
                    }
                }
            }
            return subClassMap;
        }
    }

    private static class InterfaceHierarchyExtractor {

        private ArrayList<String> allSmaliFilesList;
        private int apiVersion;

        InterfaceHierarchyExtractor(ArrayList<String> allSmaliFilePathList, int apiVersion){
            this.allSmaliFilesList = allSmaliFilePathList;
            this.apiVersion = apiVersion;
        }

        private HashMap<String, List<String>> getRawInterfacesHierarchy() throws IOException {
            HashMap<String, List<String>> rawInterfacesHierarchy = new HashMap<>();
            String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);
            int counter = 0;
            for(String classPath:allSmaliFilesList){
                try {
                    SmaliClass sc = SmaliParser.parse(classPath, basePath);
                    if(sc.isInterface() && !sc.isAnnotation()){
                        rawInterfacesHierarchy.put(sc.getClassPath(), sc.getInterfaces());
                    }
                }catch (Exception e){
                    System.err.println("Error parsing " + classPath);
                }
                System.out.println(counter + " / " + allSmaliFilesList.size());
                counter++;
            }
            return rawInterfacesHierarchy;
        }

        private HashSet<String> resolveParentInterfaces(String interfaceName, HashMap<String, List<String>> rawHierarchy){
            HashSet<String> parentInterfacesSet = new HashSet();
            List<String> parents = rawHierarchy.get(interfaceName);
            if(parents == null){
                return parentInterfacesSet;
            }
            for(String p: parents){
                parentInterfacesSet.add(p);
                HashSet<String> grandParents = resolveParentInterfaces(p, rawHierarchy);
                parentInterfacesSet.addAll(grandParents);
            }
            return parentInterfacesSet;
        }

        HashMap<String, HashSet<String>> createInterfacesInheritanceMap() throws IOException {
            HashMap<String, List<String>> rawInterfacesHierarchy = getRawInterfacesHierarchy();
            HashMap<String, HashSet<String>> finalInterfaceHierarchy = new HashMap<>();

            for(String k:rawInterfacesHierarchy.keySet()){
                finalInterfaceHierarchy.put(k,resolveParentInterfaces(k, rawInterfacesHierarchy));
            }
            return finalInterfaceHierarchy;
        }

        HashMap<String, HashSet<String>> createInterfacesImplementationLists(HashMap<String, HashSet<String>> interfaceInheritanceMap) {

            HashMap<String, HashSet<String>> interfaceImplementations = new HashMap<>();
            String basePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion);

            // for each concrete method extract the set of interfaces that it implements
            for(String classPath: allSmaliFilesList){
                SmaliClass sc;
                try {
                    sc = SmaliParser.parse(classPath, basePath);
                }catch (Exception e){
                    continue;
                }
                if(!sc.isAbstract() && !sc.isInterface()){
                    HashSet<String> interfaceSet = new HashSet<>();
                    interfaceSet.addAll(sc.getInterfaces());
                    // for each concrete class extract its direct interfaces
                    // (the interface can be declared in parent classes)
                    String parentClassPath = sc.getParentClassPath();
                    while (parentClassPath != null){
                        String parentFilePath = Config.getAndroidSdkSmaliDirectoryPath(apiVersion) + "/" +
                                parentClassPath.substring(1, parentClassPath.length() - 1) + ".smali";
                        SmaliClass psc;
                        try {
                            psc = SmaliParser.parse(parentFilePath, basePath);
                        }catch (Exception e){
                            parentClassPath = null;
                            continue;
                        }
                        interfaceSet.addAll(psc.getInterfaces());
                        parentClassPath = psc.getParentClassPath();
                    }
                    HashSet<String> extendedInterfaceSet = new HashSet<>();
                    for (String iface: interfaceSet) {
                        extendedInterfaceSet.add(iface);
                        HashSet<String> ifaceParents = interfaceInheritanceMap.get(iface);
                        if(ifaceParents != null){
                            for(String parent: ifaceParents){
                                extendedInterfaceSet.add(parent);
                            }
                        }
                    }
                    for (String iface: extendedInterfaceSet) {
                        if(interfaceImplementations.containsKey(iface)){
                            HashSet<String> implementations = interfaceImplementations.get(iface);
                            implementations.add(sc.getClassPath());
                        }
                        else {
                            HashSet<String> implementations = new HashSet<>();
                            implementations.add(sc.getClassPath());
                            interfaceImplementations.put(iface,implementations);
                        }
                    }
                }
            }
            return interfaceImplementations;
        }
    }

    private static void writeAndSortHashMapToFile(String filePath, HashMap map) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if(parentFile !=null && !parentFile.exists()){
            parentFile.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        List<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);

        for (String s: keyList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s).append(":");
            Collection c = (Collection) map.get(s);
            for(Object o: c){
                stringBuilder.append(o.toString()).append("|");
            }
            String res = stringBuilder.toString();
            if(res.endsWith("|")) res = res.substring(0,res.length() - 1);
            writer.write(res + "\n");
        }

        writer.close();
    }


    public static void start(int apiVersion) throws Exception {
        ArrayList<String> smaliFilePaths = Utils.getListOfAllSdkClasses(apiVersion);

        InterfaceHierarchyExtractor interfaceHierarchyExtractor = new InterfaceHierarchyExtractor(smaliFilePaths, apiVersion);
        ClassHierarchyExtractor classHierarchyExtractor = new ClassHierarchyExtractor(smaliFilePaths, apiVersion);

        HashMap<String, HashSet<String>> r1 = interfaceHierarchyExtractor.createInterfacesInheritanceMap();
        writeAndSortHashMapToFile(Config.interfaceInheritanceMapPath, r1);

        HashMap<String, HashSet<String>> r2 = interfaceHierarchyExtractor.createInterfacesImplementationLists(r1);
        writeAndSortHashMapToFile(Config.interfaceImplementationsMapPath, r2);

        HashMap<String, List<String>> r3 = classHierarchyExtractor.createClassesInheritanceMap();
        writeAndSortHashMapToFile(Config.classesInheritanceMapPath, r3);
        HashMap<String, HashSet<String>> r4 = classHierarchyExtractor.createSubClassesMap(r3);
        writeAndSortHashMapToFile(Config.classesSubClassesMapPath, r4);

        HashMap allTypeImplementations = new HashMap();
        allTypeImplementations.putAll(r2);
        allTypeImplementations.putAll(r4);
        writeAndSortHashMapToFile(Config.allTypesHierarchyMapPath, allTypeImplementations);
    }

}
