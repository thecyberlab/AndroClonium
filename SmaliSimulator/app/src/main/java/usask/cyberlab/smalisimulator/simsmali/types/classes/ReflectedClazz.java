package usask.cyberlab.smalisimulator.simsmali.types.classes;

import java.lang.reflect.Field;
import java.util.HashMap;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

/**
 * ReflectedClazz is a wrapper classes that already existed in the system and are not loaded from smali files.
 * These classes are java/Android classes that we can easily access using the reflection API.
 * These classes can also refer to array types of any other java/Android class.
 */
public class ReflectedClazz extends Clazz {

    /**
     * this map keep track of fields that we have assigned an AmbiguousValue to them
     */
    protected final HashMap<String, AbstractObjekt> staticFieldsWrappers = new HashMap<>();

    public ReflectedClazz(ClazzLoader loader, String className, Class mirroringClass) {
        super(loader, className, mirroringClass);
    }

    @Override
    public String getParentClassPath() {
        Class cls = mirroringClass.getSuperclass();
        if(cls == null){
            return null;
        }
        return SimulationUtils.makeSmaliStyleClassPath(cls.getName());
    }

    @Override
    public Object getStaticFieldValue(String fieldName) {
        Field field;
        try {
             field = resolveField(mirroringClass, fieldName);
        } catch (NoSuchFieldException e) {
            throw new SmaliSimulationException(fieldName + " not found!", e);
        }
        try {
            String fieldType = this.getFieldType(fieldName);
            if(SimulationUtils.isPrimitiveType(fieldType)){
                Object rawValue = field.get(mirroringClass);
                return SimulationUtils.createWrapperObjektFromRawValue(rawValue, true, this.clazzLoader);
            }
            else {
                if(staticFieldsWrappers.containsKey(fieldName)){
                    return this.staticFieldsWrappers.get(fieldName);
                }
                else {
                    Object rawValue = field.get(mirroringClass);
                    return SimulationUtils.createWrapperObjektFromRawValue(rawValue, false, clazzLoader);
                }
            }

        } catch (IllegalAccessException e) {
            throw new SmaliSimulationException();
        }
    }

    @Override
    public void setStaticFieldValue(String fieldName, Object newValue) {
        if(newValue instanceof AmbiguousValue){
            throw new SmaliSimulationException("Java/Android Class fields cannot have Ambiguous Values (yet?)!");
        }
        else {
            Field field;
            try {
                field = resolveField(mirroringClass, fieldName);
            } catch (NoSuchFieldException e) {
                throw new SmaliSimulationException(e);
            }
            SimulationUtils.setFieldValue(field, newValue, mirroringClass);

            // if the field type is not primitive then it has a wrapper and
            // we should also keep the wrapper
            if( ! SimulationUtils.isPrimitiveType(this.getFieldType(fieldName)) ) {
                this.staticFieldsWrappers.put(fieldName, (AbstractObjekt) newValue);
            }
        }
    }
}
