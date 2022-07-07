package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeBaseObjectMethodsTest {
    private static ClazzLoader loader;
    private static SmaliClass testSmaliClass;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeBaseObjectMethods.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeBaseObjectMethods;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("test1()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertTrue(ro.toString().startsWith("java.lang.Object@"));
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertTrue(ro.toString().startsWith("InvokeBaseObjectMethods@"));
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt normalObjekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/CloneNotSupportedException;", normalObjekt.getType());
    }

    @Test
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("test4()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("[1, 2]", ro.toString());
    }

    @Test
    public void test5() {
        SmaliMethod sm = testSmaliClass.getMethod("test5()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.BOOLEAN, mr.getType());
        Boolean b = (Boolean) mr.getResult();
        Assert.assertTrue(b);

    }

    @Test
    public void test6() {
        SmaliMethod sm = testSmaliClass.getMethod("test6()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.BOOLEAN, mr.getType());
        Boolean b = (Boolean) mr.getResult();
        Assert.assertTrue(b);
    }

    @Test
    public void test7() {
        SmaliMethod sm =  testSmaliClass.getMethod("test7()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.BOOLEAN, mr.getType());
        Boolean b = (Boolean) mr.getResult();
        Assert.assertFalse(b);
    }

    @Test
    public void test8() {
        SmaliMethod sm = testSmaliClass.getMethod("test8()[I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        ArrayObjekt o = (ArrayObjekt) mr.getResult();
        Assert.assertEquals("[I", o.getType());
        int i = (Integer) o.getValue(0);
        Assert.assertEquals(3, i);
    }

    @Test
    public void test9() {
        SmaliMethod sm = testSmaliClass.getMethod("test9()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", o.getType());
        Assert.assertEquals("EUROPEAN", o.toString());
    }

    @Test
    public void test10() {
        SmaliMethod sm = testSmaliClass.getMethod("test10()Ljava/lang/Class;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/Class;", o.getType());
        Assert.assertEquals("class java.lang.Object", o.toString());
    }

    @Test
    public void test11() {
        SmaliMethod sm = testSmaliClass.getMethod("test11()Ljava/lang/Class;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/Class;", o.getType());
        Assert.assertEquals("class InvokeBaseObjectMethods", o.toString());
    }
}
