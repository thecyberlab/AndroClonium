package usask.cyberlab.smalisimulator.simsmali.emulator;

import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

/***
 * this class contains the result of a method execution.
 * a method execution result is a valid object or primitive and does not include thrown exceptions
 */
public class MethodExecutionResult {
    private final SmaliMethod smaliMethod;
    private final Object result;
    private final ResultType type;

    public MethodExecutionResult(Integer result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.INTEGER;
        this.smaliMethod = smaliMethod;

    }

    public MethodExecutionResult(Float result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.FLOAT;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Double result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.DOUBLE;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Long result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.LONG;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Boolean result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.BOOLEAN;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Character result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.CHAR;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Byte result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.BYTE;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(Short result,SmaliMethod smaliMethod) {
        this.result = result;
        this.type = ResultType.SHORT;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(AmbiguousValue result, SmaliMethod smaliMethod){
        this.result = result;
        this.type = ResultType.AMBIGUOUS_VALUE;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(AmbiguousValue result,ResultType type, SmaliMethod smaliMethod){
        this.result = result;
        this.type = type;
        this.smaliMethod = smaliMethod;
    }

    public MethodExecutionResult(AbstractObjekt objekt, ResultType type, SmaliMethod smaliMethod){
        // result type is needed because if type of returned value of
        // a method is instance of Exception the we have to know if it was a thrown
        // exception or returned object
        this.result = objekt;
        this.type = type;
        this.smaliMethod = smaliMethod;
    }

    public Object getResult() {
        return result;
    }

    public ResultType getType() {
        return type;
    }


    public SmaliMethod getSmaliMethod(){
        return this.smaliMethod;
    }
}