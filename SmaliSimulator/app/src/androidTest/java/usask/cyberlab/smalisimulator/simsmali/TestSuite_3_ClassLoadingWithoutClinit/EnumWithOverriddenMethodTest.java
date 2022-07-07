package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumWithOverriddenMethodTest {

    @BeforeClass
    public static void beforeClass(){
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test0() throws Exception{
        AssetCopier.copyFile("EnumWithOverriddenMethod.smali");
        AssetCopier.copyFile("EnumWithOverriddenMethod$1.smali");

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        SmaliClazz  enumInnerClazz = (SmaliClazz) loader.getClazz("LEnumWithOverriddenMethod$1;");
        Assert.assertTrue(loader.isClazzLoaded("LEnumWithOverriddenMethod;"));
        SmaliClazz enumClazz = (SmaliClazz) loader.getClazz("LEnumWithOverriddenMethod;");

        Class enumCls = enumClazz.getMirroringClass();
        Class enumInnerCls = enumInnerClazz.getMirroringClass();

        Assert.assertFalse(Modifier.isAbstract(enumCls.getModifiers()));
        Assert.assertTrue(enumCls.isEnum());
        Assert.assertFalse(enumInnerCls.isEnum());
        Assert.assertEquals(enumCls, enumInnerCls.getSuperclass());
        Assert.assertNotNull(enumInnerCls.getDeclaredMethod("test"));

    }

}
