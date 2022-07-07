package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class StaticOperationInstruction extends SmaliInstruction{

    private final int registerANumber;
    private final String classFieldSignature;
    private final StaticOperationType type;

    private final String fieldNameStr;
    private final String fieldTypeStr;

    private final String referencedTypeClassPath;

    public enum StaticOperationType{
        SGET,
        SGET_WIDE,
        SGET_OBJECT,
        SGET_BOOLEAN,
        SGET_BYTE,
        SGET_CHAR,
        SGET_SHORT,
        SPUT,
        SPUT_WIDE,
        SPUT_OBJECT,
        SPUT_BOOLEAN,
        SPUT_BYTE,
        SPUT_CHAR,
        SPUT_SHORT,
    }

    public StaticOperationInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction21c)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction21c bi = (BuilderInstruction21c) instructionDef;
        this.registerANumber = bi.getRegisterA();
        this.classFieldSignature = bi.getReference().toString();
        String opCodeName = bi.getOpcode().name;
        if(opCodeName.equals("sget")){
            this.type = StaticOperationType.SGET;
        }
        else if(opCodeName.equals("sget-wide")){
            this.type = StaticOperationType.SGET_WIDE;
        }
        else if(opCodeName.equals("sget-object")){
            this.type = StaticOperationType.SGET_OBJECT;
        }
        else if(opCodeName.equals("sget-boolean")){
            this.type = StaticOperationType.SGET_BOOLEAN;
        }
        else if(opCodeName.equals("sget-byte")){
            this.type = StaticOperationType.SGET_BYTE;
        }
        else if(opCodeName.equals("sget-char")){
            this.type = StaticOperationType.SGET_CHAR;
        }
        else if(opCodeName.equals("sget-short")){
            this.type = StaticOperationType.SGET_SHORT;
        }
        else if(opCodeName.equals("sput")){
            this.type = StaticOperationType.SPUT;
        }
        else if(opCodeName.equals("sput-wide")){
            this.type = StaticOperationType.SPUT_WIDE;
        }
        else if(opCodeName.equals("sput-object")){
            this.type = StaticOperationType.SPUT_OBJECT;
        }
        else if(opCodeName.equals("sput-boolean")){
            this.type = StaticOperationType.SPUT_BOOLEAN;
        }
        else if(opCodeName.equals("sput-byte")){
            this.type = StaticOperationType.SPUT_BYTE;
        }
        else if(opCodeName.equals("sput-char")){
            this.type = StaticOperationType.SPUT_CHAR;
        }
        else if(opCodeName.equals("sput-short")){
            this.type = StaticOperationType.SPUT_SHORT;
        }
        else {
            throw new SmaliSimulationException("Invalid instruction variant!");
        }

        this.referencedTypeClassPath = classFieldSignature.split("->")[0];
        String fieldSignature = classFieldSignature.split("->")[1];
        this.fieldNameStr = fieldSignature.split(":")[0];
        this.fieldTypeStr = fieldSignature.split(":")[1];
    }


    @Override
    protected void execute(MethodExecution methodExecution) {
        Register r = methodExecution.getRegister(registerANumber);
        Clazz sc = methodExecution.getClazzLoader().getClazz(referencedTypeClassPath);
        if(isSGetFamily()){
            Object o = sc.getStaticFieldValue(fieldNameStr);
            if(o instanceof AmbiguousValue){
               r.set((AmbiguousValue) o);
            }
            else if(type == StaticOperationType.SGET){

                if(o instanceof Integer){
                    r.set((Integer) o);
                }
                else if(o instanceof Float){
                    r.set((Float) o);
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_WIDE){
                if(o instanceof Long){
                    r.set((Long) o);
                }
                else if(o instanceof Double){
                    r.set((Double) o);
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_OBJECT){
                if(o == null){
                    r.set((AbstractObjekt) null);
                }
                else if(o instanceof AbstractObjekt){
                    AbstractObjekt so = (AbstractObjekt) o;
                    if(so.isInstanceOf(fieldTypeStr)) {
                        r.set(so);
                    }
                    else {
                        throw new SmaliSimulationException("Invalid value for static instruction type : "+
                                o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                    }
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_BOOLEAN){
                if(o instanceof Boolean){
                    if((Boolean) o) {
                        r.set(1);
                    }
                    else {
                        r.set(0);
                    }
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_BYTE){
                if(o instanceof Byte){
                    r.set((Byte) o);
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_CHAR){
                if(o instanceof Character){
                    r.set( (Character) o);
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SGET_SHORT){
                if(o instanceof Short){
                    r.set((Short) o);
                }
                else {
                    throw new SmaliSimulationException("Invalid value for static instruction type : "+
                            o.toString() + " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
        }
        else {
            if(r.containsAmbiguousValue()){
                sc.setStaticFieldValue(fieldNameStr, r.getAmbiguousValue());
                return;
            }
            if(type == StaticOperationType.SPUT){
                if(fieldTypeStr.equals("I")) {
                    sc.setStaticFieldValue(fieldNameStr, r.getIntValue());
                }
                else if(fieldTypeStr.equals("F")){
                    sc.setStaticFieldValue(fieldNameStr, r.getFloatValue());
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SPUT_WIDE){
                if(fieldTypeStr.equals("J")) {
                    sc.setStaticFieldValue(fieldNameStr, r.getLongValue());
                }
                else if(fieldTypeStr.equals("D")){
                    sc.setStaticFieldValue(fieldNameStr, r.getDoubleValue());
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SPUT_OBJECT){
                sc.setStaticFieldValue(fieldNameStr, r.getReferencedObjectValue());
            }
            else if(type == StaticOperationType.SPUT_BOOLEAN){
                if(fieldTypeStr.equals("Z")) {
                    Boolean z = r.getIntValue()%2 == 1;
                    sc.setStaticFieldValue(fieldNameStr, z);
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SPUT_BYTE){
                if(fieldTypeStr.equals("B")) {
                    Byte b = (byte) r.getIntValue();
                    sc.setStaticFieldValue(fieldNameStr, b);
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SPUT_CHAR){
                if(fieldTypeStr.equals("C")) {
                    Character c = (char) r.getIntValue();
                    sc.setStaticFieldValue(fieldNameStr, c);
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else if(type == StaticOperationType.SPUT_SHORT){
                if(fieldTypeStr.equals("S")) {
                    Short s = (short) r.getIntValue();
                    sc.setStaticFieldValue(fieldNameStr, s);
                }
                else {
                    throw new SmaliSimulationException("Invalid field type for static instruction type : "+
                            fieldTypeStr+ ":" +fieldNameStr +" " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
            else {
                throw new SmaliSimulationException("Invalid instruction type!");
            }
        }
    }

    private boolean isSGetFamily(){
        return type == StaticOperationType.SGET ||
                type == StaticOperationType.SGET_BOOLEAN ||
                type == StaticOperationType.SGET_BYTE ||
                type == StaticOperationType.SGET_CHAR ||
                type == StaticOperationType.SGET_OBJECT ||
                type == StaticOperationType.SGET_SHORT ||
                type == StaticOperationType.SGET_WIDE;

    }

    @Override
    public String toString() {
        //sstaticop vAA, field@BBBB
        return this.opCode + " v" + registerANumber + " " +
                classFieldSignature + " &" + instructionPositionNumber;
    }

    public StaticOperationType getType() {
        return type;
    }

    public String getTargetFieldName() {
        return fieldNameStr;
    }

    public String getTargetFieldType() {
        return fieldTypeStr;
    }

    public int getRegisterANumber() {
        return registerANumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(registerANumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerANumber, getContentInfo(rA));
        return jo.toString();
    }

    public String getReferencedTypeClassPath() {
        return referencedTypeClassPath;
    }
}
