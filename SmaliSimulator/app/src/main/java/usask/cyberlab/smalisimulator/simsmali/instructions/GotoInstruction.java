package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.builder.instruction.BuilderInstruction10t;
import org.jf.dexlib2.builder.instruction.BuilderInstruction20t;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;

public class GotoInstruction extends BranchInstruction {

    final GotoInstructionType type;

    public enum GotoInstructionType{
        SHORT,
        MEDIUM,
        LONG
    }

    public GotoInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if (instructionDef instanceof BuilderInstruction10t){
            this.type = GotoInstructionType.SHORT;
        }
        else if(instructionDef instanceof BuilderInstruction20t){
            this.type = GotoInstructionType.MEDIUM;
        }
        else {
            this.type = GotoInstructionType.LONG;
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        int newIndex = this.getTargetLocation();
        methodExecution.setInstructionPointer(newIndex);
    }

    @Override
    public String toString() {
        return this.opCode + " @" +
                this.getTargetLocation() + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        return "";
    }
}
