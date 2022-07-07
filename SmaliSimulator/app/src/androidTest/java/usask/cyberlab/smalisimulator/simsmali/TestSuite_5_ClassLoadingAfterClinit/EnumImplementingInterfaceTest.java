package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

public class EnumImplementingInterfaceTest {

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
        Assert.assertFalse(ifaceClass.isEnum());
        Assert.assertFalse(enumClass.isInterface());
        Assert.assertTrue(ifaceClass.isInterface());

        Field f1 = enumClass.getDeclaredField("A");
        Field f2 = enumClass.getDeclaredField("B");
        Field f3 = enumClass.getDeclaredField("C");

        Object v1 = f1.get(null);
        Object v2 = f2.get(null);
        Object v3 = f3.get(null);

        Assert.assertNotNull(v1);
        Assert.assertNotNull(v2);
        Assert.assertNotNull(v3);

        Assert.assertEquals(enumClass, v1.getClass());
        Assert.assertEquals(enumClass, v2.getClass());
        Assert.assertEquals(enumClass, v3.getClass());

        Object[] enumConstants = enumClass.getEnumConstants();
        Assert.assertNotNull(enumConstants);
        Assert.assertEquals(v1, enumConstants[0]);
        Assert.assertEquals(v2, enumConstants[1]);
        Assert.assertEquals(v3, enumConstants[2]);

        Method valueOfMethod = enumClass.getDeclaredMethod("valueOf", String.class);
        Assert.assertEquals(v1, valueOfMethod.invoke(null, "A"));
        Assert.assertEquals(v2, valueOfMethod.invoke(null, "B"));
        Assert.assertEquals(v3, valueOfMethod.invoke(null, "C"));

        Class[] ifaces = enumClazz.getMirroringClass().getInterfaces();
        Assert.assertEquals(1, ifaces.length);
        Assert.assertEquals(ifaceClazz.getMirroringClass(), ifaces[0]);
        Assert.assertTrue(enumClazz.isSubTypeOf("LInterfaceForEnumImplementingInterface;"));

        Method testMethod = enumClass.getDeclaredMethod("t");
        Assert.assertEquals("this is LEnumImplementingInterface;->t()V", testMethod.invoke(v1));
        Assert.assertEquals("this is LEnumImplementingInterface;->t()V", testMethod.invoke(v2));
        Assert.assertEquals("this is LEnumImplementingInterface;->t()V", testMethod.invoke(v3));

    }
}
