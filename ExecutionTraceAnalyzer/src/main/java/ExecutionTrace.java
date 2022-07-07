import org.json.JSONObject;

import java.util.ArrayList;

public class ExecutionTrace {
    final ArrayList traceArray;
    final JSONObject methodInfo;
    final boolean isNestedTrace;
    String traceSig;

    public ExecutionTrace(ArrayList traceArray, JSONObject methodInfo, boolean isNestedTrace, boolean fillMethodSig) {
        this.traceArray = traceArray;
        this.methodInfo = methodInfo;
        this.isNestedTrace = isNestedTrace;
        if(fillMethodSig) {
            StringBuilder sb = new StringBuilder();
            addMethodSig(traceArray, sb);
            traceSig = sb.toString();
        }
    }

    private void addMethodSig(ArrayList separatedExecutionTrace, StringBuilder sb){
        for(Object o: separatedExecutionTrace){
            if(o instanceof TraceInstruction){
                TraceInstruction t = (TraceInstruction) o;
                sb.append(t.getContainingMethod()).append(">>").append(t.getInstruction()).append("\n");
            }
            else if(o instanceof ArrayList){
                addMethodSig((ArrayList) o, sb);
            }
            else {
                throw new IllegalArgumentException(o.getClass().toString());
            }
        }
    }

    public ExecutionTrace clone(){
        ExecutionTrace et = new ExecutionTrace(traceArray, methodInfo, isNestedTrace, false);
        et.traceSig = this.traceSig;
        return et;
    }
}
