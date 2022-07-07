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
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class BinaryOperationInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception{
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/binary_math_test.smali");
        testSmaliClass = loader.getSmaliClass("Lbinary_math_test;");

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
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("addInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        r.set(4);
        r1.set(3);
        methodExecution.execute();
        Assert.assertEquals(7, r2.getIntValue());
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("addInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        r1.set(5);
        r2.set(6);
        methodExecution.execute();
        Assert.assertEquals(11, r2.getIntValue());
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("addIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(5);
        methodExecution.execute();
        Assert.assertEquals(20, r.getIntValue());
    }

    @Test
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("addIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(5);
        methodExecution.execute();
        Assert.assertEquals(260, r.getIntValue());
    }

    @Test
    public void test5() {
        SmaliMethod sm = testSmaliClass.getMethod("mulInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        r.set(5);
        r1.set(7);
        methodExecution.execute();
        Assert.assertEquals(35, r2.getIntValue());
    }

    @Test
    public void test6() {
        SmaliMethod sm = testSmaliClass.getMethod("mulInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-4);
        r1.set(9);
        methodExecution.execute();
        Assert.assertEquals(-36, r.getIntValue());
    }

    @Test
    public void test7() {
        SmaliMethod sm = testSmaliClass.getMethod("mulIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(6);
        methodExecution.execute();
        Assert.assertEquals(90, r.getIntValue());
    }


    @Test
    public void test8() {
        SmaliMethod sm = testSmaliClass.getMethod("mulIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(3);
        methodExecution.execute();
        Assert.assertEquals(765, r.getIntValue());
    }

    @Test
    public void test9() {
        SmaliMethod sm = testSmaliClass.getMethod("divInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(10);
        r1.set(3);
        methodExecution.execute();
        Assert.assertEquals(3, r.getIntValue());
    }

    @Test
    public void test10() {
        SmaliMethod sm = testSmaliClass.getMethod("divInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-6);
        r1.set(-2);
        methodExecution.execute();
        Assert.assertEquals(3, r.getIntValue());
    }

    @Test
    public void test11() {
        SmaliMethod sm = testSmaliClass.getMethod("divIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-15);
        methodExecution.execute();
        Assert.assertEquals(-1, r1.getIntValue());
    }


    @Test
    public void test12() {
        SmaliMethod sm = testSmaliClass.getMethod("divIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(765);
        methodExecution.execute();
        Assert.assertEquals(3, r1.getIntValue());
    }


    @Test
    public void test13() {
        SmaliMethod sm = testSmaliClass.getMethod("divIntZeroDivision()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(3);
        r1.set(0);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt result = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ArithmeticException;", result.getType() );
    }

    @Test
    public void test14() {
        SmaliMethod sm = testSmaliClass.getMethod("remInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(10);
        r1.set(3);
        methodExecution.execute();
        Assert.assertEquals(1,r.getIntValue());
    }

    @Test
    public void test15() {
        SmaliMethod sm = testSmaliClass.getMethod("remInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        int a = -3;
        int b = 4;
        r.set(a);
        r1.set(b);
        int expected = a - (a / b) * b;
        methodExecution.execute();
        Assert.assertEquals(expected,r.getIntValue());
    }

    @Test
    public void test16() {
        SmaliMethod sm = testSmaliClass.getMethod("remIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(18);
        methodExecution.execute();
        Assert.assertEquals(3,r.getIntValue());
    }

    @Test
    public void test17() {
        SmaliMethod sm = testSmaliClass.getMethod("remIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(255);
        methodExecution.execute();
        Assert.assertEquals(0,r.getIntValue());
    }

    @Test
    public void test18() {
        SmaliMethod sm = testSmaliClass.getMethod("andInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(12);
        r1.set(9);
        methodExecution.execute();
        Assert.assertEquals(8,r.getIntValue());
    }

    @Test
    public void test19() {
        SmaliMethod sm = testSmaliClass.getMethod("andInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-1);
        r1.set(9);
        methodExecution.execute();
        Assert.assertEquals(9,r.getIntValue());
    }

    @Test
    public void test20() {
        SmaliMethod sm = testSmaliClass.getMethod("andIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(15);
        methodExecution.execute();
        Assert.assertEquals(6,r.getIntValue());
    }

    @Test
    public void test21() {
        SmaliMethod sm = testSmaliClass.getMethod("andIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(-2);
        methodExecution.execute();
        Assert.assertEquals(254,r.getIntValue());
    }

    @Test
    public void test22() {
        SmaliMethod sm = testSmaliClass.getMethod("orInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-2);
        r1.set(9);
        methodExecution.execute();
        Assert.assertEquals(-1,r.getIntValue());
    }

    @Test
    public void test23() {
        SmaliMethod sm = testSmaliClass.getMethod("orInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-2);
        r1.set(9);
        methodExecution.execute();
        Assert.assertEquals(-1,r.getIntValue());
    }

    @Test
    public void test24() {
        SmaliMethod sm = testSmaliClass.getMethod("orIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        methodExecution.execute();
        Assert.assertEquals(15,r.getIntValue());
    }

    @Test
    public void test25() {
        SmaliMethod sm = testSmaliClass.getMethod("orIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        methodExecution.execute();
        Assert.assertEquals(254,r.getIntValue());
    }

    @Test
    public void test26() {
        SmaliMethod sm = testSmaliClass.getMethod("xorInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(9);
        r1.set(7);
        methodExecution.execute();
        Assert.assertEquals(9^7,r.getIntValue());
    }

    @Test
    public void test27() {
        SmaliMethod sm = testSmaliClass.getMethod("xorInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(10);
        r1.set(7);
        methodExecution.execute();
        Assert.assertEquals(10^7,r.getIntValue());
    }

    @Test
    public void test28() {
        SmaliMethod sm = testSmaliClass.getMethod("xorIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(10);
        methodExecution.execute();
        Assert.assertEquals(10^3,r.getIntValue());
    }

    @Test
    public void test29() {
        SmaliMethod sm = testSmaliClass.getMethod("xorIntLit16()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(45);
        methodExecution.execute();
        Assert.assertEquals(16^45,r.getIntValue());
    }

    @Test
    public void test30() {
        SmaliMethod sm = testSmaliClass.getMethod("shlInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(45);
        r1.set(3);
        methodExecution.execute();
        Assert.assertEquals(360,r.getIntValue());
    }


    @Test
    public void test31() {
        SmaliMethod sm = testSmaliClass.getMethod("shlInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(45);
        methodExecution.execute();
        Assert.assertEquals(45,r.getIntValue());
    }

    @Test
    public void test32() {
        SmaliMethod sm = testSmaliClass.getMethod("shlIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(45);
        methodExecution.execute();
        Assert.assertEquals(180,r.getIntValue());
    }

    @Test
    public void test33() {
        SmaliMethod sm = testSmaliClass.getMethod("shrInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(45);
        r1.set(1);
        methodExecution.execute();
        Assert.assertEquals(22,r.getIntValue());
    }

    @Test
    public void test34() {
        SmaliMethod sm = testSmaliClass.getMethod("shrInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(45);
        r1.set(2);
        methodExecution.execute();
        Assert.assertEquals(11,r.getIntValue());
    }

    @Test
    public void test35() {
        SmaliMethod sm = testSmaliClass.getMethod("shrIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(-45);
        methodExecution.execute();
        Assert.assertEquals(-12,r.getIntValue());
    }

    @Test
    public void test36() {
        SmaliMethod sm = testSmaliClass.getMethod("ushrInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-45);
        r1.set(1);
        methodExecution.execute();
        Assert.assertEquals(-45 >>> 1,r.getIntValue());
    }

    @Test
    public void test37() {
        SmaliMethod sm = testSmaliClass.getMethod("ushrInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(-60);
        r1.set(2);
        methodExecution.execute();
        Assert.assertEquals(-60 >>> 2,r.getIntValue());
    }

    @Test
    public void test38() {
        SmaliMethod sm = testSmaliClass.getMethod("ushrIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(50);
        methodExecution.execute();
        Assert.assertEquals(50 >>> 2,r.getIntValue());
    }

    @Test
    public void test39() {
        SmaliMethod sm = testSmaliClass.getMethod("subInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(50);
        r1.set(-4);
        methodExecution.execute();
        Assert.assertEquals(54,r.getIntValue());
    }

    @Test
    public void test40() {
        SmaliMethod sm = testSmaliClass.getMethod("subInt2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(50);
        r1.set(6);
        methodExecution.execute();
        Assert.assertEquals(44,r.getIntValue());
    }

    @Test
    public void test41() {
        SmaliMethod sm = testSmaliClass.getMethod("RSubInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(50);
        methodExecution.execute();
        Assert.assertEquals(205,r.getIntValue());
    }

    @Test
    public void test42() {
        SmaliMethod sm = testSmaliClass.getMethod("RSubIntLit8()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(20);
        methodExecution.execute();
        Assert.assertEquals(-5,r.getIntValue());
    }

    @Test
    public void test43() {
        SmaliMethod sm = testSmaliClass.getMethod("addLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MAX_VALUE);
        r2.set(Long.MIN_VALUE);
        methodExecution.execute();
        Assert.assertEquals(-1, r1.getLongValue());

    }

    @Test
    public void test44() {
        SmaliMethod sm = testSmaliClass.getMethod("addLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MAX_VALUE - 1043L);
        r2.set(1042L);
        methodExecution.execute();
        Assert.assertEquals(Long.MAX_VALUE - 1, r.getLongValue());
    }

    @Test
    public void test45() {
        SmaliMethod sm = testSmaliClass.getMethod("subLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MIN_VALUE);
        r2.set(1L);
        methodExecution.execute();
        Assert.assertEquals(Long.MAX_VALUE, r.getLongValue());
    }

    @Test
    public void test46() {
        SmaliMethod sm = testSmaliClass.getMethod("subLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MAX_VALUE);
        r2.set(-1L);
        methodExecution.execute();
        Assert.assertEquals(Long.MIN_VALUE, r.getLongValue());
    }

    @Test
    public void test47() {
        SmaliMethod sm = testSmaliClass.getMethod("mulLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set((long) Integer.MAX_VALUE);
        r2.set(3L);
        methodExecution.execute();
        Assert.assertEquals(6442450941L, r.getLongValue());
    }

    @Test
    public void test48() {
        SmaliMethod sm = testSmaliClass.getMethod("mulLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(1000L);
        r2.set(3L);
        methodExecution.execute();
        Assert.assertEquals(3000L, r.getLongValue());
    }

    @Test
    public void test49() {
        SmaliMethod sm = testSmaliClass.getMethod("divLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(500L);
        r2.set(-3L);
        methodExecution.execute();
        Assert.assertEquals(500L / -3L, r.getLongValue());
    }

    @Test
    public void test50() {
        SmaliMethod sm = testSmaliClass.getMethod("divLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(551L);
        r2.set(43L);
        methodExecution.execute();
        Assert.assertEquals(551L / 43L, r.getLongValue());
    }

    @Test
    public void test51() {
        SmaliMethod sm = testSmaliClass.getMethod("divLongZeroDivision()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(51L);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt result = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ArithmeticException;", result.getType() );
    }

    @Test
    public void test52() {
        SmaliMethod sm = testSmaliClass.getMethod("remLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(551L);
        r2.set(43L);
        methodExecution.execute();
        Assert.assertEquals(551L % 43L, r.getLongValue());
    }

    @Test
    public void test53() {
        SmaliMethod sm = testSmaliClass.getMethod("remLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MAX_VALUE);
        r2.set(-42L);
        methodExecution.execute();
        Assert.assertEquals(Long.MAX_VALUE % -42L, r.getLongValue());
    }

    @Test
    public void test54() {
        SmaliMethod sm = testSmaliClass.getMethod("andLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MIN_VALUE);
        r2.set(Long.MAX_VALUE);
        methodExecution.execute();
        Assert.assertEquals(0L, r.getLongValue());
    }

    @Test
    public void test55() {
        SmaliMethod sm = testSmaliClass.getMethod("andLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(-423434L);
        r2.set(Long.MAX_VALUE);
        methodExecution.execute();
        Assert.assertEquals(-423434L & Long.MAX_VALUE, r.getLongValue());
    }

    @Test
    public void test56() {
        SmaliMethod sm = testSmaliClass.getMethod("orLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(32323L);
        r2.set(Long.MIN_VALUE);
        methodExecution.execute();
        Assert.assertEquals(32323L | Long.MIN_VALUE, r.getLongValue());
    }

    @Test
    public void test57() {
        SmaliMethod sm = testSmaliClass.getMethod("orLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(32323L);
        r2.set(-2L);
        methodExecution.execute();
        Assert.assertEquals(32323L | -2L, r.getLongValue());

    }

    @Test
    public void test58() {
        SmaliMethod sm = testSmaliClass.getMethod("xorLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(32323L);
        r2.set(-2L);
        methodExecution.execute();
        Assert.assertEquals(32323L ^ -2L, r.getLongValue());
    }

    @Test
    public void test59() {
        SmaliMethod sm = testSmaliClass.getMethod("xorLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(8943L);
        r2.set(4345L);
        methodExecution.execute();
        Assert.assertEquals(8943L ^ 4345L, r.getLongValue());
    }

    @Test
    public void test60() {
        SmaliMethod sm = testSmaliClass.getMethod("shlLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(8877L);
        r2.set(2);
        methodExecution.execute();
        Assert.assertEquals(8877L << 2, r.getLongValue());
    }

    @Test
    public void test61() {
        SmaliMethod sm = testSmaliClass.getMethod("shlLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MIN_VALUE);
        r2.set(2);
        methodExecution.execute();
        Assert.assertEquals(Long.MIN_VALUE << 2, r.getLongValue());
    }


    @Test
    public void test62() {
        SmaliMethod sm = testSmaliClass.getMethod("shrLong()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MIN_VALUE);
        r2.set(2);
        methodExecution.execute();
        Assert.assertEquals(Long.MIN_VALUE / 4, r.getLongValue());
    }

    @Test
    public void test63() {
        SmaliMethod sm = testSmaliClass.getMethod("shrLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MAX_VALUE);
        r2.set(2);
        methodExecution.execute();
        Assert.assertEquals(Long.MAX_VALUE >> 2, r.getLongValue());
    }

    @Test
    public void test64() {
        SmaliMethod sm = testSmaliClass.getMethod("ushrLong2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(Long.MIN_VALUE);
        r2.set(2);
        methodExecution.execute();
        Assert.assertEquals(Long.MIN_VALUE >>> 2, r.getLongValue());
    }

    @Test
    public void test65() {
        SmaliMethod sm = testSmaliClass.getMethod("addFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(5.5F);
        r2.set(4.43F);
        methodExecution.execute();
        Assert.assertEquals(5.5F + 4.43F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test66() {
        SmaliMethod sm = testSmaliClass.getMethod("addFloat2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(5.003F);
        r2.set(4.43F);
        methodExecution.execute();
        Assert.assertEquals(5.003F + 4.43F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test67() {
        SmaliMethod sm = testSmaliClass.getMethod("subFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(5.003F);
        r2.set(4.43F);
        methodExecution.execute();
        Assert.assertEquals(5.003F - 4.43F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test68() {
        SmaliMethod sm = testSmaliClass.getMethod("subFloat2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(325.023F);
        r2.set(43.243F);
        methodExecution.execute();
        Assert.assertEquals(325.023F - 43.243F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test69() {
        SmaliMethod sm = testSmaliClass.getMethod("mulFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(43.54F);
        r2.set(12.01F);
        methodExecution.execute();
        Assert.assertEquals(43.54 * 12.01F, r.getFloatValue(),0.00001F);
    }

    @Test
    public void test70() {
        SmaliMethod sm = testSmaliClass.getMethod("mulFloat2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(0.001F);
        r2.set(1.03F);
        methodExecution.execute();
        Assert.assertEquals(0.001F * 1.03F, r.getFloatValue(),0.00001F);
    }

    @Test
    public void test71() {
        SmaliMethod sm = testSmaliClass.getMethod("divFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(4.001F);
        r2.set(1.03F);
        methodExecution.execute();
        Assert.assertEquals(4.001F / 1.03F, r.getFloatValue(),0.00001F);
    }

    @Test
    public void test72() {
        SmaliMethod sm = testSmaliClass.getMethod("divFloat2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(4.001F);
        r2.set(0.03F);
        methodExecution.execute();
        Assert.assertEquals(4.001F / 0.03F, r.getFloatValue(),0.00001F);
    }

    @Test
    public void test73() {
        SmaliMethod sm = testSmaliClass.getMethod("divFloatZeroDivision()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(4.03F);
        r2.set(0F);
        methodExecution.execute();
        Assert.assertEquals(Float.POSITIVE_INFINITY, r.getFloatValue(),0F);
    }

    @Test
    public void test74() {
        SmaliMethod sm = testSmaliClass.getMethod("remFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(4.03F);
        r2.set(0.44F);
        methodExecution.execute();
        Assert.assertEquals(4.03F % 0.44F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test75() {
        SmaliMethod sm = testSmaliClass.getMethod("remFloat2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(1);
        r.set(4.03F);
        r2.set(-0.44F);
        methodExecution.execute();
        Assert.assertEquals(4.03F % -0.44F, r.getFloatValue(),0.0F);
    }

    @Test
    public void test76() {
        SmaliMethod sm = testSmaliClass.getMethod("addDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(32.0000003D);
        r2.set(434.32000023D);
        methodExecution.execute();
        Assert.assertEquals(32.0000003D + 434.32000023D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test77() {
        SmaliMethod sm = testSmaliClass.getMethod("addDouble2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(-23.0000003D);
        r2.set(4.32000023D);
        methodExecution.execute();
        Assert.assertEquals(-23.0000003D + 4.32000023D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test78() {
        SmaliMethod sm = testSmaliClass.getMethod("subDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(943.000001213D);
        r2.set(323.043421323D);
        methodExecution.execute();
        Assert.assertEquals(943.000001213D - 323.043421323D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test79() {
        SmaliMethod sm = testSmaliClass.getMethod("subDouble2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(-943.000001213D);
        r2.set(-323.043421323D);
        methodExecution.execute();
        Assert.assertEquals(-943.000001213D + 323.043421323D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test80() {
        SmaliMethod sm = testSmaliClass.getMethod("mulDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(443.000001D);
        r2.set(878.90931D);
        methodExecution.execute();
        Assert.assertEquals(443.000001D * 878.90931D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test81() {
        SmaliMethod sm = testSmaliClass.getMethod("mulDouble2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(45.6768778D);
        r2.set(8.3435D);
        methodExecution.execute();
        Assert.assertEquals(45.6768778D * 8.3435D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test82() {
        SmaliMethod sm = testSmaliClass.getMethod("divDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(0.002323D);
        r2.set(1.4340004D);
        methodExecution.execute();
        Assert.assertEquals(0.002323D / 1.4340004D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test83() {
        SmaliMethod sm = testSmaliClass.getMethod("divDouble2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(0.002323D);
        r2.set(1.4340004D);
        methodExecution.execute();
        Assert.assertEquals(0.002323D / 1.4340004D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test84() {
        SmaliMethod sm = testSmaliClass.getMethod("divDoubleZeroDivision()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(-23.43434D);
        r2.set(0D);
        methodExecution.execute();
        Assert.assertEquals(Double.NEGATIVE_INFINITY, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test85() {
        SmaliMethod sm = testSmaliClass.getMethod("remDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(0.002323D);
        r2.set(1.4340004D);
        methodExecution.execute();
        Assert.assertEquals(0.002323D % 1.4340004D, r.getDoubleValue(), 0.0D);
    }

    @Test
    public void test86() {
        SmaliMethod sm = testSmaliClass.getMethod("remDouble2Addr()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r2 = methodExecution.getRegister(2);
        r.set(0.002323D);
        r2.set(1.4340004D);
        methodExecution.execute();
        Assert.assertEquals(0.002323D % 1.4340004D, r.getDoubleValue(), 0.0D);
    }
}