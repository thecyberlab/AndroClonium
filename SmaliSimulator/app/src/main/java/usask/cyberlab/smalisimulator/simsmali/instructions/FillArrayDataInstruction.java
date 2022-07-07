package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderArrayPayload;
import org.jf.dexlib2.builder.instruction.BuilderInstruction31t;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;

public class FillArrayDataInstruction extends SmaliInstruction {

    private final int arrayReferenceRegisterNumber;
    private final List<Number> arrayData;

    public FillArrayDataInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction31t)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction31t bi = (BuilderInstruction31t) instructionDef;
        arrayReferenceRegisterNumber = bi.getRegisterA();
        BuilderArrayPayload bap = (BuilderArrayPayload) bi.getTarget().getLocation().getInstruction();
        arrayData = bap.getArrayElements();
    }

    private boolean getByteBoolVal(byte b){
        if(b == 1){
            return true;
        }
        else if( b == 0) {
            return false;
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        AbstractObjekt o = methodExecution.getRegister(arrayReferenceRegisterNumber).getReferencedObjectValue();
        if(!(o instanceof ArrayObjekt)){
            throw new SmaliSimulationException();
        }
        ArrayObjekt sa = (ArrayObjekt) methodExecution.getRegister(arrayReferenceRegisterNumber).getReferencedObjectValue();
        String arrayType = sa.getType();
        for (int i=0; i<arrayData.size(); i++) {
            Number n = arrayData.get(i);
            if(arrayType.equals("[I")){
                sa.setValue(i, n.intValue());
            }
            else if(arrayType.equals("[F")){
                sa.setValue(i, Float.intBitsToFloat(n.intValue()));
            }
            else if(arrayType.equals("[S")){
                sa.setValue(i, n.shortValue());
            }
            else if(arrayType.equals("[B")){
                sa.setValue(i,n.byteValue());
            }
            else if(arrayType.equals("[J")){
                sa.setValue(i,n.longValue());
            }
            else if(arrayType.equals("[Z")){
                sa.setValue(i,getByteBoolVal(n.byteValue()));
            }
            else if(arrayType.equals("[D")){
                sa.setValue(i,Double.longBitsToDouble(n.longValue()));
            }
            else if(arrayType.equals("[C")){
                sa.setValue(i,(char) n.shortValue());
            }
            else {
                throw new SmaliSimulationException("Invalid instruction variant! " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    @Override
    public String toString() {
        // fill-array-data vAA, +BBBBBBBB
        return this.opCode + " v"+ arrayReferenceRegisterNumber
                + " "+ arrayData.toString() + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register reg = methodExecution.getRegister(arrayReferenceRegisterNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + arrayReferenceRegisterNumber, getContentInfo(reg));
        return jo.toString();
    }
}
