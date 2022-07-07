package usask.cyberlab.smalisimulator.simsmali.emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.FakeSuperConstructorCallInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.PartialSmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliTryBlock;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

/**
 * the MethodExecution class is responsible for representing a single execution of
 * a method or constructor. Each MethodExecution keep track of all things related
 * to a single code execution such as the corresponding SmaliMethod, registers,
 * the execution trace, the result of execution as a MethodExecutionResult
 *
 */
public class MethodExecution {
    private final ClazzLoader clazzLoader;
    private final SmaliMethod smaliMethod;

//    private AmbiguousValue containingAmbiguousValue;
//    private Objekt containingObject;

//    private ArrayList<SmaliInstruction> instructionsArray;
//    private String[] methodArgTypes;

//    private int argumentStartRegister;

    private int instructionPointer = 0;
    private HashMap<Integer, Register> registers;

    private boolean exceptionThrown;
    private boolean isFinished;

    private MethodExecutionResult executionResult = null;
    private MethodExecutionResult invokedFunctionExecutionResult = null;
    private Objekt thrownExceptionObject;
    private AmbiguousValue ambiguousThrownException;
    private ExecutionTrace executionTrace;
    private int executionDepth = 0;
    private boolean ignoreExecutionTrace = false;


    private void init(SmaliMethod smaliMethod, ClazzLoader clazzLoader, boolean shouldLoadContainingClass) {
        this.executionTrace = new ExecutionTrace(smaliMethod, clazzLoader);
        this.exceptionThrown = false;
        this.isFinished = false;
        this.registers = new HashMap<>();

        if(shouldLoadContainingClass) {
            SmaliClass sc = smaliMethod.getContainingClass();
            clazzLoader.loadClazz(sc);
        }
//        this.methodArgTypes = smaliMethod.getArgumentTypes();
//        this.argumentStartRegister = smaliMethod.getFirstPassedArgumentRegisterNumber();
        int offset = 0;
        for (int i = 0; i < smaliMethod.getArgumentTypes().length; i++) {
            String s = smaliMethod.getArgumentTypes()[i];
            if(s.equals("J") || s.equals("D")){
                offset = offset + 2;
            }
            else {
                offset++;
            }
        }
    }

    // used to create MethodExecution from static methods that can be executed without a containing object
    public MethodExecution(SmaliMethod smaliMethod, ClazzLoader clazzLoader) {
        if(!smaliMethod.isStatic() && !smaliMethod.getClassMethodSignature().contains("<init>")){
            throw new IllegalStateException("MethodExecution for non static method cannot be created without the containing object!");
        }
        init(smaliMethod, clazzLoader, true);
        this.clazzLoader = clazzLoader;
        this.smaliMethod = smaliMethod;
    }

    // used to create MethodExecution from non static methods that are contained by an object
    public MethodExecution(SmaliMethod smaliMethod, ClazzLoader clazzLoader, Objekt simulatedObject) {
        init(smaliMethod, clazzLoader,true);
        this.clazzLoader = clazzLoader;
        this.smaliMethod = smaliMethod;
        if(!smaliMethod.isStatic()) {
//            this.containingObject = simulatedObject;
            int refRegNumber = smaliMethod.getNumberOfLocalRegisters();
            Register paramReg = getRegister(refRegNumber);
            paramReg.set(simulatedObject);
        }
    }

    public MethodExecution(SmaliMethod smaliMethod, ClazzLoader clazzLoader, AmbiguousValue ambiguousSelf) {
        init(smaliMethod, clazzLoader,true);
        this.clazzLoader = clazzLoader;
        this.smaliMethod = smaliMethod;
        if(!smaliMethod.isStatic()) {
//            this.containingAmbiguousValue = ambiguousSelf;
            int refRegNumber = smaliMethod.getNumberOfLocalRegisters();
            Register paramReg = getRegister(refRegNumber);
            paramReg.set(ambiguousSelf);
        }
    }


    public void execute() {
        while (!isFinished && !exceptionThrown){
            // this should never happen but anyway.
            if(instructionPointer >= getInstructionList().size()){
                isFinished = true;
                break;
            }
            SmaliInstruction instruction = getInstructionList().get(instructionPointer);
            if(instruction instanceof FakeSuperConstructorCallInstruction){
                isFinished = true;
            }
            else {
                instruction.executeWrapper(this);
            }
            if(exceptionThrown){
                for (SmaliTryBlock tb: smaliMethod.getTryBlockList()) {
                    int exceptionInstructionPointer = instructionPointer - 1;
                    int start = tb.getStartingFromInstruction();
                    int end = tb.getEndingOnInstruction();
                    String exceptionType = tb.getExceptionType();
                    if(thrownExceptionObject != null){
                        if((start <= exceptionInstructionPointer  && exceptionInstructionPointer < end)
                                &&
                                ( exceptionType == null || thrownExceptionObject.isInstanceOf(exceptionType))){
                            instructionPointer = tb.getJumpLocation();
                            exceptionThrown = false;
                            break;
                        }
                    }
                    else if(ambiguousThrownException != null){
                        Clazz clazz = clazzLoader.getClazz(ambiguousThrownException.getType());
                        if((start <= exceptionInstructionPointer  && exceptionInstructionPointer < end)
                                &&
                                ( exceptionType == null || clazz.isSubTypeOf(exceptionType))){
                            instructionPointer = tb.getJumpLocation();
                            exceptionThrown = false;
                            break;
                        }
                    }
                    else {
                        throw new IllegalStateException("exceptionThrown should not be set to True without setting the exception object.");
                    }
                }
            }
        }
        if(exceptionThrown){
            isFinished = true;
            if(thrownExceptionObject != null){
                executionResult = new MethodExecutionResult(thrownExceptionObject, ResultType.EXCEPTION, this.smaliMethod);
            }
            else if(ambiguousThrownException != null){
                executionResult = new MethodExecutionResult(ambiguousThrownException, ResultType.EXCEPTION, this.smaliMethod);
            }
            else {
                throw new IllegalStateException();
            }

        }
    }

    public Register getRegister(int registerNumber){
        if(registers.containsKey(registerNumber)){
            return registers.get(registerNumber);
        }
        Register newReg = new Register(registerNumber, this);
        registers.put(registerNumber, newReg);
        return newReg;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public ClazzLoader getClazzLoader(){
        return clazzLoader;
    }

    public SmaliMethod getSmaliMethod() {
        return smaliMethod;
    }

    public void setExecutionResult(MethodExecutionResult executionResult) {
        this.executionResult = executionResult;
    }

    public MethodExecutionResult getExecutionResult() {
        return executionResult;
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    public void setInstructionPointer(int instruction_pointer) {
        this.instructionPointer = instruction_pointer;
    }

    public boolean isExceptionThrown() {
        return exceptionThrown;
    }

    public Object getThrownExceptionObject() {
        boolean ambiguousThrownExceptionIsNull = ambiguousThrownException==null;
        boolean thrownExceptionObjectIsNull = thrownExceptionObject == null;
        boolean checkValid = ambiguousThrownExceptionIsNull ^ thrownExceptionObjectIsNull;
        if(!checkValid){
            throw new SmaliSimulationException();
        }
        if(!ambiguousThrownExceptionIsNull) return ambiguousThrownException;
        return thrownExceptionObject;
    }

    public void clearAllCachedThrownExceptionObjects(){
        ambiguousThrownException=null;
        thrownExceptionObject=null;
    }

    public void setThrownException(Objekt thrownExceptionObject) {
        if(!thrownExceptionObject.isInstanceOf("Ljava/lang/Throwable;")){
            throw new SmaliSimulationException();
        }
        if(thrownExceptionObject.getMirroringObject() instanceof SmaliSimulationException){
            throw (SmaliSimulationException) thrownExceptionObject.getMirroringObject();
        }
        this.exceptionThrown = true;
        this.ambiguousThrownException = null;
        this.thrownExceptionObject = thrownExceptionObject;
    }

    public void setThrownException(AmbiguousValue av){
        this.exceptionThrown = true;
        this.ambiguousThrownException = av;
        this.thrownExceptionObject = null;

    }

    public MethodExecutionResult getInvokedFunctionExecutionResult() {
        return invokedFunctionExecutionResult;
    }

    public void setInvokedFunctionExecutionResult(MethodExecutionResult invokedFunctionExecutionResult) {
        this.invokedFunctionExecutionResult = invokedFunctionExecutionResult;
    }


    public String[] getMethodArgTypes(){
        return smaliMethod.getArgumentTypes();
    }

    public HashMap<Integer, Register> getAllRegisters() {
        return registers;
    }

    public void  setAllRegisters(HashMap<Integer, Register> newRegSet){
        registers = newRegSet;
    }

    public void addToExecutionTrace(SmaliInstruction smaliInstruction) {
        if(ignoreExecutionTrace) return;
        executionTrace.addInstruction(smaliInstruction, this);
    }

    public void addToExecutionTrace(ExecutionTrace executionTrace) {
        if(ignoreExecutionTrace) return;
        this.executionTrace.addExecutionTrace(executionTrace);
    }

    public ExecutionTrace getExecutionTrace() {
        return executionTrace;
    }


    public int getExecutionDepth() {
        return executionDepth;
    }

    public void setExecutionDepth(int executionDepth) {
        this.executionDepth = executionDepth;
    }

    public void setIgnoreExecutionTrace(boolean ignoreExecutionTrace) {
        this.ignoreExecutionTrace = ignoreExecutionTrace;
    }

    private ArrayList<SmaliInstruction> getInstructionList(){
        if(smaliMethod instanceof PartialSmaliMethod){
            PartialSmaliMethod partialSmaliMethod = (PartialSmaliMethod) smaliMethod;
            return partialSmaliMethod.getSmaliInstructionListWithoutSuperConstructorCall();
        }
        else {
            return smaliMethod.getInstructionList();
        }
    }

    public int getArgumentStartRegister(){
        return smaliMethod.getFirstPassedArgumentRegisterNumber();
    }
}
