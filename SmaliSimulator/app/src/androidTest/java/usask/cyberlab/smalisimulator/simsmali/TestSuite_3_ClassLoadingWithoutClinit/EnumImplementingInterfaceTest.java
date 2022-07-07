package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumImplementingInterfaceTest {

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
        AssetCopier.copyFile("EnumImplementingInterface.smali");
        AssetCopier.copyFile("InterfaceForEnumImplementingInterface.smali");

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);

        SmaliClazz enumClazz = (SmaliClazz) clazzLoader.getClazz("LEnumImplementingInterface;");
        SmaliClazz ifaceClazz = (SmaliClazz) clazzLoader.getClazz("LInterfaceForEnumImplementingInterface;");

        Class enumClass = enumClazz.getMirroringClass();
        Class ifaceClass = ifaceClazz.getMirroringClass();

        Assert.assertTrue(enumClass.isEnum());

        Class[] ifaces = enumClass.getInterfaces();
        Assert.assertEquals(1, ifaces.length);
        Assert.assertEquals(ifaceClass, ifaces[0]);
        Assert.assertTrue(enumClazz.isSubTypeOf("LInterfaceForEnumImplementingInterface;"));

        Field fA = enumClass.getDeclaredField("A");
        Field fB = enumClass.getDeclaredField("B");
        Field fC = enumClass.getDeclaredField("C");

        Assert.assertTrue(fA.isEnumConstant());
        Assert.assertTrue(fB.isEnumConstant());
        Assert.assertTrue(fC.isEnumConstant());

        Assert.assertNotNull(enumClass.getDeclaredMethod("values"));
        Assert.assertNotNull(enumClass.getDeclaredMethod("valueOf", String.class));
        Assert.assertNotNull(enumClass.getDeclaredMethod("t"));

    }
}
