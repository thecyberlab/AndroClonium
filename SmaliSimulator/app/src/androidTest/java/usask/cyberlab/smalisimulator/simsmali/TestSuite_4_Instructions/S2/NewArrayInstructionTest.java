package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;

import static org.junit.Assert.*;

public class NewArrayInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/new_array_test.smali");
        testSmaliClass = loader.getSmaliClass("Lnew_array_test;");
    }

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
        SmaliMethod sm = testSmaliClass.getMethod("createIntegerArray()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsRefToObject());
        ArrayObjekt arrayObjekt = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals("[I", arrayObjekt.getType());
        assertEquals(3, arrayObjekt.getSize());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("create2DIntegerArray()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsRefToObject());
        ArrayObjekt arrayObjekt = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals("[[I", arrayObjekt.getType());
        assertEquals(4, arrayObjekt.getSize());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("createLocalInstanceArray()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsRefToObject());
        ArrayObjekt arrayObjekt = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals("[Lnew_array_test;", arrayObjekt.getType());
        assertEquals(5, arrayObjekt.getSize());
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("create2DLocalInstanceArray()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsRefToObject());
        ArrayObjekt arrayObjekt = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals("[[Lnew_array_test;", arrayObjekt.getType());
        assertEquals(6, arrayObjekt.getSize());
    }


    @Test(expected = SmaliSimulationException.class)
    public void test5(){
        SimSmaliConfig.onClassLoadErrorUseAmbiguousValue = false;
        SmaliMethod sm = testSmaliClass.getMethod("createNonExistentArrayClass()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
    }

    @Test
    public void test6(){
        SimSmaliConfig.onClassLoadErrorUseAmbiguousValue = true;
        SmaliMethod sm = testSmaliClass.getMethod("createNonExistentArrayClass()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsAmbiguousValue());
        AmbiguousValue av = r.getAmbiguousValue();
        assertEquals("[Ldoes/not/exist;", av.getType());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("createIntegerArrayAmbiguousSize()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.getRegister(0).set(new AmbiguousValue("I"));
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.containsAmbiguousValue());
        AmbiguousValue av = r.getAmbiguousValue();
        Assert.assertEquals("[I", av.getType());
    }



}