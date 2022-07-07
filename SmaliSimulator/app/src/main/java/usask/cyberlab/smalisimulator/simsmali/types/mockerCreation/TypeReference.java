package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;

import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.classesInConstructorDeclarationProcess;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.instrumentedTypesMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.previouslyCreatedInstrumentedType;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.shouldThrowInnerCyclicClassException;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.typeDescriptionWithDeclaredConstructorMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.unloadedClassesMap;

public class TypeReference {

    public enum RefState {
        Class,
        InstrumentedType,
        DynamicType_Unloaded_TypeDescription,
        TypeDefinition,
    }

    private RefState refState;
    private Class cls;
    private InstrumentedType instrumentedType;
    private TypeDescription typeDescription;
    private TypeDefinition typeDefinition;


    private TypeReference(RefState refState, Object value) {
        this.refState = refState;
        if (refState == RefState.Class) this.cls = (Class) value;
        else if (refState == RefState.InstrumentedType)
            this.instrumentedType = (InstrumentedType) value;
        else if (refState == RefState.DynamicType_Unloaded_TypeDescription)
            this.typeDescription = (TypeDescription) value;
        else if (refState == RefState.TypeDefinition) this.typeDefinition = (TypeDefinition) value;
        else throw new IllegalStateException();
    }

    public RefState getRefState() {
        return refState;
    }

    public Class getCls() {
        return cls;
    }

    public InstrumentedType getInstrumentedType() {
        return instrumentedType;
    }

    public TypeDescription getTypeDescription() {
        return typeDescription;
    }

    public TypeDefinition getTypeDefinition() {
        return typeDefinition;
    }

    //=========================================

    /**
     * This method returns and Instance of TypeReference pointing to the
     * proper object which needs to be used by byte-buddy when declaring
     * the type of parent class,constructor arguments, fields, method arguments, etc.
     * This is because in byte-buddy in case of cyclic types sometimes we need to use
     * TypeDefinition, TypeDescription or InstrumentedType instead of Class object.
     * For classes that don't have any cyclic dependency we can just load them into system
     * and return their Class object.
     *
     *
     * @param toLoadtype
     * @param wrappingType
     * @param loader
     * @return
     */
    public static TypeReference resolveType(@NotNull String toLoadtype, String wrappingType, ClazzLoader loader) {
        return doResolveType(toLoadtype, wrappingType, loader, false);
    }

    public static TypeReference resolveParentClassType(@NotNull String toLoadtype, String wrappingType, ClazzLoader loader){
        return doResolveType(toLoadtype, wrappingType, loader, true);
    }

    private static TypeReference doResolveType(@NotNull String toLoadtype, String wrappingType, ClazzLoader loader, boolean loadingParentClass){
        // if the type we want to load is an array, we will load it
        // using resolveArrayType method, which internally calls this method
        // to load the array base type
        if(toLoadtype.startsWith("[")) return resolveArrayType(toLoadtype,wrappingType, loader);

        // self referencing type.
        if (toLoadtype.equals(wrappingType)) {
            TypeDefinition td = TypeDescription.Generic.Builder.rawType(TargetType.class).build();
            return new TypeReference(RefState.TypeDefinition, td);
        }
        // referencing a inner cyclic type that has been built before
        else if (unloadedClassesMap.containsKey(toLoadtype)) {
            TypeDescription td = Objects.requireNonNull(unloadedClassesMap.get(toLoadtype)).getTypeDescription();
            // if the wrapper class is also a inner cyclic class we mark it
            addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
            return new TypeReference(RefState.DynamicType_Unloaded_TypeDescription, td);
        }
        // referencing a inner cyclic type that is in loading process
        // but has not been built yet
        else if (instrumentedTypesMap.containsKey(toLoadtype)) {
            InstrumentedType value = instrumentedTypesMap.get(toLoadtype);
            // mark wrapper class as inner cyclic class
            // unless the InstrumentedType has been created
            // outside of MockCreation process to prevent:
            // "cyclic inheritance dependent on unfinished constructors"
            if(!previouslyCreatedInstrumentedType.contains(toLoadtype)) {
                addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
            }
            return new TypeReference(RefState.InstrumentedType, value);
        }
        // the type we need has not started being loaded into system
        // so can use ClazzLoader.getClazz(type) to start loading it.
        else {
            // The following if block is checking a special case in which
            // we should not start loading the queried type and we need to
            // create an Instrumented type for it ourselves here.
            // This case happens due to a cyclic with inheritance situation which
            // a class let call "C" needs to be loaded where "C" extends another class "P"
            // which "P" is in loading process and has not finished declaring it's constructors.
            boolean b = false;
            String parentTypeWithUnfinishedCtors = null;
            if(!loadingParentClass){
                parentTypeWithUnfinishedCtors = hasIndirectDependencyOnTypeWithUnfinishedCtors(toLoadtype, loader);
                if(parentTypeWithUnfinishedCtors != null){
                    b = true;
                }
            }
            if(b){
                String javaStyleName = toLoadtype.substring(1)
                        .replace("/", ".")
                        .replace(";", "");
                InstrumentedType newInstrumentedType = InstrumentedType.Default.of(javaStyleName,
                        TypeDescription.Generic.Builder.rawType(Object.class).build(), Modifier.PUBLIC);

                // if we got to the problematic type during the loading of parent class
                // of the typeWithUnfinishedCtors we have to mark it as innerCyclicClass
                if(!classesInConstructorDeclarationProcess.contains(wrappingType)) {
                    shouldThrowInnerCyclicClassException.add(wrappingType);
                }
                // Also if it is happening during the constructor creation, we have to be careful
                // to not mark the outerCyclic class as innerCyclic
                else {
                    addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
                }

                instrumentedTypesMap.put(toLoadtype, newInstrumentedType);

                previouslyCreatedInstrumentedType.add(toLoadtype);

                if(classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.containsKey(parentTypeWithUnfinishedCtors)){
                    HashSet<String> h = classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.get(parentTypeWithUnfinishedCtors);
                    h.add(toLoadtype);
                }
                else {
                    HashSet<String> h = new HashSet<>();
                    h.add(toLoadtype);
                    classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.put(parentTypeWithUnfinishedCtors, h);
                }

                return new TypeReference(RefState.InstrumentedType, newInstrumentedType);
            }
            // type does not have the special case mentioned above
            // we can start loading it (or accessing it has been loaded)
            // with the ClazzLoader.getClazz() method
            else {
                try {
                    Class cls = loader.getClazz(toLoadtype).getMirroringClass();
                    return new TypeReference(RefState.Class, cls);
                } catch (InnerCyclicClassException e) {
                    DynamicType.Unloaded ud = unloadedClassesMap.get(toLoadtype);
                    if(ud == null){
                        throw new NullPointerException(toLoadtype + " was not inside unloadedClassesMap but InnerCyclicClassException was thrown!!");
                    }
                    TypeDescription td = ud.getTypeDescription();
                    addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
                    return new TypeReference(RefState.DynamicType_Unloaded_TypeDescription, td);
                }
            }
        }
    }


    public static TypeReference resolvePreviouslyCreatedInstrumentedType(@NotNull String type, String wrappingType, ClazzLoader loader) {
        if(type.startsWith("[")) throw new IllegalStateException();
        if(wrappingType.startsWith("[")) throw new IllegalStateException();

        if(type.equals(wrappingType) ||
                unloadedClassesMap.containsKey(type) ||
                !instrumentedTypesMap.containsKey(type)
        ){
            throw new IllegalStateException();
        }

        try {
            Class cls = loader.getClazz(type).getMirroringClass();
            return new TypeReference(RefState.Class, cls);
        } catch (InnerCyclicClassException e) {
            TypeDescription td;
            td = Objects.requireNonNull(unloadedClassesMap.get(type)).getTypeDescription();
//            addTypeToInnerCyclicClassExceptionSetIfNecessary(wrappingType, loader);
            return new TypeReference(RefState.DynamicType_Unloaded_TypeDescription, td);
        }

    }

    private static TypeReference resolveArrayType(@NotNull String arrayType,String wrappingType,ClazzLoader loader){
        int arity = SimulationUtils.countChar(arrayType, '[');
        String baseType = arrayType.replace("[","");
        TypeReference baseTypeReference = resolveType(baseType, wrappingType, loader);

        if(baseTypeReference.getRefState().equals(RefState.Class)){
            Class cls = loader.getClazz(arrayType).getMirroringClass();
            return new TypeReference(RefState.Class, cls);
        }
        else if(baseTypeReference.getRefState().equals(RefState.TypeDefinition)){
            InstrumentedType instrumentedType = instrumentedTypesMap.get(baseType);
            if(instrumentedType == null) throw new IllegalStateException();
            TypeDefinition array_TypeDefinition = TypeDescription.Generic.Builder.rawType(instrumentedType).asArray(arity).build();
            return new TypeReference(RefState.TypeDefinition, array_TypeDefinition);
        }
        else if(baseTypeReference.getRefState().equals(RefState.InstrumentedType)){
            InstrumentedType instrumentedType = baseTypeReference.getInstrumentedType();
            TypeDefinition array_TypeDefinition = TypeDescription.Generic.Builder.rawType(instrumentedType).asArray(arity).build();
            return new TypeReference(RefState.TypeDefinition, array_TypeDefinition);
        }
        else if(baseTypeReference.getRefState().equals(RefState.DynamicType_Unloaded_TypeDescription)){
            TypeDescription typeDescription = baseTypeReference.getTypeDescription();
            TypeDefinition array_TypeDefinition = TypeDescription.Generic.Builder.rawType(typeDescription).asArray(arity).build();
            return new TypeReference(RefState.TypeDefinition, array_TypeDefinition);
        }
        else {
            throw new IllegalStateException();
        }
    }

    /**
     * This method checks if the wrappingType is an InnerCyclicClass or not.
     * When the wrapping type uses an InstrumentedType or TypeDescription of an UnloadedType
     * it is a sign InnerCyclicClass.
     *
     * @param wrappingType
     * @param loader
     */
    private static void addTypeToInnerCyclicClassExceptionSetIfNecessary(String wrappingType, ClazzLoader loader){
        if(wrappingType == null || "".equals(wrappingType))return;
        if (instrumentedTypesMap.size() <= 1) return;

        HashSet<String> h = classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType.get(wrappingType);

        // get all types that are in process of loading
        for (String unfinishedType : instrumentedTypesMap.keySet()) {
            // ignore the InstrumentedType that was created
            // for the wrappingType itself
            if (unfinishedType.equals(wrappingType)) continue;

            // if the type waiting for us is a previously created InstrumentedType
            // we ignore it since even though it will pass the next checks but
            // actually we are loading it and not the other way around
            if(h != null && h.contains(unfinishedType)) continue;

            // check if any unfinishedType has indirect dependency
            // on the wrappingType meaning there is class that
            // is waiting for us load
            SmaliClass unfinishedTypeSmaliClass = loader.getSmaliClass(unfinishedType);
            if (unfinishedTypeSmaliClass.hasIndirectDependencyOnType(wrappingType, loader)) {
                shouldThrowInnerCyclicClassException.add(wrappingType);
                break;
            }
        }

    }

    /**
     * This method checks if by starting to load @toLoadType would cause
     * loading of class be started what extends from a class that has not finished
     * declaring it's constructors
     *
     * @param toLoadType
     * @param clazzLoader
     * @return returns the parent type with unfinished constructors
     */
    static String hasIndirectDependencyOnTypeWithUnfinishedCtors(String toLoadType,
                                                                 final ClazzLoader clazzLoader){
        if(toLoadType.length() == 1) return null;
        if(instrumentedTypesMap.containsKey(toLoadType) ||
                unloadedClassesMap.containsKey(toLoadType)) return null;

        // if toLoadType is array type, we should use the base type
        if(toLoadType.startsWith("[")){
            toLoadType = toLoadType.replace("[","");
        }
        // get the list of classes that are in loading process
        // but have not finished the declaration of their constructors.
        Set<String> s1 = new HashSet<>(instrumentedTypesMap.keySet());
        Set<String> s2 = new HashSet<>(typeDescriptionWithDeclaredConstructorMap.keySet());
        s1.removeAll(s2);

        ArrayList<String> possibleResults = new ArrayList<>();

        // for each type that has not finished declaring it's constructors
        // we check if toLoadType is dependent on it
        for(String typeWithUnfinishedCtors: s1){
            SmaliClass unloadedParentClass = clazzLoader.getSmaliClass(typeWithUnfinishedCtors);
            if(willCreateCyclicDependencyWithChildType(toLoadType, unloadedParentClass, clazzLoader)){
                possibleResults.add(typeWithUnfinishedCtors);
            }
        }

        if(possibleResults.size() == 0){
            return null;
        }
        else if(possibleResults.size() ==1){
            return possibleResults.get(0);
        }
        else{
            Comparator<String> comparator = new Comparator<String>() {
                @Override
                public int compare(String type1, String type2) {
                    SmaliClass sc1 = clazzLoader.getSmaliClass(type1);
                    SmaliClass sc2 = clazzLoader.getSmaliClass(type2);
                    if(sc1.extendsFrom(type2, clazzLoader)) return -1;
                    else if(sc2.extendsFrom(type1, clazzLoader)) return 1;
                    return 0;
                }
            };
            possibleResults.sort(comparator);
            return possibleResults.get(0);
        }

    }

    private static boolean willCreateCyclicDependencyWithChildType(String toLoadType,
                                                                   SmaliClass unloadedParentClass,
                                                                   ClazzLoader loader){

        boolean result = false;
        String unloadedParentClassPath = unloadedParentClass.getClassPath();
        if(toLoadType.startsWith("[")){
            toLoadType = toLoadType.replace("[","");
        }

        // check if toLoadType will cause a new mock creation
        // that will need to load a type extending from unloadedParentClass
        // which has not finished declaring it's constructors
        if(!instrumentedTypesMap.containsKey(toLoadType) &&
                !MockCreator.unloadedClassesMap.containsKey(toLoadType)) {

            if (!toLoadType.equals(unloadedParentClassPath) // not self referencing
                    && !SimulationUtils.isJavaOrAndroidExistingClass(toLoadType) // and is user defined code
            ) {
                // get the smali class of a type we want to check
                SmaliClass toLoadSmaliClass = loader.getSmaliClass(toLoadType);
                // first we want to check if the type we want to load has any cyclic issues at first
                // remember that loading a child type of a type that has not finished declaring
                // it's constructors is a also counts as cyclic types
                //TODO is b1 just extra?  meaning when b2 is true b1 is always true??
                boolean b1 = toLoadSmaliClass.hasIndirectDependencyOnType(unloadedParentClassPath, loader);
                if (b1) {
                    // then we check if the type we want to load extends has indirect dependency
                    // on a type that extends from containing class
                    boolean b2 = toLoadSmaliClass.hasIndirectDependencyOnTypeExtendingFrom(unloadedParentClass, loader);
                    if (b2) result = true;
                }
            }
        }
        return result;
    }



}
