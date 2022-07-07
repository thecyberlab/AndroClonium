package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ChannelConfigurationEnumTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        AssetCopier.copyFile("ChannelConfiguration.smali");
        testSmaliClass = loader.getSmaliClass("LChannelConfiguration;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }


    @Test
    public void test(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LChannelConfiguration;");
    }

    @Test
    public void test1(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LChannelConfiguration;");
        Objekt o1 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_UNSUPPORTED");
        Objekt o2 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_NONE");
        Objekt o3 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_MONO");
        Objekt o4 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_STEREO");
        Objekt o5 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_STEREO_PLUS_CENTER");
        Objekt o6 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO");
        Objekt o7 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_FIVE");
        Objekt o8 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_FIVE_PLUS_ONE");
        Objekt o9 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_SEVEN_PLUS_ONE");

        Assert.assertEquals(o1.getInstanceFieldValue("chCount", null), -1);
        Assert.assertEquals(o2.getInstanceFieldValue("chCount", null), 0);
        Assert.assertEquals(o3.getInstanceFieldValue("chCount", null), 1);
        Assert.assertEquals(o4.getInstanceFieldValue("chCount", null), 2);
        Assert.assertEquals(o5.getInstanceFieldValue("chCount", null), 3);
        Assert.assertEquals(o6.getInstanceFieldValue("chCount", null), 4);
        Assert.assertEquals(o7.getInstanceFieldValue("chCount", null), 5);
        Assert.assertEquals(o8.getInstanceFieldValue("chCount", null), 6);
        Assert.assertEquals(o9.getInstanceFieldValue("chCount", null), 8);
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("forInt(I)LChannelConfiguration;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        SimulationUtils.setMethodExecutionWrappedArguments(methodExecution, new Object[]{1});
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();

        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LChannelConfiguration;");
        Objekt objekt2 = (Objekt) smaliClazz.getStaticFieldValue("CHANNEL_CONFIG_MONO");

        Assert.assertEquals(objekt, objekt2);

    }

}
