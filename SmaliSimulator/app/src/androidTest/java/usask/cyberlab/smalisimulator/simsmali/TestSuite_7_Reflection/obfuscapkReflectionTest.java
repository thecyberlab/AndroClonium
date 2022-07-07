package usask.cyberlab.smalisimulator.simsmali.TestSuite_7_Reflection;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class obfuscapkReflectionTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/reflection" , context);
        AssetCopier.copyFile("reflection/ApiReflection.smali");
        AssetCopier.copyFile("reflection/MyActivity.smali");
        testSmaliClass = loader.getSmaliClass("LMyActivity;");
    }

    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }


    @Test
    public void test(){
        SmaliMethod sm = testSmaliClass.getMethod("displaymenge(D)Ljava/lang/String;");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Double d= 23.53454321;
        methodExecution.getRegister(7).set(d);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", o.getType());
        Assert.assertEquals("23.5", o.getMirroringObject());
    }

    @Test
    public void test2(){
        SmaliMethod sm = testSmaliClass.getMethod("displaymenge(D)Ljava/lang/String;");
        Assert.assertNotNull(sm);
        for (int i = 0; i < 10; i++) {
            MethodExecution me = new MethodExecution(sm, loader);
            me.getRegister(7).set(new AmbiguousValue("D"));
            me.execute();
            MethodExecutionResult mr = me.getExecutionResult();
            if(ResultType.OBJECT.equals(mr.getType())){
                Objekt o = (Objekt) mr.getResult();
                Assert.assertEquals("0.0",o.getMirroringObject());
            }
            else if(ResultType.AMBIGUOUS_VALUE.equals(mr.getType())){
                AmbiguousValue av = (AmbiguousValue) mr.getResult();
                Assert.assertEquals("Ljava/lang/String;", av.getType());
            }
            else {
                throw new IllegalStateException();
            }
        }
    }
}
