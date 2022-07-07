//package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.objects;
//
//import android.content.Context;
//import android.support.test.InstrumentationRegistry;
//
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.lang.reflect.Array;
//
//import usask.cyberlab.smalisimulator.AssetCopier;
//import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
//import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;
//
//public class SmaliObjektTest {
//
//    private static ClazzLoader loader;
//
//    private static Objekt smaliNormalObjekt;
//
//    static Objekt klassObjekt1;
//    static Objekt klassObjekt2;
//    static Objekt klassObjekt3;
//    static Objekt klassObjekt4;
//    static Objekt klassObjekt5;
//    static Objekt klassObjekt6;
//
//    private static Objekt e1;
//    private static Objekt e2;
//    private static Objekt e3;
//
//    private static ArrayObjekt a1;
//    private static ArrayObjekt a2;
//
//    @BeforeClass
//    public static void beforeClass() throws Exception {
//        SimSmaliConfig.executeClinit = false;
//
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        loader = new ClazzLoader(context.getCacheDir().getAbsolutePath(), context);
//        AssetCopier.copyFile("TestSmaliClass.smali");
//        AssetCopier.copyFile("TestSmaliClassParent.smali");
//        AssetCopier.copyFile("TestInterface.smali");
//
//        Clazz clazz = loader.getClazz("LTestSmaliClass;");
//        smaliNormalObjekt = new Objekt(clazz, clazz.getClassPath());
//        ConstructorInterceptor.selfWrapper = smaliNormalObjekt;
//        clazz.getMirroringClass().newInstance();
//
//        klassObjekt1 =  loader.getClazz("LTestSmaliClass;").getClassObjekt();
//        klassObjekt2 =  loader.getClazz("LTestSmaliClassParent;").getClassObjekt();
//        klassObjekt3 =  loader.getClazz("LTestInterface;").getClassObjekt();
//        klassObjekt4 =  loader.getClazz("[LTestSmaliClass;").getClassObjekt();
//        klassObjekt5 =  loader.getClazz("[LTestSmaliClassParent;").getClassObjekt();
//        klassObjekt6 =  loader.getClazz("[LTestInterface;").getClassObjekt();//
//
////        SmaliArrayClazz arrayClazz1 = (SmaliArrayClazz) loader.getClazz("[LTestSmaliClass;");
////        SmaliArrayClazz arrayClazz2 = (SmaliArrayClazz) loader.getClazz("[[LTestSmaliClass;");
////
////        Object o1 = Array.newInstance(clazz.getMirroringClass(), 10);
////        a1 = new ArrayObjekt(arrayClazz1, arrayClazz1.getClassPath(), o1);
////
////        Object o2 = Array.newInstance(arrayClazz1.getMirroringClass(), 5);
////        a2 = new ArrayObjekt(arrayClazz2, arrayClazz2.getClassPath(), o2);
////
////        AssetCopier.copyFile("SimpleEnum.smali");
////        Clazz sc1 = loader.getClazz("LSimpleEnum;");
////        e1 = (Objekt) sc1.getStaticFieldValue("STATE1");
////
////        AssetCopier.copyFile("FatEnum.smali");
////        Clazz sc2 = loader.getClazz("LFatEnum;");
////        e2 = (Objekt) sc2.getStaticFieldValue("STATE1");
////
//////        AssetCopier.copyFile("EnumWithConstructor.smali");
//////        Clazz sc3 = loader.getClazz("LEnumWithConstructor;");
//    }
//
//
//    @AfterClass
//    public static void afterClass() {
//        SimSmaliConfig.executeClinit = true;
//
//        ClazzLoader loader = null;
//        Objekt smaliNormalObjekt = null;
//        Objekt klassObjekt1 = null;
//        Objekt klassObjekt2 = null;
//        Objekt klassObjekt3 = null;
//        Objekt klassObjekt4 = null;
//        Objekt klassObjekt5 = null;
//        Objekt klassObjekt6 = null;
//        Objekt e1 = null;
//        Objekt e2 = null;
//        Objekt e3 = null;
//        ArrayObjekt a1 = null;
//        ArrayObjekt a2 = null;
//    }
//
////    @Test
////    public void getFieldTypeTest1(){
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("i1"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("i2"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("i3"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("i4"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("s1"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("s2"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("s3"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("s4"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("s5"));
////        Assert.assertEquals("Z",smaliNormalObjekt.getFieldType("b1"));
////        Assert.assertEquals("Z",smaliNormalObjekt.getFieldType("b2"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("ip1"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("ip2"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("sp1"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("sp2"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("ii1"));
////        Assert.assertEquals("I",smaliNormalObjekt.getFieldType("ii2"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("ss1"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("ss2"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("ss3"));
////        Assert.assertEquals("Ljava/lang/String;",smaliNormalObjekt.getFieldType("ss4"));
////    }
////
////
////    @Test
////    public void isInstanceOfTest1(){
////        Assert.assertTrue(smaliNormalObjekt.isInstanceOf("LTestSmaliClass;"));
////        Assert.assertTrue(smaliNormalObjekt.isInstanceOf("LTestSmaliClassParent;"));
////        Assert.assertTrue(smaliNormalObjekt.isInstanceOf("LTestInterface;"));
////        Assert.assertTrue(smaliNormalObjekt.isInstanceOf("Ljava/lang/Object;"));
////        Assert.assertFalse(smaliNormalObjekt.isInstanceOf("[LTestSmaliClass;"));
////    }
////
////    @Test
////    public void getAndSetInstanceFieldValue1(){
////        Assert.assertEquals(0,smaliNormalObjekt.getInstanceFieldValue("i1"));
////        Assert.assertEquals(null,smaliNormalObjekt.getInstanceFieldValue("s1"));
////        Objekt objekt = (Objekt) smaliNormalObjekt.getInstanceFieldValue("s3");
////        Assert.assertEquals("Hello from child instance", objekt.toString());
////    }
////
////
////    @Test
////    public void getTypeTest3(){
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt1.getType());
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt2.getType());
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt3.getType());
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt4.getType());
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt5.getType());
////        Assert.assertEquals("Ljava/lang/Class;", klassObjekt6.getType());
////    }
////
//////    @Test
//////    public void getRepresentedClassTypeTest3(){
//////        Assert.assertEquals("LTestSmaliClass;", klassObjekt1.getClazz().getClassPath());
//////        Assert.assertEquals("LTestSmaliClassParent;", klassObjekt2.getClazz().getClassPath());
//////        Assert.assertEquals("LTestInterface;", klassObjekt3.getClazz().getClassPath());
//////        Assert.assertEquals("[LTestSmaliClass;", klassObjekt4.getClazz().getClassPath());
//////        Assert.assertEquals("[LTestSmaliClassParent;", klassObjekt5.getClazz().getClassPath());
//////        Assert.assertEquals("[LTestInterface;", klassObjekt6.getClazz().getClassPath());
//////    }
////
////    @Test
////    public void getClazzTest3(){
////        Clazz classClazz = loader.getClazz("Ljava/lang/Class;");
////        Assert.assertEquals(classClazz, klassObjekt1.getClazz());
////        Assert.assertEquals(classClazz, klassObjekt2.getClazz());
////        Assert.assertEquals(classClazz, klassObjekt3.getClazz());
////        Assert.assertEquals(classClazz, klassObjekt4.getClazz());
////        Assert.assertEquals(classClazz, klassObjekt5.getClazz());
////        Assert.assertEquals(classClazz, klassObjekt6.getClazz());
////    }
////
//////    @Test
//////    public void getRepresentedClazzTest3(){
//////        Assert.assertEquals(smaliClazz1, klassObjekt1.getMirroringObject());
//////        Assert.assertEquals(smaliClazz2, klassObjekt2.getMirroringObject());
//////        Assert.assertEquals(smaliClazz3, klassObjekt3.getMirroringObject());
//////        Assert.assertEquals(smaliArrayClazz1, klassObjekt4.getMirroringObject());
//////        Assert.assertEquals(smaliArrayClazz2, klassObjekt5.getMirroringObject());
//////        Assert.assertEquals(smaliArrayClazz3, klassObjekt6.getMirroringObject());
//////    }
////
////    @Test
////    public void isInstanceOfTest3(){
////        Assert.assertTrue(klassObjekt1.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt1.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt1.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertTrue(klassObjekt2.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt2.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt2.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertTrue(klassObjekt3.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt3.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt3.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertTrue(klassObjekt4.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt4.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt4.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertTrue(klassObjekt5.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt5.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt5.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertTrue(klassObjekt6.isInstanceOf("Ljava/lang/Class;"));
////        Assert.assertTrue(klassObjekt6.isInstanceOf("Ljava/lang/reflect/Type;"));
////        Assert.assertTrue(klassObjekt6.isInstanceOf("Ljava/lang/Object;"));
////
////        Assert.assertFalse(klassObjekt1.isInstanceOf("[Ljava/lang/Class;"));
////        Assert.assertFalse(klassObjekt2.isInstanceOf("Ljava/lang/Integer;"));
////        Assert.assertFalse(klassObjekt3.isInstanceOf("Ljava/lang/ClassLoader;"));
////        Assert.assertFalse(klassObjekt4.isInstanceOf("[Ljava/lang/Class;"));
////        Assert.assertFalse(klassObjekt5.isInstanceOf("Ljava/lang/Integer;"));
////        Assert.assertFalse(klassObjekt6.isInstanceOf("Ljava/lang/ClassLoader;"));
////
////        Assert.assertFalse(klassObjekt1.isInstanceOf("LTestSmaliClass;"));
////        Assert.assertFalse(klassObjekt2.isInstanceOf("LTestSmaliClassParent;"));
////        Assert.assertFalse(klassObjekt3.isInstanceOf("LTestInterface;"));
////        Assert.assertFalse(klassObjekt4.isInstanceOf("[LTestSmaliClass;"));
////        Assert.assertFalse(klassObjekt5.isInstanceOf("[LTestSmaliClassParent;"));
////        Assert.assertFalse(klassObjekt6.isInstanceOf("[LTestInterface;"));
////    }
////
//////    @Test(expected = IllegalStateException.class)
//////    public void getInstanceFieldValueTest(){
//////        klassObjekt1.getInstanceFieldValue("");
//////    }
//////
//////    @Test(expected = IllegalStateException.class)
//////    public void setInstanceFieldValueTest(){
//////        klassObjekt2.setInstanceFieldValue("", null);
//////    }
////
////    @Test
////    public void toStringTest3(){
////        Assert.assertEquals("class TestSmaliClass", klassObjekt1.toString());
////        Assert.assertEquals("class TestSmaliClassParent", klassObjekt2.toString());
////        Assert.assertEquals("interface TestInterface", klassObjekt3.toString());
////        Assert.assertEquals("class [LTestSmaliClass;", klassObjekt4.toString());
////        Assert.assertEquals("class [LTestSmaliClassParent;", klassObjekt5.toString());
////        Assert.assertEquals("class [LTestInterface;", klassObjekt6.toString());
////    }
////
////    @Test
////    public void isInstanceOfTest4(){
////        Assert.assertTrue(a1.isInstanceOf("Ljava/lang/Object;"));
////        Assert.assertTrue(a1.isInstanceOf("[Ljava/lang/Object;"));
////        Assert.assertTrue(a1.isInstanceOf("[LTestSmaliClass;"));
////        Assert.assertTrue(a1.isInstanceOf("[LTestSmaliClassParent;"));
////        Assert.assertTrue(a1.isInstanceOf("[LTestInterface;"));
////        Assert.assertFalse(a1.isInstanceOf("[[LTestSmaliClass;"));
////        Assert.assertFalse(a1.isInstanceOf("[[Ljava/lang/Object;"));
////
////        Assert.assertTrue(a2.isInstanceOf("Ljava/lang/Object;"));
////        Assert.assertTrue(a2.isInstanceOf("[Ljava/lang/Object;"));
////        Assert.assertFalse(a2.isInstanceOf("[LTestSmaliClass;"));
////        Assert.assertFalse(a2.isInstanceOf("[LTestSmaliClassParent;"));
////        Assert.assertFalse(a2.isInstanceOf("[LTestInterface;"));
////        Assert.assertTrue(a2.isInstanceOf("[[LTestSmaliClass;"));
////        Assert.assertTrue(a2.isInstanceOf("[[LTestSmaliClassParent;"));
////        Assert.assertTrue(a2.isInstanceOf("[[LTestInterface;"));
////        Assert.assertTrue(a2.isInstanceOf("[[Ljava/lang/Object;"));
////    }
////
////    @Test
////    public void getArrayTypeTest(){
////        Assert.assertEquals("[LTestSmaliClass;",a1.getType());
////        Assert.assertEquals("[[LTestSmaliClass;", a2.getType());
////    }
////
////    @Test
////    public void getSizeTest(){
////        Assert.assertEquals(10, a1.getSize());
////        Assert.assertEquals(5, a2.getSize());
////    }
////
////    @Test
////    public void getDimensionTest(){
////        Assert.assertEquals(1, a1.getDimension());
////        Assert.assertEquals(2, a2.getDimension());
////    }
////
////
////    @Test(expected = IndexOutOfBoundsException.class)
////    public void getValueOutOfBoundsException1(){
////        a1.getValue(-1);
////    }
////
////    @Test(expected = IndexOutOfBoundsException.class)
////    public void getValueOutOfBoundsException2(){
////        a1.getValue(a1.getSize());
////    }
////
////    @Test
////    public void getAndSetValueTest() throws Exception{
////        Assert.assertNull(a1.getValue(a1.getSize() - 1));
////        Assert.assertNull(a2.getValue(a2.getSize() - 1));
////
////        Clazz clazz = loader.getClazz("LTestSmaliClass;");
////        Objekt objekt = new Objekt(clazz, clazz.getClassPath());
////        ConstructorInterceptor.selfWrapper = objekt;
////        clazz.getMirroringClass().newInstance();
////        a1.setValue(a1.getSize()-1, smaliNormalObjekt);
////        a2.setValue(a2.getSize()-1, a1);
////
////        Assert.assertEquals(smaliNormalObjekt, a1.getValue(a1.getSize()-1));
////        Assert.assertEquals(a1, a2.getValue(a2.getSize()-1));
////    }
////
////    @Test
////    public void testInterfaceStaticFields(){
////        Clazz clazz = loader.getClazz("LTestInterface;");
////        Objekt ao = (Objekt) clazz.getStaticFieldValue("ss3");
////        Assert.assertEquals("Hello from interface clinit 1",ao.getMirroringObject());
////        ao = (Objekt) clazz.getStaticFieldValue("ss4");
////        Assert.assertEquals("Hello from interface clinit 2", ao.getMirroringObject());
////    }
//}
