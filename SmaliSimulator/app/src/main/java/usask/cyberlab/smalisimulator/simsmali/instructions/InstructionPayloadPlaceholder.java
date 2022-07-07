package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class InstructionPayloadPlaceholder extends SmaliInstruction{

    public InstructionPayloadPlaceholder(Instruction instructionDef, int instructionPositionNumber){
        super(instructionDef, instructionPositionNumber);
    }

    @Override
    public void executeWrapper(MethodExecution methodExecution){
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        throw new SmaliSimulationException();
    }

    @Override
    public String toString() {
        return this.opCode + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        throw new SmaliSimulationException();
    }
}
