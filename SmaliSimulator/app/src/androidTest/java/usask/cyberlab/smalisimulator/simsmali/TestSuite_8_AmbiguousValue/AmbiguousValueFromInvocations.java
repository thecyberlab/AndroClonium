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
//import java.io.File;
//import java.util.ArrayList;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.emulator.BranchedExecutionsWrapper;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
//import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class AmbiguousValueFromInvocations {
//
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/ambiguousValue" , context);
//        AssetCopier.copyFile("ambiguousValue/AmbiguousValueFromInvocationsTest.smali");
//        testSmaliClass = loader.getSmaliClass("LAmbiguousValueFromInvocationsTest;");
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        testSmaliClass=null;
//        loader=null;
//    }
//
//    @Test
//    public void fileTest0(){
//        SmaliMethod sm = testSmaliClass.getMethod("fileTest0()Ljava/io/File;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Objekt objekt = (Objekt) mr.getResult();
//        File file = (File) objekt.getMirroringObject();
//        Assert.assertEquals("test.txt", file.getPath());
//
//    }
//
//    @Test
//    public void fileTest1(){
//        SmaliMethod sm = testSmaliClass.getMethod("fileTest1()Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Z", ambiguousValue.getType());
//    }
//
//    @Test
//    public void fileWriterTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("fileWriterTest()Ljava/io/FileWriter;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/io/FileWriter;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void fileReaderTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("fileReaderTest()Ljava/io/FileReader;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/io/FileReader;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void getAppPermissions(){
//        SmaliMethod sm = testSmaliClass.getMethod("getAppPermissions(Landroid/content/Context;)Ljava/util/List;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(2).set(new AmbiguousValue("Landroid/content/Context;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/util/List;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void getPackageManager(){
//        SmaliMethod sm = testSmaliClass.getMethod("getPackageManager(Landroid/content/Context;)Landroid/content/pm/PackageManager;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        MethodExecution initialExecution = executionsWrapper.getCurrentExecution();
//        initialExecution.getRegister(1).set(new AmbiguousValue("Landroid/content/Context;"));
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Landroid/content/pm/PackageManager;", ambiguousValue.getType());
//    }
//
//    @Test
//    public void isExternalStorageEmulated(){
//        SmaliMethod sm = testSmaliClass.getMethod("isExternalStorageEmulated()Z");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Z", ambiguousValue.getType());
//    }
//
//    @Test
//    public void printTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("printTest()V");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//    }
//
//    @Test
//    public void readInputTest(){
//        SmaliMethod sm = testSmaliClass.getMethod("readInputTest()Ljava/io/BufferedReader;");
//        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(sm, loader);
//        ArrayList<MethodExecution> executions = executionsWrapper.startExecution();
//        Assert.assertEquals(1, executions.size());
//        MethodExecution methodExecution = executions.get(0);
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue ambiguousValue = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/io/BufferedReader;", ambiguousValue.getType());
//    }
//
//}
