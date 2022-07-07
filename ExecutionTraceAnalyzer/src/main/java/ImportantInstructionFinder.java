import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ImportantInstructionFinder {

    private static final HashSet<String> s_safeClasses = Utils.getStaticSafeClasses();
    private static final HashSet<String> s_partialSafeClasses = Utils.getStaticPartialSafeClasses();
    private static final HashSet<String> i_safeClasses = Utils.getInstanceSafeClasses();
    private static final HashSet<String> i_partialSafeClasses = Utils.getInstancePartialSafeClasses();

    private static final HashSet<String> immutableClassesSet = Utils.getImmutableClasses();
    private static final HashSet<String> pureMethodsSet = Utils.getPureMethods();


    // A global state changing method invocation is a method that has global side effects
    // on system such as printing to stdout, writing to a file, etc.
    static boolean isGlobalStateChangingMethodInvocation(TraceInstruction traceInstruction){
        String instruction = traceInstruction.getInstruction();
        if(instruction.startsWith("invoke")){
            String[] splits = traceInstruction.getInstructionSplits();
            String classMethodSig = splits[splits.length - 2];
            String classType = classMethodSig.split("->")[0];
            if(instruction.startsWith("invoke-static")){
                if(s_safeClasses.contains(classType) ||
                        s_partialSafeClasses.contains(classMethodSig)){
                    return false;
                }
                return true;
            }
            //TODO this is in general a invalid assumption that java/android constructors
            // do not have global side effects, we should include them later
            else if(!instruction.startsWith("invoke-direct")){
                if(i_safeClasses.contains(classType) ||
                        i_partialSafeClasses.contains(classMethodSig)){
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    static ArrayList<TraceInstruction> getNonLocalObjectChangingInstructions(ArrayList<TraceInstruction> flattenedTrace) {
        ArrayList<TraceInstruction> toBackTracedInstructions = new ArrayList<>();

        for (TraceInstruction t : flattenedTrace) {
            String instruction = t.getInstruction();
            //1- all "sput"s
            if (instruction.startsWith("sput")) {
                toBackTracedInstructions.add(t);
            }
            //2- all "iput"s done on objects which are not locally created
            if (instruction.startsWith("iput")) {
                // resolve where object came from
                TraceInstruction origin = getObjectOrigin(t, flattenedTrace);
                // object origin is a parameter
                if (origin == null) {
                    toBackTracedInstructions.add(t);
                }
                // object origin is a static attribute
                // or any method calls that have not been inlined before
                // TODO (This is an over approximation, but we allow it for now,
                //  since we are assuming these methods return important objects)
                else if (origin.getInstruction().startsWith("sget")
                        || (origin.getInstruction().startsWith("move-result-object"))) {
                    toBackTracedInstructions.add(t);
                }
            }

            //3- any "aput" done on arrays which are not locally created
            if(instruction.startsWith("aput")){
                TraceInstruction origin =  getArrayOrigin(t, flattenedTrace);
                // object origin is a parameter
                if (origin == null) {
                    toBackTracedInstructions.add(t);
                }
                // object origin is a static attribute
                // or from android/java api method result (in this case we may
                // mark an unimportant or locally created object as important)
                else if (origin.getInstruction().startsWith("sget")
                        || origin.getInstruction().startsWith("move-result-object")) {
                    toBackTracedInstructions.add(t);
                }
            }

            //4- all instance methods called on objects which are not locally created objects
            // and that method is changing state of some object
            if (instruction.startsWith("invoke")
                    && !instruction.startsWith("invoke-static")) {
                String[] splits = instruction.split(" ");
                String classMethodSig = splits[splits.length - 2];
                String classType = classMethodSig.split("->")[0];
                String methodName = classMethodSig.split("->")[1];
                // checking the method is state changing
                // (here we may miss some important state changing methods because our lists are not complete)
                if (!immutableClassesSet.contains(classType)
                        && !isMethodPure(classMethodSig)) {

                    // resolve where object came from
                    TraceInstruction origin = getObjectOrigin(t, flattenedTrace);
                    // object origin is a parameter
                    if (origin == null) {
                        toBackTracedInstructions.add(t);
                    }
                    // object origin is a static attribute
                    // or from android/java api method result (in this case we may
                    // mark an unimportant or locally created object as important)
                    else if (origin.getInstruction().startsWith("sget")) {
                        toBackTracedInstructions.add(t);
                    }
                    else if(origin.getInstruction().startsWith("move-result-object")){
                        TraceInstruction prevInst = flattenedTrace.get(flattenedTrace.indexOf(origin) - 1);
                        if(prevInst.getInstruction().startsWith("invoke-") && isGlobalStateChangingMethodInvocation(prevInst)){
                            toBackTracedInstructions.add(t);
                        }
                    }
                }
            }

            //5- all non-pure static methods on java/android classes
            // such as calling add() method on a passed array list object
            if(instruction.startsWith("invoke-static")){
                String[] splits = instruction.split(" ");
                String classMethodSig = splits[splits.length - 2];
                String classType = classMethodSig.split("->")[0];
                String methodName = classMethodSig.split("->")[1];
                // checking the method is state changing
                // (here we may miss some important state changing methods because our lists are not complete)
                if (!immutableClassesSet.contains(classType)
                        && !isMethodPure(classMethodSig)) {
                    toBackTracedInstructions.add(t);
                }
            }
        }

        return toBackTracedInstructions;
    }

    static TraceInstruction getArrayOrigin(TraceInstruction givenTraceInstruction,
                                            ArrayList<TraceInstruction> trace){
        String arrayToCheckRegNumber;
        String givenInstruction = givenTraceInstruction.getInstruction();
        if( givenInstruction.startsWith("aput") ||
                givenInstruction.startsWith("aget-object") ){
            arrayToCheckRegNumber = givenInstruction.split(" ")[2];
        }
        else {
            throw new IllegalArgumentException();
        }

        int ti_index = trace.indexOf(givenTraceInstruction);
        if(ti_index == -1){
            throw new IllegalArgumentException();
        }

        int instructionIndex = ti_index;
        while (instructionIndex >= 0){
            TraceInstruction t = trace.get(instructionIndex);
            // if the instruction defines a new value to a register
            String[] def = t.getRegisterDefs();
            if(def.length == 1){
                String defRegNum = def[0];
                if(defRegNum.equals(arrayToCheckRegNumber)){
                    if(t.getInstruction().startsWith("move-result-object") ||
                            t.getInstruction().startsWith("new-array") ||
                            t.getInstruction().startsWith("sget-object")){
                        return t;
                    }
                    else if(t.getInstruction().startsWith("aget-object")
                            || t.getInstruction().startsWith("move-object")
                            || t.getInstruction().startsWith("move ")){
                        arrayToCheckRegNumber = t.getRegisterUses()[0];
                    }
                    else if(t.getInstruction().startsWith("iget-object")){
                        return getObjectOrigin(t, trace);
                    }
                    else {
//                        System.err.println(t.getInstruction() + " for " + givenTraceInstruction.getLogLine());
                        throw new IllegalStateException(t.getInstruction() + " for " + givenTraceInstruction.getLogLine());
                    }
                }
            }
            instructionIndex--;
        }
        return null;
    }

    static TraceInstruction getObjectOrigin(TraceInstruction givenTraceInstruction,
                                             ArrayList<TraceInstruction> trace){

        String objectToCheckRegNumber;
        String givenInstruction = givenTraceInstruction.getInstruction();

        // finding the register that is containing the reference object
        if((givenInstruction.startsWith("invoke")
                && !givenInstruction.startsWith("invoke-static"))){
            objectToCheckRegNumber = givenInstruction.split(" ")[1];
        }
        else if(givenInstruction.startsWith("iput")
                || givenInstruction.startsWith("iget")){
            objectToCheckRegNumber = givenInstruction.split(" ")[2];
        }

        else {
            throw new IllegalArgumentException();
        }

        int ti_index = trace.indexOf(givenTraceInstruction);
        if(ti_index == -1){
            throw new IllegalArgumentException();
        }
        int instructionIndex = ti_index;
        // move back instruction by instruction to locate the origin
        while (instructionIndex >= 0){
            TraceInstruction t = trace.get(instructionIndex);
            // if the instruction defines a new value to a register
            String[] def = t.getRegisterDefs();
            if(def.length == 1){
                String defRegNum = def[0];
                // if the define is on the regNumber
                if(defRegNum.equals(objectToCheckRegNumber)){

                    if(t.getInstruction().startsWith("sget")
                            || t.getInstruction().startsWith("new-instance")
                            || t.getInstruction().startsWith("const")
                            || t.getInstruction().startsWith("move-exception")){
                        return t;
                    }

                    else if(t.getInstruction().startsWith("move-result-object")){
                        if(!checkIfMoveResultIsUseless(t, trace)){
                            return t;
                        }
                    }

                    else if(t.getInstruction().startsWith("iget-object") ||
                            t.getInstruction().startsWith("move-object") ||
                            // the simple move instruction is needed because of the move instructions added in flattening process
                            t.getInstruction().startsWith("move ")){
                        objectToCheckRegNumber = t.getRegisterUses()[0];
                    }

                    else if(t.getInstruction().startsWith("aget-object")){
                        return getArrayOrigin(t, trace);
                    }

                    else {
                        throw new IllegalStateException(t.getInstruction() + " for " + givenTraceInstruction.getLogLine());
                    }

                }
            }
            instructionIndex--;
        }
        return null;
    }


    // a useless move-result is any move-result that writes a value
    // to register which already is pointing to the same object
    static boolean checkIfMoveResultIsUseless(TraceInstruction ti,
                                               ArrayList<TraceInstruction> traceInstructions){
        JSONObject jo = ti.getRegisterContentsInfo();
        ArrayList<String> keys = new ArrayList();
        for (Iterator<String> it = jo.keys(); it.hasNext(); ) {
            String k = it.next();
            keys.add(k);
        }
        if(keys.size() != 1){
            throw new IllegalStateException();
        }

        String registerID = keys.get(0);
        String registerContentObjectIdBeforeMove;
        try {
            registerContentObjectIdBeforeMove = jo.getString(registerID);
        } catch (JSONException e) {
//            return false;
            throw new IllegalStateException(ti.getLogLine(),e);
        }

        if(registerContentObjectIdBeforeMove.startsWith("Num")){
            return false;
        }

        if(registerContentObjectIdBeforeMove.contains(",")){
            int f = registerContentObjectIdBeforeMove.indexOf(',');
            registerContentObjectIdBeforeMove = registerContentObjectIdBeforeMove.substring(0,f);
        }
        else if(registerContentObjectIdBeforeMove.startsWith("Obj:null")){
            return false;
        }
        else {
            throw new IllegalStateException(registerContentObjectIdBeforeMove);
        }

        int instructionPos = traceInstructions.indexOf(ti);
        //since metadata info for each instruction shows the contents of registers
        // before execution of instruction, we have to look in next instructions to see what has happened
        for (int i = instructionPos + 1; i < traceInstructions.size(); i++) {
            TraceInstruction ti2 = traceInstructions.get(i);
            JSONObject jo2 = ti2.getRegisterContentsInfo();
            // TODO == shortcut to check if ti2 is not writing new value to register
            if(jo2 != null && jo2.has(registerID)){
                try {
                    // checking object id the same
                    String c = jo2.getString(registerID);
                    if(c.contains(",")){
                        c = c.substring(0, c.indexOf(','));
                    }
                    return registerContentObjectIdBeforeMove.equals(c);

                } catch (JSONException e) {
//                    return false;
                    throw new IllegalStateException(ti2.getLogLine(),e);
                }
            }
        }
        return false;
    }


    // a useless field-use is a get operation on a field that we have set
    // its value ourselves in previous instructions
    static boolean checkFieldUseIsUseless(TraceInstruction ti,
                                           ArrayList<TraceInstruction> traceInstructions){
        if(ti.getInstruction().startsWith("sget")){
            String[] splits = ti.getInstruction().split(" ");
            String sig = splits[splits.length - 2];
            int originalIndex = traceInstructions.indexOf(ti);
            for (int i = originalIndex - 1; i >= 0 ; i--) {
                TraceInstruction ti2 = traceInstructions.get(i);
                if(ti2.getInstruction().startsWith("sput")){
                    String[] splits2 = ti2.getInstruction().split(" ");
                    String sig2 = splits2[splits2.length - 2];
                    if(sig.equals(sig2)){
                        return true;
                    }
                }
            }
            return false;
        }
        else if(ti.getInstruction().startsWith("iget")){
            // finding out the id of reference object
            String[] splits1 = ti.getInstruction().split(" ");
            String sig = splits1[splits1.length - 2];
            String refObjectRegId1 = splits1[splits1.length - 3];
            JSONObject jo1 = ti.getRegisterContentsInfo();
            String objectIdOfObjectInReg1;
            try {
                objectIdOfObjectInReg1 = jo1.getString(refObjectRegId1);
            }
            catch (JSONException e) {
                throw new IllegalStateException(ti.getLogLine(),e);
//                return false;
            }
            // if reference object is null or some invalid value, return false
            if ((objectIdOfObjectInReg1.startsWith("Obj:") ||
                    objectIdOfObjectInReg1.startsWith("Amb:"))
                    && objectIdOfObjectInReg1.contains(",")){
                objectIdOfObjectInReg1 = objectIdOfObjectInReg1.substring(0, objectIdOfObjectInReg1.indexOf(','));
            }
            else {
                return false;
            }

            // going back instruction per instruction to check if any instruction has written a value
            // to the field that has been used in the iget instruction
            int originalIndex = traceInstructions.indexOf(ti);
            for (int i = originalIndex - 1; i >= 0 ; i--) {
                TraceInstruction ti2 = traceInstructions.get(i);
                if(ti2.getInstruction().startsWith("iput")){
                    // getting the id of reference object in the iput instruction
                    String[] splits2 = ti2.getInstruction().split(" ");
                    String sig2 = splits2[splits2.length - 2];
                    // if field sig matches the field sig in the iget
                    if(sig.equals(sig2)) {
                        String refObjectRegId2 = splits2[splits2.length - 3];
                        JSONObject jo2 = ti2.getRegisterContentsInfo();
                        String objectIdOfObjectInReg2;
                        try {
                            objectIdOfObjectInReg2 = jo2.getString(refObjectRegId2);
                        }
                        catch (JSONException e) {
//                            return false;
                            throw new IllegalStateException(ti.getLogLine(),e);
                        }

                        //TODO necessary?
                        if(!objectIdOfObjectInReg2.contains(",")){
                            return false;
                        }

                        if (objectIdOfObjectInReg2.startsWith("Obj:") ||
                                objectIdOfObjectInReg2.startsWith("Amb:")){
                            objectIdOfObjectInReg2 = objectIdOfObjectInReg2.substring(0, objectIdOfObjectInReg2.indexOf(','));
                        }
                        if(objectIdOfObjectInReg1.equals(objectIdOfObjectInReg2)){
                            return true;
                        }
                        else {
                            return false;
                        }
                    }

                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    static boolean isMethodPure(String classMethodSig){
        if(pureMethodsSet.contains(classMethodSig)) return true;
        String classPath = classMethodSig.split("->")[0];
        String methodSig = classMethodSig.split("->")[1];
        if(pureMethodsSet.contains(classPath+ "->*")) return true;
        if(pureMethodsSet.contains("*->" + methodSig)) return true;
        return false;
    }

    static boolean isTypeImmutable(String type){
        return immutableClassesSet.contains(type);
    }

}
