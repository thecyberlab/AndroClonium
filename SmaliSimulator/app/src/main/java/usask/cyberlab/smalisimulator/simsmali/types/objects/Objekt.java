package usask.cyberlab.smalisimulator.simsmali.types.objects;

import java.lang.reflect.Field;
import java.util.HashMap;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;

public class Objekt extends AbstractObjekt {

    //this map keeps track of fields that have been turned to an ambiguous value
    protected final HashMap<String, AmbiguousValue> instanceFieldsAmbiguousMask = new HashMap<>();

    // this map keeps track of wrapper object of fields that are not primitive type
    // this is because we want to keep track of wrappers since they contain important info
    protected final HashMap<String, AbstractObjekt> instanceFieldsWrappers = new HashMap<>();

    // this is used to cache values assigned to an Objekt before it has been initialized which
    // means that the mirroring object is null. This is needed for nested classes since only they
    // assign a value to a field before calling the constructor
    protected final HashMap<String, Object> fieldCache = new HashMap<>();

    public Objekt(Clazz clazz) {
        super(clazz);
    }

    public Objekt(Clazz clazz, Object mirroringObject) {
        super(clazz, mirroringObject);
    }

    public void setInstanceFieldValue(String fieldName, Object value, Clazz fromType) {
        if(value instanceof AmbiguousValue){
            instanceFieldsAmbiguousMask.put(fieldName, (AmbiguousValue) value);
        }
        else {
            Class fieldContainingClass;
            if(fromType != null){
                if(!(fromType.isSuperTypeOf(this.getType()))){
                    throw new SmaliSimulationException();
                }
                fieldContainingClass = fromType.getMirroringClass();
            }
            else {
                if (mirroringObject == null) {
                    throw new SmaliSimulationException("mirroringObject is null");
                }
                fieldContainingClass = mirroringObject.getClass();
            }
            // resolve field
            Field field ;
            try {
                field = Clazz.resolveField(fieldContainingClass, fieldName);
            } catch (NoSuchFieldException e) {
                throw new SmaliSimulationException(e);
            }
            // set the values
            SimulationUtils.setFieldValue(field, value, mirroringObject);
            // if the field type is not primitive then it has a wrapper and
            // we should also keep the wrapper
            if( ! SimulationUtils.isPrimitiveType(getFieldType(fieldName, fromType)) ) {
                this.instanceFieldsWrappers.put(fieldName, (AbstractObjekt) value);
            }
            instanceFieldsAmbiguousMask.remove(fieldName);
        }
    }

    public Object getInstanceFieldValue(String fieldName, Clazz fromType) {
        if (mirroringObject == null) {
            throw new SmaliSimulationException("mirroringObject is null");
        }
        // resolving the Field object
        Field field;
        Class fieldContainingClass;
        if(fromType != null){
            if(!(fromType.isSuperTypeOf(this.getType()))){
                throw new SmaliSimulationException();
            }
            fieldContainingClass = fromType.getMirroringClass();
        }
        else {
            if (mirroringObject == null) {
                throw new SmaliSimulationException("mirroringObject is null");
            }
            fieldContainingClass = mirroringObject.getClass();
        }
        try {
            field = Clazz.resolveField(fieldContainingClass, fieldName);
        } catch (NoSuchFieldException e) {
            throw new SmaliSimulationException(e);
        }

        // returning ambiguous value if field value is ambiguous
        if(instanceFieldsAmbiguousMask.containsKey(fieldName)){
            return instanceFieldsAmbiguousMask.get(fieldName);
        }
        else{
            try {
                String fieldType = this.getFieldType(fieldName, fromType);
                // if field is primitive
                if(SimulationUtils.isPrimitiveType(fieldType)){
                    field.setAccessible(true);
                    Object rawValue = field.get(mirroringObject);
                    return SimulationUtils.createWrapperObjektFromRawValue(rawValue, true, getMyClassLoader());
                }
                // field is objekt
                else {
                    // for objekts created from smali classes we can just return the wrapper
                    if(instanceFieldsWrappers.containsKey(fieldName)){
                        return this.instanceFieldsWrappers.get(fieldName);
                    }
                    // but for java objects if we want to return their fields
                    // the instanceFieldsWrappers is null since the constructor
                    // is not done through the simulator
                    else {
                        Object rawValue = field.get(mirroringObject);
                        return SimulationUtils.createWrapperObjektFromRawValue(rawValue, false, getMyClassLoader());
                    }

                }

            } catch (IllegalAccessException e) {
                throw new SmaliSimulationException(e);
            }
        }
    }

    public String getFieldType(String fieldName, Clazz fromType) {
        Field field;
        Class fieldContainingClass;
        if(fromType != null){
            if(!(fromType.isSuperTypeOf(this.getType()))){
                throw new SmaliSimulationException();
            }
            fieldContainingClass = fromType.getMirroringClass();
        }
        else {
            if (mirroringObject == null) {
                throw new SmaliSimulationException("mirroringObject is null");
            }
            fieldContainingClass = mirroringObject.getClass();
        }
        try {
            field = Clazz.resolveField(fieldContainingClass, fieldName);
            String rawTypeStr = field.getType().getName();
            return SimulationUtils.makeSmaliStyleClassPath(rawTypeStr);
        } catch (NoSuchFieldException e) {
            throw new SmaliSimulationException(e);
        }
    }





    @Override
    public void setMirroringObject(Object mirroringObject) {
        String s = mirroringObject.getClass().getName();
        s = SimulationUtils.makeSmaliStyleClassPath(s);
        String type = this.getType();
        if(!type.equals(s)) throw new SmaliSimulationException(type + " conflicts with " + s + " when setting mirrorValue");
        this.mirroringObject = mirroringObject;
        if(fieldCache != null && fieldCache.size() > 0) {
            syncFieldCacheWithObject();
        }
    }


    public void setInstanceFieldValueCache(String fieldName, Object value){
        fieldCache.put(fieldName, value);
    }

    public Object getInstanceFieldValueCache(String fieldName){
        if(fieldCache.containsKey(fieldName)){
            return fieldCache.get(fieldName);
        }
        else {
            throw new SmaliSimulationException();
        }
    }

    private void syncFieldCacheWithObject(){
        for(String k:fieldCache.keySet()){
            this.setInstanceFieldValue(k, fieldCache.get(k), null);
        }
        fieldCache.clear();
    }

    public boolean containsAmbiguousMasks(){
        return instanceFieldsAmbiguousMask.size() > 0;
    }
}
