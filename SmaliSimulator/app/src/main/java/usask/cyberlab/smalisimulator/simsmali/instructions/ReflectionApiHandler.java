package usask.cyberlab.smalisimulator.simsmali.instructions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ReflectionApiHandler {

    //TODO later also add
    //  Class.cast(Object)
    //  Class.isInstance(Object)
    //  Class.asSubclass(Object)
    // here is an example for cast() and isInstance() method
    // public void whenDowncastToCatWithCastMethod_thenMeowIsCalled() {
    //    Animal animal = new Cat();
    //    if (Cat.class.isInstance(animal)) {
    //        Cat cat = Cat.class.cast(animal);
    //        cat.meow();
    //    }
    //}

    public enum ReflectionAPI {
        MethodInvocation,
        ConstructorInvocation,
        FieldGet,
        FieldSet;
    }

    public static ReflectionAPI ifReflectionGetReflectionAPIType(Clazz actualMethodDefiningClazz,
                                                                  Method method){
        if(actualMethodDefiningClazz.getClassPath().equals("Ljava/lang/reflect/Method;")
                && method.getName().equals("invoke")){
            return ReflectionAPI.MethodInvocation;
        }
        else if((actualMethodDefiningClazz.getClassPath().equals("Ljava/lang/reflect/Constructor;")
                || actualMethodDefiningClazz.getClassPath().equals("Ljava/lang/Class;"))
                && method.getName().equals("newInstance")){
            return ReflectionAPI.ConstructorInvocation;
        }
        else if(actualMethodDefiningClazz.getClassPath().equals("Ljava/lang/reflect/Field;")){
            if(method.getName().equals("get") ||
                    method.getName().equals("getInt") ||
                    method.getName().equals("getFloat") ||
                    method.getName().equals("getLong") ||
                    method.getName().equals("getDouble") ||
                    method.getName().equals("getBoolean") ||
                    method.getName().equals("getByte") ||
                    method.getName().equals("getShort") ||
                    method.getName().equals("getChar")){
                return ReflectionAPI.FieldGet;
            }
            else if(method.getName().equals("set") ||
                    method.getName().equals("setInt") ||
                    method.getName().equals("setFloat") ||
                    method.getName().equals("setLong") ||
                    method.getName().equals("setDouble") ||
                    method.getName().equals("setBoolean") ||
                    method.getName().equals("setByte") ||
                    method.getName().equals("setShort") ||
                    method.getName().equals("setChar")){
                return ReflectionAPI.FieldSet;
            }
            return null;
        }
        return null;
    }

    private static String getProperMethodReturnType(Method trueMethod) {
        String returnTypeSmali = SimulationUtils.makeSmaliStyleClassPath(trueMethod.getReturnType().getName());
        if(returnTypeSmali.equals("I")) return "Ljava/lang/Integer;";
        if(returnTypeSmali.equals("F")) return "Ljava/lang/Float;";
        if(returnTypeSmali.equals("D")) return "Ljava/lang/Double;";
        if(returnTypeSmali.equals("J")) return "Ljava/lang/Long;";
        if(returnTypeSmali.equals("Z")) return "Ljava/lang/Boolean;";
        if(returnTypeSmali.equals("S")) return "Ljava/lang/Short;";
        if(returnTypeSmali.equals("C")) return "Ljava/lang/Char;";
        if(returnTypeSmali.equals("B")) return "Ljava/lang/Byte;";
        return returnTypeSmali;
    }


    public static void handleReflectionAPI(ReflectionAPI r_api,
                                           MethodExecution methodExecution,
                                           InvokeVirtualOrInterfaceInstruction invokeInstruction,
                                           AbstractObjekt referenceObjekt,
                                           Object[] argValsWrappers,
                                           Method reflectionApiMethod){
        if(r_api == ReflectionApiHandler.ReflectionAPI.MethodInvocation){
            ReflectionApiHandler.handleReflectiveCall(methodExecution, referenceObjekt, argValsWrappers, invokeInstruction);
            return;
        }
        else if(r_api == ReflectionApiHandler.ReflectionAPI.ConstructorInvocation){
            ReflectionApiHandler.handleReflectiveConstructorCall(methodExecution, referenceObjekt, argValsWrappers, invokeInstruction);
            return;
        }
        else if(r_api == ReflectionApiHandler.ReflectionAPI.FieldGet || r_api == ReflectionApiHandler.ReflectionAPI.FieldSet){
            ReflectionApiHandler.handleReflectiveFieldOperation(methodExecution, referenceObjekt, argValsWrappers, r_api, reflectionApiMethod.getName());
            return;
        }
    }

    private static void handleReflectiveCall(MethodExecution methodExecution,
                                      AbstractObjekt referenceObjekt,
                                      Object[] argValsWrappers,
                                     InvokeVirtualOrInterfaceInstruction invokeInstruction) {

        ClazzLoader loader = methodExecution.getClazzLoader();

        // get the real method
        Method trueMethod = (Method) referenceObjekt.getMirroringObject();
        int trueMethodModifiers = trueMethod.getModifiers();

        // get the real arguments to the method
        Object[] trueArgValWrappers;
        if(trueMethod.getParameterTypes().length == 0){
            trueArgValWrappers = new Object[0];
        }
        else {
            ArrayObjekt ao = (ArrayObjekt) argValsWrappers[1];
            trueArgValWrappers = new Object[ao.getSize()];
            for (int i = 0; i < trueArgValWrappers.length; i++) {
                trueArgValWrappers[i] = ao.getValue(i);
                String javaStyleMethodArgType = trueMethod.getParameterTypes()[i].getName();
                String methodArgType = SimulationUtils.makeSmaliStyleClassPath(javaStyleMethodArgType);
                if(SimulationUtils.isPrimitiveType(methodArgType) && trueArgValWrappers[i] instanceof Objekt){
                    Objekt o = (Objekt) trueArgValWrappers[i];
                    trueArgValWrappers[i] = o.getMirroringObject();
                }

            }
        }

        Class refClass = trueMethod.getDeclaringClass();
        Clazz trueRefClazz = loader.getClazz(SimulationUtils.makeSmaliStyleClassPath(refClass.getName()));
        String properReturnType = getProperMethodReturnType(trueMethod);
        String methodName = SimulationUtils.getSmaliMethodSignature(trueMethod);

        if(Modifier.isStatic(trueMethodModifiers)){
            InvokeStaticInstruction.handleStaticMethodInvocationRoutine(methodExecution, trueMethod, trueRefClazz, trueArgValWrappers, invokeInstruction, properReturnType);
        }
        else {
            // get the real reference object which is the first argument in the method
            Object referenceObject = argValsWrappers[0];
            if(referenceObject == null){
                SmaliInstruction.throwExceptionOn(methodExecution, NullPointerException.class);
                return;
            }
            else if(referenceObject instanceof AbstractObjekt) {
                AbstractObjekt trueReferenceObjekt = (AbstractObjekt) referenceObject;
                InvokeVirtualOrInterfaceInstruction.handleMethodInvocationRoutine(methodExecution, trueMethod, trueReferenceObjekt, trueArgValWrappers, properReturnType, methodName, invokeInstruction);
            }
            else if(referenceObject instanceof AmbiguousValue){
                InvokeVirtualOrInterfaceInstruction.handleAmbiguousInvocation(referenceObject, trueArgValWrappers, trueMethod, methodExecution, trueRefClazz, methodName, properReturnType, invokeInstruction);
            }
            // this should never happen
            // because if object is not AbstractObjekt or Ambiguous it means
            // it is primitive value which is not possible for reflection
            else {
                throw new SmaliSimulationException("invalid reference object type : " + referenceObject.toString());
            }
        }

    }

    private static void handleReflectiveConstructorCall(MethodExecution methodExecution,
                                                 AbstractObjekt reflectionReferenceObjekt,
                                                 Object[] argValsWrappers,
                                                 InvokeVirtualOrInterfaceInstruction invokeInstruction) {

        Object refMirrorObject = reflectionReferenceObjekt.getMirroringObject();
        Constructor<?> constructorObj;
        if(refMirrorObject instanceof Constructor){
            constructorObj = (Constructor<?>) refMirrorObject;
        }
        else if(refMirrorObject instanceof Class){
            Class<?> classObj = (Class<?>) refMirrorObject;
            try {
                constructorObj = classObj.getConstructor();
            } catch (NoSuchMethodException e) {
                SmaliInstruction.throwExceptionOn(methodExecution, NoSuchMethodException.class);
                return;
            }
        }
        else {
            throw new SmaliSimulationException();
        }

        // creating a new-wrapper object for the object we want to create
        // since creating an object with reflection
        // kind of implicitly has a new-instance instruction in it too
        String constructingObjektType = SimulationUtils.makeSmaliStyleClassPath(constructorObj.getName());
        Clazz clz = methodExecution.getClazzLoader().getClazz(constructingObjektType);
        Objekt newObjekt = new Objekt(clz);

        // get the real arguments to the method
        Object[] trueArgValWrappers;
        if(constructorObj.getParameterTypes().length == 0){
            trueArgValWrappers = new Object[0];
        }
        else {
            Object o = argValsWrappers[0];
            if(o instanceof AmbiguousValue){
                int refRegNum = invokeInstruction.registerNumbers[0];
                Register referenceRegister = methodExecution.getRegister(refRegNum);
                referenceRegister.set(new AmbiguousValue(reflectionReferenceObjekt.getType()));
                return;
            }
            ArrayObjekt ao = (ArrayObjekt) o;
            trueArgValWrappers = new Object[ao.getSize()];
            for (int i = 0; i < trueArgValWrappers.length; i++) {
                trueArgValWrappers[i] = ao.getValue(i);
            }
        }

        Register fakeReferenceRegister = methodExecution.getRegister(-1);
        fakeReferenceRegister.set(newObjekt);
        InvokeDirectInstruction.handleConstructorCallRoutine(constructorObj, methodExecution, fakeReferenceRegister , trueArgValWrappers, invokeInstruction);

        MethodExecutionResult mr = InvokeInstruction.createMethodExecutionResultFromWrappedResults(newObjekt, "Ljava/lang/Object;");
        methodExecution.setInvokedFunctionExecutionResult(mr);


    }

    private static void handleReflectiveFieldOperation(MethodExecution methodExecution,
                                                AbstractObjekt referenceObjekt,
                                                Object[] argValsWrappers,
                                                ReflectionAPI rApiType,
                                                String apiName){

        ClazzLoader loader = methodExecution.getClazzLoader();
        Field field = (Field) referenceObjekt.getMirroringObject();
        boolean isStaticField = Modifier.isStatic(field.getModifiers());

        if(ReflectionAPI.FieldGet == rApiType && isStaticField){
            if(isInvalidFieldApiUsed(field, apiName)){
                InvokeInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                return;
            }

            String toLoadClazzPath = SimulationUtils.makeSmaliStyleClassPath(field.getDeclaringClass().getName());
            Clazz clazz = loader.getClazz(toLoadClazzPath);
            Object result = clazz.getStaticFieldValue(field.getName());
            String fieldType =  clazz.getFieldType(field.getName());
            MethodExecutionResult mr;
            if(SimulationUtils.isPrimitiveType(fieldType) && apiName.equals("get") &&
                    !(result instanceof AmbiguousValue) && !(result instanceof AbstractObjekt)){
                mr  = InvokeInstruction.createMethodExecutionResultFromUnwrappedResults(result,loader, "Ljava/lang/Object;");
            }
            else {
                mr = InvokeInstruction.createMethodExecutionResultFromWrappedResults(result,fieldType);
            }

            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        else if(ReflectionAPI.FieldGet == rApiType){
            if(argValsWrappers[0] == null){
                SmaliInstruction.throwExceptionOn(methodExecution, NullPointerException.class);
                return;
            }
            if(isInvalidFieldApiUsed(field, apiName)){
                SmaliInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                return;
            }
            if(!(argValsWrappers[0] instanceof Objekt) && !(argValsWrappers[0] instanceof AmbiguousValue)) {
                throw new SmaliSimulationException();
            }
            String smaliStyleFieldContainerClass = SimulationUtils.makeSmaliStyleClassPath(field.getDeclaringClass().getName());
            Clazz fieldContainingClazz = loader.getClazz(smaliStyleFieldContainerClass);
            Object result;
            String fieldType;
            if(argValsWrappers[0] instanceof AmbiguousValue){
                AmbiguousValue av = (AmbiguousValue) argValsWrappers[0];
                fieldType = SimulationUtils.makeSmaliStyleClassPath(field.getType().getName());
//                Clazz ambiguousRefObjectClazz = loader.getClazz(av.getType());
                //TODO===
                // if type of refObject is not subType of fieldDeclaringClass
//                if(!ambiguousRefObjectClazz.isSubTypeOf(smaliStyleFieldContainerClass)){
//                    //TODO I will allow this case for now to not throw an exception
//                    // if ambiguousValue type is super type of fieldDeclaringClass
//                    // then we don't know what should happen for sure since the true type
//                    // of refObject could have been valid, so we use randomness
//                    if(ambiguousRefObjectClazz.isSuperTypeOf(smaliStyleFieldContainerClass)){
//                        int r = SimulationUtils.getRandomNumberInRange(0, 1);
//                        if(r==0){
//                            throwIllegalArgumentExceptionOn(methodExecution);
//                            return;
//                        }
//                    }
//                }
                result = new AmbiguousValue(fieldType);
            }
            else {
                Objekt refObjekt = (Objekt) argValsWrappers[0];
                if(!refObjekt.isInstanceOf(smaliStyleFieldContainerClass)){
                    SmaliInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                    return;
                }
                result = refObjekt.getInstanceFieldValue(field.getName(), fieldContainingClazz);
                fieldType = refObjekt.getFieldType(field.getName(), fieldContainingClazz);
            }
            MethodExecutionResult mr;
            if(SimulationUtils.isPrimitiveType(fieldType) && apiName.equals("get") &&
                    !(result instanceof AmbiguousValue) && !(result instanceof AbstractObjekt)){
                mr  = InvokeInstruction.createMethodExecutionResultFromUnwrappedResults(result,loader, "Ljava/lang/Object;");
            }
            else {
                mr = InvokeInstruction.createMethodExecutionResultFromWrappedResults(result,fieldType);
            }
            methodExecution.setInvokedFunctionExecutionResult(mr);

        }
        else if(ReflectionAPI.FieldSet == rApiType && isStaticField){
            if(isInvalidFieldApiUsed(field, apiName)){
                SmaliInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                return;
            }
            String toLoadClazzPath = SimulationUtils.makeSmaliStyleClassPath(field.getDeclaringClass().getName());
            Clazz clazz = loader.getClazz(toLoadClazzPath);
            Object newFieldValue = argValsWrappers[1];
            clazz.setStaticFieldValue(field.getName(), newFieldValue);
            MethodExecutionResult mr = InvokeInstruction.createMethodExecutionResultFromUnwrappedResults(null, loader, "V");
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        else if(ReflectionAPI.FieldSet == rApiType){
            if(argValsWrappers[0] == null){
                SmaliInstruction.throwExceptionOn(methodExecution, NullPointerException.class);
                return;
            }
            if(isInvalidFieldApiUsed(field, apiName)){
                SmaliInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                return;
            }
            if(!(argValsWrappers[0] instanceof Objekt) && !(argValsWrappers[0] instanceof AmbiguousValue)) {
                throw new SmaliSimulationException();
            }
            // setting a value on Ambiguous value still leaves the
            // the ref object ambiguous so there is nothing to do
            if(argValsWrappers[0] instanceof AmbiguousValue){
                MethodExecutionResult mr = InvokeInstruction.createMethodExecutionResultFromUnwrappedResults(null, loader, "V");
                methodExecution.setInvokedFunctionExecutionResult(mr);
                return;
            }
            Objekt refObjekt = (Objekt) argValsWrappers[0];
            String smaliStyleFieldContainerClass = SimulationUtils.makeSmaliStyleClassPath(field.getDeclaringClass().getName());
            Clazz fieldContainingClazz = loader.getClazz(smaliStyleFieldContainerClass);
            if(!refObjekt.isInstanceOf(smaliStyleFieldContainerClass)){
                SmaliInstruction.throwExceptionOn(methodExecution, IllegalArgumentException.class);
                return;
            }
            Object newFieldValue = argValsWrappers[1];
            refObjekt.setInstanceFieldValue(field.getName(), newFieldValue, fieldContainingClazz);
            MethodExecutionResult mr = InvokeInstruction.createMethodExecutionResultFromUnwrappedResults(null, loader, "V");
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        else {
            throw new SmaliSimulationException("Invalid ReflectionAPI type!");
        }
    }

    //TODO java reflection api actually does not do this and tries to convert types if possible
    // and if not possible then throw IllegalArgumentException
    private static boolean isInvalidFieldApiUsed(Field field, String apiName){
        if("getInt".equals(apiName) && !field.getType().equals(int.class)) return true;
        if("getFloat".equals(apiName) && !field.getType().equals(float.class)) return true;
        if("getLong".equals(apiName) && !field.getType().equals(long.class)) return true;
        if("getDouble".equals(apiName) && !field.getType().equals(double.class)) return true;
        if("getBoolean".equals(apiName) && !field.getType().equals(boolean.class)) return true;
        if("getByte".equals(apiName) && !field.getType().equals(byte.class)) return true;
        if("getShort".equals(apiName) && !field.getType().equals(short.class)) return true;
        if("getChar".equals(apiName) && !field.getType().equals(char.class)) return true;
        if("setInt".equals(apiName) && !field.getType().equals(int.class)) return true;
        if("setFloat".equals(apiName) && !field.getType().equals(float.class)) return true;
        if("setLong".equals(apiName) && !field.getType().equals(long.class)) return true;
        if("setDouble".equals(apiName) && !field.getType().equals(double.class)) return true;
        if("setBoolean".equals(apiName) && !field.getType().equals(boolean.class)) return true;
        if("setByte".equals(apiName) && !field.getType().equals(byte.class)) return true;
        if("setShort".equals(apiName) && !field.getType().equals(short.class)) return true;
        if("setChar".equals(apiName) && !field.getType().equals(char.class)) return true;
        return false;
    }



}
