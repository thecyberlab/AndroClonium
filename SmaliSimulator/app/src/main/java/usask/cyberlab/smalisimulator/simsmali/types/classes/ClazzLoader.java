package usask.cyberlab.smalisimulator.simsmali.types.classes;

import android.content.Context;
import android.content.res.AssetManager;

import net.bytebuddy.android.AndroidClassLoadingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import usask.cyberlab.smalisimulator.Utils;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliClassManager;
import usask.cyberlab.smalisimulator.simsmali.exceptions.CyclicClassException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;


/**
 * This class is responsible for caching the loaded Classes in the system.
 * In the simulator each loaded class is represented by a Clazz object.
 * This class also holds the list of safe methods and classes
 * **/
public class ClazzLoader {
    // The base path from which we start looking for smali files
    private final String basePath;
    // SmaliClassManager keeps track of parsed smali files in the form of SmaliClass
    // so we don't parse them twice
    private final SmaliClassManager smaliClassManager;
    // pool of loaded classes
    private final Map<String, Clazz> pool = new HashMap<>();
    private final HashSet<String> loadingSmaliClassInProgress = new HashSet<>();

    // In smali byte-code we can have classes use '-' in their name which is illegal
    // for us. So we have to replace their '-' with another character and this map
    // we keep track the old name and new name.
    public static HashMap<String,String> classesWithReplacedIllegalNames = new HashMap<>();

    // when a type with cyclic dependency is created, we can not call <clinit> on it
    // until all other classes that it has dependency on it has been loaded. Also
    // the <clinit> instructions can reference a class that is in loading process.
    // So we have to mark these classes and at the end we call <clinit> on all of them.
    public ArrayList<String> toInvokeClinitLazily = new ArrayList<>();

    private final HashSet<String> s_safeClasses;
    private final HashSet<String> i_safeClasses;
    private final HashSet<String> s_partialSafeClasses;
    private final HashSet<String> i_partialSafeClasses;

    private final HashSet<String> immutable_types;
    private final HashSet<String> pure_methods;

    private final HashSet<String> androidEntryPointMethods;
    private final HashSet<String> androidCallBackInterfaces;

    private final AndroidClassLoadingStrategy loadingStrategy;
    // we need to keep track of last ClassLoader that byte-buddy generates
    // we start by using our own ClassLoader but after byte-buddy finishes creating
    // a type, this field is updated
    public static ClassLoader javaClassLoader = ClazzLoader.class.getClassLoader();

    ///==========================================================


    private static class SavedInitialArrayValues{
        ArrayObjekt array;
        Object[] values;
    }

    // In this map we keep track of initial values of static fields
    // so when we we want to re-simulate a method we can use the same static values
    private final HashMap<String, Object> initialStaticValuesPool = new HashMap<>();

    public void saveInitialStaticFieldValues(SmaliClazz clazz){
        for(SmaliField f:clazz.getSmaliClass().getAllStaticFields()){
            String fieldType = f.getType();
            String sig = clazz.getClassPath() + ":" + f.getName() + ":" + fieldType;
            if(SimulationUtils.isPrimitiveType(fieldType)){
                Object v = clazz.getStaticFieldValue(f.getName());
                initialStaticValuesPool.put(sig, v);
            }
            else if(fieldType.startsWith("[")){
                Object rawV = clazz.getStaticFieldValue(f.getName());
                if(rawV instanceof ArrayObjekt) {
                    ArrayObjekt v = (ArrayObjekt) rawV;
                    SavedInitialArrayValues sv = getSavedInitialArrayValues(v);
                    initialStaticValuesPool.put(sig, sv);
                }
                else {
                    AmbiguousValue av = (AmbiguousValue) rawV;
                    initialStaticValuesPool.put(sig, av);
                }
            }
            else {
                //TODO for normal objects, we also have to reset their
                // instance fields but this can cause recursion
                // This is for later if I figure it out
                Object v = clazz.getStaticFieldValue(f.getName());
                initialStaticValuesPool.put(sig, v);
            }
        }
    }

    private SavedInitialArrayValues getSavedInitialArrayValues(ArrayObjekt arrayObjekt){
        if(arrayObjekt == null) return null;

        SavedInitialArrayValues sv = new SavedInitialArrayValues();
        sv.array = arrayObjekt;
        sv.values = new Object[arrayObjekt.getSize()];
        for(int i=0; i < arrayObjekt.getSize() ;i++){
            Object o = arrayObjekt.getValue(i);
            if(o instanceof ArrayObjekt){
                sv.values[i] = getSavedInitialArrayValues((ArrayObjekt) o);
            }
            else {
                sv.values[i] = o;
            }
        }
        return sv;
    }

    public void resetStaticFieldsToInitialValues(){
        for(String sig: initialStaticValuesPool.keySet()){
            String[] splits = sig.split(":");
            String classPath = splits[0];
            String fieldName = splits[1];
            String fieldType = splits[2];
            Object value = initialStaticValuesPool.get(sig);
            SmaliClazz smaliClazz = (SmaliClazz) getClazz(classPath);
            if(fieldType.startsWith("[")){
                if(value instanceof AmbiguousValue){
                    smaliClazz.setStaticFieldValue(fieldName, value);
                }
                else {
                    SavedInitialArrayValues sv = (SavedInitialArrayValues) value;
                    if (sv != null) {
                        resetArrayValues(sv);
                        smaliClazz.setStaticFieldValue(fieldName, sv.array);
                    } else {
                        smaliClazz.setStaticFieldValue(fieldName, null);
                    }
                }
            }
            else {
                smaliClazz.setStaticFieldValue(fieldName, value);
            }

        }
    }

    private void resetArrayValues(SavedInitialArrayValues sv){
        Object[] values = sv.values;
        for (int i = 0; i < values.length; i++) {
            Object o = values[i];
            if (o instanceof SavedInitialArrayValues) {
                SavedInitialArrayValues sv2 = (SavedInitialArrayValues) o;
                resetArrayValues(sv2);
                sv.array.setValue(i, sv2.array);
            } else {
                sv.array.setValue(i, o);
            }
        }
    }


    // =================================================

    public ClazzLoader(String basePath, Context context) {
        this.smaliClassManager = new SmaliClassManager(basePath);
        this.basePath = basePath;
        this.loadingStrategy = new AndroidClassLoadingStrategy.Wrapping(context.getCacheDir());
        AssetManager assetManager = context.getAssets();
        s_safeClasses = SimulationUtils.getStaticSafeClasses(assetManager);
        s_partialSafeClasses = SimulationUtils.getStaticPartialSafeClasses(assetManager);
        i_safeClasses = SimulationUtils.getInstanceSafeClasses(assetManager);
        i_partialSafeClasses = SimulationUtils.getInstancePartialSafeClasses(assetManager);
        immutable_types = SimulationUtils.getImmutableClasses(assetManager);
        pure_methods = SimulationUtils.getPureMethods(assetManager);
        androidEntryPointMethods = SimulationUtils.getAndroidEntryPointMethods(assetManager);
        androidCallBackInterfaces = SimulationUtils.getAndroidCallBackInterfaces(assetManager);
    }

    public String getBasePath() {
        return basePath;
    }

    public AndroidClassLoadingStrategy getLoadingStrategy() {
        return loadingStrategy;
    }

    public SmaliClassManager getSmaliClassManager(){
        return smaliClassManager;
    }

    //========================================

    public int getClassPoolSize(){
        return pool.size();
    }

    public String[] getAllLoadedClassesPath(){
        Set<String> keySet = pool.keySet();
        return keySet.toArray(new String[0]);
    }

    public boolean isClazzLoaded(String classPath){
        return pool.containsKey(classPath);
    }

    //=========================================

    public boolean isMalformed(String classPath){
        if(classPath.length() == 1){
            return !SimulationUtils.isPrimitiveType(classPath) && !"V".equals(classPath);
        }
        else if(classPath.startsWith("L")){
            return (!classPath.startsWith("L") || !classPath.endsWith(";") || classPath.contains("."));
        }
        else if(classPath.startsWith("[")){
            return isMalformed(classPath.replace("[", ""));
        }
        return true;
    }

    public SmaliClass getSmaliClass(String classPath) {
        if(isMalformed(classPath)){
            throw new SmaliSimulationException("malformed class path:"+classPath);
        }
        if(pool.containsKey(classPath)){
            Clazz clazz = pool.get(classPath);
            if(!(clazz instanceof SmaliClazz)){
                // This happens when we try to get SmaliClass of
                // a library that is used in our own tool
                // which makes our tool look at it as Android/Java class
                // but actually has a corresponding smali class
                return smaliClassManager.getSmaliClass(classPath);
            }
            SmaliClazz smaliClazz = (SmaliClazz) clazz;
            return smaliClazz.getSmaliClass();
        }
        else {
            return smaliClassManager.getSmaliClass(classPath);
        }
    }

    public Clazz getClazz(String classPath){
        if(isMalformed(classPath)){
            throw new SmaliSimulationException("malformed class path :" + classPath);
        }
        if(pool.containsKey(classPath)){
            return pool.get(classPath);
        }
        else{
            loadClazz(classPath);
            return pool.get(classPath);
        }
    }

    private void loadClazz(String classPath) {
        if(loadingSmaliClassInProgress.contains(classPath)){
            throw new CyclicClassException(classPath);
        }

        if(!pool.containsKey(classPath)){
            if(SimulationUtils.isPrimitiveType(classPath) || "V".equals(classPath)){
                Class cls = SimulationUtils.getPrimitiveClassFromSmaliType(classPath.charAt(0));
                ReflectedClazz reflectedClazz = new ReflectedClazz(this, classPath, cls);
                pool.put(classPath, reflectedClazz);
            }
            else {
                if (SimulationUtils.isJavaOrAndroidExistingClass(classPath)) {
                    Class cls;
                    try {
                        String javaStylePath = SimulationUtils.makeJavaStyleClassPath(classPath);
                        cls = Class.forName(javaStylePath);
                    } catch (ClassNotFoundException e) {
                        throw new SmaliSimulationException(e);
                    }
                    ReflectedClazz reflectedClazz = new ReflectedClazz(this, classPath, cls);
                    pool.put(classPath, reflectedClazz);
                } else if (classPath.startsWith("[")) {
                    SmaliArrayClazz smaliArrayClazz = new SmaliArrayClazz(classPath, this);
                    pool.put(classPath, smaliArrayClazz);
                } else {
                    SmaliClass sc = smaliClassManager.getSmaliClass(classPath);
                    loadingSmaliClassInProgress.add(classPath);
                    SmaliClazz simClazz = new SmaliClazz(sc, this);
                    loadingSmaliClassInProgress.remove(classPath);
                    pool.put(classPath, simClazz);

                    // we can only call <clinit> when we have load all classes
                    // and there is no other class on process of loading
                    // this is because during execution of <clinit> the code may reference
                    // classes that are in process of loading which
                    // will cause another loadClazz call which will cause a CyclicClassException
                    if(loadingSmaliClassInProgress.size() == 0){
                        simClazz.invokeCLinit(this);
                        if(toInvokeClinitLazily.size() > 0){
                            doLazyClinit();
                        }
                    }
                    else {
                        toInvokeClinitLazily.add(simClazz.getClassPath());
                    }
                }
            }
        }
    }

    public void loadClazz(SmaliClass smaliClass) {
        String classPath = smaliClass.getClassPath();
        if(!pool.containsKey(classPath)){
            loadingSmaliClassInProgress.add(classPath);
            SmaliClazz simClazz = new SmaliClazz(smaliClass,this);
            loadingSmaliClassInProgress.remove(classPath);
            pool.put(classPath, simClazz);

//            if(toInvokeClinitLazily.size() > 0) {
//                for (String s : toInvokeClinitLazily) {
//                    SmaliClazz clazz = (SmaliClazz) this.getClazz(s);
//                    clazz.initializeParentAndInterfaceTypes();
//                }
//            }

            // we can only call <clinit> when we have load all classes
            // and there is no other class on process of loading
            // this is because during execution of <clinit> the code may reference
            // classes that are in process of loading which
            // will cause another loadClazz call which will cause a CyclicClassException
            if(loadingSmaliClassInProgress.size() == 0){
                simClazz.invokeCLinit(this);
                if(toInvokeClinitLazily.size() > 0){
                    doLazyClinit();
                }
            }
            else {
                toInvokeClinitLazily.add(simClazz.getClassPath());
            }

        }
    }

    public String saveLoadedCyclicClass(Class cls){
        String classPath = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        if(!pool.containsKey(classPath)){
            SmaliClass sc = smaliClassManager.getSmaliClass(classPath);
            SmaliClazz smaliClazz = new SmaliClazz(sc, this, cls);
            pool.put(classPath, smaliClazz);
            loadingSmaliClassInProgress.remove(smaliClazz.getClassPath());
            return smaliClazz.getClassPath();
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    //=============================================

    public boolean isInStaticSafeClasses(String s){
        return s_safeClasses.contains(s);
    }

    public boolean isInStaticPartialSafeClasses(String s){
        return s_partialSafeClasses.contains(s);
    }

    public boolean isInInstanceSafeClasses(String s){
        return i_safeClasses.contains(s);
    }

    public boolean isInInstancePartialSafeClasses(String s){
        return i_partialSafeClasses.contains(s);
    }

    // This method is added only for testing purposes
    public void addTypeToStaticSafeClasses(Clazz clazz){
        s_safeClasses.add(clazz.getClassPath());
    }

    // This method is added only for testing purposes
    public void addTypeToInstanceSafeClasses(Clazz clazz){
        i_safeClasses.add(clazz.getClassPath());
    }

    // This method is added only for testing purposes
    public void removeTypeFromStaticSafeClasses(String s){
        s_safeClasses.remove(s);
    }

    // This method is added only for testing purposes
    public void removeTypeFromInstanceSafeClasses(String s){
        i_safeClasses.remove(s);
    }

    // This method is added only for testing purposes
    public HashMap<String, Object> getInitialStaticValuesPool() {
        return initialStaticValuesPool;
    }

    public boolean isTypeImmutable(String t){
        return immutable_types.contains(t);
    }

    public HashSet<String> getPureMethods() {
        return pure_methods;
    }

    public HashSet<String> getAndroidEntryPointMethods() {
        return androidEntryPointMethods;
    }

    public HashSet<String> getAndroidCallBackInterfaces() {
        return androidCallBackInterfaces;
    }

    //=============================================


    public void resetLoadingInProgressClassesSet(){
        loadingSmaliClassInProgress.clear();
    }

    private void doLazyClinit(){
//        System.out.println("Starting doLazyClinit() : " + Utils.getNowDateTimeString());
        ArrayList<String> clsNames = (ArrayList<String>) toInvokeClinitLazily.clone();
        toInvokeClinitLazily.clear();
        for(String s: clsNames) {
            SmaliClazz clazz = (SmaliClazz) this.getClazz(s);
            clazz.invokeCLinit(this);
        }
//        System.out.println("Starting doLazyClinit() : " + Utils.getNowDateTimeString());
    }


}
