//package usask.cyberlab.smalisimulator.simsmali.TestSuite_9_AndroidMocks;
//
//import android.content.Context;
//import android.os.Looper;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockActivity;
//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockResources;
//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockingMetaInfo;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
//import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
//import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
//import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class ActivityTest {
//
//    private static ClazzLoader clazzLoader;
//    private static Context context;
//
//    @BeforeClass
//    public static void beforeClass() throws IOException {
//        Looper.prepare();
//        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        AssetCopier.copyFile("androidMocks/ActivityTest0.smali");
//
//        clazzLoader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/androidMocks", context);
//        MockingMetaInfo.appName = "androidMockTest";
//        MockingMetaInfo.appVersion = "1";
//        MockingMetaInfo.simulatorContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        MockingMetaInfo.clazzLoader = clazzLoader;
//        MockingMetaInfo.uploadedResBasePath = context.getCacheDir().getAbsolutePath() + "/testResFiles";
//
//        StringBuilder destPath = new StringBuilder();
//        destPath.append(MockingMetaInfo.uploadedResBasePath);
//        destPath.append("/");
//        destPath.append(MockingMetaInfo.appName);
//        destPath.append("/");
//        destPath.append(MockingMetaInfo.appVersion);
//        destPath.append("/values/strings.xml");
//        AssetCopier.copyFile("androidMocks/strings.xml", destPath.toString());
//
//
//        destPath = new StringBuilder();
//        destPath.append(MockingMetaInfo.uploadedResBasePath);
//        destPath.append("/");
//        destPath.append(MockingMetaInfo.appName);
//        destPath.append("/");
//        destPath.append(MockingMetaInfo.appVersion);
//        destPath.append("/values/public.xml");
//        AssetCopier.copyFile("androidMocks/public.xml", destPath.toString());
//    }
//
//    public static void afterClass(){
//        clazzLoader = null;
//        context = null;
//    }
//
//    @Test
//    public void testLoadingMock() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Assert.assertEquals("LActivityTest0;",smaliClazz.getClassPath());
//        // TODO should the mapping becomes visible to smaliClazz objects ?
//        Assert.assertEquals(MockActivity.class, smaliClazz.getMirroringClass().getSuperclass());
//    }
//
//    @Test
//    public void testOnCreate() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("onCreate(Landroid/os/Bundle;)V");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        ExecutionTrace trace = methodExecution.getExecutionTrace();
//        System.out.println(trace.getExecutionLogs());
//        Assert.assertEquals(4,trace.size());
//    }
//
//    @Test
//    public void testGetFilesDir() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test1()Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        File f = (File) o.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedAppsInternalFiles/androidMockTest/1/files"), f);
//    }
//
//    @Test
//    public void testGetExternalFilesDir() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test2(Ljava/lang/String;)Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        File f = (File) o.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedExternalDir/androidMockTest/1/files"), f);
//    }
//
//    @Test
//    public void testGetCacheDir() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test3()Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        File f = (File) o.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedAppsInternalFiles/androidMockTest/1/cache"), f);
//    }
//
//    @Test
//    public void testGetExternalCacheDir() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test4()Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        File f = (File) o.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedExternalDir/androidMockTest/1/cache"), f);
//    }
//
//    @Test
//    public void testGetExternalMediaDirs() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test5()[Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        ArrayObjekt o = (ArrayObjekt) mr.getResult();
//        Assert.assertEquals(o.getSize(), 1);
//        Objekt o2 = (Objekt) o.getValue(0);
//        File f = (File) o2.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedExternalDir/androidMockTest/1/media"), f);
//    }
//
//    @Test
//    public void testGetExternalFilesDirs() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test6(Ljava/lang/String;)[Ljava/io/File;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        ArrayObjekt o = (ArrayObjekt) mr.getResult();
//        Assert.assertEquals(o.getSize(), 1);
//        Objekt o2 = (Objekt) o.getValue(0);
//        File f = (File) o2.getMirroringObject();
//        Assert.assertEquals(new File("/data/user/0/usask.cyberlab.smalisimulator/files/simulatedExternalDir/androidMockTest/1/files"), f);
//    }
//
//    @Test
//    public void testGetResources() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test7()Landroid/content/res/Resources;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        Assert.assertEquals(MockResources.class, o.getMirroringObject().getClass());
//    }
//
//    @Test
//    public void testGetResourcesGetString() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test8()Ljava/lang/String;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        Assert.assertEquals("TestValueInStringsXML", o.getMirroringObject());
//    }
//
//    @Test
//    public void testGetString() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test9()Ljava/lang/String;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.OBJECT, mr.getType());
//        Objekt o = (Objekt) mr.getResult();
//        Assert.assertEquals("TestValueInStringsXML", o.getMirroringObject());
//    }
//
//    @Test
//    public void testGetResourcesGetString2() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test10()Ljava/lang/String;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//    }
//
//    @Test
//    public void testGetString2() throws Exception{
//        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LActivityTest0;");
//        Objekt refObjekt = new Objekt(smaliClazz, smaliClazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = refObjekt;
//        Object refObject = smaliClazz.getMirroringClass().newInstance();
//        refObjekt.setMirroringObject(refObject);
//        SmaliMethod smaliMethod = smaliClazz.getSmaliMethod("test11()Ljava/lang/String;");
//        MethodExecution methodExecution = new MethodExecution(smaliMethod,clazzLoader, refObjekt);
//        methodExecution.execute();
//        MethodExecutionResult mr = methodExecution.getExecutionResult();
//        Assert.assertEquals(ResultType.AMBIGUOUS_VALUE, mr.getType());
//        AmbiguousValue av = (AmbiguousValue) mr.getResult();
//        Assert.assertEquals("Ljava/lang/String;", av.getType());
//    }
//
////    @Test
////    public void test() throws Exception{
//////        new MockResources(context.getAssets(), null, null);
////    }
//}
