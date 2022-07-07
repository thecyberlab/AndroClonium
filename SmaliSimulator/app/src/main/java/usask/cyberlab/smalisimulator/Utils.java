package usask.cyberlab.smalisimulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;


public class Utils {
    public static File checkForTooLongFileNamesAndFixIfNecessary(File methodAnalysisDir){
        if(methodAnalysisDir.exists() && !methodAnalysisDir.isDirectory()) throw new IllegalStateException();

        String methodName = methodAnalysisDir.getName();
        String className = methodAnalysisDir.getParentFile().getName();

        File appDir = methodAnalysisDir.getParentFile().getParentFile();
        if(!appDir.exists()){
            appDir.mkdirs();
        }

        if(methodName.length() < 256 && className.length() < 256){
            return methodAnalysisDir;
        }
        else {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            }
            catch (Exception e){
                throw new IllegalStateException(e);
            }

            if(className.length() > 255){
                md5.update(className.getBytes());
                String originalClassName = className;
                className = bytesToHex(md5.digest());
                md5.reset();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(appDir, "hashedClassNamesMap.txt")));
                    writer.write(originalClassName + "|" + className);
                    writer.flush();
                    writer.close();
                }
                catch (Exception e){
                    throw new IllegalStateException(e);
                }

            }

            File classDir = new File(appDir, className);
            if(!classDir.exists()){
                classDir.mkdirs();
            }

            if(methodName.length() > 255){
                md5.update(methodName.getBytes());
                String originalMethodName = methodName;
                methodName = bytesToHex(md5.digest());
                md5.reset();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(classDir, "hashedMethodNamesMap.txt")));
                    writer.write(originalMethodName + "|" + methodName);
                    writer.flush();
                    writer.close();
                }
                catch (Exception e){
                    throw new IllegalStateException(e);
                }


            }

            File f = new File(classDir, methodName);
            if(!f.exists()){
                f.mkdirs();
            }
            return f;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getNowDateTimeString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static void writeTimeStampToTimeLogFile(SmaliMethod smaliMethod, String stage){
        if(!SimSmaliConfig.logTimeForSimulation) return;
        try {
            File appFiles = SimulationManager.Companion.getSelf().getContext().getFilesDir();
            String appName = SimulationManager.Companion.getSelf().getAppName();
            String appVersion = SimulationManager.Companion.getSelf().getAppVersion();
            String classPath = smaliMethod.getContainingClass().getClassPath().replace("/", ".");
            String methodSig = smaliMethod.getClassMethodSignature().split("->")[1].replace("/", ".");

            File f = new File(new File(new File(new File(appFiles, "timeLogs"), appName), appVersion), classPath);
            f.mkdirs();
            f = new File(f, methodSig +".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            String timeStr = getNowDateTimeString();
            writer.write(stage + "|" + timeStr + "\n");
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeStringToTimeLogFile(SmaliMethod smaliMethod, String line){
        if(!SimSmaliConfig.logTimeForSimulation) return;
        try {
            File appFiles = SimulationManager.Companion.getSelf().getContext().getFilesDir();
            String appName = SimulationManager.Companion.getSelf().getAppName();
            String appVersion = SimulationManager.Companion.getSelf().getAppVersion();
            String classPath = smaliMethod.getContainingClass().getClassPath().replace("/", ".");
            String methodSig = smaliMethod.getClassMethodSignature().split("->")[1].replace("/", ".");

            File f = new File(new File(new File(new File(appFiles, "timeLogs"), appName), appVersion), classPath);
            f.mkdirs();
            f = new File(f, methodSig +".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            writer.write(line + "\n");
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeExecutionTraceOfMethodExecutionEndedWithError(SmaliMethod smaliMethod, MethodExecution methodExecution){
        if(!SimSmaliConfig.logExecutionTraceOfMethodWithErrors) return;
        try {
            File appFiles = SimulationManager.Companion.getSelf().getContext().getFilesDir();
            String appName = SimulationManager.Companion.getSelf().getAppName();
            String appVersion = SimulationManager.Companion.getSelf().getAppVersion();
            String classPath = smaliMethod.getContainingClass().getClassPath().replace("/", ".");
            String methodSig = smaliMethod.getClassMethodSignature().split("->")[1].replace("/", ".");
            String exeTrace = methodExecution.getExecutionTrace().getExecutionLogs();

            File f = new File(new File(new File(new File(appFiles, "executionTracesOfErroredMethods"), appName), appVersion), classPath);
            f.mkdirs();
            f = new File(f, methodSig +".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            writer.write(exeTrace + "\n==========$$$=========\n");
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getStackTraceAsString(Throwable t){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
