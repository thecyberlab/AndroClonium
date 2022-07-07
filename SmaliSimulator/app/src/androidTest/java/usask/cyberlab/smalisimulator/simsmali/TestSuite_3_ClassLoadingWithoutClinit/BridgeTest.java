package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class BridgeTest {

    static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("LinkListWithBridgeMethod.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }


    @Test
    public void test(){
        Clazz sc = clazzLoader.getClazz("LLinkListWithBridgeMethod;");
        Method[] methods = sc.getMirroringClass().getDeclaredMethods();
        Assert.assertEquals(6, methods.length);
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

}
