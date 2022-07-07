package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class EnumWithConstructorTest {

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
        AssetCopier.copyFile("EnumWithConstructor.smali");
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        Clazz clazz = loader.getClazz("LEnumWithConstructor;");
        Class cls = clazz.getMirroringClass();

        Assert.assertTrue(cls.isEnum());
        Field f1 = cls.getDeclaredField("State1");
        Field f2 = cls.getDeclaredField("State2");
        Field f3 = cls.getDeclaredField("State3");
        Assert.assertTrue(f1.isEnumConstant());
        Assert.assertTrue(f2.isEnumConstant());
        Assert.assertTrue(f3.isEnumConstant());

        Assert.assertEquals(1, cls.getDeclaredConstructors().length);
        Assert.assertNotNull(cls.getDeclaredConstructor(String.class, int.class, int.class, String.class));
    }



}
