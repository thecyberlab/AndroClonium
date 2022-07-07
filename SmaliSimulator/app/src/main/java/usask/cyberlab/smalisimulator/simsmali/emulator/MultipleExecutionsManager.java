package usask.cyberlab.smalisimulator.simsmali.emulator;

import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.InstructionPayloadPlaceholder;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class MultipleExecutionsManager {

    private static final int maxTries = 10;

    private static class MethodExecutionBuilder{
        private final SmaliMethod sm;
        private final ClazzLoader loader;
        private final Object[] args;
        private Objekt self;
        private AmbiguousValue ambSelf;

        public MethodExecutionBuilder(SmaliMethod sm, ClazzLoader loader, Object[] args) {
            this.sm = sm;
            this.loader = loader;
            this.args = args;
        }

        public MethodExecutionBuilder(SmaliMethod sm, ClazzLoader loader, Object[] args, Objekt self) {
            this.sm = sm;
            this.loader = loader;
            this.args = args;
            this.self = self;
        }

        public MethodExecutionBuilder(SmaliMethod sm, ClazzLoader loader, Object[] args, AmbiguousValue ambSelf) {
            this.sm = sm;
            this.loader = loader;
            this.args = args;
            this.ambSelf = ambSelf;
        }

        public MethodExecution getNewPreparedMethodExecution(){
            MethodExecution methodExecution;
            if(self != null){
                methodExecution = new MethodExecution(sm, loader, self);
            }
            else if(ambSelf != null){
                methodExecution = new MethodExecution(sm, loader, ambSelf);
            }
            else {
                methodExecution = new MethodExecution(sm, loader);
            }
            SimulationUtils.setMethodExecutionWrappedArguments(methodExecution, args);
            return methodExecution;
        }
    }

    public static void getPossibleExecutions(SmaliMethod sm, ClazzLoader loader, Object[] args, File outputDir){
        getPossibleExecutions(new MethodExecutionBuilder(sm, loader, args), outputDir);
    }

    public static void getPossibleExecutions(SmaliMethod sm, ClazzLoader loader, Object[] args, Objekt self, File outputDir){
        getPossibleExecutions(new MethodExecutionBuilder(sm, loader, args, self), outputDir);
    }

    public static void getPossibleExecutions(SmaliMethod sm, ClazzLoader loader, Object[] args, AmbiguousValue self, File outputDir){
        getPossibleExecutions(new MethodExecutionBuilder(sm, loader, args, self), outputDir);
    }


    private static void getPossibleExecutions(MethodExecutionBuilder methodExecutionBuilder, File outputDir){
        SmaliMethod sm = methodExecutionBuilder.sm;
        if(!sm.hasImplementation()) return;
        ArrayList<SmaliInstruction> methodInstructionList = sm.getInstructionList();
        HashMap<SmaliMethod, boolean[]> partiallyTraversedMethods = new HashMap<>();
        HashSet<SmaliMethod> fullyTraversedMethods = new HashSet<>();

        partiallyTraversedMethods.put(sm, new boolean[methodInstructionList.size()]);
        // since instruction list can also contain non-actual instructions like InstructionPayloadPlaceholder
        // which they will never get covered we have to set them to true now so we wouldn't
        // think the coverage is not full
        for (int i = 0; i < methodInstructionList.size(); i++) {
            SmaliInstruction instruction = methodInstructionList.get(i);
            if(instruction instanceof InstructionPayloadPlaceholder){
                partiallyTraversedMethods.get(sm)[i] = true;
            }
        }

        for (int i = 0; i < maxTries; i++) {
            try {
                runOnePath(methodExecutionBuilder, sm, i, outputDir, partiallyTraversedMethods, fullyTraversedMethods);
            } catch (BreakFromLoopException e) {
                break;
            }
        }

        Utils.writeStringToTimeLogFile(sm, "---------------------------------");
    }


    private static void runOnePath(MethodExecutionBuilder methodExecutionBuilder,
                                   SmaliMethod sm,
                                   int i,
                                   File outputDir,
                                   HashMap<SmaliMethod, boolean[]> partiallyTraversedMethods,
                                   HashSet<SmaliMethod> fullyTraversedMethods) throws BreakFromLoopException {
        MethodExecution methodExecution = methodExecutionBuilder.getNewPreparedMethodExecution();
        Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+":MethodExecution created.");
        try {
            methodExecution.execute();
        }
        catch (Throwable e){
            Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+":MethodExecution finished execution.");
            Utils.writeExecutionTraceOfMethodExecutionEndedWithError(sm, methodExecution);
            throw e;
        }

        Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+":MethodExecution finished execution.");
        String executionLogs = methodExecution.getExecutionTrace().getExecutionLogs();
        Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+"Finished getting the executionTrace string");
        SimulationResult sr = new SimulationResult(methodExecution.getExecutionResult(), executionLogs);
        try {
            writeExecutionResults(sm, sr, i, outputDir);
        } catch (IOException e) {
            throw new SmaliSimulationException(e);
        }
        updateTraversedMethods(partiallyTraversedMethods,fullyTraversedMethods,
                methodExecution.getExecutionTrace());
        Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+":TraversedMethods updated.");
        if(partiallyTraversedMethods.isEmpty()) throw new BreakFromLoopException();
        methodExecutionBuilder.loader.resetStaticFieldsToInitialValues();
        Utils.writeTimeStampToTimeLogFile(sm,"execution_"+i+"Finished reseting static fields for next run ");
    }

    private static class BreakFromLoopException extends Exception{

    }

    private static void updateTraversedMethods(HashMap<SmaliMethod,boolean[]> partiallyTraversedMethods,
                                               HashSet<SmaliMethod> fullyTraversedMethods,
                                               ExecutionTrace executionTrace) {

        SmaliMethod executedSmaliMethod = executionTrace.getSmaliMethod();
        if(partiallyTraversedMethods.containsKey(executedSmaliMethod)){
            boolean[] seenInstructions = partiallyTraversedMethods.get(executedSmaliMethod);

            for (Object o: executionTrace.getInstructionsOrNestedTraces()){
                if(o instanceof SmaliInstruction){
                    SmaliInstruction smaliInstruction = (SmaliInstruction) o;
                    seenInstructions[smaliInstruction.getInstructionPositionNumber()] = true;
                }
            }

            if(isAllTrue(seenInstructions)){
                fullyTraversedMethods.add(executedSmaliMethod);
                partiallyTraversedMethods.remove(executedSmaliMethod);
            }

        }
        else if(!fullyTraversedMethods.contains(executedSmaliMethod)){
            boolean[] seenInstructions = new boolean[executedSmaliMethod.getInstructionList().size()];
            // for placeholders we set them to true
            for(int i=0; i<executedSmaliMethod.getInstructionList().size() ;i++){
                if(executedSmaliMethod.getInstructionList().get(i) instanceof InstructionPayloadPlaceholder){
                    seenInstructions[i] = true;
                }
            }

            for (Object o: executionTrace.getInstructionsOrNestedTraces()){
                if(o instanceof SmaliInstruction){
                    SmaliInstruction smaliInstruction = (SmaliInstruction) o;
                    seenInstructions[smaliInstruction.getInstructionPositionNumber()] = true;
                }
            }
            partiallyTraversedMethods.put(executedSmaliMethod, seenInstructions);
        }



        for (Object o: executionTrace.getInstructionsOrNestedTraces()){
            if(o instanceof ExecutionTrace){
                updateTraversedMethods(partiallyTraversedMethods, fullyTraversedMethods, (ExecutionTrace) o);
            }
        }
    }


    public static boolean isAllTrue(boolean[] booleans){
        for (boolean b: booleans) {
            if(!b) return false;
        }
        return true;
    }

    private static void writeExecutionResults(SmaliMethod smaliMethod,
                                              SimulationResult simulationResult,
                                              int executionTryNumber,
                                              File outputDir) throws IOException {

        Utils.writeTimeStampToTimeLogFile(smaliMethod, "started writeExecutionResults function");

        // creating the folder which we write the results of a analyzed method
        String methodClassName = smaliMethod.getContainingClass().getClassPath().replace("/", ".").substring(1);
        String methodSig = smaliMethod.getClassMethodSignature().split("->")[1].replace("/", ".");

        File methodTraceAnalysisFile = Utils.checkForTooLongFileNamesAndFixIfNecessary(new File(new File(outputDir, methodClassName), methodSig));
        if (!methodTraceAnalysisFile.exists()) {
            methodTraceAnalysisFile.mkdirs();
        }

        // writing the method meta data json
        File methodMetaDataFile = new File(methodTraceAnalysisFile, "methodMetaData.json");
        BufferedWriter methodInfoWriter = new BufferedWriter(new FileWriter(methodMetaDataFile));

//        JSONObject methodInfoJson = new JSONObject();
//        try {
//            methodInfoJson.put("signature", smaliMethod.getClassMethodSignature());
//            methodInfoJson.put("total registers", smaliMethod.getTotalNumberOfRegistersUsed());
//            methodInfoJson.put("local registers", smaliMethod.getNumberOfLocalRegisters());
////            methodInfoJson.put("first passed argument register", smaliMethod.getFirstPassedArgumentRegisterNumber());
////            methodInfoJson.put("isStatic", smaliMethod.isStatic());
//            String modifiersString = "";
//            if(smaliMethod.isStatic()){
//                modifiersString = "static ";
//            }
//            modifiersString = modifiersString + smaliMethod.getAccessModifierString();
//            methodInfoJson.put("modifiers", modifiersString);
//        }
//        // This should never happen
//        catch (JSONException e) {
//            throw new IllegalStateException(e);
//        }
//        methodInfoWriter.write(methodInfoJson.toString());

        methodInfoWriter.write(SimulationUtils.getInvokedMethodInfoJsonStr(smaliMethod));
        methodInfoWriter.flush();
        methodInfoWriter.close();

        // counting how many execution traces have been written so far
        // so we don't overwrite it
        int previouslyWrittenExecutionCounter = 0;
        for (File f : methodTraceAnalysisFile.listFiles()) {
            if (f != null && f.isFile() && f.getName().startsWith("trace_")) {
                previouslyWrittenExecutionCounter++;
            }
        }

        // writing the result execution in the MethodExecutionResult
        File executionResultFile = new File(methodTraceAnalysisFile, "exeResult_" + previouslyWrittenExecutionCounter + ".txt");
        BufferedWriter exeResultWriter = new BufferedWriter(new FileWriter(executionResultFile));
        MethodExecutionResult mr = simulationResult.getExecutionResult();
        exeResultWriter.write(mr.getType().toString() + "\n------------------\n");
        if (mr.getType() == ResultType.VOID) {
            exeResultWriter.write("void\n");
        } else if (mr.getType() == ResultType.EXCEPTION) {
            if (mr.getResult() instanceof Objekt) {
                Objekt o = (Objekt) mr.getResult();
                Throwable e = (Throwable) o.getMirroringObject();
                exeResultWriter.write(Utils.getStackTraceAsString(e));
            } else {
                AmbiguousValue av = (AmbiguousValue) mr.getResult();
                exeResultWriter.write(av.toString() + "\n");
            }
        } else {
            if (mr.getResult() != null) {
                exeResultWriter.write(mr.getResult().toString());
            } else {
                exeResultWriter.write("null");
            }
        }
        exeResultWriter.flush();
        exeResultWriter.close();

        // writing the execution trace
        File traceAnalysisFile = new File(methodTraceAnalysisFile, "trace_" + previouslyWrittenExecutionCounter + ".txt");
        BufferedWriter executionTraceWriter = new BufferedWriter(new FileWriter(traceAnalysisFile));
        String executionTrace = simulationResult.getExecutionTrace();
        executionTraceWriter.write(executionTrace);
        executionTraceWriter.flush();
        executionTraceWriter.close();

        Utils.writeTimeStampToTimeLogFile(smaliMethod, "finished writing execution trace " + executionTryNumber + " to file");
    }

}
