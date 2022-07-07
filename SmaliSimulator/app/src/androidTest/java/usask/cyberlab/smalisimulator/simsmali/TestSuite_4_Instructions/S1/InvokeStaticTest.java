package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class InvokeStaticTest {
    private static SmaliClass testSmaliClass;
    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/InvokeStatic.smali");
        AssetCopier.copyFile("instructions/EnumForInvokeStaticTest.smali");
        testSmaliClass = loader.getSmaliClass("LInvokeStatic;");
    }

    @Test
    public void test0(){
        SmaliMethod sm = testSmaliClass.getMethod("returnConstantTest()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(3, mr.getResult());
    }


    @Test
    public void test1() {
        SmaliMethod sm = testSmaliClass.getMethod("adder(II)I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.getRegister(1).set(3);
        methodExecution.getRegister(2).set(4);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(7, mr.getResult());
    }

    @Test
    public void test2() {
        SmaliMethod sm = testSmaliClass.getMethod("adder2(II)V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.getRegister(1).set(11);
        methodExecution.getRegister(2).set(-2);
        methodExecution.execute();
        Clazz sc = loader.getClazz("LInvokeStatic;");
        Assert.assertEquals(9, sc.getStaticFieldValue("testField"));
    }


    @Test
    public void test3() {
        SmaliMethod sm = testSmaliClass.getMethod("IntegerParse(Ljava/lang/String;)I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt objekt = new Objekt(clazz, "12");
        methodExecution.getRegister(1).set(objekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(12, mr.getResult());
    }

    @Test
    public void test4() {
        SmaliMethod sm = testSmaliClass.getMethod("IntegerParse2(Ljava/lang/String;)V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt objekt = new Objekt(clazz, "132");
        methodExecution.getRegister(1).set(objekt);
        methodExecution.execute();
        Clazz sc = loader.getClazz("LInvokeStatic;");
        Assert.assertEquals(132, sc.getStaticFieldValue("testField"));
    }

    @Test
    public void test5() throws Exception{
        SmaliMethod sm = testSmaliClass.getMethod("exceptionTest1()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt objekt = (Objekt) mr.getResult();
        ArithmeticException e = (ArithmeticException) objekt.getMirroringObject();
        Assert.assertEquals("Ljava/lang/ArithmeticException;", objekt.getType());
    }

    @Test
    public void test6() {
        SmaliMethod sm = testSmaliClass.getMethod("IntegerParse(Ljava/lang/String;)I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt objekt = new Objekt(clazz, "12s");
        methodExecution.getRegister(1).set(objekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/NumberFormatException;", ro.getType());
    }

    @Test
    public void test7(){
        SmaliMethod sm = testSmaliClass.getMethod("exceptionTest2()V");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/lang/NumberFormatException;", ro.getType());
    }


    @Test
    public void test9() {
        SmaliMethod sm = testSmaliClass.getMethod("enumValueOfTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/nio/file/AccessMode;",objekt.getType());
        Assert.assertEquals("READ",objekt.toString());
    }

    @Test
    public void test10() {
        SmaliMethod sm = testSmaliClass.getMethod("enumValueOfTest2()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("Ljava/nio/file/AccessMode;",objekt.getType());
        Assert.assertEquals("READ",objekt.toString());
    }

    @Test
    public void test11() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LEnumForInvokeStaticTest;");
        Class cls = smaliClazz.getMirroringClass();
        Object o = cls.getEnumConstants();
        SmaliMethod sm = testSmaliClass.getMethod("enumValueOfTest3()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt objekt = (Objekt) mr.getResult();
        Assert.assertEquals("STATE1", objekt.getClazz().getStaticFieldValue("STATE1").toString());
    }

    @Test
    public void test12() {
        SmaliMethod sm = testSmaliClass.getMethod("enumValuesTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        ArrayObjekt rao = (ArrayObjekt) mr.getResult();
        Assert.assertEquals("[Ljava/nio/file/AccessMode;", rao.getType());
        Assert.assertEquals(3, rao.getSize());

        Objekt o1 = (Objekt) rao.getValue(0);
        Assert.assertEquals("Ljava/nio/file/AccessMode;",o1.getType());
        Assert.assertEquals("READ",o1.toString());

        Objekt o2 = (Objekt) rao.getValue(1);
        Assert.assertEquals("Ljava/nio/file/AccessMode;",o2.getType());
        Assert.assertEquals("WRITE",o2.toString());

        Objekt o3 = (Objekt) rao.getValue(2);
        Assert.assertEquals("Ljava/nio/file/AccessMode;",o3.getType());
        Assert.assertEquals("EXECUTE",o3.toString());
    }

    @Test
    public void test13() {
        SmaliMethod sm = testSmaliClass.getMethod("IntegerParse(Ljava/lang/String;)I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.getRegister(1).set(new AmbiguousValue("Ljava/lang/String;"));
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        AmbiguousValue av = (AmbiguousValue) mr.getResult();
        Assert.assertEquals("I", av.getType());
    }

    @Test
    public void test14(){
        SmaliMethod sm = testSmaliClass.getMethod("classForNameTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt stringObjekt = new Objekt(stringClazz, "java.lang.Object");
        methodExecution.getRegister(0).set(stringObjekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("class java.lang.Object",ro.toString());
    }

    @Test
    public void test15(){
        SmaliMethod sm = testSmaliClass.getMethod("classForNameTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt stringObjekt = new Objekt(stringClazz,"InvokeStatic");
        methodExecution.getRegister(0).set(stringObjekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("class InvokeStatic",ro.toString());
    }

    @Test
    public void test16(){
        SmaliMethod sm = testSmaliClass.getMethod("classForNameTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt stringObjekt = new Objekt(stringClazz, "[Ljava.lang.Object;");
        methodExecution.getRegister(0).set(stringObjekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("class [Ljava.lang.Object;",ro.toString());
    }

    @Test
    public void test17(){
        SmaliMethod sm = testSmaliClass.getMethod("classForNameTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt stringObjekt = new Objekt(stringClazz, "[LInvokeStatic;");
        methodExecution.getRegister(0).set(stringObjekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Objekt ro = (Objekt) mr.getResult();
        Assert.assertEquals("class [LInvokeStatic;",ro.toString());
    }

    @Test
    public void test18(){
        SmaliMethod sm = testSmaliClass.getMethod("classForNameTest()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        Clazz stringClazz = loader.getClazz("Ljava/lang/String;");
        Objekt stringObjekt = new Objekt(stringClazz, "LNonExistentClass;");
        methodExecution.getRegister(0).set(stringObjekt);
        methodExecution.execute();
        MethodExecutionResult mr = methodExecution.getExecutionResult();
        Assert.assertEquals(ResultType.EXCEPTION, mr.getType());
        Objekt o = (Objekt) mr.getResult();
        Assert.assertTrue(o.getMirroringObject() instanceof ClassNotFoundException);
    }

    @Test
    public void test19() {
        SmaliMethod sm = testSmaliClass.getMethod("pairRegisterArgPassingTest1()J");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Assert.assertEquals(46L,methodExecution.getExecutionResult().getResult());
    }

    @Test
    public void test20(){
        SmaliMethod sm = testSmaliClass.getMethod("getEnumClass()Ljava/lang/Object;");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Objekt classObjekt = (Objekt) methodExecution.getExecutionResult().getResult();
        Assert.assertEquals("Ljava/lang/Class;",classObjekt.getType());
        Class cls = (Class) classObjekt.getMirroringObject();
        Assert.assertEquals("EnumForInvokeStaticTest", cls.getName());
        Assert.assertTrue(cls.isEnum());
    }

    @Test
    public void test21(){
        SmaliMethod sm = testSmaliClass.getMethod("staticMethodInEnumCall()I");
        MethodExecution methodExecution = new MethodExecution(sm,loader);
        methodExecution.execute();
        Assert.assertEquals(10, methodExecution.getExecutionResult().getResult());
    }



}