package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class UnaryOperationInstruction extends SmaliInstruction {

    public enum UnaryOperationType{
        NEG_INT,
        NOT_INT,
        NEG_LONG,
        NOT_LONG,
        NEG_FLOAT,
        NEG_DOUBLE,
        INT_TO_LONG,
        INT_TO_FLOAT,
        INT_TO_DOUBLE,
        LONG_TO_INT,
        LONG_TO_FLOAT,
        LONG_TO_DOUBLE,
        FLOAT_TO_INT,
        FLOAT_TO_LONG,
        FLOAT_TO_DOUBLE,
        DOUBLE_TO_INT,
        DOUBLE_TO_LONG,
        DOUBLE_TO_FLOAT,
        INT_TO_BYTE,
        INT_TO_CHAR,
        INT_TO_SHORT,
    }

    private final int srcRegisterNumber;
    private final int destRegisterNumber;
    private final UnaryOperationType type;

    public UnaryOperationInstruction( Instruction instructionDef,int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction12x)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction12x bi = (BuilderInstruction12x) instructionDef;
        this.srcRegisterNumber = bi.getRegisterB();
        this.destRegisterNumber = bi.getRegisterA();
        String opCodeName = instructionDef.getOpcode().name;
        if(opCodeName.equals("neg-int")){
            type = UnaryOperationType.NEG_INT;
        }
        else if(opCodeName.equals("not-int")){
            type = UnaryOperationType.NOT_INT;
        }
        else if(opCodeName.equals("neg-long")){
            type = UnaryOperationType.NEG_LONG;
        }
        else if(opCodeName.equals("not-long")){
            type = UnaryOperationType.NOT_LONG;
        }
        else if(opCodeName.equals("neg-float")){
            type = UnaryOperationType.NEG_FLOAT;
        }
        else if(opCodeName.equals("neg-double")){
            type = UnaryOperationType.NEG_DOUBLE;
        }
        else if(opCodeName.equals("int-to-long")){
            type = UnaryOperationType.INT_TO_LONG;
        }
        else if(opCodeName.equals("int-to-float")){
            type = UnaryOperationType.INT_TO_FLOAT;
        }
        else if(opCodeName.equals("int-to-double")){
            type = UnaryOperationType.INT_TO_DOUBLE;
        }
        else if(opCodeName.equals("long-to-int")){
            type = UnaryOperationType.LONG_TO_INT;
        }
        else if(opCodeName.equals("long-to-float")){
            type = UnaryOperationType.LONG_TO_FLOAT;
        }
        else if(opCodeName.equals("long-to-double")){
            type = UnaryOperationType.LONG_TO_DOUBLE;
        }
        else if(opCodeName.equals("float-to-int")){
            type = UnaryOperationType.FLOAT_TO_INT;
        }
        else if(opCodeName.equals("float-to-long")){
            type = UnaryOperationType.FLOAT_TO_LONG;
        }
        else if(opCodeName.equals("float-to-double")){
            type = UnaryOperationType.FLOAT_TO_DOUBLE;
        }
        else if(opCodeName.equals("double-to-int")){
            type = UnaryOperationType.DOUBLE_TO_INT;
        }
        else if(opCodeName.equals("double-to-long")){
            type = UnaryOperationType.DOUBLE_TO_LONG;
        }
        else if(opCodeName.equals("double-to-float")){
            type = UnaryOperationType.DOUBLE_TO_FLOAT;
        }
        else if(opCodeName.equals("int-to-byte")){
            type = UnaryOperationType.INT_TO_BYTE;
        }
        else if(opCodeName.equals("int-to-char")){
            type = UnaryOperationType.INT_TO_CHAR;
        }
        else if(opCodeName.equals("int-to-short")){
            type = UnaryOperationType.INT_TO_SHORT;
        }
        else{
            throw new SmaliSimulationException("Invalid instruction variant!");
        }
    }


    @Override
    protected void execute(MethodExecution methodExecution) {
        Register r1 = methodExecution.getRegister(srcRegisterNumber);
        Register r2 = methodExecution.getRegister(destRegisterNumber);
        if(r1.containsAmbiguousValue()){
            setCorrectAmbiguousValue(r2);
            return;
        }

        if(type == UnaryOperationType.NEG_INT){
            r2.set( - r1.getIntValue());
        }
        else if(type == UnaryOperationType.NOT_INT){
            r2.set( - r1.getIntValue() - 1);
        }
        else if(type == UnaryOperationType.NEG_FLOAT){
            r2.set(-r1.getFloatValue());
        }
        else if(type == UnaryOperationType.INT_TO_FLOAT){
            float f = (float) r1.getIntValue();
            r2.set(f);
        }
        else if(type == UnaryOperationType.INT_TO_SHORT){
            r2.set((short) r1.getIntValue());
        }
        else if(type == UnaryOperationType.INT_TO_CHAR){
            r2.set((char) r1.getIntValue());
        }
        else if(type == UnaryOperationType.INT_TO_BYTE){
            r2.set((byte) r1.getIntValue());
        }
        else if(type == UnaryOperationType.INT_TO_LONG){
            long l = r1.getIntValue();
            r2.set(l);
        }
        else if(type == UnaryOperationType.INT_TO_DOUBLE){
            double d = r1.getIntValue();
            r2.set(d);
        }
        else if(type == UnaryOperationType.NEG_LONG){
            r2.set(- r1.getLongValue());
        }
        else if(type == UnaryOperationType.NOT_LONG){
            r2.set( - r1.getLongValue() - 1);
        }
        else if(type == UnaryOperationType.NEG_DOUBLE){
            r2.set( - r1.getDoubleValue());
        }
        else if(type == UnaryOperationType.LONG_TO_INT){
            r2.set((int) r1.getLongValue());
        }
        else if(type == UnaryOperationType.LONG_TO_FLOAT){
            r2.set((float) r1.getLongValue());
        }
        else if(type == UnaryOperationType.LONG_TO_DOUBLE){
            r2.set((double) r1.getLongValue());
        }
        else if(type == UnaryOperationType.FLOAT_TO_INT){
            r2.set((int) r1.getFloatValue());
        }
        else if(type == UnaryOperationType.FLOAT_TO_DOUBLE){
            r2.set((double) r1.getFloatValue());
        }
        else if(type == UnaryOperationType.FLOAT_TO_LONG){
            r2.set((long) r1.getFloatValue());
        }
        else if(type == UnaryOperationType.DOUBLE_TO_INT){
            r2.set((int) r1.getDoubleValue());
        }
        else if(type == UnaryOperationType.DOUBLE_TO_FLOAT){
            r2.set((float) r1.getDoubleValue());
        }
        else if(type == UnaryOperationType.DOUBLE_TO_LONG){
            r2.set((long) r1.getDoubleValue());
        }
        else {
            throw new SmaliSimulationException("Invalid instruction type! " + methodExecution.getSmaliMethod().getClassMethodSignature());
        }
    }

    private void setCorrectAmbiguousValue(Register r2) {
        if(type == UnaryOperationType.NEG_INT) r2.set(new AmbiguousValue("I"));
        else if(type == UnaryOperationType.NOT_INT) r2.set(new AmbiguousValue("I"));
        else if(type == UnaryOperationType.NEG_FLOAT) r2.set(new AmbiguousValue("F"));
        else if(type == UnaryOperationType.INT_TO_FLOAT) r2.set(new AmbiguousValue("F"));
        else if(type == UnaryOperationType.INT_TO_SHORT) r2.set(new AmbiguousValue("S"));
        else if(type == UnaryOperationType.INT_TO_CHAR) r2.set(new AmbiguousValue("C"));
        else if(type == UnaryOperationType.INT_TO_BYTE) r2.set(new AmbiguousValue("B"));
        else if(type == UnaryOperationType.INT_TO_LONG) r2.set(new AmbiguousValue("J"));
        else if(type == UnaryOperationType.INT_TO_DOUBLE) r2.set(new AmbiguousValue("D"));
        else if(type == UnaryOperationType.NEG_LONG) r2.set(new AmbiguousValue("J"));
        else if(type == UnaryOperationType.NOT_LONG) r2.set(new AmbiguousValue("J"));
        else if(type == UnaryOperationType.NEG_DOUBLE) r2.set(new AmbiguousValue("D"));
        else if(type == UnaryOperationType.LONG_TO_INT) r2.set(new AmbiguousValue("I"));
        else if(type == UnaryOperationType.LONG_TO_FLOAT) r2.set(new AmbiguousValue("F"));
        else if(type == UnaryOperationType.LONG_TO_DOUBLE) r2.set(new AmbiguousValue("D"));
        else if(type == UnaryOperationType.FLOAT_TO_INT) r2.set(new AmbiguousValue("I"));
        else if(type == UnaryOperationType.FLOAT_TO_DOUBLE) r2.set(new AmbiguousValue("D"));
        else if(type == UnaryOperationType.FLOAT_TO_LONG) r2.set(new AmbiguousValue("J"));
        else if(type == UnaryOperationType.DOUBLE_TO_INT) r2.set(new AmbiguousValue("I"));
        else if(type == UnaryOperationType.DOUBLE_TO_FLOAT) r2.set(new AmbiguousValue("F"));
        else if(type == UnaryOperationType.DOUBLE_TO_LONG) r2.set(new AmbiguousValue("J"));
        else throw new SmaliSimulationException("Invalid instruction type!");
    }


    @Override
    public String toString() {
        //unop vA, vB
        return this.opCode + " v" + destRegisterNumber + " v" +
                srcRegisterNumber + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(srcRegisterNumber);
        Register rB = methodExecution.getRegister(destRegisterNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + srcRegisterNumber, getContentInfo(rA));
        jo.put("v" + destRegisterNumber, getContentInfo(rB));
        return jo.toString();
    }
}
