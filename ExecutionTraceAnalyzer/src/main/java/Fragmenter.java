import java.util.ArrayList;
import java.util.HashSet;

public class Fragmenter {

    static ArrayList<ArrayList<TraceInstruction>> fragmentSlice(ArrayList<TraceInstruction> slice) {

        // create a map of all def and uses for all instructions
        HashSet<String>[] regUsesPerInstruction = new HashSet[slice.size()];
        String[] regDefsPerInstruction = new String[slice.size()];
        for (int i = 0; i < slice.size(); i++) {
            String[] uses = slice.get(i).getRegisterUses();
            String[] defs = slice.get(i).getRegisterDefs();
            regUsesPerInstruction[i] = new HashSet<>();
            for(String u:uses){
                regUsesPerInstruction[i].add(u);
            }
            if(defs.length > 0) regDefsPerInstruction[i] = defs[0];
        }

        ArrayList<ArrayList<TraceInstruction>> fragments = new ArrayList<>();

        // analyze slice per instruction
        for(int i=0; i < slice.size(); i++){
            // if the instruction does not have any register use or defs, ignore it
            if(regDefsPerInstruction[i] == null && regUsesPerInstruction[i].size() == 0){
                TraceInstruction t = slice.get(i);
                // this only happens if the slice is one instruction which is an important instruction
                // in the form of invoke-static without any defs or uses,
                // otherwise such instruction is not included in an slice since it does not have any
                // data dependency
                if(t.getInstruction().startsWith("invoke-static")){
                    ArrayList<TraceInstruction> fragment = new ArrayList<>();
                    fragment.add(t);
                    fragments.add(fragment);
                }
                continue;
            }

            // for each register use in the instruction we trace it back
            for (String u: regUsesPerInstruction[i]){
                ArrayList<TraceInstruction> fragment = new ArrayList<>();
                TraceInstruction t = slice.get(i);
                fragment.add(t);
                int j = i+1;
                while (j < slice.size()){
                    // if the instruction defines a value for register
                    if(regDefsPerInstruction[j] != null && regDefsPerInstruction[j].equals(u)){
                        fragment.add(slice.get(j));
                        regDefsPerInstruction[j] = null;
                        if(slice.get(j).getInstruction().startsWith("move-result")){
                            fragment.add(slice.get(j+1));
                        }
                        else if(slice.get(j).getInstruction().startsWith("move-exception")){
                            fragment.add(slice.get(j));
                        }
                        else if(slice.get(j).getInstruction().startsWith("move")){
                            fragment.add(slice.get(j));
                            u =  slice.get(j).getRegisterUses()[0];
                            continue;
                        }
                        break;
                    }
                    // if there is a use of that register
                    if(regUsesPerInstruction[j].contains(u)){
                        fragment.add(slice.get(j));
                        regUsesPerInstruction[j].remove(u);
                    }
                    j++;
                }
                fragments.add(fragment);
            }
            regUsesPerInstruction[i].clear();
        }
        return fragments;
    }
}
