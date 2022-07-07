package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class NewInstanceInstruction extends SmaliInstruction {
    private final String classPath;
    private final int destRegNumber;

    public NewInstanceInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction21c){
            BuilderInstruction21c bi = (BuilderInstruction21c) instructionDef;
            this.destRegNumber =  bi.getRegisterA();
            this.classPath = bi.getReference().toString();
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        if(classPath.startsWith("[")){
            throw new SmaliSimulationException("invalid way to create new array? " +
                    classPath + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
        }
        Clazz clazz = methodExecution.getClazzLoader().getClazz(classPath);
        Objekt objekt = new Objekt(clazz);
        methodExecution.getRegister(destRegNumber).set(objekt);
    }

    @Override
    public String toString() {
        //new-instance vAA, type@BBBB
        return this.opCode + " v" + destRegNumber + " "
                + classPath + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register r = methodExecution.getRegister(destRegNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + destRegNumber, getContentInfo(r));
        return jo.toString();
    }

    public String getToCreateTypeClassPath(){
        return classPath;
    }
}
