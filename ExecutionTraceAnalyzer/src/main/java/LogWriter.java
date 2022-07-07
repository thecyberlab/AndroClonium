import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogWriter {

    public static void writeTraceInstructionTreeToStringBuilder(ArrayList arrayList, StringBuilder stringBuilder, boolean useRawLine) {
        TraceInstruction firstInstruction = (TraceInstruction) arrayList.get(0);
        String containingMethod = firstInstruction.getContainingMethod();
        if(useRawLine) {
            stringBuilder.append("Start of method context:").append(containingMethod).append("\n");
        }
        for (Object o: arrayList) {
            if(o instanceof TraceInstruction){
                TraceInstruction t = (TraceInstruction) o;
                if(useRawLine) {
                    stringBuilder.append(t.getRawLine()).append("\n");
                }
                else {
                    stringBuilder.append(t.getLogLine()).append("\n");
                }
            }
            else if(o instanceof ArrayList){
                writeTraceInstructionTreeToStringBuilder((ArrayList) o, stringBuilder, useRawLine);
            }
            else if(o instanceof ExecutionTrace){
                ExecutionTrace et = (ExecutionTrace) o;
                writeTraceInstructionTreeToStringBuilder(et.traceArray, stringBuilder, useRawLine);

            }
        }
        if(useRawLine) {
            stringBuilder.append("End of method context:").append(containingMethod).append("\n");
        }
    }

//    public static void writeTraceInstructionTreeToStringBuilderOldFormat(ArrayList arrayList, StringBuilder stringBuilder, boolean shouldWriteLogSyntax) {
//        for (Object o: arrayList) {
//            if(o instanceof TraceInstruction){
//                TraceInstruction t = (TraceInstruction) o;
//                if(shouldWriteLogSyntax){
//                    stringBuilder.append(t.getRawLine() + "\n");
//                }
//                else {
//                    stringBuilder.append(t.getMethodCallStack())
//                            .append(">>")
//                            .append(t.getInstruction())
//                            .append("\n");
//                }
//            }
//            else {
//                writeTraceInstructionTreeToStringBuilderOldFormat((ArrayList) o, stringBuilder,shouldWriteLogSyntax);
//            }
//        }
//    }

    public static void writeTraceInstructionTreeToFile(ArrayList arrayList, String filePath, boolean shouldAppend, boolean useRawLine) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if(!file.exists()) file.createNewFile();

        StringBuilder stringBuilder = new StringBuilder();
        writeTraceInstructionTreeToStringBuilder(arrayList, stringBuilder, useRawLine);

        FileWriter fw = new FileWriter(file, shouldAppend);
        fw.write(stringBuilder.toString());
        fw.flush();
        fw.close();
    }

//    public static void writeTraceInstructionTreeToFileOldFormat(ArrayList arrayList, String filePath, boolean shouldAppend, boolean shouldWriteRawLine) throws IOException {
//        File file = new File(filePath);
//        file.getParentFile().mkdirs();
//        if(!file.exists()) file.createNewFile();
//
//        StringBuilder stringBuilder = new StringBuilder();
//        writeTraceInstructionTreeToStringBuilderOldFormat(arrayList, stringBuilder, shouldWriteRawLine);
//
//        FileWriter fw = new FileWriter(file, shouldAppend);
//        fw.write(stringBuilder.toString());
//        fw.flush();
//        fw.close();
//    }

    public static void writeTextToFile(String text, String filePath, boolean shouldAppend) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if(!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(file, shouldAppend);
        fw.write(text);
        fw.flush();
        fw.close();
    }

    public static void writeStringArrayListToFile(ArrayList<String> arrayList, String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if(!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(filePath);
        for(String s: arrayList){
            fw.write(s+"\n");
        }
        fw.flush();
        fw.close();
    }

}
