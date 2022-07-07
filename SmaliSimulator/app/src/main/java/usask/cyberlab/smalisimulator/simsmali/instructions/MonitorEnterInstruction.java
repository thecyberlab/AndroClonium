package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;

public class MonitorEnterInstruction extends SmaliInstruction {

    public MonitorEnterInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        // since I do not  handle multithreading this is basically a nope
    }

    @Override
    public String toString() {
        return this.opCode + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        return "";
    }

}
