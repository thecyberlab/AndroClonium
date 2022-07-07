package usask.cyberlab.smalisimulator.safeClassSeparation;

public class Config {

    public final static String initialSafeClassesListFilePath = "InitialSafeClasses.txt";

    private final static String androidSdkSmaliBasePath = "AndroidSdkSmali/";

    public static String getAndroidSdkSmaliDirectoryPath(int apiVersion){
        if(apiVersion < 0){
            throw new IllegalArgumentException();
        }
        return androidSdkSmaliBasePath + "android-" + apiVersion + "-smali";
    }


    public final static String interfaceInheritanceMapPath = "interfaceInheritanceMap.txt";

    public final static String interfaceImplementationsMapPath = "interfaceImplementationsMap.txt";

    public final static String classesInheritanceMapPath = "classesInheritanceMap.txt";

    public final static String classesSubClassesMapPath = "classesSubClassesMap.txt";

    public final static String allTypesHierarchyMapPath = "allTypesHierarchyMap.txt";


    public final static String staticallySafeClassesFilePath = "staticallySafeClasses.txt";

    public final static String staticallyPartialSafeClassesFilePath = "staticallyPartialSafeClasses.txt";

    public final static String initialPureMethodsFilePath = "initialPureMethods.txt";

    public final static String extractedPureMethodsFilePath = "pureMethods.txt";
}
