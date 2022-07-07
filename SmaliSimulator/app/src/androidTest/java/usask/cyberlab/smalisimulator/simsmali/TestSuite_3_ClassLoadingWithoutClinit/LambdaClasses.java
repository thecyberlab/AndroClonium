package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class LambdaClasses {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath() + "/lambdaClasses", appContext);
        AssetCopier.copyDirectory("lambdaClasses");
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test(){
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LLambdaTest;");
        SmaliClazz clazz1 = (SmaliClazz) clazzLoader.getClazz("L-$$Lambda$LambdaTest$HdZ2wRHoQql8Ti_qQ3h_6Ter86I;");

        Assert.assertTrue(clazz1.isSubTypeOf("Ljava/util/Comparator;"));

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

}
