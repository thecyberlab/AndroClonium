import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class Analyzer {

    protected static ArrayList<TraceInstruction> createTracedInstructions(File file, JSONObject executedMethodInfo) throws IOException {
        ArrayList<TraceInstruction> traceInstructions = new ArrayList<>();
        List<String> lines = Files.readAllLines(file.toPath());

        if(lines.get(0) == null || !lines.get(0).startsWith("Start of method context:")){
            throw new IllegalStateException();
        }

        Stack<String> methodCallStack = new Stack<>();
//        Stack<JSONObject> methodContextsMetadataStack = new Stack<>();
//        methodContextsMetadataStack.add(executedMethodInfo);
        String methodCallStackString = null;

        // analyze each line
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            line = line.trim();
            if (line.startsWith("Start of method context:")) {
                String methodContext = line.substring("Start of method context:".length());
                methodCallStack.add(methodContext);
                String s = methodCallStack.toString();
                methodCallStackString = s.substring(1, s.length() - 1).replace(", ", ">>");
//                if(i != 0){
//                    TraceInstruction pervInstruction = traceInstructions.get(i-1);
//                    if(!pervInstruction.getInstruction().startsWith("invoke-")){
//                        throw new IllegalStateException();
//                    }
//                    JSONObject toInvokeMethodInfo =pervInstruction.getToInvokeMethodInfo();
//                    if(toInvokeMethodInfo == null){
//                        throw new IllegalStateException();
//                    }
//                    methodContextsMetadataStack.add(toInvokeMethodInfo);
//                }
            }
            else if (line.startsWith("End of method context:")) {
                String methodContext = line.substring("End of method context:".length());
                if (!methodContext.equals(methodCallStack.peek())) {
                    throw new IllegalStateException();
                }
                methodCallStack.pop();
                String s = methodCallStack.toString();
                methodCallStackString = s.substring(1, s.length() - 1).replace(", ", ">>");
//                methodContextsMetadataStack.pop();
            }
            else {
                // ignore GOTO and Monitor instructions since they do not have any interesting information to capture
                if (!line.startsWith("goto") && !line.startsWith("monitor") && !line.startsWith("nop")) {
                    try {
                        traceInstructions.add(new TraceInstruction(line, methodCallStackString));
//                        traceInstructions.add(new TraceInstruction(line, methodCallStackString, methodContextsMetadataStack.peek()));
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        }
        return traceInstructions;
    }

    protected static ArrayList<Object> createMethodCallsTree(ArrayList<TraceInstruction> traceInstructions){
        ArrayList<Object> result = new ArrayList<>();
        String initialMethodCallStack = traceInstructions.get(0).getMethodCallStack();
        for (int i = 0; i < traceInstructions.size(); i++) {
            TraceInstruction ti = traceInstructions.get(i);
            if(ti.getMethodCallStack().equals(initialMethodCallStack)){
                result.add(ti);
            }
            else {
                ArrayList<TraceInstruction> tmp = new ArrayList<>();
                while (!ti.getMethodCallStack().equals(initialMethodCallStack)){
                    tmp.add(ti);
                    i++;
                    if(i == traceInstructions.size()) break;
                    ti = traceInstructions.get(i);
                }
                i--;
                result.add(createMethodCallsTree(tmp));
            }
        }
        return result;
    }


}
