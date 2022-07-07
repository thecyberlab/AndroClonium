import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import org.apache.commons.codec.digest.DigestUtils;

public class ExecutionTraceAnalyzer extends Analyzer{
    static String fileUnderAnalysisPath = null;

    public static void analyzeTracesOfApp(File appDir) {
        for(File appClassDir: appDir.listFiles()){
            if(appClassDir.isFile() && appClassDir.getName().equals("hashedClassNamesMap.txt")){
                continue;
            }
            for(File appMethodDir: appClassDir.listFiles()){
                if(appMethodDir.isFile() && appMethodDir.getName().equals("hashedMethodNamesMap.txt")){
                    continue;
                }

                // reading the methodMetaData.json and converting it to a JSONObject
                File methodInfoJsonFile = new File(appMethodDir, "methodMetaData.json");
                List<String> lines;
                try {
                    lines = Files.readAllLines(methodInfoJsonFile.toPath());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                String jsonData = String.join("\n", lines);
                JSONObject methodInfo = new JSONObject(jsonData);

                // creating maps to keep track of unique slices and unique fragments across all the method traces
                Set<ArrayList<String>> finalFragmentsSet = new HashSet<>();
                HashMap<String, HashSet<String>> finalFragmentToRawFragmentMap = new HashMap<>();
                HashMap<String, String> sliceHashToSliceMap = new HashMap();
                HashMap<String, HashSet<String>> sliceHashToRawFragmentsMap = new HashMap<>();

                // for each trace we analyze it and fragment it
                for(File traceFile: appMethodDir.listFiles()){
                    if(traceFile.isFile() && traceFile.getName().startsWith("trace_")){
                        fileUnderAnalysisPath = traceFile.getAbsolutePath();
                        try {
                            analyzeTracesOfMethod(traceFile, methodInfo, finalFragmentsSet, finalFragmentToRawFragmentMap, sliceHashToRawFragmentsMap, sliceHashToSliceMap);
                        }
                        catch (Exception e) {
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(appMethodDir)
                                        .append("\n")
                                        .append(traceFile)
                                        .append("\n")
                                        .append(Utils.stackTraceToString(e))
                                        .append("\n=====\n");
                                String errorLog = sb.toString();
                                LogWriter.writeTextToFile(errorLog,"./SliceAndFragmentError.txt" , true);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            System.err.println("Error on analyzing method : " + appMethodDir.getAbsolutePath());
//                             System.exit(0);
                        }
                    }
                }

                // writing the final extracted fragments to file
                File fragOutPutFile = new File(appMethodDir, "frags.txt");
                BufferedWriter fragOutPutWriter;
                try {
                    fragOutPutWriter = new BufferedWriter(new FileWriter(fragOutPutFile));
                    for(ArrayList<String> frag: finalFragmentsSet){
                        fragOutPutWriter.write(String.join("|", frag) + "\n");
                    }
                    fragOutPutWriter.flush();
                    fragOutPutWriter.close();
                } catch (IOException e) {
                    throw new IllegalStateException();
                }

                File finalFragmentToRawFragmentOutputFile = new File(appMethodDir, "finalFragmentToRawFragment.txt");
                BufferedWriter finalFragmentToRawFragmentWriter;
                try {
                    finalFragmentToRawFragmentWriter = new BufferedWriter(new FileWriter(finalFragmentToRawFragmentOutputFile));
                    for(String k: finalFragmentToRawFragmentMap.keySet()){
                        HashSet<String> h = finalFragmentToRawFragmentMap.get(k);
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        for(String s: h){
                            sb.append(s).append("#$#");
                        }
                        String res = sb.substring(0, sb.length()-3) + "]";
                        finalFragmentToRawFragmentWriter.write(k + "|" + res + "\n");
                    }
                    finalFragmentToRawFragmentWriter.flush();
                    finalFragmentToRawFragmentWriter.close();
                }
                catch (IOException e){
                    throw new IllegalStateException(e);
                }

                File sliceToRawFragmentsMapOutputFile = new File(appMethodDir, "sliceToRawFragments.txt");
                BufferedWriter sliceToRawFragmentsWriter;
                try {
                    sliceToRawFragmentsWriter = new BufferedWriter(new FileWriter(sliceToRawFragmentsMapOutputFile));
                    for(String k: sliceHashToRawFragmentsMap.keySet()){
                        HashSet<String> h = sliceHashToRawFragmentsMap.get(k);
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        for(String s: h){
                            sb.append(s).append("#$#");
                        }
                        String res = sb.substring(0, sb.length()-3) + "]";
                        sliceToRawFragmentsWriter.write(k + "|" + res + "\n");
                    }
                    sliceToRawFragmentsWriter.flush();
                    sliceToRawFragmentsWriter.close();
                }
                catch (IOException e){
                    throw new IllegalStateException(e);
                }


                File sliceHashToSliceOutputFile = new File(appMethodDir, "sliceHashToSlice.txt");
                BufferedWriter sliceHashToSliceWriter;
                try {
                    sliceHashToSliceWriter = new BufferedWriter(new FileWriter(sliceHashToSliceOutputFile));
                    for(String k: sliceHashToSliceMap.keySet()){
                        String slice = sliceHashToSliceMap.get(k);
                        sliceHashToSliceWriter.write(k + "|" + slice + "\n");
                    }
                    sliceHashToSliceWriter.flush();
                    sliceHashToSliceWriter.close();
                }
                catch (IOException e){
                    throw new IllegalStateException(e);
                }



//                // writing the final extracted slice with their corresponding fragments to file
//                File fragmentToSliceMapOutPutFile = new File(appMethodDir, "fragmentToSliceMap.txt");
//                BufferedWriter fragmentToSliceMapOutPutFileWriter;
//                try {
//                    fragmentToSliceMapOutPutFileWriter = new BufferedWriter(new FileWriter(fragmentToSliceMapOutPutFile));
//                    for(String k: fragmentToSliceMap.keySet()){
//                        HashSet<Integer> h = fragmentToSliceMap.get(k);
//                        fragmentToSliceMapOutPutFileWriter.write(k + "|" + h.toString() +  "\n");
//                    }
//                    fragmentToSliceMapOutPutFileWriter.flush();
//                    fragmentToSliceMapOutPutFileWriter.close();
//                } catch (IOException e) {
//                    throw new IllegalStateException();
//                }

                // finish
            }
        }

    }

    private static void analyzeTracesOfMethod(File executionTraceFile,
                                            JSONObject methodInfo,
                                            Set<ArrayList<String>> finalFragmentsSet,
                                              HashMap<String, HashSet<String>> finalFragmentToRawFragmentMap,
                                              HashMap<String, HashSet<String>> sliceHashToRawFragmentsMap,
                                              HashMap<String, String> sliceHashToSliceMap) throws Exception{

        // the folder that the intermediate result of an execution trace analysis will be saved in it
        File executionTraceAnalysisDir = new File(executionTraceFile.getParentFile(), executionTraceFile.getName().replace(".txt", "_analysis"));

        // parsing the execution trace file to an ArrayList of TraceInstruction
        ArrayList<TraceInstruction> rawTraces = createTracedInstructions(executionTraceFile, methodInfo);

        // creating a tree-like model of execution trace where each nested execution trace
        // is represented by an array list
        ArrayList<?> rawExeTraceTree = createMethodCallsTree(rawTraces);

        // converting this tree-like ArrayList of instructions
        // to an ExecutionTrace object
        boolean isNested = executionTraceFile.getName().startsWith("trace_nested");
        ArrayList convertedSeparatedMethodCalls= convertNestedTracesFromArrayListToExecutionTrace(rawExeTraceTree);
        ExecutionTrace exeTrace = new ExecutionTrace(convertedSeparatedMethodCalls, methodInfo, isNested, false);


//        //TODO implement
//        // normalize reflective constructor calls


        //  TODO check logic
        // replacing the primitive and String values that are generated from methods with constant inputs to const-* instructions
        ConstantTransformationsSimplifier.simplifyConstantTransformations(exeTrace, new ArrayList<>(), 0);


        //  TODO check logic
        // we start our reflection normalization, here we try to replace reflection calls
        // with normal method calls.
        // This is not trivial since we have to undo the part of execution trace that puts
        // the real arguments into an array for passing to the reflection API.
        // To do this first we need to keep track of values which are the arguments to the normal method call.
        // The trueArgsSrcInstMap maps the new non-reflective method-invocation
        // instruction to the instructions that are responsible for the creation of values that are the
        // method arguments. The need for this is that we need to put a move instruction after these
        // argument creating instructions to connect them with new method-invocation instruction.
        // However, we should do this part later and not here since
        // after execution trace flattening step the register numbers will change
        HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap = new HashMap<TraceInstruction, ArrayList<TraceInstruction>>();
        ReflectionNormalizer.removeReflectionCalls(exeTrace,new ArrayList<>(), trueArgsSrcInstMap);


        if(Config.shouldLogIntermediateFiles) {
            if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
            String a = executionTraceFile.getName().replace(".txt", "_analysis");
            String path = new File(executionTraceAnalysisDir, "intermediate_file_1_partial_reflection_normalized_" + a + ".txt").getAbsolutePath();
            LogWriter.writeTraceInstructionTreeToFile(convertedSeparatedMethodCalls, path, false,true);
        }



        // then we flatten our execution trace, meaning that we inline all invoked methods
        // that we have their execution trace. To do this we change the register numbers
        // in the invoked method to new numbers. In this conversion we have assumed we have
        // infinite number of registers
        int totalRegUse = (Integer) methodInfo.get("total registers");
        HashMap<TraceInstruction, TraceInstruction> updatedInstructions = new HashMap<>();
        HashSet<TraceInstruction> removedInstructions = new HashSet<>();
        Object[] res = InstructionTraceFlattener.flattenExecutionTraceOneLevel(exeTrace, totalRegUse,updatedInstructions,removedInstructions);

        Long newOffset = (Long) res[0];
        ExecutionTrace rawFlattenedInstructionTrace = (ExecutionTrace) res[1];
        Boolean flattened = (Boolean) res[2];

        while(flattened){
            res = InstructionTraceFlattener.flattenExecutionTraceOneLevel(rawFlattenedInstructionTrace, newOffset, updatedInstructions,removedInstructions);
            newOffset = (Long) res[0];
            rawFlattenedInstructionTrace = (ExecutionTrace) res[1];
            flattened = (Boolean) res[2];
        }

        // move the flattened instruction trace into an array of ArrayList<TraceInstruction>
        // since later methods take ArrayList<TraceInstruction> as an input
        ArrayList<TraceInstruction> flattenedTrace= new ArrayList<>();
        for (Object o:rawFlattenedInstructionTrace.traceArray) {
            TraceInstruction ti = (TraceInstruction) o;
            flattenedTrace.add(ti);
        }


        if(Config.shouldLogIntermediateFiles) {
            if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
            String a = executionTraceFile.getName().replace(".txt", "_analysis");
            String path = new File(executionTraceAnalysisDir, "intermediate_file_2_initial_flattened_trace_" + a + ".txt").getAbsolutePath();
            LogWriter.writeTraceInstructionTreeToFile(flattenedTrace, path, false, true);
        }


        //  TODO check logic
        // we finish the reflection normalization with putting a move-* instruction
        // which will correct the data-dependency flow
        ReflectionNormalizer.addDataDependencyConnectingInstructions(flattenedTrace, trueArgsSrcInstMap, updatedInstructions, removedInstructions);

        if(Config.shouldLogIntermediateFiles) {
            if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
            String a = executionTraceFile.getName().replace(".txt", "_analysis");
            String path = new File(executionTraceAnalysisDir, "intermediate_file_3_final_reflection_normalization_" + a + ".txt").getAbsolutePath();
            LogWriter.writeTraceInstructionTreeToFile(flattenedTrace, path, false, false);
        }


        // we remove any register aliasing in our execution trace
        flattenedTrace = RegisterAliasingRemover.removeRegisterAliasing(flattenedTrace);

        if(Config.shouldLogIntermediateFiles) {
            if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
            String a = executionTraceFile.getName().replace(".txt", "_analysis");
            String path = new File(executionTraceAnalysisDir, "intermediate_file_4_final_flattened_trace_after_register_unAliasing_" + a + ".txt").getAbsolutePath();
            LogWriter.writeTraceInstructionTreeToFile(flattenedTrace, path, false, false);
        }


        // here we start separating the instructions that could depict the net side effects of an execution trace.
        // Later we will use back tracing on them in our analysis
        ArrayList<TraceInstruction> toBacktrackInstructions = new ArrayList<>();

        // The first type effect a method can have is its return type if it does not return void
        int lastOne = flattenedTrace.size() - 1;
        if(!(flattenedTrace.get(lastOne).getInstruction().startsWith("return-void"))){
            toBacktrackInstructions.add(flattenedTrace.get(lastOne));
        }

        // The second type of effect is instructions that change the state of system
        // outside method scope by invoking methods that have global side effects such as printing to stdout, writing to a file, etc
        for (Object o: flattenedTrace) {
            TraceInstruction ti = (TraceInstruction) o;
            if(ImportantInstructionFinder.isGlobalStateChangingMethodInvocation(ti)){
                toBacktrackInstructions.add(ti);
            }
        }

        // the third kind of effect is instructions that change state of objects that have not been created
        // in the method scope. These changes can be made by changing static fields or changing fields on objects
        // that have been passed as argument to this method, or invoking methods on them that change state of those objects.
        ArrayList<TraceInstruction> r= ImportantInstructionFinder.getNonLocalObjectChangingInstructions(flattenedTrace);
        toBacktrackInstructions.addAll(r);

        if(Config.shouldLogIntermediateFiles){
            if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
            int counter = 0;
            for(TraceInstruction ti:toBacktrackInstructions){
                String path = new File(executionTraceAnalysisDir, "intermediate_file_important_instruction_" + counter + "_of_" + executionTraceFile.getName()).getAbsolutePath();
                LogWriter.writeTextToFile(ti.getLogLine(), path, false);
            }
        }

        int slice_counter = 0;
//        HashSet<String> sliceSigsSet = new HashSet();
//        HashSet<String> uniqueFragmentSignatures = new HashSet<>();

        // then for each important instruction, we perform back tracing on it
        // based on data-dependency on registers that the instructions is using.
        // All the instructions that are needed for execution of one important instruction
        // create a set of instructions which we call one slice.
        for (TraceInstruction ti: toBacktrackInstructions) {
            ArrayList<TraceInstruction> slice = Slicer.extractCodeSlice(ti,flattenedTrace);

            StringBuilder sb0 = new StringBuilder();
            for(TraceInstruction sliceTI: slice){
                sb0.append(sliceTI.getLogLine()).append("$%$%$");
            }
            String sliceString = sb0.toString();
            String sliceHash = DigestUtils.md5Hex(sliceString);
            if(sliceHashToSliceMap.containsKey(sliceHash)){
                continue;
            }
            sliceHashToSliceMap.put(sliceHash, sliceString);

//            String sliceSig = getSliceSig(slice);
//            // ignore if slice repeated
//            if(sliceSigsSet.contains(sliceSig)){
//                continue;
//            }
//            sliceSigsSet.add(sliceSig);

            if(Config.shouldLogIntermediateFiles) {
                if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
                // write the slice to slices file
                String p2 = executionTraceAnalysisDir.getAbsolutePath() + "/slices.txt";
                LogWriter.writeTextToFile("slice:" + slice_counter + "\n", p2, true);
                LogWriter.writeTextToFile("----\n", p2, true);
                LogWriter.writeTraceInstructionTreeToFile(slice, p2, true, false);
                LogWriter.writeTextToFile("==============\n", p2, true);
            }



            // program slices are too coarse grained for our analysis. So we break them
            // down to smaller pieces called fragments
            ArrayList<ArrayList<TraceInstruction>> sliceFragments = Fragmenter.fragmentSlice(slice);
            for (ArrayList<TraceInstruction> fragment:sliceFragments){

                StringBuilder sb1 = new StringBuilder();
                for(TraceInstruction fragTI: fragment){
                    sb1.append(fragTI.getContainingMethod())
                            .append(">>")
                            .append(fragTI.getInstruction())
                            .append("^*^*^");
                }
                String rawFragmentString = sb1.toString();

                if(sliceHashToRawFragmentsMap.containsKey(sliceHash)){
                    sliceHashToRawFragmentsMap.get(sliceHash).add(rawFragmentString);
                }
                else {
                    HashSet<String> h = new HashSet<>();
                    h.add(rawFragmentString);
                    sliceHashToRawFragmentsMap.put(sliceHash, h);
                }

                ArrayList<TraceInstruction> simpleFrag = removeRepeatedInstructionsInSlices(removeNotNecessaryInstructions(fragment));
                ArrayList<String> readyFragment = InstructionNormalizer.normalizeTraceInstructions(simpleFrag);

                StringBuilder sb = new StringBuilder();
                for (String s:readyFragment) {
                    sb.append(s);
                }
                String readyFragmentString = sb.toString();
                finalFragmentsSet.add(readyFragment);
                if(finalFragmentToRawFragmentMap.containsKey(readyFragmentString)){
                    finalFragmentToRawFragmentMap.get(readyFragmentString).add(rawFragmentString);
                }
                else {
                    HashSet<String> h = new HashSet<>();
                    h.add(rawFragmentString);
                    finalFragmentToRawFragmentMap.put(readyFragmentString, h);
                }

            }
            slice_counter ++;
        }

    }

    private static ArrayList<?> convertNestedTracesFromArrayListToExecutionTrace(ArrayList<?> rawTrace){
        ArrayList result = new ArrayList<>();
        for (int i = 0; i < rawTrace.size(); i++) {
            Object o = rawTrace.get(i);
            if(o instanceof TraceInstruction){
                result.add(o);
            }
            else if(o instanceof ArrayList){
                ArrayList<?> a = (ArrayList<?>) o;
                TraceInstruction pervInstruction = (TraceInstruction) rawTrace.get(i-1);
                JSONObject invokedMethodInfo = pervInstruction.getToInvokeMethodInfo();
                if(invokedMethodInfo == null){
                    throw new IllegalStateException("methodInfo json is null!!!!!!!#######");
                }
                ArrayList converted = convertNestedTracesFromArrayListToExecutionTrace(a);
                result.add(new ExecutionTrace(converted, invokedMethodInfo, true, false));
            }
            else {
                throw new IllegalStateException();
            }
        }
        return result;
    }

    private static String getSliceSig(ArrayList<TraceInstruction> slice) {
        StringBuilder sb = new StringBuilder();
        for (TraceInstruction ti: slice) {
            String inst = ti.getInstruction();
            String[] splits = ti.getInstructionSplits();
            if(inst.startsWith("invoke-") ||
                    inst.startsWith("iput") ||
                    inst.startsWith("iget") ||
                    inst.startsWith("sget") ||
                    inst.startsWith("sput")){
                String methodOrFieldSignature = splits[splits.length - 2];
                String r = splits[0] + ":" + methodOrFieldSignature;
                sb.append(ti.getContainingMethod()+">>"+r);
            }
            else {
                sb.append(ti.getContainingMethod()+">>"+splits[0]);
            }
        }
        return sb.toString();
    }

    private static ArrayList<TraceInstruction> removeNotNecessaryInstructions(ArrayList<TraceInstruction> codeFragment){
        ArrayList<TraceInstruction> res = new ArrayList<>();
        for (TraceInstruction ti: codeFragment) {
            if(ti.getInstruction().startsWith("move")){
                continue;
            }
            if(ti.getInstruction().startsWith("if")){
                continue;
            }
            res.add(ti);
        }
        return res;
    }

    private static ArrayList<TraceInstruction> removeRepeatedInstructionsInSlices(ArrayList<TraceInstruction> fragmentSignatures){
        HashSet set = new HashSet();
        ArrayList<TraceInstruction> result = new ArrayList<>();
        for (TraceInstruction t: fragmentSignatures) {
            if(!set.contains(t.getContainingMethod()+t.getInstruction())){
                set.add(t.getContainingMethod()+t.getInstruction());
                result.add(t);
            }
        }
        return result;
    }

}
