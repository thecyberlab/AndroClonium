package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

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
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

public class NewInstanceInstructionTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/new_instance_test.smali");
        testSmaliClass = loader.getSmaliClass("Lnew_instance_test;");
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
        SmaliMethod sm = testSmaliClass.getMethod("newLocalInstance()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Objekt createdObject = (Objekt) r.getReferencedObjectValue();
        assertEquals("Lnew_instance_test;",createdObject.getClazz().getClassPath());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("newNonLocalInstance()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Register r = methodExecution.getRegister(0);
        Objekt createdObject = (Objekt) r.getReferencedObjectValue();
        assertEquals("Ljava/lang/Integer;",createdObject.getClazz().getClassPath());
    }
}