package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousExceptionThrownException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileParseException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptorHolder;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeStaticInstruction extends InvokeInstruction{

    public InvokeStaticInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!instructionDef.getOpcode().name.contains("invoke-static")){
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        String methodName = this.methodOnlySignature.split("\\(")[0];
        Method method;
        Clazz actualMethodDefiningClazz;

        // This is done to stop infinite loop invocations from breaking the simulation
        // in case that execution depth is more that the threshold it we will throw an MethodDepthExceeded exception
        if(methodExecution.getExecutionDepth() == SimSmaliConfig.maxMethodDepth){
            handleMethodInvocationDepthExceed(methodExecution);
            return;
        }

        // here we need to load the Clazz that is containing the definition of method we want to use.
        // First we load the Clazz corresponding to the class path used in the invoke-static instruction
        // which is supposed to be that correct class, however, it is correct to call a static method
        // using the name of it's child classes which in that case we need the correct parent class.
        // so we also try resolve the method properly using resolveMethodInClass API
        Clazz instructionClazzOfMethod = loader.getClazz(this.classPath);
        Class[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);
        try {
            method = Clazz.resolveMethodInClass(instructionClazzOfMethod.getMirroringClass(), methodName , argsClasses);
        } catch (Exception e) {
            throw new SmaliSimulationException(e);
        }
        String actualMethodDefiningClassPath = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());
        actualMethodDefiningClazz = loader.getClazz(actualMethodDefiningClassPath);
        // set method accessibility to true
        method.setAccessible(true);

        // here we get the wrapper objects of the arguments that have been passed
        Object[] argValsWrappers = getMethodArgWrapperValues(methodExecution);
        handleStaticMethodInvocationRoutine(methodExecution, method, actualMethodDefiningClazz, argValsWrappers, this, this.methodReturnType);

    }

    // This method can also be called from InvokeVirtualOrInterface class
    // because of reflection on static methods.
    public static void handleStaticMethodInvocationRoutine(MethodExecution methodExecution,
                                                           Method method,
                                                           Clazz refClazz,
                                                           Object[] argValsWrappers,
                                                           InvokeInstruction invokeInstruction,
                                                           String methodReturnType) {
        ClazzLoader loader = methodExecution.getClazzLoader();

        // if a method is unsafe we should not execute it and it's side effects will be capture
        // using the Ambiguous value. When a method is unsafe it will return an Ambiguous value
        // and can turn all of it's passed object arguments to ambiguous value
        if(isStaticMethodUnSafe(refClazz, method, loader)){
            captureAmbiguousSideEffects(methodExecution, method, refClazz, methodReturnType, invokeInstruction);
            return;
        }
        // if method is safe, we can try to execute it
        else{
            if(argumentsContainAmbiguousValue(argValsWrappers)){
                handleAmbiguousMethodCall(methodExecution, refClazz, method, argValsWrappers, methodReturnType, invokeInstruction);
                return;
            }
            try {
                if(Class.class.getMethod("forName", String.class).equals(method) ||
                    Class.class.getMethod("forName", String.class, boolean.class, ClassLoader.class).equals(method)){
                    handleClassForNameReflection(methodExecution, argValsWrappers, invokeInstruction);
                    return;
                }
            }
            catch (Exception e){
                throw new SmaliSimulationException(e);
            }


            // we call the invoke method
            try {
                // here we also set the wrappers for the method interceptor
                // since it needs to use the actual wrappers since the wrappers contain important
                // information such as the fields or values that have been masked with Ambiguous value
                if(refClazz instanceof SmaliClazz) {
                    // we increase the execution depth for future executions
                    MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);
                    // we also set the argument wrappers
                    Object[] tmp = new Object[argValsWrappers.length];
                    for (int i = 0; i < argValsWrappers.length; i++) {
                        tmp[i] = argValsWrappers[i];
                    }
                    MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);
                }

                // we need the true arg values in case that the method we invoke is actually a java/android method
                Object[] argVals = invokeInstruction.unwrapArgObjects(argValsWrappers, method.getParameterTypes());
                //TODO use the AbstractObjekt return value
                Object result = method.invoke(refClazz.getClassObjekt(), argVals);
                if(refClazz instanceof SmaliClazz) {
                    if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null){
                        methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                        MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                    }
                }
                MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, methodReturnType);
                methodExecution.setInvokedFunctionExecutionResult(mr);
                return;
            }
            // if a exception has happened during the method execution...
            catch (InvocationTargetException e) {
                // we get the actual reason the exception has happened
                Throwable actualException = e.getCause();
                handleMethodInvocationException(actualException, methodExecution, invokeInstruction, method, refClazz,methodReturnType);
                return;
            }
            catch (Throwable e) {
                throw new SmaliSimulationException(e);
            }
        }
    }

    private static void handleAmbiguousMethodCall(MethodExecution methodExecution, Clazz methodContainingClazz,
                                           Method method, Object[] argValWrappers, String methodReturnType,
                                                  InvokeInstruction invokeInstruction) {
        // if the method we want invoke is actually smali method then it can be executed
        // normally with ambiguous value since the method will also be simulated.
        if(methodContainingClazz instanceof SmaliClazz){
            // increasing the executionDepth
            MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

            // here we also set the wrappers for the method interceptor
            // since it needs to use the actual wrappers since the wrappers contain important
            // information such as the fields or values that have been masked with Ambiguous value
            Object[] tmp = new Object[argValWrappers.length];
            for (int i = 0; i < argValWrappers.length; i++) {
                tmp[i] = argValWrappers[i];
            }
            MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);

            Object result;
            try {
                // we also use the actual values for passing them to be safe
                Object[] argVals = invokeInstruction.unwrapArgObjects(argValWrappers, method.getParameterTypes());
                result = method.invoke(methodContainingClazz.getClassObjekt(), argVals);

                if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null){
                    methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                    MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                }
            }
            catch (InvocationTargetException e) {
                Throwable actualException = e.getCause();
                handleMethodInvocationException(actualException, methodExecution, invokeInstruction, method, methodContainingClazz, methodReturnType);
                return;
            }
            catch (Throwable e){
                throw new SmaliSimulationException(e);
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result,
                    methodExecution.getClazzLoader(), methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        // in case that the method is java/android then we need to capture the effects of it
        // using ambiguous value
        else {
            captureAmbiguousSideEffects(methodExecution, method, methodContainingClazz, methodReturnType, invokeInstruction);
        }
    }



    private static void captureAmbiguousSideEffects(MethodExecution methodExecution,
                                                    Method method,
                                                    Clazz methodContainingClazz,
                                                    String methodReturnType,
                                                    InvokeInstruction invokeInstruction){

        // if a method is pure it will not change state of it's arguments
        if(!SimulationUtils.isMethodPure(methodContainingClazz, method, methodExecution.getClazzLoader())) {
            invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
        }

        // return ambiguous value of proper type if method return type is not void
        MethodExecutionResult mr;
        if( !methodReturnType.equals("V"))mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(methodReturnType), methodExecution.getClazzLoader(), methodReturnType);
        else mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), methodReturnType);
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }

    private static void handleMethodInvocationException(Throwable actualException,
                                                        MethodExecution methodExecution,
                                                        InvokeInstruction invokeInstruction,
                                                        Method method,
                                                        Clazz methodContainingClazz,
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
        if(actualException instanceof AmbiguousValueReturnException){
            AmbiguousValue result = ((AmbiguousValueReturnException) actualException).getAmbiguousValue();
            if(SimulationUtils.isPrimitiveType(result.getType()) && !SimulationUtils.isPrimitiveType(methodReturnType)){
                result = new AmbiguousValue(methodReturnType);
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, methodReturnType);
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
            Throwable exception = actualException;
            String exceptionType = SimulationUtils.makeSmaliStyleClassPath(exception.getClass().getName());
            Clazz exceptionClazz = loader.getClazz(exceptionType);
            Objekt exceptionObject = new Objekt(exceptionClazz, exception);
            methodExecution.setThrownException(exceptionObject);
            return;
        }
    }


    private static void handleClassForNameReflection(MethodExecution methodExecution,
                                                     Object[] argValsWrappers,
                                                     InvokeInstruction invokeInstruction){
        if(!(argValsWrappers[0] instanceof Objekt)){
            throw new SmaliSimulationException();
        }
        Objekt o = (Objekt) argValsWrappers[0];
        if(!(o.getMirroringObject() instanceof String)){
            throw new SmaliSimulationException();
        }
        String s = (String) o.getMirroringObject();
        String smaliStyleClassPath = SimulationUtils.makeSmaliStyleClassPath(s);
        ClazzLoader loader = methodExecution.getClazzLoader();
        Clazz clazz;
        try {
            clazz = loader.getClazz(smaliStyleClassPath);
        }
        catch (SmaliFileNotFoundException e){
//            throwClassNotFoundExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ClassNotFoundException.class);
            return;
        }
        catch (Throwable e){
            SmaliFileParseException causedBySmaliFileParseException = null;
            Throwable cause = e.getCause();
            while (cause != null){
                if(cause instanceof SmaliFileParseException){
                    causedBySmaliFileParseException = (SmaliFileParseException) cause;
                    break;
                }
                if(cause.getCause() == cause){
                    break;
                }
                cause = cause.getCause();
            }

            if(causedBySmaliFileParseException instanceof SmaliFileNotFoundException){
//                throwClassNotFoundExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, ClassNotFoundException.class);
                return;
            }
            else{
                throw new SmaliSimulationException(e);
            }
        }
        Objekt classObjekt = clazz.getClassObjekt();
        MethodExecutionResult mr = createMethodExecutionResultFromWrappedResults(classObjekt, invokeInstruction.methodReturnType);
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }


//    private static void throwClassNotFoundExceptionOn(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ClassNotFoundException classNotFoundException = new ClassNotFoundException();
//        classNotFoundException.setStackTrace(new StackTraceElement[0]);
//        ReflectedClazz exceptionClazz = (ReflectedClazz) loader.getClazz("Ljava/lang/ClassNotFoundException;");
//        Objekt exceptionObjekt = new Objekt(exceptionClazz, exceptionClazz.getClassPath(), classNotFoundException);
//        methodExecution.setThrownException(exceptionObjekt);
//    }


}