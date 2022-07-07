package usask.cyberlab.smalisimulator.simsmali.TestSuite_1_ClazzAndObjekt.objects;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import usask.cyberlab.smalisimulator.TestClass;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class JavaObjektTest {

    private static ClazzLoader loader;

    static Objekt o1;
    static Objekt o2;
    static Objekt o3;
    static Objekt o4;
    static Objekt o5;

    static Objekt enumObjekt1;
    static Objekt enumObjekt2;
    static Objekt enumObjekt3;

    static Objekt klassObjekt1;
    static Objekt klassObjekt2;
    static Objekt klassObjekt3;
    static Objekt klassObjekt4;
    static Objekt klassObjekt5;
    static Objekt klassObjekt6;

    static ArrayObjekt arrayObjekt1;
    static ArrayObjekt arrayObjekt2;
    static ArrayObjekt arrayObjekt3;
    static ArrayObjekt arrayObjekt4;

    @BeforeClass
    public static void beforeClass() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        loader = new ClazzLoader("don't_care", context);

        ReflectedClazz c1 = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
        ReflectedClazz c2 = (ReflectedClazz) loader.getClazz("Ljava/lang/Integer;");
        ReflectedClazz c3 = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        ReflectedClazz c4 = (ReflectedClazz) loader.getClazz("Ljava/util/ArrayList;");
        ReflectedClazz c5 = (ReflectedClazz) loader.getClazz("Lusask/cyberlab/smalisimulator/TestClass;");

        o1 = new Objekt(c1 , new Object());
        o2 = new Objekt(c2, 20);
        o3 = new Objekt(c3, "Test String");
        o4 = new Objekt(c4, new ArrayList());
        o5 = new Objekt(c5,  new TestClass());

        ReflectedClazz clazz1 = (ReflectedClazz) loader.getClazz("Ljava/text/Normalizer$Form;");
        ReflectedClazz clazz2 = (ReflectedClazz) loader.getClazz("Ljava/lang/annotation/ElementType;");
        ReflectedClazz clazz3 = (ReflectedClazz) loader.getClazz("Ljava/math/RoundingMode;");

        enumObjekt1 = (Objekt) clazz1.getStaticFieldValue("NFC");
        enumObjekt2 = (Objekt) clazz2.getStaticFieldValue("FIELD");
        enumObjekt3 = (Objekt) clazz3.getStaticFieldValue("CEILING");

        ReflectedClazz reflectedClazz1 = (ReflectedClazz) loader.getClazz("I");
        ReflectedClazz reflectedClazz2 = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
        ReflectedClazz reflectedClazz3 = (ReflectedClazz) loader.getClazz("Ljava/lang/Class;");
        ReflectedClazz reflectedClazz4 = (ReflectedClazz) loader.getClazz("Ljava/util/Map;");
        ReflectedClazz reflectedClazz5 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        ReflectedClazz reflectedClazz6 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Class;");

        klassObjekt1 = reflectedClazz1.getClassObjekt();
        klassObjekt2 = reflectedClazz2.getClassObjekt();
        klassObjekt3 = reflectedClazz3.getClassObjekt();
        klassObjekt4 = reflectedClazz4.getClassObjekt();
        klassObjekt5 = reflectedClazz5.getClassObjekt();
        klassObjekt6 = reflectedClazz6.getClassObjekt();

        ReflectedClazz ac1 = (ReflectedClazz) loader.getClazz("[I");
        ReflectedClazz ac2 = (ReflectedClazz) loader.getClazz("[[Z");
        ReflectedClazz ac3 = (ReflectedClazz) loader.getClazz("[[Ljava/lang/Object;");
        ReflectedClazz ac4 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Integer;");

        arrayObjekt1 = new ArrayObjekt(ac1, new int[10]);
        arrayObjekt2 = new ArrayObjekt(ac2, new boolean[5][]);
        arrayObjekt3 = new ArrayObjekt(ac3, new Object[7][]);
        arrayObjekt4 = new ArrayObjekt(ac4, new Integer[15]);
    }

    @AfterClass
    public static void afterClass() {
        loader = null;
        o1 = null;
        o2 = null;
        o3 = null;
        o4 = null;
        o5 = null;
        enumObjekt1 = null;
        enumObjekt2 = null;
        enumObjekt3 = null;
        klassObjekt1 = null;
        klassObjekt2 = null;
        klassObjekt3 = null;
        klassObjekt4 = null;
        klassObjekt5 = null;
        klassObjekt6 = null;
        arrayObjekt1 = null;
        arrayObjekt2 = null;
        arrayObjekt3 = null;
        arrayObjekt4 = null;
    }


    @Test
    public void getTypeTest1(){
        Assert.assertEquals("Ljava/lang/Object;", o1.getType());
        Assert.assertEquals("Ljava/lang/Integer;", o2.getType());
        Assert.assertEquals("Ljava/lang/String;", o3.getType());
        Assert.assertEquals("Ljava/util/ArrayList;", o4.getType());
        Assert.assertEquals("Lusask/cyberlab/smalisimulator/TestClass;", o5.getType());
    }

    @Test
    public void isInstanceOfTest1(){
        Assert.assertTrue(o1.isInstanceOf(o1.getType()));
        Assert.assertFalse(o1.isInstanceOf(o2.getType()));
        Assert.assertFalse(o1.isInstanceOf(o3.getType()));
        Assert.assertFalse(o1.isInstanceOf(o4.getType()));
        Assert.assertFalse(o1.isInstanceOf(o5.getType()));

        Assert.assertTrue(o2.isInstanceOf(o1.getType()));
        Assert.assertTrue(o2.isInstanceOf(o2.getType()));
        Assert.assertFalse(o2.isInstanceOf(o3.getType()));
        Assert.assertFalse(o2.isInstanceOf(o4.getType()));
        Assert.assertFalse(o2.isInstanceOf(o5.getType()));
        Assert.assertTrue(o2.isInstanceOf("Ljava/lang/Number;"));

        Assert.assertTrue(o3.isInstanceOf(o1.getType()));
        Assert.assertFalse(o3.isInstanceOf(o2.getType()));
        Assert.assertTrue(o3.isInstanceOf(o3.getType()));
        Assert.assertFalse(o3.isInstanceOf(o4.getType()));
        Assert.assertFalse(o3.isInstanceOf(o5.getType()));
        Assert.assertTrue(o3.isInstanceOf("Ljava/lang/CharSequence;"));

        Assert.assertTrue(o4.isInstanceOf(o1.getType()));
        Assert.assertFalse(o4.isInstanceOf(o2.getType()));
        Assert.assertFalse(o4.isInstanceOf(o3.getType()));
        Assert.assertTrue(o4.isInstanceOf(o4.getType()));
        Assert.assertFalse(o4.isInstanceOf(o5.getType()));
        Assert.assertTrue(o4.isInstanceOf("Ljava/util/AbstractList;"));
        Assert.assertTrue(o4.isInstanceOf("Ljava/util/List;"));

        Assert.assertTrue(o5.isInstanceOf(o1.getType()));
        Assert.assertFalse(o5.isInstanceOf(o2.getType()));
        Assert.assertFalse(o5.isInstanceOf(o3.getType()));
        Assert.assertFalse(o5.isInstanceOf(o4.getType()));
        Assert.assertTrue(o5.isInstanceOf(o5.getType()));
    }

    @Test
    public void getAndSetValueTest1(){
        Assert.assertEquals('@', o5.getInstanceFieldValue("c", null));

        Objekt ro = (Objekt) o5.getInstanceFieldValue("cls", null);
        Class cls = (Class) ro.getMirroringObject();
        String s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Lusask/cyberlab/smalisimulator/TestClass;", s);

        ArrayObjekt arrayObjekt = (ArrayObjekt) o5.getInstanceFieldValue("clsArray", null);
        Assert.assertEquals("[Ljava/lang/Class;", arrayObjekt.getType());

        Objekt arrayValue = (Objekt) arrayObjekt.getValue(0);
        cls = (Class) arrayValue.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Lusask/cyberlab/smalisimulator/TestClass;", s);

        arrayValue = (Objekt) arrayObjekt.getValue(1);
        cls = (Class) arrayValue.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Lusask/cyberlab/smalisimulator/TestClass;", s);

        Assert.assertNull(o5.getInstanceFieldValue("nullObj", null));
    }

    @Test
    public void getTypeTest2(){
        Assert.assertEquals("Ljava/text/Normalizer$Form;", enumObjekt1.getType());
        Assert.assertEquals("Ljava/lang/annotation/ElementType;", enumObjekt2.getType());
        Assert.assertEquals("Ljava/math/RoundingMode;", enumObjekt3.getType());
    }

    @Test
    public void isInstanceOfTest2(){
        Assert.assertTrue(enumObjekt1.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertTrue(enumObjekt2.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertTrue(enumObjekt3.isInstanceOf("Ljava/lang/Object;"));

        Assert.assertTrue(enumObjekt1.isInstanceOf("Ljava/lang/Enum;"));
        Assert.assertTrue(enumObjekt2.isInstanceOf("Ljava/lang/Enum;"));
        Assert.assertTrue(enumObjekt3.isInstanceOf("Ljava/lang/Enum;"));

        Assert.assertTrue(enumObjekt1.isInstanceOf("Ljava/lang/Comparable;"));
        Assert.assertTrue(enumObjekt2.isInstanceOf("Ljava/lang/Comparable;"));
        Assert.assertTrue(enumObjekt3.isInstanceOf("Ljava/lang/Comparable;"));

        Assert.assertTrue(enumObjekt1.isInstanceOf("Ljava/text/Normalizer$Form;"));
        Assert.assertTrue(enumObjekt2.isInstanceOf("Ljava/lang/Comparable;"));
        Assert.assertTrue(enumObjekt3.isInstanceOf("Ljava/math/RoundingMode;"));
    }

    @Test
    public void getClazzTest2(){
        ReflectedClazz clazz1 = (ReflectedClazz) loader.getClazz("Ljava/text/Normalizer$Form;");
        ReflectedClazz clazz2 = (ReflectedClazz) loader.getClazz("Ljava/lang/annotation/ElementType;");
        ReflectedClazz clazz3 = (ReflectedClazz) loader.getClazz("Ljava/math/RoundingMode;");
        Assert.assertEquals(clazz1, enumObjekt1.getClazz());
        Assert.assertEquals(clazz2, enumObjekt2.getClazz());
        Assert.assertEquals(clazz3, enumObjekt3.getClazz());
    }

    @Test
    public void getTypeTest3(){
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt1.getType());
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt2.getType());
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt3.getType());
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt4.getType());
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt5.getType());
        Assert.assertEquals("Ljava/lang/Class;", klassObjekt6.getType());
    }

    @Test
    public void getRepresentedTypeTest3(){
        Class cls = (Class) klassObjekt1.getMirroringObject();
        String s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("I", s);

        cls = (Class) klassObjekt2.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Ljava/lang/Object;", s);

        cls = (Class) klassObjekt3.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Ljava/lang/Class;", s);

        cls = (Class) klassObjekt4.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("Ljava/util/Map;", s);

        cls = (Class) klassObjekt5.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("[Ljava/lang/Object;", s);

        cls = (Class) klassObjekt6.getMirroringObject();
        s = SimulationUtils.makeSmaliStyleClassPath(cls.getName());
        Assert.assertEquals("[Ljava/lang/Class;", s);
    }

    @Test
    public void getClazzTest3(){
        Clazz classClazz = loader.getClazz("Ljava/lang/Class;");
        Assert.assertEquals(classClazz, klassObjekt1.getClazz());
        Assert.assertEquals(classClazz, klassObjekt2.getClazz());
        Assert.assertEquals(classClazz, klassObjekt3.getClazz());
        Assert.assertEquals(classClazz, klassObjekt4.getClazz());
        Assert.assertEquals(classClazz, klassObjekt5.getClazz());
        Assert.assertEquals(classClazz, klassObjekt6.getClazz());
    }

    @Test
    public void getTypeTest4(){
        Assert.assertEquals("[I", arrayObjekt1.getType());
        Assert.assertEquals("[[Z", arrayObjekt2.getType());
        Assert.assertEquals("[[Ljava/lang/Object;", arrayObjekt3.getType());
        Assert.assertEquals("[Ljava/lang/Integer;", arrayObjekt4.getType());
    }

    @Test
    public void getArrayTypeTest4(){
        Assert.assertEquals("[I", arrayObjekt1.getType());
        Assert.assertEquals("[[Z", arrayObjekt2.getType());
        Assert.assertEquals("[[Ljava/lang/Object;", arrayObjekt3.getType());
        Assert.assertEquals("[Ljava/lang/Integer;", arrayObjekt4.getType());
    }

    @Test
    public void getSizeTest(){
        Assert.assertEquals(10, arrayObjekt1.getSize());
        Assert.assertEquals(5, arrayObjekt2.getSize());
        Assert.assertEquals(7, arrayObjekt3.getSize());
        Assert.assertEquals(15, arrayObjekt4.getSize());
    }

    @Test
    public void getDimensionTest(){
        Assert.assertEquals(1, arrayObjekt1.getDimension());
        Assert.assertEquals(2, arrayObjekt2.getDimension());
        Assert.assertEquals(2, arrayObjekt3.getDimension());
        Assert.assertEquals(1, arrayObjekt4.getDimension());
    }

    @Test
    public void isInstanceOfTest(){
        Assert.assertEquals(true, arrayObjekt1.isInstanceOf("[I"));
        Assert.assertEquals(false, arrayObjekt1.isInstanceOf("[[I"));
        Assert.assertEquals(true, arrayObjekt1.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertEquals(false, arrayObjekt1.isInstanceOf("[Ljava/lang/Object;"));

        Assert.assertEquals(true, arrayObjekt2.isInstanceOf("[[Z"));
        Assert.assertEquals(false, arrayObjekt2.isInstanceOf("[Z"));
        Assert.assertEquals(true, arrayObjekt2.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertEquals(true, arrayObjekt2.isInstanceOf("[Ljava/lang/Object;"));
        Assert.assertEquals(false, arrayObjekt2.isInstanceOf("[[Ljava/lang/Object;"));

        Assert.assertEquals(true, arrayObjekt3.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertEquals(true, arrayObjekt3.isInstanceOf("[Ljava/lang/Object;"));
        Assert.assertEquals(true, arrayObjekt3.isInstanceOf("[[Ljava/lang/Object;"));
        Assert.assertEquals(false, arrayObjekt3.isInstanceOf("[[[Ljava/lang/Object;"));

        Assert.assertEquals(true, arrayObjekt4.isInstanceOf("[Ljava/lang/Integer;"));
        Assert.assertEquals(true, arrayObjekt4.isInstanceOf("[Ljava/lang/Object;"));
        Assert.assertEquals(true, arrayObjekt4.isInstanceOf("Ljava/lang/Object;"));
        Assert.assertEquals(false, arrayObjekt4.isInstanceOf("[[Ljava/lang/Integer;"));
        Assert.assertEquals(false, arrayObjekt4.isInstanceOf("Ljava/lang/Integer;"));
    }


    @Test
    public void getValueIndexOfOutBoundsTest(){
        try {
            arrayObjekt1.getValue(-1);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }

        try {
            arrayObjekt2.getValue(-12);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }

        try {
            arrayObjekt3.getValue(7);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }

        try {
            arrayObjekt4.getValue(16);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }
    }

    @Test
    public void setValueIndexOfOutBoundsTest(){
        try {
            arrayObjekt1.setValue(-1, 5);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }

        try {
            arrayObjekt2.setValue(-12, null);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){ }

        try {
            arrayObjekt3.setValue(7, null);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){}

        try {
            arrayObjekt4.setValue(16, null);
            Assert.assertTrue(false);
        }
        catch (IndexOutOfBoundsException e){
        }
    }

    @Test
    public void setValueArrayStoreExceptionTest(){
        ReflectedClazz reflectedClazz = (ReflectedClazz) loader.getClazz("Ljava/lang/String;");
        Objekt o = new Objekt(reflectedClazz, "Test");
        try {
            arrayObjekt2.setValue(0, o);
            Assert.assertTrue(false);
        }
        catch (ArrayStoreException e){ }
    }

    @Test
    public void getAndSetValueTest(){
        Assert.assertEquals(0, arrayObjekt1.getValue(0));
        Assert.assertEquals(null, arrayObjekt2.getValue(0));
        Assert.assertEquals(null, arrayObjekt3.getValue(0));
        Assert.assertEquals(null, arrayObjekt4.getValue(0));

        ReflectedClazz rc1 = (ReflectedClazz) loader.getClazz("[Z");
        ArrayObjekt ra1 = new ArrayObjekt(rc1, new boolean[3]);

        ReflectedClazz rc2 = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
        // yes in java 0 sized arrays are valid
        ArrayObjekt ra2 = new ArrayObjekt(rc2, new Object[0]);

        ReflectedClazz rc3 = (ReflectedClazz) loader.getClazz("Ljava/lang/Integer;");
        Objekt rn3 = new Objekt(rc3, 10);

        arrayObjekt1.setValue(0,44);
        arrayObjekt2.setValue(0, ra1);
        arrayObjekt3.setValue(0, ra2);
        arrayObjekt4.setValue(0, rn3);

        Object o1 = arrayObjekt1.getValue(0);
        Object o2 = arrayObjekt2.getValue(0);
        Object o3 = arrayObjekt3.getValue(0);
        Object o4 = arrayObjekt4.getValue(0);

        Assert.assertEquals(44, o1);
        Assert.assertEquals(ra1, o2);
        Assert.assertEquals(ra2, o3);
        Assert.assertEquals(rn3, o4);
    }
}
