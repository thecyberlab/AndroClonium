package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumWithAbstractMethodTest {

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
        AssetCopier.copyFile("EnumWithAbstractMethod.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$1.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$2.smali");
        AssetCopier.copyFile("EnumWithAbstractMethod$3.smali");

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        SmaliClazz enumClazz = (SmaliClazz) loader.getClazz("LEnumWithAbstractMethod;");
        SmaliClazz enumInner1Clazz = (SmaliClazz) loader.getClazz("LEnumWithAbstractMethod$1;");
        SmaliClazz enumInner2Clazz = (SmaliClazz) loader.getClazz("LEnumWithAbstractMethod$2;");
        SmaliClazz enumInner3Clazz = (SmaliClazz) loader.getClazz("LEnumWithAbstractMethod$3;");

        Class enumClass = enumClazz.getMirroringClass();
        Class enumInner1Class = enumInner1Clazz.getMirroringClass();
        Class enumInner2Class = enumInner2Clazz.getMirroringClass();
        Class enumInner3Class = enumInner3Clazz.getMirroringClass();

        Assert.assertTrue(enumClass.isEnum());
        Assert.assertFalse(enumInner1Class.isEnum());
        Assert.assertFalse(enumInner2Class.isEnum());
        Assert.assertFalse(enumInner3Class.isEnum());

        Assert.assertTrue(Modifier.isAbstract(enumClass.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(enumInner1Class.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(enumInner2Class.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(enumInner3Class.getModifiers()));

        Assert.assertEquals(enumClass, enumInner1Class.getSuperclass());
        Assert.assertEquals(enumClass, enumInner2Class.getSuperclass());
        Assert.assertEquals(enumClass, enumInner3Class.getSuperclass());

        Assert.assertTrue(enumClass.getDeclaredField("A").isEnumConstant());
        Assert.assertTrue(enumClass.getDeclaredField("B").isEnumConstant());
        Assert.assertTrue(enumClass.getDeclaredField("C").isEnumConstant());

        Method m0 = enumClass.getDeclaredMethod("test");
        Method m1 = enumInner1Class.getDeclaredMethod("test");
        Method m2 = enumInner2Class.getDeclaredMethod("test");
        Method m3 = enumInner3Class.getDeclaredMethod("test");

        Assert.assertTrue(Modifier.isAbstract(m0.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(m1.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(m2.getModifiers()));
        Assert.assertFalse(Modifier.isAbstract(m3.getModifiers()));
    }
}
