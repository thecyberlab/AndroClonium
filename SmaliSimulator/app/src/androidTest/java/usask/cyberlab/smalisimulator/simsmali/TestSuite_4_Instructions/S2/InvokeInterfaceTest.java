package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

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
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeInterfaceTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeInterface.smali");
        AssetCopier.copyFile("instructions/InvokeInterface$1.smali");
        AssetCopier.copyFile("instructions/TestInterface.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeInterface;");
    }

    public static void afterClass(){
        testSmaliClass = null;
        loader = null;
    }

    @Test
    public void test0(){
        SmaliMethod sm = testSmaliClass.getMethod("test0()Ljava/lang/Object;");
        loader.getClazz("LInvokeInterface;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt objekt = (Objekt) mr.getResult();
        System.out.println();
    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("test1()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(13, mr.getResult());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("test2()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(7, mr.getResult());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("test3()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt reflectedObjekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/Exception;", reflectedObjekt.getType());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("test4()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt reflectedObjekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/IllegalArgumentException;", reflectedObjekt.getType());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("sizeOfNewList1()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(0, mr.getResult());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("sizeOfNewList2()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(-1, mr.getResult());
    }
}
