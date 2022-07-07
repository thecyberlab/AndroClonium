package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeSuperTest2 {

    private static ClazzLoader loader;
    private static SmaliClass invokeSuper2SmaliClass;
    private static SmaliClass invokeSuper2ChildSmaliClass;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeSuper2GrandParent.smali");
        AssetCopier.copyFile("instructions/InvokeSuper2Parent.smali");
        AssetCopier.copyFile("instructions/InvokeSuper2.smali");
        AssetCopier.copyFile("instructions/InvokeSuper2Child.smali");

        invokeSuper2SmaliClass = loader.getSmaliClass("LInvokeSuper2;");
        invokeSuper2ChildSmaliClass = loader.getSmaliClass("LInvokeSuper2Child;");
    }


    public static void afterClass(){
        loader = null;
        invokeSuper2SmaliClass = null;
        invokeSuper2ChildSmaliClass = null;
    }

    @Test
    public void test1() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2ChildSmaliClass.getMethod("test1()I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(60, i.intValue());
    }

    @Test
    public void test2() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2SmaliClass.getMethod("test2()I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(62, i.intValue());
    }

    @Test
    public void test3() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2ChildSmaliClass.getMethod("test3()I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(-20, i.intValue());
    }

    @Test
    public void test4_1() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2SmaliClass.getMethod("test4_1()I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(110, i.intValue());
    }

    @Test
    public void test4_2() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2ChildSmaliClass.getMethod("test4_2()I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(120, i.intValue());
    }

    @Test
    public void test5() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2ChildSmaliClass.getMethod("test5(I)I");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);
        methodExecution.getRegister(2).set(4);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(74, i.intValue());
    }

    @Test
    public void test6() throws Exception{
        SmaliClazz clazz = (SmaliClazz) loader.getClazz("LInvokeSuper2Child;");
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        SmaliMethod sm = invokeSuper2ChildSmaliClass.getMethod("test6(Ljava/lang/String;)Ljava/lang/String;");
        Assert.assertNotNull(sm);
        MethodExecution methodExecution = new MethodExecution(sm,loader, self);

        ReflectedClazz stringClazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(stringClazz, "Test");
        methodExecution.getRegister(3).set(o);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Objekt res = (Objekt) mr.getResult();
        String s = (String) res.getMirroringObject();
        Assert.assertEquals("Test|InvokeSuper2Parent|InvokeSuper2Child", s);
    }


}
