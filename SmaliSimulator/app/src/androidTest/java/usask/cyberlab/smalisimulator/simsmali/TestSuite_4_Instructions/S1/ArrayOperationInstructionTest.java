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
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

public class ArrayOperationInstructionTest {
    private static SmaliClass getTestSmaliClass;
    private static SmaliClass putTestSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/aget_test.smali");
        AssetCopier.copyFile("instructions/aput_test.smali");

        getTestSmaliClass = loader.getSmaliClass("Laget_test;");
        putTestSmaliClass = loader.getSmaliClass("Laput_test;");

    }

    public static void afterClass(){
        loader = null;
        getTestSmaliClass = null;
        putTestSmaliClass = null;
    }

//    @Test
//    public void _(){
//        loader.getClazz(getTestSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//        loader.getClazz(putTestSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void agetUninitializedInt(){
        SmaliMethod sm = getTestSmaliClass.getMethod("get()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt sa = new ArrayObjekt(clazz, new int[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0, r0.getIntValue());
    }

    @Test
    public void agetInt(){
        SmaliMethod sm = getTestSmaliClass.getMethod("get()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt reflectedArrayObjekt = new ArrayObjekt(clazz, new int[3]);
        reflectedArrayObjekt.setValue(0,10);
        reflectedArrayObjekt.setValue(1,20);
        reflectedArrayObjekt.setValue(2,30);
        r0.set(reflectedArrayObjekt);
        r1.set(1);
        methodExecution.execute();
        assertEquals(20, r0.getIntValue());
    }

    @Test
    public void agetUninitializedFloat(){
        SmaliMethod sm = getTestSmaliClass.getMethod("get()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[F");
        ArrayObjekt arrayObjekt = new ArrayObjekt(clazz, new float[3]);
        r0.set(arrayObjekt);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0F, r0.getFloatValue(), 0F);
    }

    @Test
    public void agetFloat(){
        SmaliMethod sm = getTestSmaliClass.getMethod("get()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[F");
        float[] array = new float[3];
        array[1] = Float.MAX_VALUE;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(Float.MAX_VALUE, r0.getFloatValue(), 0F);
    }

    @Test
    public void agetUninitializedLong(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[J");
        ArrayObjekt sa = new ArrayObjekt(clazz, new long[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0L, r0.getLongValue());
    }

    @Test
    public void agetLong(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[J");
        long[] array = new long[3];
        array[1] = Long.MAX_VALUE;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(Long.MAX_VALUE, r0.getLongValue());
    }

    @Test
    public void agetUninitializedDouble(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[D");
        ArrayObjekt sa = new ArrayObjekt(clazz, new double[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0D, r0.getDoubleValue(), 0D);
    }

    @Test
    public void agetDouble(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[D");
        double[] array = new double[3];
        array[1] = Double.MAX_VALUE;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(Double.MAX_VALUE, r0.getDoubleValue(), 0D);
    }

    @Test
    public void agetUninitializedByte(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getByte()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[B");
        ArrayObjekt sa = new ArrayObjekt(clazz, new byte[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0, r0.getByteValue());
    }

    @Test
    public void agetByte(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getByte()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[B");
        byte[] array = new byte[3];
        array[1] = 20;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(20, r0.getByteValue());
    }

    @Test
    public void agetUninitializedChar(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getChar()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[C");
        ArrayObjekt sa = new ArrayObjekt(clazz, new char[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0, r0.getCharValue());
    }

    @Test
    public void agetChar(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getChar()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[C");
        char[] array = new char[3];
        array[1] = 'b';
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals('b', r0.getCharValue());
    }

    @Test
    public void agetUninitializedBoolean(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getBoolean()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Z");
        ArrayObjekt sa = new ArrayObjekt(clazz, new boolean[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertFalse(r0.getBooleanValue());
    }

    @Test
    public void agetBoolean(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getBoolean()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Z");
        boolean[] array = new boolean[3];
        array[1] = true;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertTrue(r0.getBooleanValue());
    }

    @Test
    public void agetUninitializedShort(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getShort()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[S");
        ArrayObjekt sa = new ArrayObjekt(clazz, new short[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(0, r0.getShortValue());
    }

    @Test
    public void agetShort(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getShort()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[S");
        short[] array = new short[3];
        array[1] = 20;
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals(20, r0.getShortValue());
    }

    @Test
    public void agetUninitializedObject(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        ArrayObjekt sa = new ArrayObjekt(clazz, new Object[3]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertNull(r0.getReferencedObjectValue());
    }

    @Test
    public void agetObject(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        Object[] array = new Object[3];
        array[1] = new Object();
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertEquals("Ljava/lang/Object;", r0.getReferencedObjectValue().getClazz().getClassPath());
    }

    @Test
    public void agetUninitializedArrayObject(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[Ljava/lang/Object;");
        ArrayObjekt sa = new ArrayObjekt(clazz, new Object[3][]);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        assertNull(r0.getReferencedObjectValue());
    }

    @Test
    public void agetArrayObject(){
        SmaliMethod sm = getTestSmaliClass.getMethod("getObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[Ljava/lang/Object;");
        ReflectedClazz clazz2 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        Object[][] array = new Object[3][];
        array[1] = new Object[2];
        ArrayObjekt sa = new ArrayObjekt(clazz, array);
        ArrayObjekt sa2 = new ArrayObjekt(clazz2, array[1]);
        sa.setValue(1,sa2);
        r0.set(sa);
        r1.set(1);
        methodExecution.execute();
        ArrayObjekt resArray = (ArrayObjekt) r0.getReferencedObjectValue();
        assertEquals(sa2, resArray);
    }

    @Test
    public void aputInt(){
        SmaliMethod sm = putTestSmaliClass.getMethod("put()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt sa = new ArrayObjekt(clazz, new int[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(20);
        methodExecution.execute();
        assertEquals(20,sa.getValue(1));
    }

    @Test
    public void aputFloat(){
        SmaliMethod sm = putTestSmaliClass.getMethod("put()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[F");
        ArrayObjekt sa = new ArrayObjekt(clazz, new float[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(20.05F);
        methodExecution.execute();
        assertEquals(20.05F,(Float) sa.getValue(1), 0f);
    }

    @Test
    public void aputLong(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[J");
        ArrayObjekt sa = new ArrayObjekt(clazz, new long[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(20000000000L);
        methodExecution.execute();
        long l = (Long) sa.getValue(1);
        assertEquals(20000000000L,l);
    }

    @Test
    public void aputDouble(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putWide()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[D");
        ArrayObjekt sa = new ArrayObjekt(clazz, new double[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(20.00005D);
        methodExecution.execute();
        assertEquals(20.00005D,(Double) sa.getValue(1), 0d);
    }

    @Test
    public void aputBoolean(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putBoolean()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Z");
        ArrayObjekt sa = new ArrayObjekt(clazz, new boolean[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(true);
        methodExecution.execute();
        assertEquals(true,sa.getValue(1));
    }

    @Test
    public void aputChar(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putChar()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[C");
        ArrayObjekt sa = new ArrayObjekt(clazz, new char[3]);
        r0.set(sa);
        r1.set(1);
        r2.set('r');
        methodExecution.execute();
        assertEquals('r',sa.getValue(1));
    }

    @Test
    public void aputByte(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putByte()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[B");
        ArrayObjekt sa = new ArrayObjekt(clazz, new byte[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(333);
        methodExecution.execute();
        assertEquals((byte)77,sa.getValue(1));
    }

    @Test
    public void aputShort(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putShort()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[S");
        ArrayObjekt sa = new ArrayObjekt(clazz, new short[3]);
        r0.set(sa);
        r1.set(1);
        r2.set(434);
        methodExecution.execute();
        assertEquals((short) 434,sa.getValue(1));
    }

    @Test
    public void aputObject(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        ArrayObjekt sa = new ArrayObjekt(clazz, new Object[3]);
        ReflectedClazz clazz2 = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
        Objekt so = new Objekt(clazz2, new Object());
        r0.set(sa);
        r1.set(1);
        r2.set(so);
        methodExecution.execute();
        Objekt result = (Objekt) sa.getValue(1);
        assertEquals(so, result);
    }

    @Test
    public void aputArrayObject(){
        SmaliMethod sm = putTestSmaliClass.getMethod("putObject()V");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[Ljava/lang/Object;");
        ReflectedClazz clazz2 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        ArrayObjekt sa = new ArrayObjekt(clazz, new Object[3][]);
        ArrayObjekt sa2 = new ArrayObjekt(clazz2, new Object[2]);
        r0.set(sa);
        r1.set(1);
        r2.set(sa2);
        methodExecution.execute();
        ArrayObjekt res = (ArrayObjekt) sa.getValue(1);
        assertTrue(res.equals(sa2));
    }

}