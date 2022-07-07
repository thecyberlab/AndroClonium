package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

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
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ConstructorCallWithExceptionTest {

    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception{
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath(), context);
        AssetCopier.copyFile("ClassWithConstructorException.smali");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LClassWithConstructorException;");
        SmaliMethod sm = clazz.getSmaliMethod("test()LClassWithConstructorException;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        IllegalStateException e = (IllegalStateException) o.getMirroringObject();
        Assert.assertEquals("Exception in my constructor!!", e.getMessage());
        Assert.assertNull(e.getCause());
    }

    @Test
    public void test2() {
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LClassWithConstructorException;");
        SmaliMethod sm = clazz.getSmaliMethod("test2()LClassWithConstructorException;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        ArithmeticException e = (ArithmeticException) o.getMirroringObject();
        Assert.assertNull(e.getCause());
    }

}
