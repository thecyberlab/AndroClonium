package usask.cyberlab.smalisimulator.simsmali.TestSuite_6_ExecutionTraceCapturing;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;

public class TestExecutionTraceCapturingWithOtherNestedConstructors {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/executionTraceCapturing" , context);
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceWithNonSuperNestedCallsTest.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceWithNonSuperNestedCallsTest_A.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceWithNonSuperNestedCallsTest_B.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceWithNonSuperNestedCallsTest_C.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceWithNonSuperNestedCallsTest_Collection.smali");
        testSmaliClass = loader.getSmaliClass("LConstructorExecutionTraceWithNonSuperNestedCallsTest;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test(){
        SmaliMethod sm = testSmaliClass.getMethod("test()V");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        String s = me.getExecutionTrace().getExecutionLogs();
        Assert.assertEquals(53, s.split("\n").length);

    }


}
