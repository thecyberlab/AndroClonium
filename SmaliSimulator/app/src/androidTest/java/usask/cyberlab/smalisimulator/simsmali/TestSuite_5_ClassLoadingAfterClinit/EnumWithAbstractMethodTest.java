package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumWithAbstractMethodTest {

    @Test
    public void test0() throws Exception{
        AssetCopier.copyFile("EnumWithAbstractMethod.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$1.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$2.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$3.smali");

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        SmaliClazz enumClazz = (SmaliClazz) loader.getClazz("LEnumWithAbstractMethod;");

        Class enumClass = enumClazz.getMirroringClass();
        Object[] enumConstants = enumClass.getEnumConstants();
        Method m = enumClass.getMethod("test");

        Assert.assertEquals("A", m.invoke(enumConstants[0]));
        Assert.assertEquals("B", m.invoke(enumConstants[1]));
        Assert.assertEquals("C", m.invoke(enumConstants[2]));

    }
}
