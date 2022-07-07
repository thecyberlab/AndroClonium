package usask.cyberlab.smalisimulator.simsmali.TestSuite_2_Register;


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

public class RegisterTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader clazzLoader;
    private static MethodExecution methodExecution;
    private static Register r;

    @BeforeClass
    public static void setUp() throws IOException {
        AssetCopier.copyFile("register_test.smali");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String basePath = context.getCacheDir().getAbsolutePath();
        clazzLoader = new ClazzLoader(basePath, context);
        testSmaliClass = clazzLoader.getSmaliClass("Lregister_test;");
        SmaliMethod sm = testSmaliClass.getMethod("test()V");
        methodExecution = new MethodExecution(sm , clazzLoader);
        r = new Register(0, methodExecution);
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        testSmaliClass = null;
        methodExecution = null;
        r = null;
    }


    @Test
    public void intTest() {
        r.set(0);
        assertTrue(r.containsNumericData());
        assertEquals(0,r.getIntValue());
        r.set(22);
        assertTrue(r.containsNumericData());
        assertEquals(22,r.getIntValue());
        r.set(-12);
        assertTrue(r.containsNumericData());
        assertEquals(-12,r.getIntValue());
        r.set(Integer.MAX_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Integer.MAX_VALUE,r.getIntValue());
        r.set(Integer.MIN_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Integer.MIN_VALUE,r.getIntValue());
    }

    @Test
    public void floatTest(){
        r.set(0f);
        assertTrue(r.containsNumericData());
        assertEquals(0f, r.getFloatValue(),0f);
        r.set(22.5f);
        assertTrue(r.containsNumericData());
        assertEquals(22.5f,r.getFloatValue(), 0f);
        r.set(-12.5f);
        assertTrue(r.containsNumericData());
        assertEquals(-12.5f,r.getFloatValue(),0f);
        r.set(3.33f);
        assertTrue(r.containsNumericData());
        assertEquals(3.33f,r.getFloatValue(),0f);
        r.set(0.003f);
        assertTrue(r.containsNumericData());
        assertEquals(0.003f,r.getFloatValue(),0f);
        r.set(-0.0007f);
        assertTrue(r.containsNumericData());
        assertEquals(-0.0007f,r.getFloatValue(),0f);
        r.set(Float.MAX_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Float.MAX_VALUE,r.getFloatValue(),0f);
        r.set(Float.MIN_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Float.MIN_VALUE,r.getFloatValue(),0f);
        r.set(Float.NaN);
        assertTrue(r.containsNumericData());
        assertEquals(Float.NaN,r.getFloatValue(),0f);
    }

    @Test
    public void longTest(){
        r.set(123L);
        assertTrue(r.containsNumericData());
        assertEquals(123L, r.getLongValue());
        r.set(-445L);
        assertTrue(r.containsNumericData());
        assertEquals(-445L, r.getLongValue());
        r.set(0L);
        assertTrue(r.containsNumericData());
        assertEquals(0L, r.getLongValue());
        r.set(Long.MAX_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Long.MAX_VALUE, r.getLongValue());
        r.set(Long.MIN_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Long.MIN_VALUE, r.getLongValue());
    }

    @Test
    public void doubleTest(){
        r.set(12D);
        assertTrue(r.containsNumericData());
        assertEquals(12D, r.getDoubleValue(), 0D);
        r.set(-23D);
        assertTrue(r.containsNumericData());
        assertEquals(-23D, r.getDoubleValue(), 0D);
        r.set(-34.4343D);
        assertTrue(r.containsNumericData());
        assertEquals(-34.4343D, r.getDoubleValue(), 0D);
        r.set(1.00000001D);
        assertTrue(r.containsNumericData());
        assertEquals(1.00000001D, r.getDoubleValue(), 0D);
        r.set(Double.MIN_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Double.MIN_VALUE, r.getDoubleValue(), 0D);
        r.set(Double.MAX_VALUE);
        assertTrue(r.containsNumericData());
        assertEquals(Double.MAX_VALUE, r.getDoubleValue(), 0D);
        r.set(Double.MIN_NORMAL);
        assertTrue(r.containsNumericData());
        assertEquals(Double.MIN_NORMAL, r.getDoubleValue(), 0D);
        r.set(Double.NaN);
        assertTrue(r.containsNumericData());
        assertEquals(Double.NaN, r.getDoubleValue(), 0D);
    }


    @Test
    public void objectTest() {
        Clazz clazz = clazzLoader.getClazz(testSmaliClass.getClassPath());
        Objekt so = new Objekt(clazz);
        r.set(so);
        assertTrue(r.containsRefToObject());
        String type = r.getReferencedObjectValue().getClazz().getClassPath();
        assertEquals("Lregister_test;", type);
    }

    @Test
    public void arrayTest() {
        Clazz clazz = clazzLoader.getClazz("[Ljava/lang/Integer;");
        ArrayObjekt array = new ArrayObjekt(clazz, new Integer[3]);
        r.set(array);
        assertTrue(r.containsRefToObject());
        AbstractObjekt o = r.getReferencedObjectValue();
        assertEquals(array, o);
    }

    @Test
    public void nullTest(){
        r.set(0);
        assertTrue(r.containsNumericData());
        assertTrue(r.containsRefToObject());
        assertEquals(0, r.getIntValue());
        assertNull(r.getReferencedObjectValue());
        r.set((AbstractObjekt) null);
        assertTrue(r.containsNumericData());
        assertTrue(r.containsRefToObject());
        assertEquals(0, r.getIntValue());
        assertNull(r.getReferencedObjectValue());
    }

    @Test
    public void mixTest(){
        Clazz clazz = clazzLoader.getClazz("[Ljava/lang/Integer;");
        ArrayObjekt sa = new ArrayObjekt(clazz, new Integer[3]);
        r.set(sa);
        assertTrue(r.containsRefToObject());
        assertEquals(sa, r.getReferencedObjectValue());

        r.set(-23D);
        assertTrue(r.containsNumericData());
        assertEquals(-23D, r.getDoubleValue(), 0D);

        r.set(123L);
        assertTrue(r.containsNumericData());
        assertEquals(123L, r.getLongValue());

        r.set(10);
        assertTrue(r.containsNumericData());
        assertEquals(10,r.getIntValue());

        r.set(-445L);
        assertTrue(r.containsNumericData());
        assertEquals(-445L, r.getLongValue());
    }


}