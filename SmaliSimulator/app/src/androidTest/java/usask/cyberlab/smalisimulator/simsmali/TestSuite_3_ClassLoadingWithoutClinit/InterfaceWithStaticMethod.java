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

public class InterfaceWithStaticMethod {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("TestInterfaceWithStaticMethod.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LTestInterfaceWithStaticMethod;");
        Class cls = clazz.getMirroringClass();
        Assert.assertEquals(2, cls.getDeclaredMethods().length);
        Method method = cls.getDeclaredMethod("doStuffStatic");
        Assert.assertTrue(Modifier.isStatic(method.getModifiers()));
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

}
