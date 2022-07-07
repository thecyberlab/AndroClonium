package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class ThrowInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/throw_test.smali");
        testSmaliClass = loader.getSmaliClass("Lthrow_test;");
    }


    public static void afterClass(){
        testSmaliClass = null;
        loader = null;
    }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("throwTest()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register register = methodExecution.getRegister(0);
        ReflectedClazz sc = (ReflectedClazz) loader.getClazz("Ljava/lang/UnsupportedOperationException;");
        Objekt reflectedObjekt = new Objekt(sc, new UnsupportedOperationException());
        register.set(reflectedObjekt);
        methodExecution.execute();
        Assert.assertEquals(ResultType.EXCEPTION,methodExecution.getExecutionResult().getType());
        Objekt resObjekt = (Objekt) methodExecution.getExecutionResult().getResult();
        Assert.assertEquals(reflectedObjekt, resObjekt);
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("throwCatchTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register register = methodExecution.getRegister(0);
        ReflectedClazz sc = (ReflectedClazz) loader.getClazz("Ljava/lang/UnsupportedOperationException;");
        UnsupportedOperationException e = new UnsupportedOperationException();
        Objekt reflectedObjekt = new Objekt(sc, e);
        register.set(reflectedObjekt);
        methodExecution.execute();
        Assert.assertEquals(ResultType.OBJECT,methodExecution.getExecutionResult().getType());
        Objekt resObjekt = (Objekt) methodExecution.getExecutionResult().getResult();
        Assert.assertEquals(reflectedObjekt, resObjekt);

    }
}