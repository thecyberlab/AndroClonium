package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.objects;
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//public class ObjektCloneTest {
//
//    private static ClazzLoader loader;
//
//    @BeforeClass
//    public static void beforeClass(){
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath(), context);
//    }
//
//    @Test
//    public void test1(){
//        Clazz c1 = loader.getClazz("Ljava/lang/Object;");
//        Objekt o1 = new Objekt(c1, c1.getClassPath(), new Object());
//        Objekt o1_clone = o1.tryClone();
////        Assert.assertNotEquals(o1.getMirroringObject(), o1_clone.getMirroringObject());
//
//    }
//}
