import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.net.URLDecoder;
import java.util.HashSet;


public class ReflectionNormalizer {


    public static void removeReflectionCalls(ExecutionTrace exeTrace,
                                             ArrayList<ExecutionTrace> parentExecutions,
                                             HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap) {
        if (trueArgsSrcInstMap == null) {
            throw new IllegalArgumentException("trueArgsSrcInstMap should not be null!");
        }

        if (parentExecutions == null) {
            throw new IllegalArgumentException("parentExecutions should not be null!");
        }

        for (int i = 0; i < exeTrace.traceArray.size(); i++) {
            Object o = exeTrace.traceArray.get(i);
            // for instructions check if they are Reflection Method.invoke()
            // instructions and if so normalize them
            if (o instanceof TraceInstruction) {
                TraceInstruction underAnalysisInstruction = (TraceInstruction) o;
                // sample Method.invoke() instruction :
                // invoke-virtual va vb vc Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object; &n
                if (underAnalysisInstruction.getInstruction().startsWith("invoke-virtual")) {
                    String[] originalInstructionSplits = underAnalysisInstruction.getInstructionSplits();
                    String originalInstructionMethodSig = originalInstructionSplits[originalInstructionSplits.length - 2];
                    if ("Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;".equals(originalInstructionMethodSig)) {
                        String toInvokeMethodSig;
                        boolean toInvokeMethodIsStaticMethod = false;
                        boolean toInvokeMethodIsPrivateMethod = false;

                        // here from the instruction run-time logs we want to find out what is the invoked method
                        try {
                            toInvokeMethodSig = underAnalysisInstruction.getToInvokeMethodInfo().getString("signature");
                            String toInvokeMethodModifiers = underAnalysisInstruction.getToInvokeMethodInfo().getString("modifiers").toLowerCase();
                            if(toInvokeMethodModifiers.contains("static")){
                                toInvokeMethodIsStaticMethod = true;
                            }

                            if(toInvokeMethodModifiers.contains("private")){
                                toInvokeMethodIsPrivateMethod = true;
                            }


                        }
                        catch (Exception e) {
                            //TODO (This can be fixed by how things are logged in the simulator?)
                            // if the method does not have the InvokeMethodInfo logs
                            // we have to use the toString value in the matching register of getRegisterContentsInfo
                            JSONObject regContentInfo = underAnalysisInstruction.getRegisterContentsInfo();
                            String MethodRefObjectRegNum = underAnalysisInstruction.getInstructionSplits()[1];
                            String methodObjToString;
                            try {
                                methodObjToString = regContentInfo.getString(MethodRefObjectRegNum);
                                if(methodObjToString.startsWith("Amb:")){
                                    continue;
                                }
                            } catch (JSONException jsonException) {
                                continue;
                            }

                            methodObjToString = methodObjToString.substring(methodObjToString.indexOf(',')+1);
                            if(methodObjToString.startsWith("private") || methodObjToString.contains(" private ")){
                                toInvokeMethodIsPrivateMethod = true;
                            }
                            //TODO make check condition better what if ends with "static"?
                            if(methodObjToString.startsWith("static") || methodObjToString.contains(" static ")){
                                toInvokeMethodIsStaticMethod = true;
                            }
                            // URL decoding
                            if(methodObjToString.contains("%")){
                                methodObjToString = URLDecoder.decode(methodObjToString);
                            }
                            toInvokeMethodSig = convertMethodToStringToSmaliStyle(methodObjToString);
                        }

                        // we want to replace the reflection invocation with a normal method invocation
                        // but this is non-trivial because we need to open the array that contains
                        // arguments and put the values in the registers we want to use in the new normal invoke method.
                        // We need to use some registers for our own logic, but we cannot reuse existing registers
                        // in the trace since don't want to play around with them since it can break data-dependency flow.
                        // So we need to use new registers but when we use new registers which means we have to
                        // update the number local registers for all the wrapping methods from the point of reflection instruction
                        //
                        // to start our normalization, we start by finding out how many new registers we need
                        // for our new method-invocation instruction. This is done by analyzing the arguments of
                        // the true invoked method. Note that we only need to count the arguments and not the
                        // reference object since we only need to unpack the arguments array and reference object
                        // is already in the correct register
                        int numberOfNeededRegistersForTrueArgs = 0;

                        int s = toInvokeMethodSig.indexOf('(') + 1;
                        int f = toInvokeMethodSig.lastIndexOf(')');
                        String argString = toInvokeMethodSig.substring(s, f);
                        String[] actualMethodArgTypes = Utils.parseMethodArgumentsString(argString);
                        for (String t : actualMethodArgTypes) {
                            if ("D".equals(t) || "J".equals(t)) {
                                numberOfNeededRegistersForTrueArgs += 2;
                            } else {
                                numberOfNeededRegistersForTrueArgs += 1;
                            }
                        }


                        // in smali the way that registers in a method work is:
                        // total registers = local registers + (reference object register)? + arguments registers
                        // since the new registers we want to add should not intervene
                        // with the original registers that method is using we
                        // need to use registers after the argument registers. Example:
                        // total registers = local registers + arguments registers + fake registers
                        // So we try to get the total number of registers in the wrapping method
                        int numberOfTotalRegisters;
                        try {
                            numberOfTotalRegisters = exeTrace.methodInfo.getInt("total registers");
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(e);
                        }


                        // Creating the new non-reflective invoke-* instruction
                        // 1- adding the correct instruction mnemonic
                        StringBuilder newInstructionRawLine = new StringBuilder();
                        if (toInvokeMethodIsStaticMethod) {
                            newInstructionRawLine.append("invoke-static ");
                        }
                        else {
                            // Note: invoke-super is impossible to do using reflection
                            if (toInvokeMethodIsPrivateMethod) {
                                newInstructionRawLine.append("invoke-direct ");
                            }
                            else {
                                newInstructionRawLine.append("invoke-virtual ");
                            }
                        }

                        // 2- adding the list of register numbers we need to use in the new invoke instruction
                        // adding the reference register number if needed
                        if(!toInvokeMethodIsStaticMethod){
                            newInstructionRawLine.append(underAnalysisInstruction.getInstructionSplits()[2]).append(" ");
                        }
                        // adding the arguments registers
                        for (int j = 0; j < numberOfNeededRegistersForTrueArgs; j++) {
                            newInstructionRawLine.append("v");
                            int newRegNum = numberOfTotalRegisters + j;
                            newInstructionRawLine.append(newRegNum);
                            newInstructionRawLine.append(" ");
                        }

                        //3- adding the invoked method signature
                        newInstructionRawLine.append(toInvokeMethodSig);
                        newInstructionRawLine.append(" ");
                        //4- adding the instruction position counter
                        newInstructionRawLine.append(originalInstructionSplits[originalInstructionSplits.length - 1]);

//                        if(newInstructionRawLine.toString().endsWith(" La2dp/Vol/btDevice;->getMac()Ljava/lang/String; &4")){
//                            System.out.println("111");
//                        }

                        //5- adding the register contents info meta data
                        //TODO == what am I doing here???
                        JSONObject newArgInfo = new JSONObject();
                        if(!toInvokeMethodIsStaticMethod) {
                            String k = originalInstructionSplits[2];
                            String v = underAnalysisInstruction.getRegisterContentsInfo().getString(k);
                            newArgInfo.put(k, v);
                        }
                        JSONObject actualArgsInfo = null;
                        try {
                            JSONObject ahh = underAnalysisInstruction.getRegisterContentsInfo();
                            if(!ahh.get("actualArgsInfo").equals("null")){
                                actualArgsInfo = ahh.getJSONObject("actualArgsInfo");
                            }
                        }
                        catch (Exception e) {
                            continue;
//                            throw new IllegalStateException(e);
                        }
                        int argTypeCounter = 0;
                        //TODO make the newInfoArg generation better and more robust
                        try {
                            for (int j = 0; j < numberOfNeededRegistersForTrueArgs; j++) {
                                int newRegNum = numberOfTotalRegisters + j;
                                String argType;
                                argType = actualMethodArgTypes[argTypeCounter];
                                String argInfo;
                                try {
                                    argInfo = actualArgsInfo.getString(argTypeCounter + "");
                                    newArgInfo.put("v" + newRegNum, argInfo);
                                } catch (Exception e) {
                                    throw new IllegalStateException(e);
                                }


                                if (argType.equals("D") || argType.equals("J")) j++;
                                argTypeCounter++;

                            }
                        }
                        catch (Exception e) {

                        }

                        newInstructionRawLine.append("|");
                        newInstructionRawLine.append(newArgInfo.toString());

                        //6- adding the InvokedMethodInfo json
                        if(underAnalysisInstruction.getToInvokeMethodInfo() != null){
                            newInstructionRawLine.append("|");
                            newInstructionRawLine.append(underAnalysisInstruction.getToInvokeMethodInfo().toString());
                        }


                        //7- creating the new TraceInstruction from the string representation
                        TraceInstruction newTraceInstruction = new TraceInstruction(newInstructionRawLine.toString(), underAnalysisInstruction.getMethodCallStack());
                        newTraceInstruction.oldTraceInstruction = underAnalysisInstruction;

                        //Before we replace the reflection instruction with the new instruction
                        // first we find the instructions corresponding to creation of
                        // the argument values, and we put them in the trueArgsSrcInstMap
                        // we dot this by first getting the objectID of the array
                        String argsArrayObjectID;
                        try {
                            String argsArrayReg = originalInstructionSplits[3];
                            argsArrayObjectID = underAnalysisInstruction.getRegisterContentsInfo().getString(argsArrayReg);
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                        //TODO what if the argsArray is Ambiguous

                        // then for each argument we trace it back to find the instruction we are looking for
                        ArrayList<TraceInstruction> sourceInstructions = new ArrayList<>();
                        for (int j = 0; j < actualMethodArgTypes.length; j++) {

                            // getting the argument object ID
                            String trueArgumentObjectID = actualArgsInfo.getString(Integer.toString(j));

                            // we need to trace back the source of any argument
                            TraceInstruction argumentCreatingInstruction = resolveTrueArgumentCreatingInstruction(exeTrace,
                                    (ArrayList<ExecutionTrace>) parentExecutions.clone(),
                                    exeTrace.traceArray.indexOf(underAnalysisInstruction) - 1,
                                    argsArrayObjectID,
                                    trueArgumentObjectID,
                                    j,
                                    trueArgsSrcInstMap);
                            sourceInstructions.add(argumentCreatingInstruction);
                        }
                        trueArgsSrcInstMap.put(newTraceInstruction, sourceInstructions);

                        //we increase the number of registers used in this method
                        try {
                            exeTrace.methodInfo.put("total registers", numberOfTotalRegisters + numberOfNeededRegistersForTrueArgs);
                        }
                        catch (JSONException e) {
                            throw new IllegalStateException(e);
                        }

                        // replacing the old reflection instruction with the new invoke instruction
                        int invocationInstructionIndex = exeTrace.traceArray.indexOf(underAnalysisInstruction);
                        exeTrace.traceArray.set(invocationInstructionIndex, newTraceInstruction);


                        // if the true invoked method is returning a primitive value, the reflection API will return an object instead
                        // which means there is an extra type conversion added for the return value
                        // in the code. So we need to remove it if possible
                        //
                        // checking if the true method return value is primitive
                        String retType = toInvokeMethodSig.substring(toInvokeMethodSig.lastIndexOf(')') + 1);
                        if (retType.length() == 1) {
                            // checking if the extra type conversion can be removed
                            boolean canRemoveRetConversionInstructions = false;
                            TraceInstruction t1 = null;
                            TraceInstruction t2 = null;
                            ExecutionTrace parentExe = null;
                            try {
                                // we want to get the next instruction after the method invocation
                                // So we calculate where is the next instruction located in our ExecutionTrace
                                int returnInstIndex = invocationInstructionIndex + 1;
                                if (exeTrace.traceArray.get(returnInstIndex) instanceof ExecutionTrace) {
                                    returnInstIndex++;
                                }

                                // checking if the method return value is captured
                                if (exeTrace.traceArray.get(returnInstIndex) instanceof TraceInstruction) {
                                    String retInst = ((TraceInstruction) exeTrace.traceArray.get(returnInstIndex)).getInstruction();
                                    if (retInst.startsWith("move-result")) {
                                        //TODO why I am looking at the parent ExecutionTrace instead of
                                        // current execution trace
                                        parentExe = parentExecutions.get(parentExecutions.size() - 1);
                                        int exeTraceIndexInParent = parentExe.traceArray.indexOf(exeTrace);

                                        // checking if the next instruction are responsible for type conversion
                                        TraceInstruction t0 = (TraceInstruction) parentExe.traceArray.get(exeTraceIndexInParent + 2);
                                        t1 = (TraceInstruction) parentExe.traceArray.get(exeTraceIndexInParent + 3);
                                        t2 = (TraceInstruction) parentExe.traceArray.get(exeTraceIndexInParent + 4);
                                        if (t0.getInstruction().startsWith("check-cast")) {
                                            String[] checkCastSplits = t0.getInstructionSplits();
                                            String regNum = checkCastSplits[1];
                                            String checkedType = checkCastSplits[2];

                                            String[] t1Splits = t1.getInstructionSplits();

                                            if (t1.getInstruction().startsWith("invoke-virtual " + regNum + " " + checkedType + "->")
                                                    && t2.getInstruction().startsWith("move-result")
                                                    && t1Splits[t1Splits.length - 2].endsWith(retType)) {
                                                canRemoveRetConversionInstructions = true;
                                            }
                                        }

                                    }

                                }
                            }
                            catch (Exception e) {
                            }

                            if (canRemoveRetConversionInstructions) {
                                parentExe.traceArray.remove(t1);
                                parentExe.traceArray.remove(t2);
                            }
                        }


                    }
                }
            }
            // for nested executions call removeReflectionCalls recursively on them
            else if (o instanceof ExecutionTrace) {
                ExecutionTrace innerTrace = (ExecutionTrace) o;
                ArrayList<ExecutionTrace> parentExecutionsClone = (ArrayList<ExecutionTrace>) parentExecutions.clone();
                parentExecutionsClone.add(exeTrace);
                removeReflectionCalls(innerTrace, parentExecutionsClone, trueArgsSrcInstMap);
            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    // In this method we find the instructions that create the value that
    // is passed to our method called by reflection via putting this value
    // in the array used for passing arguments by reflection API
    private static TraceInstruction resolveTrueArgumentCreatingInstruction(ExecutionTrace exeTrace,
                                                                           ArrayList<ExecutionTrace> parentExecutions,
                                                                           int tracingBackFrom,
                                                                           String argsArrayObjectID,
                                                                           String trueArgumentObjectID,
                                                                           int arrayIndexCounter,
                                                                           HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap) {
        // finding the first instruction going back from tracingBackFrom that is putting a value
        // in the arguments array in the correct argument index with the correct value
        for (int i = tracingBackFrom; i >= 0; i--) {
            Object o = exeTrace.traceArray.get(i);
            if (o instanceof TraceInstruction) {
                TraceInstruction ti = (TraceInstruction) o;
                // finding all aput-object instructions
                if (ti.getInstruction().startsWith("aput-object")) {
                    String writingObjectReg = ti.getInstructionSplits()[1];
                    String arrayRefReg = ti.getInstructionSplits()[2];
                    String indexReg = ti.getInstructionSplits()[3];
                    JSONObject registerContents = ti.getRegisterContentsInfo();
                    String arrayID = registerContents.getString(arrayRefReg);
                    String writingObjectID = registerContents.getString(writingObjectReg);
                    String indexValue = registerContents.getString(indexReg);

                    // if this aput instruction is writing the argument we are looking for
                    if (arrayID.equals(argsArrayObjectID) && writingObjectID.equals(trueArgumentObjectID)) {
                        //Obj:null means number 0
                        if(!indexValue.equals("Obj:null") && !indexValue.startsWith("Num:")){
                            throw new IllegalStateException();
                        }
                        int index = 0;
                        if(indexValue.startsWith("Num:")){
                            String s = indexValue.substring(4);
                            index = Integer.parseInt(s);
                        }
                        if(index == arrayIndexCounter) {
                            TraceInstruction res = resolveArrayWritingInstruction(exeTrace,
                                    (ArrayList<ExecutionTrace>) parentExecutions.clone(),
                                    i,
                                    writingObjectReg,
                                    trueArgsSrcInstMap);

                            return res;
                        }
                    }
                }
            }
        }

        if (parentExecutions.size() == 0) return null;

        ExecutionTrace wrapperExecution = parentExecutions.remove(parentExecutions.size() - 1);

        return resolveTrueArgumentCreatingInstruction(wrapperExecution,
                parentExecutions,
                wrapperExecution.traceArray.indexOf(exeTrace) - 1,
                argsArrayObjectID,
                trueArgumentObjectID,
                arrayIndexCounter,
                trueArgsSrcInstMap);
    }

    // This method finds any instruction before tracingBackFrom that is writing a value
    // to register specified by regNumber argument
    private static TraceInstruction resolveArrayWritingInstruction(ExecutionTrace exeTrace,
                                                                   ArrayList<ExecutionTrace> parentExecutions,
                                                                   int tracingBackFrom,
                                                                   String regNumber,
                                                                   HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap) {
        //TODO this should not happen if everything is ok
        // but it happens because of trying to resolve a register
        // that is newly used by me for the
        try {
            Integer.parseInt(regNumber.substring(1));
        }
        catch (NumberFormatException e){
            return null;
        }

        // if any instruction in the current execution trace is writing to the register we are interested in
        for (int i = tracingBackFrom; i >= 0; i--) {
            Object o = exeTrace.traceArray.get(i);
            if (o instanceof TraceInstruction) {
                TraceInstruction ti = (TraceInstruction) o;
                String[] registerDefs = ti.getRegisterDefs();
                if (registerDefs.length > 0) {
                    String writingToRegister = registerDefs[0];
                    if (regNumber.equals(writingToRegister)) {
                        return ti;
                    }
                }
            }
        }

        // if no instruction is writing to that register it has to come from passed arguments
        // if our execution trace is a nested trace maybe we can find the writing instruction
        // in the parent executions. If our execution trace is not nested in any parent trace
        // there is no instruction writing to this register, so we return null
        if (parentExecutions.size() == 0) return null;

        // our trace is nested so lets look in parent executions
        ExecutionTrace parentExeTrace = parentExecutions.remove(parentExecutions.size() - 1);
        int i = parentExeTrace.traceArray.indexOf(exeTrace) - 1;

        TraceInstruction invokerInstruction = (TraceInstruction) parentExeTrace.traceArray.get(i);
        String[] splits = invokerInstruction.getInstructionSplits();

        int numberOfLocalRegistersInNestedMethod;
        String toInvokeMethodModifiers = exeTrace.methodInfo.getString("modifiers").toLowerCase();
        boolean isStatic = splits[0].startsWith("invoke-static");

        numberOfLocalRegistersInNestedMethod = exeTrace.methodInfo.getInt("local registers");
        // finding out in the invoker instruction to what passed register we have too look at
        int argIndexCount = Integer.parseInt(regNumber.substring(1)) - numberOfLocalRegistersInNestedMethod;
        if(argIndexCount == -1){
            throw new IllegalStateException();
        }


        String newRegNumber = splits[1 + argIndexCount];
        try {
            Integer.parseInt(newRegNumber.substring(1));
        }
        catch (Exception e){
            throw new IllegalStateException(e);
        }

        //TODO why I have put this here??
        // if we have already calculated this before
        // we just return the result
        if(trueArgsSrcInstMap.containsKey(invokerInstruction) && !invokerInstruction.getInstruction().contains("()")){
            if(isStatic) {
                ArrayList<TraceInstruction> a = trueArgsSrcInstMap.get(invokerInstruction);
                return a.get(argIndexCount);
            }
            else if(argIndexCount > 0){
                ArrayList<TraceInstruction> a = trueArgsSrcInstMap.get(invokerInstruction);
                return a.get(argIndexCount-1);
            }
        }

        return resolveArrayWritingInstruction(parentExeTrace, parentExecutions, i, newRegNumber,trueArgsSrcInstMap);
    }

    private static String getSmaliStyleArgTypes(String argsString){
        if(argsString == null ||  argsString.equals("")){
            return "";
        }
        String[] argTypes = argsString.split(",");
        String res = "";
        for(String rawArgType: argTypes){
            if(rawArgType.contains("[")){
                rawArgType = rawArgType.replace("]", "");
                int a = Utils.countChar(rawArgType,'[');
                rawArgType = rawArgType.replace("[", "");
                rawArgType = Utils.makeSmaliStyleClassPath(rawArgType.trim());
                for(int i = 0; i<a; i++){
                    rawArgType = "[" + rawArgType;
                }
                res = res + rawArgType;
            }
            else {
                res = res + Utils.makeSmaliStyleClassPath(rawArgType.trim());
            }
        }
        return res;
    }

    private static String convertMethodToStringToSmaliStyle(String origin){
        String clean_origin = origin.substring(0, origin.lastIndexOf(')')+1);
        String[] splits = clean_origin.split(" ");
        String methodClassSignature = splits[splits.length - 1];

        int methodNameStart = methodClassSignature.substring(0, methodClassSignature.indexOf('(')).lastIndexOf('.');
        String classPath = methodClassSignature.substring(0, methodNameStart);
        String smaliStyleClassPath = Utils.makeSmaliStyleClassPath(classPath);

        String rawReturnType = splits[splits.length - 2];
        String methodReturnType = getSmaliStyleArgTypes(rawReturnType);
        String methodNameAndArgs = methodClassSignature.substring(methodNameStart + 1);
        String methodName = methodNameAndArgs.substring(0, methodNameAndArgs.indexOf('('));
        String methodArgs = methodNameAndArgs.substring(methodNameAndArgs.indexOf('(') + 1);
        methodArgs = methodArgs.substring(0, methodArgs.length() - 1);
        methodArgs = getSmaliStyleArgTypes(methodArgs);
        String smaliStyleMethodSignature = methodName + "(" + methodArgs + ")" + methodReturnType;
        return smaliStyleClassPath + "->" + smaliStyleMethodSignature;
    }



    public static void addDataDependencyConnectingInstructions(ArrayList<TraceInstruction> flattenedTrace,
                                                               HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap,
                                                               HashMap<TraceInstruction, TraceInstruction> updatedInstructionsDuringFlattening,
                                                               HashSet<TraceInstruction> removedInstructionsDuringFlattening) {

        // for each Reflection Method.invoke() that has been replaced with normal method invocation
        // lets add the necessary move instructions to fix the data flow
        for (TraceInstruction newMethodCallInstruction : trueArgsSrcInstMap.keySet()) {
            //first we check if the invoke-* instruction we added before has been removed
            // during the execution trace flattening step, and then we get it splits so we can get the used registers
            String[] splits;
            if (flattenedTrace.contains(newMethodCallInstruction) || removedInstructionsDuringFlattening.contains(newMethodCallInstruction)) {
                splits = newMethodCallInstruction.getInstructionSplits();
            }
            else if (updatedInstructionsDuringFlattening.containsKey(newMethodCallInstruction)) {
                splits = updatedInstructionsDuringFlattening.get(newMethodCallInstruction).getInstructionSplits();
            }
            else {
                throw new IllegalStateException();
            }

            if(!splits[0].startsWith("invoke")){
                throw new IllegalStateException("the replacement instruction of reflective method call is an instruction call!!!");
            }


            // getting the method arguments string
            String classMethodSig = splits[splits.length - 2];
            int s = classMethodSig.indexOf('(') + 1;
            int f = classMethodSig.lastIndexOf(')');
            String argString = classMethodSig.substring(s, f);
            String[] actualMethodArgTypes = Utils.parseMethodArgumentsString(argString);
            ArrayList<TraceInstruction> argSrcInstList = trueArgsSrcInstMap.get(newMethodCallInstruction);
            //check if argSrcInstList length matches with the registers needed for the argTypes
            if(argSrcInstList.size() != actualMethodArgTypes.length){
                throw new IllegalStateException();
            }

            int regCounter = 1;
            if(!splits[0].startsWith("invoke-static")){
                regCounter ++;
            }

            for (int i = 0; i < actualMethodArgTypes.length; i++) {
                String argType = actualMethodArgTypes[i];
                // the register used for our argument would be the destRegister of our move instructions
                String destRegister = splits[regCounter];
                regCounter++;
                if (argType.equals("J") || argType.equals("D")) regCounter ++;

                // we need to know the instruction that was responsible
                // for creating the argument value. Since we have flattened our trace before
                // we find its location in the flattened trace
                int argSrcInstPosInFlattenTrace;
                {
                    TraceInstruction argSrcInst = argSrcInstList.get(i);
                    // null argSrcInst means the value was supplied as an input
                    // to the execution trace
                    if (argSrcInst == null) continue;

                    // if our argument creating instruction still exists in our flatten trace
                    if (flattenedTrace.contains(argSrcInst)) {
                        argSrcInstPosInFlattenTrace = flattenedTrace.indexOf(argSrcInst);
                    }
                    // if our argument creating instruction has been removed in the flattening process
                    else if (updatedInstructionsDuringFlattening.containsKey(argSrcInst)) {
                        TraceInstruction newInst = updatedInstructionsDuringFlattening.get(argSrcInst);

                        // This is only happens with move-result-* instruction after calling a method in a nested trace
                        // first time the reg number is changed during flattening and second time is
                        // when move-result-* is changed to move-*
                        if (updatedInstructionsDuringFlattening.containsKey(newInst)){
                            newInst = updatedInstructionsDuringFlattening.get(newInst);
                        }
                        argSrcInstPosInFlattenTrace = flattenedTrace.indexOf(newInst);
                    }
                    else {
                        throw new IllegalStateException("argSrcInst not found!");
                    }

                    if (argSrcInstPosInFlattenTrace == -1) {
                        throw new IllegalStateException("argSrcInst not found!");
                    }
                }

                // after finding the instruction creating the argument value
                // we have to put a move after it
                TraceInstruction valueWritingInst = flattenedTrace.get(argSrcInstPosInFlattenTrace);
                String methodCallContext = valueWritingInst.getMethodCallStack();
                StringBuilder newMoveInstructionRawLine = new StringBuilder();
                String srcRegister;
                if (argType.length() == 1) {
                    //TODO make this better and smarter since these assumptions will not always work
                    TraceInstruction primitiveConvertorInst = flattenedTrace.get(argSrcInstPosInFlattenTrace - 1);
                    String[] primitiveConvertorInstSplits = primitiveConvertorInst.getInstructionSplits();
                    srcRegister = primitiveConvertorInstSplits[1];
                    if (argType.equals("D") || argType.equals("J")) {
                        newMoveInstructionRawLine.append("move-wide ");
                    } else {
                        newMoveInstructionRawLine.append("move ");
                    }
                }
                else {
                    srcRegister = valueWritingInst.getRegisterDefs()[0];
                    newMoveInstructionRawLine.append("move-object ");
                }

                newMoveInstructionRawLine.append(destRegister);
                newMoveInstructionRawLine.append(" ");
                newMoveInstructionRawLine.append(srcRegister);
                newMoveInstructionRawLine.append(" &");
                newMoveInstructionRawLine.append(-1 - i);
                newMoveInstructionRawLine.append("|{}");

                TraceInstruction traceInstruction = new TraceInstruction(newMoveInstructionRawLine.toString(), methodCallContext);
                flattenedTrace.add(argSrcInstPosInFlattenTrace + 1, traceInstruction);
            }
        }
    }

}
