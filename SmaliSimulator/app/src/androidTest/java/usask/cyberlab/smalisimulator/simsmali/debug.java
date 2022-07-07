package usask.cyberlab.smalisimulator.simsmali;

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeDirectInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;


public class debug {

//    private static ClazzLoader clazzLoader;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception{
////        AssetCopier.copyDirectory("realApkSamples/at.jclehner.rxdroid_9331");
////        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
////        String basePath = appContext.getCacheDir().getAbsolutePath() + "/realApkSamples/at.jclehner.rxdroid_9331";
////        clazzLoader = new ClazzLoader(basePath, appContext);
//    }
//
//    @AfterClass
//    public static void afterClass(){
//        clazzLoader = null;
//    }


    @Test
    public void test() throws Exception{
        AssetCopier.copyFile("CtorLoopTest.smali");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String basePath = appContext.getCacheDir().getAbsolutePath();
        ClazzLoader clazzLoader = new ClazzLoader(basePath, appContext);
        SmaliClass sc = clazzLoader.getSmaliClass("LCtorLoopTest;");
        SmaliMethod sm = sc.getMethod("<init>()V");
        Assert.assertNotNull(sm);
        InvokeDirectInstruction instruction = SmaliMethod.getSuperConstructorCall(sm);
        System.out.println("111");
//        Assert.assertEquals(-23, Build.VERSION.SDK_INT);
//        Assert.assertEquals("11", Object.class.getSuperclass().toString());
    }


}
