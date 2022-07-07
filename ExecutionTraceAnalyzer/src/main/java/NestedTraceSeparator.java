import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NestedTraceSeparator extends Analyzer{

    public static void separateIntermediateExecutionTracesOfApp(File appDir){
        // first we read all the traces of an app
        // then we make a map of method to trace
        // in this map we only keep unique traces
        // for each trace that is not already written to file
        // we also write it

        HashMap<String, List<ExecutionTrace>> allSeenExecutionTraces = new HashMap<>();

        for(File classDir: appDir.listFiles()){
            if(classDir.isFile()){
                // for classes that had too large name
                throw new UnsupportedOperationException();
            }
            else {
                for (File methodDir: classDir.listFiles()){
                    if(methodDir.isFile()){
                        // for methods that had too large name
                        throw new UnsupportedOperationException();
                    }
                    System.out.println("analyzing "+methodDir);
                    File methodInfoJsonFile = new File(methodDir, "methodMetaData.json");
                    try {
                        List<String> lines = Files.readAllLines(methodInfoJsonFile.toPath());
                        String jsonData = String.join("\n", lines);
                        JSONObject methodInfo = new JSONObject(jsonData);

                        for(File traceFile: methodDir.listFiles()){
                            if(traceFile.isFile() && traceFile.getName().startsWith("trace_")){
                                ArrayList<ExecutionTrace> allNestedTraces = separateIntermediateExecutionTracesFrom(traceFile, methodInfo);
                                for(ExecutionTrace t: allNestedTraces){
                                    addTraceIfUnique(allSeenExecutionTraces, t);
                                }
                            }
                        }

                    }
                    catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }

        writeNestedTraces(appDir, allSeenExecutionTraces);

    }

    private static ArrayList<ExecutionTrace> separateIntermediateExecutionTracesFrom(File inputExecutionTraceFile, JSONObject methodInfo){
        try {
            // we parse the execution trace logs and turn them into an ArrayList of TraceInstruction
            // each TraceInstruction corresponds to a single line in the execution trace
            ArrayList<TraceInstruction> traceInstructions = createTracedInstructions(inputExecutionTraceFile, methodInfo);

            // we then create a tree structure of execution trace which is an ArrayList of
            // instructions or another ArrayList for nested instruction of invoked methods
            ArrayList<Object> separatedMethodCallsTraceInstructions = createMethodCallsTree(traceInstructions);

            ArrayList<ExecutionTrace> allNestedExecutionTraces = new ArrayList<>();
            addAllNestedExecutionTraces(separatedMethodCallsTraceInstructions, methodInfo, allNestedExecutionTraces, false);
            return allNestedExecutionTraces;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected static void addAllNestedExecutionTraces(ArrayList<?> separatedMethodCallsTraceInstructions,
                                                      JSONObject methodInfo,
                                                      ArrayList<ExecutionTrace> result,
                                                      boolean traceIsNested){

        result.add(new ExecutionTrace(separatedMethodCallsTraceInstructions, methodInfo, traceIsNested, true));
        for (int i = 0; i < separatedMethodCallsTraceInstructions.size(); i++) {
            Object o = separatedMethodCallsTraceInstructions.get(i);
            if (o instanceof ArrayList) {
                ArrayList<?> a = (ArrayList<?>) o;
                TraceInstruction pervInstruction = (TraceInstruction) separatedMethodCallsTraceInstructions.get(i-1);
                JSONObject invokedMethodInfo = pervInstruction.getToInvokeMethodInfo();
                if(invokedMethodInfo == null){
                    throw new IllegalStateException("methodInfo json is null!!!!!!!#######");
                }
                addAllNestedExecutionTraces(a, invokedMethodInfo, result, true);
            } else if (!(o instanceof TraceInstruction)) {
                throw new IllegalStateException();
            }
        }
    }


    private static void addTraceIfUnique(HashMap<String, List<ExecutionTrace>> allSeenExecutionTraces, ExecutionTrace nestedExecutionTrace){
        String classMethodSig = nestedExecutionTrace.methodInfo.getString("signature").replace("/", ".");
        if(allSeenExecutionTraces.containsKey(classMethodSig)){
            List<ExecutionTrace> seenMethodTraces = allSeenExecutionTraces.get(classMethodSig);
            if(!doesTraceExistIn(nestedExecutionTrace, seenMethodTraces)){
                seenMethodTraces.add(nestedExecutionTrace);
            }
        }
        else {
            ArrayList<ExecutionTrace> methodTracesList = new ArrayList<>();
            methodTracesList.add(nestedExecutionTrace);
            allSeenExecutionTraces.put(classMethodSig, methodTracesList);
        }
    }

    private static boolean doesTraceExistIn(ExecutionTrace trace, List<ExecutionTrace> allSeenTraces){
        for(ExecutionTrace et: allSeenTraces){
            if(et.traceSig.equals(trace.traceSig)){
                return true;
            }
        }
        return false;
    }

    private static void writeNestedTraces(File appDir, HashMap<String, List<ExecutionTrace>> allSeenExecutionTraces ){
        for(String methodClassSig: allSeenExecutionTraces.keySet()){
            String className = methodClassSig.split("->")[0]
                    .substring(1); // removing the initial L
            String methodName = methodClassSig.split("->")[1];
            int counter = 0;
            List<ExecutionTrace> seenTraces = allSeenExecutionTraces.get(methodClassSig);


            File methodDirFile  = new File(new File(appDir, className), methodName);
            methodDirFile = Utils.checkForTooLongFileNamesAndFixIfNecessary(methodDirFile);
            File methodInfoJsonFile = new File(methodDirFile, "methodMetaData.json");
            if(!methodInfoJsonFile.exists()){
                try {
                    LogWriter.writeTextToFile(seenTraces.get(0).methodInfo.toString(), methodInfoJsonFile.getAbsolutePath(), false);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }

            for(ExecutionTrace et: seenTraces){
                if(et.isNestedTrace) {
                    try {
                        File output = new File(methodDirFile, "trace_nested_"+counter+".txt");
                        System.out.println("writing " + output.getPath());
                        LogWriter.writeTraceInstructionTreeToFile(et.traceArray, output.getAbsolutePath(), false, true);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
                counter++;
            }
        }
    }




}
