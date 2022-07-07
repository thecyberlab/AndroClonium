import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegisterAliasingRemover {

    static ArrayList<TraceInstruction> removeRegisterAliasing(ArrayList<TraceInstruction> traceInstructions) {
        HashMap<String, String> aliases = new HashMap<>();
        ArrayList<TraceInstruction> result = new ArrayList<>();
        for (int j = 0; j < traceInstructions.size(); j++) {
            TraceInstruction ti = traceInstructions.get(j);
            // if the instruction is move then it is introducing a new alias
            if (ti.getInstruction().startsWith("move ")
                    || ti.getInstruction().startsWith("move/from16 ")
                    || ti.getInstruction().startsWith("move/16 ")
                    || ti.getInstruction().startsWith("move-wide ")
                    || ti.getInstruction().startsWith("move-wide/from16 ")
                    || ti.getInstruction().startsWith("move-wide/16 ")
                    || ti.getInstruction().startsWith("move-object ")
                    || ti.getInstruction().startsWith("move-object/from16 ")
                    || ti.getInstruction().startsWith("move-object/16 ")) {
                String[] splits = ti.getInstructionSplits();
                String moveDestReg = splits[1];
                String moveSrcReg = splits[2];

                // check that extracted register names are correct
                if (!moveDestReg.startsWith("v") || !moveSrcReg.startsWith("v")) throw new IllegalStateException();
                Integer.parseInt(moveDestReg.substring(1));
                Integer.parseInt(moveSrcReg.substring(1));


                // check that the second register is not alias of something itself
                String dealiasedMoveSrcReg = moveSrcReg;
                while (aliases.containsKey(dealiasedMoveSrcReg)) {
                    dealiasedMoveSrcReg = aliases.get(dealiasedMoveSrcReg);
                }


                // put the alias in the table
                // this check is required since if a method returns a value which was given to it from method arguments
                if (!moveDestReg.equals(dealiasedMoveSrcReg)) {
                    aliases.put(moveDestReg, dealiasedMoveSrcReg);
                }

                // now that dest register points to a new object
                // it is cannot be alias of someone else
                for(String k: new ArrayList<>(aliases.keySet())){
                    String val = aliases.get(k);
                    if(val.equals(moveDestReg)){
                        aliases.remove(k);
                    }
                }

                result.add(ti);
            }
            // otherwise :
            else {
                String[] registerDef = ti.getRegisterDefs();
                String[] registerUses = ti.getRegisterUses();

                String oldInstruction = ti.getInstruction();
                String newInstructionPart = getNewInstructionAfterAliasNormalization(oldInstruction, registerUses, registerDef, aliases);

                // updating the register contents json
                JSONObject newRegContent = null;
                if (ti.getRegisterContentsInfo() != null) {
                    try {
                        newRegContent = new JSONObject(ti.getRegisterContentsInfo().toString());
                        // if a register has been changed after register number unaliasing
                        if (!newInstructionPart.equals(oldInstruction)) {
                            String[] splits1 = oldInstruction.split(" ");
                            String[] splits2 = newInstructionPart.split(" ");
                            // This should be always true but just in case
                            if (splits1.length == splits2.length) {
                                // we also have to keep track of registers that we have already
                                // updated in the newRegContent json since a register can be repeated
                                // in an instruction
                                HashSet<String> doneRegs = new HashSet<>();
                                for (int i = 0; i < splits1.length; i++) {
                                    // if splits are both registers and a register number has changed
                                    if (splits1[i].startsWith("v") &&
                                            splits2[i].startsWith("v") &&
                                            !splits1[i].equals(splits2[i])) {

                                        String oldKey = splits1[i];
                                        if(doneRegs.contains(oldKey)) continue;
                                        String newKey = splits2[i];
                                        newRegContent.put(newKey, newRegContent.get(oldKey));
                                        newRegContent.remove(oldKey);
                                        doneRegs.add(oldKey);

                                    }
                                }
                            }
                            else {
                                // in one case it is normal for the split length be different
                                // and that is /2addr instructions, except them lets throw error
                                if(!splits1[0].endsWith("2addr")) {
                                    throw new IllegalStateException();
                                }
                            }
                        }

                    }
                    catch (JSONException e) {
                        throw new IllegalStateException(e);
                    }

                }

                // adding the new modified instruction
                TraceInstruction toAddTrace;
                if (newRegContent != null) {
                    toAddTrace = new TraceInstruction(ti.getMethodCallStack(), newInstructionPart, ti.getToInvokeMethodInfo(), newRegContent);
                } else {
                    toAddTrace = new TraceInstruction(ti.getMethodCallStack(), newInstructionPart, ti.getToInvokeMethodInfo(), ti.getRegisterContentsInfo());
                }
                result.add(toAddTrace);
            }
        }
        return result;
    }


    private static String getNewInstructionAfterAliasNormalization(String oldInstruction,
                                                                   String[] registerUses,
                                                                   String[] registerDef,
                                                                   HashMap<String, String> aliases) {

        String[] oldInstructionSplits = oldInstruction.split(" ");

        //Instructions which don't use any registers
        if (oldInstruction.startsWith("nop ")
                || oldInstruction.startsWith("return-void ")
                || oldInstruction.startsWith("goto")) {
            return oldInstruction;
        }
        // instructions that only use one register and write a new value to that register
        else if (oldInstruction.startsWith("move-result ")
                || oldInstruction.startsWith("move-result-wide ")
                || oldInstruction.startsWith("move-result-object ")
                || oldInstruction.startsWith("move-exception ")
                || oldInstruction.startsWith("const ")
                || oldInstruction.startsWith("const/")
                || oldInstruction.startsWith("const-")
                || oldInstruction.startsWith("new-instance ")
                || oldInstruction.startsWith("sget")) {
            aliases.remove(registerDef[0]);
            for(String k: new ArrayList<>(aliases.keySet())){
                if(aliases.get(k).equals(registerDef[0])){
                    aliases.remove(k);
                }
            }
            return oldInstruction;
        }


        // instructions that only use one register and read from it
        else if (oldInstruction.startsWith("return ")
                || oldInstruction.startsWith("return-wide ")
                || oldInstruction.startsWith("return-object ")
                || oldInstruction.startsWith("monitor-")
                || oldInstruction.startsWith("check-cast ")
                || oldInstruction.startsWith("fill-array-data ")
                || oldInstruction.startsWith("throw ")
                || oldInstruction.startsWith("packed-switch ")
                || oldInstruction.startsWith("sparse-switch ")
                || (oldInstruction.startsWith("if-") && oldInstructionSplits[0].endsWith("z"))
                || oldInstruction.startsWith("sput")) {

            String regUse = registerUses[0];
            if (aliases.containsKey(regUse)) {
                String originalReg = aliases.get(regUse);
                return oldInstruction.replace(regUse + " ", originalReg + " ");
            } else {
                return oldInstruction;
            }
        }

        // instructions that only read from multiple registers
        else if (oldInstruction.startsWith("filled-new-array ")
                || oldInstruction.startsWith("filled-new-array/range ")
                || oldInstruction.startsWith("iput")
                || oldInstruction.startsWith("aput")
                || oldInstruction.startsWith("if")
                || oldInstruction.startsWith("invoke-")) {
            String newInstruction = oldInstruction;
            for (String regUse : registerUses) {
                if (aliases.containsKey(regUse)) {
                    String originalReg = aliases.get(regUse);
                    newInstruction = newInstruction.replace(regUse + " ", originalReg + " ");
                }
            }
            return newInstruction;
        }

        // instructions that read from a register and writes to the same register
        // this is a special case since we need to treat the write and read part differently
        // so we convert this binop/2addr to normal binop
        else if(oldInstructionSplits[0].endsWith("2addr")){

            // create a new instructions with correct reg numbers for register reads
            String newInstruction = oldInstructionSplits[0].replace("/2addr", "")
                    +" " + oldInstructionSplits[1] + " ";
            for (int i = 1; i < 3 ; i++) {
                String srcReg = oldInstructionSplits[i];
                if(aliases.containsKey(srcReg)){
                    newInstruction = newInstruction + aliases.get(srcReg).trim() + " ";
                }
                else {
                    newInstruction = newInstruction + srcReg + " ";
                }
            }
            newInstruction = newInstruction + oldInstructionSplits[3];

            // remove the written register from aliases map
            aliases.remove(registerDef[0]);
            for(String k: new ArrayList<>(aliases.keySet())){
                if(aliases.get(k).equals(registerDef[0])){
                    aliases.remove(k);
                }
            }
            return newInstruction;
        }
        // other instructions that do read and write to registers
        else if (oldInstruction.startsWith("instance-of ")
                || oldInstruction.startsWith("new-array ")
                || oldInstruction.startsWith("array-length ")
                || oldInstruction.startsWith("cmp")
                || oldInstruction.startsWith("iget")
                || oldInstruction.startsWith("aget")
                || oldInstruction.startsWith("neg-")
                || oldInstruction.startsWith("not-")
                || oldInstruction.startsWith("int-to-")
                || oldInstruction.startsWith("long-to-")
                || oldInstruction.startsWith("float-to")
                || oldInstruction.startsWith("double-to-")
                || oldInstruction.startsWith("add-")
                || oldInstruction.startsWith("sub-")
                || oldInstruction.startsWith("mul-")
                || oldInstruction.startsWith("div-")
                || oldInstruction.startsWith("rem-")
                || oldInstruction.startsWith("and-")
                || oldInstruction.startsWith("or-")
                || oldInstruction.startsWith("xor-")
                || oldInstruction.startsWith("shl-")
                || oldInstruction.startsWith("shr-")
                || oldInstruction.startsWith("ushr-")
                || oldInstruction.startsWith("rsub-int")) {

            // the mnemonic and first register which is destination register are unchanged
            String newInstruction = oldInstructionSplits[0] + " " + oldInstructionSplits[1] + " ";
            // for source registers we may need to update their register numbers
            for (int i = 2; i < oldInstructionSplits.length; i++) {
                String split = oldInstructionSplits[i];
                if(split.startsWith("v")){
                    if (aliases.containsKey(split)) {
                        String originalReg = aliases.get(split);
                        newInstruction = newInstruction + originalReg.trim() + " ";
                    }
                    else {
                        newInstruction = newInstruction + split + " ";
                    }
                }
                else {
                    newInstruction = newInstruction + split + " ";
                }
            }

            // remove the written register from aliases map
            aliases.remove(registerDef[0]);
            for(String k: new ArrayList<>(aliases.keySet())){
                if(aliases.get(k).equals(registerDef[0])){
                    aliases.remove(k);
                }
            }

            return newInstruction.trim();
        }
        else {
            throw new IllegalStateException(oldInstruction);
        }


        //                // check if the instruction register-uses include a register that is an alias and if so replace it with original register
//                String instructionPart = ti.getInstruction();
//                String newInstructionPart = instructionPart;
//                for (String regUse : registerUses) {
//                    if (aliases.containsKey(regUse)) {
//                        String originalReg = aliases.get(regUse);
//                        newInstructionPart = newInstructionPart.replace(regUse + " ", originalReg + " ");
//                    }
//                }
//
//
//                // check if the instruction is writing a new value to an alias register so then the register is not an alias anymore
//                if (registerDef.length > 0) {
//                    String regDef = registerDef[0];
//                    if (aliases.containsKey(regDef)) aliases.remove(regDef);
//                }
    }
}
