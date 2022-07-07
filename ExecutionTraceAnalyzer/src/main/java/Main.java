import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {


    public static final String inputExecutionTracesDirPath = "./executionTraces";
    public static String underAnalysisApp;

    public static void main(String[] args){
//        Config.shouldLogIntermediateFiles = true;
        File inputDir = new File(inputExecutionTracesDirPath);
        File[] apps = inputDir.listFiles();
        Arrays.sort(apps, (f1, f2) -> {
            String s1 = f1.getName();
            String s2 = f2.getName();
            return s1.compareTo(s2);
        });
        ArrayList<String> doneApps = new ArrayList<>();
        try {
            List<String> d = Files.readAllLines(new File("doneApps.txt").toPath());
            for(String s: d){
                s = s.trim();
                if(!s.isEmpty()){
                    doneApps.add(s);
                }
            }
        }
        catch (Exception e){

        }


        for(File appDir: apps){
            underAnalysisApp = appDir.getName();
            if(!underAnalysisApp.startsWith("LegitimateBankApps")) continue;

            // first we need to log intermediate traces
            System.out.println("Starting to separate nested trace from execution traces of "+appDir);
            NestedTraceSeparator.separateIntermediateExecutionTracesOfApp(appDir);

            if(doneApps.contains(underAnalysisApp)){
                continue;
            }

//          then we analyze each method to create fragments out of it
            System.out.println("Starting to fragment execution traces of "+appDir);
            ExecutionTraceAnalyzer.analyzeTracesOfApp(appDir);

            try {
                Files.write(new File("doneApps.txt").toPath(), (underAnalysisApp + "\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("=============\n");
        }
    }
}
