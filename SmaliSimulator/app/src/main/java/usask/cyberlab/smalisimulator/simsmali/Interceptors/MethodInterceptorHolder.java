package usask.cyberlab.smalisimulator.simsmali.Interceptors;

import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class MethodInterceptorHolder {

    private Object[] argValWrappers;
    private int executionDepth;
    private Objekt selfWrapper;
    private Object resultObject;
    private ExecutionTrace lastExecutionTrace;

    private static final MethodInterceptorHolder instance = new MethodInterceptorHolder();
    
    private MethodInterceptorHolder(){
    }

    public static MethodInterceptorHolder getInstance(){
        return instance;
    }

    public Object[] getArgValWrappers() {
        return argValWrappers;
    }

    public int getExecutionDepth() {
        return executionDepth;
    }

    public Objekt getSelfWrapper() {
        return selfWrapper;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public ExecutionTrace getLastExecutionTrace() {
        return lastExecutionTrace;
    }

    public void setArgValWrappers(Object[] a) {
        this.argValWrappers = a;
    }

    public void setExecutionDepth(int a) {
        this.executionDepth = a;
    }


    public void setSelfWrapper(Objekt a) {
        this.selfWrapper = a;
    }

    public void setResultObject(Object a) {
        this.resultObject = a;
    }

    public void setLastExecutionTrace(ExecutionTrace a) {
        this.lastExecutionTrace = a;
    }
}
