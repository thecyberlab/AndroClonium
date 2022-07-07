package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumWithOverriddenMethodTest {

    @Test
    public void test0() throws Exception{
        AssetCopier.copyFile("EnumWithOverriddenMethod.smali");
        AssetCopier.copyFile("EnumWithOverriddenMethod$1.smali");

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        loader.getClazz("LEnumWithOverriddenMethod$1;");
        Assert.assertTrue(loader.isClazzLoaded("LEnumWithOverriddenMethod;"));
        SmaliClazz enumClazz = (SmaliClazz) loader.getClazz("LEnumWithOverriddenMethod;");

        Class enumCls = enumClazz.getMirroringClass();

        Object[] enumConstants = enumCls.getEnumConstants();
        Assert.assertNotNull(enumConstants);

        Method m = enumCls.getMethod("test");
        Assert.assertEquals("default", m.invoke(enumConstants[0]));
        Assert.assertEquals("B", m.invoke(enumConstants[1]));
        Assert.assertEquals("default", m.invoke(enumConstants[2]));

    }

}
