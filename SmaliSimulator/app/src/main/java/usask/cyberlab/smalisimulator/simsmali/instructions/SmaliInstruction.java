package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;

import java.net.URLEncoder;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public abstract class SmaliInstruction {

    protected final int instructionPositionNumber;
    protected final String opCode;

    protected SmaliInstruction(Instruction instructionDef, int instructionPositionNumber) {
        this.instructionPositionNumber = instructionPositionNumber;
        this.opCode = instructionDef.getOpcode().name;
    }

    //This is only used for the FakeSuperConstructorCallInstruction
    protected SmaliInstruction(){
        instructionPositionNumber = -1;
        this.opCode = null;
    }

    public void executeWrapper(MethodExecution methodExecution){
        methodExecution.addToExecutionTrace(this);
        try {
            execute(methodExecution);
        }
        catch (SmaliSimulationException e){
            throw e;
        }
        catch (RuntimeException e){
            throw new SmaliSimulationException(e);
        }
        methodExecution.setInstructionPointer(methodExecution.getInstructionPointer() + 1);
    }

    protected abstract void execute(MethodExecution methodExecution);

    public abstract String toString();

    public String getOpCode(){
        return opCode ;
    }

    public abstract String getRegisterContents(MethodExecution methodExecution) throws JSONException;

    public static String getContentInfo(Register r){
        if(r.containsAmbiguousValue()) {
            AmbiguousValue av = r.getAmbiguousValue();
            return getAmbiguousValueLoggingInfo(av);
        }
        else if(r.containsRefToObject()){
            AbstractObjekt ao = r.getReferencedObjectValue();
            return getAbstractObjektLoggingInfo(ao);
        }
        else {
//            String s1 = Integer.toBinaryString(r.getIntValue());
//            if(s1.length() < 32){
//                s1 = SimulationUtils.zeroPadString(s1, 32 - s1.length());
//            }
//            return "Num:" + s1;
            return "Num:" + r.getIntValue();
        }
    }

    public static String getContentInfo(Register r, String regPrimitiveType){
        if(r.containsAmbiguousValue()) {
            AmbiguousValue av = r.getAmbiguousValue();
            return getAmbiguousValueLoggingInfo(av);
        }
        else if(r.containsRefToObject()){
            AbstractObjekt ao = r.getReferencedObjectValue();
            return getAbstractObjektLoggingInfo(ao);
        }
        else {
            if(regPrimitiveType.equals("I")){
                return "Num:" + r.getIntValue();
            }
            else if(regPrimitiveType.equals("F")){
                return "Num:" + r.getFloatValue();
            }
            else if(regPrimitiveType.equals("D")){
                return "Num:" + r.getDoubleValue();
            }
            else if(regPrimitiveType.equals("J")){
                return "Num:" + r.getLongValue();
            }
            else if(regPrimitiveType.equals("Z")){
                boolean b = r.getBooleanValue();
                if(b) {
                    return "Num:" + 1;
                }
                else {
                    return "Num:" + 0;
                }
            }
            else if(regPrimitiveType.equals("S")){
                return "Num:" + r.getShortValue();
            }
            else if(regPrimitiveType.equals("C")){
                return "Num:" + r.getCharValue();
            }
            else if(regPrimitiveType.equals("B")){
                return "Num:" + r.getByteValue();
            }
            else {
                return "Num:" + r.getIntValue();
            }
        }
    }


    protected static String getAbstractObjektLoggingInfo(AbstractObjekt ao){
        if(ao == null){
            return "Obj:null";
        }
        Object o = ao.getMirroringObject();
        String s = "";
        if(o == null){
            return "Obj:nullMirror";
        }

        if(!SimSmaliConfig.addToStringOfObjectsInExecutionLog){
            return "Obj:" + System.identityHashCode(o) + ",";
        }

        //Why I am only logging the java/android objects to string?
        // I think it was because to stop
        else if(!(ao.getClazz() instanceof SmaliClazz)){
            try {
               s = URLEncoder.encode(o.toString());
            }
            catch (AmbiguousValueReturnException av){
                s = getAmbiguousValueLoggingInfo(av.getAmbiguousValue());
            }
            catch (Exception e){
                s = "error on invocation of toString() method.";
            }

        }
        return "Obj:" + System.identityHashCode(o) + "," + s;
    }

    protected static String getAmbiguousValueLoggingInfo(AmbiguousValue av){
        return "Amb:" + av.getObjectID() + "," + av.getType();
    }

//    protected static void throwNullPointerExceptionOn(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ReflectedClazz nullExceptionClazz = (ReflectedClazz) loader.getClazz("Ljava/lang/NullPointerException;");
//        NullPointerException e = new NullPointerException();
//        e.setStackTrace(new StackTraceElement[0]);
//        Objekt nullPointerExceptionObjekt = new Objekt(nullExceptionClazz,
//                nullExceptionClazz.getClassPath(), e);
//        methodExecution.setThrownException(nullPointerExceptionObjekt);
//    }
//
//    protected static void throwArrayIndexOutOfBoundsExceptionOn(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/ArrayIndexOutOfBoundsException;");
//        ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException();
//        e.setStackTrace(new StackTraceElement[0]);
//        Objekt exceptionObjekt = new Objekt(clazz, clazz.getClassPath(), e);
//        methodExecution.setThrownException(exceptionObjekt);
//    }
//
//    protected static void throwArithmeticExceptionOn(MethodExecution methodExecution){
//        ClazzLoader loader = methodExecution.getClazzLoader();
//        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/ArithmeticException;");
//        ArithmeticException e = new ArithmeticException();
//        e.setStackTrace(new StackTraceElement[0]);
//        Objekt exceptionObjekt = new Objekt(clazz, clazz.getClassPath(), e);
//        methodExecution.setThrownException(exceptionObjekt);
//    }

    public static void throwExceptionOn(MethodExecution methodExecution, Class<? extends Exception> exceptionClass){
        String smaliStyleExceptionClassPath = SimulationUtils.makeSmaliStyleClassPath(exceptionClass.getName());
        ClazzLoader loader = methodExecution.getClazzLoader();
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz(smaliStyleExceptionClassPath);
        Exception e;
        try {
            e = exceptionClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e2) {
            throw new SmaliSimulationException(e2);
        }
        e.setStackTrace(new StackTraceElement[0]);
        Objekt exceptionObjekt = new Objekt(clazz, e);
        methodExecution.setThrownException(exceptionObjekt);
    }

    public int getInstructionPositionNumber() {
        return instructionPositionNumber;
    }
}
