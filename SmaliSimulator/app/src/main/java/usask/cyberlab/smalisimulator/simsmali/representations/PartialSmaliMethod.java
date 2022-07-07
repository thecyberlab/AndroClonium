package usask.cyberlab.smalisimulator.simsmali.representations;


import java.util.ArrayList;

import usask.cyberlab.smalisimulator.simsmali.instructions.FakeSuperConstructorCallInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;

public class PartialSmaliMethod extends SmaliMethod{

    private final ArrayList<SmaliInstruction> instructionsListWithoutSuperConstructorCall;
    private final int superConstructorCallPos;

    public PartialSmaliMethod(SmaliMethod sm, int superConstructorPos){
        super(sm);
        superConstructorCallPos = superConstructorPos;
        instructionsListWithoutSuperConstructorCall = (ArrayList<SmaliInstruction>) sm.getInstructionList().clone();
        instructionsListWithoutSuperConstructorCall.set(superConstructorPos, new FakeSuperConstructorCallInstruction());
    }

    public ArrayList<SmaliInstruction> getSmaliInstructionListWithoutSuperConstructorCall() {
        return instructionsListWithoutSuperConstructorCall;
    }

    public int getSuperConstructorCallPos(){
        return superConstructorCallPos;
    }
}
