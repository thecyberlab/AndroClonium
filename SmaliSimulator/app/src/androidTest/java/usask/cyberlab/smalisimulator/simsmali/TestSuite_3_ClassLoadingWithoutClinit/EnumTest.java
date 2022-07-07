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

public class EnumTest {

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
        AssetCopier.copyFile("SimpleEnum.smali");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LSimpleEnum;");
        Class cls = smaliClazz.getMirroringClass();
        Assert.assertTrue(cls.isEnum());
        Field f_state1 = cls.getDeclaredField("STATE1");
        Field f_state2 = cls.getDeclaredField("STATE2");
        Field f_state3 = cls.getDeclaredField("STATE3");

        Assert.assertTrue(f_state1.isEnumConstant());
        Assert.assertTrue(f_state2.isEnumConstant());
        Assert.assertTrue(f_state3.isEnumConstant());

        Assert.assertEquals(cls, f_state1.getType());
        Assert.assertEquals(cls, f_state2.getType());
        Assert.assertEquals(cls, f_state3.getType());

        Field f_values = cls.getDeclaredField("$VALUES");
        Assert.assertFalse(f_values.isEnumConstant());
        Class enumArrayClass = Array.newInstance(cls, 3).getClass();
        Assert.assertEquals(enumArrayClass, f_values.getType());

        Assert.assertEquals(1, cls.getDeclaredConstructors().length);
        Assert.assertNotNull(cls.getDeclaredConstructor(String.class, int.class));

        Method m1 = cls.getDeclaredMethod("values");
        Method m2 = cls.getDeclaredMethod("valueOf", String.class);

        Assert.assertEquals(enumArrayClass, m1.getReturnType());
        Assert.assertEquals(cls, m2.getReturnType());

    }

}
