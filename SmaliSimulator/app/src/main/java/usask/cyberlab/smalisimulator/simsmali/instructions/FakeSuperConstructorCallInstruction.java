package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.json.JSONException;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class FakeSuperConstructorCallInstruction extends SmaliInstruction{

    @Override
    protected void execute(MethodExecution methodExecution) {
        throw new SmaliSimulationException();
    }

    @Override
    public String toString() {
        return "FakeSuperConstructorCallInstruction!!";
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        throw new SmaliSimulationException();
    }
}
