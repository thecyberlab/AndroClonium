package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class ShuffledClinitWithInstructionPayloadTest {

    @Test
    public void test() throws Exception{
        SimSmaliConfig.logClinitExecutionTraceInSmaliClazz = true;
        AssetCopier.copyFile("ClinitWithInstructionPayloadTest.smali");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        SmaliClass sc= clazzLoader.getSmaliClass("LClinitWithInstructionPayloadTest;");
        SmaliMethod sm = sc.getMethod("<clinit>()V");
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz(sc.getClassPath());
        ExecutionTrace executionTrace = smaliClazz.getClinitExecutionTrace();
        String s = executionTrace.getExecutionLogs().trim();
        String[] lines = s.split("\n");
        Assert.assertEquals(56, lines.length);
        SimSmaliConfig.logClinitExecutionTraceInSmaliClazz = false;

    }
}
