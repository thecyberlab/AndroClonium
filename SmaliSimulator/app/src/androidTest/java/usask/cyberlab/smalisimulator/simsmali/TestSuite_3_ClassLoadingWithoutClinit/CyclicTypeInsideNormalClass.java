package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class CyclicTypeInsideNormalClass {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception{
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/NormalWrapper.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/IfaceWithRef.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference3.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference4.smali");
        String basePath = appContext.getCacheDir().getAbsolutePath() + "/cyclicTypes";
        clazzLoader = new ClazzLoader(basePath, appContext);
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        SimSmaliConfig.executeClinit = true;
    }


    @Test
    public void test1() throws Exception{
        Clazz wrapperClazz = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/NormalWrapper;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFieldsInsideNormalClass/IfaceWithRef;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFieldsInsideNormalClass/CircularReference1;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFieldsInsideNormalClass/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFieldsInsideNormalClass/CircularReference3;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFieldsInsideNormalClass/CircularReference4;"));
        Class wrapperClass = wrapperClazz.getMirroringClass();

        Clazz ifaceClazz = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/IfaceWithRef;");
        Class ifaceClass = ifaceClazz.getMirroringClass();

        Clazz sc1 = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference1;");
        Clazz sc2 = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference2;");
        Clazz sc3 = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference3;");
        Clazz sc4 = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference4;");

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();
        Class cls4 = sc4.getMirroringClass();

        Assert.assertNotNull(wrapperClass.getDeclaredMethod("test", ifaceClass));
        Assert.assertTrue(ifaceClass.isInterface());
        Assert.assertNotNull(ifaceClass.getDeclaredMethod("test2", cls1));

        Assert.assertEquals(cls2, cls1.getField("c2").getType());
        Assert.assertEquals(cls3, cls2.getField("c3").getType());
        Assert.assertEquals(cls4, cls3.getField("c4").getType());
        Assert.assertEquals(cls1, cls4.getField("c1").getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

}
