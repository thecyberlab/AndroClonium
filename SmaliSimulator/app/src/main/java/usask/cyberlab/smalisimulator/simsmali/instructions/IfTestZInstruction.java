package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.builder.instruction.BuilderInstruction21t;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class IfTestZInstruction extends BranchInstruction{

    private final IfTestZInstructionType type;
    private final int registerANumber;

    public enum IfTestZInstructionType{
        IF_EQZ,
        IF_NEZ,
        IF_LEZ,
        IF_LTZ,
        IF_GEZ,
        IF_GTZ,
    }

    public IfTestZInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction21t)){
            throw new SmaliSimulationException();
        }

        String opCodeName = instructionDef.getOpcode().name;
        switch (opCodeName) {
            case "if-eqz":
                this.type = IfTestZInstructionType.IF_EQZ;
                break;
            case "if-nez":
                this.type = IfTestZInstructionType.IF_NEZ;
                break;
            case "if-ltz":
                this.type = IfTestZInstructionType.IF_LTZ;
                break;
            case "if-lez":
                this.type = IfTestZInstructionType.IF_LEZ;
                break;
            case "if-gez":
                this.type = IfTestZInstructionType.IF_GEZ;
                break;
            case "if-gtz":
                this.type = IfTestZInstructionType.IF_GTZ;
                break;
            default:
                throw new SmaliSimulationException();
        }
        BuilderInstruction21t bi = (BuilderInstruction21t) instructionDef;
        this.registerANumber = bi.getRegisterA();
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register rA = methodExecution.getRegister(registerANumber);
        if(rA.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(0, 1);
            if(r==0){
                int jumpedPos = this.getTargetLocation();
                methodExecution.setInstructionPointer(jumpedPos);
                // if instruction is if-eqz and we are testing an Ambiguous value then if
                // test says equal we can simplify the value of Ambiguous value to zero
                if(type.equals(IfTestZInstructionType.IF_EQZ)){
                    rA.set(0);
                }
            }
            else {
                methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
            }
        }
        else {
            int a;
            if(type == IfTestZInstructionType.IF_EQZ){
                // null check for objects is also done by IF_EQZ and IF_NEQ
                // if the rA points to an object means that rA is non zero and
                // we need to not take the jump
                if(rA.containsNumericData()){
                    a = rA.getIntValue();
                    if(a==0){
                        methodExecution.setInstructionPointer(this.getTargetLocation());
                    }
                    else{
                        methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                    }
                }else {
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestZInstructionType.IF_NEZ){
                // null check for objects is also done by IF_EQZ and IF_NEQ
                // if the rA points to an object means that rA is non zero and
                // we need to take the jump
                if(rA.containsNumericData()){
                    a = rA.getIntValue();
                    if(a!=0){
                        methodExecution.setInstructionPointer(this.getTargetLocation());
                    }
                    else{
                        methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                    }
                }else{
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
            }
            else if(type == IfTestZInstructionType.IF_LEZ){
                a = rA.getIntValue();
                if(a <= 0){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestZInstructionType.IF_LTZ){
                a = rA.getIntValue();
                if(a < 0){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestZInstructionType.IF_GEZ){
                a = rA.getIntValue();
                if(a >= 0){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else if(type == IfTestZInstructionType.IF_GTZ){
                a = rA.getIntValue();
                if(a > 0){
                    methodExecution.setInstructionPointer(this.getTargetLocation());
                }
                else{
                    methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
                }
            }
            else {
                throw new SmaliSimulationException("Invalid instruction type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    @Override
    public String toString() {
        //if-testz vAA, +BBBB
        return this.opCode + " v" + registerANumber
                + " @" + this.getTargetLocation()
                + " &" + instructionPositionNumber ;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(registerANumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerANumber, getContentInfo(rA));
        return jo.toString();
    }
}
