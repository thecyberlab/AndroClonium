package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.BuilderOffsetInstruction;
import org.jf.dexlib2.builder.MethodLocation;
import org.jf.dexlib2.iface.instruction.Instruction;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public abstract class BranchInstruction extends SmaliInstruction {
    protected final int targetLocationIndex;

    public BranchInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        BuilderOffsetInstruction offsetInstruction = (BuilderOffsetInstruction) instructionDef;
        this.targetLocationIndex = offsetInstruction.getTarget().getLocation().getIndex();
    }


    @Override
    public void executeWrapper(MethodExecution methodExecution) {
        methodExecution.addToExecutionTrace(this);
        try {
            execute(methodExecution);
        }
        catch (SmaliSimulationException e){
            throw e;
        }
        catch (RuntimeException e){
            throw new SmaliSimulationException(e);
        }


    }

    public int getTargetLocation(){
        return targetLocationIndex;
    }
}
