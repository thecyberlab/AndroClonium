package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;

public class CyclicTypeInsideNormalClass {


    @Test
    public void test1() throws Exception{
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/NormalWrapper.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/IfaceWithRef.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference3.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFieldsInsideNormalClass/CircularReference4.smali");
        String basePath = appContext.getCacheDir().getAbsolutePath() + "/cyclicTypes";
        ClazzLoader clazzLoader = new ClazzLoader(basePath, appContext);

        Clazz wrapperClazz = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/NormalWrapper;");

        Clazz sc = clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference1;");

        Assert.assertNotNull(sc.getStaticFieldValue("c2"));
        AbstractObjekt ao = (AbstractObjekt) sc.getStaticFieldValue("c2");

        Clazz sc2 = ao.getClazz();
        Assert.assertEquals(sc2, clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference2;"));
        Assert.assertNotNull(sc2.getStaticFieldValue("c3"));
        AbstractObjekt ao2 = (AbstractObjekt) sc2.getStaticFieldValue("c3");

        Clazz sc3 = ao2.getClazz();
        Assert.assertEquals(sc3, clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference3;"));
        Assert.assertNotNull(sc3.getStaticFieldValue("c4"));
        AbstractObjekt ao3 = (AbstractObjekt) sc3.getStaticFieldValue("c4");

        Clazz sc4 = ao3.getClazz();
        Assert.assertEquals(sc4, clazzLoader.getClazz("LcyclicFieldsInsideNormalClass/CircularReference4;"));
        Assert.assertNotNull(sc4.getStaticFieldValue("c1"));
        AbstractObjekt ao4 = (AbstractObjekt) sc4.getStaticFieldValue("c1");
        Assert.assertEquals(ao4.getClazz(), sc);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }
}
