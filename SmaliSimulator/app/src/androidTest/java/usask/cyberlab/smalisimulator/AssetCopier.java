package usask.cyberlab.smalisimulator;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetCopier {

    private static final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private static final Context testContext = InstrumentationRegistry.getInstrumentation().getContext();
    private static final AssetManager testAssetManager = testContext.getAssets();

    public static void copyFile(String filePath) throws IOException {
        String destPath = appContext.getCacheDir().getAbsolutePath() + "/" + filePath;
        copyFile(filePath, destPath);
    }

    public static void copyFile(String filePath, String destPath) throws IOException {
        File f = new File(destPath);
        if(!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
        BufferedReader reader = new BufferedReader(new InputStreamReader(testAssetManager.open(filePath)));
        String line = reader.readLine();
        while (line != null){
            writer.write( line + "\n");
            line = reader.readLine();
        }

        reader.close();
        writer.flush();
        writer.close();
    }

    public static void copyDirectory(String dirPath) throws IOException{
        if(dirPath.endsWith("/")) dirPath = dirPath.substring(0, dirPath.length() - 1);
        for (String s: testAssetManager.list(dirPath)) {
                if (s.endsWith(".smali") || s.endsWith(".txt")) copyFile(dirPath + "/" + s);
                else copyDirectory(dirPath + "/" + s);
        }
    }

}
