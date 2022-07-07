package usask.cyberlab.smalisimulator.simsmali.TestSuite_6_ExecutionTraceCapturing;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.instructions.BinaryOperationInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.ConstInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeDirectInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.NewInstanceInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.NopInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.ReturnInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;

public class InvokeDirectSmaliConstructor {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/executionTraceCapturing" , context);
        AssetCopier.copyFile("executionTraceCapturing/InvokeDirectSmaliConstructor.smali");
        AssetCopier.copyFile("executionTraceCapturing/TestClass.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeDirectSmaliConstructor;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("test1()LTestClass;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        ExecutionTrace executedInstructions = me.getExecutionTrace();
        System.out.println("-------");
        System.out.println(executedInstructions.getExecutionLogs());
        System.out.println("-------");
        Assert.assertTrue(executedInstructions.get(0) instanceof ConstInstruction);
        Assert.assertTrue(executedInstructions.get(1) instanceof NewInstanceInstruction);
        Assert.assertTrue(executedInstructions.get(2) instanceof InvokeDirectInstruction);
        ExecutionTrace a = (ExecutionTrace) executedInstructions.get(3);
        Assert.assertTrue(a.get(0) instanceof ConstInstruction);
        Assert.assertTrue(a.get(1) instanceof BinaryOperationInstruction);
        Assert.assertTrue(a.get(2) instanceof InvokeDirectInstruction);
        Assert.assertTrue(a.get(3) instanceof NopInstruction);
        Assert.assertTrue(a.get(4) instanceof ConstInstruction);
        Assert.assertTrue(a.get(5) instanceof BinaryOperationInstruction);
        Assert.assertTrue(a.get(6) instanceof ReturnInstruction);
        Assert.assertTrue(executedInstructions.get(4) instanceof ReturnInstruction);
    }
}
