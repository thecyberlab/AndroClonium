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
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class TestExecutionTraceCapturingWhenConstructorError {


    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/executionTraceCapturing" , context);
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceUnderExceptionTest.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceUnderExceptionTest_A.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceUnderExceptionTest_B.smali");
        AssetCopier.copyFile("executionTraceCapturing/ConstructorExecutionTraceUnderExceptionTest_C.smali");
        testSmaliClass = loader.getSmaliClass("LConstructorExecutionTraceUnderExceptionTest;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test0(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "0");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.VOID, me.getExecutionResult().getType());
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(48, s.split("\n").length);
    }

    @Test
    public void test1(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "1");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof StringIndexOutOfBoundsException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(13, s.split("\n").length);
    }

    @Test
    public void test2(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "2");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof StringIndexOutOfBoundsException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(21, s.split("\n").length);
    }

    @Test
    public void test3(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "3");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof ArithmeticException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(28, s.split("\n").length);
    }

    @Test
    public void test4(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "4");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof IllegalArgumentException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(33, s.split("\n").length);
    }

    @Test
    public void test5(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "5");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof ArithmeticException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(38, s.split("\n").length);
    }

    @Test
    public void test6(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "6");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof IllegalStateException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(44, s.split("\n").length);
    }

    @Test
    public void test7(){
        SmaliMethod smaliMethod = testSmaliClass.getMethod("test(Ljava/lang/String;)V");
        MethodExecution me = new MethodExecution(smaliMethod, loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "7");
        me.getRegister(1).set(o);
        me.execute();
        Assert.assertEquals(ResultType.EXCEPTION, me.getExecutionResult().getType());
        Objekt res = (Objekt) me.getExecutionResult().getResult();
        Exception e = (Exception) res.getMirroringObject();
        Assert.assertTrue(e instanceof RuntimeException);
        ExecutionTrace executionTrace = me.getExecutionTrace();
        String s = executionTrace.getExecutionLogs();
        Assert.assertEquals(49, s.split("\n").length);
    }
}
