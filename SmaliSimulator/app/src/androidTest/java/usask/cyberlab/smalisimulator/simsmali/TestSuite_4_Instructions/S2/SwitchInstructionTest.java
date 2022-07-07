package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

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
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class SwitchInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/switch_test.smali");
        testSmaliClass = loader.getSmaliClass("Lswitch_test;");
    }

    @AfterClass
    public static void afterClass() throws Exception{
        loader = null;
        testSmaliClass = null;
    }

//    @Test/
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('A');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Excellent!", reflectedObjekt.toString());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('B');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Well done", reflectedObjekt.toString());
    }


    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('C');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Well done", reflectedObjekt.toString());
    }


    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('D');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Better try again", reflectedObjekt.toString());
    }


    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('E');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Invalid grade", reflectedObjekt.toString());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("packedSwitchTest(C)Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set('F');
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT,result.getType());
        Objekt reflectedObjekt = (Objekt) result.getResult();
        Assert.assertEquals("Better try again", reflectedObjekt.toString());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("sparseSwitch(I)I");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set(1);
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(20, result.getResult());
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("sparseSwitch(I)I");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set(2);
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(30, result.getResult());
    }


    @Test
    public void test9(){
        SmaliMethod sm = testSmaliClass.getMethod("sparseSwitch(I)I");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.getRegister(1).set(3);
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(10, result.getResult());
    }

    @Test
    public void test10(){
        SmaliMethod sm = testSmaliClass.getMethod("sparseSwitch(I)I");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.execute();
        MethodExecutionResult result = methodExecution.getExecutionResult();
        Assert.assertEquals(10, result.getResult());
    }

}
