import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws Exception{
        File inputDir = new File(Main.inputExecutionTracesDirPath);
        File[] apps = inputDir.listFiles();
        Arrays.sort(apps, (f1, f2) -> {
            String s1 = f1.getName();
            String s2 = f2.getName();
            return s1.compareTo(s2);
        });

        for(File appDir: apps){
            for(File appClassFile: appDir.listFiles()){
                for(File appMethodFile: appClassFile.listFiles()){
                    for(File trace: appMethodFile.listFiles()){
                        if(trace.isFile() && trace.getName().startsWith("trace_")){
                            List<String> lines = Files.readAllLines(trace.toPath());
                            boolean isChanged = false;
                            for (int i = 0; i < lines.size(); i++) {
                                String line = lines.get(i);
                                if (line.split(" ")[0].endsWith("/2addr")) {
                                    String instruction = line.split("\\|")[0];
                                    String registerContentInfo = line.split("\\|")[1];
                                    JSONObject jo = new JSONObject(registerContentInfo);
                                    String[] instructionSplits = instruction.split(" ");
                                    Set<String> keys = jo.keySet();
                                    if(keys.size() != 2){
                                        throw new IllegalStateException();
                                    }
                                    keys.remove(instructionSplits[1]);
                                    if(keys.size() != 1){
                                        throw new IllegalStateException();
                                    }
                                    String otherReg = (String) keys.toArray()[0];
                                    String newInstruction = instructionSplits[0] + " " + instructionSplits[1] + " " + otherReg + " " + instructionSplits[3];
                                    String newLine = newInstruction+"|"+registerContentInfo;
                                    lines.set(i, newLine);
                                    isChanged = true;
                                }
                            }
                            if(isChanged) {
                                Files.write(trace.toPath(), lines);
                                System.out.println("finished fixing " + trace.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
    }
}
