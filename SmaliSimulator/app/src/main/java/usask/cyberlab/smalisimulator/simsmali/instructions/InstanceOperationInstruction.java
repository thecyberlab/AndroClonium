package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InstanceOperationInstruction extends SmaliInstruction {

    private final int registerANumber;
    private final int registerBNumber;
    private final String classFieldSignature;
    private final InstanceOperationType type;

    public enum InstanceOperationType{
        IGET,
        IGET_WIDE,
        IGET_OBJECT,
        IGET_BOOLEAN,
        IGET_BYTE,
        IGET_CHAR,
        IGET_SHORT,
        IPUT,
        IPUT_WIDE,
        IPUT_OBJECT,
        IPUT_BOOLEAN,
        IPUT_BYTE,
        IPUT_CHAR,
        IPUT_SHORT,
    }

    public InstanceOperationInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(!(instructionDef instanceof BuilderInstruction22c)){
            throw new SmaliSimulationException();
        }
        BuilderInstruction22c bi = (BuilderInstruction22c) instructionDef;
        this.registerANumber = bi.getRegisterA();
        this.registerBNumber = bi.getRegisterB();
        this.classFieldSignature = bi.getReference().toString();
        String opCodeName = bi.getOpcode().name;
        switch (opCodeName) {
            case "iget":
                this.type = InstanceOperationType.IGET;
                break;
            case "iget-wide":
                this.type = InstanceOperationType.IGET_WIDE;
                break;
            case "iget-object":
                this.type = InstanceOperationType.IGET_OBJECT;
                break;
            case "iget-boolean":
                this.type = InstanceOperationType.IGET_BOOLEAN;
                break;
            case "iget-byte":
                this.type = InstanceOperationType.IGET_BYTE;
                break;
            case "iget-char":
                this.type = InstanceOperationType.IGET_CHAR;
                break;
            case "iget-short":
                this.type = InstanceOperationType.IGET_SHORT;
                break;
            case "iput":
                this.type = InstanceOperationType.IPUT;
                break;
            case "iput-wide":
                this.type = InstanceOperationType.IPUT_WIDE;
                break;
            case "iput-object":
                this.type = InstanceOperationType.IPUT_OBJECT;
                break;
            case "iput-boolean":
                this.type = InstanceOperationType.IPUT_BOOLEAN;
                break;
            case "iput-byte":
                this.type = InstanceOperationType.IPUT_BYTE;
                break;
            case "iput-char":
                this.type = InstanceOperationType.IPUT_CHAR;
                break;
            case "iput-short":
                this.type = InstanceOperationType.IPUT_SHORT;
                break;
            default:
                throw new SmaliSimulationException("Invalid instruction variant!");
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register valueRegister = methodExecution.getRegister(registerANumber);
        Register refRegister = methodExecution.getRegister(registerBNumber);


        if(refRegister.containsAmbiguousValue()){
            handleAmbiguousOutcome(refRegister, valueRegister, methodExecution);
            return;
        }
        Objekt referenceObjekt = (Objekt) refRegister.getReferencedObjectValue();

        String classPath = classFieldSignature.split("->")[0];
        String fieldSignature = classFieldSignature.split("->")[1];
        String fieldName = fieldSignature.split(":")[0];
        String fieldType = fieldSignature.split(":")[1];

        if(referenceObjekt == null){
//            throwNullPointerExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, NullPointerException.class);
            return;
        }

        if(this.isIPutFamily() && valueRegister.containsAmbiguousValue()){
            handleAmbiguousOutcome(refRegister, valueRegister, methodExecution);
            return;
        }


        if(!referenceObjekt.isInstanceOf(classPath)){
            String msg = "referenced object is not of specified types! ref object type : " + referenceObjekt.getType() +
                    ", needing : " + classPath +", at instruction " + this.toString();
            throw new SmaliSimulationException(msg);
        }

        if(shouldCacheFieldValue(referenceObjekt)){
            handleFieldCaching(valueRegister, refRegister, fieldName, fieldType, methodExecution);
            return;
        }

        Object fieldValue=null;
        Clazz clazzOfTypeInInstruction = methodExecution.getClazzLoader().getClazz(classPath);

        if(isIGetFamily()) fieldValue = referenceObjekt.getInstanceFieldValue(fieldName, clazzOfTypeInInstruction);
        if(type == InstanceOperationType.IGET){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("I")){
                Integer i = (Integer) fieldValue;
                valueRegister.set(i);
            }
            else if(fieldType.equals("F")){
                Float f = (Float) fieldValue;
                valueRegister.set(f);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IGET_WIDE){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("J")){
                Long l = (Long) fieldValue;
                valueRegister.set(l);
            }
            else if(fieldType.equals("D")){
                Double d = (Double) fieldValue;
                valueRegister.set(d);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IGET_OBJECT){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else {
                AbstractObjekt newSo = (AbstractObjekt) fieldValue;
                if (newSo == null) {
                    valueRegister.set(newSo);
                } else {
                    Clazz clz = newSo.getClazz();
                    if (clz.isSubTypeOf(fieldType)) {
                        valueRegister.set(newSo);
                    } else
                        throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                                " " + methodExecution.getSmaliMethod().getClassMethodSignature());
                }
            }
        }
        else if(type == InstanceOperationType.IGET_BOOLEAN){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("Z")){
                Boolean z = (Boolean) fieldValue;
                valueRegister.set(z);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IGET_BYTE){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("B")){
                Byte b = (Byte) fieldValue;
                valueRegister.set(b);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IGET_CHAR){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("C")){
                Character c = (Character) fieldValue;
                valueRegister.set(c);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IGET_SHORT){
            if(fieldValue instanceof AmbiguousValue){
                valueRegister.set((AmbiguousValue) fieldValue);
            }
            else if(fieldType.equals("S")){
                Short s = (Short) fieldValue;
                valueRegister.set(s);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IPUT){
            if(fieldType.equals("I") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getIntValue(), clazzOfTypeInInstruction);
            }
            else if(fieldType.equals("F") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getFloatValue(), clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }

        }
        else if(type == InstanceOperationType.IPUT_WIDE){
            if(fieldType.equals("J") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getLongValue(), clazzOfTypeInInstruction);
            }
            else if(fieldType.equals("D") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getDoubleValue(), clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IPUT_OBJECT){
            AbstractObjekt objekt = valueRegister.getReferencedObjectValue();
            referenceObjekt.setInstanceFieldValue(fieldName, objekt, clazzOfTypeInInstruction);
        }
        else if(type == InstanceOperationType.IPUT_BOOLEAN){
            if(fieldType.equals("Z") && valueRegister.containsNumericData()){
                Boolean b = valueRegister.getBooleanValue();
                referenceObjekt.setInstanceFieldValue(fieldName,b, clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IPUT_BYTE){
            if(fieldType.equals("B") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName,valueRegister.getByteValue(), clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IPUT_CHAR){
            if(fieldType.equals("C") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getCharValue(), clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else if(type == InstanceOperationType.IPUT_SHORT){
            if(fieldType.equals("S") && valueRegister.containsNumericData()){
                referenceObjekt.setInstanceFieldValue(fieldName, valueRegister.getShortValue(), clazzOfTypeInInstruction);
            }
            else {
                throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                        " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        else {
            throw new SmaliSimulationException("Invalid instruction type! " +methodExecution.getSmaliMethod().getClassMethodSignature());
        }
    }

    private boolean shouldCacheFieldValue(AbstractObjekt referenceObjekt) {
        String[] splits = referenceObjekt.getClazz().getClassPath().split("/");
        String className = splits[splits.length - 1];
        if(className.contains("$")
        && referenceObjekt.getMirroringObject() == null) return true;
        return false;
    }

    private void handleFieldCaching(Register ra, Register rb, String fieldName, String fieldType, MethodExecution methodExecution){
        Objekt referenceObjekt = (Objekt) rb.getReferencedObjectValue();

        if(type.toString().startsWith("IPUT") && ra.containsAmbiguousValue()){
            AmbiguousValue ambiguousValue = ra.getAmbiguousValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, ambiguousValue);
        }
        else if(type == InstanceOperationType.IPUT_OBJECT){
            AbstractObjekt objekt = ra.getReferencedObjectValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, objekt);
        }
        else if(type == InstanceOperationType.IPUT_BOOLEAN){
            Boolean b = ra.getBooleanValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, b);
        }
        else if(type == InstanceOperationType.IPUT_SHORT){
            Short s = ra.getShortValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, s);
        }
        else if(type == InstanceOperationType.IPUT_CHAR){
            Character c = ra.getCharValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, c);
        }
        else if(type == InstanceOperationType.IPUT_BYTE){
            Byte b = ra.getByteValue();
            referenceObjekt.setInstanceFieldValueCache(fieldName, b);
        }
        else if(type == InstanceOperationType.IPUT_WIDE){
            if(fieldType.equals("D")){
                Double d = ra.getDoubleValue();
                referenceObjekt.setInstanceFieldValueCache(fieldName, d);
            }
            else if(fieldType.equals("J")){
                Long l = ra.getLongValue();
                referenceObjekt.setInstanceFieldValueCache(fieldName, l);
            }
            else {
                throw new SmaliSimulationException();
            }
        }
        else if(type == InstanceOperationType.IPUT){
            if(fieldType.equals("I")){
                Integer i = ra.getIntValue();
                referenceObjekt.setInstanceFieldValueCache(fieldName, i);
            }
            else if(fieldType.equals("F")){
                Float f = ra.getFloatValue();
                referenceObjekt.setInstanceFieldValueCache(fieldName, f);
            }
            else {
                throw new SmaliSimulationException();
            }
        }
        else if(type.toString().startsWith("IGET")
                && referenceObjekt.getInstanceFieldValueCache(fieldName) instanceof AmbiguousValue){
            Object fieldValue = referenceObjekt.getInstanceFieldValueCache(fieldName);
            if(fieldValue instanceof AmbiguousValue){
                ra.set((AmbiguousValue) fieldValue);
            }
        }
        else if(type == InstanceOperationType.IGET_OBJECT){
            Object fieldValue = referenceObjekt.getInstanceFieldValueCache(fieldName);
            AbstractObjekt newSo = (AbstractObjekt) fieldValue;
            if (newSo == null) {
                ra.set(newSo);
            } else {
                Clazz clz = newSo.getClazz();
                if (clz.isSubTypeOf(fieldType)) {
                    ra.set(newSo);
                } else
                    throw new SmaliSimulationException("field type is invalid for this instance operation variant! " + fieldType +
                            " " + methodExecution.getSmaliMethod().getClassMethodSignature());
            }
        }
        //TODO==
//        else if(type == InstanceOperationType.IGET){}
//        else if(type == InstanceOperationType.IGET_BOOLEAN){}
//        else if(type == InstanceOperationType.IGET_BYTE){}
//        else if(type == InstanceOperationType.IGET_CHAR){}
//        else if(type == InstanceOperationType.IGET_SHORT){}
//        else if(type == InstanceOperationType.IGET_BOOLEAN){}
        else {
            throw new SmaliSimulationException("Field caching is only implemented for iput-object and iget-object, this is : " + this.toString());
        }
    }


    private void handleAmbiguousOutcome(Register referenceRegister, Register valueRegister, MethodExecution methodExecution) {
        String classPath = classFieldSignature.split("->")[0];
        String fieldSignature = classFieldSignature.split("->")[1];
        String fieldName = fieldSignature.split(":")[0];
        String fieldType = fieldSignature.split(":")[1];
        Clazz clazzOfTypeInInstruction = methodExecution.getClazzLoader().getClazz(classPath);

        // Ambiguous Reference Object
        if(referenceRegister.containsAmbiguousValue()){
            int r = SimulationUtils.getRandomNumberInRange(0, 1);
            if(SimSmaliConfig.canThrowNullPointerExceptionOnAmbiguity && r==0){
//                throwNullPointerExceptionOn(methodExecution);
                return;
            }
            else {
                if(isIGetFamily()){
                    valueRegister.set(new AmbiguousValue(fieldType));
                }
            }

        }
        else if(this.isIPutFamily()){
            Objekt referenceObjekt = (Objekt) referenceRegister.getReferencedObjectValue();
            if(shouldCacheFieldValue(referenceObjekt)){
                handleFieldCaching(valueRegister, referenceRegister, fieldName, fieldType, methodExecution);
                return;
            }

            AmbiguousValue ambiguousValue = valueRegister.getAmbiguousValue();
            referenceObjekt.setInstanceFieldValue(fieldName, ambiguousValue, clazzOfTypeInInstruction);
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    private boolean isIGetFamily(){
        return type ==  InstanceOperationType.IGET ||
                type ==  InstanceOperationType.IGET_BOOLEAN ||
                type ==  InstanceOperationType.IGET_BYTE ||
                type ==  InstanceOperationType.IGET_CHAR ||
                type ==  InstanceOperationType.IGET_OBJECT ||
                type ==  InstanceOperationType.IGET_SHORT ||
                type == InstanceOperationType.IGET_WIDE;

    }

    private boolean isIPutFamily(){
        return !isIGetFamily();
    }

    @Override
    public String toString() {
        //iinstanceop vA, vB, field@CCCC ()
        return this.opCode + " v" + registerANumber + " v"+registerBNumber +
                " " + classFieldSignature + " &" + instructionPositionNumber;
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rA = methodExecution.getRegister(registerANumber);
        Register rB = methodExecution.getRegister(registerBNumber);

        JSONObject jo = new JSONObject();
        jo.put("v" + registerANumber, getContentInfo(rA));
        jo.put("v" + registerBNumber, getContentInfo(rB));
        return jo.toString();
    }

    public String getClassFieldSignature(){
        return classFieldSignature;
    }
}
