package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;

import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class InterfaceWithDefaultMethods {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("InterfaceWithDefaultMethods.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }


    @Test
    public void test() throws Exception{
        SmaliClazz sc = (SmaliClazz) clazzLoader.getClazz("LInterfaceWithDefaultMethods;");
        Method m = sc.getMirroringClass().getDeclaredMethod("doStuff");

        Assert.assertFalse(Modifier.isStatic(m.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(m.getModifiers()));

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }
}
