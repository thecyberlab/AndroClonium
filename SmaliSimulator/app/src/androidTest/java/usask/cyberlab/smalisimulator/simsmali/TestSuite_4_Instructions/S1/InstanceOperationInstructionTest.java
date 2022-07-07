package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.Interceptors.ConstructorInterceptor;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

import static org.junit.Assert.*;

public class InstanceOperationInstructionTest {
        private static SmaliClass testSmaliClass;
        private static ClazzLoader loader;

        @BeforeClass
        public static void beforeClass() throws Exception {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
            AssetCopier.copyFile("instructions/InstanceTest.smali");
            testSmaliClass = loader.getSmaliClass("LInstanceTest;");
        }

        public static void afterClass(){
            testSmaliClass = null;
            loader = null;
        }

//    @Test
//    public void _(){
//        loader.getClazz(testSmaliClass.getClassPath());
//        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
//    }

        @Test
        public void test1() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("get_i1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0,r1.getIntValue());
        }

        @Test
        public void test2() throws Exception {
            SmaliMethod sm = testSmaliClass.getMethod("put_i1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(50);
            methodExecution.execute();
            assertEquals(50,so.getInstanceFieldValue("i1",so.getClazz()));
        }

        @Test
        public void test3()  throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("put_i1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(50);
            methodExecution.execute();
            assertEquals(50,so.getInstanceFieldValue("i1", so.getClazz()));
            sm = testSmaliClass.getMethod("get_i1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals(50,r1.getIntValue());
        }

        @Test
        public void test4() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("get_f1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0f,r1.getFloatValue(), 0f);
        }

        @Test
        public void test5()  throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("put_f1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(50.03f);
            methodExecution.execute();
            assertEquals(50.03f,so.getInstanceFieldValue("f1", so.getClazz()));
        }

        @Test
        public void test6() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("put_f1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(50.03f);
            methodExecution.execute();
            assertEquals(50.03f,so.getInstanceFieldValue("f1", so.getClazz()));
            sm = testSmaliClass.getMethod("get_f1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals(50.03f,r1.getFloatValue(), 0f);
        }

        @Test
        public void test7() throws Exception {
            SmaliMethod sm = testSmaliClass.getMethod("getWide_l1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0l,r1.getLongValue());
        }

        @Test
        public void test8() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putWide_l1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(Long.MAX_VALUE);
            methodExecution.execute();
            long l = (Long) so.getInstanceFieldValue("l1", so.getClazz());
            assertEquals(Long.MAX_VALUE,l);
        }

        @Test
        public void test9()  throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putWide_l1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(Long.MIN_VALUE);
            methodExecution.execute();
            long l = (Long) so.getInstanceFieldValue("l1", so.getClazz());
            assertEquals(Long.MIN_VALUE,l);
            //--
            sm = testSmaliClass.getMethod("getWide_l1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals(Long.MIN_VALUE,r1.getLongValue());
        }

        @Test
        public void test10()  throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getWide_d1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0D,r1.getDoubleValue(),0D);
        }

        @Test
        public void test11() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putWide_d1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(Double.MAX_VALUE);
            methodExecution.execute();
            Double d = (Double) so.getInstanceFieldValue("d1", so.getClazz());
            assertEquals(Double.MAX_VALUE,d, 0D);
        }

        @Test
        public void test12() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putWide_d1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(Double.MAX_VALUE);
            methodExecution.execute();
            Double d = (Double) so.getInstanceFieldValue("d1", so.getClazz());
            assertEquals(Double.MAX_VALUE,d, 0D);
            sm = testSmaliClass.getMethod("getWide_d1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals(Double.MAX_VALUE,r1.getDoubleValue(),0D);
        }

        @Test
        public void test13() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getBoolean_z1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertFalse(r1.getBooleanValue());
        }

        @Test
        public void test14() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putBoolean_z1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(true);
            methodExecution.execute();
            Boolean b = (Boolean) so.getInstanceFieldValue("z1", so.getClazz());
            assertEquals(true,b);
        }

        @Test
        public void test15() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putBoolean_z1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set(true);
            methodExecution.execute();
            Boolean b = (Boolean) so.getInstanceFieldValue("z1", so.getClazz());
            assertEquals(true,b);
            sm = testSmaliClass.getMethod("getBoolean_z1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertTrue(r1.getBooleanValue());
        }

        @Test
        public void test16() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getByte_b1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0,r1.getByteValue());
        }

        @Test
        public void test17() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putByte_b1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set((byte) 333);
            methodExecution.execute();
            assertEquals((byte) 333, so.getInstanceFieldValue("b1", so.getClazz()));
        }

        @Test
        public void test18() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putByte_b1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set((byte) 333);
            methodExecution.execute();
            assertEquals((byte) 333, so.getInstanceFieldValue("b1", so.getClazz()));
            sm = testSmaliClass.getMethod("getByte_b1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals((byte) 333,r1.getByteValue());
        }

        @Test
        public void test19() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getChar_c1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0,r1.getCharValue());
        }

        @Test
        public void test20() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putChar_c1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set('$');
            methodExecution.execute();
            assertEquals('$', so.getInstanceFieldValue("c1", so.getClazz()));
        }

        @Test
        public void test21() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putChar_c1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set('$');
            methodExecution.execute();
            assertEquals('$', so.getInstanceFieldValue("c1", so.getClazz()));
            sm = testSmaliClass.getMethod("getChar_c1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals('$',r1.getCharValue());
        }

        @Test
        public void test22() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getShort_s1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(0,r1.getCharValue());
        }

        @Test
        public void test23() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putShort_s1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set((short) 3213);
            methodExecution.execute();
            assertEquals((short) 3213, so.getInstanceFieldValue("s1", so.getClazz()));
        }

        @Test
        public void test24() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putShort_s1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            r1.set((short) 3213);
            methodExecution.execute();
            assertEquals((short) 3213, so.getInstanceFieldValue("s1", so.getClazz()));
            sm = testSmaliClass.getMethod("getShort_s1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals((short) 3213,r1.getCharValue());
        }

        @Test
        public void test25() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getObject_o1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(null,r1.getReferencedObjectValue());
        }

        @Test
        public void test26() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_o1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz sc2 = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
            Objekt so2 = new Objekt(sc2, new Object());
            r1.set(so2);
            methodExecution.execute();
            assertEquals(so2, so.getInstanceFieldValue("o1", so.getClazz()));
        }

        @Test
        public void test27() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_o1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz sc2 = (ReflectedClazz) loader.getClazz("Ljava/lang/Object;");
            Objekt so2 = new Objekt(sc2, new Object());
            r1.set(so2);
            methodExecution.execute();
            assertEquals(so2, so.getInstanceFieldValue("o1", so.getClazz()));
            sm = testSmaliClass.getMethod("getObject_o1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            assertEquals(so2,r1.getReferencedObjectValue());
        }

        @Test
        public void test28() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getObject_a1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertEquals(null,r1.getReferencedObjectValue());
        }

        @Test
        public void test29() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_a1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
            ArrayObjekt sa = new ArrayObjekt(clazz, new int[4]);
            r1.set(sa);
            methodExecution.execute();
            assertEquals(sa, so.getInstanceFieldValue("a1", so.getClazz()));
        }

        @Test
        public void test30() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_a1()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[I");
            ArrayObjekt sa = new ArrayObjekt(clazz, new int[5]);
            r1.set(sa);
            methodExecution.execute();
            assertEquals(sa, so.getInstanceFieldValue("a1", so.getClazz()));
            sm = testSmaliClass.getMethod("getObject_a1()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            ArrayObjekt array = (ArrayObjekt) r1.getReferencedObjectValue();
            assertEquals(sa,array);
        }

        @Test
        public void test31() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("getObject_a2()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            methodExecution.execute();
            assertNull(r1.getReferencedObjectValue());
        }

        @Test
        public void test32() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_a2()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
            ArrayObjekt sa = new ArrayObjekt(clazz, new Object[4]);
            r1.set(sa);
            methodExecution.execute();
            assertEquals(sa, so.getInstanceFieldValue("a2", so.getClazz()));
        }

        @Test
        public void test33() throws Exception{
            SmaliMethod sm = testSmaliClass.getMethod("putObject_a2()V");
            MethodExecution methodExecution = new MethodExecution(sm,loader);
            Register r = methodExecution.getRegister(0);
            Register r1 = methodExecution.getRegister(1);
            Clazz sc = loader.getClazz("LInstanceTest;");
            Objekt so = new Objekt(sc);
            ConstructorInterceptor.selfWrapper = so;
            Object mirroringObject = sc.getMirroringClass().newInstance();
            so.setMirroringObject(mirroringObject);

            r.set(so);
            ReflectedClazz clazz = (ReflectedClazz) loader.getClazz("[Ljava/lang/Object;");
            ArrayObjekt sa = new ArrayObjekt(clazz,new Object[5]);
            r1.set(sa);
            methodExecution.execute();
            assertEquals(sa, so.getInstanceFieldValue("a2", so.getClazz()));
            sm = testSmaliClass.getMethod("getObject_a2()V");
            methodExecution = new MethodExecution(sm,loader);
            r = methodExecution.getRegister(0);
            r1 = methodExecution.getRegister(1);
            r.set(so);
            methodExecution.execute();
            ArrayObjekt array = (ArrayObjekt) r1.getReferencedObjectValue();
            assertEquals(sa,array);
        }

}