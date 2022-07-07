import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class TraceInstruction {
    // this is only used for creating nested traces
    // in the next parts of analysis this may be null
    private String rawLine;

    private final String methodCallStack;
    private final String containingMethod;

//    private final JSONObject containingMethodInfo;

    private final String instruction;
    private final String[] instructionSplits;

    private final String[] registerUses;
    private final String[] registerDefs;

    private JSONObject toInvokeMethodInfo;
    private JSONObject registerContentsInfo;

    public Object oldTraceInstruction;

    TraceInstruction(String rawLine, String methodContextStackStr
//            , JSONObject containingMethodInfo
    ) {
        rawLine = rawLine.trim();
        this.rawLine = rawLine;
        methodCallStack = methodContextStackStr;
        String[] methodCallStackSplits = methodCallStack.split(">>");
        containingMethod = methodCallStackSplits[methodCallStackSplits.length-1];
//        this.containingMethodInfo = containingMethodInfo;

        instruction = rawLine.split("\\|")[0].trim();
        instructionSplits = instruction.split(" ");

        // if we have metadata for the instruction
        if(rawLine.endsWith("}")){
            // has register content json and the invoked method data
            if(rawLine.contains("}|")){
                String invokedMethodMetaData = rawLine.substring(rawLine.lastIndexOf('|')+1);
                try {
                    toInvokeMethodInfo = new JSONObject(invokedMethodMetaData);
                }catch (JSONException e){
                    throw new IllegalArgumentException(e);
                }
                String[] s = rawLine.replace("|"+ invokedMethodMetaData,"").split("\\|");
                try {
                    registerContentsInfo = new JSONObject(s[1]);
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
            }
            // has register content only
            else {
                String[] s = rawLine.split("\\|");
                try {
                    registerContentsInfo = new JSONObject(s[1]);
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        ArrayList[] defUses = getRegisterDefAndUsesForInstruction(this.instruction);
        ArrayList<String> d = defUses[0];
        ArrayList<String> u = defUses[1];
        this.registerDefs = d.toArray(new String[d.size()]);
        this.registerUses = u.toArray(new String[u.size()]);
    }

    TraceInstruction(String methodCallStack, String instruction, JSONObject toInvokeMethodInfo, JSONObject registerContentsInfo
//            , JSONObject containingMethodInfo
    ){
        this.methodCallStack = methodCallStack.trim();
        String[] methodCallStackSplits = this.methodCallStack.split(">>");
        containingMethod = methodCallStackSplits[methodCallStackSplits.length-1];

        this.instruction = instruction.trim();
        instructionSplits = this.instruction.split(" ");

        ArrayList[] defUses = getRegisterDefAndUsesForInstruction(instruction);
        ArrayList<String> d = defUses[0];
        ArrayList<String> u = defUses[1];
        this.registerDefs = d.toArray(new String[d.size()]);
        this.registerUses = u.toArray(new String[u.size()]);

        this.toInvokeMethodInfo = toInvokeMethodInfo;
        this.registerContentsInfo = registerContentsInfo;
//        this.containingMethodInfo = containingMethodInfo;

    }

    private static ArrayList[] getRegisterDefAndUsesForInstruction(String instructionTrace){
        ArrayList<String> registerDefs = new ArrayList();
        ArrayList<String> registerUses = new ArrayList();
//        String itrace = instructionTrace.trim();
//
        String[] splits = instructionTrace.split(" ");
        if (splits[0].startsWith("return-void")){}
        else if (splits[0].startsWith("move-result") || instructionTrace.startsWith("move-exception")){
           registerDefs.add(splits[1]);
        }
        else if (splits[0].startsWith("move")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("return")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("const")){
            registerDefs.add(splits[1]);
        }
        else if (splits[0].startsWith("check-cast")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("instance-of")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("array-length")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("new-instance")){
            registerDefs.add(splits[1]);
        }
        else if (splits[0].startsWith("new-array")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("filled-new-array")){
            for (int i = 1; i < splits.length - 1 ; i++) {
                registerUses.add(splits[i]);
            }
        }
        else if (splits[0].startsWith("fill-array-data")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("throw")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("packed-switch")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("sparse-switch")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("cmp")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
            registerUses.add(splits[3]);
        }
        else if (splits[0].startsWith("if-") && splits[0].length() == 5){
            registerUses.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("if-") && splits[0].length() == 6){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("aget")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
            registerUses.add(splits[3]);
        }
        else if (splits[0].startsWith("aput")){
            registerUses.add(splits[1]);
            registerUses.add(splits[2]);
            registerUses.add(splits[3]);
        }
        else if (splits[0].startsWith("iget")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("iput")){
            registerUses.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("sget")){
            registerDefs.add(splits[1]);
        }
        else if (splits[0].startsWith("sput")){
            registerUses.add(splits[1]);
        }
        else if (splits[0].startsWith("invoke-")){
            for (int i = 1; i < splits.length - 2; i++) {
                registerUses.add(splits[i]);
            }
        }
        else if (splits[0].startsWith("neg-")
                || splits[0].startsWith("not-")
                || splits[0].contains("-to-")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].contains("-int/lit")
                || splits[0].contains("rsub-int")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].contains("2addr")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
        }
        else if (splits[0].startsWith("add-")
                || splits[0].startsWith("sub-")
                || splits[0].startsWith("mul-")
                || splits[0].startsWith("div-")
                || splits[0].startsWith("rem-")
                || splits[0].startsWith("and-")
                || splits[0].startsWith("or-")
                || splits[0].startsWith("xor-")
                || splits[0].startsWith("shl-")
                || splits[0].startsWith("shr-")
                || splits[0].startsWith("ushr-")){
            registerDefs.add(splits[1]);
            registerUses.add(splits[2]);
            registerUses.add(splits[3]);
        }
        else throw new IllegalArgumentException("error on get use/def of "+instructionTrace);
        ArrayList[] result = new ArrayList[2];
        result[0] = registerDefs;
        result[1] = registerUses;
        return result;
    }

    public String getRawLine() {
        return rawLine;
    }

    public String getMethodCallStack() {
        return methodCallStack;
    }

    public String[] getSeparatedMethodCallStack(){
        return methodCallStack.split(">>");
    }

    public String getInstruction() {
        return instruction;
    }

    public String[] getInstructionSplits(){
        return instructionSplits;
    }

    public String[] getRegisterUses() {
        return registerUses;
    }

    public String[] getRegisterDefs() {
        return registerDefs;
    }

    public JSONObject getToInvokeMethodInfo(){
        return toInvokeMethodInfo;
    }

    public String getContainingMethod(){
        return containingMethod;
    }

    public JSONObject getRegisterContentsInfo(){
        return registerContentsInfo;
    }

    public String getLogLine(){
        String r = methodCallStack + ">>" + instruction;
        if(registerContentsInfo != null){
            r = r + "|" + registerContentsInfo.toString();
        }
        if(toInvokeMethodInfo != null){
            r = r + "|" + toInvokeMethodInfo.toString();
        }

        return r;
    }

//    public JSONObject getContainingMethodInfo() {
//        return containingMethodInfo;
//    }
}
