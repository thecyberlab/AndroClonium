package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.iface.instruction.Instruction;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;

public class SmaliInstructionFactory {

    public static SmaliInstruction buildSmaliInstruction(Instruction instructionDef, int instructionPositionNumber){
        String opCodeName = instructionDef.getOpcode().name;
        if(opCodeName.equals("nop")){
            return new NopInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.contains("const")){
            return new ConstInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.contains("return")){
            return new ReturnInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.contains("move")){
            return new MoveInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("goto")){
            return new GotoInstruction(instructionDef, instructionPositionNumber);
        }
        // IfTest Instruction -----------------------------
        else if(opCodeName.startsWith("if-") && opCodeName.endsWith("z")){
            return new IfTestZInstruction(instructionDef, instructionPositionNumber);
        }
        // IfTestZ Instruction -----------------------------
        else if(opCodeName.startsWith("if-")){
            return new IfTestInstruction(instructionDef, instructionPositionNumber);
        }
        // instance-of instuction -----------------------------
        else if(opCodeName.equals("instance-of")){
            return new InstanceOfInstruction(instructionDef, instructionPositionNumber);
        }
        // UnaryOperation Instruction ---------------------------
        else if(opCodeName.startsWith("int-")
                || opCodeName.startsWith("float-")
                || opCodeName.startsWith("long-")
                || opCodeName.startsWith("double-")
                || opCodeName.startsWith("neg-")
                || opCodeName.startsWith("not-")){
            return new UnaryOperationInstruction(instructionDef, instructionPositionNumber);
        }
        // BinaryOperation Instruction ---------------------------
        else if(opCodeName.startsWith("add-")
        || opCodeName.startsWith("sub-")
        || opCodeName.startsWith("mul-")
        || opCodeName.startsWith("div-")
        || opCodeName.startsWith("rem-")
        || opCodeName.startsWith("and-")
        || opCodeName.startsWith("or-")
        || opCodeName.startsWith("xor-")
        || opCodeName.startsWith("shl-")
        || opCodeName.startsWith("shr-")
        || opCodeName.startsWith("ushr-")
        || opCodeName.startsWith("rsub-")){
            return new BinaryOperationInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("new-array")){
            return new NewArrayInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.contains("filled-new-array")){
            return new FilledNewArrayInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("new-instance")){
            return new NewInstanceInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("fill-array-data")){
            return new FillArrayDataInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("check-cast")){
            return new CheckCastInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("array-length")){
            return new ArrayLengthInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("aget") || opCodeName.startsWith("aput")){
            return new ArrayOperationInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("sget") || opCodeName.startsWith("sput")){
            return new StaticOperationInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("iget") || opCodeName.startsWith("iput")){
            return new InstanceOperationInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("cmp")){
            return new CmpOperationInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("packed-switch") || opCodeName.equals("sparse-switch")){
            return new SwitchInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("throw")){
            return new ThrowInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("invoke-") && opCodeName.contains("polymorphic")){
            throw new SmaliSimulationException(new UnsupportedOperationException("invoke-polymorphic not implemented!"));
        }
        else if(opCodeName.startsWith("invoke-static")){
            return new InvokeStaticInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("invoke-direct")){
            return new InvokeDirectInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("invoke-super")){
            return new InvokeSuperInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("invoke-virtual")){
            return new InvokeVirtualOrInterfaceInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.startsWith("invoke-interface")){
            return new InvokeVirtualOrInterfaceInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("monitor-enter")){
            return new MonitorEnterInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("monitor-exit")){
            return new MonitorExitInstruction(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("array-payload")){
            return new InstructionPayloadPlaceholder(instructionDef, instructionPositionNumber);
        }
        else if(opCodeName.equals("sparse-switch-payload") || opCodeName.equals("packed-switch-payload")){
            return new InstructionPayloadPlaceholder(instructionDef, instructionPositionNumber);
        }
        else {
            throw new SmaliSimulationException(new UnsupportedOperationException(opCodeName));
        }
    }
}
