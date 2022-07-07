package usask.cyberlab.smalisimulator.simsmali.types.mockerCreation;

import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeDirectInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.classesInConstructorDeclarationProcess;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.getMethodArgTypes;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.instrumentedTypesMap;
import static usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator.typeDescriptionWithDeclaredConstructorMap;

public class MockClassConstructorCreator {

    static DynamicType.Builder addClassConstructors(SmaliClazz smaliClazz, DynamicType.Builder builder,
                                                    TypeReference parentTypeRef){

        classesInConstructorDeclarationProcess.add(smaliClazz.getClassPath());
        // These fields are used for passing contents of registers during an <init>
        // to parent <init> function. In this implementation each <init> function gets its own fields
        builder = defineConstructorVariableHolder(smaliClazz, builder);

        SmaliClass smaliClass = smaliClazz.getSmaliClass();
        for(SmaliMethod sm: smaliClass.getAllMethods()){
            String classMethodSignature = sm.getClassMethodSignature();
            // if the method is a constructor
            if (classMethodSignature.contains("-><init>(")) {
                builder = createClassConstructor(sm, builder, parentTypeRef, smaliClazz);
            }
        }
        classesInConstructorDeclarationProcess.remove(smaliClazz.getClassPath());
        typeDescriptionWithDeclaredConstructorMap.put(smaliClazz.getClassPath(), builder.toTypeDescription());
        return builder;
    }

    private static DynamicType.Builder<?> createClassConstructor(final SmaliMethod sm,
                                                         DynamicType.Builder<?> builder,
                                                         TypeReference parentTypeRef,
                                                         SmaliClazz smaliClazz) {
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();

//        Object[] constructorArgTypes = getConstructorArgTypes(sm, clazzLoader);
        Object[] constructorArgTypes = getMethodArgTypes(sm, clazzLoader);
        DynamicType.Builder.MethodDefinition.ParameterDefinition.Simple constructorBuilder =
                builder.defineConstructor(Modifier.PUBLIC);
        for (Object argType : constructorArgTypes) {
            if (argType instanceof Type) {
                constructorBuilder = constructorBuilder.withParameter((Type) argType);
            } else if (argType instanceof TypeDefinition) {
                constructorBuilder = constructorBuilder.withParameter((TypeDefinition) argType);
            } else {
                throw new IllegalStateException();
            }
        }

        ConstructorInterceptor ctorInterceptor = new ConstructorInterceptor(sm, clazzLoader);
        ConstructorInterceptor.CtorInterceptor1 c1 = ctorInterceptor.new CtorInterceptor1();
        ConstructorInterceptor.CtorInterceptor2 c2 = ctorInterceptor.new CtorInterceptor2();

        InvokeInstruction superCtorCall = getSuperConstructorInvocationInstruction(sm);
        String ctorDefClassPath = superCtorCall.getClassMethodSignature().split("->")[0];

        Object[] superConstructorArgTypes = getArgTypesOfNestedSuperConstructorCall(sm, smaliClazz);
        String[] ctorVariableHolderNames = new String[superConstructorArgTypes.length];

        for (int i = 0; i < ctorVariableHolderNames.length; i++) {
            ctorVariableHolderNames[i] = MockCreator.getConstructorArgHolderName(sm, i);
        }

        // self referencing constructor
        if(ctorDefClassPath.equals(sm.getContainingClass().getClassPath())){
            ElementMatcher.Junction selector = ElementMatchers.isConstructor()
                    .and(ElementMatchers.takesArguments(superConstructorArgTypes.length));


            if(parentTypeRef.getRefState().equals(TypeReference.RefState.Class)){
                selector = selector.and(ElementMatchers.not(ElementMatchers.isDeclaredBy(parentTypeRef.getCls())));
            }
            else if(parentTypeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                selector = selector.and(ElementMatchers.not(ElementMatchers.isDeclaredBy(parentTypeRef.getInstrumentedType())));
            }
            else if(parentTypeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                selector = selector.and(ElementMatchers.not(ElementMatchers.isDeclaredBy(parentTypeRef.getTypeDescription())));
            }
            else throw new SmaliSimulationException();

            for (int i = 0; i < superConstructorArgTypes.length; i++) {
                if(superConstructorArgTypes[i] instanceof Class) {
                    Class c = (Class) superConstructorArgTypes[i];
                    selector = selector.and(ElementMatchers.takesArgument(i, c));
                }
                else if(superConstructorArgTypes[i] instanceof TypeDescription){
                    selector = selector.and(ElementMatchers.takesArgument(i, (TypeDescription) superConstructorArgTypes[i]));
                }
                else {
                    throw new SmaliSimulationException();
                }
            }

            return constructorBuilder.intercept(to(c1).andThen(MethodCall.invoke(selector).withField(ctorVariableHolderNames)).andThen(to(c2)));
        }
        // parent referencing constructor
        else {
            // if parent is already loaded and we have its class
            if(parentTypeRef.getRefState().equals(TypeReference.RefState.Class)){
                Constructor<?> superConstructor;
                try {
                    Class[] classes = new Class[superConstructorArgTypes.length];
                    for (int i = 0; i < superConstructorArgTypes.length; i++) {
                        Object o = superConstructorArgTypes[i];
                        if(o instanceof Class) {
                            classes[i] = (Class) o;
                        }
                        else {
                            //What is this ??==========================
                            throw new UnsupportedOperationException();
                        }
                    }
                    superConstructor = parentTypeRef.getCls().getDeclaredConstructor(classes);
                    superConstructor.setAccessible(true);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
                return constructorBuilder.intercept(to(c1)
                        .andThen(MethodCall.invoke(superConstructor).withField(ctorVariableHolderNames))
                        .andThen(to(c2)));
            }
            // if parent is not fully loaded yet. This happens in InheritedCyclic types
            else {
                ElementMatcher.Junction selector = ElementMatchers.isConstructor()
                        .and(ElementMatchers.takesArguments(superConstructorArgTypes.length));

                if(parentTypeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                    selector = selector.and(ElementMatchers.isDeclaredBy(parentTypeRef.getInstrumentedType()));
                }
                else if(parentTypeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                    selector = selector.and(ElementMatchers.isDeclaredBy(parentTypeRef.getTypeDescription()));
                }
                else throw new IllegalStateException();

                for (int i = 0; i < superConstructorArgTypes.length; i++) {
                    if(superConstructorArgTypes[i] instanceof Class) {
                        Class c = (Class) superConstructorArgTypes[i];
                        selector = selector.and(ElementMatchers.takesArgument(i, c));
                    }
                    else if(superConstructorArgTypes[i] instanceof TypeDescription){
                        selector = selector.and(ElementMatchers.takesArgument(i, (TypeDescription) superConstructorArgTypes[i]));
                    }
                    else {
                        throw new IllegalStateException();
                    }
                }
                return constructorBuilder.intercept(to(c1)
                        .andThen(MethodCall.invoke(selector).withField(ctorVariableHolderNames))
                        .andThen(to(c2)));
            }
        }
    }

    private static InvokeInstruction getSuperConstructorInvocationInstruction(SmaliMethod sm){
        return SmaliMethod.getSuperConstructorCall(sm);
    }


    private static DynamicType.Builder defineConstructorVariableHolder(SmaliClazz smaliClazz,
                                                               DynamicType.Builder builder) {
        SmaliClass smaliClass = smaliClazz.getSmaliClass();
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        for (SmaliMethod sm : smaliClass.getAllMethods()) {
            if (sm.getClassMethodSignature().contains("<init>")) {
                InvokeDirectInstruction nestedCtorCall = (InvokeDirectInstruction) getSuperConstructorInvocationInstruction(sm);
                for (int i = 0; i < nestedCtorCall.getArgTypes().length; i++) {
                    String fieldName = MockCreator.getConstructorArgHolderName(sm, i);
                    String argType = nestedCtorCall.getArgTypes()[i];

                    TypeReference typeRef = TypeReference.resolveType(argType, smaliClass.getClassPath(), clazzLoader);

                    if(typeRef.getRefState().equals(TypeReference.RefState.Class)){
                        builder = builder.defineField(fieldName,typeRef.getCls() , Modifier.STATIC | Modifier.PUBLIC);
                    }
                    else if(typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)){
                        builder = builder.defineField(fieldName,typeRef.getTypeDefinition() , Modifier.STATIC | Modifier.PUBLIC);
                    }
                    else if(typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                        builder = builder.defineField(fieldName,typeRef.getInstrumentedType(), Modifier.STATIC | Modifier.PUBLIC);
                    }
                    else if(typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                        builder = builder.defineField(fieldName,typeRef.getTypeDescription() , Modifier.STATIC | Modifier.PUBLIC);
                    }
                    // this never happens but just to be sure
                    else {
                        throw new IllegalStateException();
                    }
                }
            }
        }
        return builder;
    }


    private static Object[] getArgTypesOfNestedSuperConstructorCall(SmaliMethod sm,
                                                                    SmaliClazz smaliClazz) {
        SmaliInstruction instruction = SmaliMethod.getSuperConstructorCall(sm);
        InvokeDirectInstruction directInstruction = (InvokeDirectInstruction) instruction;
        ClazzLoader clazzLoader = smaliClazz.getClazzLoader();
        String[] argTypes = directInstruction.getArgTypes();
        Object[] result = new Object[argTypes.length];

        for (int i = 0; i < argTypes.length; i++) {
            String argType = argTypes[i];

            TypeReference typeRef = TypeReference.resolveType(argType,smaliClazz.getClassPath(),clazzLoader);
            if(typeRef.getRefState().equals(TypeReference.RefState.Class)){
                result[i] = typeRef.getCls();
            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)){
                // ElementMatchers.takesArgument() API does not take TypeDefinition
                // and InstrumentedType class should be used.

                //  For arrays we can just use asErasure() api to convert it to InstrumentedType
                if(argType.contains("[")){
                    result[i] = typeRef.getTypeDefinition().asErasure();
                }
                // but for self referencing type asErasure() api does not work properly
                // so we can just use the InstrumentedType of the type
                else {
                    result[i] = instrumentedTypesMap.get(argType);
                }

            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
                result[i] = typeRef.getInstrumentedType();
            }
            else if(typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
                result[i] = typeRef.getTypeDescription();
            }
            // this never happens but just to be sure
            else {
                throw new IllegalStateException();
            }
        }
        return result;
    }



//    // this method returns an array of Class, InstrumentedType, TypeDefinition or TypeDescription
//    // which will be used in creating a constructor using byte-buddy
//    static Object[] getConstructorArgTypes(SmaliMethod sm, ClazzLoader loader){
//        String[] argTypes = sm.getArgumentTypes();
//        Object[] res = new Object[argTypes.length];
//        SmaliClass containingSmaliClass = sm.getContainingClass();
//        String containingClassType = containingSmaliClass.getClassPath();
//        for (int i = 0; i < res.length; i++) {
//            String argType = argTypes[i];
//            TypeReference typeRef = TypeReference.resolveType(argType, containingClassType, loader);
//            if(typeRef.getRefState().equals(TypeReference.RefState.Class)){
//                res[i] = typeRef.getCls();
//            }
//            else if(typeRef.getRefState().equals(TypeReference.RefState.TypeDefinition)){
//                res[i] = typeRef.getTypeDefinition();
//            }
//            else if(typeRef.getRefState().equals(TypeReference.RefState.InstrumentedType)){
//                res[i] = typeRef.getInstrumentedType();
//            }
//            else if(typeRef.getRefState().equals(TypeReference.RefState.DynamicType_Unloaded_TypeDescription)){
//                res[i] = typeRef.getTypeDescription();
//            }
//            else {
//                throw new IllegalStateException();
//            }
//        }
//        return res;
//    }

}
