package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
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
        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz clazz1 = (SmaliClazz) clazzLoader.getClazz("LKotlinClassWithCompanion;");
        SmaliClazz clazz2 = (SmaliClazz) clazzLoader.getClazz("LKotlinClassWithCompanion$Companion;");
        Class cls = clazz2.getMirroringClass();
        Constructor[] ctors = cls.getDeclaredConstructors();
        Assert.assertEquals(2, ctors.length);
        Assert.assertNotNull(cls.getDeclaredConstructor());
        Assert.assertNotNull(cls.getDeclaredConstructor(Class.forName("kotlin.jvm.internal.DefaultConstructorMarker")));
    }

}
