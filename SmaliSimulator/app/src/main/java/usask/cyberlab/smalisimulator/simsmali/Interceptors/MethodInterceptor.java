package usask.cyberlab.smalisimulator.simsmali.Interceptors;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousExceptionThrownException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class MethodInterceptor {

    private final SmaliMethod smaliMethod;
    private final ClazzLoader clazzLoader;


    public MethodInterceptor(SmaliMethod smaliMethod, ClazzLoader clazzLoader){
        this.smaliMethod = smaliMethod;
        this.clazzLoader = clazzLoader;
    }

    @RuntimeType
    public Object interceptStatic(@AllArguments Object[] argsMirrorVals) throws Throwable{

        Object[] argValWrappers = MethodInterceptorHolder.getInstance().getArgValWrappers();
        MethodInterceptorHolder.getInstance().setArgValWrappers(null);

        int executionDepth = MethodInterceptorHolder.getInstance().getExecutionDepth();
        MethodInterceptorHolder.getInstance().setExecutionDepth(0);

        MethodExecutionResult  mr = executeMethod(this.smaliMethod,
                this.clazzLoader, argValWrappers, argsMirrorVals,
                executionDepth, null, null);

        MethodInterceptorHolder.getInstance().setResultObject(mr.getResult());
        return getJavaObjectFromMethodExecutionResult(mr);
    }

    @RuntimeType
    public Object intercept(@AllArguments Object[] argsMirrorVals, @This Object self)
            throws Throwable{
        Object[] argValWrappers = MethodInterceptorHolder.getInstance().getArgValWrappers();
        MethodInterceptorHolder.getInstance().setArgValWrappers(null);

        int executionDepth = MethodInterceptorHolder.getInstance().getExecutionDepth();
        MethodInterceptorHolder.getInstance().setExecutionDepth(0);

        Objekt selfWrapper = MethodInterceptorHolder.getInstance().getSelfWrapper();
        MethodInterceptorHolder.getInstance().setSelfWrapper(null);

        MethodExecutionResult  mr = executeMethod(this.smaliMethod,
                this.clazzLoader, argValWrappers, argsMirrorVals,
                executionDepth, selfWrapper ,self);

            MethodInterceptorHolder.getInstance().setResultObject(mr.getResult());

        return getJavaObjectFromMethodExecutionResult(mr);
    }


    private static MethodExecutionResult executeMethod(SmaliMethod smaliMethod,
                                                      ClazzLoader clazzLoader,
                                                      Object[] argValWrappers,
                                                      Object[] argsMirrorVals,
                                                      int executionDepth,
                                                      Objekt selfWrapper,
                                                      Object self){

        MethodExecution newExecution;
        if(smaliMethod.isStatic()){
            newExecution = new MethodExecution(smaliMethod, clazzLoader);
        }
        else {
            if(selfWrapper != null) {
                newExecution = new MethodExecution(smaliMethod, clazzLoader, selfWrapper);

            }
            else {
                String smaliStyle = SimulationUtils.makeSmaliStyleClassPath(self.getClass().getName());
                Clazz clazz = clazzLoader.getClazz(smaliStyle);
                Objekt o = new Objekt(clazz, self);
                newExecution = new MethodExecution(smaliMethod, clazzLoader, o);
            }
        }

        // we set the execution depth to stop infinite recursive calls
        newExecution.setExecutionDepth(executionDepth);

        // now we set the arguments to our initialExecution
        // if we have the wrappers we will use them, otherwise we fall back to mirrorValues
        if(argValWrappers != null) {
            if(argValWrappers.length != argsMirrorVals.length){
                throw new SmaliSimulationException();
            }
            SimulationUtils.setMethodExecutionWrappedArguments(newExecution, argValWrappers);
        }
        else {
            SimulationUtils.setMethodExecutionArguments(newExecution, argsMirrorVals);
        }

        newExecution.execute();
        MethodInterceptorHolder.getInstance().setLastExecutionTrace(newExecution.getExecutionTrace());
        return newExecution.getExecutionResult();
    }

    private static Object getJavaObjectFromMethodExecutionResult(MethodExecutionResult executionResult)
            throws Throwable{
        if (executionResult.getType() == ResultType.VOID){
            return null;
        }
        if(executionResult.getType() == ResultType.AMBIGUOUS_VALUE){
            throw new AmbiguousValueReturnException((AmbiguousValue) executionResult.getResult());
        }
        if (executionResult.getType() == ResultType.EXCEPTION) {
            if(executionResult.getResult() instanceof AmbiguousValue){
                AmbiguousValue av = (AmbiguousValue) executionResult.getResult();
                throw new AmbiguousExceptionThrownException(av);
            }
            else {
                Objekt exceptionObjekt = (Objekt) executionResult.getResult();
                Throwable t = (Throwable) exceptionObjekt.getMirroringObject();
                throw t;
            }
        }
        Object resultObject = executionResult.getResult();
        if(resultObject instanceof AbstractObjekt){
            AbstractObjekt abstractObjekt = (AbstractObjekt) resultObject;
            return abstractObjekt.getMirroringObject();
        }
        return resultObject;
    }

}
