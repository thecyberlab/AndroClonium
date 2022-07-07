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
import usask.cyberlab.smalisimulator.simsmali.instructions.BinaryOperationInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.ConstInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeStaticInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.MoveInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.ReturnInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;

public class InvokeStaticOnSmaliTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/executionTraceCapturing" , context);
        AssetCopier.copyFile("executionTraceCapturing/InvokeStaticOnSmali.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeStaticOnSmali;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("test1()I");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        int i = (Integer) mr.getResult();
        Assert.assertEquals(15, i);
        ExecutionTrace executedInstructions = me.getExecutionTrace();
        System.out.println("-------");
        System.out.println(executedInstructions.getExecutionLogs());
        System.out.println("-------");
        Assert.assertTrue(executedInstructions.get(0) instanceof ConstInstruction);
        Assert.assertTrue(executedInstructions.get(1) instanceof InvokeStaticInstruction);
        ExecutionTrace innerInstructions = (ExecutionTrace) executedInstructions.get(2);
        Assert.assertTrue(innerInstructions.get(0) instanceof ConstInstruction);
        Assert.assertTrue(innerInstructions.get(1) instanceof BinaryOperationInstruction);
        Assert.assertTrue(innerInstructions.get(2) instanceof ReturnInstruction);
        Assert.assertTrue(executedInstructions.get(3) instanceof MoveInstruction);
        Assert.assertTrue(executedInstructions.get(4) instanceof ReturnInstruction);
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("test2()I");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        int i = (Integer) mr.getResult();
        Assert.assertEquals(10, i);
        ExecutionTrace executedInstructions = me.getExecutionTrace();
        System.out.println("-------");
        System.out.println(executedInstructions.getExecutionLogs());
        System.out.println("-------");
    }
}
