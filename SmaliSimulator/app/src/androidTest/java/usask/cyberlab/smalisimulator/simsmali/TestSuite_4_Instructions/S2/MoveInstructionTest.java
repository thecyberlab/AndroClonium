package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/move_test.smali");
        testSmaliClass = loader.getSmaliClass("Lmove_test;");
    }

    @AfterClass
    public static void afterClass() throws Exception{
        loader = null;
        testSmaliClass = null;
    }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }


    @Test
    public void testMove1() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertEquals(1,r.getIntValue());
    }

    @Test
    public void testMove2() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(1);
        assertTrue(r.containsNumericData());
        assertEquals(1800000L,r.getLongValue());
    }

    @Test
    public void testMove3() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest3()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(1);
        assertTrue(r.containsRefToObject());
        assertEquals("Ljava/lang/Object;",r.getReferencedObjectValue().getClazz().getClassPath());
    }

    @Test
    public void testMove4() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest4()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        assertTrue(r.getReferencedObjectValue() instanceof ArrayObjekt);
        assertEquals("[Ljava/lang/String;",r.getReferencedObjectValue().getType());
        ArrayObjekt arrayObjekt = (ArrayObjekt) r.getReferencedObjectValue();
        assertEquals(2,arrayObjekt.getSize());
        Objekt sjo0 = (Objekt) arrayObjekt.getValue(0);
        Objekt sjo1 = (Objekt) arrayObjekt.getValue(1);
        assertEquals("text1", sjo0.toString());
        assertEquals("text2", sjo1.toString());
    }


    @Test
    public void testMove5() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest5()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(18);
        assertTrue(r.containsNumericData());
        assertEquals(255,r.getIntValue());
    }

    @Test
    public void testMove6() {
        SmaliMethod sm = testSmaliClass.getMethod("moveTest6()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(256);
        assertTrue(r.containsNumericData());
        assertEquals(-1,r.getIntValue());
    }

}
