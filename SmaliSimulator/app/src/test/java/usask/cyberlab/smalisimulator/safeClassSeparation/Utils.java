package usask.cyberlab.smalisimulator.safeClassSeparation;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Utils {

    public static HashSet<String> getInitialSafeClasses() throws IOException {
        HashSet<String> safeClasses = new HashSet<>();
        FileReader fileReader = new FileReader(Config.initialSafeClassesListFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            if(line.startsWith("L")){
                safeClasses.add(line);
            }
            line = bufferedReader.readLine();
        }
        return safeClasses;
    }

    public static ArrayList<String> getAllSmaliFilesInDir(File dirPath, String basePath){
        ArrayList<String> result = new ArrayList<>();
        File filesList[] = dirPath.listFiles();
        for(File file : filesList) {
            if(file.isFile() && file.getName().endsWith(".smali") && !file.getName().equals("package-info.smali")) {
                String cleanLine = file.getPath().replace(".smali", ";");
                cleanLine = cleanLine.replace(basePath, "");
                if(cleanLine.startsWith("/")) cleanLine = cleanLine.substring(1);
                String smaliStyleClassPath = "L" + cleanLine;
                result.add(smaliStyleClassPath);
            } else if(file.isDirectory()){
                result.addAll(getAllSmaliFilesInDir(file, basePath));
            }
        }
        return result;
    }

    public static ArrayList<String> getListOfAllSdkClasses(int apiVersion){
        File baseDir = new File(Config.getAndroidSdkSmaliDirectoryPath(apiVersion));
        if(!baseDir.exists() || !baseDir.isDirectory()){
            throw new IllegalStateException();
        }
        ArrayList<String> allSmaliFilePaths = Utils.getAllSmaliFilesInDir(baseDir, baseDir.getPath());
        return allSmaliFilePaths;
    }

    public static HashSet<String> getStaticSafeClasses() throws Exception{
        HashSet<String> safeClasses = new HashSet<>();
        FileReader fileReader = new FileReader(Config.staticallySafeClassesFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            if(line.startsWith("L")){
                safeClasses.add(line);
            }
            line = bufferedReader.readLine();
        }
        return safeClasses;
    }

    public static HashSet<String> getStaticPartialSafeClasses() throws Exception{
        HashSet<String> safeClasses = new HashSet<>();
        FileReader fileReader = new FileReader(Config.staticallyPartialSafeClassesFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            if(line.startsWith("L")){
                safeClasses.add(line);
            }
            line = bufferedReader.readLine();
        }
        return safeClasses;
    }

    public static HashSet<String> getPureMethods() throws Exception{
        HashSet<String> pureMethods = new HashSet<>();
        FileReader fileReader = new FileReader(Config.initialPureMethodsFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            line = line.trim();
            if(!line.isEmpty()){
                pureMethods.add(line);
            }
            line = bufferedReader.readLine();
        }
        return pureMethods;

    }

}
