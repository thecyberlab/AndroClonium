//package usask.cyberlab.smalisimulator.simsmali.TestSuite_8_AmbiguousValue;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
//import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
//import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
//import usask.cyberlab.smalisimulator.simsmali.emulator.Simulator;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class AmbiguousValueInstanceOfTest {
//
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/ambiguousValue" , context);
//        AssetCopier.copyFile("ambiguousValue/AmbiguousValueInstanceOf.smali");
//        testSmaliClass = loader.getSmaliClass("LAmbiguousValueInstanceOf;");
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        testSmaliClass=null;
//        loader=null;
//    }
//
//    @Test
//    public void test1(){
//        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
//        SmaliMethod sm = testSmaliClass.getMethod("test1(Ljava/lang/Object;)Z");
//        Simulator simulator = new Simulator(loader);
//        Object[] args = new Object[1];
//        args[0] = new AmbiguousValue("Ljava/lang/Object;");
//        ArrayList<MethodExecution> results = simulator.simulateStaticMethod(sm.getClassMethodSignature(), args);
//        Assert.assertEquals(2, results.size());
//        MethodExecution me0 = results.get(0);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecution me1 = results.get(1);
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//
//        Assert.assertEquals(ResultType.BOOLEAN, mr0.getType());
//        Assert.assertEquals(ResultType.BOOLEAN, mr1.getType());
//        Boolean b1 = (Boolean) mr0.getResult();
//        Boolean b2 = (Boolean) mr1.getResult();
//        Assert.assertTrue(b1);
//        Assert.assertFalse(b2);
//        AmbiguousValue av1 = me0.getRegister(1).getAmbiguousValue();
//        AmbiguousValue av2 = me1.getRegister(1).getAmbiguousValue();
//        Assert.assertEquals("[Ljava/lang/Object;", av1.getType());
//        Assert.assertEquals("Ljava/lang/Object;", av2.getType());
//    }
//
//    @Test
//    public void test2(){
//        SmaliClazz clazz = (SmaliClazz) loader.getClazz(testSmaliClass.getClassPath());
//        SmaliMethod sm = testSmaliClass.getMethod("test2(Ljava/lang/Object;)Z");
//        Simulator simulator = new Simulator(loader);
//        Object[] args = new Object[1];
//        args[0] = new AmbiguousValue("Ljava/lang/Object;");
//        ArrayList<MethodExecution> results = simulator.simulateStaticMethod(sm.getClassMethodSignature(), args);
//        Assert.assertEquals(2, results.size());
//        MethodExecution me0 = results.get(0);
//        MethodExecutionResult mr0 = me0.getExecutionResult();
//        MethodExecution me1 = results.get(1);
//        MethodExecutionResult mr1 = me1.getExecutionResult();
//
//        Assert.assertEquals(ResultType.BOOLEAN, mr0.getType());
//        Assert.assertEquals(ResultType.BOOLEAN, mr1.getType());
//        Boolean b1 = (Boolean) mr0.getResult();
//        Boolean b2 = (Boolean) mr1.getResult();
//        Assert.assertTrue(b1);
//        Assert.assertFalse(b2);
//        AmbiguousValue av1 = me0.getRegister(1).getAmbiguousValue();
//        AmbiguousValue av2 = me1.getRegister(1).getAmbiguousValue();
//        Assert.assertEquals("[I", av1.getType());
//        Assert.assertEquals("Ljava/lang/Object;", av2.getType());
//    }
//
//
//}
