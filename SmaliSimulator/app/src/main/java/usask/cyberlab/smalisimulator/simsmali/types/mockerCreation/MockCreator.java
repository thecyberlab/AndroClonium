package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import net.bytebuddy.android.AndroidClassLoadingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import usask.cyberlab.smalisimulator.Utils;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

import static net.bytebuddy.implementation.MethodDelegation.to;

@SuppressWarnings("rawtypes")
public class MockCreator {

    // In this Map we keep track of InstrumentedType of each class,Enum or Interface we try to create.
    // This is because in case of cyclic types, at some point we would need to use InstrumentedType
    // of a type instead of loading it.
    static HashMap<String, InstrumentedType> instrumentedTypesMap = new HashMap();

    // When classes are cyclic, both the first Class we start to load and all
    // next cyclic classes need to be built differently than normal non-cyclic classes.
    // The outer cyclic class is also different since it needs to know
    // to include already build but not loaded classes in it's build time.
    // The inner cyclic types are different since they need to use
    // a 'InstrumentedType' or a 'TypeDescription' to declare types of things in byte-buddy.
    // This HashSet keep track of classes that are an inner cyclic class because they should
    // throw an InnerCyclicClassException instead of loading themselves into system after being made.
    static HashSet<String> shouldThrowInnerCyclicClassException = new HashSet<>();

    // In this map we keep track of inner cyclic types that have been built but not loaded into system.
    // We save the result of just making a type (DynamicType.Unloaded) in a map
    // since the outer cyclic types need to use it at build and load time.
    static HashMap<String, DynamicType.Unloaded> unloadedClassesMap = new HashMap();


    static HashSet<String> classesInConstructorDeclarationProcess = new HashSet<>();

    // When declaring constructors of a type, byte-buddy has to know that any constructor
    // properly invokes one of the constructors of its parent type or another constructor in the type itself.
    // This means if the parent class is still in loading process, it should have
    // finished declaring it's constructors.
    // So for classes that have finished their constructor declaration,
    // we save their builder.toTypeDescription() in this map if any other class
    // needs to extend them.
    static HashMap<String, TypeDescription> typeDescriptionWithDeclaredConstructorMap = new HashMap<>();

    // In some case of cyclic types with inheritance relationship,
    // during the parent class type resolving or in declaration of constructors of a type,
    // another type may be needed to be loaded that extends the type we are making.
    //In these scenarios we should not load those child types and we create a InstrumentedType
    // for them. After the necessary constructors have been declared, we can start to load them.
    // In this map we keep track of the set of type that should be loaded,
    // after the needed constructors of a type have been declared.
    static HashMap<String,HashSet<String>> classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType = new HashMap<>();


    static HashSet<String> previouslyCreatedInstrumentedType = new HashSet<>();



    // this is the method that starts to create a type in this system
    // corresponding to a type represented in a Smali file.
    public static Class<?> createType(SmaliClazz smaliClazz) {
        System.out.println("Starting loading class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());
        try {
            if (smaliClazz.isInterfaceClass()) {
                Class c = MockInterfaceCreator.createMockerInterface(smaliClazz);
                System.out.println("Finished loading class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());
                return c;
            }
            else if (smaliClazz.isEnumClass()) {
                Class c =  MockEnumCreator.createMockerEnum(smaliClazz);
                System.out.println("Finished loading class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());
                return c;
            }
            else {
                Class c =  MockClassCreator.createMockerClass(smaliClazz);
                System.out.println("Finished loading class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());
                return c;
            }
        } catch (InnerCyclicClassException e) {
            throw e;
        } catch (Exception e) {
            instrumentedTypesMap.clear();
            shouldThrowInnerCyclicClassException.clear();
            unloadedClassesMap.clear();
            smaliClazz.getClazzLoader().toInvokeClinitLazily.clear();
            smaliClazz.getClazzLoader().resetLoadingInProgressClassesSet();
            throw new IllegalStateException("error loading class " + smaliClazz.getClassPath(),e);
        }
    }

    // each argument holder name has three parts
    // 1- is the name of a class that has the constructor that needs this argument holders to
    // call it's nested constructor call
    // 2- is the concatenated string of the constructor call, since all constructors all
    // called <init>, the argument types string is unique per each constructor
    // 3- the argument position which corresponds to number of types in nested constructor call
    // not the constructor referenced by ctorSmaliMethod.
    public static String getConstructorArgHolderName(SmaliMethod ctorSmaliMethod, int argPos) {
        String normalizedClassPath = ctorSmaliMethod.getContainingClass().getClassPath();
        normalizedClassPath = normalizedClassPath.substring(1, normalizedClassPath.length() - 1);
        normalizedClassPath = normalizedClassPath.replace("/", "_");
        String[] argTypes = ctorSmaliMethod.getArgumentTypes();
        String argString = "V";
        for (int i = 0; i < argTypes.length; i++) {
            String argType = argTypes[i];
            String cleanedArgType = argType.replace(";", "")
                    .replace("/", "_")
                    .replace("[", "$");
            argString = argString + cleanedArgType;
        }
        String tmpNamePrefix = normalizedClassPath + "_" + argString;
        return "_" + tmpNamePrefix + "_" + argPos;
    }

    static TypeReference[] getInterfacesTypes(SmaliClazz smaliClazz) {
        List<String> interfaces = smaliClazz.getInterfaces();
        TypeReference[] res = new TypeReference[interfaces.size()];
        for (int i = 0; i < res.length; i++) {
            String ifaceType = interfaces.get(i);
            TypeReference typeRef = TypeReference.resolveType(ifaceType, smaliClazz.getClassPath(), smaliClazz.getClazzLoader());
            res[i] = typeRef;
        }
        return res;
    }

    // this method returns an array of Class, InstrumentedType, TypeDefinition or TypeDescription
    // which will be used in creating a method or using byte-buddy
    static Object[] getMethodArgTypes(SmaliMethod sm, ClazzLoader loader) {
        String[] argTypes = sm.getArgumentTypes();
        Object[] res = new Object[argTypes.length];
        SmaliClass containingSmaliClass = sm.getContainingClass();
        String containingClassType = containingSmaliClass.getClassPath();
        for (int i = 0; i < res.length; i++) {
            String argType = argTypes[i];
            TypeReference typeRef = TypeReference.resolveType(argType, containingClassType, loader);
            if(typeRef.getRefState().equals(TypeReference.RefState.Class)){
                res[i] = typeRef.getCls();
            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)){
                res[i] = typeRef.getTypeDefinition();
            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                res[i] = typeRef.getInstrumentedType();
            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                res[i] = typeRef.getTypeDescription();
            }
            else {
                throw new IllegalStateException();
            }
        }
        return res;
    }

    static Class makeAndLoadIntoSystem(SmaliClazz smaliClazz,
                                              ClazzLoader clazzLoader,
                                              DynamicType.Builder builder){
        // to keep track of all previously build classes we need to use the latest
        // java classLoader (not my ClazzLoader) for building new types which is
        // saved in the ClassLoader class
        ClassLoader classLoader = ClazzLoader.javaClassLoader;
        AndroidClassLoadingStrategy loadingStrategy = clazzLoader.getLoadingStrategy();

        // Now finally we can build our class and maybe load it into the system
        // but for loading it there are 3 scenario that can happen
        //1- normal class with out any cyclic dependency
        //2- inner cyclic classes
        //3- outer cyclic classes
        System.out.println("Started making class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());
        DynamicType.Unloaded unloadedType = builder.make();
        System.out.println("Finished making class : " + smaliClazz.getClassPath() + " " + Utils.getNowDateTimeString());


        // if this type is an innerCyclicClass we just need to save the unloadedType
        // since the outerCyclic class is going to use it first and then load the
        // inner cyclic classes too
        if (shouldThrowInnerCyclicClassException.contains(smaliClazz.getClassPath())) {
            shouldThrowInnerCyclicClassException.remove(smaliClazz.getClassPath());
            // we also remove InstrumentedType since outer class creations
            // should use unloadedType instead
            instrumentedTypesMap.remove(smaliClazz.getClassPath());
            unloadedClassesMap.put(smaliClazz.getClassPath(), unloadedType);
            throw new InnerCyclicClassException();
        }


        // if the class we are loading is outer cyclic class then unloadedClassesMap should not be empty
        // and there are unloadedTypes that need to be included in the outer class loading process
        HashSet<String> namesOfInnerLoadedClasses = new HashSet<>();
        if (unloadedClassesMap.size() > 0) {
            ArrayList<String> keys = new ArrayList<>(unloadedClassesMap.keySet());
            for(String k:keys){
                SmaliClass sc= smaliClazz.getSmaliClass();
                if(sc.hasIndirectDependencyOnType(k, clazzLoader)){
                    DynamicType.Unloaded innerCyclicUnloadType = unloadedClassesMap.get(k);
                    unloadedType = unloadedType.include(innerCyclicUnloadType);
                    unloadedClassesMap.remove(k);
                    namesOfInnerLoadedClasses.add(innerCyclicUnloadType.getTypeDescription().getName());
                }
            }
        }


        // loading the class in the system
        DynamicType.Loaded loadedType = unloadedType.load(classLoader, loadingStrategy);
        Class<?> result = loadedType.getLoaded();
        ClazzLoader.javaClassLoader = result.getClassLoader();

        // if the class we loaded has been an outer class
        // we also need to put their Java Class object in my ClazzLoader
        // since by the loading the outer class we have suddenly loaded multiple classes
        // which we need to add to our ClazzLoader. Furthermore, these inner classes
        // also need to call <clinit> too. so we need to add them to toInvokeClinitLazily list
        if (namesOfInnerLoadedClasses.size() != 0) {
            for (String s : namesOfInnerLoadedClasses) {
                Class cls;
                try {
                    cls = result.getClassLoader().loadClass(s);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
                String classPath = clazzLoader.saveLoadedCyclicClass(cls);
                clazzLoader.toInvokeClinitLazily.add(classPath);
            }
        }


        // we remove the instrumentedType we added so we know that the class
        // has been loaded and subsequent refers to this class should be done
        // using the Class object
        instrumentedTypesMap.remove(smaliClazz.getClassPath());

        return result;
    }

    /**
     *
     *  This method starts the mocking classes that their InstrumentedType
     *  was declared inside TypeReference.resolveType()
     *
     *  This happens in a special case where a TypeReference refers to a class that
     *  has (direct or indirect) dependency on another class its parent class is in loading process
     *  and has not finished declaring it's constructors.
     *  In this cases TypeReference.resolveType() will create an InstrumentedType
     *  and save it instead of starting the class loading process.
     */
    static void resolveClassesWithPreviouslyCreatedInstrumentedTypeIfNeeded(String containingType,
                                                                                   ClazzLoader loader){
        if(classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.containsKey(containingType)){
            HashSet<String> unfinishedClasses = classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.remove(containingType);

            // here we want to empty the unfinishedClasses set before we start
            // the resolving of the types that were inside it, since in the
            // TypeReference.resolveType we have this check:
            // if(!previouslyCreatedInstrumentedType.contains(toLoadtype)) {
            //   addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
            //  }
            // in case during loading a type in this set we would refer to a type
            // that it would refer to another type in this set, we need to mark it
            // as innerCyclicClass
            HashSet<String> clone = (HashSet<String>) unfinishedClasses.clone();
            unfinishedClasses.clear();

            for(String needToLoadClass: clone){
                //TODO shouldn't we clear previouslyCreatedInstrumentedType set
                // before the forLoop like unfinishedClasses??
                previouslyCreatedInstrumentedType.remove(needToLoadClass);

                // after thinking about this, I think this should not ever happen but
                // in case if when resolving the previous iterations of this loop
                // has caused needToLoadClass to be created we ignore continue
                if(unloadedClassesMap.containsKey(needToLoadClass)) continue;

                shouldThrowInnerCyclicClassException.add(needToLoadClass);
                TypeReference.resolvePreviouslyCreatedInstrumentedType(needToLoadClass, containingType, loader);
            }
        }
    }


    // methods used in testing:
    ///============================================
//
//    // this method is used for testing only and should not be used in real program logic
//    public static HashMap<String, InstrumentedType> getInstrumentedTypesMap() {
//        return instrumentedTypesMap;
//    }
//
//    // this method is used for testing only and should not be used in real program logic
//    public static HashSet<String> getShouldThrowInnerCyclicClassException() {
//        return shouldThrowInnerCyclicClassException;
//    }
//
//
//    // this method is used for testing only and should not be used in real program logic
//    public static HashMap<String, DynamicType.Unloaded> getUnloadedClassesMap() {
//        return unloadedClassesMap;
//    }
//
//
//    public static HashMap<String, HashSet<String>> getClassesNeedingResolvingTheirPreviouslyCreatedInstrumentedType() {
//        return classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType;
//    }
//
//    public static HashMap<String, TypeDescription> getTypeDescriptionWithDeclaredConstructorMap() {
//        return typeDescriptionWithDeclaredConstructorMap;
//    }
//
//    public static HashSet<String> getPreviouslyCreatedInstrumentedType() {
//        return previouslyCreatedInstrumentedType;
//    }

    public static boolean areAllInternalLogicMapsAndSetsEmpty(){
        if(instrumentedTypesMap.size() > 0) return false;
        if(shouldThrowInnerCyclicClassException.size() > 0) return false;
        if(unloadedClassesMap.size() > 0) return false;
        if(classesInConstructorDeclarationProcess.size() > 0) return false;
        if(classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.size() > 0) return false;
        if(typeDescriptionWithDeclaredConstructorMap.size() > 0) return false;
        if(previouslyCreatedInstrumentedType.size() > 0) return false;
        return true;
    }
}
