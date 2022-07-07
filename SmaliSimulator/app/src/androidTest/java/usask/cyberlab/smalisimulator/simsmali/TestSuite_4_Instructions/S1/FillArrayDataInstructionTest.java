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
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;

import static org.junit.Assert.*;

public class FillArrayDataInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/fill_array_data_test.smali");
        testSmaliClass = loader.getSmaliClass("Lfill_array_data_test;");
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
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataInt()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt ao = new ArrayObjekt(clazz, new int[5]);
        r.set(ao);
        methodExecution.execute();
        ArrayObjekt ao1 = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(1, ao1.getValue(0));
        assertEquals(2, ao1.getValue(1));
        assertEquals(3, ao1.getValue(2));
        assertEquals(4, ao1.getValue(3));
        assertEquals(5, ao1.getValue(4));

    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataLongWithLongs()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[J");
        r.set(new ArrayObjekt(clazz, new long[3]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(68719476736L,sa.getValue(0));
        assertEquals(137438953472L,sa.getValue(1));
        assertEquals(3L,sa.getValue(2));
    }


    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataLongWithInts()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[J");
        r.set(new ArrayObjekt(clazz, new long[3]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(16L,sa.getValue(0));
        assertEquals(32L,sa.getValue(1));
        assertEquals(48L,sa.getValue(2));
    }

    @Test
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataBoolean()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Z");
        r.set(new ArrayObjekt(clazz, new boolean[4]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(true,sa.getValue(0));
        assertEquals(true,sa.getValue(1));
        assertEquals(false,sa.getValue(2));
        assertEquals(true,sa.getValue(3));
    }

    @Test
    public void test5() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataByte()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[B");
        r.set(new ArrayObjekt(clazz, new byte[4]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals((byte)10,sa.getValue(0));
        assertEquals((byte)11,sa.getValue(1));
        assertEquals((byte)12,sa.getValue(2));
        assertEquals((byte)13,sa.getValue(3));
    }

    @Test
    public void test6() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataChar()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[C");
        r.set(new ArrayObjekt(clazz, new char[3]));
        methodExecution.execute();
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals('a',sa.getValue(0));
        assertEquals('b',sa.getValue(1));
        assertEquals('c',sa.getValue(2));
    }

    @Test
    public void test7() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataShortWithShorts()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[S");
        r.set(new ArrayObjekt(clazz, new short[3]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals((short)100,sa.getValue(0));
        assertEquals((short)200,sa.getValue(1));
        assertEquals((short)5,sa.getValue(2));
    }

    @Test
    public void test8() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataShortWithInts()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[S");
        r.set(new ArrayObjekt(clazz, new short[3]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals((short)16,sa.getValue(0));
        assertEquals((short)32,sa.getValue(1));
        assertEquals((short)48,sa.getValue(2));
    }

    @Test
    public void test9() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataFloat()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[F");
        r.set(new ArrayObjekt(clazz, new float[2]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(1.0f,sa.getValue(0));
        assertEquals(2.0f,sa.getValue(1));
    }

    @Test
    public void test10() {
        SmaliMethod sm = testSmaliClass.getMethod("fillArrayDataDouble()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[D");
        r.set(new ArrayObjekt(clazz, new double[2]));
        methodExecution.execute();
        ArrayObjekt sa = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(0.1D,sa.getValue(0));
        assertEquals(1.5D,sa.getValue(1));
    }
}