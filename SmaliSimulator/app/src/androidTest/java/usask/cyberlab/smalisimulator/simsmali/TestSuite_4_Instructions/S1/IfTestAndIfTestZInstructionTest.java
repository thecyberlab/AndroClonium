package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IfTestAndIfTestZInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/if_test_and_if_testZ.smali");
        testSmaliClass = loader.getSmaliClass("Lif_test_and_if_testZ;");
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
    public void testIfEqual1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifEqual1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfEqual2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifEqual2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }


    @Test
    public void testIfNotEqual1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifNotEqual1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfNotEqual2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifNotEqual2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfLessThan1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThan1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(5,r.getIntValue());
    }

    @Test
    public void testIfLessThan2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThan2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(5,r.getIntValue());
    }

    @Test
    public void testIfLessThan3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThan3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    @Test
    public void testIfLessOrEqual1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqual1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfLessOrEqual2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqual2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(5,r.getIntValue());

    }

    @Test
    public void testIfLessOrEqual3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqual3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqual1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqual1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqual2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqual2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqual3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqual3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(7,r.getIntValue());
    }

    @Test
    public void testIfGreaterThan1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThan1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(7,r.getIntValue());
    }

    @Test
    public void testIfGreaterThan2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThan2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfGreaterThan3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThan3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    // -------------------------------------

    @Test
    public void testIfEqualZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifEqualZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfEqualZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifEqualZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfNotEqualZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifNotEqualZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfNotEqualZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifNotEqualZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    @Test
    public void testIfLessThanZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThanZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfLessThanZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThanZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }


    @Test
    public void testIfLessThanZero3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessThanZero3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(-4,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqualZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqualZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqualZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqualZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    @Test
    public void testIfGreaterOrEqualZero3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterOrEqualZero3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfGreaterThanZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThanZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfGreaterThanZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThanZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(3,r.getIntValue());
    }

    @Test
    public void testIfGreaterThanZero3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifGreaterThanZero3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfLessOrEqualZero1() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqualZero1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(0,r.getIntValue());
    }

    @Test
    public void testIfLessOrEqualZero2() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqualZero2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(4,r.getIntValue());
    }

    @Test
    public void testIfLessOrEqualZero3() {
        SmaliMethod sm = testSmaliClass.getMethod("ifLessOrEqualZero3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertTrue(r2.containsNumericData());
        assertEquals(-2,r.getIntValue());
    }

    @Test
    public void nullCheckTest(){
        SmaliMethod sm = testSmaliClass.getMethod("nullCheckObject()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Objekt o = new Objekt(loader.getClazz("Ljava/lang/Object;"), new Object());
        methodExecution.getRegister(0).set(o);
        methodExecution.execute();
        assertEquals(0, methodExecution.getExecutionResult().getResult());
    }

    @Test
    public void notNullCheckTest(){
        SmaliMethod sm = testSmaliClass.getMethod("notNullCheckObject()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Objekt o = new Objekt(loader.getClazz("Ljava/lang/Object;"), new Object());
        methodExecution.getRegister(0).set(o);
        methodExecution.execute();
        assertEquals(1, methodExecution.getExecutionResult().getResult());
    }

}
