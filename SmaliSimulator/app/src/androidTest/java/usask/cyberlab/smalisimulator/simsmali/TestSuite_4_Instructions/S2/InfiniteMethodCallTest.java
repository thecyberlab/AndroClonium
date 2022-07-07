package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.MethodDepthExceeded;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InfiniteMethodCallTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;
    private static int origDepth;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InfiniteMethodCallTest.smali");
        testSmaliClass = loader.getSmaliClass("LInfiniteMethodCallTest;");
        origDepth = SimSmaliConfig.maxMethodDepth;
        SimSmaliConfig.maxMethodDepth = 80;
    }

    @AfterClass
    public static void afterClass() throws Exception{
        loader = null;
        testSmaliClass = null;
        SimSmaliConfig.maxMethodDepth = origDepth;
    }


    @Test
    public void test1_1(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = true;
        SmaliMethod sm = testSmaliClass.getMethod("test1()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm , loader);
        me.execute();
        MethodExecutionResult methodExecutionResult = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, methodExecutionResult.getType());
        AmbiguousValue av = (AmbiguousValue) methodExecutionResult.getResult();
        Assert.assertEquals("Ljava/lang/String;", av.getType());
    }

    @Test
    public void test1_2(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = false;
        SmaliMethod sm = testSmaliClass.getMethod("test1()Ljava/lang/String;");
        MethodExecution me = new MethodExecution(sm , loader);
        me.execute();
        MethodExecutionResult methodExecutionResult = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, methodExecutionResult.getType());
        Objekt o = (Objekt) methodExecutionResult.getResult();
        MethodDepthExceeded e = (MethodDepthExceeded) o.getMirroringObject();
    }


    @Test
    public void test2_1() throws Exception{
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = true;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        Objekt selfObjekt = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = selfObjekt;
        Constructor ctor = clazz.getMirroringClass().getConstructor();
        ctor.newInstance();
        Assert.assertNotNull(selfObjekt.getMirroringObject());
        MethodExecution me = new MethodExecution(sm , loader, selfObjekt);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", av.getType());
    }

    @Test
    public void test2_2() throws Exception{
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = false;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        Objekt selfObjekt = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = selfObjekt;
        Constructor ctor = clazz.getMirroringClass().getConstructor();
        ctor.newInstance();
        Assert.assertNotNull(selfObjekt.getMirroringObject());
        MethodExecution me = new MethodExecution(sm , loader, selfObjekt);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        MethodDepthExceeded e = (MethodDepthExceeded) o.getMirroringObject();
    }

    @Test
    public void test2_3(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = true;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        AmbiguousValue self = new AmbiguousValue(testSmaliClass.getClassPath());
        MethodExecution me = new MethodExecution(sm , loader, self);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", av.getType());
    }

    @Test
    public void test2_4(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = false;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test2()Ljava/lang/String;");
        AmbiguousValue self = new AmbiguousValue(testSmaliClass.getClassPath());
        MethodExecution me = new MethodExecution(sm , loader, self);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        MethodDepthExceeded e = (MethodDepthExceeded) o.getMirroringObject();
    }




    @Test
    public void test3_1() throws Exception{
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = true;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/String;");
        Objekt selfObjekt = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = selfObjekt;
        Constructor ctor = clazz.getMirroringClass().getConstructor();
        ctor.newInstance();
        Assert.assertNotNull(selfObjekt.getMirroringObject());
        MethodExecution me = new MethodExecution(sm , loader, selfObjekt);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", av.getType());
    }

    @Test
    public void test3_2() throws Exception{
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = false;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/String;");
        Objekt selfObjekt = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = selfObjekt;
        Constructor ctor = clazz.getMirroringClass().getConstructor();
        ctor.newInstance();
        Assert.assertNotNull(selfObjekt.getMirroringObject());
        MethodExecution me = new MethodExecution(sm , loader, selfObjekt);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        MethodDepthExceeded e = (MethodDepthExceeded) o.getMirroringObject();
    }

    @Test
    public void test3_3(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = true;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/String;");
        AmbiguousValue self = new AmbiguousValue(testSmaliClass.getClassPath());
        MethodExecution me = new MethodExecution(sm , loader, self);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("Ljava/lang/String;", av.getType());
    }

    @Test
    public void test3_4(){
        SimSmaliConfig.useAmbiguousValueOnMethodInvocationDepthExceed = false;
        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/String;");
        AmbiguousValue self = new AmbiguousValue(testSmaliClass.getClassPath());
        MethodExecution me = new MethodExecution(sm , loader, self);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        MethodDepthExceeded e = (MethodDepthExceeded) o.getMirroringObject();
    }


}
