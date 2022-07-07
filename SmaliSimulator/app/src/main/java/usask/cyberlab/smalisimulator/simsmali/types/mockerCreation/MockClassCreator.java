package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockClassesMap;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.instrumentedTypesMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.typeDescriptionWithDeclaredConstructorMap;


@SuppressWarnings("rawtypes")
public class MockClassCreator {

//    private static Object debug1_instrumentedTypesMap = instrumentedTypesMap;
//    private static Object debug2_unloadedClassesMap = unloadedClassesMap;
//    private static Object debug3_shouldThrowInnerCyclicClassException = shouldThrowInnerCyclicClassException;
//    private static Object debug4_classesInConstructorDeclarationProcess = classesInConstructorDeclarationProcess;
//    private static Object debug5_typeDescriptionWithDeclaredConstructorMap = typeDescriptionWithDeclaredConstructorMap;
//    private static Object debug6_classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType = classesNeedingResolvingTheirPreviouslyCreatedInstrumentedType;
//    private static Object debug7_previouslyCreatedInstrumentedType = previouslyCreatedInstrumentedType;

    static Class createMockerClass(SmaliClazz smaliClazz) {
        ByteBuddy byteBuddy = new ByteBuddy().with(TypeValidation.DISABLED);
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        String thisClassPath = smaliClazz.getClassPath();
        String javaStyleName = thisClassPath.substring(1)
                .replace("/", ".")
                .replace(";", "");

        if(javaStyleName.contains("-")){
            javaStyleName = javaStyleName.replace("-", "_");
            // in make smaliStyleClass path we want to reverse java name to smali name
            // since we have modified java name here, we have to keep this map of names
            // for proper renaming
            ClazzLoader.classesWithReplacedIllegalNames.put(javaStyleName, smaliClazz.getClassPath());
        }

        String parentClassPath = smaliClazz.getParentClassPath();

        // We need to create an InstrumentedType object for
        // any type before making it. This is necessary in handling cyclic types.
        // However, there are some special cases that cause the InstrumentedType of a class
        // be created and placed into the "instrumentedTypesMap" before it's corresponding
        // createMockerClass() is called.
        // In this cases we want to use the previously created InstrumentedType
        // and not recreate another InstrumentedType. This is the reason of the if block.
        if(!instrumentedTypesMap.containsKey(thisClassPath)) {
            InstrumentedType instrumentedType = InstrumentedType.Default.of(javaStyleName,
                    TypeDescription.Generic.Builder.rawType(Object.class).build(), Modifier.PUBLIC);
            instrumentedTypesMap.put(thisClassPath, instrumentedType);
        }

        final TypeReference parentTypeRef;

//        // if the parent class is one of the Android classes that we have manually mocked,
//        // we swap the parent Class object to the corresponding mocked Class object
//        if (MockClassesMap.mockMap.containsKey(parentClassPath)) {
//            String mockClassPath = MockClassesMap.mockMap.get(parentClassPath);
//            parentTypeRef = TypeReference.resolveType(mockClassPath, null, clazzLoader);
//        }

        // otherwise we just try to resolve the parents TypeReference.
        // in cases with types cyclic dependency with each other which also have
        // inheritance relationship, the parent type can be loading process and
        // thus TypeReference would be in InstrumentedType or TypeDescription state
//        else {
            parentTypeRef = TypeReference.resolveParentClassType(parentClassPath, thisClassPath, clazzLoader);
//        }

        // initializing the Class that needs to be built
        DynamicType.Builder builder;
        if(parentTypeRef.getRefState().equals(TypeReference.RefState.Class)){
          builder = byteBuddy.subclass(parentTypeRef.getCls(), ConstructorStrategy.Default.NO_CONSTRUCTORS).name(javaStyleName);
        }
        else if(parentTypeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
            TypeDescription p = typeDescriptionWithDeclaredConstructorMap.get(parentClassPath);
            if(p==null){
                throw new IllegalStateException("TypeDescription of " + parentClassPath + " is not present in typeDescriptionWithDeclaredConstructorMap");
            }
            builder = byteBuddy.subclass(p, ConstructorStrategy.Default.NO_CONSTRUCTORS).name(javaStyleName);
        }
        else if(parentTypeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
            builder = byteBuddy.subclass(parentTypeRef.getTypeDescription(), ConstructorStrategy.Default.NO_CONSTRUCTORS).name(javaStyleName);
        }
        else {
            throw new IllegalStateException();
        }

        //adding class modifiers
        int classModifier = Modifier.PUBLIC;
        if(smaliClazz.isAbstract()){
            classModifier = classModifier | Modifier.ABSTRACT;
        }
        builder = builder.modifiers(classModifier);



        // The first thing we need to do is add the constructors.
        // This stage of making a class is both important and bit different from other parts.
        // The reason is if there are any other class which extends the type we are loading
        // it needs to know about the constructors it needs to call since byte-buddy checks
        // if constructor call chain is valid. How this issue is handled is
        // explained in the comments of addClassConstructors and it's nested methods.
        builder = MockClassConstructorCreator.addClassConstructors(smaliClazz, builder, parentTypeRef);

        // load types that could not have been loaded since they needed this class
        // to finish declaring it's constructors
        MockCreator.resolveClassesWithPreviouslyCreatedInstrumentedTypeIfNeeded(smaliClazz.getClassPath(), clazzLoader);

        //  we declare the interfaces that our class has implemented
        TypeReference[] interfacesTypes = MockCreator.getInterfacesTypes(smaliClazz);
        for(TypeReference tr: interfacesTypes){
            if(tr.getRefState().equals(TypeReference.RefState.Class)){
                builder = builder.implement(tr.getCls());
            }
            else if(tr.getRefState().equals(TypeReference.RefState.TypeDefinition)){
                builder = builder.implement(tr.getTypeDefinition());
            }
            else if(tr.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                builder = builder.implement(tr.getInstrumentedType());
            }
            else if(tr.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                builder = builder.implement(tr.getTypeDescription());
            }
            else throw new IllegalStateException();
        }

        //adding the fields in the smaliClazz
        builder = addClassFields(smaliClazz, builder);

        // adding the methods in the SmaliClazz
        builder = addClassMethods(smaliClazz, builder);

        typeDescriptionWithDeclaredConstructorMap.remove(thisClassPath);
        Class res =  MockCreator.makeAndLoadIntoSystem(smaliClazz, clazzLoader, builder);
        return res;
    }


    static DynamicType.Builder addClassFields(SmaliClazz smaliClazz, DynamicType.Builder builder) {
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        SmaliClass smaliClass = smaliClazz.getSmaliClass();

        // defining the fields in the class
        for (SmaliField sf : smaliClass.getAllFields()) {
            String fieldName = sf.getName();
            String fieldType = sf.getType();

            int modifiers = sf.getAccessModifier();
            if(sf.isStatic()) modifiers = modifiers | Modifier.STATIC;

            TypeReference typeRef = TypeReference.resolveType(fieldType, smaliClass.getClassPath(), clazzLoader);
            Object initialFieldValue = sf.getInitialValue();
            // only String and primitive types can have initial values
            // in field definition, other are initialized in <init> or <clinit>
            if (initialFieldValue != null) {
                Class fieldTypeClass = typeRef.getCls();
                DynamicType.Builder.FieldDefinition.Optional.Valuable fieldDef = builder.defineField(fieldName, fieldTypeClass, modifiers);
                if (initialFieldValue instanceof Integer) {
                    Integer i = (Integer) initialFieldValue;
                    builder = fieldDef.value(i);
                } else if (initialFieldValue instanceof Float) {
                    Float f = (Float) initialFieldValue;
                    builder = fieldDef.value(f);
                } else if (initialFieldValue instanceof Long) {
                    Long l = (Long) initialFieldValue;
                    builder = fieldDef.value(l);
                } else if (initialFieldValue instanceof Double) {
                    Double d = (Double) initialFieldValue;
                    builder = fieldDef.value(d);
                } else if (initialFieldValue instanceof Boolean) {
                    Boolean z = (Boolean) initialFieldValue;
                    builder = fieldDef.value(z);
                } else if (initialFieldValue instanceof Byte) {
                    Byte b = (Byte) initialFieldValue;
                    builder = fieldDef.value(b);
                } else if (initialFieldValue instanceof Short) {
                    Short s = (Short) initialFieldValue;
                    builder = fieldDef.value(s);
                } else if (initialFieldValue instanceof Character) {
                    Character c = (Character) initialFieldValue;
                    builder = fieldDef.value(c);
                } else if(initialFieldValue instanceof String){
                    String strValue = (String) initialFieldValue;
                    builder = fieldDef.value(strValue);
                }
                else {
                    throw new IllegalStateException(initialFieldValue.toString());
                }

            } else {
                // normal types
                if (typeRef.getRefState().equals(TypeReference.RefState.Class)) {
                    builder = builder.defineField(fieldName, typeRef.getCls(), modifiers);
                }
                // self types
                else if (typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)) {
                    builder = builder.defineField(fieldName, typeRef.getTypeDefinition(), modifiers);
                }
                // inner cyclic types
                else if (typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)) {
                    builder = builder.defineField(fieldName, typeRef.getInstrumentedType(), modifiers);
                }
                // outer cyclic types
                else if (typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)) {
                    builder = builder.defineField(fieldName, typeRef.getTypeDescription(), modifiers);
                }
                // this condition should never be reached
                else {
                    throw new IllegalStateException();
                }
            }
        }
        return builder;
    }


    static DynamicType.Builder addClassMethods(SmaliClazz smaliClazz, DynamicType.Builder builder){
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();

        //TODO better explain this
        List<SmaliMethod> methods = smaliClazz.getSmaliClass().getAllMethods();
        List<SmaliMethod> sortedMethod = new ArrayList<>();
        for(SmaliMethod m: methods){
            if(m.isBridge()) sortedMethod.add(m);
        }

        for (SmaliMethod m:methods){
            if(!m.isBridge()) sortedMethod.add(m);
        }
        methods = sortedMethod;

        for (SmaliMethod sm : methods) {
            String methodSignature = sm.getClassMethodSignature();
            // if the method is a not constructor and is a normal method
            if (!methodSignature.contains("<init>") && !methodSignature.contains("<clinit>")) {
                builder = addClassMethod(sm, builder, clazzLoader);
            }
        }
        return builder;
    }


    static DynamicType.Builder addClassMethod(SmaliMethod sm,
                                              DynamicType.Builder builder,
                                              ClazzLoader clazzLoader) {
        String methodSignature = sm.getClassMethodSignature();
        int methodNameStart = methodSignature.indexOf("->") + 2;
        int methodNameEnd = methodSignature.indexOf('(');
        int methodArgsEnd = methodSignature.indexOf(')');
        int methodRetTypeEnd = methodSignature.length();
        String retTypeStr = methodSignature.substring(methodArgsEnd + 1, methodRetTypeEnd);
        String methodName = methodSignature.substring(methodNameStart, methodNameEnd);
        String containingClassType = sm.getContainingClass().getClassPath();


        TypeReference typeRef = TypeReference.resolveType(retTypeStr, containingClassType, clazzLoader);

        int modifiers = Modifier.PUBLIC;
        if (sm.isStatic()) modifiers = modifiers | Modifier.STATIC;
        if (sm.isAbstract()) modifiers = modifiers | Modifier.ABSTRACT;

        DynamicType.Builder.MethodDefinition.ParameterDefinition.Simple methodBuilder;
        // normal class
        if (typeRef.getRefState().equals(TypeReference.RefState.Class)) {
            methodBuilder = builder.defineMethod(methodName, typeRef.getCls(), modifiers);
        }
        // self referencing
        else if (typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)) {
            methodBuilder = builder.defineMethod(methodName, typeRef.getTypeDefinition(), modifiers);
        }
        //inner cyclic type
        else if (typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)) {
            methodBuilder = builder.defineMethod(methodName, typeRef.getInstrumentedType(), modifiers);
        }
        //outer or middle cyclic type
        else if (typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)) {
            methodBuilder = builder.defineMethod(methodName, typeRef.getTypeDescription(), modifiers);
        } else {
            throw new IllegalStateException();
        }

        Object[] argTypes = MockCreator.getMethodArgTypes(sm, clazzLoader);
        for (Object argType : argTypes) {
            if (argType instanceof Type) {
                methodBuilder = methodBuilder.withParameter((Type) argType);
            } else if (argType instanceof TypeDefinition) {
                methodBuilder = methodBuilder.withParameter((TypeDefinition) argType);
            } else {
                throw new IllegalStateException();
            }
        }

        if(sm.isAbstract()){
            builder = methodBuilder.withoutCode();
        }
        else {
            builder = methodBuilder.intercept(to(new MethodInterceptor(sm, clazzLoader)));
        }
        return builder;
    }


}
