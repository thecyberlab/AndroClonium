package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class KotlinClassesTest {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("KotlinClassWithCompanion.smali");
        AssetCopier.copyFile("KotlinClassWithCompanion$Companion.smali");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz clazz1 = (SmaliClazz) clazzLoader.getClazz("LKotlinClassWithCompanion;");
        SmaliClazz clazz2 = (SmaliClazz) clazzLoader.getClazz("LKotlinClassWithCompanion$Companion;");

        Class testClass = clazz1.getMirroringClass();
        Class testCompanionClass = clazz2.getMirroringClass();

        Constructor[] ctors = testCompanionClass.getDeclaredConstructors();
        Assert.assertEquals(2, ctors.length);
        Assert.assertNotNull(testCompanionClass.getDeclaredConstructor());
        Assert.assertNotNull(testCompanionClass.getDeclaredConstructor(Class.forName("kotlin.jvm.internal.DefaultConstructorMarker")));

        Field f = testClass.getDeclaredField("Companion");
        Object companion = f.get(null);
        Assert.assertNotNull(companion);
        Assert.assertEquals(testCompanionClass, companion.getClass());

        Method m = testCompanionClass.getDeclaredMethod("getTest");
        Assert.assertEquals("Hello", m.invoke(companion));
    }

}
