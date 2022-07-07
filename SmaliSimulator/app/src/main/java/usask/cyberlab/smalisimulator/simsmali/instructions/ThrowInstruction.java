package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ThrowInstruction extends SmaliInstruction {

    private final int exceptionObjectRegisterNumber;

    public ThrowInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction11x)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction11x bi = (BuilderInstruction11x) instructionDef;
        this.exceptionObjectRegisterNumber = bi.getRegisterA();
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register r = methodExecution.getRegister(exceptionObjectRegisterNumber);
        if(r.containsAmbiguousValue()){
            AmbiguousValue av = r.getAmbiguousValue();
            methodExecution.setThrownException(av);
        }
        else {
            Objekt so = (Objekt) r.getReferencedObjectValue();
            if(so == null){
//                throwNullPointerExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, NullPointerException.class);
            }
            else if (so.isInstanceOf("Ljava/lang/Throwable;")) {
                methodExecution.setThrownException(so);
            } else {
                throw new SmaliSimulationException("Invalid object set as Exception " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    @Override
    public String toString() {
        //throw vAA
        return this.opCode +" v" + exceptionObjectRegisterNumber
                + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(exceptionObjectRegisterNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + exceptionObjectRegisterNumber, getContentInfo(rA));
        return jo.toString();
    }
}
