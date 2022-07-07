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
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class EnumRenamedFieldsTest {

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
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        AssetCopier.copyFile("EnumWithRenamedFields.smali");
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LEnumWithRenamedFields;");
        Class cls = smaliClazz.getMirroringClass();

        Assert.assertTrue(cls.isEnum());

        Field f1 = cls.getDeclaredField("a");
        Field f2 = cls.getDeclaredField("b");
        Field f3 = cls.getDeclaredField("c");

        Assert.assertTrue(f1.isEnumConstant());
        Assert.assertTrue(f2.isEnumConstant());
        Assert.assertTrue(f3.isEnumConstant());


    }

}
