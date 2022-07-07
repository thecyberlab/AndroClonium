import org.json.JSONObject;

import java.util.ArrayList;


public class ConstantTransformationsSimplifier {

    // this method basically checks for any code transformations in the form of method invocations
    // that take constant primitive or String values and return a primitive or String
    public static void simplifyConstantTransformations(ExecutionTrace exeTrace,
                                                       ArrayList<ExecutionTrace> parentExecutions,
                                                       int depth) {

        // this method calls itself recursively,
        // so here we want to check depth and parentExecutions stack do match
        if(depth != parentExecutions.size()){
            throw new IllegalStateException();
        }

        // for each item in execution trace, if it is an instruction check if we can simplify it,
        // if it is nested execution trace call simplifyConstantTransformations recursively on it
        for (int i = 0; i < exeTrace.traceArray.size(); i++) {
            Object o = exeTrace.traceArray.get(i);
            if(o instanceof TraceInstruction){
                TraceInstruction ti = (TraceInstruction) o;
                // find all method invocations that are doing transformations on constant values
                if(ti.getInstruction().startsWith("invoke-")){
                    String[] splits = ti.getInstructionSplits();
                    String classMethodSig = splits[splits.length - 2];
                    String retType = classMethodSig.substring(classMethodSig.lastIndexOf(')')+1);
                    // check if their output is String or a primitive type
                    if((retType.length() == 1 && !retType.equals("V")) || "Ljava/lang/String;".equals(retType)){
                        //TODO we only consider methods with bodies for now
                        // meaning any java or Android methods without body are not simplified
                        // WHY ??
                        if(i+1 < exeTrace.traceArray.size() && exeTrace.traceArray.get(i+1) instanceof ExecutionTrace) {
                            ExecutionTrace innerTrace = (ExecutionTrace) exeTrace.traceArray.get(i+1);
                            // getting the last item in the nested trace and checking
                            // if it is a return instruction
                            Object lastItemInNestedTrace = innerTrace.traceArray.get(innerTrace.traceArray.size() - 1);
                            if(lastItemInNestedTrace instanceof TraceInstruction){
                                TraceInstruction lastInstructionInNestedTrace = (TraceInstruction) lastItemInNestedTrace;
                                if(lastInstructionInNestedTrace.getInstruction().startsWith("return")){
                                    // if last instruction is returning a value we want to
                                    // convert that value to a const-* instruction
                                    JSONObject jo = lastInstructionInNestedTrace.getRegisterContentsInfo();
                                    if(jo == null){
                                        throw new IllegalStateException("RegisterContentsInfo is null!! : " + lastInstructionInNestedTrace.getLogLine());
                                    }
                                    String retReg = lastInstructionInNestedTrace.getInstructionSplits()[1];
                                    String retObjInfo = jo.getString(retReg);
                                    // ignoring return values that are not valid objects
                                    if(retObjInfo== null
//                                            || retObjInfo.toLowerCase().startsWith("null")
                                            || retObjInfo.startsWith("Obj:null")
                                            || retObjInfo.startsWith("Amb:")){
//                                            logExeTrace(exeTrace);
                                        continue;
                                    }
                                    // check if all the inputs of the transformations come from const-values
                                    if(allInvocationInputsComeFromConst(ti, exeTrace, (ArrayList<ExecutionTrace>) parentExecutions.clone())) {
                                        // checking if the return value of method is used by a move-result instruction
                                        // and if so changing the move-result instruction with a const instruction
                                        TraceInstruction moveResInst = (TraceInstruction) exeTrace.traceArray.get(i+2);
                                        if(moveResInst.getInstruction().startsWith("move-result")){
                                            // since in analyzing execution traces we don't care about values
                                            // we can put a dummy value which we will use 0 or "" for strings
                                            //TODO is there any case that the dummy value will cause problems ??

                                            StringBuilder newInstrRaw = new StringBuilder();
                                            String moveResReg = moveResInst.getInstructionSplits()[1];

                                            if(retType.equals("D") || retType.equals("J")){
                                                newInstrRaw.append("const-wide ");
                                                newInstrRaw.append(moveResReg);
                                                newInstrRaw.append(" 0 &-100|");
                                            }
                                            else if(retType.length() ==1){
                                                newInstrRaw.append("const ");
                                                newInstrRaw.append(moveResReg);
                                                newInstrRaw.append(" 0 &-100|");

                                            }
                                            else {
                                                newInstrRaw.append("const-string ");
                                                newInstrRaw.append(moveResReg);
                                                newInstrRaw.append(" ");
                                                newInstrRaw.append(" \"\" &-100|");
                                            }
                                            TraceInstruction newInstruction = new TraceInstruction(newInstrRaw.toString(), moveResInst.getMethodCallStack());
                                            exeTrace.traceArray.set(i+2, newInstruction);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                ExecutionTrace innerTrace = (ExecutionTrace) o;
                ArrayList<ExecutionTrace> parentExecutionsClone = (ArrayList<ExecutionTrace>) parentExecutions.clone();
                parentExecutionsClone.add(exeTrace);
                simplifyConstantTransformations(innerTrace, parentExecutionsClone, depth+1);
            }
        }
    }



    private static boolean allInvocationInputsComeFromConst(TraceInstruction invocationInstruction,
                                                            ExecutionTrace exeTrace,
                                                            ArrayList<ExecutionTrace> parentExecutions){
        String[] splits = invocationInstruction.getInstructionSplits();
        String classMethodSig = splits[splits.length - 2];

        // getting the arguments types of invoked method
        int s = classMethodSig.indexOf('(') + 1;
        int f = classMethodSig.lastIndexOf(')');
        String argString = classMethodSig.substring(s, f);
        String[] methodArgTypes = Utils.parseMethodArgumentsString(argString);

        int regCounter = 0;
        String toInvokeMethodModifiers = invocationInstruction.getToInvokeMethodInfo().getString("modifiers").toLowerCase();
        // if method is not static then ignore the ref object
        if (!toInvokeMethodModifiers.contains("static")) {
            regCounter = 1;
        }

        // for each argument lets check if it comes from a const instruction
        for(int argCount=0; argCount < methodArgTypes.length ;argCount++){
            // the +1 is to ignore the initial mnemonic
            String regNum = splits[1+regCounter];
            if(!regNum.startsWith("v")){
                throw new IllegalStateException();
            }

            int backTraceFrom = exeTrace.traceArray.indexOf(invocationInstruction);
            if(backTraceFrom == -1) throw new IllegalStateException();

            TraceInstruction ti = resolveValueWritingInstruction(exeTrace,
                    (ArrayList<ExecutionTrace>) parentExecutions.clone(),backTraceFrom , regNum);

            if(ti == null) return false;
            if(!ti.getInstruction().startsWith("const")) return false;

            regCounter++;
            if(methodArgTypes[argCount].equals("D") || methodArgTypes[argCount].equals("J")){
                regCounter++;
            }
        }
        return true;
    }


    private static TraceInstruction resolveValueWritingInstruction(ExecutionTrace exeTrace,
                                                                   ArrayList<ExecutionTrace> parentExecutions,
                                                                   int tracingBackFrom,
                                                                   String regNumber) {

        // let's see if we can find the writing instruction in this method context
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

        // if the writing instruction is not found, then we should look at parent executionTrac
        // first check if there is a parent ExecutionTrace
        if (parentExecutions.size() == 0) return null;
        ExecutionTrace parentExeTrace = parentExecutions.remove(parentExecutions.size() - 1);

        // let's find the 'invoke-*' instruction in the parent execution trace
        // which started current execution trace
        int invokerInstructionPosition = parentExeTrace.traceArray.indexOf(exeTrace) - 1;
        if(invokerInstructionPosition==-2){
            throw new IllegalStateException();
        }

        TraceInstruction invokerInstruction = (TraceInstruction) parentExeTrace.traceArray.get(invokerInstructionPosition);
        String[] invokerInstSplits = invokerInstruction.getInstructionSplits();
        if(!invokerInstruction.getInstruction().startsWith("invoke-")){
            throw new IllegalStateException();
        }

        String invokerSig = invokerInstSplits[invokerInstSplits.length - 2];
        if(invokerSig.startsWith("Ljava/lang/reflect/Constructor;->newInstance") ||
            invokerSig.startsWith("Ljava/lang/Class;->newInstance") ||
            invokerSig.startsWith("Ljava/lang/reflect/Method;->invoke")){
            return null;
        }

        // now let's see what register was used as the argument that we are interested in
        // to do this first we check if our method is static or not
        // this is needed for one of our checks later
        boolean isInstanceMethod = false;
        if(exeTrace.methodInfo.has("modifiers")){
            if(!exeTrace.methodInfo.getString("modifiers").contains("static")){
                isInstanceMethod = true;
            }
        }
        else {
            String isStatic = exeTrace.methodInfo.getString("isStatic").toLowerCase();
            if(isStatic.equals("false")){
                isInstanceMethod = true;
            }
        }

        // here we want to calculate the index of passed argument
        // meaning is it the first arg, or second or Nth arg

        int parentArgRegisterIndex = Integer.parseInt(regNumber.substring(1)) - exeTrace.methodInfo.getInt("local registers");;

        // obviously reference objects cannot come from
        // const instructions so return null instead of looking for actual value writing instruction
        if(isInstanceMethod && parentArgRegisterIndex == 0){
            return null;
        }

        if(parentArgRegisterIndex == -1){
            throw new IllegalStateException();
        }


        String newRegNumber = invokerInstSplits[1 + parentArgRegisterIndex];
        if(!newRegNumber.startsWith("v")){
           throw new IllegalStateException();
        }
        return resolveValueWritingInstruction(parentExeTrace, parentExecutions, invokerInstructionPosition, newRegNumber);
    }

//    private static void logExeTrace(ArrayList exeTrace){
//        for (Object o :exeTrace) {
//            if(o instanceof TraceInstruction){
//                TraceInstruction t = (TraceInstruction) o;
//                Object ss = t.getRegisterContentsInfo();
//                if(ss!=null) {
//                    System.out.println(t.getInstruction() + "|" + ss.toString());
//                }
//                else {
//                    System.out.println(t.getInstruction());
//                }
//            }
//            else {
//                logExeTrace((ArrayList) o);
//            }
//        }
//    }


}
