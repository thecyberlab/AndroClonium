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
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

import java.lang.reflect.Array;

public class InstanceOfInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/instanceof_test.smali");
        testSmaliClass = loader.getSmaliClass("Linstanceof_test;");
    }

    @AfterClass
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
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObject()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
        r.set(new Objekt(clazz, new Object()));
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObject()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        r.set(43);
        methodExecution.execute();
        assertEquals(0,r1.getIntValue());
    }

    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfString()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt objekt = new Objekt(clazz, "amigo");
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfString()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new String[3]);
        Register r = methodExecution.getRegister(0);
        r.set(objekt);
        Register r1 = methodExecution.getRegister(1);
        methodExecution.execute();
        assertEquals(0,r1.getIntValue());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfStringArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new String[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new String[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObject()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new String[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfString()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new String[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(0,r1.getIntValue());
    }

    @Test
    public void test9(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfIntArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new int[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test10(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObject()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new int[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test11(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new int[3]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(0,r1.getIntValue());
    }


    @Test
    public void test12(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOf2DObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[Ljava/lang/Object;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new Object[3][]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test13(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOf2DObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[[Ljava/lang/Object;");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new Object[3][][]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test14(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOf2DObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[[[I");
        ArrayObjekt objekt = new ArrayObjekt(clazz, new int[3][][]);
        r.set(objekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test15(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOf2DSelfArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        SmaliArrayClazz clazz = (SmaliArrayClazz) loader.getClazz("[[Linstanceof_test;");
        Class baseTypeClass = clazz.getMirroringClass().getComponentType().getComponentType();
        Object[] o = (Object[]) Array.newInstance(baseTypeClass, 3, 4);
        ArrayObjekt arrayObjekt = new ArrayObjekt(clazz, o);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test16(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObject()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        SmaliArrayClazz clazz = (SmaliArrayClazz) loader.getClazz("[[Linstanceof_test;");
        Class baseTypeClass = clazz.getMirroringClass().getComponentType().getComponentType();
        Object[] o = (Object[]) Array.newInstance(baseTypeClass, 3, 4);
        ArrayObjekt arrayObjekt = new ArrayObjekt(clazz, o);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test17(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfObjectArray()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        SmaliArrayClazz clazz = (SmaliArrayClazz) loader.getClazz("[[Linstanceof_test;");
        Class baseTypeClass = clazz.getMirroringClass().getComponentType().getComponentType();
        Object[] o = (Object[]) Array.newInstance(baseTypeClass, 3, 4);
        ArrayObjekt arrayObjekt = new ArrayObjekt(clazz, o);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(1,r1.getIntValue());
    }

    @Test
    public void test18(){
        SmaliMethod sm = testSmaliClass.getMethod("instanceOfString()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        SmaliArrayClazz clazz = (SmaliArrayClazz) loader.getClazz("[[Linstanceof_test;");
        Class baseTypeClass = clazz.getMirroringClass().getComponentType().getComponentType();
        Object[] o = (Object[]) Array.newInstance(baseTypeClass, 3, 4);
        ArrayObjekt arrayObjekt = new ArrayObjekt(clazz, o);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(0,r1.getIntValue());
    }
}