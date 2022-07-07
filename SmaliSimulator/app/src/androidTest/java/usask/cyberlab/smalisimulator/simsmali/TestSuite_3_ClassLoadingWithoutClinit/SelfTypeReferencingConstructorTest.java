package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class SelfTypeReferencingConstructorTest {
    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("SelfTypeReferencingConstructor.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }


    @Test
    public void test1() throws Exception {
        Clazz sc = clazzLoader.getClazz("LSelfTypeReferencingConstructor;");
        Class cls = sc.getMirroringClass();
        Constructor ctor = cls.getConstructor(cls);
        Assert.assertNotNull(ctor);
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());

    }

}
