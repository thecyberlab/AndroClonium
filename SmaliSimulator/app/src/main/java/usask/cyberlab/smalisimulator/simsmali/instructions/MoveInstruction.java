package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.*;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class MoveInstruction extends SmaliInstruction {

    public final MoveInstructionType type;
    public Integer srcRegisterNumber;
    public final Integer destRegisterNumber;

    public enum MoveInstructionType{
        WIDE,
        REGISTER,
        EXCEPTION,
        OBJECT,
        RESULT,
        RESULT_WIDE,
        RESULT_OBJECT,
    }

    public MoveInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        String opCodeName = instructionDef.getOpcode().name;
        if(instructionDef instanceof BuilderInstruction12x){
            BuilderInstruction12x bi = (BuilderInstruction12x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();
            this.srcRegisterNumber = bi.getRegisterB();
        }
        else if(instructionDef instanceof BuilderInstruction22x){
            BuilderInstruction22x bi = (BuilderInstruction22x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();
            this.srcRegisterNumber = bi.getRegisterB();
        }
        else if(instructionDef instanceof BuilderInstruction32x){
            BuilderInstruction32x bi = (BuilderInstruction32x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();
            this.srcRegisterNumber = bi.getRegisterB();
        }
        else if(instructionDef instanceof BuilderInstruction11x){
            BuilderInstruction11x bi = (BuilderInstruction11x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();

        }
        else{
            throw new SmaliSimulationException();
        }

        if (opCodeName.contains("result")){
            if(opCodeName.contains("object")){
                this.type = MoveInstructionType.RESULT_OBJECT;
            }
            else if(opCodeName.contains("wide")){
                this.type = MoveInstructionType.RESULT_WIDE;
            }
            else {
                this.type = MoveInstructionType.RESULT;
            }
        }
        else if(opCodeName.contains("exception")){
            this.type = MoveInstructionType.EXCEPTION;
        }
        else if(opCodeName.contains("object")){
            this.type = MoveInstructionType.OBJECT;
        }
        else if(opCodeName.contains("wide")){
            this.type = MoveInstructionType.WIDE;
        }
        else {
            this.type = MoveInstructionType.REGISTER;
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register srcReg = null;

        // if move instruction is move-result-*
        // or it is move-exception there is no src_reg
        if(type != MoveInstructionType.RESULT
                && type != MoveInstructionType.RESULT_OBJECT
                && type != MoveInstructionType.RESULT_WIDE
                && type != MoveInstructionType.EXCEPTION){
            srcReg = methodExecution.getRegister(srcRegisterNumber);
        }
        Register destReg = methodExecution.getRegister(destRegisterNumber);

        // since internally all registers use an int value to represent their value
        // and different types get converted to int when assigning them register
        // for int, float, short, byte, char, and boolean I just use getIntValue to move them
        // and for float and double I use getLongValue to move the values
        if(type == MoveInstructionType.REGISTER){
            if(srcReg.containsAmbiguousValue()) destReg.set(srcReg.getAmbiguousValue());
            else destReg.set(srcReg.getIntValue());
        }
        else if(type == MoveInstructionType.WIDE){
            if(srcReg.containsAmbiguousValue()) destReg.set(srcReg.getAmbiguousValue());
            else destReg.set(srcReg.getLongValue());
        }
        else if(type == MoveInstructionType.OBJECT){
            if(srcReg.containsAmbiguousValue()) destReg.set(srcReg.getAmbiguousValue());
            else destReg.set(srcReg.getReferencedObjectValue());
        }

        else if(type == MoveInstructionType.RESULT ||
                type == MoveInstructionType.RESULT_OBJECT ||
                type == MoveInstructionType.RESULT_WIDE){
            MethodExecutionResult invokedExecutionResult = methodExecution.getInvokedFunctionExecutionResult();
            if(invokedExecutionResult.getType() == ResultType.AMBIGUOUS_VALUE){
                AmbiguousValue ambiguousValue = (AmbiguousValue) invokedExecutionResult.getResult();
                destReg.set(ambiguousValue);
            }
            else if(type == MoveInstructionType.RESULT){
                if(invokedExecutionResult.getType() == ResultType.INTEGER) {
                    Integer res = (Integer) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else if(invokedExecutionResult.getType() == ResultType.FLOAT){
                    Float res = (Float) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else if(invokedExecutionResult.getType() == ResultType.BOOLEAN){
                    Boolean res = (Boolean) invokedExecutionResult.getResult();
                    if(res){
                        destReg.set(1);
                    }
                    else {
                        destReg.set(0);
                    }
                }
                else if(invokedExecutionResult.getType() == ResultType.BYTE){
                    Byte res = (Byte) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else if(invokedExecutionResult.getType() == ResultType.CHAR){
                    Character temp = (char) invokedExecutionResult.getResult();
                    Integer res = (int) temp;
                    destReg.set(res);
                }
                else if(invokedExecutionResult.getType() == ResultType.SHORT){
                    Short res = (Short) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else {
                    throw new SmaliSimulationException("Invalid value!, invokedExecutionResult type is : " + invokedExecutionResult.getType());
                }
            }
            else if(type == MoveInstructionType.RESULT_WIDE){
                if(invokedExecutionResult.getType() == ResultType.LONG) {
                    Long res = (Long) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else if(invokedExecutionResult.getType() == ResultType.DOUBLE) {
                    Double res = (Double) invokedExecutionResult.getResult();
                    destReg.set(res);
                }
                else {
                    throw new SmaliSimulationException("Invalid value!, invokedExecutionResult type is : " + invokedExecutionResult.getType());
                }
            }
            else {
                Object o = invokedExecutionResult.getResult();
                destReg.set((AbstractObjekt) o);
            }

        }

        else if(type == MoveInstructionType.EXCEPTION){
            Object exceptionObject = methodExecution.getThrownExceptionObject();
            methodExecution.clearAllCachedThrownExceptionObjects();
            if(exceptionObject instanceof AmbiguousValue){
                AmbiguousValue av = (AmbiguousValue) exceptionObject;
                destReg.set(av);
            }
            else {
                Objekt so = (Objekt) exceptionObject;
                destReg.set(so);
            }
        }
        else {
            throw new SmaliSimulationException("Invalid instruction type!");
        }
    }

    @Override
    public String toString() {
        // move-* vA
        if(type == MoveInstructionType.RESULT
        || type == MoveInstructionType.RESULT_OBJECT
        || type == MoveInstructionType.RESULT_WIDE
        || type == MoveInstructionType.EXCEPTION){
            return this.opCode + " v"+destRegisterNumber
                    + " &" + instructionPositionNumber;
        }
        // move-* vA, vB
        else {
            return this.opCode + " v"+destRegisterNumber + " v"
                    + srcRegisterNumber + " &" + instructionPositionNumber;
        }
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {


        JSONObject jo = new JSONObject();
        if(type == MoveInstructionType.EXCEPTION ||
            type == MoveInstructionType.RESULT ||
            type == MoveInstructionType.RESULT_OBJECT ||
            type == MoveInstructionType.RESULT_WIDE){
            Register r = methodExecution.getRegister(destRegisterNumber);
            jo.put("v" + destRegisterNumber, getContentInfo(r));
        }
        else {
            Register rA = methodExecution.getRegister(srcRegisterNumber);
            Register rB = methodExecution.getRegister(destRegisterNumber);

            jo.put("v" + srcRegisterNumber, getContentInfo(rA));
            jo.put("v" + destRegisterNumber, getContentInfo(rB));
        }

        return jo.toString();
    }
}