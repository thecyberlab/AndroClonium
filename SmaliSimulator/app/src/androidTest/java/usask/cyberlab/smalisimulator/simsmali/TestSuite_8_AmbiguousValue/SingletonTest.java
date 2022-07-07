//package usask.cyberlab.smalisimulator.simsmali.TestSuite_8_AmbiguousValue;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.emulator.BranchedExecutionsWrapper;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//public class SingletonTest {
//
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/ambiguousValue" , context);
//        AssetCopier.copyFile("ambiguousValue/MyDatabaseHelper.smali");
//        testSmaliClass = loader.getSmaliClass("LMyDatabaseHelper;");
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
//
//
//        SmaliMethod sm = testSmaliClass.getMethod("getInstance(Landroid/content/Context;)LMyDatabaseHelper;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader, new AmbiguousValue("LMyDatabaseHelper;"));
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Landroid/content/Context;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        System.out.println(executions.size());
//
//
//        sm = testSmaliClass.getMethod("getInstance(Landroid/content/Context;)LMyDatabaseHelper;");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader, new AmbiguousValue("LMyDatabaseHelper;"));
//        initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("Landroid/content/Context;"));
//        executions = executionsWrapper.startExecution();
//        System.out.println(executions.size());
//
//        sm = testSmaliClass.getMethod("getInstance(Landroid/content/Context;)LMyDatabaseHelper;");
//        executionsWrapper = new BranchedExecutionsWrapper(sm, loader, new AmbiguousValue("LMyDatabaseHelper;"));
//        initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("Landroid/content/Context;"));
//        executions = executionsWrapper.startExecution();
//        System.out.println(executions.size());
//
//
//
//    }
//
//
//
//}
