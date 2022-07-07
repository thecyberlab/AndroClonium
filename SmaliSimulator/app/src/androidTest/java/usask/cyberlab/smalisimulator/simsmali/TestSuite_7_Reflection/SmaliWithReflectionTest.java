package usask.cyberlab.smalisimulator.simsmali.TestSuite_7_Reflection;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class SmaliWithReflectionTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/reflection" , context);
        AssetCopier.copyFile("reflection/ReflectionTest.smali");
        testSmaliClass = loader.getSmaliClass("LReflectionTest;");
    }

    @AfterClass
    public static void afterClass(){
        testSmaliClass=null;
        loader=null;
    }

    @Test
    public void test0(){
        SmaliMethod sm = testSmaliClass.getMethod("test0()I");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        int res = (Integer) mr.getResult();
        Assert.assertEquals(12, res);
        System.out.println(me.getExecutionTrace().getExecutionLogs());
    }



    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("test1()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Objekt res = (Objekt) mr.getResult();
        Assert.assertEquals("field1", res.getMirroringObject());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Objekt res = (Objekt) mr.getResult();
        Assert.assertEquals("Hola Tester from static world.", res.getMirroringObject());
        System.out.println(me.getExecutionTrace().getExecutionLogs());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("test3()LReflectionTest;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Objekt res = (Objekt) mr.getResult();
        Assert.assertEquals("ReflectionTest", res.getMirroringObject().getClass().getName());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("test4()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Objekt res = (Objekt) mr.getResult();
        Assert.assertEquals("field2", res.getMirroringObject());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("test5()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Objekt res = (Objekt) mr.getResult();
        Assert.assertEquals("Hello Tester from instance world.", res.getMirroringObject());
        System.out.println(me.getExecutionTrace().getExecutionLogs());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("test6()Z");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Z", av.getType());
        System.out.println(me.getExecutionTrace().getExecutionLogs());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("test7()I");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals(IllegalArgumentException.class, o.getMirroringObject().getClass());
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("test8()V");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals(IllegalArgumentException.class, o.getMirroringObject().getClass());
    }

}
