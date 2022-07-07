package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.builder.BuilderSwitchPayload;
import org.jf.dexlib2.builder.MethodLocation;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31t;
import org.jf.dexlib2.builder.instruction.BuilderSwitchElement;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class SwitchInstruction extends BranchInstruction {

    private final int registerToTestNumber;
    private final SwitchElement[] switchElements;

    public class SwitchElement{
        int targetLocationIndex;
        int value;
        SwitchElement(int value, int targetLocationIndex){
            this.value = value;
            this.targetLocationIndex = targetLocationIndex;
        }

        public int getTargetLocationIndex(){
            return targetLocationIndex;
        }

        public int getValue(){
            return value;
        }
    }

    public SwitchInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction31t)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction31t bi = (BuilderInstruction31t) instructionDef;
        this.registerToTestNumber = bi.getRegisterA();
        MethodLocation ml = bi.getTarget().getLocation();
        BuilderSwitchPayload switchPayload = (BuilderSwitchPayload) ml.getInstruction();
        List<BuilderSwitchElement> l = (List<BuilderSwitchElement>) switchPayload.getSwitchElements();
        this.switchElements = new SwitchElement[l.size()];
        for (int i = 0; i < switchElements.length; i++) {
            BuilderSwitchElement bse = l.get(i);
            switchElements[i] = new SwitchElement(bse.getKey(),bse.getTarget().getLocation().getIndex());
        }

    }


    @Override
    protected void execute(MethodExecution methodExecution) {
        Register reg = methodExecution.getRegister(registerToTestNumber);
        if(reg.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(0, switchElements.length);
            if(r== switchElements.length){
                methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
            }
            else {
                reg.set(switchElements[r].value);
               methodExecution.setInstructionPointer(switchElements[r].targetLocationIndex);
            }
        }
        else {
            for(SwitchElement se : switchElements) {
                if (reg.getIntValue() == se.value) {
                    methodExecution.setInstructionPointer(se.targetLocationIndex);
                    return;
                }
            }
            methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
        }
    }

    @Override
    public String toString() {
        //packed-switch vAA, +BBBBBBBB
        //sparse-switch vAA, +BBBBBBBB
        int[] a  = new int[switchElements.length];
        for (int i = 0; i < switchElements.length; i++) {
            a[i] = switchElements[i].value;
        }
        return this.opCode + " v" + registerToTestNumber + " " +
                Arrays.toString(a) + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(registerToTestNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerToTestNumber, getContentInfo(rA));
        return jo.toString();
    }

    public SwitchElement[] getSwitchElements(){
        return switchElements.clone();
    }
}