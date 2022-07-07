package usask.cyberlab.smalisimulator.simsmali.emulator;

import org.jetbrains.annotations.NotNull;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

import static com.google.common.primitives.UnsignedInts.parseUnsignedInt;
import static com.google.common.primitives.UnsignedLongs.parseUnsignedLong;

/**
 * This class represents a register in the Dalvik bytecode.
 * In in Dalvik bytecode registers are all 32 bit in size and each one is identified with a number.
 * In dalvik bytecode most primitive types and object references are 32 bit long
 * but the values of type "long" and "double" take 64 bit which use two registers
 */
public class Register {

    /**
     *  In our simulator each register can be in 3 different states:
     *  1-containing a primitive type (int,float,long,double,boolean,char,short,byte)
     *  2-containing a pointer to a Objekt
     *  3-containing a pointer to an AmbiguousValue
     */
    public enum ContainedDataType {
        NUMBER,
        REF_TO_OBJECT,
        AMBIGUOUS_VALUE,
    }

    // register identifier
    private final int registerNumber;
    // state of the register
    private ContainedDataType type;
    // the MethodExecution object which holds the map all registers
    private final MethodExecution methodExecution;

    // using an int to represent the 32 bit
    private int intValue;
    // the referenced object wrapped in a AbstractObjekt class
    private AbstractObjekt objectValue;
    // the ambiguous value contained in this register
    private AmbiguousValue ambiguousValue;


    /**
     *
     * @param registerNumber the number associated with each register which is used as its identifier
     * @param methodExecution the MethodExecution that keep track of all registers including this one.
     *                       The reason a register need to hold the instance of MethodExecution is that
     *                        in case of values of type double or long which take 64 bits, it needs to access the next register
     */
    public Register(int registerNumber, MethodExecution methodExecution){
        this.registerNumber = registerNumber;
        this.methodExecution = methodExecution;
        intValue = 0;
        this.type = ContainedDataType.NUMBER;
    }


    public boolean containsNumericData(){
        return type == ContainedDataType.NUMBER;
    }


    public boolean containsAmbiguousValue(){
        return type == ContainedDataType.AMBIGUOUS_VALUE;
    }

    public boolean containsRefToObject(){
        // Dalvik bytecode uses the number 0 to indicate a Null value
        // To keep things simple we also use number 0 to indicate Null too
        if(type == ContainedDataType.NUMBER && intValue == 0){
            return true;
        }
        return type == ContainedDataType.REF_TO_OBJECT;
    }

    /**
     *
     * @return the int number represented by the 32 bits in this register
     */
    public int getIntValue() {
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return intValue;
    }

    /**
     *
     * @return the float number represented by the 32 bits in this register
     */
    public float getFloatValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return Float.intBitsToFloat(intValue);
    }

    /**
     *
     * @return the long number represented by the 64 bits in this register
     * and the register with the next number
     */
    public long getLongValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        String s1 = Integer.toBinaryString(intValue);
        String s2 = Integer.toBinaryString(methodExecution.getRegister(registerNumber + 1).getIntValue());
        if(s1.length() < 32){
            s1 = SimulationUtils.zeroPadString(s1, 32 - s1.length());
        }
        if(s2.length() < 32){
            s2 = SimulationUtils.zeroPadString(s2, 32 - s2.length());
        }
        return parseUnsignedLong(s1+ s2, 2);
    }

    /**
     *
     * @return the double number represented by the 64 bits in this register
     * and the register with the next number
     */
    public double getDoubleValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        Register r2 = methodExecution.getRegister(registerNumber + 1);
        String s1 = Integer.toBinaryString(intValue);
        String s2 = Integer .toBinaryString(r2.getIntValue());
        if(s1.length() < 32){
            s1 = SimulationUtils.zeroPadString(s1, 32 - s1.length());
        }
        if(s2.length() < 32){
            s2 = SimulationUtils.zeroPadString(s2, 32 - s2.length());
        }
        return Double.longBitsToDouble(parseUnsignedLong(s1+ s2, 2));
    }

    /**
     *
     * @return the boolean value represented by the 32 bits in this register.
     * In case of booleans the first bit is only important
     */
    public boolean getBooleanValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return intValue % 2 != 0;
    }

    /**
     *
     * @return the byte value represented by the 32 bits in this register.
     * In case of byte values the first 8 bits are important
     */
    public byte getByteValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return (byte) intValue;
    }

    /**
     *
     * @return the short value represented by the 32 bits in this register.
     * In case of short values the first 16 bits are important
     */
    public short getShortValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return (short) intValue;
    }

    /**
     *
     * @return the char value represented by the 32 bits in this register.
     * In case of char values the first 16 bits are important
     */
    public char getCharValue(){
        if (type != ContainedDataType.NUMBER){
            throw new SmaliSimulationException("The register is not in number state! state is "+type);
        }
        return (char) intValue;
    }

    public AbstractObjekt getReferencedObjectValue() {
        // NULL-zero case
        if(type == ContainedDataType.NUMBER && intValue == 0){
            return null;
        }
        else if(type != ContainedDataType.REF_TO_OBJECT){
            throw new SmaliSimulationException("The register is not in object state! state is "+type);
        }
        return objectValue;
    }

    public AmbiguousValue getAmbiguousValue(){
        if (type != ContainedDataType.AMBIGUOUS_VALUE){
            throw new SmaliSimulationException("The register does not contain Ambiguous value! state is " + type);
        }
        if (ambiguousValue==null){
            throw new SmaliSimulationException("The Ambiguous value object is Null!!");
        }
        return ambiguousValue;
    }

    public void set(int intValue)  {
        type = ContainedDataType.NUMBER;
        this.intValue = intValue;
        this.objectValue = null;
        this.ambiguousValue = null;
    }

    public void set(float floatValue)  {
        type = ContainedDataType.NUMBER;
        this.intValue = Float.floatToIntBits(floatValue);
        this.objectValue = null;
        this.ambiguousValue = null;
    }

    public void set(AbstractObjekt objectValue){
        if(objectValue != null) {
            type = ContainedDataType.REF_TO_OBJECT;
            this.objectValue = objectValue;
            this.ambiguousValue = null;
            this.intValue = 0;
        }
        // A null object. Dalvik uses number 0 to indicate the Null value
        // so we do same here
        else {
            type = ContainedDataType.NUMBER;
            this.intValue = 0;
            this.ambiguousValue = null;
            this.objectValue = null;
        }
    }

    public void set(long l){
        type = ContainedDataType.NUMBER;
        String s = Long.toBinaryString(l);
        if(s.length() < 64){
            s = SimulationUtils.zeroPadString(s, 64 - s.length());
        }
        int v1 = parseUnsignedInt(s.substring(0,32), 2);
        int v2 = parseUnsignedInt(s.substring(32), 2);
        this.set(v1);
        methodExecution.getRegister(registerNumber + 1).set(v2);
    }

    public void set(double d){
        type = ContainedDataType.NUMBER;
        String s = Long.toBinaryString(Double.doubleToLongBits(d));
        if(s.length() < 64){
            s = SimulationUtils.zeroPadString(s, 64 - s.length());
        }
        int v1 = parseUnsignedInt(s.substring(0,32), 2);
        int v2 = parseUnsignedInt(s.substring(32), 2);
        this.set(v1);
        methodExecution.getRegister(registerNumber + 1).set(v2);
    }

    public void set(boolean b){
        type = ContainedDataType.NUMBER;
        if(b){
            intValue = 1;
        }
        else {
            intValue = 0;
        }
        this.objectValue = null;
        this.ambiguousValue = null;
    }

    public void set(@NotNull AmbiguousValue ambiguousValue){
        this.ambiguousValue = ambiguousValue;
        this.type = ContainedDataType.AMBIGUOUS_VALUE;
        this.objectValue = null;
        this.intValue = 0;
    }

}
