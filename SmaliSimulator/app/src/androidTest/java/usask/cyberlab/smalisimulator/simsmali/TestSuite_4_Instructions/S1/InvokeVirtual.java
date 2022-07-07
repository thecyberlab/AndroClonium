package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

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

public class InvokeVirtual {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeVirtual.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeVirtual;");
    }

    public static void afterClass(){
        testSmaliClass = null;
        loader = null;
    }


    @Test
    public void test1() {
        Clazz sc = loader.getClazz(testSmaliClass.getClassPath());
        loader.addTypeToInstanceSafeClasses(sc);
        loader.addTypeToInstanceSafeClasses(loader.getClazz("Ljava/util/Random;"));
        SmaliMethod sm = testSmaliClass.getMethod("test()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) sc.getStaticFieldValue("testField");
        Assert.assertEquals(i, mr.getResult());
        loader.removeTypeFromInstanceSafeClasses(sc.getClassPath());
        loader.removeTypeFromInstanceSafeClasses("Ljava/util/Random;");
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("test2()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION ,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/IndexOutOfBoundsException;", objekt.getType());
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("test3()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION ,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/Exception;", objekt.getType());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("test4()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT ,mr.getType());
        Assert.assertEquals(null, mr.getResult());
    }

   @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("printSomething()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
    }

}
