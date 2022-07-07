//package usask.cyberlab.smalisimulator.simsmali.TestSuite_8_AmbiguousValue;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.emulator.BranchedExecutionsWrapper;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//public class AmbgValPathExplosionTest {
//
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception{
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/ambiguousValue" , context);
//        AssetCopier.copyFile("ambiguousValue/AmbgValPathExplosion.smali");
//        testSmaliClass = loader.getSmaliClass("LAmbgValPathExplosion;");
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        testSmaliClass=null;
//        loader=null;
//    }
//
//    @Test
//    public void simpleLoopTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("simpleLoop(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        Assert.assertEquals(3, executions.get(0).getExecutionTrace().size());
//        Assert.assertEquals(9, executions.get(1).getExecutionTrace().size());
//    }
//
//    @Test
//    public void nonNestedLoopsTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("nonNestedLoops(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(4, executions.size());
//        Assert.assertEquals(8, executions.get(0).getExecutionTrace().size());
//        Assert.assertEquals(14, executions.get(1).getExecutionTrace().size());
//        Assert.assertEquals(14, executions.get(2).getExecutionTrace().size());
//        Assert.assertEquals(20, executions.get(3).getExecutionTrace().size());
//
//    }
//
//    @Test
//    public void nestedLoopsTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("nestedLoops(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(3, executions.size());
//        Assert.assertEquals(3, executions.get(0).getExecutionTrace().size());
//        Assert.assertEquals(9, executions.get(1).getExecutionTrace().size());
//        Assert.assertEquals(16, executions.get(2).getExecutionTrace().size());
//    }
//
//    @Test
//    public void recursiveFibonacciTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("fibonacci(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        Assert.assertEquals(3, executions.get(0).getExecutionTrace().size());
////        Assert.assertEquals(10, executions.get(1).getExecutionTrace().size());
//    }
//
//    @Test
//    public void twoRecursiveMethodsTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("isEven(I)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(7, executions.size());
//    }
//
//    @Test
//    public void recursionAndLoopTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("recursionAndLoopMix(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(sm.getNumberOfLocalRegisters()).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(3, executions.size());
//    }
//
//}
