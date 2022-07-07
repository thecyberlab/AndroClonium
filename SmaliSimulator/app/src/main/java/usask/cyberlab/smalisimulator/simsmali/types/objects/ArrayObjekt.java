package usask.cyberlab.smalisimulator.simsmali.types.objects;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;

public class ArrayObjekt extends AbstractObjekt {

    // these arrays are the arrays that contain the mirroring array object
    int[] intArray;
    float[] floatArray;
    long[] longArray;
    double[] doubleArray;
    boolean[] booleanArray;
    short[] shortArray;
    byte[] byteArray;
    char[] charArray;
    Object[] objectArray;

    // this array keeps the wrapper objekt of the values
    // that will be assigned to objectArray. This is because
    // the array can point to an object that has inner ambiguous
    // fields or values that we need to keep track of
    AbstractObjekt[] wrapperObjektArray;

    // this array is used to keep the ambiguous values
    // that are kept in the array but we cannot put them in the real
    // array because java type checking does not allow it
    AmbiguousValue[] ambiguousMask;

    private enum ArrayType{
        INT,
        FLOAT,
        LONG,
        DOUBLE,
        BOOLEAN,
        SHORT,
        BYTE,
        CHAR,
        OBJECT
    }

    private ArrayType arrayType;

    private int arraySize;
    private int arrayDimension;

    public ArrayObjekt(Clazz clazz, Object mirroringObject) {
        super(clazz);
        if(!clazz.isArray()) throw new SmaliSimulationException();
        setMirroringObject(mirroringObject);
        this.ambiguousMask = new AmbiguousValue[this.arraySize];
        this.wrapperObjektArray = new AbstractObjekt[this.arraySize];
    }

    public int getSize(){
        return arraySize;
    }

    public int getDimension(){
        return arrayDimension;
    }

    public Object getValue(int index){
        Object retVal;
        if(ambiguousMask[index] != null) return ambiguousMask[index];
        switch (arrayType){
            case INT:
                retVal =  intArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case FLOAT:
                retVal = floatArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case LONG:
                retVal = longArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case DOUBLE:
                retVal = doubleArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case BOOLEAN:
                retVal = booleanArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case BYTE:
                retVal = byteArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case SHORT:
                retVal = shortArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case CHAR:
                retVal = charArray[index];
                return SimulationUtils.createWrapperObjektFromRawValue(retVal, true, getMyClassLoader());
            case OBJECT:
                // for array objekts created from smali classes we can just return the wrapper
                if(wrapperObjektArray[index] != null){
                    return wrapperObjektArray[index];
                }
                // for array objekts created in java world we should return the mirroring value
                // since the array if is initialized, the initialization was not done in simulator
                else {
                    retVal = objectArray[index];
                    String containerType = this.getType().substring(1);
                    return SimulationUtils.createWrapperObjektFromRawValue(retVal, SimulationUtils.isPrimitiveType(containerType), getMyClassLoader());
                }
            default:
                throw new SmaliSimulationException();
        }
    }

    public void setValue(int index, Object value){
        if(value instanceof AmbiguousValue){
            ambiguousMask[index] = (AmbiguousValue) value;
        }
        else {
            switch (arrayType) {
                case INT:
                    Integer i = (Integer) value;
                    intArray[index] = i;
                    break;
                case FLOAT:
                    Float f = (Float) value;
                    floatArray[index] = f;
                    break;
                case LONG:
                    Long l = (Long) value;
                    longArray[index] = l;
                    break;
                case DOUBLE:
                    Double d = (Double) value;
                    doubleArray[index] = d;
                    break;
                case BOOLEAN:
                    Boolean z = (Boolean) value;
                    booleanArray[index] = z;
                    break;
                case BYTE:
                    Byte b = (Byte) value;
                    byteArray[index] = b;
                    break;
                case SHORT:
                    Short s = (Short) value;
                    shortArray[index] = s;
                    break;
                case CHAR:
                    Character c = (Character) value;
                    charArray[index] = c;
                    break;
                case OBJECT:
                    if (value == null) {
                        objectArray[index] = null;
                        wrapperObjektArray[index] = null;
                    } else {
                        AbstractObjekt abstractObjekt = (AbstractObjekt) value;
                        wrapperObjektArray[index] = abstractObjekt;
                        Object v = abstractObjekt.getMirroringObject();
                        objectArray[index] = v;
                    }
                    break;
                default:
                    throw new SmaliSimulationException();
            }
            ambiguousMask[index] = null;
        }
    }

    @Override
    public void setMirroringObject(Object mirroringObject) {
        if(this.mirroringObject != null){
            throw new SmaliSimulationException("Mirroring Object should not be set twice!!");
        }
        String type = this.getType();
        if(type.equals("[I") && mirroringObject instanceof int[]){
            this.arrayType = ArrayType.INT;
            intArray = (int[]) mirroringObject;
            this.arraySize = intArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[F") && mirroringObject instanceof float[]){
            this.arrayType = ArrayType.FLOAT;
            floatArray = (float[]) mirroringObject;
            this.arraySize = floatArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[J") && mirroringObject instanceof long[]){
            this.arrayType = ArrayType.LONG;
            longArray = (long[]) mirroringObject;
            this.arraySize = longArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[D") && mirroringObject instanceof double[]){
            this.arrayType = ArrayType.DOUBLE;
            doubleArray = (double[]) mirroringObject;
            this.arraySize = doubleArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[Z") && mirroringObject instanceof boolean[]){
            this.arrayType = ArrayType.BOOLEAN;
            booleanArray = (boolean[]) mirroringObject;
            this.arraySize = booleanArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[S") && mirroringObject instanceof short[]){
            this.arrayType = ArrayType.SHORT;
            shortArray = (short[]) mirroringObject;
            this.arraySize = shortArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[B") && mirroringObject instanceof byte[]){
            this.arrayType = ArrayType.BYTE;
            byteArray = (byte[]) mirroringObject;
            this.arraySize = byteArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(type.equals("[C") && mirroringObject instanceof char[]){
            this.arrayType = ArrayType.CHAR;
            charArray = (char[]) mirroringObject;
            this.arraySize = charArray.length;
            this.arrayDimension = 1;
            this.mirroringObject = mirroringObject;
        }
        else if(mirroringObject instanceof Object[]){
            this.arrayType = ArrayType.OBJECT;
            Object[] array= (Object[]) mirroringObject;
            String s = SimulationUtils.makeSmaliStyleClassPath(array.getClass().getName());
            if(!this.getType().equals(s)) throw new SmaliSimulationException(type + " conflicts with " + s + " when setting mirrorValue");
            objectArray = array;
            this.arraySize = objectArray.length;
            this.arrayDimension = SimulationUtils.countChar(type, '[');
            this.mirroringObject = mirroringObject;
        }
        else {
            throw new SmaliSimulationException(mirroringObject.toString() + "|ArrayType:" + type);
        }
    }

    public boolean containsAmbiguousMasks(){
        for (AmbiguousValue ambiguousValue : ambiguousMask) {
            if (ambiguousValue != null) return true;
        }
        return false;
    }

}
