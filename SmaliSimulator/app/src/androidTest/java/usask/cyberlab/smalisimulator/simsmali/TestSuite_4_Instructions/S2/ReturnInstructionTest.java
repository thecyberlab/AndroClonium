package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class ReturnInstructionTest {

    private static ClazzLoader loader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
        AssetCopier.copyFile("instructions/ReturnTest.smali");
    }

    @AfterClass
    public static void afterClass(){
        loader = null;
    }

    @Test
    public void test1(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test1()V");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.VOID, mr.getType());
        Assert.assertNull(mr.getResult());
    }

//    @Test
//    public void _(){
//        loader.getClazz("LReturnTest;");
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

    @Test
    public void test2(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test2()I");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.INTEGER, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Integer);
        Assert.assertEquals(0, mr.getResult());
    }

    @Test
    public void test3(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test3()F");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.FLOAT, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Float);
        Assert.assertEquals(0.0f, mr.getResult());
    }

    @Test
    public void test4(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test4()J");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.LONG, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Long);
        Assert.assertEquals(0l, mr.getResult());
    }

    @Test
    public void test5(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test5()D");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.DOUBLE, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Double);
        Assert.assertEquals(0d, mr.getResult());
    }

    @Test
    public void test6(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test6()Z");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.BOOLEAN, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Boolean);
        Assert.assertEquals(false, mr.getResult());
    }

    @Test
    public void test7(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test7()C");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.CHAR, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Character);
        Assert.assertEquals(Character.valueOf((char) 0), mr.getResult());
    }

    @Test
    public void test8(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test8()S");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.SHORT, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Short);
        Assert.assertEquals(Short.valueOf((short) 0), mr.getResult());
    }

    @Test
    public void test9(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test9()B");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.BYTE, mr.getType());
        Assert.assertTrue(mr.getResult() instanceof Byte);
        Assert.assertEquals(Byte.valueOf((byte) 0), mr.getResult());
    }

    @Test
    public void test10(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test10()Ljava/lang/Object;");
        MethodExecution me = new MethodExecution(sm, loader);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.OBJECT, mr.getType());
        Assert.assertNull(mr.getResult());
    }

    @Test
    public void test11(){
        SmaliClazz smaliClazz = (SmaliClazz) loader.getClazz("LReturnTest;");
        SmaliMethod sm = smaliClazz.getSmaliMethod("test10()Ljava/lang/Object;");
        MethodExecution me = new MethodExecution(sm, loader);
        AmbiguousValue av = new AmbiguousValue("Ljava/lang/String");
        me.getRegister(0).set(av);
        me.execute();
        MethodExecutionResult mr = me.getExecutionResult();
        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
        Assert.assertEquals(av, mr.getResult());
    }


}
