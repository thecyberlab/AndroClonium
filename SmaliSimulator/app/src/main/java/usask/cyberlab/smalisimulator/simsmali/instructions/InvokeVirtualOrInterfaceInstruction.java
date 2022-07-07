package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.iface.instruction.Instruction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import kotlinx.html.A;
import usask.cyberlab.smalisimulator.Utils;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousExceptionThrownException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptorHolder;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class InvokeVirtualOrInterfaceInstruction extends InvokeInstruction {


    public InvokeVirtualOrInterfaceInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if (!instructionDef.getOpcode().name.contains("invoke-virtual")
                && !instructionDef.getOpcode().name.contains("invoke-interface")) {
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        Clazz clazz = loader.getClazz(this.classPath);
        Register referenceRegister = methodExecution.getRegister(registerNumbers[0]);
        Class[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);


        // resolving the method we want to invoke
        Method method = Clazz.resolveMethod(referenceRegister, clazz, this.methodOnlySignature.split("\\(")[0], argsClasses);
        method.setAccessible(true);
        Clazz actualMethodDefiningClazz = loader.getClazz(SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName()));

        // get the arguments we need
        Object[] argValsWrappers = getMethodArgWrapperValues(methodExecution);

        // This is done to stop infinite loop invocations from breaking the simulation
        // in case that execution depth is more that the threshold it we will throw an MethodDepthExceeded exception
        if(methodExecution.getExecutionDepth() == SimSmaliConfig.maxMethodDepth){
            handleMethodInvocationDepthExceed(methodExecution);
            return;
        }

        if(referenceRegister.containsAmbiguousValue()){
            AmbiguousValue refAV = referenceRegister.getAmbiguousValue();
            handleAmbiguousInvocation(refAV, argValsWrappers, method, methodExecution, actualMethodDefiningClazz, this.methodOnlySignature, this.methodReturnType, this);
            return;
        }

        // in case that we are invoking a method on a null object we throw a null pointer exception
        AbstractObjekt referenceObjekt = referenceRegister.getReferencedObjectValue();
        if(referenceObjekt == null){
            throwExceptionOn(methodExecution, NullPointerException.class);
            return;
        }

        // checking if we are calling a reflectionAPI that needs to be manually handled
        // this is because of existence of AmbiguousValue which java reflection API does not know about it
        // and also we having safe and unsafe methods
        ReflectionApiHandler.ReflectionAPI r_api = ReflectionApiHandler.ifReflectionGetReflectionAPIType(actualMethodDefiningClazz, method);
        if(r_api != null){
            ReflectionApiHandler.handleReflectionAPI(r_api, methodExecution, this, referenceObjekt, argValsWrappers, method);
        }
        else {
            handleMethodInvocationRoutine(methodExecution, method, referenceObjekt, argValsWrappers, this.methodReturnType, this.methodOnlySignature, this);
        }

    }


    // the reason this method is public and takes all needed values as inputs
    // is that we want to also call this method in case of a method being invoked via reflection
    public static void handleMethodInvocationRoutine(MethodExecution methodExecution,
                                               Method method,
                                               AbstractObjekt referenceObjekt,
                                               Object[] argValsWrappers,
                                               String properMethodReturnType,
                                               String methodName,
                                               InvokeVirtualOrInterfaceInstruction invokeInstruction){

        ClazzLoader loader = methodExecution.getClazzLoader();
        method.setAccessible(true);
        String s = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());
        Clazz actualMethodDefiningClazz = loader.getClazz(s);

        // TODO in-case of reflection this will make the passed array ambiguous
        //  logically we should make the values inside it and the registers containing it ambiguous
        if(isInstanceMethodUnSafe(method,referenceObjekt, loader)){
            captureAmbiguousSideEffects(methodExecution, method, actualMethodDefiningClazz, properMethodReturnType, referenceObjekt,true, invokeInstruction);
            return;
        }

        if(argumentsContainAmbiguousValue(argValsWrappers)){
            handleAmbiguousInvocation(referenceObjekt, argValsWrappers, method, methodExecution, actualMethodDefiningClazz,methodName,properMethodReturnType, invokeInstruction);
            return;
        }

        try {
            if(actualMethodDefiningClazz instanceof SmaliClazz) {
                // increasing the executionDepth
                MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

                // here we also set the wrappers for the method interceptor
                // since it needs to use the actual wrappers since the wrappers contain important
                // information such as the fields or values that have been masked with Ambiguous value
                Object[] tmp = new Object[argValsWrappers.length];
                for (int i = 0; i < argValsWrappers.length; i++) {
                    tmp[i] = argValsWrappers[i];
                }
                MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);
                MethodInterceptorHolder.getInstance().setSelfWrapper((Objekt) referenceObjekt);
            }
            Object[] argVals = unwrapArgObjects(argValsWrappers, method.getParameterTypes());
            Object result = method.invoke(referenceObjekt.getMirroringObject(), argVals);

            // if the invoked method was defined by the user we need to capture the trace
            if(actualMethodDefiningClazz instanceof SmaliClazz) {
                if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null) {
                    methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                    MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                }
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, properMethodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            handleMethodInvocationException(actualException, methodExecution, properMethodReturnType);
            return;
        }
        catch (Throwable e) {
            throw new SmaliSimulationException(e);
        }
    }

    public static void handleAmbiguousInvocation(Object referenceObject,
                                           Object[] argValsWrappers,
                                           Method method,
                                           MethodExecution methodExecution,
                                           Clazz methodDefiningClazz,
                                           String methodName,
                                           String properMethodReturnType,
                                                 InvokeVirtualOrInterfaceInstruction invokeInstruction) {

        if(methodDefiningClazz instanceof SmaliClazz){
            ClazzLoader loader = methodExecution.getClazzLoader();
            if(referenceObject instanceof AmbiguousValue){
                SmaliClazz sc = (SmaliClazz) methodDefiningClazz;
                SmaliMethod sm = sc.getSmaliMethod(methodName);

                if(!sm.hasImplementation()){
                    captureAmbiguousSideEffects(methodExecution,method, methodDefiningClazz, properMethodReturnType, null, false, invokeInstruction);
                    return;
                }

                MethodExecution me = new MethodExecution(sm, loader, (AmbiguousValue) referenceObject);
                me.setExecutionDepth(methodExecution.getExecutionDepth() + 1);
                SimulationUtils.setMethodExecutionWrappedArguments(me, argValsWrappers);
                me.execute();
                MethodExecutionResult mr = me.getExecutionResult();
                methodExecution.addToExecutionTrace(me.getExecutionTrace());
                if(mr.getType() == ResultType.EXCEPTION){
                    Object o = mr.getResult();
                    if(o instanceof Objekt){
                        methodExecution.setThrownException((Objekt) o);
                    }
                    else {
                        methodExecution.setThrownException((AmbiguousValue) o);
                    }

                }
                else {
                    // the invoked could have been called by reflection and would need
                    // type conversion for primitive value
                    if(method.getReturnType().isPrimitive() && method.getReturnType() != void.class){
                        Object o = mr.getResult();
                        MethodExecutionResult mr2 = createMethodExecutionResultFromUnwrappedResults(o, loader, properMethodReturnType);
                        methodExecution.setInvokedFunctionExecutionResult(mr2);
                    }
                    else {
                        methodExecution.setInvokedFunctionExecutionResult(mr);
                    }
                }
            }
            else {
                Objekt referenceObjekt = (Objekt) referenceObject;
                Object result;
                // increasing execution depth
                MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

                // here we also set the wrappers for the method interceptor
                // since it needs to use the actual wrappers since the wrappers contain important
                // information such as the fields or values that have been masked with Ambiguous value
                Object[] tmp =  new Object[argValsWrappers.length];
                for (int i = 0; i < argValsWrappers.length; i++) {
                    tmp[i] = argValsWrappers[i];
                }
                MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);
                MethodInterceptorHolder.getInstance().setSelfWrapper(referenceObjekt);
                try {
                    Object[] argVals = unwrapArgObjects(argValsWrappers, method.getParameterTypes());
                    result = method.invoke(referenceObjekt.getMirroringObject(), argVals);
                }
                catch (InvocationTargetException e) {
                    Throwable actualException = e.getCause();
                    handleMethodInvocationException(actualException, methodExecution, properMethodReturnType);
                    return;
                }
                catch (Throwable e) {
                    throw new SmaliSimulationException(e);
                }
                MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, properMethodReturnType);
                methodExecution.setInvokedFunctionExecutionResult(mr);
                return;
            }
        }
        else {
            if(referenceObject instanceof AmbiguousValue){
                captureAmbiguousSideEffects(methodExecution, method, methodDefiningClazz, properMethodReturnType, null, false, invokeInstruction);
            }
            else {
                Objekt referenceObjekt = (Objekt) referenceObject;
                captureAmbiguousSideEffects(methodExecution, method, methodDefiningClazz, properMethodReturnType, referenceObjekt, true, invokeInstruction);
            }

            return;
        }
    }

    private static void captureAmbiguousSideEffects(MethodExecution methodExecution,
                                                    Method method,
                                                    Clazz methodContainingClazz,
                                                    String methodReturnType,
                                                    AbstractObjekt referenceObjekt,
                                                    boolean needToMakeReferenceObjektAmbiguous,
                                                    InvokeVirtualOrInterfaceInstruction invokeInstruction){
        ClazzLoader loader = methodExecution.getClazzLoader();

        if(!SimulationUtils.isMethodPure(methodContainingClazz, method, loader)){

            if(needToMakeReferenceObjektAmbiguous) {
                // make reference object ambiguous
                Register referenceObjectRegister = methodExecution.getRegister(invokeInstruction.registerNumbers[0]);
                referenceObjectRegister.set(new AmbiguousValue(referenceObjekt.getType(), System.identityHashCode(referenceObjekt.getMirroringObject())));
            }

            // turn parameters ambiguous
            invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
        }
        // return ambiguous value of proper type if method return type is not null
        MethodExecutionResult mr;
        if( !methodReturnType.equals("V"))mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(methodReturnType), methodExecution.getClazzLoader(), methodReturnType);
        else mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), methodReturnType);
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }


    private static void handleMethodInvocationException(Throwable actualException,
                                                        MethodExecution methodExecution,
                                                        String methodReturnType){

        ClazzLoader loader = methodExecution.getClazzLoader();

        // adding the execution trace
        if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null){
            methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
            MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
        }

        // if the Error is my logical error just throw it again
        if(actualException instanceof SmaliSimulationException){
            throw (SmaliSimulationException) actualException;
        }
        // if thrown exception is signaling ambiguous return value
        if (actualException instanceof AmbiguousValueReturnException) {
            AmbiguousValue ambiguousResult = ((AmbiguousValueReturnException) actualException).getAmbiguousValue();
            if(SimulationUtils.isPrimitiveType(ambiguousResult.getType()) && !SimulationUtils.isPrimitiveType(methodReturnType)){
                ambiguousResult = new AmbiguousValue(methodReturnType);
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(ambiguousResult, loader, methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
            return;

        }
        // if thrown exception is signaling thrown ambiguous exception
        else if(actualException instanceof AmbiguousExceptionThrownException){
            AmbiguousExceptionThrownException ee = (AmbiguousExceptionThrownException) actualException;
            methodExecution.setThrownException(ee.getAmbiguousValue());
            return;
        }
        // else if normal exception is thrown
        else {
            String exceptionType = SimulationUtils.makeSmaliStyleClassPath(actualException.getClass().getName());
            Clazz exceptionClazz = loader.getClazz(exceptionType);
            Objekt exceptionObject = new Objekt(exceptionClazz, actualException);
            methodExecution.setThrownException(exceptionObject);
            return;
        }
    }





}
