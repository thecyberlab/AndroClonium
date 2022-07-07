package usask.cyberlab.smalisimulator.simsmali.emulator;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.preprocess.SmaliClassManager;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;

public class ExecutionTrace {
    // in this array we keep SmaliInstruction objects or another ExecutionTrace
    private final ArrayList<Object> instructionsOrNestedTraces = new ArrayList<>();
    // adding the runtime data of when an instruction is executed
    private final ArrayList<String> runtimeDataOfInstructions = new ArrayList<>();

//    private final ArrayList<String> invokedMethodActualTypes = new ArrayList<>();


    private final SmaliMethod smaliMethod;

    private final ClazzLoader clazzLoader;


    public ExecutionTrace(SmaliMethod sm, ClazzLoader clazzLoader){
        this.smaliMethod = sm;
        this.clazzLoader = clazzLoader;
    }

    public void addInstruction(SmaliInstruction si, MethodExecution methodExecution){
        if(si == null){
            throw new SmaliSimulationException("null instruction were passed!!");
        }
        if(methodExecution == null){
            throw new SmaliSimulationException("null method execution passed!!");
        }
        try {
            runtimeDataOfInstructions.add(si.getRegisterContents(methodExecution));
            instructionsOrNestedTraces.add(si);
        } catch (JSONException e) {
            throw new SmaliSimulationException(e);
        }

//        if(si instanceof InvokeInstruction){
//            InvokeInstruction ii = (InvokeInstruction) si;
//            invokedMethodActualTypes.add(ii.getMethodSignatureInMethodDefiningClass(methodExecution));
//        }
//        else {
//            invokedMethodActualTypes.add(null);
//        }

    }

    public void addExecutionTrace(ExecutionTrace et){
        if(et == null){
            throw new SmaliSimulationException("null ExecutionTrace were passed!!");
        }
        if(et == this) throw new SmaliSimulationException("ExecutionTrace should not be added to itself.");

        instructionsOrNestedTraces.add(et);
        runtimeDataOfInstructions.add(null);
//        invokedMethodActualTypes.add(null);
    }

    public void extendExecutionTrace(ExecutionTrace et){
        if(et == null){
            throw new SmaliSimulationException("null ExecutionTrace were passed!!");
        }
        if(et == this) throw new SmaliSimulationException("ExecutionTrace should not be added to itself.");

        if(et.instructionsOrNestedTraces.size() != et.runtimeDataOfInstructions.size()){
            throw new SmaliSimulationException("Execution trace has invalid content!!");
        }
        for (int i = 0; i < et.instructionsOrNestedTraces.size() ; i++) {
            Object o = et.instructionsOrNestedTraces.get(i);
            String s1 = et.runtimeDataOfInstructions.get(i);
//            String s2 = et.invokedMethodActualTypes.get(i);

            instructionsOrNestedTraces.add(o);
            runtimeDataOfInstructions.add(s1);
//            invokedMethodActualTypes.add(s2);
        }
    }

    public String getExecutionLogs(){
        StringBuilder sb = new StringBuilder();
        getExecutionLogs(sb);
        return sb.toString().trim();
    }

    private void getExecutionLogs(StringBuilder sb){
        if(instructionsOrNestedTraces.size() != runtimeDataOfInstructions.size()){
            throw new SmaliSimulationException("Invalid Execution trace state!!");
        }
        sb.append("Start of method context:").append(smaliMethod.getClassMethodSignature()).append("\n");
        for (int i = 0; i < instructionsOrNestedTraces.size(); i++) {
            Object o = instructionsOrNestedTraces.get(i);
            if(o instanceof SmaliInstruction){
                // Filling the method context
//                sb.append(prefix);
//                sb.append(smaliMethod.getClassMethodSignature());
//                sb.append(">>");

                // if the item is a invoke-* instruction we want to replace the
                // type declared in the instruction with the type that actually
                // defines the method.
                if(o instanceof InvokeInstruction){
                    InvokeInstruction ii = (InvokeInstruction) o;
                    String a = ii.getMethodSignatureInMethodDefiningClass(clazzLoader);
                    String s = o.toString().replace(ii.getClassMethodSignature(), a);
                    sb.append(s);
                }
                // for normal instructions we just add the instruction toString()
                else {
                    sb.append(o.toString());
                }

                // adding runtime metadata
                sb.append("|");
                sb.append(runtimeDataOfInstructions.get(i));


                // for cases where a method is invoked inside our execution
                // we need to add the methods metadata to our logs. we do this in two ways:
                // 1- if we have the logs of the invoked method we can just use the SmaliMethod in the nested ExecutionTrace
                // 2- if we don't have the logs of the invoked method, we can try to find it's corresponding SmaliMethod and use that
                if(i < instructionsOrNestedTraces.size() - 1 && instructionsOrNestedTraces.get(i+1) instanceof ExecutionTrace){
                    //check the previous instruction is invoke-*
                    Object pervObj = instructionsOrNestedTraces.get(i);
                    if(!(pervObj instanceof InvokeInstruction)){
                        throw new SmaliSimulationException("Invalid execution trace, nested trace without invoke-* call");
                    }
                    //TODO check pervObj is the correct invoke-*
                    SmaliMethod invokedSM = ((ExecutionTrace) instructionsOrNestedTraces.get(i+1)).smaliMethod;
                    addInvokedMethodInfoToLogs(sb, invokedSM);
                }
                else if(o instanceof InvokeInstruction){
                    InvokeInstruction invokeInstruction = (InvokeInstruction) o;
                    String methodSig = invokeInstruction.getClassMethodSignature();
                    // if the invoked method is invoked using reflection then we need to
                    // add the method info of SmaliMethod represented by the Method object
                    // which this method has been called upon
                    if(methodSig.equals("Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;")){
                        tryAddMethodInfoOfInvokedMethodWithReflection(invokeInstruction, runtimeDataOfInstructions.get(i), sb);
                    }
                    else if(methodSig.equals("Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;")){
                        tryAddConstructorInfoOfInvokedConstructorWithReflection(invokeInstruction, runtimeDataOfInstructions.get(i), sb);
                    }
                    else {
                        //The method you look for can be in java world
                        try {
                            String smaliClassPath = invokeInstruction.getClassPath();
                            if(!SimulationUtils.isJavaOrAndroidExistingClass(smaliClassPath)) {
                                SmaliClass smaliClass = clazzLoader.getSmaliClass(smaliClassPath);
                                SmaliMethod invokedSM = smaliClass.getMethod(invokeInstruction.getMethodOnlySignature());
                                addInvokedMethodInfoToLogs(sb, invokedSM);
                            }
                        }
                        catch (Exception e){}
                    }
                }

                sb.append("\n");
            }
            else {
                ExecutionTrace et = (ExecutionTrace) o;
//                String newPrefix = prefix + smaliMethod.getClassMethodSignature() + ">>";
//                String newPrefix = "";
                et.getExecutionLogs(sb);
            }
        }
        sb.append("End of method context:").append(smaliMethod.getClassMethodSignature()).append("\n");
//        return sb.toString();
    }


    private void tryAddMethodInfoOfInvokedMethodWithReflection(InvokeInstruction invokeInstruction,
                             String instructionRuntimeData,
                             StringBuilder sb){
        try {
            // we want to look at the info of the content of register
            // which points to the Method object to know that the value is not ambiguous
            JSONObject jo = new JSONObject(instructionRuntimeData);
            String methodRegName = "v" + invokeInstruction.getRegisterNumbers()[0];
            String rawMetadata = jo.getString(methodRegName).trim();

            // if valid object
            if(rawMetadata.startsWith("Obj:") && !rawMetadata.startsWith("Obj:null")) {

                // lets get rid of the first part of log which is object id and also
                // decode the string since it is urlencoded
                String rawMethodInfo = URLDecoder.decode(rawMetadata.substring(rawMetadata.indexOf(','+1)));

                // we need to convert the java syntax of Method.toString() to
                // smali syntax, the raw Method.toString() is like :
                //       public static void Main.main(java.lang.String[]) throws java.lang.Exception

                // 1- we get rid of the throws exception part at the end
                if(rawMethodInfo.contains(" throws ")){
                    rawMethodInfo = rawMethodInfo
                            .substring(rawMethodInfo.lastIndexOf(" throws "))
                            .trim();
                }
                if(!rawMethodInfo.endsWith(")")){
                    System.err.println("bad Method.name() string!! couldn't resolve it! " + rawMetadata);
                    return;
                }
                // 2- now we want to extract the method containing class, method name, arguments and return type
                String[] splits = rawMethodInfo.split(" ");

                String rawClassMethodSig = splits[splits.length - 1];
                String retType = convertArgType(splits[splits.length - 2]);

                int openParenthesisIndex = rawClassMethodSig.lastIndexOf('(');
                String rawArgTypes = rawClassMethodSig.substring(openParenthesisIndex + 1, rawClassMethodSig.length() - 1);

                int methodSeparationIndex = rawClassMethodSig.substring(0, openParenthesisIndex).lastIndexOf('.');
                String methodName = rawClassMethodSig.substring(methodSeparationIndex + 1, openParenthesisIndex);
                String rawClassPath = rawClassMethodSig.substring(0, methodSeparationIndex);

                String smaliClassPath = SimulationUtils.makeSmaliStyleClassPath(rawClassPath);
                StringBuilder smaliMethodSig = new StringBuilder(methodName + "(");
                for(String argType: rawArgTypes.split(",")){
                    smaliMethodSig.append(convertArgType(argType));
                }
                smaliMethodSig.append(")");
                smaliMethodSig.append(retType);
                //if the method we are looking at is a smali method,
                // we would add its metadata
                if(!SimulationUtils.isJavaOrAndroidExistingClass(smaliClassPath)) {
                    SmaliClass smaliClass = clazzLoader.getSmaliClass(smaliClassPath);
                    SmaliMethod invokedSM = smaliClass.getMethod(smaliMethodSig.toString());
                    addInvokedMethodInfoToLogs(sb, invokedSM);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryAddConstructorInfoOfInvokedConstructorWithReflection(InvokeInstruction invokeInstruction,
                                                                          String instructionRuntimeData,
                                                                          StringBuilder sb) {
        try {
            // we want to look at the info of the content of register
            // which points to the Method object to know that the value is not ambiguous
            JSONObject jo = new JSONObject(instructionRuntimeData);
            String ctorRegName = "v" + invokeInstruction.getRegisterNumbers()[0];
            String rawMetadata = jo.getString(ctorRegName).trim();

            // if valid object
            if(rawMetadata.startsWith("Obj:") && !rawMetadata.startsWith("Obj:null")) {

                // lets get rid of the first part of log which is object id and also
                // decode the string since it is urlencoded
                String rawCtorInfo = URLDecoder.decode(rawMetadata.substring(rawMetadata.indexOf(','+1)));

                // we need to convert the java syntax of Method.toString() to
                // smali syntax, the raw Constructor.toString() is like :
                //       public com.myPackage.testPackage.Test(java.lang.String)
                // 1- we get rid of the throws exception part at the end
                if(rawCtorInfo.contains(" throws ")){
                    rawCtorInfo = rawCtorInfo
                            .substring(rawCtorInfo.lastIndexOf(" throws "))
                            .trim();
                }
                if(!rawCtorInfo.endsWith(")")){
                    System.err.println("bad Method.name() string!! couldn't resolve it! " + rawMetadata);
                    return;
                }

                // 2- now we want to extract the method containing class, method name, arguments and return type
                String[] splits = rawCtorInfo.split(" ");
                String rawClassMethodSig = splits[splits.length - 1];
                int openParenthesisIndex = rawClassMethodSig.lastIndexOf('(');
                String rawArgTypes = rawClassMethodSig.substring(openParenthesisIndex + 1, rawClassMethodSig.length() - 1);
                String rawClassPath = rawClassMethodSig.substring(0, openParenthesisIndex);
                String smaliClassPath = SimulationUtils.makeSmaliStyleClassPath(rawClassPath);
                StringBuilder smaliMethodSig = new StringBuilder("<init>(");
                for(String argType: rawArgTypes.split(",")){
                    smaliMethodSig.append(convertArgType(argType));
                }
                smaliMethodSig.append(")V");
                //if the method we are looking at is a smali method,
                // we would add its metadata
                if(!SimulationUtils.isJavaOrAndroidExistingClass(smaliClassPath)) {
                    SmaliClass smaliClass = clazzLoader.getSmaliClass(smaliClassPath);
                    SmaliMethod invokedSM = smaliClass.getMethod(smaliMethodSig.toString());
                    addInvokedMethodInfoToLogs(sb, invokedSM);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String convertArgType(String argType){
        if(SimulationUtils.isPrimitiveType(argType)){
            return SimulationUtils.convertPrimitiveTypeFromJavaToSmaliStyle(argType);
        }
        else if(argType.contains("[")){
            int arrayDimension = SimulationUtils.countChar(argType, '[');
            String componentType = argType.replace("[","").replace("]", "");
            String cleanArgType = SimulationUtils.makeSmaliStyleClassPath(componentType);
            for (int j = 0; j < arrayDimension; j++) {
                cleanArgType += "[" + cleanArgType;
            }
            return cleanArgType;
        }
        else {
            return SimulationUtils.makeSmaliStyleClassPath(argType);
        }
    }

    private static void addInvokedMethodInfoToLogs(StringBuilder sb, SmaliMethod invokedSM){
        if(invokedSM == null) return;
        sb.append("|").append(SimulationUtils.getInvokedMethodInfoJsonStr(invokedSM));
    }

    public SmaliMethod getSmaliMethod(){
        return smaliMethod;
    }

    public ArrayList<Object> getInstructionsOrNestedTraces(){
        return instructionsOrNestedTraces;
    }

    // methods used for testing
    //=====================
    public int size(){
        return instructionsOrNestedTraces.size();
    }

    public Object get(int i){
        return instructionsOrNestedTraces.get(i);
    }

}
