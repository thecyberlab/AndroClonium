package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

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
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;

import static org.junit.Assert.*;

public class FilledNewArrayInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/filled_new_array_test.smali");
        testSmaliClass = loader.getSmaliClass("Lfilled_new_array_test;");
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
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("filledNewArray()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        r0.set(10);
        r1.set(20);
        r2.set(30);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getInvokedFunctionExecutionResult();
        assertEquals(ResultType.OBJECT, mr.getType());
        ArrayObjekt sa = (ArrayObjekt) mr.getResult();
        assertEquals(3,sa.getSize());
        assertEquals(10,sa.getValue(0));
        assertEquals(20,sa.getValue(1));
        assertEquals(30,sa.getValue(2));
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("filledNewArrayRange()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Register r0 = methodExecution.getRegister(0);
        Register r1 = methodExecution.getRegister(1);
        Register r2 = methodExecution.getRegister(2);
        Register r3 = methodExecution.getRegister(3);
        Register r4 = methodExecution.getRegister(4);
        Register r5 = methodExecution.getRegister(5);
        r0.set(10);
        r1.set(20);
        r2.set(30);
        r3.set(40);
        r4.set(50);
        r5.set(60);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getInvokedFunctionExecutionResult();
        assertEquals(ResultType.OBJECT, mr.getType());
        ArrayObjekt sa = (ArrayObjekt) mr.getResult();
        assertEquals(6,sa.getSize());
        assertEquals(10,sa.getValue(0));
        assertEquals(20,sa.getValue(1));
        assertEquals(30,sa.getValue(2));
        assertEquals(40,sa.getValue(3));
        assertEquals(50,sa.getValue(4));
        assertEquals(60,sa.getValue(5));
    }
}