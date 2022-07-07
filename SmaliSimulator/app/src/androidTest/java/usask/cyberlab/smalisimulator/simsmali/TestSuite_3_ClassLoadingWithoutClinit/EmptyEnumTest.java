package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EmptyEnumTest {

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
        AssetCopier.copyFile("EmptyEnum.smali");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LEmptyEnum;");
        Class enumCls = smaliClazz.getMirroringClass();
        Class enumArrayCls = Array.newInstance(enumCls, 0).getClass();

        Assert.assertTrue(enumCls.isEnum());

        for(Field f: enumCls.getDeclaredFields()){
            Assert.assertFalse(f.isEnumConstant());
        }
        Field f = enumCls.getDeclaredField("$VALUES");
        Assert.assertEquals(enumArrayCls, f.getType());

    }
}
