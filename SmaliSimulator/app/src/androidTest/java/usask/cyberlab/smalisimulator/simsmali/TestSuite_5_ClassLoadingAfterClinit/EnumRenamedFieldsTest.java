package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

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
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class EnumRenamedFieldsTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);

        AssetCopier.copyFile("EnumWithRenamedFields.smali");
        testSmaliClass = loader.getSmaliClass("LEnumWithRenamedFields;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LEnumWithRenamedFields;");

        Class cls = smaliClazz.getMirroringClass();
        Assert.assertTrue(cls.isEnum());
        Field f1 = cls.getDeclaredField("a");
        Field f2 = cls.getDeclaredField("b");
        Field f3 = cls.getDeclaredField("c");
        Assert.assertTrue(f1.isEnumConstant());
        Assert.assertTrue(f2.isEnumConstant());
        Assert.assertTrue(f3.isEnumConstant());
        Object o1 = f1.get(null);
        Object o2 = f2.get(null);
        Object o3 = f3.get(null);
        Object[] values = cls.getEnumConstants();
        Assert.assertEquals(o1, values[0]);
        Assert.assertEquals(o2, values[1]);
        Assert.assertEquals(o3, values[2]);

        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("testEnumWithRenamedFields()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(smaliMethod, loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", objekt.getType());
        String s = (String) objekt.getMirroringObject();
        Assert.assertEquals("State2\nState1\nState2\nState3\nState1", s);
    }

}
