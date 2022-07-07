package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.instrumentedTypesMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.makeAndLoadIntoSystem;

@SuppressWarnings("rawtypes")
public class MockInterfaceCreator {

    static Class<?> createMockerInterface(SmaliClazz smaliClazz) {
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        ByteBuddy byteBuddy = new ByteBuddy()
                // this is needed because we want to be able to add static methods and static fields to interfaces
                // which was only added in new java versions but since don't know about android it will raise an error in those cases
                .with(TypeValidation.DISABLED);

        String javaStyleName = smaliClazz.getClassPath().substring(1)
                .replace("/", ".").replace(";", "");

        if(javaStyleName.contains("-")){
            javaStyleName = javaStyleName.replace("-", "_");
            // in make smaliStyleClass path we want to reverse java name to smali name
            // since we have modified java name here, we have to keep this map of names
            // for proper renaming
            ClazzLoader.classesWithReplacedIllegalNames.put(javaStyleName, smaliClazz.getClassPath());
        }

        // we create a corresponding InstrumentedType object for all types
        // which will be used if necessary in case of cyclic types.
        // There is a special case for cyclic types with inheritance relationship
        // which parent type refers to child type in constructor. In this case we have to
        // instrumentedType during parent constructor creation so it already exists in instrumentedTypesMaps
        if(!instrumentedTypesMap.containsKey(smaliClazz.getClassPath())) {
            // we create a corresponding InstrumentedType object which will be used in case of
            // cyclic types. This value will be put in instrumentedTypesMaps in order for the
            // next recursive createMockerClass invocations can use it.
            TypeDescription.Generic g = TypeDescription.Generic.Builder.rawType(Object.class).build();
            InstrumentedType instrumentedType = InstrumentedType.Default.of(javaStyleName, g, Modifier.PUBLIC);
            instrumentedTypesMap.put(smaliClazz.getClassPath(), instrumentedType);
        }

        TypeReference[] ifaces = MockCreator.getInterfacesTypes(smaliClazz);

        DynamicType.Builder builder = byteBuddy.makeInterface().name(javaStyleName);

        for(TypeReference tr: ifaces){
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
            else throw new SmaliSimulationException();
        }


        builder = addInterfaceFields(smaliClazz, builder);

        builder = addInterfaceMethods(smaliClazz, builder);

//        MockCreator.resolveClassesWithPreviouslyCreatedInstrumentedTypeIfNeeded(smaliClazz.getClassPath(), clazzLoader);

        return makeAndLoadIntoSystem(smaliClazz, clazzLoader, builder);

    }

    static DynamicType.Builder addInterfaceFields(SmaliClazz smaliClazz, DynamicType.Builder builder) {
        if (!smaliClazz.isInterfaceClass()) {
            throw new SmaliSimulationException();
        }
        SmaliClass smaliClass = smaliClazz.getSmaliClass();
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();

        // defining the fields in the class
        for (SmaliField sf : smaliClass.getAllFields()) {
            String fieldName = sf.getName();
            String fieldType = sf.getType();

            TypeReference fieldTypeRef = TypeReference.resolveType(fieldType, smaliClazz.getClassPath(), clazzLoader);

            Object initialFieldValue = sf.getInitialValue();

            // because we added .with(TypeValidation.DISABLED) before
            // we can forcefully make fields which are not final
            //int modifiers = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
            int modifiers = Modifier.PUBLIC | Modifier.STATIC;
            if (initialFieldValue != null) {
                DynamicType.Builder.FieldDefinition.Optional.Valuable fieldDef = builder.defineField(fieldName,fieldTypeRef.getCls() , modifiers);
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
                }else {
                    throw new SmaliSimulationException(initialFieldValue.toString());
                }

            } else {
                // normal type
                if (fieldTypeRef.getRefState().equals(TypeReference.RefState.Class)) {
                    builder = builder.defineField(fieldName, fieldTypeRef.getCls(), modifiers);
                }
                // self referencing type
                else if (fieldTypeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)) {
                    builder = builder.defineField(fieldName, fieldTypeRef.getTypeDefinition(), modifiers);
                }
                // inner cyclic type
                else if (fieldTypeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)) {
                    builder = builder.defineField(fieldName, fieldTypeRef.getInstrumentedType(), modifiers);
                }
                // outer or middle cyclic type
                else if (fieldTypeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)) {
                    builder = builder.defineField(fieldName, fieldTypeRef.getTypeDescription(), modifiers);
                } else {
                    throw new SmaliSimulationException();
                }
            }
        }

        return builder;
    }


    static DynamicType.Builder addInterfaceMethods(SmaliClazz smaliClazz, DynamicType.Builder builder){
        ClazzLoader loader = smaliClazz.getClazzLoader();
        for (SmaliMethod sm : smaliClazz.getSmaliClass().getAllMethods()) {
            String methodSignature = sm.getClassMethodSignature();
            int methodNameStart = methodSignature.indexOf("->") + 2;
            int methodNameEnd = methodSignature.indexOf('(');
            int methodArgsEnd = methodSignature.indexOf(')');
            int methodRetTypeEnd = methodSignature.length();
            String methodName = methodSignature.substring(methodNameStart, methodNameEnd);
            methodName = methodName.replace("<", "_").replace(">", "_");
            String retTypeStr = methodSignature.substring(methodArgsEnd + 1, methodRetTypeEnd);

            int modifier =  Modifier.PUBLIC;

            if (sm.isStatic()) {
                if (sm.getClassMethodSignature().contains("<clinit>")) {
                    continue;
                }
                else {
                    modifier = modifier | Modifier.STATIC;
                }
            }



            TypeReference retTypeReference = TypeReference.resolveType(retTypeStr, smaliClazz.getClassPath(), loader);

            DynamicType.Builder.MethodDefinition.ParameterDefinition.Simple methodBuilder;
            if (retTypeReference.getRefState().equals(TypeReference.RefState.Class)) {
                methodBuilder =
                        builder.defineMethod(methodName, retTypeReference.getCls(), modifier);
            }
            else if (retTypeReference.getRefState().equals(TypeReference.RefState.TypeDefinition)) {
                methodBuilder =
                        builder.defineMethod(methodName, retTypeReference.getTypeDefinition(), modifier);
            }
            else if (retTypeReference.getRefState().equals(TypeReference.RefState.InstrumentedType)) {
                methodBuilder =
                        builder.defineMethod(methodName, retTypeReference.getInstrumentedType(), modifier);
            }
            else if (retTypeReference.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)) {
                methodBuilder =
                        builder.defineMethod(methodName, retTypeReference.getTypeDescription(), modifier);
            }
            else {
                throw new IllegalStateException();
            }

            Object[] argTypes = MockCreator.getMethodArgTypes(sm, loader);
            for (Object argType : argTypes) {
                if (argType instanceof Type) {
                    methodBuilder = methodBuilder.withParameter((Type) argType);
                } else if (argType instanceof TypeDefinition) {
                    methodBuilder = methodBuilder.withParameter((TypeDefinition) argType);
                } else {
                    throw new IllegalStateException();
                }
            }

            if(sm.isStatic() || sm.hasImplementation()){
                builder = methodBuilder.intercept(to(new MethodInterceptor(sm, loader)));
            }
            else {
                builder = methodBuilder.withoutCode();
            }
        }
        
        return builder;
    }

}
