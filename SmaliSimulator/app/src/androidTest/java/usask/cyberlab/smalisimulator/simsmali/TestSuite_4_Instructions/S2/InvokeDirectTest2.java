package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeDirectTest2 {

    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeDirect2.smali");
        AssetCopier.copyFile("instructions/InvokeDirect2_A.smali");
        AssetCopier.copyFile("instructions/InvokeDirect2_A_Parent.smali");
        AssetCopier.copyFile("instructions/InvokeDirect2_B.smali");

    }

    public static void afterClass(){
        loader = null;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LInvokeDirect2;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test()LInvokeDirect2_A;");
        MethodExecution methodExecution = new MethodExecution(sm, loader);
        methodExecution.execute();

        //String s = methodExecution.getExecutionTrace().getExecutionLogs();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Objekt objekt1 = (Objekt) objekt.getInstanceFieldValue("b", objekt.getClazz());
        Assert.assertNotNull(objekt1);
        Assert.assertEquals("LInvokeDirect2_B;", objekt1.getType());
    }

}
