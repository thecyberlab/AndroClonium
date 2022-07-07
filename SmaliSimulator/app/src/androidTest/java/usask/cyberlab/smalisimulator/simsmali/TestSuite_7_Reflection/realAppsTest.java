package usask.cyberlab.smalisimulator.simsmali.TestSuite_7_Reflection;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.BranchedExecutionsWrapper;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//public class realAppsTest {
//
//    private static SmaliClass testSmaliClass;
//    private static ClazzLoader loader;
//
////    @BeforeClass
////    public static void beforeClass() throws Exception {
////        //starcom.snd.WebStreamPlayer;->onCompletion(Landroid.media.MediaPlayer;)V
////        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
////        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath()  + "/reflection/starcom.snd_3" , context);
////        AssetCopier.copyDirectory("reflection/starcom.snd_3");
////        testSmaliClass = loader.getSmaliClass("Lstarcom/snd/WebStreamPlayer;");
////    }
////
////    @Test
////    public void test(){
////        Clazz clazz = loader.getClazz(testSmaliClass.getClassPath());
////        SmaliMethod smaliMethod = testSmaliClass.getMethod("onCompletion(Landroid/media/MediaPlayer;)V");
////        Assert.assertNotNull(smaliMethod);
////        BranchedExecutionsWrapper executionsWrapper = new BranchedExecutionsWrapper(smaliMethod, loader);
////    }
//
//}
