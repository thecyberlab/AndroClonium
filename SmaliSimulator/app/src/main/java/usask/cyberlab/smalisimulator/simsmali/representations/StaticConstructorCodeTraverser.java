package usask.cyberlab.smalisimulator.simsmali.representations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.BranchInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.GotoInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.IfTestInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.IfTestZInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InstructionPayloadPlaceholder;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeDirectInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SwitchInstruction;

public class StaticConstructorCodeTraverser {
    static class StaticExecution{
        final ArrayList<SmaliInstruction> executionTrace;
        private final ArrayList<SmaliInstruction> traversedInstructions;
        int instructionPointer = 0;

        public StaticExecution(){
            executionTrace = new ArrayList<>();
            traversedInstructions = new ArrayList<>();
        }

        private StaticExecution(int pos, ArrayList<SmaliInstruction> trace, ArrayList<SmaliInstruction> traversed){
            this.instructionPointer = pos;
            this.executionTrace = trace;
            this.traversedInstructions = traversed;
        }

        public StaticExecution clone(){
            return new StaticExecution(instructionPointer,
                    (ArrayList<SmaliInstruction>) executionTrace.clone(),
                    (ArrayList<SmaliInstruction>) traversedInstructions.clone());
        }

        public void addInstruction(SmaliInstruction instruction){
            executionTrace.add(instruction);
            if(!traversedInstructions.contains(instruction)) {
                traversedInstructions.add(instruction);
            }
        }

        public int getTraversedInstructionsSize(){
            return traversedInstructions.size();
        }

        public void incrementInstructionPointer(){
            instructionPointer = instructionPointer + 1;
        }
    }

    public static List<StaticExecution> getPossiblePaths(SmaliMethod sm){
        // check given SmaliMethod is for a constructor
        if(!sm.getClassMethodSignature().contains("<init>(")) {
            throw new SmaliSimulationException();
        }
        ArrayList<SmaliInstruction> methodInstructions = sm.getInstructionList();
        ArrayList<StaticExecution> finishedPaths = new ArrayList<>();
        ArrayList<StaticExecution> unfinishedPaths = new ArrayList<>();
        unfinishedPaths.add(new StaticExecution());
        while (!unfinishedPaths.isEmpty()){
            StaticExecution currentExecution = unfinishedPaths.remove(0);
            while (true){
                SmaliInstruction instruction = methodInstructions.get(currentExecution.instructionPointer);
                if(instruction instanceof InstructionPayloadPlaceholder){
                    // just ignore the place holders
                    currentExecution.incrementInstructionPointer();
                }
                else if(instruction instanceof GotoInstruction){
                    GotoInstruction gotoInstruction = (GotoInstruction) instruction;
                    currentExecution.addInstruction(gotoInstruction);
                    currentExecution.instructionPointer = gotoInstruction.getTargetLocation();
                }
                else if(instruction instanceof IfTestInstruction ||
                        instruction instanceof IfTestZInstruction ){

                    StaticExecution otherPath = currentExecution.clone();
                    int initialTraversedInstructionsSize = currentExecution.getTraversedInstructionsSize();
                    // update currentExecution
                    currentExecution.incrementInstructionPointer();
                    currentExecution.addInstruction(instruction);
                    // check if currentExecution is in a loop
                    if(currentExecution.getTraversedInstructionsSize() == initialTraversedInstructionsSize){
                        // current path is in loop, lets discard it and take other path
                        otherPath.addInstruction(instruction);
                        otherPath.instructionPointer = ((BranchInstruction) instruction).getTargetLocation();
                        currentExecution = otherPath;
                    }
                    // current branch ok
                    else {
                        // lets check if other branch will not create loop
                        otherPath.addInstruction(instruction);
                        otherPath.instructionPointer = ((BranchInstruction) instruction).getTargetLocation();
                        if(otherPath.getTraversedInstructionsSize() != initialTraversedInstructionsSize){
                            boolean shouldAdd = true;
                            for(StaticExecution execution: finishedPaths) {
                                HashSet<SmaliInstruction> h1 = new HashSet<>(execution.traversedInstructions);
                                HashSet<SmaliInstruction> h2 = new HashSet<>(otherPath.traversedInstructions);
                                if(h1.containsAll(h2)){
                                    shouldAdd = false;
                                    break;
                                }
                            }
                            if(shouldAdd) {
                                unfinishedPaths.add(otherPath);
                            }
                        }
                    }
                }
                else if(instruction instanceof SwitchInstruction){
                    SwitchInstruction switchInstruction = (SwitchInstruction) instruction;
                    SwitchInstruction.SwitchElement[] switchElements = switchInstruction.getSwitchElements();
                    for(SwitchInstruction.SwitchElement se: switchElements){
                        StaticExecution otherExecution = currentExecution.clone();
                        otherExecution.instructionPointer = se.getTargetLocationIndex();
                        otherExecution.addInstruction(instruction);
                        unfinishedPaths.add(otherExecution);
                    }
                    currentExecution.addInstruction(instruction);
                    currentExecution.incrementInstructionPointer();
                }
                else {
                    currentExecution.addInstruction(instruction);
                    int newInstructionPointer = currentExecution.instructionPointer + 1;
                    currentExecution.incrementInstructionPointer();


                    if(instruction instanceof InvokeDirectInstruction){
                        String selfClassPath = sm.getContainingClass().getClassPath();
                        String parentClassPath = sm.getContainingClass().getParentClassPath();
                        InvokeDirectInstruction invokeDirectInstruction = (InvokeDirectInstruction) instruction;
                        if(invokeDirectInstruction.getMethodOnlySignature().startsWith("<init>(")
                                && (invokeDirectInstruction.getClassPath().equals(parentClassPath)
                                    || invokeDirectInstruction.getClassPath().equals(selfClassPath))
                                && SmaliMethod.isConstructorCallOnSelfObject(invokeDirectInstruction, sm, currentExecution.executionTrace)){
                            finishedPaths.add(currentExecution);
                            break;
                        }
                    }

                    if(newInstructionPointer >= methodInstructions.size()){
                        finishedPaths.add(currentExecution);
                        break;
                    }
                }
            }
        }
        return finishedPaths;
    }
}
