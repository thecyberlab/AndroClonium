package usask.cyberlab.smalisimulator.simsmali;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class SampleCodeForThesis {

    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws IOException {
        AssetCopier.copyFile("SampleCodeForThesis.smali");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String basePath = context.getCacheDir().getAbsolutePath();
        loader = new ClazzLoader(basePath, context);
    }

    @AfterClass
    public static void afterClass() {
        loader = null;
    }

    @Test
    public void test(){
        SmaliClass sc = loader.getSmaliClass("LSampleCodeForThesis;");
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz(sc.getClassPath());
        SmaliMethod sm = smaliClazz.getSmaliMethod("initAccountSpinner(Landroid/widget/Spinner;)V");
        MethodExecution methodExecution = new MethodExecution(sm, loader, new AmbiguousValue("LSampleCodeForThesis;"));
        methodExecution.execute();
        ExecutionTrace executionTrace = methodExecution.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        System.out.println(s);
    }
}
