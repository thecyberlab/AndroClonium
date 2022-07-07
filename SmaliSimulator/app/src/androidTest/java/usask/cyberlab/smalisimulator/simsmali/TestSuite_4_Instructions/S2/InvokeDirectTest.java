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
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeDirectTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeDirect.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeDirect;");
    }

    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test00() {
        SmaliMethod sm = testSmaliClass.getMethod("test00()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertNotNull(o.getMirroringObject());
        Assert.assertEquals(Object.class ,o.getMirroringObject().getClass());
    }

    @Test
    public void test0() {
        Clazz clazz = loader.getClazz("LInvokeDirect;");
        SmaliMethod sm = testSmaliClass.getMethod("test0()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertNotNull(o.getMirroringObject());
        Assert.assertEquals(clazz.getMirroringClass() ,o.getMirroringObject().getClass());
    }


    @Test
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("test1()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(11, mr.getResult());
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("test2()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(80, mr.getResult());
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("test3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/Exception;", objekt.getType());
    }

    @Test
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("test4()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/IllegalArgumentException;", objekt.getType());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("test5()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/NullPointerException;", objekt.getType());
    }
}
