package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class CmpOperationInstruction extends SmaliInstruction{

    public enum CmpOperationType{
        CMPL_FLOAT,
        CMPG_FLOAT,
        CMPL_DOUBLE,
        CMPG_DOUBLE,
        CMP_LONG,
    }

    private final int registerDestNumber;
    private final int registerSrc1Number;
    private final int registerSrc2Number;
    private final CmpOperationType type;

    public CmpOperationInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction23x)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction23x bi = (BuilderInstruction23x) instructionDef;
        registerDestNumber = bi.getRegisterA();
        registerSrc1Number = bi.getRegisterB();
        registerSrc2Number = bi.getRegisterC();
        String opCode = instructionDef.getOpcode().name;
        switch (opCode) {
            case "cmpl-float":
                type = CmpOperationType.CMPL_FLOAT;
                break;
            case "cmpg-float":
                type = CmpOperationType.CMPG_FLOAT;
                break;
            case "cmpl-double":
                type = CmpOperationType.CMPL_DOUBLE;
                break;
            case "cmpg-double":
                type = CmpOperationType.CMPG_DOUBLE;
                break;
            case "cmp-long":
                type = CmpOperationType.CMP_LONG;
                break;
            default:
                throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register dest = methodExecution.getRegister(registerDestNumber);
        Register src1 = methodExecution.getRegister(registerSrc1Number);
        Register src2 = methodExecution.getRegister(registerSrc2Number);
        if(src1.containsAmbiguousValue() || src2.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(-1, 1);
            if(r == 0){
                if(src1.containsNumericData() &&
                        src2.containsAmbiguousValue()){
                    copyNumericValue(src1, src2);
                }
                else if(src1.containsAmbiguousValue() &&
                        src2.containsNumericData()){
                    copyNumericValue(src2, src1);
                }
            }
            dest.set(r);
            return;
        }
        if(type ==  CmpOperationType.CMPL_FLOAT || type ==  CmpOperationType.CMPG_FLOAT){
            float f1 = src1.getFloatValue();
            float f2 = src2.getFloatValue();
            if(Float.isNaN(f1) || Float.isNaN(f2)){
                if(type ==  CmpOperationType.CMPG_FLOAT) {
                    dest.set(1);
                }
                else {
                    dest.set(-1);
                }
            }
            else if(f1 > f2) {
                dest.set(1);
            }
            else if(f1 == f2){
                dest.set(0);
            }
            else {
                dest.set(-1);
            }
        }
        else if(type ==  CmpOperationType.CMPL_DOUBLE || type ==  CmpOperationType.CMPG_DOUBLE){
            double d1 = src1.getDoubleValue();
            double d2 = src2.getDoubleValue();
            if(Double.isNaN(d1) || Double.isNaN(d2)){
                if(type ==  CmpOperationType.CMPG_DOUBLE) {
                    dest.set(1);
                }
                else {
                    dest.set(-1);
                }
            }
            else if(d1 > d2){
                dest.set(1);
            }
            else if(d1 == d2){
                dest.set(0);
            }
            else {
                dest.set(-1);
            }
        }
        else if(type ==  CmpOperationType.CMP_LONG){
            long l1 = src1.getLongValue();
            long l2 = src2.getLongValue();
            if(l1 > l2){
                dest.set(1);
            }
            else if(l1 == l2){
                dest.set(0);
            }
            else {
                dest.set(-1);
            }
        }
        else {
            throw new SmaliSimulationException("Invalid instruction type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
        }
    }

    @Override
    public String toString() {
        //cmpkind vAA, vBB, vCC
        return this.opCode + " v" + registerDestNumber + " v"
                + registerSrc1Number + " v" + registerSrc2Number + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register dest = methodExecution.getRegister(registerDestNumber);
        Register src1 = methodExecution.getRegister(registerSrc1Number);
        Register src2 = methodExecution.getRegister(registerSrc2Number);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerDestNumber, getContentInfo(dest));
        jo.put("v" + registerSrc1Number, getContentInfo(src1));
        jo.put("v" + registerSrc2Number, getContentInfo(src2));
        return jo.toString();
    }

    private void copyNumericValue(Register src, Register dest){
        if(type.equals(CmpOperationType.CMP_LONG)){
            dest.set(src.getLongValue());
        }
        else if(type.equals(CmpOperationType.CMPG_DOUBLE) || type.equals(CmpOperationType.CMPL_DOUBLE)){
            dest.set(src.getDoubleValue());
        }
        else {
            dest.set(src.getFloatValue());
        }

    }
}
