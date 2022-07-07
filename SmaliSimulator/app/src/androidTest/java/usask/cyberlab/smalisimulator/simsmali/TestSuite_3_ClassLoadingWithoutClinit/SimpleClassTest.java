package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;


public class SimpleClassTest {
    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        AssetCopier.copyFile("SimpleClass.smali");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        SimSmaliConfig.executeClinit = false;
    }

    public static void afterClass(){
        clazzLoader = null;
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test1() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LSimpleClass;");
        Class cls = smaliClazz.getMirroringClass();
        Assert.assertEquals(Object.class, cls.getSuperclass());

        Assert.assertNotNull(cls.getConstructor());
        Assert.assertNotNull(cls.getConstructor(int.class));
        Assert.assertEquals(2, cls.getDeclaredConstructors().length);

        Assert.assertNotNull(cls.getMethod("instanceMethodTest"));
        Assert.assertNotNull(cls.getMethod("staticMethodTest"));
        Assert.assertEquals(2, cls.getDeclaredMethods().length);

        // byte-buddy inserts some fields itself so we don't count the declared
        // fields here
        Assert.assertNotNull(cls.getField("staticFieldTest"));
        Assert.assertNotNull(cls.getField("instanceFieldTest"));

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());

    }
}
