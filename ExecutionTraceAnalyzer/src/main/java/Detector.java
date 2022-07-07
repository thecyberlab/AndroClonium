//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.*;
//import java.util.*;
//
//
///**
// * This class is responsible for analysing execution traces.
// * It first normalizes them and second it breaks the execution traces
// * to smaller pieces called fragments
// * */
//public class Detector {
//
//    private final ImportantInstructionFinder importantInstructionFinder;
//
//    private class NestedExecutionTrace{
//        ArrayList separatedExecutionTrace;
//        JSONObject methodInfo;
//
//        public NestedExecutionTrace(ArrayList separatedExecutionTrace, JSONObject methodInfo) {
//            this.separatedExecutionTrace = separatedExecutionTrace;
//            this.methodInfo = methodInfo;
//        }
//    }
//
//
//    public Detector() {
//        HashSet<String> s_safeClasses = Utils.getStaticSafeClasses();
//        HashSet<String> s_partialSafeClasses = Utils.getStaticPartialSafeClasses();
//        HashSet<String> i_safeClasses = Utils.getInstanceSafeClasses();
//        HashSet<String> i_partialSafeClasses = Utils.getInstancePartialSafeClasses();
//        HashSet<String> immutableClassesSet = Utils.getImmutableClasses();
//        HashSet<String> pureMethodsSet = Utils.getPureMethods();
//
//        importantInstructionFinder = new ImportantInstructionFinder(s_safeClasses, s_partialSafeClasses,
//                i_safeClasses,i_partialSafeClasses, immutableClassesSet, pureMethodsSet);
//    }
//
//    public void analyzeCapturedTrace(File inputExecutionTraceFile,
//                                     JSONObject methodInfo,
//                                     Map<String, Set<ArrayList<String>>> methodToFinalFragmentsMap,
//                                     HashMap<String, HashMap<String, HashSet<Integer>>> methodToFragmentToSliceMap) throws Exception {
//
//        // we parse the execution trace logs and turn them into an ArrayList of TraceInstruction
//        // each TraceInstruction corresponds to a single line in the execution trace
//        ArrayList<TraceInstruction> traceInstructions = createTracedInstructions(inputExecutionTraceFile);
//
//        // we then create a tree structure of execution trace which is an ArrayList of
//        // instructions or another ArrayList for nested instruction of invoked methods
//        ArrayList separatedMethodCallsTraceInstructions = createMethodCallsTree(traceInstructions);
//
//        // for nested instructions of invoked methods, we also want to treat them as another
//        // execution trace. So we separate them and their method metadata json in to a NestedExecutionTrace
//        ArrayList<NestedExecutionTrace> allNestedExecutionTraces = new ArrayList<>();
//        getAllNestedExecutionTraces(separatedMethodCallsTraceInstructions, methodInfo, allNestedExecutionTraces);
//
//        String appResultsDirPath = inputExecutionTraceFile.getParentFile().getParentFile().getParent();
//        for(NestedExecutionTrace nestedExecutionTrace: allNestedExecutionTraces) {
//
//            // getting the methodTraceAnalysisFile
//            String methodFullSignature = nestedExecutionTrace.methodInfo.getString("signature");
//            String methodClassCleanName = methodFullSignature.split("->")[0].substring(1).replace("/", ".");
//            String methodCleanName = methodFullSignature.split("->")[1].replace("/", ".");
//            File methodTraceAnalysisFile = new File(new File(appResultsDirPath, methodClassCleanName), methodCleanName);
//            methodTraceAnalysisFile = Utils.checkForTooLongFileNamesAndFixIfNecessary(methodTraceAnalysisFile);
//            if(!methodTraceAnalysisFile.exists()){
//                methodTraceAnalysisFile.mkdirs();
//            }
//
//            // for each nestedExecutionTrace we create it's corresponding directory
//            // and we write the methodInfo json to file, except the wrapper execution trace
//            // which has been already written to file
//            if(nestedExecutionTrace.methodInfo != methodInfo){
//                // writing the methodInfo json if needed
//                File jsonMethodInfoFile = new File(methodTraceAnalysisFile, "methodMetaData.json");
//                if(!jsonMethodInfoFile.exists()){
//                    jsonMethodInfoFile.getParentFile().mkdirs();
//                    BufferedWriter writer = new BufferedWriter(new FileWriter(jsonMethodInfoFile));
//                    writer.write(nestedExecutionTrace.methodInfo.toString());
//                    writer.flush();
//                    writer.close();
//                }
//                // counting how many execution traces have been written so far
//                // so we don't overwrite it
//                int executionCounter = 0;
//                for(File f: methodTraceAnalysisFile.listFiles()){
//                    if(f != null && f.isFile() && f.getName().startsWith("trace_")){
//                        executionCounter++;
//                    }
//                }
//
//                File newExecutionTraceFile = new File(methodTraceAnalysisFile, "trace_" + executionCounter + ".txt");
//                LogWriter.writeTraceInstructionTreeToFile(nestedExecutionTrace.separatedExecutionTrace, newExecutionTraceFile.getPath(), false);
//                analyzeNestedCapturedTrace(newExecutionTraceFile, nestedExecutionTrace, methodToFinalFragmentsMap, methodToFragmentToSliceMap);
//            }
//            else {
//                analyzeNestedCapturedTrace(inputExecutionTraceFile, nestedExecutionTrace, methodToFinalFragmentsMap, methodToFragmentToSliceMap);
//            }
//        }
//
//    }
//
//    private static ArrayList<TraceInstruction> createTracedInstructions(File file) throws IOException {
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        ArrayList<TraceInstruction> traceInstructions = new ArrayList<>();
//
//        String line = bufferedReader.readLine();
//        // making sure first line is ok
//        if(line == null || !line.startsWith("Start of method context:")){
//            throw new IllegalStateException();
//        }
//
//        Stack<String> methodCallStack = new Stack<>();
//        String methodCallStackString = null;
//
//        // analyze each line
//        while (line != null){
//            line = line.trim();
//            if(line.startsWith("Start of method context:")){
//                String methodContext = line.substring("Start of method context:".length());
//                methodCallStack.add(methodContext);
//                String s = methodCallStack.toString();
//                methodCallStackString = s.substring(1, s.length()-1).replace(", ", ">>");
//            }
//            else if(line.startsWith("End of method context:")){
//                String methodContext = line.substring("End of method context:".length());
//                if(!methodContext.equals(methodCallStack.peek())){
//                    throw new IllegalStateException();
//                }
//                methodCallStack.pop();
//                String s = methodCallStack.toString();
//                methodCallStackString = s.substring(1, s.length()-1).replace(", ", ">>");
//            }
//            else {
//                // ignore GOTO and Monitor instructions since they do not have any interesting information to capture
//                if(!line.startsWith("goto") && !line.startsWith("monitor") && !line.startsWith("nop")){
//                    try {
//                        traceInstructions.add(new TraceInstruction(line, methodCallStackString));
//                    }
//                    catch (Exception e){
//                        throw e;
//                    }
//                }
//            }
//            line = bufferedReader.readLine();
//        }
//        return traceInstructions;
//    }
//
//    private ArrayList<Object> createMethodCallsTree(ArrayList<TraceInstruction> traceInstructions){
//        ArrayList result = new ArrayList();
//        String initialMethodCallStack = traceInstructions.get(0).getMethodCallStack();
//        for (int i = 0; i < traceInstructions.size(); i++) {
//            TraceInstruction ti = traceInstructions.get(i);
//            if(ti.getMethodCallStack().equals(initialMethodCallStack)){
//                result.add(ti);
//            }
//            else {
//                ArrayList<TraceInstruction> tmp = new ArrayList<>();
//                while (!ti.getMethodCallStack().equals(initialMethodCallStack)){
//                    tmp.add(ti);
//                    i++;
//                    if(i == traceInstructions.size()) break;
//                    ti = traceInstructions.get(i);
//                }
//                i--;
//                result.add(createMethodCallsTree(tmp));
//            }
//        }
//        return result;
//    }
//
//    public void getAllNestedExecutionTraces(ArrayList separatedMethodCallsTraceInstructions,
//                                            JSONObject methodInfo,
//                                            ArrayList<NestedExecutionTrace> result){
//
//        result.add(new NestedExecutionTrace(separatedMethodCallsTraceInstructions, methodInfo));
//        for (int i = 0; i < separatedMethodCallsTraceInstructions.size(); i++) {
//            Object o = separatedMethodCallsTraceInstructions.get(i);
//            if (o instanceof ArrayList) {
//                ArrayList a = (ArrayList) o;
//                TraceInstruction pervInstruction = (TraceInstruction) separatedMethodCallsTraceInstructions.get(i-1);
//                JSONObject invokedMethodInfo = pervInstruction.getToInvokeMethodInfo();
//                if(invokedMethodInfo == null){
//                    throw new IllegalStateException("methodInfo json is null!!!!!!!#######");
//                }
//                getAllNestedExecutionTraces(a, invokedMethodInfo, result);
//            } else if (!(o instanceof TraceInstruction)) {
//                throw new IllegalStateException();
//            }
//        }
//    }
//
//        private void analyzeNestedCapturedTrace(File executionTraceFile,
//                                            NestedExecutionTrace nestedExecutionTrace,
//                                            Map<String, Set<ArrayList<String>>> methodToFinalFragmentsMap,
//                                            HashMap<String, HashMap<String, HashSet<Integer>>> methodToFragmentToSliceMap) throws Exception{
//
//        // first we create the folder that analysis result of a execution trace will be saved in
//        File executionTraceAnalysisDir = new File(executionTraceFile.getParentFile(), executionTraceFile.getName().replace(".txt", "_analysis"));
//        if(!executionTraceAnalysisDir.exists()){ executionTraceAnalysisDir.mkdir(); }
//
//
//        ArrayList separatedMethodCallsTraceInstructions = nestedExecutionTrace.separatedExecutionTrace;
//        JSONObject methodInfo = nestedExecutionTrace.methodInfo;
//
//        // first we try to replace the primitive and String values that are generated
//        // from methods with constant inputs to const-* instructions
//        try {
//            ConstantTransformationsSimplifier.simplifyConstantTransformations(separatedMethodCallsTraceInstructions,
//                    methodInfo, new ArrayList<>(), new ArrayList<>());
//        }
//        catch (Exception e){
//        }
//
//        // we make a clone of execution trace before do any change, we will need it later for
//        // our reflection normalization
//        ArrayList origClone = (ArrayList) separatedMethodCallsTraceInstructions.clone();
//
//        // we start our reflection normalization, here we try to replace reflection calls
//        // with normal method calls. To do this we need to keep track of objects which are
//        // the arguments the normal method calll
//        HashMap<TraceInstruction, ArrayList<TraceInstruction>> trueArgsSrcInstMap = new HashMap<TraceInstruction, ArrayList<TraceInstruction>>();
//        ReflectionNormalizer.removeReflectionCalls(separatedMethodCallsTraceInstructions,
//                new ArrayList<>(),
//                methodInfo,
//                new ArrayList<>(),
//                trueArgsSrcInstMap);
//
//
//        if(Config.shouldLogIntermediateFiles) {
//            String a = executionTraceFile.getName().replace(".txt", "_analysis");
//            String path = new File(executionTraceAnalysisDir, "1_partial_reflection_normalized_" + a + ".txt").getAbsolutePath();
//            LogWriter.writeTraceInstructionTreeToFileOldFormat(separatedMethodCallsTraceInstructions, path, false,true);
//        }
//
//        // then we flatten our execution trace, meaning that we inline all invoked methods
//        // that we have their execution trace. To do this we change the register numbers
//        // in the invoked method to new numbers. In this conversion we have assumed we have
//        // infinite number of registers
//        int totalRegUse = (Integer) methodInfo.get("total registers");
//        HashMap<TraceInstruction, TraceInstruction> updatedInstructions = new HashMap<>();
//        Object[] res = InstructionTraceFlattener.flattenExecutionTraceOneLevel(separatedMethodCallsTraceInstructions, totalRegUse,updatedInstructions);
//        Integer newOffset = (Integer) res[0];
//        ArrayList rawFlattenedInstructionTrace = (ArrayList) res[1];
//        Boolean flattened = (Boolean) res[2];
//        while(flattened){
//            res = InstructionTraceFlattener.flattenExecutionTraceOneLevel(rawFlattenedInstructionTrace, newOffset, updatedInstructions);
//            newOffset = (Integer) res[0];
//            rawFlattenedInstructionTrace = (ArrayList) res[1];
//            flattened = (Boolean) res[2];
//        }
//
//        // move the flattened instruction trace into an array of ArrayList<TraceInstruction>
//        // since later methods take ArrayList<TraceInstruction> as an input
//        ArrayList<TraceInstruction> flattenedTrace= new ArrayList<>();
//        for (Object o:rawFlattenedInstructionTrace) {
//            TraceInstruction ti = (TraceInstruction) o;
//            flattenedTrace.add(ti);
//        }
//
//        if(Config.shouldLogIntermediateFiles) {
//            String a = executionTraceFile.getName().replace(".txt", "_analysis");
//            String path = new File(executionTraceAnalysisDir, "2_initial_flattened_trace_" + a + ".txt").getAbsolutePath();
//            LogWriter.writeTraceInstructionTreeToFileOldFormat(flattenedTrace, path, false, true);
//        }
//
//        // we finish the reflection normalization with putting a move-* instruction
//        // which will correct the data-dependency flow
//        ReflectionNormalizer.addDataDependencyConnectingInstructions(flattenedTrace, trueArgsSrcInstMap, updatedInstructions,origClone);
//
//        if(Config.shouldLogIntermediateFiles) {
//            String a = executionTraceFile.getName().replace(".txt", "_analysis");
//            String path = new File(executionTraceAnalysisDir, "3_final_reflection_normalization_" + a + ".txt").getAbsolutePath();
//            LogWriter.writeTraceInstructionTreeToFileOldFormat(flattenedTrace, path, false,true);
//        }
//
//        // we remove register aliasing, meaning two registers that point to same object
//        flattenedTrace = removeRegisterAliasing(flattenedTrace);
//
//        if(Config.shouldLogIntermediateFiles) {
//            String a = executionTraceFile.getName().replace(".txt", "_analysis");
//            String path = new File(executionTraceAnalysisDir, "4_final_flattened_trace_after_register_unAliasing_" + a + ".txt").getAbsolutePath();
//            LogWriter.writeTraceInstructionTreeToFileOldFormat(flattenedTrace, path, false, true);
//        }
//
//
//        // here we separate the instructions that show the net effects of execution of a method
//        // and the we use back tracing on them in our later parts of analysis
//        ArrayList<TraceInstruction> toBacktrackInstructions = new ArrayList<>();
//
//        // The first type effect a method can have is it's return type if it does not return vois
//        int lastOne = flattenedTrace.size() - 1;
//        if(!(flattenedTrace.get(lastOne).getInstruction().startsWith("return-void"))){
//            toBacktrackInstructions.add(flattenedTrace.get(lastOne));
//        }
//
//        // The second type of effect is instructions that change the state of system
//        // outside method scope, such as changing static fields or invoking methods that have
//        // global side effects such as printing to stdout, writing to a file, etc
//        for (Object o: flattenedTrace) {
//            TraceInstruction ti = (TraceInstruction) o;
//            if(importantInstructionFinder.isGlobalStateChangingMethod(ti)){
//                toBacktrackInstructions.add(ti);
//            }
//        }
//
//        // the third kind of effect is instructions that change state of objects that have been
//        // passed as argument to this method, such as changing their fields, or invoking methods
//        // that change state of those objects
//        ArrayList<TraceInstruction> r= importantInstructionFinder.getNonLocalObjectChangingInstructions(flattenedTrace);
//        toBacktrackInstructions.addAll(r);
//
//        if(Config.shouldLogIntermediateFiles){
//            int counter = 0;
//            for(TraceInstruction ti:toBacktrackInstructions){
//                String path = new File(executionTraceAnalysisDir, "important_instruction_" + counter + "_of_" + executionTraceFile.getName()).getAbsolutePath();
//                LogWriter.writeTextToFile(ti.getLogLine(), path, false);
//            }
//        }
//
//
//        int slice_counter = 0;
//        HashSet<String> sliceSigsSet = new HashSet();
//        HashSet<String> uniqueFragmentSignatures = new HashSet<>();
//        HashSet<ArrayList<String>> uniqueFragmentSet = new HashSet<>();
//        HashMap<String, HashSet<Integer>> fragmentToSliceMap = new HashMap<>();
//
//        // then for each important instruction, we perform back tracing on it
//        // based on data-dependency on registers that the instructions is using
//        // all instructions that are needed for execution of one important instruction
//        // result in one slice
//        for (TraceInstruction ti: toBacktrackInstructions) {
//            ArrayList<TraceInstruction> slice = Slicer.extractCodeSlice(ti,flattenedTrace, importantInstructionFinder);
//            String sliceSig = getSliceSig(slice);
//            // ignore if slice repeated
//            if(sliceSigsSet.contains(sliceSig)){
//                continue;
//            }
//            sliceSigsSet.add(sliceSig);
//
//            // write the slice to slices file
//            String p2 = executionTraceAnalysisDir.getAbsolutePath() + "/slices.txt";
//            LogWriter.writeTextToFile("slice:" + slice_counter + "\n", p2, true);
//            LogWriter.writeTextToFile("----\n", p2, true);
//            LogWriter.writeTraceInstructionTreeToFileOldFormat(slice, p2, true, false);
//            LogWriter.writeTextToFile("==============\n", p2, true);
//
//            // get fragments of this slice
//            ArrayList<ArrayList<TraceInstruction>> sliceFragments = Fragmenter.fragmentSlice(slice);
//            for (ArrayList<TraceInstruction> fragment:sliceFragments){
//
//                ArrayList<TraceInstruction> simpleFrag = removeRepeatedInstructionsInSlices(removeNotNecessaryInstructions(fragment));
//
//                ArrayList<String> readyFragment = InstructionNormalizer.normalizeTraceInstructions(simpleFrag);
//                StringBuilder sb = new StringBuilder();
//                for (String s:readyFragment) {
//                    sb.append(s);
//                }
//                String readyFragmentSig = sb.toString();
//
//                // new unique fragment
//                if(!uniqueFragmentSignatures.contains(readyFragmentSig)){
//                    uniqueFragmentSignatures.add(readyFragmentSig);
//                    uniqueFragmentSet.add(readyFragment);
//                    HashSet<Integer> h = new HashSet<>();
//                    h.add(slice_counter);
//                    fragmentToSliceMap.put(readyFragmentSig, h);
//                }
//                // repeated fragment
//                else {
//                    HashSet<Integer> h = fragmentToSliceMap.get(readyFragmentSig);
//                    h.add(slice_counter);
//                }
//            }
//            slice_counter ++;
//        }
//
//        methodToFinalFragmentsMap.put(methodInfo.getString("signature"), uniqueFragmentSet);
//        methodToFragmentToSliceMap.put(methodInfo.getString("signature"), fragmentToSliceMap);
//    }
//
//
//    private ArrayList<TraceInstruction> removeRegisterAliasing(ArrayList<TraceInstruction> traceInstructions){
//        HashMap<String, String> aliases = new HashMap<>();
//        ArrayList<TraceInstruction> result = new ArrayList<>();
//        for (TraceInstruction ti: traceInstructions){
//            // if the instruction is move the it is introducing a new alias
//            if(ti.getInstruction().startsWith("move ")
//            || ti.getInstruction().startsWith("move/from16 ")
//            || ti.getInstruction().startsWith("move/16 ")
//            || ti.getInstruction().startsWith("move-wide ")
//            || ti.getInstruction().startsWith("move-wide/from16 ")
//            || ti.getInstruction().startsWith("move-wide/16 ")){
//                String[] splits = ti.getInstructionSplits();
//                String r1 = splits[1];
//                String r2 = splits[2];
//                // check that extracted register names are correct
//                if(!r1.startsWith("v") || !r2.startsWith("v")) throw new IllegalStateException();
//                Integer.parseInt(r1.substring(1));
//                Integer.parseInt(r2.substring(1));
//                // check that the second register is not alias of something itself
//                String originalRegister = r2;
//                while (aliases.containsKey(originalRegister)){
//                    originalRegister = aliases.get(originalRegister);
//                }
//                // put the alias in the table
//                aliases.put(r1, originalRegister);
//                result.add(ti);
//            }
//            // otherwise :
//            else {
//                String[] registerDef = ti.getRegisterDefs();
//                String[] registerUses = ti.getRegisterUses();
//                // check if the instruction is writing a new value to a alias register so then the register is not an alias any more
//                if(registerDef.length > 0){
//                    String regDef = registerDef[0];
//                    if(aliases.containsKey(regDef)) aliases.remove(regDef);
//                }
//                // check if the instruction register uses include a register that is an alias and if so replace it with original register
//                String instructionPart = ti.getInstruction();
//                String newInstructionPart = instructionPart;
//                for (String regUse: registerUses) {
//                    if(aliases.containsKey(regUse)){
//                        String originalReg = aliases.get(regUse);
//                        newInstructionPart = newInstructionPart.replace(regUse+" ", originalReg+" ");
//                    }
//                }
//                // updating the register contents json
//                JSONObject newRegContent = null;
//                if(ti.getRegisterContentsInfo() != null) {
//                    String joOldString = ti.getRegisterContentsInfo().toString();
//                    try {
//                        newRegContent = new JSONObject(joOldString);
//                        if (!newInstructionPart.equals(instructionPart)) {
//                            String[] splits1 = instructionPart.split(" ");
//                            String[] splits2 = newInstructionPart.split(" ");
//                            // This should be always true but just in case
//                            if (splits1.length == splits2.length) {
//                                for (int i = 0; i < splits1.length; i++) {
//                                    if (splits1[i].startsWith("v") &&
//                                            splits2[i].startsWith("v") &&
//                                            !splits1[i].equals(splits2[i])) {
//
//                                        String oldKey = splits1[i];
//                                        String newKey = splits2[i];
//                                        newRegContent.put(newKey, newRegContent.get(oldKey));
//                                        newRegContent.remove(oldKey);
//
//                                    }
//                                }
//                            }
//                        }
//
//                    } catch (JSONException e) {
//                        newRegContent = null;
//                    }
//
//                }
//                try {
//                    TraceInstruction toAddTrace;
//                    if(newRegContent != null) {
//                        toAddTrace = new TraceInstruction(ti.getMethodCallStack(), newInstructionPart, ti.getToInvokeMethodInfo(), newRegContent);
//                    }
//                    else {
//                        toAddTrace = new TraceInstruction(ti.getMethodCallStack(), newInstructionPart, ti.getToInvokeMethodInfo(), ti.getRegisterContentsInfo());
//                    }
//                    result.add(toAddTrace);
//                } catch (Exception e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        }
//        return result;
//    }
//
//    private String getSliceSig(ArrayList<TraceInstruction> slice) {
//        StringBuilder sb = new StringBuilder();
//        for (TraceInstruction ti: slice) {
//            String inst = ti.getInstruction();
//            String[] splits = ti.getInstructionSplits();
//            if(inst.startsWith("invoke-") ||
//                    inst.startsWith("iput") ||
//                    inst.startsWith("iget") ||
//                    inst.startsWith("sget") ||
//                    inst.startsWith("sput")){
//                String methodOrFieldSignature = splits[splits.length - 2];
//                String r = splits[0] + ":" + methodOrFieldSignature;
//                sb.append(ti.getLastMethod()+">>"+r);
//            }
//            else {
//                sb.append(ti.getLastMethod()+">>"+splits[0]);
//            }
//        }
//        return sb.toString();
//    }
//
//    private ArrayList<TraceInstruction> removeNotNecessaryInstructions(ArrayList<TraceInstruction> codeFragment){
//        ArrayList<TraceInstruction> res = new ArrayList<>();
//        for (TraceInstruction ti: codeFragment) {
//            if(ti.getInstruction().startsWith("move")){
//                continue;
//            }
//            if(ti.getInstruction().startsWith("if")){
//                continue;
//            }
//            res.add(ti);
//        }
//        return res;
//    }
//
//    private ArrayList<TraceInstruction> removeRepeatedInstructionsInSlices(ArrayList<TraceInstruction> fragmentSignatures){
//        HashSet set = new HashSet();
//        ArrayList<TraceInstruction> result = new ArrayList<>();
//        for (TraceInstruction t: fragmentSignatures) {
//            if(!set.contains(t.getLastMethod()+t.getInstruction())){
//                set.add(t.getLastMethod()+t.getInstruction());
//                result.add(t);
//            }
//        }
//        return result;
//    }
//
//
//}
