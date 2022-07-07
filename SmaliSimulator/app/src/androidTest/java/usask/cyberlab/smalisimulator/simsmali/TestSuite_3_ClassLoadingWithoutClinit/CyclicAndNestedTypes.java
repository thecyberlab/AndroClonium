package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class CyclicAndNestedTypes {

    @BeforeClass
    public static void beforeClass(){
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test1() throws Exception{

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyDirectory("nestedTypes/realWorldSamples");

        clazzLoader.getSmaliClass("LnestedTypes/realWorldSamples/GridView;");
        clazzLoader.getSmaliClass("LnestedTypes/realWorldSamples/GridView$OnGridTouchListener;");
        clazzLoader.getSmaliClass("LnestedTypes/realWorldSamples/GridView$OnSolvedListener;");
        clazzLoader.getSmaliClass("LnestedTypes/realWorldSamples/GridCell;");
        clazzLoader.getSmaliClass("LnestedTypes/realWorldSamples/GridCage;");

        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/realWorldSamples/GridView;");

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());

    }
}
