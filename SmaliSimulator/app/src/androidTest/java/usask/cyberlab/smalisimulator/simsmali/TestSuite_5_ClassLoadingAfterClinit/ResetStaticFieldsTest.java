package usask.cyberlab.smalisimulator.simsmali.TestSuite_5_ClassLoadingAfterClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ResetStaticFieldsTest {

    @Test
    public void test() throws Exception{
        AssetCopier.copyFile("ClassWithStaticFieldsForReseting.smali");
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ClazzLoader clazzLoader = new ClazzLoader(appContext.getCacheDir().getAbsolutePath(), appContext);
        Clazz clazz = clazzLoader.getClazz("LClassWithStaticFieldsForReseting;");
        Integer i = (Integer) clazz.getStaticFieldValue("i");
        Float f = (Float) clazz.getStaticFieldValue("f");
        Objekt s = (Objekt) clazz.getStaticFieldValue("s");
        ArrayObjekt s_array = (ArrayObjekt) clazz.getStaticFieldValue("s_array");
        ArrayObjekt s_array2 = (ArrayObjekt) clazz.getStaticFieldValue("s_array2");
        String[] s_array_mirror = (String[]) s_array.getMirroringObject();
        String[][] s_array2_mirror  = (String[][]) s_array2.getMirroringObject();
        Objekt o1 = (Objekt) clazz.getStaticFieldValue("o1");
        Integer ii = (Integer) o1.getInstanceFieldValue("ii", null);
        Float ff = (Float) o1.getInstanceFieldValue("ff", null);
        Objekt ss = (Objekt) o1.getInstanceFieldValue("ss", null);
        ArrayObjekt ss_array = (ArrayObjekt) o1.getInstanceFieldValue("ss_array", null);
        String[] ss_array_mirror = (String[]) ss_array.getMirroringObject();

        Assert.assertEquals(1, i.intValue());
        Assert.assertEquals(0.0f, f.floatValue(), 0);
        Assert.assertEquals("Hello", s.getMirroringObject());
        Assert.assertEquals("A", s_array_mirror[0]);
        Assert.assertEquals("B", s_array_mirror[1]);
        Assert.assertEquals("C", s_array_mirror[2]);
        Assert.assertEquals("a", s_array2_mirror[0][0]);
        Assert.assertEquals("b", s_array2_mirror[0][1]);
        Assert.assertEquals("c", s_array2_mirror[0][2]);
        Assert.assertEquals("e", s_array2_mirror[1][0]);
        Assert.assertEquals("f", s_array2_mirror[1][1]);
        Assert.assertEquals("g", s_array2_mirror[1][2]);
        Assert.assertEquals(23, ii.intValue());
        Assert.assertEquals(84.54f, ff.floatValue(), 0);
        Assert.assertEquals("Hello Instance", ss.getMirroringObject());
        Assert.assertEquals("Q", ss_array_mirror[0]);
        Assert.assertEquals("W", ss_array_mirror[1]);
        Assert.assertEquals("E", ss_array_mirror[2]);

        Method m = clazz.getMirroringClass().getMethod("changeValues");
        m.invoke(null);

        i = (Integer) clazz.getStaticFieldValue("i");
        f = (Float) clazz.getStaticFieldValue("f");
        s = (Objekt) clazz.getStaticFieldValue("s");
        s_array = (ArrayObjekt) clazz.getStaticFieldValue("s_array");
        s_array_mirror = (String[]) s_array.getMirroringObject();
        o1 = (Objekt) clazz.getStaticFieldValue("o1");
        ii = (Integer) o1.getInstanceFieldValue("ii", null);
        ff = (Float) o1.getInstanceFieldValue("ff", null);
        ss = (Objekt) o1.getInstanceFieldValue("ss", null);
        ss_array = (ArrayObjekt) o1.getInstanceFieldValue("ss_array", null);
        ss_array_mirror = (String[]) ss_array.getMirroringObject();

        Assert.assertEquals(2, i.intValue());
        Assert.assertEquals(23.4f, f.floatValue(), 0);
        Assert.assertEquals("Bye", s.getMirroringObject());
        Assert.assertEquals("A", s_array_mirror[0]);
        Assert.assertEquals("K", s_array_mirror[1]);
        Assert.assertEquals("C", s_array_mirror[2]);
        Assert.assertEquals("a", s_array2_mirror[0][0]);
        Assert.assertEquals("m", s_array2_mirror[0][1]);
        Assert.assertEquals("c", s_array2_mirror[0][2]);
        Assert.assertEquals("e", s_array2_mirror[1][0]);
        Assert.assertEquals("f", s_array2_mirror[1][1]);
        Assert.assertEquals("n", s_array2_mirror[1][2]);
        Assert.assertEquals(45, ii.intValue());
        Assert.assertEquals(98.12f, ff.floatValue(), 0);
        Assert.assertEquals("Bye Instance", ss.getMirroringObject());
        Assert.assertEquals("P", ss_array_mirror[0]);
        Assert.assertEquals("W", ss_array_mirror[1]);
        Assert.assertEquals("E", ss_array_mirror[2]);

//        HashMap h = clazzLoader.getInitialStaticValuesPool();
        clazzLoader.resetStaticFieldsToInitialValues();

        i = (Integer) clazz.getStaticFieldValue("i");
        f = (Float) clazz.getStaticFieldValue("f");
        s = (Objekt) clazz.getStaticFieldValue("s");
        s_array = (ArrayObjekt) clazz.getStaticFieldValue("s_array");
        s_array_mirror = (String[]) s_array.getMirroringObject();
        o1 = (Objekt) clazz.getStaticFieldValue("o1");
        ii = (Integer) o1.getInstanceFieldValue("ii", null);
        ff = (Float) o1.getInstanceFieldValue("ff", null);
        ss = (Objekt) o1.getInstanceFieldValue("ss", null);
        ss_array = (ArrayObjekt) o1.getInstanceFieldValue("ss_array", null);
        ss_array_mirror = (String[]) ss_array.getMirroringObject();

        Assert.assertEquals(1, i.intValue());
        Assert.assertEquals(0.0f, f.floatValue(), 0);
        Assert.assertEquals("Hello", s.getMirroringObject());
        Assert.assertEquals("A", s_array_mirror[0]);
        Assert.assertEquals("B", s_array_mirror[1]);
        Assert.assertEquals("C", s_array_mirror[2]);
        Assert.assertEquals("a", s_array2_mirror[0][0]);
        Assert.assertEquals("b", s_array2_mirror[0][1]);
        Assert.assertEquals("c", s_array2_mirror[0][2]);
        Assert.assertEquals("e", s_array2_mirror[1][0]);
        Assert.assertEquals("f", s_array2_mirror[1][1]);
        Assert.assertEquals("g", s_array2_mirror[1][2]);
//        Assert.assertEquals(23, ii.intValue());
//        Assert.assertEquals(84.54f, ff.floatValue(), 0);
//        Assert.assertEquals("Hello Instance", ss.getMirroringObject());
//        Assert.assertEquals("Q", ss_array_mirror[0]);
//        Assert.assertEquals("W", ss_array_mirror[1]);
//        Assert.assertEquals("E", ss_array_mirror[2]);

    }

}
