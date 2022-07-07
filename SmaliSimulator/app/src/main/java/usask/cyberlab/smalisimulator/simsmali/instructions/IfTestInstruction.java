package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction22t;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class IfTestInstruction extends BranchInstruction{

    private final IfTestInstructionType type;
    private final int registerANumber;
    private final int registerBNumber;

    public enum IfTestInstructionType{
        IF_EQ,
        IF_NE,
        IF_LE,
        IF_LT,
        IF_GE,
        IF_GT,
    }

    public IfTestInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction22t)){
            throw new SmaliSimulationException();
        }
        String opCodeName = instructionDef.getOpcode().name;
        switch (opCodeName) {
            case "if-eq":
                this.type = IfTestInstructionType.IF_EQ;
                break;
            case "if-ne":
                this.type = IfTestInstructionType.IF_NE;
                break;
            case "if-lt":
                this.type = IfTestInstructionType.IF_LT;
                break;
            case "if-le":
                this.type = IfTestInstructionType.IF_LE;
                break;
            case "if-ge":
                this.type = IfTestInstructionType.IF_GE;
                break;
            case "if-gt":
                this.type = IfTestInstructionType.IF_GT;
                break;
            default:
                throw new SmaliSimulationException();
        }
        BuilderInstruction22t bi = (BuilderInstruction22t) instructionDef;
        this.registerANumber = bi.getRegisterA();
        this.registerBNumber = bi.getRegisterB();
    }


    @Override
    protected void execute(MethodExecution methodExecution) {
        Register rA = methodExecution.getRegister(registerANumber);
        Register rB = methodExecution.getRegister(registerBNumber);

        if(rA.containsAmbiguousValue() || rB.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(0,1);
            if(r==0){
                int jumpedPos = this.getTargetLocation();
                methodExecution.setInstructionPointer(jumpedPos);

                // in case of if-eq instruction if one register is ambiguous value and
                // the other one is constant number, we can simplify the ambiguous value to the number
                if(type == IfTestInstructionType.IF_EQ){
                    if(rA.containsAmbiguousValue() && rB.containsNumericData()){
                        rA.set(rB.getIntValue());
                    }
                    else if(rA.containsNumericData() && rB.containsAmbiguousValue()){
                        rB.set(rA.getIntValue());
                    }
                }
            }
            else {
                methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
            }
        }
        else {
            if(type == IfTestInstructionType.IF_EQ){
                boolean c;
                if(rA.containsNumericData() && rB.containsNumericData()){
                    int a = rA.getIntValue();
                    int b = rB.getIntValue();
                    c = a == b;
                }
                else {
                    AbstractObjekt a = rA.getReferencedObjectValue();
                    AbstractObjekt b = rB.getReferencedObjectValue();
                    boolean aIsNull = a == null;
                    boolean bIsNull = b == null;
                    // both null
                    if(aIsNull && bIsNull){
                        c = true;
                    }
                    // one is null, the other one is not
                    else if(aIsNull ^ bIsNull){
                        c = false;
                    }
                    else {
                        c = a.equals(b);
                    }
                }
                if(c){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestInstructionType.IF_NE){
                boolean c;
                if(rA.containsNumericData() && rB.containsNumericData()){
                    int a = rA.getIntValue();
                    int b = rB.getIntValue();
                    c = a != b;
                }
                else {
                    AbstractObjekt a = rA.getReferencedObjectValue();
                    AbstractObjekt b = rB.getReferencedObjectValue();
                    boolean aIsNull = a == null;
                    boolean bIsNull = b == null;
                    // both null
                    if(aIsNull && bIsNull){
                        c = false;
                    }
                    // one is null, the other one is not
                    else if(aIsNull ^ bIsNull){
                        c = true;
                    }
                    // both not null
                    else {
                        c = !a.equals(b);
                    }

                }
                if(c){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestInstructionType.IF_LE){
                int a = rA.getIntValue();
                int b = rB.getIntValue();
                if(a <= b){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestInstructionType.IF_LT){
                int a = rA.getIntValue();
                int b = rB.getIntValue();
                if(a < b){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestInstructionType.IF_GE){
                int a = rA.getIntValue();
                int b = rB.getIntValue();
                if(a >= b){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestInstructionType.IF_GT){
                int a = rA.getIntValue();
                int b = rB.getIntValue();
                if(a > b){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else {
                throw new SmaliSimulationException("Invalid instruction type! " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    @Override
    public String toString() {
        //if-test vA, vB, +CCCC
        return this.opCode + " v" + registerANumber
                + " v"+ registerBNumber + " @" +
                this.getTargetLocation() + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(registerANumber);
        Register rB = methodExecution.getRegister(registerBNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerANumber, getContentInfo(rA));
        jo.put("v" + registerBNumber, getContentInfo(rB));
        return jo.toString();
    }
}