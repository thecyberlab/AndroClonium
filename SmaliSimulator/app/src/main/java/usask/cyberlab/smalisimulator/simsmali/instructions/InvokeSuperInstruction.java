package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousExceptionThrownException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptorHolder;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeSuperInstruction extends InvokeInstruction {

    public InvokeSuperInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!instructionDef.getOpcode().name.contains("invoke-super")){
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        Register referenceRegister = methodExecution.getRegister(registerNumbers[0]);
        Object[] argValWrappers = this.getMethodArgWrapperValues(methodExecution);

        if(methodExecution.getExecutionDepth() == SimSmaliConfig.maxMethodDepth){
            handleMethodInvocationDepthExceed(methodExecution);
            return;
        }

        if(referenceRegister.containsAmbiguousValue()){
            handleAmbiguousInvocation(referenceRegister.getAmbiguousValue(), argValWrappers, null, methodExecution, null);
            return;
        }

        Objekt referenceObjekt = (Objekt) referenceRegister.getReferencedObjectValue();
        if(referenceObjekt == null){
//            throwNullPointerExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, NullPointerException.class);
            return;
        }


        // resolving the method that causes the corresponding method
        // be called in the super class
        Method method;
        Clazz actualMethodDefiningClazz;
        Class[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);
        SmaliClazz containingSmaliClazz;
        try {
            SmaliClass containingSmaliClass = methodExecution.getSmaliMethod().getContainingClass();
            containingSmaliClazz = (SmaliClazz) loader.getClazz(containingSmaliClass.getClassPath());
            String methodName = this.methodOnlySignature.split("\\(")[0];
            method = resolveSuperMethod(containingSmaliClazz, methodName, argsClasses, loader);
            String s = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());
            actualMethodDefiningClazz = loader.getClazz(s);
        } catch (Exception e) {
            throw new SmaliSimulationException(e);
        }


        method.setAccessible(true);

        // if method is unsafe and therefore should not be executed
        // we use ambiguous value to capture it's side effects
        if(isInstanceMethodUnSafe(method, referenceObjekt, loader)){
            captureAmbiguousSideEffects(methodExecution,method,actualMethodDefiningClazz, referenceObjekt);
            return;
        }

        if(argumentsContainAmbiguousValue(argValWrappers)){
            handleAmbiguousInvocation(referenceObjekt, argValWrappers, method, methodExecution, actualMethodDefiningClazz);
            return;
        }

        if(actualMethodDefiningClazz instanceof SmaliClazz) {
            // increase the maximum execution depth
            MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

            // here we also set the wrappers for the method interceptor
            // since it needs to use the actual wrappers since the wrappers contain important
            // information such as the fields or values that have been masked with Ambiguous value
            Object[] tmp = new Object[argValWrappers.length];
            for (int i = 0; i < argValWrappers.length; i++) {
                tmp[i] = argValWrappers[i];
            }
            MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);

            MethodInterceptorHolder.getInstance().setSelfWrapper(referenceObjekt);
        }

        try {
            Object[] argVals = this.unwrapArgObjects(argValWrappers, method.getParameterTypes());
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle mHandle;
            try {
                mHandle = lookup.unreflectSpecial(method, containingSmaliClazz.getMirroringClass());
            } catch (IllegalAccessException e) {
                throw new SmaliSimulationException(e);
            }

            Object[] allArgs = new Object[argVals.length+1];
            allArgs[0] = referenceObjekt.getMirroringObject();
            for (int i = 0; i < argVals.length; i++) {
                allArgs[1+i] = argVals[i];
            }

            //TODO use resultWrapper object instead
            Object result = mHandle.invokeWithArguments(allArgs);

            if(actualMethodDefiningClazz instanceof SmaliClazz) {
                if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null) {
                    methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                    MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                }
            }

            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        catch (Throwable e) {
            if(e instanceof SmaliSimulationException){
                throw (SmaliSimulationException) e;
            }
            handleMethodInvocationException(e, methodExecution);
            return;
        }
    }


    private Method resolveSuperMethod(SmaliClazz containingSmaliClazz,
                                      String methodName,
                                      Class[] argClasses,
                                      ClazzLoader loader) throws NoSuchMethodException {
        // containingSmaliClass is the SmaliClass that has contains the method that we are in its context
        // because invoke-super can be used to invoke methods in parent class of containing class
        // or default methods in the interfaces that are implemented by the containing class
        // first we should check where we are should look for it
        /// 1- we get the Clazz indicated by the type in the invoke-super instruction
        // 2- if it has defined the method we are looking for we get it by getDeclaredMethod()
        // 3- otherwise we look in the parent of our class and we go up until find the method we lookfor

        Class lookingInClass;
        Clazz clazz = loader.getClazz(this.classPath);


        if(clazz.isInterfaceClass()){
            lookingInClass = clazz.getMirroringClass();
        }
        else {
            String lookingInClassPath = containingSmaliClazz.getParentClassPath();
            lookingInClass = loader.getClazz(lookingInClassPath).getMirroringClass();
        }

        return Clazz.resolveMethodInClass(lookingInClass, methodName, argClasses);
    }


    private void handleAmbiguousInvocation(Object referenceObject,
                                           Object[] argValWrappers,
                                           Method method,
                                           MethodExecution methodExecution,
                                           Clazz methodDefiningClazz) {

        boolean isMethodPure;
        if(method == null || methodDefiningClazz == null){
            isMethodPure = SimulationUtils.isMethodPure(this.classMethodSignature, methodExecution.getClazzLoader());
        }
        else {
            isMethodPure = SimulationUtils.isMethodPure(methodDefiningClazz, method, methodExecution.getClazzLoader());
        }
        if(referenceObject instanceof AmbiguousValue){
            if(methodDefiningClazz instanceof SmaliClazz){
                SmaliClazz sc = (SmaliClazz) methodDefiningClazz;
                SmaliMethod sm = sc.getSmaliMethod(this.methodOnlySignature);
                MethodExecution me = new MethodExecution(sm, methodExecution.getClazzLoader(), (AmbiguousValue) referenceObject);
                SimulationUtils.setMethodExecutionWrappedArguments(me, argValWrappers);
                me.execute();
                MethodExecutionResult mr = me.getExecutionResult();
                methodExecution.addToExecutionTrace(me.getExecutionTrace());
                if(mr.getType() == ResultType.EXCEPTION){
                    Objekt o = (Objekt) mr.getResult();
                    methodExecution.setThrownException(o);
                }
                else {
                    methodExecution.setInvokedFunctionExecutionResult(mr);
                }
            }
            else{
                if(!isMethodPure) {
                    // turn parameters ambiguous
                    makeAllObjectParametersRegistersAmbiguous(methodExecution);
                }
                // return ambiguous value of proper type if method return type is not null
                MethodExecutionResult mr;
                if( !methodReturnType.equals("V"))mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(methodReturnType), methodExecution.getClazzLoader(), methodReturnType);
                else mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), methodReturnType);
                methodExecution.setInvokedFunctionExecutionResult(mr);
            }
        }
        else {
            Objekt referenceObjekt = (Objekt) referenceObject;
            ClazzLoader loader = methodExecution.getClazzLoader();
            if(methodDefiningClazz instanceof SmaliClazz){
                Object result;
                // increase the execution depth
                MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

                // here we also set the wrappers for the method interceptor
                // since it needs to use the actual wrappers since the wrappers contain important
                // information such as the fields or values that have been masked with Ambiguous value
                Object[] tmp = new Object[argValWrappers.length];
                for (int i = 0; i < argValWrappers.length; i++) {
                    tmp[i] = argValWrappers[i];
                }
                MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);

                MethodInterceptorHolder.getInstance().setSelfWrapper(referenceObjekt);

                try {
                    Object[] argVals = this.unwrapArgObjects(argValWrappers, method.getParameterTypes());
                    result = method.invoke(referenceObjekt.getMirroringObject(), argVals);
                }
                catch (InvocationTargetException e) {
                    Throwable actualException = e.getCause();
                    handleMethodInvocationException(actualException, methodExecution);
                    return;
                }
                catch (Throwable e) {
                    throw new SmaliSimulationException(e);
                }
                MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, methodReturnType);
                methodExecution.setInvokedFunctionExecutionResult(mr);
            }
            else {
                if(!isMethodPure) {
                    // make reference object ambiguous
                    Register referenceObjectRegister = methodExecution.getRegister(registerNumbers[0]);
                    referenceObjectRegister.set(new AmbiguousValue(referenceObjekt.getType(), System.identityHashCode(referenceObjekt.getMirroringObject())));

                    // turn parameters ambiguous
                    makeAllObjectParametersRegistersAmbiguous(methodExecution);
                }

                // return ambiguous value of proper type if method return type is not null
                MethodExecutionResult mr;
                if( !methodReturnType.equals("V"))mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(methodReturnType), methodExecution.getClazzLoader(), methodReturnType);
                else mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), methodReturnType);
                methodExecution.setInvokedFunctionExecutionResult(mr);
            }
        }
    }


    private void captureAmbiguousSideEffects(MethodExecution methodExecution,
                                             Method method,
                                             Clazz actualMethodDefiningClazz,
                                             Objekt referenceObjekt){

        ClazzLoader loader = methodExecution.getClazzLoader();
        if(!SimulationUtils.isMethodPure(actualMethodDefiningClazz, method, loader)) {
            // make reference object ambiguous
            Register referenceObjectRegister = methodExecution.getRegister(registerNumbers[0]);
            referenceObjectRegister.set(new AmbiguousValue(referenceObjekt.getType(), System.identityHashCode(referenceObjekt.getMirroringObject())));
            // turn parameters ambiguous
            makeAllObjectParametersRegistersAmbiguous(methodExecution);
        }
        // return ambiguous value of proper type if method return type is not null
        MethodExecutionResult mr;
        if( !methodReturnType.equals("V"))mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(methodReturnType), methodExecution.getClazzLoader(), methodReturnType);
        else mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), methodReturnType);
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }

    private void handleMethodInvocationException(Throwable actualException,
                                                 MethodExecution methodExecution){

        ClazzLoader loader = methodExecution.getClazzLoader();

        if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null) {
            methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
            MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
        }

        // if error is my logical error just throw it again
        if(actualException instanceof SmaliSimulationException){
            throw (SmaliSimulationException) actualException;
        }
        // if thrown exception is signaling ambiguous return value
        if(actualException instanceof AmbiguousValueReturnException){
            AmbiguousValue ambiguousResult = ((AmbiguousValueReturnException) actualException).getAmbiguousValue();
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(ambiguousResult, loader, methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        // if thrown exception is signaling thrown ambiguous exception
        else if(actualException instanceof AmbiguousExceptionThrownException){
            AmbiguousExceptionThrownException ee = (AmbiguousExceptionThrownException) actualException;
            methodExecution.setThrownException(ee.getAmbiguousValue());
        }
        // else if normal exception is thrown
        else {
            String exceptionType = SimulationUtils.makeSmaliStyleClassPath(actualException.getClass().getName());
            Clazz exceptionClazz = loader.getClazz(exceptionType);
            Objekt exceptionObject = new Objekt(exceptionClazz, actualException);
            methodExecution.setThrownException(exceptionObject);
        }
    }

}