package usask.cyberlab.smalisimulator;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;

public class MethodSorter {

//    private static final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private static final Context testContext = InstrumentationRegistry.getInstrumentation().getContext();

    public static ArrayList<String> sortMethods(String filePath) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        AssetManager assetManager = testContext.getAssets();
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(filePath)));
        String line = reader.readLine();
        while (line != null){
            result.add(line);
            line = reader.readLine();
        }

        result.sort(new Comparator<String>() {
            @Override
            public int compare(String line1, String line2) {
                int s = line1.indexOf('(') + 1;
                int f = line1.lastIndexOf(')');
                String methodArgumentLine1 = line1.substring(s, f);
                s = line2.indexOf('(') + 1;
                f = line2.lastIndexOf(')');
                String methodArgumentLine2 = line2.substring(s, f);
                String[] methodArgs1 = SimulationUtils.parseMethodArgumentsString(methodArgumentLine1);
                String[] methodArgs2 = SimulationUtils.parseMethodArgumentsString(methodArgumentLine2);
                int method1NumberOfPrimitiveArgs = 0 ;
                int method1NumberOfObjectArgs = 0 ;
                int method2NumberOfPrimitiveArgs = 0 ;
                int method2NumberOfObjectArgs = 0 ;
                for (String argType: methodArgs1) {
                    if(argType.length() == 1) method1NumberOfPrimitiveArgs++;
                    else method1NumberOfObjectArgs++;
                }
                for (String argType: methodArgs2) {
                    if(argType.length() == 1) method2NumberOfPrimitiveArgs++;
                    else method2NumberOfObjectArgs++;
                }

                if(method1NumberOfPrimitiveArgs != method2NumberOfPrimitiveArgs)
                    return method1NumberOfPrimitiveArgs - method2NumberOfPrimitiveArgs;
                else
                    return method1NumberOfObjectArgs - method2NumberOfObjectArgs;
            }
        });
        return result;
    }
}
