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

public class SelfReferencingFieldTest {
    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("SelfReferencingField.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test1() throws Exception{
        Clazz sc = clazzLoader.getClazz("LSelfReferencingField;");
        Assert.assertNull(sc.getStaticFieldValue("s"));
        Assert.assertEquals("LSelfReferencingField;", sc.getFieldType("s"));
        Class cls = sc.getMirroringClass();
        Assert.assertEquals(cls, cls.getDeclaredField("s").getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }
}
