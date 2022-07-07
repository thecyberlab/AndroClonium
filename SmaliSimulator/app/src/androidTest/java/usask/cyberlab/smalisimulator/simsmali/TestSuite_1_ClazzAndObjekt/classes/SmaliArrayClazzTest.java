//package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.classes;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.Utils;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class SmaliArrayClazzTest {
//
//    private static ClazzLoader clazzLoader;
//    private static SmaliArrayClazz smaliArrayClazz;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
//
//        AssetCopier.copyFile("TestSmaliClass.smali");
//        AssetCopier.copyFile("TestSmaliClassParent.smali");
//        AssetCopier.copyFile("TestInterface.smali");
//
//        smaliArrayClazz = (SmaliArrayClazz) clazzLoader.getClazz("[[LTestSmaliClass;");
//    }
//
//    @AfterClass
//    public static void afterClass() {
//        clazzLoader = null;
//        smaliArrayClazz = null;
//    }
//
//
//
//    @Test
//    public void getClassPathTest(){
//        Assert.assertEquals("[[LTestSmaliClass;", smaliArrayClazz.getClassPath());
//    }
//
//    @Test
//    public void isSubClassOfTest(){
//        Assert.assertTrue(smaliArrayClazz.isSubTypeOf("Ljava/lang/Object;"));
//        Assert.assertFalse(smaliArrayClazz.isSubTypeOf("[LTestSmaliClass;"));
//        Assert.assertTrue(smaliArrayClazz.isSubTypeOf("[[LTestSmaliClass;"));
//        Assert.assertFalse(smaliArrayClazz.isSubTypeOf("[[[LTestSmaliClass;"));
//
//        Assert.assertTrue(smaliArrayClazz.isSubTypeOf("[Ljava/lang/Object;"));
//        Assert.assertTrue(smaliArrayClazz.isSubTypeOf("[[Ljava/lang/Object;"));
//    }
//
//    @Test
//    public void isSuperClassOfTest(){
//        Assert.assertFalse(smaliArrayClazz.isSuperTypeOf("Ljava/lang/Object;"));
//        Assert.assertFalse(smaliArrayClazz.isSuperTypeOf("[LTestSmaliClass;"));
//        Assert.assertTrue(smaliArrayClazz.isSuperTypeOf("[[LTestSmaliClass;"));
//        Assert.assertFalse(smaliArrayClazz.isSuperTypeOf("[[[LTestSmaliClass;"));
//
//        Assert.assertFalse(smaliArrayClazz.isSuperTypeOf("[Ljava/lang/Object;"));
//        Assert.assertFalse(smaliArrayClazz.isSuperTypeOf("[[Ljava/lang/Object;"));
//    }
//
//    @Test
//    public void getClassObjektTest(){
//        Objekt sco = smaliArrayClazz.getClassObjekt();
//        Class cls = (Class) sco.getMirroringObject();
//        String s = Utils.makeSmaliStyleClassPath(cls.getName());
//        Assert.assertEquals(smaliArrayClazz.getClassPath(), s);
//    }
//}
