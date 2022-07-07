package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.assertEquals;


public class StaticOperationInstructionTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/sget_test.smali");
        AssetCopier.copyFile("instructions/sput_test.smali");
        testSmaliClass = loader.getSmaliClass("Lsget_test;");
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
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0, r.getIntValue());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticIntLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(66, r.getIntValue());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0L, r.getLongValue());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticLongLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(68719476735L, r.getLongValue());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticString()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(null, r.getReferencedObjectValue());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticStringLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Objekt ro = (Objekt) r.getReferencedObjectValue();
        assertEquals("life, what's life?", ro.toString());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticBoolean()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticBooleanLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(1,r.getIntValue());
    }

    @Test
    public void test9(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticByte()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void test10(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticByteLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(15,r.getIntValue());
    }

    @Test
    public void test11(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticChar()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void test12(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticCharLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals('x',(char) r.getIntValue());
    }

    @Test
    public void test13(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticShort()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0, r.getIntValue());
    }

    @Test
    public void test14(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticShortLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(256, r.getIntValue());
    }


    @Test
    public void test15(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0D, r.getDoubleValue(),0D);
    }


    @Test
    public void test16(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticDoubleLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(1.0E10, r.getDoubleValue(),0D);
    }

    @Test
    public void test17(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(0f, r.getFloatValue(),0f);
    }

    @Test
    public void test18(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticFloatLiteral()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(1.1f, r.getFloatValue(),0f);
    }

    @Test
    public void test19(){
        SmaliMethod sm = testSmaliClass.getMethod("getStaticWhitelistedClassField()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertEquals(Integer.MAX_VALUE, r.getIntValue());
    }

    @Test(expected = SmaliSimulationException.class)
    public void test20() throws Exception{
        SmaliMethod sm = testSmaliClass.getMethod("getStaticUnknownClassField()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
    }
}