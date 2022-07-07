package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousExceptionThrownException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousInternalConstructorInvocationException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptorHolder;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeDirectInstruction extends InvokeInstruction {
    public InvokeDirectInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!instructionDef.getOpcode().name.contains("invoke-direct")){
            throw new SmaliSimulationException();
        }
    }


    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        Clazz clazz = loader.getClazz(classPath);
        Register referenceRegister = methodExecution.getRegister(registerNumbers[0]);
        String methodName = this.methodOnlySignature.split("\\(")[0];

        if(methodExecution.getExecutionDepth() == SimSmaliConfig.maxMethodDepth && !methodName.equals("<init>")){
            handleMethodInvocationDepthExceed(methodExecution);
            return;
        }

        // if the register contains ambiguous value then we need to handle it
        // this differ a bit for cases where this is happening during constructor call
        // and a private method call
        if(referenceRegister.containsAmbiguousValue()){
            if(methodOnlySignature.startsWith("<init>")) {
                handleAmbiguousConstructorCall(methodExecution, referenceRegister.getAmbiguousValue(),
                        null, null, referenceRegister, this);
            }
            else {
                Object[] argValWrappers = getMethodArgWrapperValues(methodExecution);
                handleAmbiguousPrivateMethodExecution(methodExecution, referenceRegister.getAmbiguousValue(), argValWrappers, null, (SmaliClazz) clazz);
            }
            return;
        }

        // since invoke direct is only used for constructors and private method
        // we can know that the reference object is an objekt not an array objekt
        // so we simply cast it to objekt
        Objekt referenceObjekt = (Objekt) referenceRegister.getReferencedObjectValue();

        // if reference object is null we throw a null pointer exception
        // of course this only makes sense if this invocation is used for private method invocation
        // for constructor initialization this should never happen
        if(referenceObjekt == null){
            if(methodName.equals("<init>"))
                throw new SmaliSimulationException("constructor initialization should never be called with a null reference object! at:" + methodExecution.getSmaliMethod() + "->" +this.toString());

//            throwNullPointerExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, NullPointerException.class);
            return;
        }


        // this instruction is calling a constructor
        else if(methodName.equals("<init>")){
            Clazz refObjClazz =  referenceObjekt.getClazz();
            Class<?> cls = refObjClazz.getMirroringClass();
            Constructor<?> ctor;
            try {
                Class<?>[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);
                ctor = cls.getDeclaredConstructor(argsClasses);
            }catch (Exception e){
                throw new SmaliSimulationException(e);
            }
            ctor.setAccessible(true);
            Object[] argValWrappers = this.getMethodArgWrapperValues(methodExecution);
            handleConstructorCallRoutine(ctor, methodExecution, referenceRegister, argValWrappers,this);
        }
        // this instruction is calling a private method
        else {
            Method method;
            Class[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);
            try {
                method = Clazz.resolveMethodInClass(clazz.getMirroringClass(), methodName , argsClasses);
            } catch (Exception e) {
                throw new SmaliSimulationException(e);
            }
            method.setAccessible(true);

            Object[] argValWrappers = getMethodArgWrapperValues(methodExecution);
            handlePrivateMethodExecutionRoutine(methodExecution, method, argValWrappers, referenceObjekt, (SmaliClazz) clazz);
        }
    }


    // constructor logic --------------------------------------------------

    public static void handleConstructorCallRoutine(Constructor ctor,
                                                    MethodExecution methodExecution,
                                                    Register referenceRegister,
                                                    Object[] argValWrappers,
                                                    InvokeInstruction invokeInstruction){
        ClazzLoader loader = methodExecution.getClazzLoader();
        Objekt referenceObjekt = (Objekt) referenceRegister.getReferencedObjectValue();
        Clazz refObjClazz = referenceObjekt.getClazz();
        String constructingObjectType = SimulationUtils.makeSmaliStyleClassPath(ctor.getName());

        if(isInstanceMethodUnSafe(ctor, referenceObjekt, loader)){
            captureAmbiguousSideEffectsForConstructors(methodExecution, ctor, referenceRegister, invokeInstruction);
            return;
        }


        if(argumentsContainAmbiguousValue(argValWrappers)){
            handleAmbiguousConstructorCall(methodExecution, referenceObjekt, argValWrappers, ctor, referenceRegister, invokeInstruction);
            return;
        }

        try {
            if(refObjClazz instanceof SmaliClazz){
                // here we also set the wrappers for the method interceptor
                // since it needs to use the actual wrappers since the wrappers contain important
                // information such as the fields or values that have been masked with Ambiguous value
                ConstructorInterceptor.argsWrappers = new Object[argValWrappers.length];
                for (int i = 0; i < argValWrappers.length; i++) {
                    ConstructorInterceptor.argsWrappers[i] = argValWrappers[i];
                }

                // set the referenceObjekt
                ConstructorInterceptor.selfWrapper = referenceObjekt;
            }

            Object[] argVals = invokeInstruction.unwrapArgObjects(argValWrappers, ctor.getParameterTypes());
            Object instance = ctor.newInstance(argVals);
            ConstructorInterceptor.selfWrapper = null;
            ConstructorInterceptor.argsWrappers = null;
            if(refObjClazz instanceof SmaliClazz && SimSmaliConfig.shouldCaptureConstructorCalls){
                methodExecution.addToExecutionTrace(ConstructorInterceptor.executionTraceStack.pop());
            }
            // this check is done since for java objects
            // the mirroring object is not set but for smali objects
            // it is set in CtorInterceptor class
            if(referenceObjekt.getMirroringObject() == null){
                referenceObjekt.setMirroringObject(instance);
            }
        }
        catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            ConstructorInterceptor.selfWrapper = null;
            ConstructorInterceptor.argsWrappers = null;
            handleConstructorInvocationException(actualException, methodExecution,
                    invokeInstruction, referenceRegister, constructingObjectType, refObjClazz);
        }
        catch (Throwable e) {
            ConstructorInterceptor.selfWrapper = null;
            ConstructorInterceptor.argsWrappers = null;
            throw new SmaliSimulationException(e);
        }
    }

    private static void handleAmbiguousConstructorCall(MethodExecution methodExecution,
                                                       Object referenceObject,
                                                       Object[] argValWrappers,
                                                       Constructor constructor,
                                                       Register refRegister,
                                                       InvokeInstruction invokeInstruction){

        String constructingObjectType = SimulationUtils.makeSmaliStyleClassPath(constructor.getName());
        if(referenceObject instanceof AmbiguousValue){
            ClazzLoader loader = methodExecution.getClazzLoader();
            try {
                Clazz clz = loader.getClazz(((AmbiguousValue) referenceObject).getType());
                if(!SimulationUtils.isMethodPure(clz,constructor,loader)){
                    // turn parameters ambiguous
                    invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
                }
            }
            catch (Exception e){
                invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), "V");
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
        else {
            Objekt refObjekt = (Objekt) referenceObject;
            Clazz refClazz = refObjekt.getClazz();
            if (refClazz instanceof SmaliClazz) {
                ConstructorInterceptor.selfWrapper = refObjekt;
                ConstructorInterceptor.argsWrappers = new Object[argValWrappers.length];
                for (int i = 0; i < argValWrappers.length; i++) {
                    ConstructorInterceptor.argsWrappers[i] = argValWrappers[i];
                }

                try {
                    Object[] argVals = invokeInstruction.unwrapArgObjects(argValWrappers, constructor.getParameterTypes());
                    Object o = constructor.newInstance(argVals);
                    ConstructorInterceptor.selfWrapper = null;
                    ConstructorInterceptor.argsWrappers = null;
                    if(refObjekt.getMirroringObject() == null){
                        refObjekt.setMirroringObject(o);
                    }

                    if(SimSmaliConfig.shouldCaptureConstructorCalls) {
                        methodExecution.addToExecutionTrace(ConstructorInterceptor.executionTraceStack.pop());
                    }


                }
                catch (InvocationTargetException e) {
                    ConstructorInterceptor.selfWrapper = null;
                    ConstructorInterceptor.argsWrappers = null;
                    Throwable actualException = e.getCause();
                    handleConstructorInvocationException(actualException, methodExecution,
                            invokeInstruction, refRegister, constructingObjectType, refClazz);
                }
                catch (Throwable e) {
                    ConstructorInterceptor.selfWrapper = null;
                    ConstructorInterceptor.argsWrappers = null;
                    throw new SmaliSimulationException(e);
                }
            }
            else {
                if(!SimulationUtils.isMethodPure(refClazz, constructor, methodExecution.getClazzLoader())) {
                    invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
                }
                refRegister.set(new AmbiguousValue(constructingObjectType));
                MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(null, methodExecution.getClazzLoader(), "V");
                methodExecution.setInvokedFunctionExecutionResult(mr);
            }
        }
    }

    private static void captureAmbiguousSideEffectsForConstructors(MethodExecution methodExecution,
                                                    Constructor ctor,
                                                    Register referenceRegister,
                                                    InvokeInstruction invokeInstruction){
        ClazzLoader loader = methodExecution.getClazzLoader();
        Objekt referenceObjekt = (Objekt) referenceRegister.getReferencedObjectValue();
        Clazz refObjClazz = referenceObjekt.getClazz();

        if(!SimulationUtils.isMethodPure(refObjClazz,ctor, loader)){
            // make reference object ambiguous
            referenceRegister.set(new AmbiguousValue(referenceObjekt.getType(), System.identityHashCode(referenceObjekt.getMirroringObject())));

            // turn parameters ambiguous
            invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
        }

        // return ambiguous value of proper type if method return type is not null
        MethodExecutionResult mr;
        mr = createMethodExecutionResultFromUnwrappedResults(null, loader, "V");
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }


    private static void handleConstructorInvocationException(Throwable actualException,
                                                              MethodExecution methodExecution,
                                                              InvokeInstruction invokeInstruction,
                                                              Register refRegister,
                                                              String constructingObjectType,
                                                              Clazz refObjClazz){
        // adding the execution trace
        if(refObjClazz instanceof SmaliClazz && SimSmaliConfig.shouldCaptureConstructorCalls){
            methodExecution.addToExecutionTrace(ConstructorInterceptor.executionTraceStack.pop());
        }
        // if the Error is my logical error just throw it again
        if (actualException instanceof SmaliSimulationException){
            throw (SmaliSimulationException) actualException;
        }
        // if thrown exception is signaling ambiguous return value
        if(actualException instanceof AmbiguousExceptionThrownException){
            AmbiguousExceptionThrownException ee = (AmbiguousExceptionThrownException) actualException;
            methodExecution.setThrownException(ee.getAmbiguousValue());
        }
        // if thrown exception is signaling thrown ambiguous exception
        else if (actualException instanceof AmbiguousInternalConstructorInvocationException) {
            invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);
            refRegister.set(new AmbiguousValue(constructingObjectType));
        }
        // else if normal exception is thrown
        else {
            ClazzLoader loader = methodExecution.getClazzLoader();
            String exceptionType = SimulationUtils.makeSmaliStyleClassPath(actualException.getClass().getName());
            Clazz exceptionClazz = loader.getClazz(exceptionType);
            Objekt exceptionObject = new Objekt(exceptionClazz, actualException);
            methodExecution.setThrownException(exceptionObject);
        }
    }




    // private method logic -------------------------------------------------------
    private void handlePrivateMethodExecutionRoutine(MethodExecution methodExecution,
                                                     Method method,
                                                     Object[] argValWrappers,
                                                     Objekt referenceObjekt,
                                                     SmaliClazz clazz){
        ClazzLoader loader = methodExecution.getClazzLoader();
        if(argumentsContainAmbiguousValue(argValWrappers)){
            handleAmbiguousPrivateMethodExecution(methodExecution, referenceObjekt, argValWrappers, method, clazz);
            return;
        }
        Clazz refObjClazz =  referenceObjekt.getClazz();
        if(refObjClazz instanceof SmaliClazz){
            // we increase the execution depth for future executions
            MethodInterceptorHolder.getInstance().setExecutionDepth(methodExecution.getExecutionDepth() + 1);

            // we also set the argument wrappers
            Object[] tmp = new Object[argValWrappers.length];
            for (int i = 0; i < argValWrappers.length; i++) {
                tmp[i] = argValWrappers[i];
            }
            MethodInterceptorHolder.getInstance().setArgValWrappers(tmp);
        }

        MethodInterceptorHolder.getInstance().setSelfWrapper(referenceObjekt);

        try {
            Object[] argVals = unwrapArgObjects(argValWrappers, method.getParameterTypes());
            Object result = method.invoke(referenceObjekt.getMirroringObject(), argVals);

            if(refObjClazz instanceof SmaliClazz){
                if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null) {
                    methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                    MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                }
            }
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, loader, this.methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
            return;
        }
        catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            handleMethodInvocationException(actualException, methodExecution);
        }

        catch (Throwable e) {
            throw new SmaliSimulationException(e);
        }
    }




    private void handleAmbiguousPrivateMethodExecution(MethodExecution methodExecution,
                                                       Object referenceObject,
                                                       Object[] argValWrappers,
                                                       Method method,
                                                       SmaliClazz methodDefiningClazz) {

        if(referenceObject instanceof AmbiguousValue){
            SmaliClazz sc = methodDefiningClazz;
            SmaliMethod sm = sc.getSmaliMethod(this.methodOnlySignature);
            if(!sm.hasImplementation()){
                captureAmbiguousSideEffectsForPrivateMethodWithAmbiguousReference(methodExecution, this);
                return;
            }
            MethodExecution me = new MethodExecution(sm, methodExecution.getClazzLoader(), (AmbiguousValue) referenceObject);
            SimulationUtils.setMethodExecutionWrappedArguments(me, argValWrappers);
            me.setExecutionDepth(methodExecution.getExecutionDepth() + 1);
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
                methodExecution.setInvokedFunctionExecutionResult(mr);
            }

        }
        // since invoke-direct on a private method can only be called in a user code
        // we can try to execute this
        else {
            Object result;
            Objekt referenceObjekt = (Objekt) referenceObject;

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
            MethodInterceptorHolder.getInstance().setSelfWrapper(referenceObjekt);

            try {
                Object[] argVals = this.unwrapArgObjects(argValWrappers, method.getParameterTypes());
                result = method.invoke(referenceObjekt.getMirroringObject(), argVals);

                if(MethodInterceptorHolder.getInstance().getLastExecutionTrace() != null) {
                    methodExecution.addToExecutionTrace(MethodInterceptorHolder.getInstance().getLastExecutionTrace());
                    MethodInterceptorHolder.getInstance().setLastExecutionTrace(null);
                }
            }
            catch (InvocationTargetException e) {
                Throwable actualException = e.getCause();
                handleMethodInvocationException(actualException, methodExecution);
                return;
            }
            catch (Throwable e) {
                throw new SmaliSimulationException(e);
            }

            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(result, methodExecution.getClazzLoader(), this.methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(mr);
        }
    }

    private void handleMethodInvocationException(Throwable actualException,
                                                 MethodExecution methodExecution){

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
            AmbiguousValueReturnException re = (AmbiguousValueReturnException) actualException;
            AmbiguousValue av = re.getAmbiguousValue();
            MethodExecutionResult mr = createMethodExecutionResultFromUnwrappedResults(av, loader, this.methodReturnType);
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


    private static void captureAmbiguousSideEffectsForPrivateMethodWithAmbiguousReference(MethodExecution methodExecution,
                                                    InvokeInstruction invokeInstruction){
        ClazzLoader loader = methodExecution.getClazzLoader();

        // turn parameters ambiguous
        invokeInstruction.makeAllObjectParametersRegistersAmbiguous(methodExecution);

        // return ambiguous value of proper type if method return type is not null
        MethodExecutionResult mr;
        if("V".equals(invokeInstruction.methodReturnType)) mr = createMethodExecutionResultFromUnwrappedResults(null, loader, "V");
        else mr = createMethodExecutionResultFromUnwrappedResults(new AmbiguousValue(invokeInstruction.methodReturnType), loader, invokeInstruction.methodReturnType);
        methodExecution.setInvokedFunctionExecutionResult(mr);
    }


}
