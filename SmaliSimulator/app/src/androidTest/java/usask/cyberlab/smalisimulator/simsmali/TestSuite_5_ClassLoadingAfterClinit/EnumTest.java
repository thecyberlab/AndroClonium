package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class EnumTest {

    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() , context);
        AssetCopier.copyFile("SimpleEnumTest.smali");
        AssetCopier.copyFile("SimpleEnum.smali");
        testSmaliClass = loader.getSmaliClass("LSimpleEnumTest;");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
        testSmaliClass = null;
    }

    @Test
    public void test0(){
        loader.getClazz("LSimpleEnum;");
    }

    @Test
    public void test1(){
        SmaliMethod sm = testSmaliClass.getMethod("test1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
    }

    @Test
    public void test2_() throws Exception{
        SmaliClazz enumClazz = (SmaliClazz) loader.getClazz("LSimpleEnum;");
        Class c = enumClazz.getMirroringClass();
        Method[] methods = c.getDeclaredMethods();
        Method m = c.getMethod("valueOf", String.class);
        Object o1 = m.invoke(null, "STATE1");
        Object o2 = m.invoke(null, "STATE2");
        Object o3 = m.invoke(null, "STATE3");
        System.out.println("OK");
    }

    @Test
    public void test2() throws Exception{
        SmaliMethod sm = testSmaliClass.getMethod("test2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Objekt o = (Objekt) mr.getResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
    }

    @Test
    public void test3(){
        SmaliMethod sm = testSmaliClass.getMethod("test3()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        String s = mr.getResult().toString();
        Assert.assertEquals("STATE1", s);
    }

    @Test
    public void test4(){
        SmaliMethod sm = testSmaliClass.getMethod("test4()Ljava/lang/String;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        String s = (mr.getResult()).toString();
        Assert.assertEquals("STATE1", s);
    }

    @Test
    public void test5(){
        SmaliMethod sm = testSmaliClass.getMethod("test5()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
    }

    @Test
    public void test6(){
        SmaliMethod sm = testSmaliClass.getMethod("test6()Z");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(true ,mr.getResult());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("test7()Ljava/lang/Class;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt classObjekt = (Objekt) mr.getResult();
        Class<?> cls = (Class<?>) classObjekt.getMirroringObject();
        String smaliStyleClsType = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("LSimpleEnum;", smaliStyleClsType);
    }

    @Test
    public void test8(){
        SmaliMethod sm = testSmaliClass.getMethod("test8()Ljava/lang/Class;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt classObjekt = (Objekt) mr.getResult();
        Class<?> cls = (Class<?>) classObjekt.getMirroringObject();
        String smaliStyleClsType = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("LSimpleEnum;", smaliStyleClsType);
    }

    @Test
    public void test9(){
        SmaliMethod sm = testSmaliClass.getMethod("test9()LSimpleEnum;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt enumObjekt = (Objekt) mr.getResult();
        Assert.assertEquals("LSimpleEnum;",enumObjekt.getClazz().getClassPath());
        Assert.assertTrue(enumObjekt.getMirroringObject() instanceof Enum);
    }

    @Test
    public void test10(){
        SmaliMethod sm = testSmaliClass.getMethod("test10()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(0,mr.getResult());
    }

    @Test
    public void test11(){
        SmaliMethod sm = testSmaliClass.getMethod("test11()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt enumObjekt = (Objekt) loader.getClazz("LSimpleEnum;")
                .getStaticFieldValue("STATE1");
        int objHashCode = enumObjekt.getMirroringObject().hashCode();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(objHashCode,mr.getResult());
    }

    @Test
    public void test12(){
        SmaliMethod sm = testSmaliClass.getMethod("test12()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(0, mr.getResult());
    }

    @Test
    public void test13(){
        SmaliMethod sm = testSmaliClass.getMethod("test13()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(1, mr.getResult());
    }

    @Test
    public void test14(){
        SmaliMethod sm = testSmaliClass.getMethod("test14()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertEquals(-1, mr.getResult());
    }
}
