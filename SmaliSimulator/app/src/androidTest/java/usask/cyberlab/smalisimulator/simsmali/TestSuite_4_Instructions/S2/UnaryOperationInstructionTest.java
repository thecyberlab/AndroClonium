package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

import static org.junit.Assert.*;

public class UnaryOperationInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);

        AssetCopier.copyFile("instructions/unary_operation_test.smali");
        testSmaliClass = loader.getSmaliClass("Lunary_operation_test;");

    }

    public static void AfterClass() throws Exception{
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
        SmaliMethod sm = testSmaliClass.getMethod("negInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-6,r.getIntValue());
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("notInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-7,r.getIntValue());
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("negLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-65280L,r.getLongValue());
    }

    @Test(expected = SmaliSimulationException.class)
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("negLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        // this instruction throws an exception since the register should be in numeric state
        // and not refer to object
        assertEquals("should raise an exception",r.getReferencedObjectValue());
    }

    @Test
    public void test5() {
        SmaliMethod sm = testSmaliClass.getMethod("notLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-65281L,r.getLongValue());
    }


    @Test
    public void test6() {
        SmaliMethod sm = testSmaliClass.getMethod("negFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-2.5f,r.getFloatValue(), 0.0f);
    }


    @Test
    public void test7() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("negDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-5.0000003,r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test8() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("negDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(-5.0000003,r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test9() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(65280L,r.getLongValue());
    }

    @Test
    public void test10() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(1075838976,r.getIntValue());
        assertEquals(1075838976f,r2.getFloatValue(),0f);
    }

    @Test
    public void test11() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(1075838976,r.getIntValue());
        assertEquals(1075838976D,r2.getDoubleValue(),0d);
    }


    @Test
    public void test12() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("longToInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(983040,r.getIntValue());
    }


    @Test
    public void test13() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("longToFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(1075838976f,r.getFloatValue(),0f);
    }

    @Test
    public void test14() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("longToDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(1075838976d,r.getDoubleValue(), 0d);
    }


    @Test
    public void test15() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("floatToInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(2,r.getIntValue());
    }

    @Test
    public void test16() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("floatToLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(2L,r.getLongValue());
    }

    @Test
    public void test17() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("floatToDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(2.5D,r.getDoubleValue(),0D);
    }

    @Test
    public void test18() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("doubleToInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(5,r.getIntValue());
    }

    @Test
    public void test19() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("doubleToLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(5L,r.getLongValue());
    }

    @Test
    public void test20() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("doubleToFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(5.0000005f,r.getFloatValue(), 0.0D);
    }

    @Test
    public void test21() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToByte()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(1,r.getIntValue());
    }

    @Test
    public void test22() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToChar()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsNumericData());
        assertEquals(65535,r.getIntValue());
    }

    @Test
    public void test23() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("intToShort()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertEquals(-1,r.getIntValue());
        assertEquals(65535,r1.getIntValue());
    }
}