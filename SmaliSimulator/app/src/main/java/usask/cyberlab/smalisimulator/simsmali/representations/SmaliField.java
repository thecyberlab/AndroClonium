package usask.cyberlab.smalisimulator.simsmali.representations;

import org.jf.dexlib2.writer.builder.BuilderEncodedValues;
import org.jf.dexlib2.writer.builder.BuilderField;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


/**
 * this class represents any filed defined in a smali file
 */
public class SmaliField extends SmaliItem {
    private final String name;
    private final String type;
    private final SmaliClass containingClass;
    private final Object initialValue;

    SmaliField( SmaliClass smaliClass, BuilderField fieldDef) {
        super(fieldDef.getAccessFlags());
        this.containingClass = smaliClass;
        this.name = fieldDef.getName();
        this.type = fieldDef.getType();
        this.initialValue = createInitialValue(fieldDef.getInitialValue(), type);
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isEnumField(){
        int isEnum = (flags & 0x00004000) / 0x00004000;
        return isEnum == 1;
    }

    public SmaliClass getContainingClass() {
        return containingClass;
    }

    public String toString(){
        return containingClass.getClassPath() + "->" + name+":"+type;
    }

    private static Object createInitialValue(BuilderEncodedValues.BuilderEncodedValue be, String type) {
        if(type.equals("I")){
            if(be == null){
                return 0;
            }
            int i = ((BuilderEncodedValues.BuilderIntEncodedValue) be).getValue();
            return i;
        }
        else if(type.equals("F")){
            if(be == null){
                return 0F;
            }
            float f = ((BuilderEncodedValues.BuilderFloatEncodedValue) be).getValue();
            return f;
        }
        else if(type.equals("J")){
            if(be == null){
                return 0L;
            }
            long j =  ((BuilderEncodedValues.BuilderLongEncodedValue) be).getValue();
            return j;
        }
        else if(type.equals("D")){
            if(be == null){
                return 0D;
            }
            double d = ((BuilderEncodedValues.BuilderDoubleEncodedValue) be).getValue();
            return d;
        }
        else if(type.equals("C")){
            if(be == null){
                return (char) 0;
            }
            char c = ((BuilderEncodedValues.BuilderCharEncodedValue) be).getValue();
            return c;
        }
        else if(type.equals("S")){
            if(be == null){
                return (short) 0;
            }
            short s = ((BuilderEncodedValues.BuilderShortEncodedValue) be).getValue();
            return s;
        }
        else if(type.equals("B")){
            if(be == null){
                return (byte) 0;
            }
            byte b = ((BuilderEncodedValues.BuilderByteEncodedValue) be).getValue();
            return b;
        }
        else if(type.equals("Z")){
            if(be == null){
                return false;
            }
            boolean z = ((BuilderEncodedValues.BuilderBooleanEncodedValue) be).getValue();
            return z;
        }
        else if(type.startsWith("[")){
            if(be == null){
                return null;
            }
            else if(be instanceof BuilderEncodedValues.BuilderNullEncodedValue){
                return null;
            }
            else {
                throw new IllegalStateException();
            }
        }
        else if(type.startsWith("L")){
            if(be == null){
                return null;
            }
            if(be instanceof BuilderEncodedValues.BuilderStringEncodedValue) {
                String s = ((BuilderEncodedValues.BuilderStringEncodedValue) be).getValue();
                return s;
            }
            else if(be instanceof BuilderEncodedValues.BuilderNullEncodedValue){
                return null;
            }
            else {
                throw new IllegalStateException();
            }
        }
        else{
            throw new IllegalStateException();
        }
    }

    public Object getInitialValue(){
        return initialValue;
    }

}
