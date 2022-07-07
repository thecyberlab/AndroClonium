package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ReflectedClazzTest {

    private static ClazzLoader clazzLoader;
    private static ReflectedClazz c1;
    private static ReflectedClazz c2;
    private static ReflectedClazz c3;
    private static ReflectedClazz c4;
    private static ReflectedClazz c5;
    private static ReflectedClazz c6;
    private static ReflectedClazz c7;
    private static ReflectedClazz c8;
    private static ReflectedClazz c9;
    private static ReflectedClazz c10;
    private static ReflectedClazz c11;

    private static ReflectedClazz testClass;

    @BeforeClass
    public static void beforeClass() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        clazzLoader = new ClazzLoader(context.getCacheDir().getAbsolutePath(), context);
        c1 = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/Enum;");
        c2 = (ReflectedClazz) clazzLoader.getClazz("Ljava/text/Normalizer$Form;");
        c3 = (ReflectedClazz) clazzLoader.getClazz("[I");
        c4 = (ReflectedClazz) clazzLoader.getClazz("[[I");
        c5 = (ReflectedClazz) clazzLoader.getClazz("[Ljava/lang/Object;");
        c6 = (ReflectedClazz) clazzLoader.getClazz("[Ljava/text/Normalizer$Form;");
        c7 = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/Class;");
        c8 = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/Object;");
        c9 = (ReflectedClazz) clazzLoader.getClazz("Ljava/util/ArrayList;");
        c10 = (ReflectedClazz) clazzLoader.getClazz("I");
        c11 = (ReflectedClazz) clazzLoader.getClazz("V");
        testClass = (ReflectedClazz) clazzLoader.getClazz("Lusask/cyberlab/smalisimulator/TestClass;");
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        ReflectedClazz c1 = null;
        ReflectedClazz c2 = null;
        ReflectedClazz c3 = null;
        ReflectedClazz c4 = null;
        ReflectedClazz c5 = null;
        ReflectedClazz c6 = null;
        ReflectedClazz c7 = null;
        ReflectedClazz c8 = null;
        ReflectedClazz c9 = null;
        ReflectedClazz c10 = null;
        ReflectedClazz c11 = null;
        ReflectedClazz testClass = null;
    }


    @Test
    public void getFieldTypeTest(){
        assertEquals("I", testClass.getFieldType("si"));
        assertEquals("[I", testClass.getFieldType("spa"));
        assertEquals("Ljava/lang/Object;", testClass.getFieldType("so"));
        assertEquals("[Ljava/lang/Object;", testClass.getFieldType("soa"));
        assertEquals("C", testClass.getFieldType("sch"));
        assertEquals("Ljava/lang/String;", testClass.getFieldType("ss"));
        assertEquals("S", testClass.getFieldType("ssh"));
        assertEquals("Z", testClass.getFieldType("sb"));
        assertEquals("F", testClass.getFieldType("sf"));
        assertEquals("Ljava/lang/Object;", testClass.getFieldType("sn"));

        assertEquals("C", testClass.getFieldType("c"));
        assertEquals("Ljava/lang/Class;", testClass.getFieldType("cls"));
        assertEquals("[Ljava/lang/Class;", testClass.getFieldType("clsArray"));
        assertEquals("Ljava/lang/Object;", testClass.getFieldType("nullObj"));
    }

    @Test
    public void getClassPathTest(){
        assertEquals("Ljava/lang/Enum;", c1.getClassPath());
        assertEquals("Ljava/text/Normalizer$Form;", c2.getClassPath());
        assertEquals("[I", c3.getClassPath());
        assertEquals("[[I", c4.getClassPath());
        assertEquals("[Ljava/lang/Object;", c5.getClassPath());
        assertEquals("[Ljava/text/Normalizer$Form;", c6.getClassPath());
        assertEquals("Ljava/lang/Class;", c7.getClassPath());
        assertEquals("Ljava/lang/Object;", c8.getClassPath());
        assertEquals("Ljava/util/ArrayList;", c9.getClassPath());
        assertEquals("I", c10.getClassPath());
        assertEquals("V", c11.getClassPath());
    }

    @Test
    public void isSubClassOfTest() {
        assertTrue(c1.isSubTypeOf(c1.getClassPath()));
        assertTrue(c2.isSubTypeOf(c2.getClassPath()));
        assertTrue(c3.isSubTypeOf(c3.getClassPath()));
        assertTrue(c4.isSubTypeOf(c4.getClassPath()));
        assertTrue(c5.isSubTypeOf(c5.getClassPath()));
        assertTrue(c6.isSubTypeOf(c6.getClassPath()));
        assertTrue(c7.isSubTypeOf(c7.getClassPath()));
        assertTrue(c8.isSubTypeOf(c8.getClassPath()));
        assertTrue(c9.isSubTypeOf(c9.getClassPath()));
    }

    @Test
    public void isSuperClassOfTest() {
        assertTrue(c1.isSuperTypeOf(c1.getClassPath()));
        assertTrue(c2.isSuperTypeOf(c2.getClassPath()));
        assertTrue(c3.isSuperTypeOf(c3.getClassPath()));
        assertTrue(c4.isSuperTypeOf(c4.getClassPath()));
        assertTrue(c5.isSuperTypeOf(c5.getClassPath()));
        assertTrue(c6.isSuperTypeOf(c6.getClassPath()));
        assertTrue(c7.isSuperTypeOf(c7.getClassPath()));
        assertTrue(c8.isSuperTypeOf(c8.getClassPath()));
        assertTrue(c9.isSuperTypeOf(c9.getClassPath()));
    }

    @Test
    public void getParentClassTest() {
        assertEquals("Ljava/lang/Object;", c1.getParentClassPath());
        assertEquals("Ljava/lang/Enum;", c2.getParentClassPath());
        assertEquals("Ljava/lang/Object;", c3.getParentClassPath());
        assertEquals("Ljava/lang/Object;", c4.getParentClassPath());
        assertEquals("Ljava/lang/Object;", c5.getParentClassPath());
        assertEquals("Ljava/lang/Object;", c6.getParentClassPath());
        assertEquals("Ljava/lang/Object;", c7.getParentClassPath());
        assertNull(c8.getParentClassPath());
        assertEquals("Ljava/util/AbstractList;", c9.getParentClassPath());
        assertNull(c10.getParentClassPath());
        assertNull(c11.getParentClassPath());
    }


    @Test
    public void getAndSetStaticFieldValueTest(){
        assertEquals(20 , testClass.getStaticFieldValue("si"));
        assertEquals('#', testClass.getStaticFieldValue("sch"));
        assertEquals((short)23, testClass.getStaticFieldValue("ssh"));
        assertEquals(true, testClass.getStaticFieldValue("sb"));
        assertEquals(23.454f, testClass.getStaticFieldValue("sf"));

        ArrayObjekt arrayObjekt = (ArrayObjekt) testClass.getStaticFieldValue("spa");
        assertEquals("[I", arrayObjekt.getType());
        assertEquals(3 , arrayObjekt.getSize());
        Objekt objekt = (Objekt) testClass.getStaticFieldValue("so");
        assertEquals("Ljava/util/ArrayList;", objekt.getType());
        arrayObjekt = (ArrayObjekt) testClass.getStaticFieldValue("soa");
        assertEquals("[Ljava/lang/Object;", arrayObjekt.getType());
        objekt = (Objekt) testClass.getStaticFieldValue("ss");
        assertEquals("Ljava/lang/String;", objekt.getType());
        assertNull(testClass.getStaticFieldValue("sn"));

        //---------------------------------------------
        testClass.setStaticFieldValue("si",-30);
        assertEquals(-30 , testClass.getStaticFieldValue("si"));
        testClass.setStaticFieldValue("sch",'@');
        assertEquals('@', testClass.getStaticFieldValue("sch"));
        testClass.setStaticFieldValue("ssh", (short) -100);
        assertEquals((short)-100, testClass.getStaticFieldValue("ssh"));
        testClass.setStaticFieldValue("sb", false);
        assertEquals(false, testClass.getStaticFieldValue("sb"));
        testClass.setStaticFieldValue("sf",-24.54f);
        assertEquals(-24.54f, testClass.getStaticFieldValue("sf"));

        ArrayObjekt ro = new ArrayObjekt(c3,new int[]{1, 2, 3});
        testClass.setStaticFieldValue("spa",ro);

        arrayObjekt = (ArrayObjekt) testClass.getStaticFieldValue("spa");
        assertEquals("[I", arrayObjekt.getType());
        assertEquals(3 , arrayObjekt.getSize());

        testClass.setStaticFieldValue("so", ro);
        arrayObjekt = (ArrayObjekt) testClass.getStaticFieldValue("so");
        assertEquals("[I", arrayObjekt.getType());
        assertEquals(3 , arrayObjekt.getSize());

        ro = new ArrayObjekt(c5, new Object[3]);
        testClass.setStaticFieldValue("soa", ro);
        arrayObjekt = (ArrayObjekt) testClass.getStaticFieldValue("soa");
        assertEquals("[Ljava/lang/Object;", arrayObjekt.getType());
        assertEquals(3 , arrayObjekt.getSize());

        ReflectedClazz rc = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/String;");
        objekt = new Objekt(rc, "testing");
        testClass.setStaticFieldValue("ss", objekt);
        objekt = (Objekt) testClass.getStaticFieldValue("ss");
        assertEquals("Ljava/lang/String;", objekt.getType());
        assertEquals("testing",objekt.toString());
    }

    @Test
    public void isEnumClassTest(){
        Assert.assertFalse(c1.isEnumClass());
        Assert.assertTrue(c2.isEnumClass());
        Assert.assertFalse(c3.isEnumClass());
        Assert.assertFalse(c4.isEnumClass());
        Assert.assertFalse(c5.isEnumClass());
        Assert.assertFalse(c6.isEnumClass());
        Assert.assertFalse(c7.isEnumClass());
        Assert.assertFalse(c8.isEnumClass());
        Assert.assertFalse(c9.isEnumClass());
        Assert.assertFalse(c10.isEnumClass());
        Assert.assertFalse(c11.isEnumClass());
    }

    @Test
    public void isInterfaceClassTest(){
        Assert.assertFalse(c1.isInterfaceClass());
        Assert.assertFalse(c9.isInterfaceClass());
        ReflectedClazz rc = (ReflectedClazz) clazzLoader.getClazz("Ljava/util/List;");
        Assert.assertTrue(rc.isInterfaceClass());
        rc = (ReflectedClazz) clazzLoader.getClazz("Ljava/lang/Comparable;");
        Assert.assertTrue(rc.isInterfaceClass());
    }

    @Test
    public void isAbstractTest(){
        Assert.assertTrue(c1.isAbstract());
        Assert.assertFalse(c2.isAbstract());
        ReflectedClazz rc = (ReflectedClazz) clazzLoader.getClazz("Ljava/util/AbstractList;");
        Assert.assertTrue(rc.isAbstract());
        Assert.assertFalse(c9.isAbstract());
    }

    @Test
    public void isArrayTest(){
        Assert.assertFalse(c1.isArray());
        Assert.assertFalse(c2.isArray());
        Assert.assertTrue(c3.isArray());
        Assert.assertTrue(c4.isArray());
        Assert.assertTrue(c5.isArray());
        Assert.assertTrue(c6.isArray());
        Assert.assertFalse(c7.isArray());
        Assert.assertFalse(c8.isArray());
        Assert.assertFalse(c9.isArray());
        Assert.assertFalse(c10.isArray());
    }

    @Test
    public void getClazzObjektTest(){
        Objekt rco = c1.getClassObjekt();
        Class cls = (Class) rco.getMirroringObject();
        String s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c1.getClassPath(), s);

        rco = c2.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c2.getClassPath(), s);

        rco = c3.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c3.getClassPath(), s);

        rco = c4.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c4.getClassPath(), s);

        rco = c5.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c5.getClassPath(), s);

        rco = c6.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c6.getClassPath(), s);

        rco = c7.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c7.getClassPath(), s);

        rco = c8.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c8.getClassPath(), s);

        rco = c8.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c8.getClassPath(), s);

        rco = c9.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c9.getClassPath(), s);

        rco = c10.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c10.getClassPath(), s);

        rco = c11.getClassObjekt();
        cls = (Class) rco.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals(c11.getClassPath(), s);
    }
}