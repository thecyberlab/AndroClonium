package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;


public class FilledNewArrayInstruction extends SmaliInstruction{
    private final int[] registerNumbers ;
    private final String arrayType;

    public FilledNewArrayInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction35c){
            BuilderInstruction35c bi = (BuilderInstruction35c) instructionDef;
            int[] tmp = new int[5];
            tmp[0] = bi.getRegisterC();
            tmp[1] = bi.getRegisterD();
            tmp[2] = bi.getRegisterE();
            tmp[3] = bi.getRegisterF();
            tmp[4] = bi.getRegisterG();
            registerNumbers = new int[bi.getRegisterCount()];
            if (registerNumbers.length >= 0) System.arraycopy(tmp, 0, registerNumbers, 0, registerNumbers.length);
            arrayType = bi.getReference().toString();
        }
        else if(instructionDef instanceof BuilderInstruction3rc){
            BuilderInstruction3rc bi = (BuilderInstruction3rc) instructionDef;
            registerNumbers = new int[bi.getRegisterCount()];
            for(int i =0; i< registerNumbers.length; i++){
                registerNumbers[i] = i + bi.getStartRegister();
            }
            arrayType = bi.getReference().toString();
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        ArrayObjekt sa;

        // I have only seen [I and [java/lang/String; so this code should be fine
        if(SimulationUtils.isJavaOrAndroidExistingClass(arrayType)){
            ReflectedClazz rc = (ReflectedClazz) loader.getClazz(arrayType);
            try {
                Object newArray = SimulationUtils.createArray(arrayType.substring(1), registerNumbers.length);
                sa = new ArrayObjekt(rc, newArray);
                for(int i=0; i < registerNumbers.length; i++){
                    Register r = methodExecution.getRegister(registerNumbers[i]);
                    if(arrayType.length() > 2){
                        sa.setValue(i ,r.getReferencedObjectValue());
                    }
                    else {
                        sa.setValue(i ,r.getIntValue());
                    }
                }
                MethodExecutionResult newResult = new MethodExecutionResult(sa, ResultType.OBJECT, null);
                methodExecution.setInvokedFunctionExecutionResult(newResult);
            } catch (ClassNotFoundException e) {
                throw new SmaliSimulationException(e);
            }
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.opCode);
        for (int i:registerNumbers) {
            sb.append(" v");
            sb.append(i);
        }
        sb.append(" ");
        sb.append(arrayType);
        sb.append(" &");
        sb.append(instructionPositionNumber);
        return sb.toString();
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        JSONObject jo = new JSONObject();
        for(int rNum: registerNumbers) {
            jo.put("v" + rNum, getContentInfo(methodExecution.getRegister(rNum)));
        }
        return jo.toString();
    }
}