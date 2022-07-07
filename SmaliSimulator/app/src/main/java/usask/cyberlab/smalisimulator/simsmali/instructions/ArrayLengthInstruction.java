package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class ArrayLengthInstruction extends SmaliInstruction {
    private final int arrayRegisterNumber;
    private final int destRegisterNumber;

    public ArrayLengthInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction12x)){
            throw new SmaliSimulationException("Bad InstructionDef!");
        }
        BuilderInstruction12x bi = (BuilderInstruction12x) instructionDef;
        this.arrayRegisterNumber = bi.getRegisterB();
        this.destRegisterNumber = bi.getRegisterA();
    }

    @Override
    protected void execute(MethodExecution methodExecution){
        Register arrayReg = methodExecution.getRegister(arrayRegisterNumber);
        Register destReg = methodExecution.getRegister(destRegisterNumber);

        // if reference object is an ambiguous value then the length of array
        // is ambiguous
        if(arrayReg.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(0,1);
            if(SimSmaliConfig.canThrowNullPointerExceptionOnAmbiguity && r==0){
//                throwNullPointerExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, NullPointerException.class);
            }
            else {
                destReg.set(new AmbiguousValue("I"));
            }
            return;
        }
        else {
            AbstractObjekt o = arrayReg.getReferencedObjectValue();
            if(o == null){
//                throwNullPointerExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, NullPointerException.class);
                return;
            }
            else if (o instanceof ArrayObjekt) {
                ArrayObjekt array = (ArrayObjekt) o;
                destReg.set(array.getSize());
                return;
            } else {
                throw new SmaliSimulationException("Referenced object is not an array! " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    @Override
    public String toString() {
        //array-length vA, vB
        return this.opCode + " v" +
                destRegisterNumber+ " v"+ arrayRegisterNumber + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        JSONObject jo = new JSONObject();
        Register arrayReg = methodExecution.getRegister(arrayRegisterNumber);
        jo.put("v" + arrayRegisterNumber, getContentInfo(arrayReg));
        return jo.toString();
    }
}
