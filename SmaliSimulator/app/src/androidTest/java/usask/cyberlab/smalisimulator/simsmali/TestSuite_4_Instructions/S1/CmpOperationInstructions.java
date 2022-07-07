package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

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

public class CmpOperationInstructions {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/cmp_test.smali");
        testSmaliClass = loader.getSmaliClass("Lcmp_test;");
    }

    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void cmplFloatTest1() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmplFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r0.set(0.33f);
        r1.set(-0.33f);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(0.33f);
        r1.set(4.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(0.33f);
        r1.set(0.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(0,r0.getIntValue());
    }

    @Test
    public void cmplFloatTest2() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmplFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r0.set(0.33f);
        r1.set(Float.NaN);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(Float.NaN);
        r1.set(4.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(Float.NaN);
        r1.set(Float.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
    }

    @Test
    public void cmpgFloatTest1() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmpgFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r0.set(0.33f);
        r1.set(-0.33f);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(0.33f);
        r1.set(4.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(0.33f);
        r1.set(0.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(0,r0.getIntValue());
    }

    @Test
    public void cmpgFloatTest2() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmpgFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r0.set(0.33f);
        r1.set(Float.NaN);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(Float.NaN);
        r1.set(4.33f);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(Float.NaN);
        r1.set(Float.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
    }

    @Test
    public void cmplDoubleTest1() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmplDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r0.set(0.33);
        r2.set(-0.33);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(4.33d);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(0.33d);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(0,r0.getIntValue());
    }

    @Test
    public void cmplDoubleTest2() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmplDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r0.set(Double.NaN);
        r2.set(-0.33);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(Double.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(Double.NaN);
        r2.set(Double.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
    }

    @Test
    public void cmpgDoubleTest1() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmplDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r0.set(0.33);
        r2.set(-0.33);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(4.33d);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(0.33d);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(0,r0.getIntValue());
    }

    @Test
    public void cmpgDoubleTest2() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmpgDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r0.set(Double.NaN);
        r2.set(-0.33);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(0.33d);
        r2.set(Double.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(Double.NaN);
        r2.set(Double.NaN);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
    }


    @Test
    public void cmpLongTest() throws Exception {
        SmaliMethod sm = testSmaliClass.getMethod("cmpLong()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r0.set(3123123L);
        r2.set(324234L);
        methodExecution.execute();
        Assert.assertEquals(1,r0.getIntValue());
        r0.set(4234234234L);
        r2.set(Long.MAX_VALUE);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(-1,r0.getIntValue());
        r0.set(43434343L);
        r2.set(43434343L);
        methodExecution.setFinished(false);
        methodExecution.setInstructionPointer(0);
        methodExecution.execute();
        Assert.assertEquals(0,r0.getIntValue());
    }

}
