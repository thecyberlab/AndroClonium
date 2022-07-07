package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.text.Normalizer;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class CheckCastInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;
    private static AbstractObjekt o1;
    private static AbstractObjekt o2;
    private static AbstractObjekt o3;
    private static AbstractObjekt o4;
    private static AbstractObjekt o5;
    private static AbstractObjekt o6;
    private static AbstractObjekt o7;
    private static AbstractObjekt o8;
    private static AbstractObjekt o9;
    private static AbstractObjekt o10;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/check_cast_test.smali");
        testSmaliClass = loader.getSmaliClass("Lcheck_cast_test;");

        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
        o1 = new Objekt(clazz , new Object());

        clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        o2 = new Objekt(clazz ,"hi");

        clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/Integer;");
        o3 = new Objekt(clazz, 32);

        clazz = (ReflectedClazz) loader.getClazz("[I");
        o4 = new ArrayObjekt(clazz, new int[2]);

        clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        o5 = new ArrayObjekt(clazz, new Object[3]);

        clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/String;");
        o6 = new ArrayObjekt(clazz, new String[4]);

        clazz = (ReflectedClazz) loader.getClazz("Ljava/text/Normalizer$Form;");
        o7 = new Objekt(clazz, Normalizer.Form.NFC);

        clazz = (ReflectedClazz) loader.getClazz("[Ljava/text/Normalizer$Form;");
        o8 = new ArrayObjekt(clazz, new Normalizer.Form[5]);

        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("Lcheck_cast_test;");
        o9 = new Objekt(smaliClazz, smaliClazz.getClassPath());

        SmaliArrayClazz arrayClazz = (SmaliArrayClazz) loader.getClazz("[Lcheck_cast_test;");
        Class baseClass = arrayClazz.getMirroringClass().getComponentType();
        o10 = new ArrayObjekt(arrayClazz, Array.newInstance(baseClass, 0));

    }

    public static void afterClass(){
        testSmaliClass = null;
        loader = null;
        o1 = null;
        o2 = null;
        o3 = null;
        o4 = null;
        o5 = null;
        o6 = null;
        o7 = null;
        o8 = null;
        o9 = null;
        o10 = null;
    }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("castToObject()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());
    }

    @Test
    public void test2() throws Exception{
        SmaliMethod sm = testSmaliClass.getMethod("castToString()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertNull(objekt);

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("castToInteger()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("castToIntArray()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("castToObjectArray()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("castToStringArray()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertNull(objekt);

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("castToSelfArray()V");

        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        r.set(o1);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o2);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());

        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o3);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o4);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o5);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o6);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o7);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o8);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o9);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/ClassCastException;", objekt.getType());


        methodExecution = new MethodExecution(sm,loader);
        r = methodExecution.getRegister(0);
        r.set(o10);
        methodExecution.execute();
        mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID,mr.getType());
        objekt = (Objekt) mr.getResult();
        Assert.assertNull(objekt);
    }
}