import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Slicer {

    static ArrayList<TraceInstruction> extractCodeSlice(TraceInstruction toTraceInstruction,
                                                        ArrayList<TraceInstruction> traceInstructions){
        ArrayList<TraceInstruction> slice = new ArrayList<>();
        HashSet registerUses = new HashSet();
        // static and instance fields can also be used to move around values between registers
        HashSet<String> fieldUses = new HashSet<>();
        slice.add(toTraceInstruction);
        registerUses.addAll(Arrays.asList(toTraceInstruction.getRegisterUses()));
        int tracePos = traceInstructions.indexOf(toTraceInstruction);
        if(tracePos == -1){
            throw new IllegalStateException();
        }
        int offset = 1;

        // the exit conditions are:
        // 1- if all the register uses and field uses have been defined and the size of them is 0
        // 2- if we reach the beginning of the trace which means the unresolved uses are method arguments
        while((registerUses.size() > 0 || fieldUses.size() > 0)
                && tracePos - offset >= 0) {
            TraceInstruction ti = traceInstructions.get(tracePos - offset);
            offset ++;

            // since instructions that define(write) any value in a register only
            // write to a single register the length of defines is always one or zero
            boolean writesToUsedValue = ti.getRegisterDefs().length > 0 && registerUses.contains(ti.getRegisterDefs()[0]);
            boolean isUsedAsMethodParameterInStateChangingMethod = false;
            boolean isWritingToCapturedField = false;

            if(ti.getInstruction().startsWith("invoke")){
                String[] uses = ti.getRegisterUses();
                for (String regNum: uses) {
                    if(registerUses.contains(regNum)){
                        String[] splits = ti.getInstructionSplits();
                        String classMethodSignature = splits[splits.length-2];
                        String className = classMethodSignature.split("->")[0];
                        String methodName = classMethodSignature.split("->")[1];
                        if(!ImportantInstructionFinder.isTypeImmutable(className)
                                && !ImportantInstructionFinder.isMethodPure(classMethodSignature)) {
                            isUsedAsMethodParameterInStateChangingMethod = true;
                            registerUses.addAll(Arrays.asList(uses));
                            break;
                        }
                    }
                }
            }

            if(ti.getInstruction().startsWith("sput")){
                String[] splits = ti.getInstructionSplits();
                String methodOrFieldSignature = splits[splits.length - 2];
                if(fieldUses.contains(methodOrFieldSignature)){
                    isWritingToCapturedField = true;
                    fieldUses.remove(methodOrFieldSignature);
                }
            }

            if(ti.getInstruction().startsWith("iput")){
                String[] splits = ti.getInstructionSplits();
                String fieldSignature = splits[splits.length - 2];
                String refObjectRegId1 = splits[splits.length - 3];
                JSONObject jo1 = ti.getRegisterContentsInfo();
                try {
                    String objectIdOfObjectInReg1 = jo1.getString(refObjectRegId1);
                    if ((objectIdOfObjectInReg1.startsWith("Obj:") ||
                            objectIdOfObjectInReg1.startsWith("Amb:"))
                            && !objectIdOfObjectInReg1.toLowerCase().startsWith("obj:null")){
                        objectIdOfObjectInReg1 = objectIdOfObjectInReg1.substring(0, objectIdOfObjectInReg1.indexOf(','));
                    }
                    if(fieldUses.contains(fieldSignature + "_" + objectIdOfObjectInReg1)){
                        isWritingToCapturedField = true;
                        fieldUses.remove(fieldSignature);
                    }
                } catch (JSONException e) {

                }
            }

            if(isWritingToCapturedField){
                registerUses.addAll(Arrays.asList(ti.getRegisterUses()));
            }
            else if(writesToUsedValue || isUsedAsMethodParameterInStateChangingMethod) {
                slice.add(ti);
                if(writesToUsedValue){
                    registerUses.remove(ti.getRegisterDefs()[0]);
                }
                registerUses.addAll(Arrays.asList(ti.getRegisterUses()));
                // after flattening there are move-result instructions left due to the
                // method calls to java/androidAPI calls and also the filled-new-array instruction
                // the purpose of this check is that the analyzed TraceInstruction is move-result
                // we force the previous instruction that should be invoke-* to java/android API
                // or a filled-new-array to be analyzed
                if(ti.getInstruction().startsWith("move-result")){
                    // take the previous instruction
                    TraceInstruction ti2 = traceInstructions.get(tracePos - offset);
                    // check that it is invoke- or filled-new-array
                    if(ti2.getInstruction().startsWith("invoke-") || ti2.getInstruction().startsWith("filled-new-array")){
                        // add the uses of this instructions to registerUses set
                        registerUses.addAll(Arrays.asList(ti2.getRegisterUses()));
                        //TODO check that this move-result is not a useless move-result
                        // meaning writing a value to a register with the same value
                        // this happens in some compilers
                        if(ImportantInstructionFinder.checkIfMoveResultIsUseless(ti, traceInstructions)) {
                            slice.remove(slice.size()-1);
                        }
                        slice.add(ti2);
                        offset++;
                    }
                    else {
                        throw new IllegalStateException(ti2.getInstruction() + " error in backtracing of "
                                + toTraceInstruction.getInstruction() );
                    }
                }
                else if(ti.getInstruction().startsWith("move-exception")){
                    // take the previous instruction
                    TraceInstruction ti2 = traceInstructions.get(tracePos - offset);

//                    if(ti2.getInstruction().startsWith("invoke-") || ti2.getInstruction().startsWith("throw")){
                    // add the uses of this instructions to registerUses set
                    registerUses.addAll(Arrays.asList(ti2.getRegisterUses()));
                    slice.add(ti2);
                    offset++;
//                    }
//                    else {
//                        throw new IllegalStateException(ti2.getInstruction() + " error in backtracing of "
//                                + toTraceInstruction.getInstruction() );
//                    }
                }
                else if(ti.getInstruction().startsWith("sget")){
                    if(ImportantInstructionFinder.checkFieldUseIsUseless(ti, traceInstructions)){
                        String[] splits = ti.getInstructionSplits();
                        String fieldSignature = splits[splits.length - 2];
                        fieldUses.add(fieldSignature);
                        slice.remove(slice.size()-1);
                    }
                }
                else if(ti.getInstruction().startsWith("iget")){
                    if(ImportantInstructionFinder.checkFieldUseIsUseless(ti, traceInstructions)){
                        String[] splits = ti.getInstructionSplits();
                        String fieldSignature = splits[splits.length - 2];
                        String refObjectRegId1 = splits[splits.length - 3];
                        JSONObject jo1 = ti.getRegisterContentsInfo();
                        try {
                            String objectIdOfObjectInReg1 = jo1.getString(refObjectRegId1);
                            if (objectIdOfObjectInReg1.startsWith("Obj:") ||
                                    objectIdOfObjectInReg1.startsWith("Amb:")){
                                objectIdOfObjectInReg1 = objectIdOfObjectInReg1.substring(0, objectIdOfObjectInReg1.indexOf(','));
                            }
                            fieldUses.add(fieldSignature + "_" + objectIdOfObjectInReg1);
                            slice.remove(slice.size()-1);
                        } catch (JSONException e) {

                        }
                    }
                }
            }
        }
        return slice;
    }


}
