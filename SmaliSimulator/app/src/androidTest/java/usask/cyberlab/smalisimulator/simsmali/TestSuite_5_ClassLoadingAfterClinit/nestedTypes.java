package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class nestedTypes {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception  {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyDirectory("nestedTypes");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
    }

    @Test
    public void test1(){
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/NestingAndAnonymousClassesTest;");
        SmaliClass sc = clazz.getSmaliClass();
        SmaliMethod sm = sc.getMethod("test1()LnestedTypes/OuterClass$NestedClass;");
        MethodExecution me =new MethodExecution(sm, clazzLoader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(mr.getType(),ResultType.OBJECT);
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals(o.getType(), "LnestedTypes/OuterClass$NestedClass;");
        Assert.assertEquals(o.getInstanceFieldValue("b", null), 11);
        Assert.assertNotNull(o.getInstanceFieldValue("this$0", null));
        Objekt o2 = (Objekt) o.getInstanceFieldValue("this$0", null);
        Assert.assertEquals(o2.getType(), "LnestedTypes/OuterClass;");

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


    @Test
    public void test2() {
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/NestingAndAnonymousClassesTest;");
        SmaliClass sc = clazz.getSmaliClass();
        SmaliMethod sm = sc.getMethod("test2()Ljava/util/ArrayList;");
        MethodExecution me =new MethodExecution(sm, clazzLoader);
        me.execute();

        System.out.println(me.getExecutionTrace().getExecutionLogs());
        System.out.println("===========");
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(mr.getType(), ResultType.OBJECT);
        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/util/ArrayList;", o.getType());
        ArrayList a = (ArrayList) o.getMirroringObject();
        Assert.assertEquals(3, a.size());
        Assert.assertEquals(1, a.get(0));
        Assert.assertEquals(2, a.get(1));
        Assert.assertEquals(3, a.get(2));

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test3(){
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/OuterClass2;");
        SmaliClass sc = clazz.getSmaliClass();
        SmaliMethod sm = sc.getMethod("test()I");
        Assert.assertNotNull(sm);
        MethodExecution me =new MethodExecution(sm, clazzLoader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(mr.getType(),ResultType.INTEGER);
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(5, i.intValue());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test4() throws Exception{
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/OuterClass3;");
        SmaliClass sc = clazz.getSmaliClass();
        SmaliMethod sm = sc.getMethod("test()I");
        Assert.assertNotNull(sm);
        Objekt self = new Objekt(clazz);
        ConstructorInterceptor.selfWrapper = self;
        clazz.getMirroringClass().newInstance();
        Assert.assertNotNull(self.getMirroringObject());
        MethodExecution me =new MethodExecution(sm, clazzLoader, self);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(mr.getType(),ResultType.INTEGER);
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(30, i.intValue());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


    @Test
    public void test5(){
        SmaliClazz clazz = (SmaliClazz) clazzLoader.getClazz("LnestedTypes/OuterClass3;");
        SmaliClass sc = clazz.getSmaliClass();
        SmaliMethod sm = sc.getMethod("test2()I");
        Assert.assertNotNull(sm);
        MethodExecution me =new MethodExecution(sm, clazzLoader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(mr.getType(),ResultType.INTEGER);
        Integer i = (Integer) mr.getResult();
        Assert.assertEquals(20, i.intValue());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }


}
