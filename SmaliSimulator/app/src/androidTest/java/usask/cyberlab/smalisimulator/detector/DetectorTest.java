//package usask.cyberlab.smalisimulator.detector;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.support.test.InstrumentationRegistry;
//
//import org.json.JSONObject;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import java.io.*;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//public class DetectorTest {
//
//    static Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//    Detector detector = new Detector(appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/",
//            appContext.getAssets());
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        AssetCopier.copyDirectory("detector");
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        String path = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/";
//        deleteFolder(new File(path));
//    }
//
//    private static void deleteFolder(File folder){
//        if(!folder.isDirectory()){
//            throw new IllegalArgumentException();
//        }
//        File[] files = folder.listFiles();
//        if(files!=null){
//            for (File file:files) {
//                if(file.isDirectory()){
//                    deleteFolder(file);
//                }
//                else {
//                    file.delete();
//                }
//            }
//        }
//        folder.delete();
//    }
//
//    private static String getFileContent(File f) throws IOException {
//        if(!f.exists() || f.isDirectory())  throw new IllegalArgumentException();
//        BufferedReader reader = new BufferedReader(new FileReader(f));
//        StringBuilder sb = new StringBuilder();
//        String line = reader.readLine();
//        while (line!= null){
//            sb.append(line+"\n");
//            line = reader.readLine();
//        }
//        reader.close();
//        return sb.toString().trim();
//    }
//
//
//    @Test
//    public void test0() throws Exception {
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_0_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test0()V");
//        methodInfo.put("total registers",2);
//
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test0()V/trace_0_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test0()V>>invoke-virtual v0 v1 Ljava/io/PrintStream;->print(Ljava/lang/String;)V &2\n" +
//                "LTest;->test0()V>>const-string v1 \"*\" &1\n" +
//                "LTest;->test0()V>>sget-object v0 Ljava/lang/System;->out:Ljava/io/PrintStream; &0", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->print(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->print(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test1() throws Exception {
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_1_test.txt";
//        File traceFile = new File(inputPath);
//
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test1()V");
//        methodInfo.put("total registers",4);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test1()V/trace_1_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->onCreate>>test1()V>>invoke-virtual v2 v3 Ljava/io/PrintStream;->print(Ljava/lang/String;)V &6\n" +
//                "LTest;->onCreate>>test1()V>>const-string v3 \"* \" &5\n" +
//                "LTest;->onCreate>>test1()V>>sget-object v2 Ljava/lang/System;->out:Ljava/io/PrintStream; &4", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->print(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->print(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test2() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_2_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test2()V");
//        methodInfo.put("total registers",3);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test2()V/trace_2_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test2()V>>invoke-virtual v1 v2 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &5\n" +
//                "LTest;->test2()V>>const-string v2 \".\" &4\n" +
//                "LTest;->test2()V>>sget-object v1 Ljava/lang/System;->out:Ljava/io/PrintStream; &3", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test3() throws Exception {
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_3_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test3(I)V");
//        methodInfo.put("total registers",3);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test3(I)V/trace_3_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test3(I)V>>invoke-virtual v1 v2 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &5\n" +
//                "LTest;->test3(I)V>>const-string v2 \".\" &4\n" +
//                "LTest;->test3(I)V>>sget-object v1 Ljava/lang/System;->out:Ljava/io/PrintStream; &3", getFileContent(slice0));
//
//        File slice1 = new File(basePath +"slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath +"fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test3_1() throws Exception {
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_3_1_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test3(I)V");
//        methodInfo.put("total registers",3);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test3(I)V/trace_3_1_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test3(I)V>>invoke-virtual v1 v2 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &5\n" +
//                "LTest;->test3(I)V>>const-string v2 \".\" &4\n" +
//                "LTest;->test3(I)V>>sget-object v1 Ljava/lang/System;->out:Ljava/io/PrintStream; &3", getFileContent(slice0));
//
//        File slice1 = new File(basePath +"slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath +"fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test4() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_4_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test4(I)V");
//        methodInfo.put("total registers",3);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test4(I)V/trace_4_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test4>>invoke-virtual v0 v1 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &4\n" +
//                "LTest;->test4>>const-string v1 \".\" &3\n" +
//                "LTest;->test4>>sget-object v0 Ljava/lang/System;->out:Ljava/io/PrintStream; &2", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test4_1() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_4_1_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test4(I)V");
//        methodInfo.put("total registers",3);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test4(I)V/trace_4_1_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test4>>invoke-virtual v0 v1 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &4\n" +
//                "LTest;->test4>>const-string v1 \".\" &3\n" +
//                "LTest;->test4>>sget-object v0 Ljava/lang/System;->out:Ljava/io/PrintStream; &2", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test5() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_5_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test5()V");
//        methodInfo.put("total registers",2);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test5()V/trace_5_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test5>>invoke-virtual v2 v1 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &4\n" +
//                "LTest;->test5>>sget-object v2 Ljava/lang/System;->out:Ljava/io/PrintStream; &3\n" +
//                "LTest;->test5>>move-object v1 v0 &2\n" +
//                "LTest;->test5>>const-string v0 \"hello\" &0", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test6() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_6_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test6()V");
//        methodInfo.put("total registers",1);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test6()V/trace_6_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test6>>throw v0 &5\n" +
//                "LTest;->test6>>invoke-virtual v0 Ljava/lang/Exception;->printStackTrace()V &4\n" +
//                "LTest;->test6>>move-exception v0 &3\n" +
//                "LTest;->test6>>throw v0 &2\n" +
//                "LTest;->test6>>invoke-direct v0 Ljava/lang/NullPointerException;-><init>()V &1\n" +
//                "LTest;->test6>>new-instance v0 Ljava/lang/NullPointerException; &0", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertTrue(slice1.exists());
//        Assert.assertEquals("LTest;->test6>>invoke-virtual v0 Ljava/lang/Exception;->printStackTrace()V &4\n" +
//                "LTest;->test6>>move-exception v0 &3\n" +
//                "LTest;->test6>>throw v0 &2\n" +
//                "LTest;->test6>>invoke-direct v0 Ljava/lang/NullPointerException;-><init>()V &1\n" +
//                "LTest;->test6>>new-instance v0 Ljava/lang/NullPointerException; &0", getFileContent(slice1));
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("throw\n" +
//                "invoke-virtual:Ljava/lang/Exception;->printStackTrace()V", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("throw\n" +
//                "invoke-direct:Ljava/lang/NullPointerException;-><init>()V\n" +
//                "new-instance", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//
//        File fragment0of1 = new File(basePath + "fragment_0_of_slice1.txt");
//        Assert.assertTrue(fragment0of1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/lang/Exception;->printStackTrace()V", getFileContent(fragment0of1));
//
//        File fragment1of1 = new File(basePath + "fragment_1_of_slice1.txt");
//        Assert.assertTrue(fragment1of1.exists());
//        Assert.assertEquals("throw\n" +
//                "invoke-direct:Ljava/lang/NullPointerException;-><init>()V\n" +
//                "new-instance", getFileContent(fragment1of1));
//
//        File fragment2of1 = new File(basePath + "fragment_2_of_slice1.txt");
//        Assert.assertFalse(fragment2of1.exists());
//    }
//
//    @Test
//    public void test7() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_7_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test7()V");
//        methodInfo.put("total registers",1);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test7()V/trace_7_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test7>>throw v0 &9\n" +
//                "LTest;->test7>>invoke-direct v0 Ljava/lang/IllegalStateException;-><init>()V &8\n" +
//                "LTest;->test7>>new-instance v0 Ljava/lang/IllegalStateException; &7", getFileContent(slice0));
//
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertTrue(slice1.exists());
//        Assert.assertEquals("LTest;->test7>>invoke-virtual v0 Ljava/lang/Exception;->printStackTrace()V &6\n" +
//                "LTest;->test7>>move-exception v0 &5\n" +
//                "LTest;->test7>>throw v0 &2\n" +
//                "LTest;->test7>>invoke-direct v0 Ljava/lang/NullPointerException;-><init>()V &1\n" +
//                "LTest;->test7>>new-instance v0 Ljava/lang/NullPointerException; &0", getFileContent(slice1));
//
//
//
//        File slice2 = new File(basePath + "slice_2.txt");
//        Assert.assertFalse(slice2.exists());
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("throw\n" +
//                "invoke-direct:Ljava/lang/IllegalStateException;-><init>()V\n" +
//                "new-instance", getFileContent(fragment0));
//
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertFalse(fragment1.exists());
//
//
//        File fragment0of1 = new File(basePath + "fragment_0_of_slice1.txt");
//        Assert.assertTrue(fragment0of1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/lang/Exception;->printStackTrace()V", getFileContent(fragment0of1));
//
//        File fragment1of1 = new File(basePath + "fragment_1_of_slice1.txt");
//        Assert.assertTrue(fragment1of1.exists());
//        Assert.assertEquals("throw\n" +
//                "invoke-direct:Ljava/lang/NullPointerException;-><init>()V\n" +
//                "new-instance", getFileContent(fragment1of1));
//
//        File fragment2of1 = new File(basePath + "fragment_2_of_slice1.txt");
//        Assert.assertFalse(fragment2of1.exists());
//    }
//
//    @Test
//    public void test8() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_8_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test8(I)V");
//        methodInfo.put("total registers",5);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test8(I)V/trace_8_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test8>>invoke-virtual v1 v2 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &6\n" +
//                "LTest;->test8>>const-string v2 \".\" &5\n" +
//                "LTest;->test8>>sget-object v1 Ljava/lang/System;->out:Ljava/io/PrintStream; &4", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//
//    @Test
//    public void test8_1() throws Exception{
//        String inputPath = appContext.getCacheDir().getAbsolutePath() + "/detector/trace_8_1_test.txt";
//        File traceFile = new File(inputPath);
//        JSONObject methodInfo = new JSONObject();
//        methodInfo.put("signature","LTest;->test8(I)V");
//        methodInfo.put("total registers",5);
//        detector.analyzeCapturedTrace(traceFile, methodInfo);
//
//        String basePath = appContext.getCacheDir().getAbsolutePath() + "/traceAnalysisResults/Test/test8(I)V/trace_8_1_test_analysis/";
//
//        File slice0 = new File(basePath + "slice_0.txt");
//        Assert.assertTrue(slice0.exists());
//        Assert.assertEquals("LTest;->test8>>invoke-virtual v1 v2 Ljava/io/PrintStream;->println(Ljava/lang/String;)V &6\n" +
//                "LTest;->test8>>const-string v2 \".\" &5\n" +
//                "LTest;->test8>>sget-object v1 Ljava/lang/System;->out:Ljava/io/PrintStream; &4", getFileContent(slice0));
//
//        File slice1 = new File(basePath + "slice_1.txt");
//        Assert.assertFalse(slice1.exists());
//
//
//        File fragment0 = new File(basePath + "fragment_0_of_slice0.txt");
//        Assert.assertTrue(fragment0.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "sget-object:Ljava/io/PrintStream;", getFileContent(fragment0));
//
//        File fragment1 = new File(basePath + "fragment_1_of_slice0.txt");
//        Assert.assertTrue(fragment1.exists());
//        Assert.assertEquals("invoke-virtual:Ljava/io/PrintStream;->println(Ljava/lang/String;)V\n" +
//                "const-string", getFileContent(fragment1));
//
//        File fragment2 = new File(basePath + "fragment_2_of_slice0.txt");
//        Assert.assertFalse(fragment2.exists());
//    }
//}
