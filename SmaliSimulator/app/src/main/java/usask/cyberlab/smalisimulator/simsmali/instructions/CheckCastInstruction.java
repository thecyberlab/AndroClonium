package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class CheckCastInstruction extends SmaliInstruction{

    private final int srcRegisterNumber;
    private final String checkingType;

    public CheckCastInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction21c)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction21c bi = (BuilderInstruction21c) instructionDef;
        this.srcRegisterNumber = bi.getRegisterA();
        this.checkingType = bi.getReference().toString();
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register reg = methodExecution.getRegister(srcRegisterNumber);
        // if we are checking object type against primitive types
        if(SimulationUtils.isPrimitiveType(checkingType)){
//            throwClassCastExceptionToMethodExecution(methodExecution);
            throwExceptionOn(methodExecution, ClassCastException.class);
            return;
        }

        if(reg.containsAmbiguousValue()){
            String ambiguousValueType = reg.getAmbiguousValue().getType();
            if(!ambiguousValueType.equals(checkingType)){
                Clazz toCheckClazz = methodExecution.getClazzLoader().getClazz(checkingType);
                // when toCheckType is subclass of ambiguous value type
                // e.g. AmbiguousValue::Ljava/util/List; toCheckType::Ljava/util/ArrayList;
                // in this case it is possible that this instruction would throw ClassCastException
                // but this can lead to path explosion, so we look at config and we do so
                // if such behaviour is desired
                if(toCheckClazz.isSubTypeOf(ambiguousValueType)){
                    int r = SimulationUtils.getRandomNumberInRange(0, 1);
                    if(SimSmaliConfig.canThrowCheckCastExceptionOnAmbiguity && r==0){
//                        throwClassCastExceptionToMethodExecution(methodExecution);
                        throwExceptionOn(methodExecution, ClassCastException.class);
                    }
                    else {
                        reg.getAmbiguousValue().setType(checkingType);
                    }
                    return;
                }

                // when toCheckType is not super class of ambiguous value type
                // e.g. AmbiguousValue::Ljava/util/List; toCheckType::Ljava/lang/Integer;
                else if(!toCheckClazz.isSuperTypeOf(ambiguousValueType)){
//                    throwClassCastExceptionToMethodExecution(methodExecution);
                    throwExceptionOn(methodExecution, ClassCastException.class);
                    return;
                }
            }
        }
        else {
            AbstractObjekt so = reg.getReferencedObjectValue();
            if (so != null) {
                boolean b = so.isInstanceOf(checkingType);
                if (!b) {
//                    throwClassCastExceptionToMethodExecution(methodExecution);
                    throwExceptionOn(methodExecution, ClassCastException.class);
                    return;
                }
            }
        }
    }

//    private void throwClassCastExceptionToMethodExecution(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/ClassCastException;");
//        ClassCastException e = new ClassCastException();
//        e.setStackTrace(new StackTraceElement[0]);
//        Objekt exceptionObjekt = new Objekt(clazz, clazz.getClassPath(), e);
//        methodExecution.setThrownException(exceptionObjekt);
//    }

    @Override
    public String toString() {
        // check-cast vAA, type@BBBB
        return this.opCode + " v" + srcRegisterNumber
                + " " + checkingType + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register reg = methodExecution.getRegister(srcRegisterNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + srcRegisterNumber, getContentInfo(reg));
        return jo.toString();
    }

    public String getCheckingType() {
        return checkingType;
    }
}
