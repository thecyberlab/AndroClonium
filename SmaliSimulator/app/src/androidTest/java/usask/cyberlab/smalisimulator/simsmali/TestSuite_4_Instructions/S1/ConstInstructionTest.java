package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

public class ConstInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/const_test.smali");
        testSmaliClass = loader.getSmaliClass("Lconst_test;");
    }

    @AfterClass
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
    public void testConst4() {
        SmaliMethod sm = testSmaliClass.getMethod("const4_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-3,r.getIntValue());
    }

    @Test
    public void testConst16() {
        SmaliMethod sm = testSmaliClass.getMethod("const16_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(16962,r.getIntValue());
    }

    @Test
    public void testConst16Negative() {
        SmaliMethod sm = testSmaliClass.getMethod("const16Negative_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-15,r.getIntValue());
    }

    @Test
    public void testConst() {
        SmaliMethod sm = testSmaliClass.getMethod("const_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(1111638594,r.getIntValue());
    }

    @Test
    public void testConstHigh16() {
        SmaliMethod sm = testSmaliClass.getMethod("constHigh16_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(1111621632,r.getIntValue());
    }

    @Test
    public void testConstWide16() {
        SmaliMethod sm = testSmaliClass.getMethod("constWide16_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r1.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(16962L,r1.getLongValue());

    }

    @Test
    public void testConstWide32() {
        SmaliMethod sm = testSmaliClass.getMethod("constWide32_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r1.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(1800000L,r1.getLongValue());
    }

    @Test
    public void testConstWide() {
        SmaliMethod sm = testSmaliClass.getMethod("constWide_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r1.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4774451407313060418L, r1.getLongValue());
    }

    @Test
    public void testConstWideHigh16() {
        SmaliMethod sm = testSmaliClass.getMethod("constWideHigh16_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r1.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4774378554966147072L, r1.getLongValue());
    }

    @Test
    public void testConstString() {
        SmaliMethod sm = testSmaliClass.getMethod("constString_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        assertTrue(r1.containsRefToObject());
        Objekt so = (Objekt) r1.getReferencedObjectValue();
        assertEquals("When I need to identify rebels, I look for men with principles.",so.toString());
    }

    @Test
    public void testConstStringUTF() {
        SmaliMethod sm = testSmaliClass.getMethod("constString_UTF_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        assertTrue(r1.containsRefToObject());
        Objekt so = (Objekt) r1.getReferencedObjectValue();
        assertEquals("سلام",so.toString());
    }

    @Test
    public void testConstStringJumbo() {
        SmaliMethod sm = testSmaliClass.getMethod("constStringJumbo_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        assertTrue(r1.containsRefToObject());
        Objekt so = (Objekt) r1.getReferencedObjectValue();
        assertEquals("When I need to identify JUMBO rebels, I look for JUMBO men with JUMBO principles.",so.toString());
    }

    @Test
    public void testConstClassSelf() {
        SmaliMethod sm = testSmaliClass.getMethod("constClassLocal_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Objekt klass = (Objekt) r1.getReferencedObjectValue();
        Class cls = (Class) klass.getMirroringObject();
        String clsType = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        assertEquals(testSmaliClass.getClassPath(), clsType );
    }

    @Test
    public void testConstClassNotLoaded() {
        SmaliMethod sm = testSmaliClass.getMethod("constClassRemote_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Objekt objekt = (Objekt) r1.getReferencedObjectValue();
        Class cls = (Class) objekt.getMirroringObject();
        String clsType = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        assertEquals("Ljava/util/HashMap;", clsType);
    }

    @Test(expected = SmaliSimulationException.class)
    public void testConstClassBadClass() {
        SimSmaliConfig.onClassLoadErrorUseAmbiguousValue = false;
        SmaliMethod sm = testSmaliClass.getMethod("constClassBadClass_test()Ljava/lang/Class;");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
    }

    @Test
    public void testConstClassBadClass2() {
        SimSmaliConfig.onClassLoadErrorUseAmbiguousValue = true;
        SmaliMethod sm = testSmaliClass.getMethod("constClassBadClass_test()Ljava/lang/Class;");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Ljava/lang/Class;", av.getType());
    }

    @Test
    public void testConstClassPrimitive() {
        SmaliMethod sm = testSmaliClass.getMethod("constClassPrimitive_test()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r1 = methodExecution.getRegister(0);
        Objekt klassObjekt = (Objekt) r1.getReferencedObjectValue();
        Class cls = (Class) klassObjekt.getMirroringObject();
        String clsType = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        assertEquals("I", clsType);
    }

}