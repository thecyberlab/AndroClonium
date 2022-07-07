package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction10x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
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

public class ReturnInstruction extends SmaliInstruction {
    private final ReturnInstructionType type;
    private int destRegisterNumber;

    public enum ReturnInstructionType{
        VOID,
        NORMAL,
        RETURN_WIDE,
        RETURN_OBJECT,
    }

    public ReturnInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction11x){
            BuilderInstruction11x bi = (BuilderInstruction11x) instructionDef;
            String opcode = instructionDef.getOpcode().name;
            this.destRegisterNumber = bi.getRegisterA();
            if (opcode.contains("wide")){
                type = ReturnInstructionType.RETURN_WIDE;
            }
            else if (opcode.contains("object")){
                type = ReturnInstructionType.RETURN_OBJECT;
            }
            else {
                type = ReturnInstructionType.NORMAL;
            }
        }
        else if(instructionDef instanceof BuilderInstruction10x){
            type = ReturnInstructionType.VOID;
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        methodExecution.setFinished(true);
        MethodExecutionResult mr = createExecutionResult(methodExecution);
        methodExecution.setExecutionResult(mr);
    }

    @Override
    public String toString() {
        //return-void
        if(type == ReturnInstructionType.VOID){
            return this.opCode + " &" + instructionPositionNumber;
        }
        //return vAA
        //return-wide vAA
        //return-object vAA
        else {
            return this.opCode + " v" +
                    destRegisterNumber + " &" + instructionPositionNumber;
        }
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        if(type == ReturnInstructionType.VOID){
            return "";
        }
        else {
            Register r = methodExecution.getRegister(destRegisterNumber);
            JSONObject jo = new JSONObject();
            jo.put("v" + destRegisterNumber, getContentInfo(r));
            return jo.toString();
        }
    }

    private MethodExecutionResult createExecutionResult(MethodExecution methodExecution) {
        String returnType =  methodExecution.getSmaliMethod().getReturnType();
        Register destRegister = methodExecution.getRegister(destRegisterNumber);

        //void
        if(returnType.equals("V")){
            if(type != ReturnInstructionType.VOID){
                throw new SmaliSimulationException();
            }
            return new MethodExecutionResult((AbstractObjekt) null, ResultType.VOID, methodExecution.getSmaliMethod());
        }
        // Ambiguous Value
        else if(destRegister.containsAmbiguousValue()){
            AmbiguousValue ambiguousValue = destRegister.getAmbiguousValue();
            return new MethodExecutionResult(ambiguousValue,
                    methodExecution.getSmaliMethod());
        }
        // boolean
        else if(returnType.equals("Z")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }
            Boolean res = destRegister.getIntValue() % 2 == 1;
            return new MethodExecutionResult(res, methodExecution.getSmaliMethod());
        }
        // byte
        else if(returnType.equals("B")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }
            Byte b = (byte) destRegister.getIntValue();
            return new MethodExecutionResult(b, methodExecution.getSmaliMethod());
        }
        // short
        else if(returnType.equals("S")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }

            Short s = (short) destRegister.getIntValue();
            return new MethodExecutionResult(s, methodExecution.getSmaliMethod());
        }
        // char
        else if(returnType.equals("C")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }
            Character c = (char) destRegister.getIntValue();
            return new MethodExecutionResult(c, methodExecution.getSmaliMethod());
        }
        // int
        else if(returnType.equals("I")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }
            Integer res = methodExecution.getRegister(destRegisterNumber).getIntValue();
            return new MethodExecutionResult(res, methodExecution.getSmaliMethod());
        }
        // float
        else if(returnType.equals("F")){
            if(type != ReturnInstructionType.NORMAL){
                throw new SmaliSimulationException();
            }
            Float res = destRegister.getFloatValue();
            return new MethodExecutionResult(res, methodExecution.getSmaliMethod());
        }
        // long
        else if(returnType.equals("J")){
            if(type != ReturnInstructionType.RETURN_WIDE){
                throw new SmaliSimulationException();
            }
            Long res = destRegister.getLongValue();
            return new MethodExecutionResult(res, methodExecution.getSmaliMethod());
        }
        // double
        else if(returnType.equals("D")){
            if(type != ReturnInstructionType.RETURN_WIDE){
                throw new SmaliSimulationException();
            }
            Double res = destRegister.getDoubleValue();
            return new MethodExecutionResult(res, methodExecution.getSmaliMethod());
        }
        // Object
        else if(returnType.startsWith("L") || returnType.startsWith("[")){
            if(type != ReturnInstructionType.RETURN_OBJECT){
                throw new SmaliSimulationException();
            }

            AbstractObjekt so = destRegister.getReferencedObjectValue();
            return new MethodExecutionResult(so, ResultType.OBJECT, methodExecution.getSmaliMethod());
        }
        // Invalid cases
        else {
            throw new SmaliSimulationException("Illegal Method Return Type");
        }
    }
}
