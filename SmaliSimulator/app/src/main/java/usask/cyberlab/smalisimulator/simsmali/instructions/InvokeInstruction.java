package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.MethodDepthExceeded;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public abstract class InvokeInstruction extends SmaliInstruction{

    protected final int[] registerNumbers;

    protected final String classMethodSignature;
    protected final String classPath;
    protected final String methodOnlySignature;

    protected final String[] argTypes;
    protected final String methodReturnType;



    public InvokeInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction35c){
            BuilderInstruction35c bi = (BuilderInstruction35c) instructionDef;
            this.classMethodSignature = bi.getReference().toString();
            int[] tmp = new int[5];
            tmp[0] = bi.getRegisterC();
            tmp[1] = bi.getRegisterD();
            tmp[2] = bi.getRegisterE();
            tmp[3] = bi.getRegisterF();
            tmp[4] = bi.getRegisterG();
            this.registerNumbers = new int[bi.getRegisterCount()];
            if (registerNumbers.length >= 0) System.arraycopy(tmp, 0, registerNumbers, 0, registerNumbers.length);
        }
        else if(instructionDef instanceof BuilderInstruction3rc){
            BuilderInstruction3rc bi = (BuilderInstruction3rc) instructionDef;
            this.classMethodSignature = bi.getReference().toString();
            this.registerNumbers = new int[bi.getRegisterCount()];
            for(int i =0; i< registerNumbers.length; i++){
                registerNumbers[i] = bi.getStartRegister() + i;
            }
        }
        else {
            throw new SmaliSimulationException();
        }
        classPath = classMethodSignature.split("->")[0];
        methodOnlySignature = classMethodSignature.split("->")[1];
        String argsString = methodOnlySignature.substring(methodOnlySignature.indexOf('(') + 1, methodOnlySignature.indexOf(')'));
        this.argTypes = SimulationUtils.parseMethodArgumentsString(argsString);
        methodReturnType = methodOnlySignature.substring(methodOnlySignature.indexOf(')') + 1);
    }

    public static MethodExecutionResult createMethodExecutionResultFromUnwrappedResults(Object result,
                                                                                           ClazzLoader loader,
                                                                                           String retType) {
        MethodExecutionResult newResult;
        if(retType.equals("V")){
            newResult = new MethodExecutionResult((AbstractObjekt) null, ResultType.VOID, null);
        }
        else if(result instanceof AmbiguousValue){
            AmbiguousValue av = (AmbiguousValue) result;
            newResult = new MethodExecutionResult(av, null);
        }
        else if(retType.equals("I")){
            if(!(result instanceof Integer)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Integer) result, null);
        }
        else if(retType.equals("F")){
            if(!(result instanceof Float)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Float) result, null);
        }
        else if(retType.equals("Z")){
            if(!(result instanceof Boolean)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Boolean) result,null);
        }
        else if(retType.equals("J")){
            if(!(result instanceof Long)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Long) result, null);
        }
        else if(retType.equals("D")){
            if(!(result instanceof Double)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Double) result, null);
        }
        else if(retType.equals("S")){
            if(!(result instanceof Short)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Short) result, null);
        }
        else if(retType.equals("B")){
            if(!(result instanceof Byte)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Byte) result, null);
        }
        else if(retType.equals("C")){
            if(!(result instanceof Character)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Character) result, null);
        }
        else if(retType.startsWith("L") || retType.startsWith("[")){
            if(result == null){
                newResult = new MethodExecutionResult((AbstractObjekt) null, ResultType.OBJECT, null);
            }
            else {
                String resultObjType = result.getClass().getName();
                String smaliStyleObjType = SimulationUtils.makeSmaliStyleClassPath(resultObjType);
                Clazz clazz = loader.getClazz(smaliStyleObjType);
                AbstractObjekt resultAbstractObjekt;
                if(clazz.isArray()){
                    resultAbstractObjekt = new ArrayObjekt(clazz, result);
                }
                else {
                    resultAbstractObjekt = new Objekt(clazz, result);
                }
                newResult = new MethodExecutionResult(resultAbstractObjekt, ResultType.OBJECT,null);
            }
        }
        else {
            throw new SmaliSimulationException();
        }
        return newResult;
    }


    public static MethodExecutionResult createMethodExecutionResultFromWrappedResults(Object result,
                                                                                         String retType){
        MethodExecutionResult newResult;
        if(retType.equals("V")){
            newResult = new MethodExecutionResult((AbstractObjekt) null, ResultType.VOID, null);
        }
        else if(result instanceof AmbiguousValue){
            AmbiguousValue av = (AmbiguousValue) result;
            newResult = new MethodExecutionResult(av, null);
        }
        else if(retType.equals("I")){
            if(!(result instanceof Integer)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Integer) result, null);
        }
        else if(retType.equals("F")){
            if(!(result instanceof Float)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Float) result, null);
        }
        else if(retType.equals("Z")){
            if(!(result instanceof Boolean)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Boolean) result,null);
        }
        else if(retType.equals("J")){
            if(!(result instanceof Long)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Long) result, null);
        }
        else if(retType.equals("D")){
            if(!(result instanceof Double)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Double) result, null);
        }
        else if(retType.equals("S")){
            if(!(result instanceof Short)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Short) result, null);
        }
        else if(retType.equals("B")){
            if(!(result instanceof Byte)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Byte) result, null);
        }
        else if(retType.equals("C")){
            if(!(result instanceof Character)){
                throw new SmaliSimulationException();
            }
            newResult = new MethodExecutionResult((Character) result, null);
        }
        else if(retType.startsWith("L") || retType.startsWith("[")){
            if(result == null){
                newResult = new MethodExecutionResult((AbstractObjekt) null, ResultType.OBJECT, null);
            }
            else {
                if(!(result instanceof AbstractObjekt)) throw new SmaliSimulationException();
                AbstractObjekt resultAbstractObjekt = (AbstractObjekt) result;
                newResult = new MethodExecutionResult(resultAbstractObjekt, ResultType.OBJECT,null);
            }
        }
        else {
            throw new SmaliSimulationException();
        }
        return newResult;
    }


    protected Object[] getMethodArgWrapperValues(MethodExecution methodExecution){
        Object[] res = new Object[this.argTypes.length];
        int argRegCounter ;
        if(this instanceof InvokeStaticInstruction) {
            argRegCounter = 0;
        }else {
            argRegCounter = 1;
        }
        for(int i=0; i < res.length; i++){
            Register reg = methodExecution.getRegister(registerNumbers[argRegCounter]);
            String argType = argTypes[i];
            // if the parameter type is not primitive
            if(argType.length() != 1){
                if(reg.containsRefToObject()){
                    AbstractObjekt ao = reg.getReferencedObjectValue();
                    res[i] = ao;
                }
                else if(reg.containsAmbiguousValue()){
                    res[i] = reg.getAmbiguousValue();
                }
                else {
                    throw new SmaliSimulationException();
                }

            }
            // else if the parameter type is primitive
            else {
                if(argType.equals("I")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getIntValue();
                }
                else if(argType.equals("F")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getFloatValue();
                }
                else if(argType.equals("D")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getDoubleValue();
                    argRegCounter++;
                }
                else if(argType.equals("J")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getLongValue();
                    argRegCounter++;
                }
                else if(argType.equals("Z")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getBooleanValue();
                }
                else if(argType.equals("B")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getByteValue();
                }
                else if(argType.equals("S")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getShortValue();
                }
                else if(argType.equals("C")){
                    if(reg.containsAmbiguousValue()) res[i] = reg.getAmbiguousValue();
                    else res[i] = reg.getCharValue();
                }
                else {
                    throw new SmaliSimulationException();
                }
            }
            argRegCounter ++;
        }

        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.opCode);
        for (int r:registerNumbers) {
            sb.append(" v");
            sb.append(r);
        }
        sb.append(" ");
        sb.append(classMethodSignature);
        sb.append(" &");
        sb.append(instructionPositionNumber);
        return sb.toString();
    }


    protected void makeAllObjectParametersRegistersAmbiguous(MethodExecution methodExecution){
        int regCounter = 0;
        for(int i =0; i < argTypes.length; i++){
            String argType = argTypes[i];
            int regNumber = registerNumbers[regCounter];
            if(argType.startsWith("[") || argType.startsWith("L")){
                Register r = methodExecution.getRegister(regNumber);
                // if the argument is not already Ambiguous or null
                if(r.containsRefToObject()){
                    AbstractObjekt ao = r.getReferencedObjectValue();
                    // if a type is immutable we also should not turn it to ambiguous
                    if(ao != null && !methodExecution.getClazzLoader().isTypeImmutable(ao.getType())) {
                        r.set(new AmbiguousValue(ao.getType(), System.identityHashCode(ao.getMirroringObject())));
                    }
                }
            }
            else if(argType.equals("D") || argType.equals("J")){
                regCounter++;
            }
            regCounter++;
        }
    }


    protected static boolean argumentsContainAmbiguousValue(Object[] argVals) {
        for (Object o: argVals) {
            if(o instanceof AmbiguousValue) return true;
            else if(o instanceof ArrayObjekt){
                ArrayObjekt ao = (ArrayObjekt) o;
                if(ao.containsAmbiguousMasks()) return true;
                // TODO indirect references to objects with ambiguousValues has to be handled later
            }
            else if(o instanceof Objekt){
                Objekt ok = (Objekt) o;
                if(ok.containsAmbiguousMasks()) return true;
                // TODO indirect references to objects with ambiguousValues has to be handled later
            }
        }
        return false;
    }

    protected static boolean isStaticMethodUnSafe(Clazz methodContainingClazz, Method method,
                                           ClazzLoader loader){
        String methodSig = SimulationUtils.getSmaliMethodSignature(method);
        if( ! Modifier.isStatic(method.getModifiers())) throw new SmaliSimulationException();
        if(methodContainingClazz instanceof SmaliClazz) return false;
        // The reason for this is that static methods can be called using the child class path
        // but we want to use the original parent class path that is defining the class
        String trueClassPath = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());
        if(loader.isInStaticSafeClasses(trueClassPath)) return false;
        if(loader.isInStaticPartialSafeClasses(trueClassPath + "->" + methodSig)) return false;
        return true;
    }

    protected static boolean isInstanceMethodUnSafe(Method method,
                                           AbstractObjekt refObjekt,
                                           ClazzLoader loader){
        if(Modifier.isStatic(method.getModifiers())) throw new SmaliSimulationException();

        String methodRefObjectClassPath = refObjekt.getType();
        if(loader.isInInstanceSafeClasses(methodRefObjectClassPath)) return false;

        String methodDefiningClassPath = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());

        Clazz clazz = loader.getClazz(methodDefiningClassPath);
        String methodSig = SimulationUtils.getSmaliMethodSignature(method);

        //TODO-----------
        if(methodSig.contains("_superWrapper")){
            methodSig = methodSig.replace("_superWrapper", "");
        }


        if(clazz instanceof SmaliClazz) return false;
        if(loader.isInInstanceSafeClasses(clazz.getClassPath())) return false;
        if(loader.isInInstancePartialSafeClasses(clazz.getClassPath() + "->" + methodSig)) return false;
        return true;
    }

    protected static boolean isInstanceMethodUnSafe(Constructor<?> constructorMethod,
                                             AbstractObjekt refObjekt,
                                                    ClazzLoader loader){
        String methodSig = SimulationUtils.getSmaliMethodSignature(constructorMethod);

        String methodRefObjectClassPath = refObjekt.getType();
        if(loader.isInInstanceSafeClasses(methodRefObjectClassPath)) return false;

        String methodDefiningClassPath = SimulationUtils.makeSmaliStyleClassPath(constructorMethod.getDeclaringClass().getName());
        Clazz clazz = loader.getClazz(methodDefiningClassPath);
        if(clazz instanceof SmaliClazz) return false;
        if(loader.isInInstanceSafeClasses(clazz.getClassPath())) return false;
        if(loader.isInInstancePartialSafeClasses(clazz.getClassPath() + "->" + methodSig)) return false;
        return true;
    }


//    private static  MethodExecutionResult fixPossibleTypeConversion(MethodExecutionResult otherMR, ClazzLoader loader, String methodReturnType) {
//        if(SimulationUtils.isResultTypePrimitiveType(otherMR.getType()) && methodReturnType.startsWith("L")){
//            return createMethodExecutionResultFromUnwrappedResults(otherMR.getResult(),loader, methodReturnType);
//        }
//        else if(otherMR.getType() == ResultType.AMBIGUOUS_VALUE){
//            AmbiguousValue av = (AmbiguousValue) otherMR.getResult();
//            if(SimulationUtils.isPrimitiveType(av.getType()) && methodReturnType.startsWith("L")){
//                AmbiguousValue newAv = new AmbiguousValue(methodReturnType);
//                return createMethodExecutionResultFromUnwrappedResults(newAv, loader, methodReturnType);
//            }
//        }
//        return otherMR;
//    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        JSONObject jo = new JSONObject();
        for(int rNum: registerNumbers) {
            jo.put("v" + rNum, getContentInfo(methodExecution.getRegister(rNum)));
        }
        // this meta information is needed when we need to analyze traces
        // with reflection calls later
        if("Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".equals(this.classMethodSignature)){
            Register r = methodExecution.getRegister(this.registerNumbers[2]);
            if(r.containsRefToObject()){
                AbstractObjekt AbstractObjekt = r.getReferencedObjectValue();
                if(AbstractObjekt == null){
                    jo.put("actualArgsInfo", "null");
                }
                else if(AbstractObjekt instanceof ArrayObjekt){
                    ArrayObjekt arrayObjekt = (ArrayObjekt) AbstractObjekt;
                    JSONObject extraInfo = new JSONObject();
                    for (int i = 0; i < arrayObjekt.getSize(); i++) {
                        Object o = arrayObjekt.getValue(i);
                        if(o instanceof AmbiguousValue){
                            extraInfo.put(i + "", getAmbiguousValueLoggingInfo((AmbiguousValue) o));
                        }
                        else {
                            extraInfo.put( i+"", getAbstractObjektLoggingInfo((AbstractObjekt) o));
                        }
                    }
                    jo.put("actualArgsInfo", extraInfo);
                }

            }
        }
        return jo.toString();
    }

    //TODO better naming and adding some comments
    public String getMethodSignatureInMethodDefiningClass(ClazzLoader loader){
        if(classMethodSignature.contains("<clinit>") || classMethodSignature.contains("<init>")){
            return classMethodSignature;
        }
        else {
            Clazz clazz = loader.getClazz(this.classPath);
            String methodName = this.methodOnlySignature.split("\\(")[0];
            Class[] argsClasses = SimulationUtils.getTypeClasses(this.argTypes, loader);
            try {
                Method method = Clazz.resolveMethodInClass(clazz.getMirroringClass(), methodName , argsClasses);
                method.setAccessible(true);
                String c = SimulationUtils.makeSmaliStyleClassPath(method.getDeclaringClass().getName());
                String m = classMethodSignature.split("->")[1];
                return c + "->" + m ;
            }catch (Exception e){
                return classMethodSignature;
            }

        }
    }


    // this method is used to unwrap method arguments for passing them method invocations
    // this method assumes the arguments are not ambiguous and they execpt primitive types
    // other objects have been properly wrapped
    protected static Object[] unwrapArgObjects(Object[] objects, Class[] argTypes){
        Object[] res = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object o = objects[i];
            if(o instanceof AbstractObjekt){
                res[i] = ((AbstractObjekt) o).getMirroringObject();
            }
            else if(o instanceof AmbiguousValue){
                Class c = argTypes[i];
                if(c.isPrimitive()){
                    String s = SimulationUtils.makeSmaliStyleClassPath(c.getName());
                    res[i] = SimulationUtils.getDefaultPrimitiveValue(s.charAt(0));
                }
                else {
                    res[i] = null;
                }
            }
            else{
                res[i] = o;
            }
        }
        return res;
    }

    public void handleMethodInvocationDepthExceed(MethodExecution methodExecution){
        ClazzLoader loader = methodExecution.getClazzLoader();
        if(SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed){
            AmbiguousValue av = new AmbiguousValue(methodReturnType);
            methodExecution.setInvokedFunctionExecutionResult(createMethodExecutionResultFromUnwrappedResults(av, loader, av.getType()));
        }
        else {
            ReflectedClazz methodDepthExceptionClazz = (ReflectedClazz) loader.getClazz("Lusask/cyberlab/smalisimulator/simsmali/exceptions/MethodDepthExceeded;");
            Exception e =  new MethodDepthExceeded();
            e.setStackTrace(new StackTraceElement[0]);
            Objekt methodDepthExceptionObjekt = new Objekt(methodDepthExceptionClazz, e);
            methodExecution.setThrownException(methodDepthExceptionObjekt);
        }
    }

    public String getClassPath(){
        return classPath;
    }

    public String getClassMethodSignature(){
        return classMethodSignature;
    }

    public String getMethodOnlySignature(){
        return methodOnlySignature;
    }

    public int[] getRegisterNumbers() {
        return registerNumbers;
    }

    public String[] getArgTypes() {
        return argTypes;
    }
}
