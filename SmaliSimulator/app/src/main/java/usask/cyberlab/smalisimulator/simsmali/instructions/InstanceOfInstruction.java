package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class InstanceOfInstruction extends SmaliInstruction {
    private final int srcRegNumber;
    private final int destRegNumber;
    private final String classPathString;

    public InstanceOfInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction22c)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction22c bi = (BuilderInstruction22c) instructionDef;
        this.srcRegNumber = bi.getRegisterB();
        this.destRegNumber = bi.getRegisterA();
        this.classPathString = bi.getReference().toString();
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register src_reg = methodExecution.getRegister(srcRegNumber);
        Register dest_reg = methodExecution.getRegister(destRegNumber);
        if(src_reg.containsAmbiguousValue()){
            String ambiguousValueType = src_reg.getAmbiguousValue().getType();

            if(ambiguousValueType.equals(classPathString)){
                dest_reg.set(1);
                return;
            }
            else {
                Clazz toCheckClazz = methodExecution.getClazzLoader().getClazz(classPathString);
                // when toCheckType is subclass of ambiguous value type
                // e.g. AmbiguousValue::Ljava/util/List; toCheckType::Ljava/util/ArrayList;
                // in this case if the
                if(toCheckClazz.isSubTypeOf(ambiguousValueType)){
                    int r = SimulationUtils.getRandomNumberInRange(0, 1);
                    if(r==0){
                        dest_reg.set(0);
                        return;
                    }
                    else {
                        src_reg.getAmbiguousValue().setType(classPathString);
                        dest_reg.set(1);
                        return;
                    }

                }
                // when toCheckType is super class of ambiguous value type
                // e.g. AmbiguousValue::Ljava/util/ArrayList; toCheckType::Ljava/util/List;
                else if(toCheckClazz.isSuperTypeOf(ambiguousValueType)){
                    dest_reg.set(1);
                    return;
                }
                // when toCheckType is not super class of ambiguous value type
                // e.g. AmbiguousValue::Ljava/util/List; toCheckType::Ljava/lang/Integer;
                else{
                    dest_reg.set(0);
                    return;
                }
            }
        }
        else if(src_reg.containsNumericData()){
            dest_reg.set(0);
            return;
        }
        else {
            AbstractObjekt objekt = src_reg.getReferencedObjectValue();
            if (objekt.isInstanceOf(classPathString)) {
                dest_reg.set(1);
                return;
            } else {
                dest_reg.set(0);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return this.opCode + " v" + destRegNumber + " v"
                + srcRegNumber + " " + classPathString + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(srcRegNumber);
        Register rB = methodExecution.getRegister(destRegNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + srcRegNumber, getContentInfo(rA));
        jo.put("v" + destRegNumber, getContentInfo(rB));
        return jo.toString();
    }
    public String getClassPathString(){
        return classPathString;
    }
}