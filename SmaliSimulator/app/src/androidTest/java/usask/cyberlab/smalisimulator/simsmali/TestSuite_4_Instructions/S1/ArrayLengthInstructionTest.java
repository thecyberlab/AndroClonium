package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;

import static org.junit.Assert.assertEquals;

public class ArrayLengthInstructionTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/array_length_test.smali");
        testSmaliClass = loader.getSmaliClass("Larray_length_test;");

    }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("getLength()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz reflectedClazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        ArrayObjekt arrayObjekt = new ArrayObjekt(reflectedClazz, new Object[9]);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(9,r.getIntValue());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("getLength()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        ReflectedClazz reflectedClazz = (ReflectedClazz) loader.getClazz("[I");
        ArrayObjekt arrayObjekt = new ArrayObjekt(reflectedClazz , new int[12]);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(12,r.getIntValue());
    }


    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("getLength()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r = methodExecution.getRegister(0);
        SmaliArrayClazz smaliArrayClazz = (SmaliArrayClazz) loader.getClazz("[Larray_length_test;");
        SmaliClazz sc = (SmaliClazz) loader.getClazz("Larray_length_test;");
        Object mirroringValue = Array.newInstance(sc.getMirroringClass() ,7);
        ArrayObjekt arrayObjekt = new ArrayObjekt(smaliArrayClazz, mirroringValue);
        r.set(arrayObjekt);
        methodExecution.execute();
        assertEquals(7,r.getIntValue());
    }
}
