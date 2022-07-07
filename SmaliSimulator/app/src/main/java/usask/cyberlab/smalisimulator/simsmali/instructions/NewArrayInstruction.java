package usask.cyberlab.smalisimulator.simsmali.instructions;


import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class NewArrayInstruction extends SmaliInstruction{
    private final int destRegisterNumber;
    private final int sizeRegisterNumber;
    private final String arrayType;

    public NewArrayInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction22c){
            BuilderInstruction22c bi = (BuilderInstruction22c) instructionDef;
            destRegisterNumber = bi.getRegisterA();
            sizeRegisterNumber = bi.getRegisterB();
            arrayType = bi.getReference().toString();
        }
        else {
            throw new SmaliSimulationException("Bad InstructionDef");
        }
    }


    //TODO add more tests for this instruction
    @Override
    protected void execute(MethodExecution methodExecution) {
        ClazzLoader loader = methodExecution.getClazzLoader();
        Register sizeRegister = methodExecution.getRegister(sizeRegisterNumber);
        Register destRegister = methodExecution.getRegister(destRegisterNumber);
        if(sizeRegister.containsAmbiguousValue()){
            destRegister.set(new AmbiguousValue(arrayType));
        }
        else {
            int size = sizeRegister.getIntValue();
            Clazz arrayClazz;
            Clazz componentClazz;
            try {
                arrayClazz = loader.getClazz(arrayType);
                componentClazz = loader.getClazz(arrayType.substring(1));
            }
            catch (RuntimeException e){
                if(SimSmaliConfig.onClassLoadErrorUseAmbiguousValue){
                    destRegister.set(new AmbiguousValue(arrayType));
                    return;
                }
                else {
                    throw new SmaliSimulationException(e);
                }
            }
            if(size < 0){
//                throwNegativeArraySizeExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, NegativeArraySizeException.class);
                return;
            }
            Object o = Array.newInstance(componentClazz.getMirroringClass(), size);
            ArrayObjekt sa = new ArrayObjekt(arrayClazz, o);
            destRegister.set(sa);
        }
    }

    @Override
    public String toString() {
        // new-array vA, vB, type@CCCC
        return this.opCode + " v" + destRegisterNumber + " v"
                + sizeRegisterNumber + " " + arrayType + " &" + instructionPositionNumber;
    }


//    protected static void throwNegativeArraySizeExceptionOn(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/NegativeArraySizeException;");
//        NegativeArraySizeException e = new NegativeArraySizeException();
//        e.setStackTrace(new StackTraceElement[0]);
//        Objekt exceptionObjekt = new Objekt(clazz, clazz.getClassPath(), e);
//        methodExecution.setThrownException(exceptionObjekt);
//    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(sizeRegisterNumber);
        Register rB = methodExecution.getRegister(destRegisterNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + sizeRegisterNumber, getContentInfo(rA));
        jo.put("v" + destRegisterNumber, getContentInfo(rB));
        return jo.toString();
    }

    public String getArrayType(){
        return arrayType;
    }
}
