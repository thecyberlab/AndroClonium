import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class InstructionTraceFlattener {


    static Object[] flattenExecutionTraceOneLevel(ExecutionTrace inputExecutionTrace,
                                                   long totalNumberOfRegisterUsedInMethod,
                                                   HashMap<TraceInstruction, TraceInstruction> updatedInstructionsMap,
                                                  HashSet<TraceInstruction> removedInstructions)  {
        ArrayList<Object> result = new ArrayList<>();

        // since a single execution trace contains multiple inner execution traces,
        // we need to flatten them multiple times. However, we want to use different
        // register numbers for each nested trace so need to keep track of new registers we have added.
        long regOffsetForNewRegisters = totalNumberOfRegisterUsedInMethod;

        // this shows that some flattening was done during this method execution
        // this is needed to stop this method calling itself when there is no more flattening possible
        boolean flattened = false;

        // read each item
        for (int i = 0; i< inputExecutionTrace.traceArray.size(); i++) {
            Object o = inputExecutionTrace.traceArray.get(i);
            // if line is simple TraceInstruction add it to result
            if(o instanceof TraceInstruction){
                result.add(o);
            }
            else if(o instanceof ExecutionTrace){
                // getting the previous instruction which should be an invoke-* instruction
                TraceInstruction invokeInstruction = (TraceInstruction) result.get(result.size() - 1);
                if(!invokeInstruction.getInstruction().startsWith("invoke-")){
                    throw new IllegalStateException(invokeInstruction.getLogLine() + " is not invoke instruction!!");
                }

                // if we have a constructor call via reflection we cannot flatten it
                // so we ignore it (a reflective constructor call should have been normalized before)
                if(ReflectionConstructorNormalizer.isReflectiveConstructorCall(invokeInstruction)){

                    //TODO log so we know reflective constructors for later
                    // after ReflectiveConstructor normalizer is done we should not see this
                    try {
                        String methodName = inputExecutionTrace.methodInfo.getString("signature");
                        String log = Main.underAnalysisApp + "|" + methodName +"|" + invokeInstruction.getContainingMethod() + ">>" +
                                invokeInstruction.getInstruction() + "|" +
                                invokeInstruction.getRegisterContentsInfo().toString() + "\n";

                        File f = new File("ExeTraceWithReflectiveConstructorCall.txt");
                        if(!f.exists()) f.createNewFile();
                        Files.write(f.toPath(), log.getBytes(), StandardOpenOption.APPEND);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    // continue without adding the nested trace for the constructor
                    // invoked by reflection
                    continue;
                }

                // now we can start flattening, we start by removing the invocation call
                result.remove(result.size() -1);
                removedInstructions.add(invokeInstruction);
                flattened = true;

                // analyzing the method invocation method to capture the used registers numbers
                // so we can add proper move instructions to connect the data flow between
                // the wrapper method and the inner method
                //
                // we have to be careful with invoke-static vs the other invoke-* types since invoke-static
                // does not have a reference register, and also be careful with long and double types
                // since they take two registers but only the first is included in the registerContent json

                int invokedMethodLocalRegistersSize = invokeInstruction.getToInvokeMethodInfo().getInt("local registers");
                JSONObject invokeInstRegisterContentsInfo = invokeInstruction.getRegisterContentsInfo();
                String[] methodInvokeSplits = invokeInstruction.getInstructionSplits();
                String invokedMethodSig =  methodInvokeSplits[methodInvokeSplits.length - 2];
                String invokedMethodArgsStr = invokedMethodSig.substring(invokedMethodSig.indexOf('(')+1, invokedMethodSig.indexOf(')'));
                String[] invokedMethodArgTypes = Utils.parseMethodArgumentsString(invokedMethodArgsStr);

                int argumentRegCounter = 0;
                // adding the move for reference register if invoked method is not static
                if(!methodInvokeSplits[0].startsWith("invoke-static")){
                    argumentRegCounter = 1;
                    long newArgRegisterNumber = invokedMethodLocalRegistersSize + regOffsetForNewRegisters;
                    String usedRegisterInMethodInvocation = methodInvokeSplits[1];
                    String moveInstructionStr = "move v" + newArgRegisterNumber + " " + usedRegisterInMethodInvocation + " &-1";
                    JSONObject moveInstContentInfo = new JSONObject();

                    moveInstContentInfo.put("v"+newArgRegisterNumber, "Obj:null");

                    String usedRegisterInMethodInvocationContent = invokeInstRegisterContentsInfo.getString(usedRegisterInMethodInvocation);
                    moveInstContentInfo.put(usedRegisterInMethodInvocation, usedRegisterInMethodInvocationContent);

                    String rawNewLine = moveInstructionStr+"|" + moveInstContentInfo;
                    result.add(new TraceInstruction(rawNewLine, invokeInstruction.getMethodCallStack()));
                }

                //adding the move instructions for invoked method arguments
                for (int argTypeCounter = 0; argTypeCounter < invokedMethodArgTypes.length; argTypeCounter++) {
                    int newInstructionPos = -1 - argumentRegCounter;

                    long newArgRegisterNumber = invokedMethodLocalRegistersSize + argumentRegCounter + regOffsetForNewRegisters;
                    String usedRegisterInMethodInvocation = methodInvokeSplits[1+argumentRegCounter];
                    if(!usedRegisterInMethodInvocation.startsWith("v")){
                        throw new IllegalStateException();
                    }

                    String moveInstructionStr;
                    if(invokedMethodArgTypes[argTypeCounter].equals("D") || invokedMethodArgTypes[argTypeCounter].equals("J")){
                        moveInstructionStr = "move-wide v" + newArgRegisterNumber + " " + usedRegisterInMethodInvocation + " &" + newInstructionPos;
                        argumentRegCounter++;
                    }
                    else {
                        moveInstructionStr = "move v" + newArgRegisterNumber + " " + usedRegisterInMethodInvocation + " &" + newInstructionPos;
                    }

                    JSONObject moveInstContentInfo = new JSONObject();
                    moveInstContentInfo.put("v"+newArgRegisterNumber, "Obj:null");

                    String usedRegisterInMethodInvocationContent = invokeInstRegisterContentsInfo.getString(usedRegisterInMethodInvocation);
                    moveInstContentInfo.put(usedRegisterInMethodInvocation, usedRegisterInMethodInvocationContent);

                    String rawNewLine = moveInstructionStr+"|" + moveInstContentInfo;
                    result.add(new TraceInstruction(rawNewLine, invokeInstruction.getMethodCallStack()));
                    argumentRegCounter++;
                }

                ExecutionTrace nestedExecutionTrace = (ExecutionTrace) o;
                for (Object o2: nestedExecutionTrace.traceArray) {
                    // if it is a simple TraceInstruction
                    if(o2 instanceof TraceInstruction){
                        // update its register numbers
                        TraceInstruction ti = (TraceInstruction) o2;
                        String instr = ti.getInstruction();
                        String[] splits = instr.split(" ");
                        String newInstr = "";
                        for (String s: splits) {
                            if(s.startsWith("v")){
                                int regNum = Integer.parseInt(s.substring(1));
                                long newRegNum = regNum + regOffsetForNewRegisters;
                                newInstr = newInstr.concat("v"+ newRegNum + " ");

                            }else {
                                newInstr = newInstr.concat(s + " ");
                            }
                        }
                        newInstr = newInstr.trim();

                        // if we have register content meta data we want to update them
                        JSONObject newRegContent = null;
                        if(ti.getRegisterContentsInfo() != null) {
                            newRegContent = new JSONObject();
                            for (Iterator<String> it = ti.getRegisterContentsInfo().keys(); it.hasNext(); ) {
                                String k = it.next();
                                // if metadata is not for a register and is for
                                // other types of metadata just add it
                                if(!k.startsWith("v")){
                                    newRegContent.put(k, ti.getRegisterContentsInfo().get(k));
                                    continue;
                                }

                                long newRegNum;

                                try {
                                    newRegNum = Integer.parseInt(k.substring(1)) +regOffsetForNewRegisters;
                                }
                                // if metadata is not for a register and is for
                                // other types of metadata just add it
                                catch (NumberFormatException e){
                                    newRegContent.put(k, ti.getRegisterContentsInfo().get(k));
                                    continue;
                                }

                                String newK = "v" + newRegNum;
                                newRegContent.put(newK, ti.getRegisterContentsInfo().get(k));
                            }
                        }

                        TraceInstruction newTraceInstruction;
                        if(newRegContent != null){
                            newTraceInstruction = new TraceInstruction(ti.getMethodCallStack(), newInstr, ti.getToInvokeMethodInfo(), newRegContent);
                        }
                        else {
                            newTraceInstruction = new TraceInstruction(ti.getMethodCallStack(), newInstr, ti.getToInvokeMethodInfo(), ti.getRegisterContentsInfo());
                        }

                        // add it to result
                        result.add(newTraceInstruction);
                        updatedInstructionsMap.put((TraceInstruction) o2, newTraceInstruction);
                    }
                    // if it is a nested trace
                    else if(o2 instanceof ExecutionTrace) {
                        // just add it
                        result.add(o2);
                    }
                    else {
                        throw new IllegalStateException();
                    }
                }

                // if the last instruction in the nested trace is a return instruction except return-void we remove it
                Object lastItemInResult = result.get(result.size() -1);
                if(lastItemInResult instanceof TraceInstruction &&
                        ((TraceInstruction) lastItemInResult).getInstruction().startsWith("return")){

                    TraceInstruction returnInstruction = (TraceInstruction) result.remove(result.size() - 1);
                    removedInstructions.add(returnInstruction);

                    // changing intermediate move-result-* if present to move-* since the method calls have been flattened
                    // we cannot do this for last return since there is no move-result instruction after it
                    if(!returnInstruction.getInstruction().startsWith("return-void")
                        && i+1 < inputExecutionTrace.traceArray.size()){
                        String returnReg = returnInstruction.getInstructionSplits()[1];
                        TraceInstruction moveResultInstruction = (TraceInstruction) inputExecutionTrace.traceArray.get(i+1);
                        if(moveResultInstruction.getInstruction().startsWith("move-result")){
                            i++;
                            String[] splits = moveResultInstruction.getInstructionSplits();
                            String moveResultRegNum = splits[1];
                            String newOpcode = splits[0].replace("-result", "");
                            String newInstruction = newOpcode + " " + moveResultRegNum + " " + returnReg + " ";
                            for(int j=2;j<splits.length;j++){
                                newInstruction = newInstruction + splits[j] + " ";
                            }

                            JSONObject newRegContent = moveResultInstruction.getRegisterContentsInfo();
                            String s = returnInstruction.getRegisterContentsInfo().getString(returnReg);
                            newRegContent.put(returnReg, s);

                            newInstruction = newInstruction.trim();
                            TraceInstruction newMoveInstruction = new TraceInstruction(moveResultInstruction.getMethodCallStack(), newInstruction,
                                    moveResultInstruction.getToInvokeMethodInfo(), newRegContent);
                            result.add(newMoveInstruction);
                            updatedInstructionsMap.put(moveResultInstruction, newMoveInstruction);
                        }
                    }
                }


                //update the newOffset by adding the captured total number of register of the invoke method of this method call
                // the total number of registers used in the called method in this invoke
                int calledMethodTotalNumberOfRegisters;
                try {
                    calledMethodTotalNumberOfRegisters = invokeInstruction.getToInvokeMethodInfo().getInt("total registers");
                }
                catch (RuntimeException e){
                    throw new IllegalStateException("Error on accessing 'total registers' number: "+invokeInstruction.getLogLine());
                }
                regOffsetForNewRegisters = regOffsetForNewRegisters + calledMethodTotalNumberOfRegisters;
            }
            else {
                throw new IllegalStateException();
            }
        }


        Object[] r = new Object[3];
        r[0] = regOffsetForNewRegisters;
        r[1] = new ExecutionTrace(result, inputExecutionTrace.methodInfo, inputExecutionTrace.isNestedTrace, false);
        r[2] = flattened;
        return r;
    }

}
