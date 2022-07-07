package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class LambdaClasses {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath() + "/lambdaClasses", appContext);
        AssetCopier.copyDirectory("lambdaClasses");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test(){
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LLambdaTest;");
        SmaliMethod smaliMethod = clazz.getSmaliMethod("test()Ljava/util/ArrayList;");
        MethodExecution methodExecution = new MethodExecution(smaliMethod, clazzLoader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/util/ArrayList;", objekt.getType());
        ArrayList arrayList = (ArrayList) objekt.getMirroringObject();
        Assert.assertEquals(1, arrayList.get(0));
        Assert.assertEquals(5, arrayList.get(1));
        Assert.assertEquals(8, arrayList.get(2));
        Assert.assertEquals(9, arrayList.get(3));
        System.out.println(methodExecution.getExecutionTrace().getExecutionLogs());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


}
