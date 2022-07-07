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

public class SelfReferencingMethodTest {
    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("SelfReferencingMethod.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test() throws NoSuchMethodException {
        Clazz sc = clazzLoader.getClazz("LSelfReferencingMethod;");
        Class cls = sc.getMirroringClass();

        Class methodRetType = cls.getMethod("test").getReturnType();
        Assert.assertEquals(cls, methodRetType);

        Method method = cls.getMethod("test2", cls);
        Assert.assertEquals(cls, method.getParameterTypes()[0]);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

}
