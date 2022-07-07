package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class SelfReferencingConstructorTest {


    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("SelfReferencingConstructor.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }


    @Test
    public void test1() throws Exception {
        Clazz sc = clazzLoader.getClazz("LSelfReferencingConstructor;");
        Class cls = sc.getMirroringClass();
        Assert.assertEquals(2, cls.getDeclaredConstructors().length);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


}
