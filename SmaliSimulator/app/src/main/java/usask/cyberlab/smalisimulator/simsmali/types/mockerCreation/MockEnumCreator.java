package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.instrumentedTypesMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.typeDescriptionWithDeclaredConstructorMap;

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

import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class MockEnumCreator {

    static Class<?> createMockerEnum(SmaliClazz smaliClazz) {
        String thisClassPath = smaliClazz.getClassPath();
        String parentClassPath = smaliClazz.getParentClassPath();
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();

        String thisJavaStyleName = thisClassPath.substring(1)
                .replace("/", ".").replace(";", "");

        if(thisJavaStyleName.contains("-")){
            thisJavaStyleName = thisJavaStyleName.replace("-", "_");
            // in make smaliStyleClass path we want to reverse java name to smali name
            // since we have modified java name here, we have to keep this map of names
            // for proper renaming
            ClazzLoader.classesWithReplacedIllegalNames.put(thisJavaStyleName, thisClassPath);
        }

        // we create a corresponding InstrumentedType object for all types
        // which will be used if necessary in case of cyclic types.
        // There is a special case for cyclic types with inheritance relationship
        // which parent type refers to child type in constructor. In this case we have to
        // instrumentedType during parent constructor creation so it already exists in instrumentedTypesMaps
        if(!instrumentedTypesMap.containsKey(thisClassPath)) {
            // we create a corresponding InstrumentedType object which will be used in case of
            // cyclic types. This value will be put in instrumentedTypesMaps in order for the
            // next recursive createMockerClass invocations can use it.
            InstrumentedType instrumentedType = InstrumentedType.Default.of(thisJavaStyleName,
                    TypeDescription.Generic.Builder.rawType(Enum.class).build(), Modifier.PUBLIC);
            instrumentedTypesMap.put(thisClassPath, instrumentedType);
        }


        int classModifiers = 0x00004000 | Modifier.PUBLIC;
        if(smaliClazz.isAbstract()) classModifiers = classModifiers | Modifier.ABSTRACT;

        TypeReference parentTypeRef = TypeReference.resolveParentClassType(parentClassPath, thisClassPath, clazzLoader);

        DynamicType.Builder builder;
        ByteBuddy bb = new ByteBuddy().with(TypeValidation.DISABLED);
        if(parentTypeRef.getRefState().equals(TypeReference.RefState.Class)){
           builder = bb.subclass(parentTypeRef.getCls() , ConstructorStrategy.Default.NO_CONSTRUCTORS);

        }
        else if(parentTypeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
            TypeDescription p = typeDescriptionWithDeclaredConstructorMap.get(parentClassPath);
            if(p==null){
                throw new IllegalStateException("TypeDescription of " + parentClassPath + " is not present in typeDescriptionWithDeclaredConstructorMap");
            }
            builder = bb.subclass(p , ConstructorStrategy.Default.NO_CONSTRUCTORS);
        }
        else if(parentTypeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
            builder = bb.subclass(parentTypeRef.getTypeDescription() , ConstructorStrategy.Default.NO_CONSTRUCTORS);
        }
        else {
            throw new IllegalStateException();
        }

        builder = builder.name(thisJavaStyleName)
                .modifiers(classModifiers);

        // Build enum constructors
        builder = MockClassConstructorCreator.addClassConstructors(smaliClazz, builder, parentTypeRef);

        // load types that could not have been loaded since they needed this class
        // to finish declaring it's constructors
        MockCreator.resolveClassesWithPreviouslyCreatedInstrumentedTypeIfNeeded(thisClassPath, clazzLoader);

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

        builder = defineEnumValueFields(smaliClazz, builder);

        // adding the methods in the SmaliClazz
        builder = addEnumMethods(smaliClazz, builder);

        typeDescriptionWithDeclaredConstructorMap.remove(thisClassPath);
        Class res =  MockCreator.makeAndLoadIntoSystem(smaliClazz, clazzLoader, builder);
        return res;
    }

    private static DynamicType.Builder defineEnumValueFields(SmaliClazz smaliClazz, DynamicType.Builder builder){
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        SmaliClass smaliClass = smaliClazz.getSmaliClass();

        // defining the fields in the class
        for (SmaliField sf : smaliClass.getAllFields()) {
            String fieldName = sf.getName();
            String fieldType = sf.getType();
            int modifiers = sf.getAccessModifier();
            if(sf.isStatic()) modifiers = modifiers | Modifier.STATIC;
            if(sf.isEnumField()) modifiers = modifiers | 0x00004000;

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
                } else {
                    throw new IllegalStateException(initialFieldValue.toString());
                }

            }
            else {
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

    static DynamicType.Builder addEnumMethods(SmaliClazz smaliClazz, DynamicType.Builder builder){
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();

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
            if (!methodSignature.contains("<init>") && !methodSignature.contains("<clinit>")) {
                builder = addEnumMethod(sm, builder, clazzLoader);
            }
        }
        return builder;
    }

    static DynamicType.Builder addEnumMethod(SmaliMethod sm,
                                              DynamicType.Builder builder,
                                              ClazzLoader clazzLoader) {
        String methodSignature = sm.getClassMethodSignature();
        int methodNameStart = methodSignature.indexOf("->") + 2;
        int methodNameEnd = methodSignature.indexOf('(');
        int methodArgsEnd = methodSignature.indexOf(')');
        int methodRetTypeEnd = methodSignature.length();
        String retTypeStr = methodSignature.substring(methodArgsEnd + 1, methodRetTypeEnd);
        String methodName = methodSignature.substring(methodNameStart, methodNameEnd);
        methodName = methodName.replace("<", "_").replace(">", "_");
        String containingClassType = sm.getContainingClass().getClassPath();


        TypeReference typeRef = TypeReference.resolveType(retTypeStr, containingClassType, clazzLoader);

        int modifiers = Modifier.PUBLIC;
        if (sm.isStatic()) modifiers = modifiers | Modifier.STATIC;
        if (sm.isAbstract()) {
            modifiers = modifiers | Modifier.ABSTRACT;
        }

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
