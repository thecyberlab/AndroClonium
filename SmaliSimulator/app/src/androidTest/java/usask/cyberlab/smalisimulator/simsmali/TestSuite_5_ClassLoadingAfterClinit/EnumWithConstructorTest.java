package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

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
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class EnumWithConstructorTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        AssetCopier.copyFile("EnumWithConstructor.smali");
        AssetCopier.copyFile("EnumWithConstructorTest.smali");
        testSmaliClass = loader.getSmaliClass("LEnumWithConstructorTest;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test0(){
        Clazz clazz = loader.getClazz("LEnumWithConstructor;");
    }


    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("testNumber1()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(10, mr.getResult());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("testNumber2()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(20, mr.getResult());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("testNumber3()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(30, mr.getResult());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("testName1()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N1", o.getMirroringObject());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("testName2()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N2", o.getMirroringObject());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("testName3()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N3", o.getMirroringObject());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("getSignature1()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N1 10", o.getMirroringObject());
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("getSignature2()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N2 20", o.getMirroringObject());
    }

    @Test
    public void test9(){
        SmaliMethod sm = testSmaliClass.getMethod("getSignature3()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("N3 30", o.getMirroringObject());
    }

    @Test
    public void test10(){
        SmaliMethod sm = loader.getSmaliClass("LEnumWithConstructor;")
                .getMethod("greet()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Hello from EnumWithConstructor", o.getMirroringObject());
    }

}
