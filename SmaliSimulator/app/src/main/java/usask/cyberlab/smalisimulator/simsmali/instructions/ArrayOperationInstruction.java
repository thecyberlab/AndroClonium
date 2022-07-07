package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class ArrayOperationInstruction extends SmaliInstruction {
    private final int registerANumber;
    private final int registerBNumber;
    private final int registerCNumber;
    private final ArrayOperationType type;

    public enum ArrayOperationType{
        AGET,
        AGET_WIDE,
        AGET_OBJECT,
        AGET_BOOLEAN,
        AGET_BYTE,
        AGET_CHAR,
        AGET_SHORT,
        APUT,
        APUT_WIDE,
        APUT_OBJECT,
        APUT_BOOLEAN,
        APUT_BYTE,
        APUT_CHAR,
        APUT_SHORT,
    }

    public ArrayOperationInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction23x)){
            throw new SmaliSimulationException("Bad InstructionDef!");
        }
        BuilderInstruction23x bi = (BuilderInstruction23x) instructionDef;
        this.registerANumber = bi.getRegisterA();
        this.registerBNumber = bi.getRegisterB();
        this.registerCNumber = bi.getRegisterC();
        String opCodeName = bi.getOpcode().name;
        switch (opCodeName) {
            case "aget":
                this.type = ArrayOperationType.AGET;
                break;
            case "aget-wide":
                this.type = ArrayOperationType.AGET_WIDE;
                break;
            case "aget-object":
                this.type = ArrayOperationType.AGET_OBJECT;
                break;
            case "aget-boolean":
                this.type = ArrayOperationType.AGET_BOOLEAN;
                break;
            case "aget-byte":
                this.type = ArrayOperationType.AGET_BYTE;
                break;
            case "aget-char":
                this.type = ArrayOperationType.AGET_CHAR;
                break;
            case "aget-short":
                this.type = ArrayOperationType.AGET_SHORT;
                break;
            case "aput":
                this.type = ArrayOperationType.APUT;
                break;
            case "aput-wide":
                this.type = ArrayOperationType.APUT_WIDE;
                break;
            case "aput-object":
                this.type = ArrayOperationType.APUT_OBJECT;
                break;
            case "aput-boolean":
                this.type = ArrayOperationType.APUT_BOOLEAN;
                break;
            case "aput-byte":
                this.type = ArrayOperationType.APUT_BYTE;
                break;
            case "aput-char":
                this.type = ArrayOperationType.APUT_CHAR;
                break;
            case "aput-short":
                this.type = ArrayOperationType.APUT_SHORT;
                break;
            default:
                throw new SmaliSimulationException("Invalid instruction variant!");
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register r_value = methodExecution.getRegister(registerANumber);
        Register r_array_reg = methodExecution.getRegister(registerBNumber);
        Register r_index = methodExecution.getRegister(registerCNumber);

        // in the reference object of an array or the index value is ambiguous
        // then outcome and effect of this instruction will be ambiguous
        if(r_array_reg.containsAmbiguousValue() || r_index.containsAmbiguousValue()){
            handleAmbiguousValueOutcomes(r_value, r_array_reg, r_index, methodExecution);
            return;
        }

        int index = r_index.getIntValue();
        ArrayObjekt sa = (ArrayObjekt) r_array_reg.getReferencedObjectValue();
        ClazzLoader loader = methodExecution.getClazzLoader();

        // if reference object is null throw NullPointerException
        if(sa == null){
//            throwNullPointerExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, NullPointerException.class);
            return;
        }

        // if index is out of bounds throw  ArrayIndexOutOfBoundsException
        if(index >= sa.getSize() || index < 0){
//            throwArrayIndexOutOfBoundsExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, IndexOutOfBoundsException.class);
            return;
        }

        if(isAgetFamily()){
            if(sa.getValue(index) instanceof AmbiguousValue){
                AmbiguousValue ambiguousValue = (AmbiguousValue) sa.getValue(index);
                r_value.set(ambiguousValue);
            }
            else if(type == ArrayOperationType.AGET){
                if(!sa.getType().equals("[I") && !sa.getType().equals("[F")) {
                    throw new SmaliSimulationException("Referenced array is of invalid type! " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                if(sa.getType().equals("[I")){
                    Integer I = (Integer) sa.getValue(index);
                    if(I != null) {
                        r_value.set(I);
                    }else {
                        r_value.set(0);
                    }
                }
                else {
                    Float F = (Float) sa.getValue(index);
                    if(F != null) {
                        r_value.set(F);
                    }else {
                        r_value.set(0F);
                    }
                }
            }
            else if(type == ArrayOperationType.AGET_WIDE){
                if(!sa.getType().equals("[J") && !sa.getType().equals("[D")){
                    throw new SmaliSimulationException("Referenced array is of invalid type! " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                if(sa.getType().equals("[J")){
                    Long L = (Long) sa.getValue(index);
                    if(L != null) {
                        r_value.set(L);
                    }else {
                        r_value.set(0L);
                    }
                }
                else {
                    Double D = (Double) sa.getValue(index);
                    if(D != null) {
                        r_value.set(D);
                    }else {
                        r_value.set(0D);
                    }
                }
            }
            else if(type == ArrayOperationType.AGET_OBJECT){
                if(!sa.getType().startsWith("[L") && !sa.getType().startsWith("[[")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                AbstractObjekt o = (AbstractObjekt) sa.getValue(index);
                r_value.set(o);

            }
            else if(type == ArrayOperationType.AGET_BOOLEAN){
                if(!sa.getType().equals("[Z")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                Boolean Z = (Boolean) sa.getValue(index);
                if(Z == null){
                    r_value.set(false);
                }else {
                    r_value.set(Z);
                }
            }
            else if(type == ArrayOperationType.AGET_BYTE){
                if(!sa.getType().equals("[B")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                Byte B = (Byte) sa.getValue(index);
                if(B == null) {
                    r_value.set(0);
                }else {
                    r_value.set(B);
                }
            }
            else if(type == ArrayOperationType.AGET_CHAR){
                if(!sa.getType().equals("[C")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                Character C = (Character) sa.getValue(index);
                if(C == null) {
                    r_value.set(0);
                }else {
                    r_value.set(C);
                }
            }
            else if(type == ArrayOperationType.AGET_SHORT){
                if(!sa.getType().equals("[S")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                Short S = (Short) sa.getValue(index);
                if(S != null) {
                    r_value.set(S);
                }else {
                    r_value.set(0);
                }
            }
        }
        else {
            if(r_value.containsAmbiguousValue()){
                sa.setValue(index, r_value.getAmbiguousValue());
                return;
            }
            if(type == ArrayOperationType.APUT){
                if(!"[I".equals(sa.getType()) && !"[F".equals(sa.getType())) {
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                if(sa.getType().equals("[I")){
                    int i = r_value.getIntValue();
                    sa.setValue(index,i);
                }
                else{
                    float f = r_value.getFloatValue();
                    sa.setValue(index,f);
                }
            }
            else if(type == ArrayOperationType.APUT_WIDE){
                if(!"[D".equals(sa.getType()) && !"[J".equals(sa.getType())){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                if(sa.getType().equals("[D")){
                    double d = r_value.getDoubleValue();
                    sa.setValue(index,d);
                }
                else if(sa.getType().equals("[J")){
                    long l = r_value.getLongValue();
                    sa.setValue(index,l);
                }
            }
            else if(type == ArrayOperationType.APUT_OBJECT){
                if(!sa.getType().startsWith("[L") && !sa.getType().startsWith("[[")){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                AbstractObjekt objekt = r_value.getReferencedObjectValue();
                sa.setValue(index, objekt);
            }
            else if(type == ArrayOperationType.APUT_BOOLEAN){
                if(!"[Z".equals(sa.getType())){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                boolean z = r_value.getBooleanValue();
                sa.setValue(index,z);
            }
            else if(type == ArrayOperationType.APUT_BYTE){
                if(!"[B".equals(sa.getType())){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                byte b = r_value.getByteValue();
                sa.setValue(index,b);
            }
            else if(type == ArrayOperationType.APUT_CHAR){
                if(!"[C".equals(sa.getType())){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                char c = r_value.getCharValue();
                sa.setValue(index,c);
            }
            else if(type == ArrayOperationType.APUT_SHORT){
                if(!"[S".equals(sa.getType())){
                    throw new SmaliSimulationException("Referenced array is of invalid type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
                short s = r_value.getShortValue();
                sa.setValue(index,s);
            }
            else {
                throw new SmaliSimulationException("Invalid instruction variant type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
    }

    private void handleAmbiguousValueOutcomes(Register r_value, Register r_array_reg, Register r_index, MethodExecution methodExecution) {
        if(isAgetFamily()){
            if(r_array_reg.containsAmbiguousValue()){
                int r = SimulationUtils.getRandomNumberInRange(0, 2);
                if(SimSmaliConfig.canThrowNullPointerExceptionOnAmbiguity && r==0){
//                    throwNullPointerExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, NullPointerException.class);
                    return;
                }
                else if(SimSmaliConfig.canThrowArrayIndexOutOfBoundsExceptionOnAmbiguity && r==1){
//                    throwArrayIndexOutOfBoundsExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArrayIndexOutOfBoundsException.class);
                    return;
                }
                else {
                    String ambiguousArrayType = r_array_reg.getAmbiguousValue().getType();
                    r_value.set(new AmbiguousValue(ambiguousArrayType.substring(1)));
                    return;
                }
            }
            else {
                // write ambiguous value with type of array component to dest register
                ArrayObjekt arrayObjekt = (ArrayObjekt) r_array_reg.getReferencedObjectValue();
                if(arrayObjekt == null){
//                    throwNullPointerExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, NullPointerException.class);
                    return;
                }
                else {
                    int r = SimulationUtils.getRandomNumberInRange(0, 1);
                    if(SimSmaliConfig.canThrowArrayIndexOutOfBoundsExceptionOnAmbiguity && r==0){
//                        throwArrayIndexOutOfBoundsExceptionOn(methodExecution);
                        throwExceptionOn(methodExecution, ArrayIndexOutOfBoundsException.class);
                        return;
                    }
                    else {
                        r_value.set(new AmbiguousValue(arrayObjekt.getType().substring(1)));
                    }
                }
            }
        }
        // aput instructions
        else {
            // reference object is Ambiguous so it can also throw a NullPointerException
            // but this can lead to path explosion so we look to configurations
            // if such behaviour is desired
            if(r_array_reg.containsAmbiguousValue()){
                int r = SimulationUtils.getRandomNumberInRange(0, 1);
                if(SimSmaliConfig.canThrowArrayIndexOutOfBoundsExceptionOnAmbiguity && r==0){
//                    throwArrayIndexOutOfBoundsExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArrayIndexOutOfBoundsException.class);
                    return;
                }
                // no else needed since array is already ambiguous
            }
            else if(r_index.containsAmbiguousValue()) {
                int r = SimulationUtils.getRandomNumberInRange(0, 1);
                if(SimSmaliConfig.canThrowArrayIndexOutOfBoundsExceptionOnAmbiguity && r==0){
//                    throwArrayIndexOutOfBoundsExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArrayIndexOutOfBoundsException.class);
                    return;
                }
                //r_index is ambiguous so whole array object will turn to ambiguous
                ArrayObjekt arrayObjekt = (ArrayObjekt) r_array_reg.getReferencedObjectValue();
                r_array_reg.set(new AmbiguousValue(arrayObjekt.getType()));
            }

        }
    }


    private boolean isAgetFamily(){
        return this.type == ArrayOperationType.AGET ||
                this.type == ArrayOperationType.AGET_WIDE ||
                this.type == ArrayOperationType.AGET_BOOLEAN ||
                this.type == ArrayOperationType.AGET_BYTE ||
                this.type == ArrayOperationType.AGET_CHAR ||
                this.type == ArrayOperationType.AGET_SHORT ||
                this.type == ArrayOperationType.AGET_OBJECT;
    }

    @Override
    public String toString() {
        // arrayop vAA, vBB, vCC
        return this.opCode + " v"+registerANumber +
                " v"+registerBNumber+ " v"+registerCNumber + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        JSONObject jo = new JSONObject();
        Register r_value = methodExecution.getRegister(registerANumber);
        Register r_array_reg = methodExecution.getRegister(registerBNumber);
        Register r_index = methodExecution.getRegister(registerCNumber);
        jo.put("v" + registerANumber, getContentInfo(r_value));
        jo.put("v" + registerBNumber, getContentInfo(r_array_reg));
        jo.put("v" + registerCNumber, getContentInfo(r_index));
        return jo.toString();
    }
}