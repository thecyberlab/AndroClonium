package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class ClassWithConstructorSelfObjectAlias {

    @Test
    public void test() throws Exception{
        AssetCopier.copyFile("ClassWithConstructorSelfObjectAliasParent.smali");
        AssetCopier.copyFile("ClassWithConstructorSelfObjectAliasChild.smali");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        Clazz clazz = loader.getClazz("LClassWithConstructorSelfObjectAliasChild;");
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }
}
