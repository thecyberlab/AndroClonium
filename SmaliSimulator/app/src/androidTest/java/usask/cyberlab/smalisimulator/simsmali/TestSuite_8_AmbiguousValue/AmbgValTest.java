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
//import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
//import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
//import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class AmbgValTest {
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/ambiguousValue" , context);
//        AssetCopier.copyFile("ambiguousValue/AmbgValTest.smali");
//        AssetCopier.copyFile("ambiguousValue/FieldOwner.smali");
//        AssetCopier.copyFile("ambiguousValue/MethodOwner.smali");
//        AssetCopier.copyFile("ambiguousValue/JavaDescendentClass.smali");
//        AssetCopier.copyFile("ambiguousValue/SmaliDescendentClass.smali");
//        testSmaliClass = loader.getSmaliClass("LAmbgValTest;");
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        testSmaliClass=null;
//        loader=null;
//    }
//
//    @Test
//    public void testMove(){
//        SmaliMethod sm = testSmaliClass.getMethod("moveAmbiguousValueTest(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testMoveWide(){
//        SmaliMethod sm = testSmaliClass.getMethod("moveWideAmbiguousValueTest(D)D");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("D"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("D", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testMoveObject(){
//        SmaliMethod sm = testSmaliClass.getMethod("moveObjectAmbiguousValueTest(Ljava/lang/Object;)Ljava/lang/Object;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/lang/Object;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testNewArray(){
//        SmaliMethod sm = testSmaliClass.getMethod("testNewArray(I)[Ljava/lang/Object;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("[Ljava/lang/Object;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testArrayLength(){
//        SmaliMethod sm = testSmaliClass.getMethod("testArrayLength([Ljava/lang/Object;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("[Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 2);
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(mr0.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr0.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//        Assert.assertEquals(mr1.getType(), ResultType.EXCEPTION);
//    }
//
//    @Test
//    public void testCmpKind(){
//        SmaliMethod sm = testSmaliClass.getMethod("testCmpKind(FF)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("F"));
//        initialExecution.getRegister(2).set(new AmbiguousValue("F"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testUnaryOp1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testUnaryOp1(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testUnaryOp2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testUnaryOp2(I)C");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("C", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testUnaryOp3(){
//        SmaliMethod sm = testSmaliClass.getMethod("testUnaryOp3(F)J");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("F"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("J", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testUnaryOp4(){
//        SmaliMethod sm = testSmaliClass.getMethod("testUnaryOp4(D)D");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("D"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.AMBIGUOUS_VALUE);
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("D", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testCheckCast1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testCheckCast(Ljava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/util/ArrayList;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.VOID);
//    }
//
//    @Test
//    public void testCheckCast2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testCheckCast(Ljava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/util/List;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.VOID);
//    }
//
//    @Test
//    public void testCheckCast3(){
//        SmaliMethod sm = testSmaliClass.getMethod("testCheckCast(Ljava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Integer;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.EXCEPTION);
//    }
//
//    @Test
//    public void testCheckCast4(){
//        SmaliMethod sm = testSmaliClass.getMethod("testCheckCast(Ljava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 2);
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(mr0.getType(), ResultType.VOID);
//        Assert.assertEquals(mr1.getType(), ResultType.EXCEPTION);
//        Object exp = ((Objekt) mr1.getResult()).getMirroringObject();
//        Assert.assertTrue(exp instanceof ClassCastException);
//    }
//
//    @Test
//    public void testIf(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIfTest(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 2);
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        Assert.assertEquals(me0.getExecutionTrace().size(), 5);
//        Assert.assertEquals(me1.getExecutionTrace().size(), 3);
//    }
//
//    @Test
//    public void testIfZ(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIfTestZ(I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 2);
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        Assert.assertEquals(me0.getExecutionTrace().size(), 4);
//        Assert.assertEquals(me1.getExecutionTrace().size(), 2);
//    }
//
//    @Test
//    public void testInstanceOf1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInstanceOf(Ljava/lang/Object;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/util/ArrayList;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.INTEGER);
//        int i = (Integer) mr.getResult();
//        Assert.assertEquals(1, i);
//    }
//
//    @Test
//    public void testInstanceOf2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInstanceOf(Ljava/lang/Object;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/util/List;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.INTEGER);
//        int i = (Integer) mr.getResult();
//        Assert.assertEquals(1, i);
//    }
//
//    @Test
//    public void testInstanceOf3(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInstanceOf(Ljava/lang/Object;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Integer;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(executions.size(), 1);
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(mr.getType(), ResultType.INTEGER);
//        int i = (Integer) mr.getResult();
//        Assert.assertEquals(0, i);
//    }
//
//    @Test
//    public void testInstanceOf4(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInstanceOf(Ljava/lang/Object;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(ResultType.INTEGER, mr0.getType());
//        Assert.assertEquals(ResultType.INTEGER, mr1.getType());
//        int i0 = (Integer) mr0.getResult();
//        int i1 = (Integer) mr1.getResult();
//        Assert.assertEquals(1, i0);
//        Assert.assertEquals(0, i1);
//    }
//
//    @Test
//    public void testPackedSwitch(){
//        SmaliMethod sm = testSmaliClass.getMethod("testPackedSwitch(I)Ljava/lang/String;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(5, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecution me2 = executions.get(2);
//        MethodExecution me3 = executions.get(3);
//        MethodExecution me4 = executions.get(4);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        MethodExecutionResult mr2 = me2.getExecutionResult();
//        MethodExecutionResult mr3 = me3.getExecutionResult();
//        MethodExecutionResult mr4 = me4.getExecutionResult();
//        Assert.assertEquals("Invalid grade", mr0.getResult().toString());
//        Assert.assertEquals("Excellent!", mr1.getResult().toString());
//        Assert.assertEquals("Well done", mr2.getResult().toString());
//        Assert.assertEquals("Better try again", mr3.getResult().toString());
//        Assert.assertEquals("Better try again", mr4.getResult().toString());
//        Assert.assertEquals(5, me3.getExecutionTrace().size());
//        Assert.assertEquals(4, me4.getExecutionTrace().size());
//    }
//
//    @Test
//    public void testSparseSwitch(){
//        SmaliMethod sm = testSmaliClass.getMethod("testSparseSwitch(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(3, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        MethodExecutionResult mr2 = executions.get(2).getExecutionResult();
//        int i0 = (Integer) mr0.getResult();
//        int i1 = (Integer) mr1.getResult();
//        int i2 = (Integer) mr2.getResult();
//        Assert.assertEquals(10, i0);
//        Assert.assertEquals(20, i1);
//        Assert.assertEquals(30, i2);
//    }
//
//    @Test
//    public void testBinOp1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testBinOp1(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//    }
//
//    @Test
//    public void testBinOp2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testBinOp2(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//    }
//
//    @Test
//    public void testBinOp3(){
//        SmaliMethod sm = testSmaliClass.getMethod("testBinOp3(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//    }
//
//    @Test
//    public void testBinOp4(){
//        SmaliMethod sm = testSmaliClass.getMethod("testBinOp4(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//    }
//
//    @Test
//    public void testAGetAmbiguousIndex(){
//        SmaliMethod sm = testSmaliClass.getMethod("testAGet_Ambg_index(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult res = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, res.getType());
//        AmbiguousValue av = (AmbiguousValue) res.getResult();
//        Assert.assertEquals("I", av.getType());
//    }
//
//    @Test
//    public void testAGetAmbiguousArray(){
//        SmaliMethod sm = testSmaliClass.getMethod("testAGet_Ambg_array([I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("[I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//        Assert.assertEquals("java.lang.NullPointerException", mr1.getResult().toString());
//    }
//
//    @Test
//    public void testAPutAmbiguousArray(){
//        SmaliMethod sm = testSmaliClass.getMethod("testAPut_Ambg_array([I)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("[I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        Assert.assertEquals(ResultType.VOID, mr0.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//        Assert.assertEquals("java.lang.NullPointerException", mr1.getResult().toString());
//    }
//
//    @Test
//    public void testAPutAmbiguousIndex(){
//        SmaliMethod sm = testSmaliClass.getMethod("testAPut_Ambg_index(I)[I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult res = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, res.getType());
//        AmbiguousValue av = (AmbiguousValue) res.getResult();
//        Assert.assertEquals("[I", av.getType());
//    }
//
//    @Test
//    public void testAPutAmbiguousValue(){
//        SmaliMethod sm = testSmaliClass.getMethod("testAPut_Ambg_value(I)[I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult res = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, res.getType());
//        ArrayObjekt ao = (ArrayObjekt) res.getResult();
//        AmbiguousValue av = (AmbiguousValue) ao.getValue(1);
//        Assert.assertEquals("I", av.getType());
//    }
//
//    @Test
//    public void testSPutAmbiguousValue(){
//        SmaliMethod sm = testSmaliClass.getMethod("testSPutAmbgValue(ILjava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(0).set(new AmbiguousValue("I"));
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LFieldOwner;");
//        AmbiguousValue i = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticIntField");
//        AmbiguousValue o = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticObjectField");
//        Assert.assertEquals("I", i.getType());
//        Assert.assertEquals("Ljava/lang/Object;", o.getType());
//    }
//
//    @Test
//    public void testSGetIntAmbiguousValue(){
//        //First we do a SPut to make sure the ambiguous value in it's place
//        SmaliMethod sm = testSmaliClass.getMethod("testSPutAmbgValue(ILjava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(0).set(new AmbiguousValue("I"));
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LFieldOwner;");
//        AmbiguousValue i = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticIntField");
//        AmbiguousValue o = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticObjectField");
//        Assert.assertEquals("I", i.getType());
//        Assert.assertEquals("Ljava/lang/Object;", o.getType());
//        // now to the real test
//        sm = testSmaliClass.getMethod("testSGetInt()I");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult res = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, res.getType());
//        AmbiguousValue av = (AmbiguousValue) res.getResult();
//        Assert.assertEquals("I", av.getType());
//    }
//
//
//    @Test
//    public void testSGetObjectAmbiguousValue(){
//        //First we do a SPut to make sure the ambiguous value in it's place
//        SmaliMethod sm = testSmaliClass.getMethod("testSPutAmbgValue(ILjava/lang/Object;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(0).set(new AmbiguousValue("I"));
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LFieldOwner;");
//        AmbiguousValue i = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticIntField");
//        AmbiguousValue o = (AmbiguousValue) smaliClazz.getStaticFieldValue("staticObjectField");
//        Assert.assertEquals("I", i.getType());
//        Assert.assertEquals("Ljava/lang/Object;", o.getType());
//        // now to the real test
//        sm = testSmaliClass.getMethod("testSGetObject()Ljava/lang/Object;");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult res = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, res.getType());
//        AmbiguousValue av = (AmbiguousValue) res.getResult();
//        Assert.assertEquals("Ljava/lang/Object;", av.getType());
//    }
//
//    @Test
//    public void testIPutAmbiguousReferenceObject(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIPutAmbiguousReferenceObject(LFieldOwner;)V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("LFieldOwner;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(3, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecution me2 = executions.get(2);
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        MethodExecutionResult mr2 = executions.get(2).getExecutionResult();
//        Assert.assertEquals(ResultType.VOID, mr0.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr2.getType());
//        Assert.assertEquals(6, me0.getExecutionTrace().size());
//        Assert.assertEquals(2, me1.getExecutionTrace().size());
//        Assert.assertEquals(5, me2.getExecutionTrace().size());
//    }
//
//    @Test
//    public void testIGetAmbiguousReferenceObject1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIGetAmbiguousReferenceObject1(LFieldOwner;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("LFieldOwner;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr0.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//    }
//
//    @Test
//    public void testIGetAmbiguousReferenceObject2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIGetAmbiguousReferenceObject2(LFieldOwner;)Ljava/lang/Object;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("LFieldOwner;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecutionResult mr0 = executions.get(0).getExecutionResult();
//        MethodExecutionResult mr1 = executions.get(1).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr0.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr0.getResult();
//        Assert.assertEquals("Ljava/lang/Object;", ambiguousValue.getType());
//        Assert.assertEquals(ResultType.EXCEPTION, mr1.getType());
//    }
//
//    @Test
//    public void testIPutAmbiguousValue1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIPutAmbiguousValue(I)LFieldOwner;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt objekt = (Objekt) mr.getResult();
//        Object fieldValue = objekt.getInstanceFieldValue("instanceIntField");
//        Assert.assertTrue(fieldValue instanceof AmbiguousValue);
//        Assert.assertEquals("I", ((AmbiguousValue) fieldValue).getType());
//    }
//
//    @Test
//    public void testIPutAmbiguousValue2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testIPutAmbiguousValue(Ljava/lang/Object;)LFieldOwner;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt objekt = (Objekt) mr.getResult();
//        Object fieldValue = objekt.getInstanceFieldValue("instanceObjectField");
//        Assert.assertTrue(fieldValue instanceof AmbiguousValue);
//        Assert.assertEquals("Ljava/lang/Object;", ((AmbiguousValue) fieldValue).getType());
//    }
//
//    @Test
//    public void testIGetAmbiguousValue1(){
//        // First I put ambiguous value to a FieldOwner object so I can test iget properly
//        SmaliMethod sm = testSmaliClass.getMethod("testIPutAmbiguousValue(I)LFieldOwner;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt refObject = (Objekt) mr.getResult();
//        // ----------------------------------------
//        sm = testSmaliClass.getMethod("testIGetAmbiguousValue1(LFieldOwner;)I");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(refObject);
//        executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testIGetAmbiguousValue2(){
//        // First I put ambiguous value to a FieldOwner object so I can test iget properly
//        SmaliMethod sm = testSmaliClass.getMethod("testIPutAmbiguousValue(Ljava/lang/Object;)LFieldOwner;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt refObject = (Objekt) mr.getResult();
//        // ----------------------------------------
//        sm = testSmaliClass.getMethod("testIGetAmbiguousValue2(LFieldOwner;)Ljava/lang/Object;");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(refObject);
//        executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/lang/Object;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testInvokeStaticAmbiguousParamOnSmaliMethod(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInvokeStaticOnSmaliMethodWithAmbiguousParameter(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", ambiguousValue.getType());
//    }
//
//    @Test
//    public void testInvokeStaticWithAmbiguousParamOnJavaMethod(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInvokeStaticOnJavaMethodWithAmbiguousParameter([Ljava/lang/Integer;)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("[Ljava/lang/Integer;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Z", av.getType());
//        Register r1 = me.getRegister(1);
//        Assert.assertTrue(r1.containsAmbiguousValue());
//        AmbiguousValue av1 = r1.getAmbiguousValue();
//        Assert.assertEquals("[Ljava/lang/Integer;", av1.getType());
//    }
//
//    @Test
//    public void testInvokeVirtualWithAmbiguousParamOnSmaliMethod(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInvokeVirtualWithAmbiguousParamOnSmaliMethod(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", av.getType());
//        Register r0 = me.getRegister(0);
//        Assert.assertTrue(r0.containsRefToObject());
//        Objekt o = (Objekt) r0.getReferencedObjectValue();
//        Assert.assertEquals("LMethodOwner;", o.getType());
//    }
//
//    @Test
//    public void testInvokeVirtualWithAmbiguousParamOnJavaMethod(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInvokeVirtualWithAmbiguousParamOnJavaMethod(Ljava/lang/Object;)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Z", av.getType());
//        Register r0 = me.getRegister(0);
//        Assert.assertTrue(r0.containsAmbiguousValue());
//        av = r0.getAmbiguousValue();
//        Assert.assertEquals("Ljava/util/ArrayList;", av.getType());
//    }
//
//    @Test
//    public void testInvokeVirtualWithAmbiguousParamAndRef(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInvokeVirtualWithAmbiguousParamAndRef(Ljava/lang/Object;)Ljava/lang/String;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("Ljava/lang/Object;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/lang/String;", av.getType());
//    }
//
//    //-------------------------------------
//
//    @Test
//    public void testConstructorInvokeDirectOnJavaObject(){
//        SmaliMethod sm = testSmaliClass.getMethod("testConstructorInvokeDirectOnJavaObject(I)Ljava/util/ArrayList;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/util/ArrayList;", av.getType());
//    }
//
//    @Test
//    public void testConstructorInvokeDirectSmaliObjekt1(){
//        SmaliMethod sm = testSmaliClass.getMethod("testConstructorInvokeDirectOnSmaliObjekt1(I)LMethodOwner;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt objekt = (Objekt) mr.getResult();
//        Assert.assertEquals("MethodOwner", objekt.getMirroringObject().getClass().getName());
//    }
//
//    @Test
//    public void testConstructorInvokeDirectSmaliObjekt2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testConstructorInvokeDirectOnSmaliObjekt2(I)LJavaDescendentClass;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution me = executions.get(0);
//        MethodExecutionResult mr = me.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("LJavaDescendentClass;", av.getType());
//    }
//
//    @Test
//    public void testPrivateMethodInvokeDirectTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("testPrivateMethodInvokeDirect(I)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", av.getType());
//    }
//
//
//    @Test
//    public void testPrivateMethodInvokeDirectTest2(){
//        SmaliMethod sm = testSmaliClass.getMethod("testPrivateMethodInvokeDirect(LMethodOwner;)I");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("LMethodOwner;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecutionResult mr = executions.get(0).getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("I", av.getType());
//    }
//
//    //todo invoke-super where parent is Java class
//    //todo invoke-super where parent is Smali class
//
//    @Test
//    public void nestedInvokeStaticBranchingTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInnerBranchingInvokeStatic(I)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(ResultType.BOOLEAN, mr0.getType());
//        Assert.assertEquals(ResultType.BOOLEAN, mr1.getType());
//        Assert.assertTrue((Boolean) mr0.getResult());
//        Assert.assertFalse((Boolean) mr1.getResult());
//        ExecutionTrace inner0 = (ExecutionTrace) me0.getExecutionTrace().get(1);
//        ExecutionTrace inner1 = (ExecutionTrace) me1.getExecutionTrace().get(1);
//        Assert.assertEquals(4, inner0.size());
//        Assert.assertEquals(3, inner1.size());
//    }
//
//    @Test
//    public void nestedInvokeVirtualBranchingTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInnerBranchingInvokeVirtual(I)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(ResultType.BOOLEAN, mr0.getType());
//        Assert.assertEquals(ResultType.BOOLEAN, mr1.getType());
//        Assert.assertFalse((Boolean) mr0.getResult());
//        Assert.assertTrue((Boolean) mr1.getResult());
//        ExecutionTrace inner0 = (ExecutionTrace) me0.getExecutionTrace().get(4);
//        ExecutionTrace inner1 = (ExecutionTrace) me1.getExecutionTrace().get(4);
//        Assert.assertEquals(4, inner0.size());
//        Assert.assertEquals(3, inner1.size());
//    }
//
//    @Test
//    public void nestedInvokeDirectPrivateMethodBranchingTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("testInnerBranchingInvokeDirectPrivateMethod(I)Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(2, executions.size());
//        MethodExecution me0 = executions.get(0);
//        MethodExecution me1 = executions.get(1);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//        Assert.assertEquals(ResultType.BOOLEAN, mr0.getType());
//        Assert.assertEquals(ResultType.BOOLEAN, mr1.getType());
//        Assert.assertFalse((Boolean) mr0.getResult());
//        Assert.assertTrue((Boolean) mr1.getResult());
//        ExecutionTrace inner0 = (ExecutionTrace) me0.getExecutionTrace().get(4);
//        ExecutionTrace inner1 = (ExecutionTrace) me1.getExecutionTrace().get(4);
//        Assert.assertEquals(4, inner0.size());
//        Assert.assertEquals(5, inner1.size());
//    }
//
//    @Test
//    public void nestedInvokeSuperBranchingTest(){
//        Clazz clazz = loader.getClazz("LSmaliDescendentClass;");
////        SmaliMethod sm = testSmaliClass.getMethod("testInnerBranchingInvokeSuper(I)Z");
////        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
////        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
////        initialExecution.getRegister(1).set(new AmbiguousValue("I"));
////        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
////        MethodExecutionResult mr = executions.get(0).getExecutionResult();
////        Assert.assertEquals(2, executions.size());
////        MethodExecution me0 = executions.get(0);
////        MethodExecution me1 = executions.get(1);
////        MethodExecutionResult mr0 = me0.getExecutionResult();
////        MethodExecutionResult mr1 = me1.getExecutionResult();
////        Assert.assertTrue((Boolean) mr0.getResult());
////        Assert.assertFalse((Boolean) mr1.getResult());
////        ExecutionTrace inner0 = (ExecutionTrace) ((ExecutionTrace) me0.getExecutionTrace().get(4)).get(1);
////        ExecutionTrace inner1 = (ExecutionTrace) ((ExecutionTrace) me1.getExecutionTrace().get(4)).get(1);
////        Assert.assertEquals(4, inner0.size());
////        Assert.assertEquals(3, inner1.size());
////        System.out.println(me0.getExecutionTrace().getExecutionLogs());
//    }
//
//    @Test
//    public void throwAmbiguousValueTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("testThrowWrapper(Ljava/lang/Exception;)I");
//        MethodExecution methodExecution = new MethodExecution(sm, loader);
//        Register r1 = methodExecution.getRegister(1);
//        r1.set(new AmbiguousValue("Ljava/lang/NullPointerException;"));
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.INTEGER, mr.getType());
//        Assert.assertEquals(2, mr.getResult());
//
//        methodExecution = new MethodExecution(sm, loader);
//        r1 = methodExecution.getRegister(1);
//        r1.set(new AmbiguousValue("Ljava/lang/Exception;"));
//        methodExecution.execute();
//        mr = methodExecution.getExecutionResult();
//        System.out.println(mr.getResult());
//        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/lang/Exception;", av.getType());
//    }
//
//    //TODO considering branching on potential exceptions of method invocations
//
//
//}
