package usask.cyberlab.smalisimulator.simsmali.types.classes;

import java.lang.reflect.Array;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

/**
 * This class is a wrapper for any Class that corresponds to an array of N dimension
 * of a SmaliClazz.
 */
public class SmaliArrayClazz extends Clazz{
    private final int dimensions;

    SmaliArrayClazz(String classPath, ClazzLoader loader) {
        super(loader, classPath);
        dimensions = SimulationUtils.countChar(classPath, '[');
        if(dimensions < 1){
            throw new SmaliSimulationException();
        }
        int baseTypeIndexStart = classPath.lastIndexOf('[') + 1;
        SmaliClazz baseTypeClazz = (SmaliClazz) loader.getClazz(classPath.substring(baseTypeIndexStart));
        mirroringClass = getArrayClass(baseTypeClazz.mirroringClass, dimensions);
    }

    private Class<?> getArrayClass(Class<?> baseTypeClass, int dimensions) {
        Class<?> arrayClass = baseTypeClass;
        for (int i = 0; i < dimensions; i++) {
            arrayClass = Array.newInstance(arrayClass, 0).getClass();
        }
        return arrayClass;
    }


    @Override
    public String getParentClassPath() {
        return "Ljava/lang/Object;";
    }

    @Override
    public Object getStaticFieldValue(String fieldName) {
        throw new SmaliSimulationException();
    }

    @Override
    public String getFieldType(String fieldName) {
        throw new SmaliSimulationException();
    }

    @Override
    public void setStaticFieldValue(String fieldName, Object fieldValue) {
        throw new SmaliSimulationException();
    }

    @Override
    public boolean isEnumClass() {
        return false;
    }

    @Override
    public boolean isInterfaceClass() {
        return false;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    public int getDimensions(){
        return dimensions;
    }
}