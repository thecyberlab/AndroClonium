package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class BranchingCLINIT {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("BranchingCLINIT.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }


    @Test
    public void test(){
        SmaliClass smaliClass = clazzLoader.getSmaliClass("LBranchingCLINIT;");
        SmaliClazz sc = (SmaliClazz) clazzLoader.getClazz("LBranchingCLINIT;");

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


}
