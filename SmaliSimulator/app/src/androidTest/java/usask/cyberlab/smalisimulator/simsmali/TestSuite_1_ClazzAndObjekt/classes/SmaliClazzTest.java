package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.classes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class SmaliClazzTest {

    private static SmaliClazz smaliClazz;
    private  static Clazz parentSimClass;
    private static ClazzLoader clazzLoader;


    @BeforeClass
    public static void beforeClass() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        AssetCopier.copyFile("TestSmaliClass.smali");
        AssetCopier.copyFile("TestSmaliClassParent.smali");
        AssetCopier.copyFile("TestInterface.smali");
        SimSmaliConfig.executeClinit = false;
        smaliClazz = (SmaliClazz) clazzLoader.getClazz("LTestSmaliClass;");
        parentSimClass =  clazzLoader.getClazz(smaliClazz.getParentClassPath());
    }

    @AfterClass
    public static void afterClass() {
        SimSmaliConfig.executeClinit = true;
        clazzLoader = null;
        smaliClazz = null;
        parentSimClass = null;
    }


    @Test
    public void getSmaliStyleClassPath() {
        Assert.assertEquals("LTestSmaliClass;", smaliClazz.getClassPath());
        Assert.assertEquals("LTestSmaliClassParent;", parentSimClass.getClassPath());
    }

    @Test
    public void getStaticFieldType() {
        Assert.assertEquals("I", smaliClazz.getFieldType("i1"));
        Assert.assertEquals("I", smaliClazz.getFieldType("i2"));
        Assert.assertEquals("I", smaliClazz.getFieldType("i3"));
        Assert.assertEquals("Z", smaliClazz.getFieldType("b1"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("s1"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("s2"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("s3"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("s4"));
        ///---
        Assert.assertEquals("I", smaliClazz.getFieldType("ip1"));
        Assert.assertEquals("I", smaliClazz.getFieldType("ip2"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("sp1"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("sp2"));
        ///---
        Assert.assertEquals("I", smaliClazz.getFieldType("ii1"));
        Assert.assertEquals("I", smaliClazz.getFieldType("ii2"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("ss1"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("ss2"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("ss3"));
        Assert.assertEquals("Ljava/lang/String;", smaliClazz.getFieldType("ss4"));
    }

//    @Test
//    public void getStaticFieldValue() {
//        //static fields
//        Assert.assertEquals(6, smaliClazz.getStaticFieldValue("i2"));
//        Assert.assertEquals(16, smaliClazz.getStaticFieldValue("i3"));
//        Assert.assertEquals(0, smaliClazz.getStaticFieldValue("i4"));
//        Assert.assertEquals(true, smaliClazz.getStaticFieldValue("b1"));
//        Assert.assertEquals(false, smaliClazz.getStaticFieldValue("b2"));
//        Objekt objekt = (Objekt) smaliClazz.getStaticFieldValue("s2");
//        Assert.assertEquals("Hello from child static", objekt.toString());
//
//        Objekt refObjekt = (Objekt) smaliClazz.getStaticFieldValue("s4");
//        Assert.assertEquals("Hello from child final", refObjekt.toString());
//
//
//        Assert.assertNull(smaliClazz.getStaticFieldValue("s5"));
//        // parent static fields
//        Assert.assertEquals(4, smaliClazz.getStaticFieldValue("ip2"));
////        refObjekt = (ReflectedObjekt) smaliClazz.getStaticFieldValue("sp2");
////        Assert.assertEquals("Hello from parent", refObjekt.toStringReflected());
//        Assert.assertEquals(4,parentSimClass.getStaticFieldValue("i2"));
////        //interface static fields
////        refObjekt = (ReflectedObjekt) smaliClazz.getStaticFieldValue("ss1");
////        Assert.assertEquals("hello from static interface", refObjekt.toStringReflected());
//        Assert.assertEquals(3, smaliClazz.getStaticFieldValue("ii1"));
//        Assert.assertEquals(0, smaliClazz.getStaticFieldValue("ii2"));
//    }

//    @Test
//    public void setStaticFieldValue() {
//        smaliClazz.setStaticFieldValue("i2", -20);
//        Assert.assertEquals(-20, smaliClazz.getStaticFieldValue("i2"));
//        smaliClazz.setStaticFieldValue("i3", -323);
//        Assert.assertEquals(-323, smaliClazz.getStaticFieldValue("i3"));
//        smaliClazz.setStaticFieldValue("b1", false);
//        Assert.assertEquals(false, smaliClazz.getStaticFieldValue("b1"));
//
//        ReflectedClazz strClazz = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/String;");
//        Objekt strObject = new Objekt(strClazz,strClazz.getClassPath(),"changed string in child static");
//        smaliClazz.setStaticFieldValue("s2", strObject);
//
//        strObject = (Objekt) smaliClazz.getStaticFieldValue("s2");
//        Assert.assertEquals("changed string in child static", strObject.toString());
//        ///-----------
//        smaliClazz.setStaticFieldValue("ip2", 222);
//        Assert.assertEquals(222, smaliClazz.getStaticFieldValue("ip2"));
//        Assert.assertEquals(222,parentSimClass.getStaticFieldValue("ip2"));
//
//        strObject = new Objekt(strClazz, strClazz.getClassPath(),"Changed String in parent");
//        smaliClazz.setStaticFieldValue("sp2",strObject);
//        strObject = (Objekt) smaliClazz.getStaticFieldValue("sp2");
//        Assert.assertEquals("Changed String in parent", strObject.toString());
//        ///-----------
////        strRefObject = new ReflectedNormalObjekt(strClazz,
////                myRpcClient.getProxy().createString("changed string in interface static"));
////        smaliClazz.setStaticFieldValue("ss1",strRefObject);
////        strRefObject = (ReflectedNormalObjekt) smaliClazz.getStaticFieldValue("ss1");
////        assertEquals("changed string in interface static", strRefObject.toStringReflected());
////        smaliClazz.setStaticFieldValue("ii1",333);
////        assertEquals(333, smaliClazz.getStaticFieldValue("ii1"));
////        smaliClazz.setStaticFieldValue("ii1",Integer.MIN_VALUE);
////        assertEquals(0, smaliClazz.getStaticFieldValue("ii2"));
//    }
//
    @Test
    public void isSubClassOf() {
        Assert.assertTrue(parentSimClass.isSubTypeOf("LTestSmaliClassParent;"));
        Assert.assertFalse(parentSimClass.isSubTypeOf("LTestSmaliClass;"));
        Assert.assertTrue(smaliClazz.isSubTypeOf("LTestSmaliClass;"));
        Assert.assertTrue(smaliClazz.isSubTypeOf("LTestInterface;"));
        Assert.assertTrue(smaliClazz.isSubTypeOf("LTestSmaliClassParent;"));
        Assert.assertTrue(smaliClazz.isSubTypeOf("Ljava/lang/Object;"));
        Assert.assertFalse(smaliClazz.isSubTypeOf("Ljava/lang/String;"));
    }

    @Test
    public void isSuperClassOf() {
        Clazz sc = clazzLoader.getClazz("Ljava/lang/Object;");
        Assert.assertFalse(smaliClazz.isSuperTypeOf("Ljava/lang/Object;"));
        Assert.assertFalse(smaliClazz.isSuperTypeOf("Ljava/lang/String;"));
        Assert.assertTrue(sc.isSuperTypeOf("LTestSmaliClassParent;"));
        Assert.assertTrue(sc.isSuperTypeOf("LTestSmaliClass;"));
        Assert.assertTrue(sc.isSuperTypeOf("LTestInterface;"));
        Assert.assertFalse(smaliClazz.isSuperTypeOf("LTestSmaliClassParent;"));
        Assert.assertTrue(parentSimClass.isSuperTypeOf("LTestSmaliClass;"));
    }

    @Test
    public void isEnumTest(){
        Assert.assertFalse(smaliClazz.isEnumClass());
    }

//    @Test
//    public void isAnnotation(){
//        Assert.assertFalse(smaliClazz.isAnnotationClass());
//    }

    @Test
    public void isInterface() {
        Assert.assertFalse(smaliClazz.isInterfaceClass());
        SmaliClazz interfaceClass = (SmaliClazz) clazzLoader.getClazz("LTestInterface;");
        Assert.assertTrue(interfaceClass.isInterfaceClass());
    }

////    @Test
////    public void thisClassContainsFieldTest(){
////        Assert.assertTrue(smaliClazz.thisClassContainsField("i2"));
////        Assert.assertFalse(smaliClazz.thisClassContainsField("ip2"));
////    }
//
//    @Test
//    public void getAllImplementedInterfaceClazzesTest(){
//        HashSet<Clazz> h = smaliClazz.getAllImplementedInterfaceClazzes();
//        Assert.assertEquals(2, h.size());
//        Clazz c1 = clazzLoader.getClazz("Ljava/lang/Comparable;");
//        Clazz c2 = clazzLoader.getClazz("LTestInterface;");
//        Assert.assertTrue(h.contains(c1));
//        Assert.assertTrue(h.contains(c2));
//    }
}