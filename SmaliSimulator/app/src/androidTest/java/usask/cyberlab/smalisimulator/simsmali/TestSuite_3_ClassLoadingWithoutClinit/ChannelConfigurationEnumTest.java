package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class ChannelConfigurationEnumTest {

    @BeforeClass
    public static void beforeClass(){
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test() throws Exception{
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        AssetCopier.copyFile("ChannelConfiguration.smali");

        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LChannelConfiguration;");
        Class cls = smaliClazz.getMirroringClass();

        Assert.assertTrue(cls.isEnum());
        Field f1 = cls.getDeclaredField("CHANNEL_CONFIG_UNSUPPORTED");
        Field f2 = cls.getDeclaredField("CHANNEL_CONFIG_NONE");
        Field f3 = cls.getDeclaredField("CHANNEL_CONFIG_MONO");
        Field f4 = cls.getDeclaredField("CHANNEL_CONFIG_STEREO");
        Field f5 = cls.getDeclaredField("CHANNEL_CONFIG_STEREO_PLUS_CENTER");
        Field f6 = cls.getDeclaredField("CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO");
        Field f7 = cls.getDeclaredField("CHANNEL_CONFIG_FIVE");
        Field f8 = cls.getDeclaredField("CHANNEL_CONFIG_FIVE_PLUS_ONE");
        Field f9 = cls.getDeclaredField("CHANNEL_CONFIG_SEVEN_PLUS_ONE");

        Assert.assertTrue(f1.isEnumConstant());
        Assert.assertTrue(f2.isEnumConstant());
        Assert.assertTrue(f3.isEnumConstant());
        Assert.assertTrue(f4.isEnumConstant());
        Assert.assertTrue(f5.isEnumConstant());
        Assert.assertTrue(f6.isEnumConstant());
        Assert.assertTrue(f7.isEnumConstant());
        Assert.assertTrue(f8.isEnumConstant());
        Assert.assertTrue(f9.isEnumConstant());

        Assert.assertTrue(Modifier.isStatic(f1.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f2.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f3.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f4.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f5.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f6.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f7.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f8.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f9.getModifiers()));

        Field f_chCount = cls.getDeclaredField("chCount");
        Field f_descr = cls.getDeclaredField("descr");

        Assert.assertFalse(f_chCount.isEnumConstant());
        Assert.assertFalse(f_descr.isEnumConstant());

        Assert.assertFalse(Modifier.isStatic(f_chCount.getModifiers()));
        Assert.assertFalse(Modifier.isStatic(f_descr.getModifiers()));
    }

}
