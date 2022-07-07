package usask.cyberlab.smalisimulator.simsmali.TestSuite_3_ClassLoadingWithoutClinit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;

public class CyclicTypes {

    private static ClazzLoader clazzLoader;

    @BeforeClass
    public static void beforeClass() throws Exception{
        AssetCopier.copyFile("cyclicTypes/cyclicFields1/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields1/CircularReference2.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicFields2/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields2/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields2/CircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicFields3/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields3/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields3/CircularReference3.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields3/CircularReference4.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicFields4/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields4/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields4/CircularReference3.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicFields4/CircularReference4.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicMethodRetType/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicMethodRetType/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicMethodRetType/CircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicMethodArgType/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicMethodArgType/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicMethodArgType/CircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicConstructor/CircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicConstructor/CircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicConstructor/CircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceField/ICircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceField/ICircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceField/ICircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodArgType/ICircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodArgType/ICircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodArgType/ICircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodRetType/ICircularReference1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodRetType/ICircularReference2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInterfaceMethodRetType/ICircularReference3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritance1/CyclicInheritance1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritance1/CyclicInheritance2.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritance2/CyclicInheritance1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritance2/CyclicInheritance2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritance2/CyclicInheritance3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicEnum1/EnumWithCyclicTypes.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicEnum1/EnumWithCyclicTypesCompanion.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicEnum2/EnumWithCyclicConstructor.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicEnum2/EnumWithCyclicConstructorCompanion.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceWithOverriddenMethods/CyclicInheritance1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceWithOverriddenMethods/CyclicInheritance2.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceInInterfaces/TestCyclicInheritanceInInterfaces.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef1/CyclicTypesWithArrayRef1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef1/CyclicTypesWithArrayRef2.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef2/CyclicTypesWithArrayRef1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef2/CyclicTypesWithArrayRef2.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef3/CyclicTypesWithArrayRef1.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef3/CyclicTypesWithArrayRef2.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicArrayRef3/CyclicTypesWithArrayRef3.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyChild.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency1/TestClass.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency1/TestClassParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency1/TestClassChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency2/TestClass.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency2/TestClassParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency2/TestClassChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency3/TestClass.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency3/TestClassParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency3/TestClassChild.smali");

        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency4/TestClass.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency4/TestClassParent.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency4/TestClassChild.smali");
        AssetCopier.copyFile("cyclicTypes/cyclicInheritanceParentGrandchildDependency4/TestIntermediateClass.smali");

        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops1/A.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops1/B.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops1/C.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops1/K.smali");

        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/A.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/B.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/C.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/K.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/M.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops2/N.smali");

        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops3/A.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops3/B.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops3/C.smali");
        AssetCopier.copyFile("cyclicTypes/multipleCyclicLoops3/D.smali");

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String basePath = appContext.getCacheDir().getAbsolutePath() + "/cyclicTypes";
        clazzLoader = new ClazzLoader(basePath, appContext);

        SimSmaliConfig.executeClinit = false;
    }

    @AfterClass
    public static void afterClass(){
        clazzLoader = null;
        SimSmaliConfig.executeClinit = true;
    }

    @Test
    public void test1() throws Exception{
        Clazz sc = clazzLoader.getClazz("LcyclicFields1/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields1/CircularReference2;"));
        Clazz sc2 = clazzLoader.getClazz("LcyclicFields1/CircularReference2;");

        Class cls1 = sc.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();

        Field f1 = cls1.getField("c2");
        Field f2 = cls2.getField("c1");

        Assert.assertEquals(cls2, f1.getType());
        Assert.assertEquals(cls1, f2.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test2() throws Exception{
        Clazz sc1 = clazzLoader.getClazz("LcyclicFields2/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields2/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields2/CircularReference3;"));

        Clazz sc2 = clazzLoader.getClazz("LcyclicFields2/CircularReference2;");
        Clazz sc3 = clazzLoader.getClazz("LcyclicFields2/CircularReference3;");

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        Field f1 = cls1.getField("c2");
        Field f2 = cls2.getField("c3");
        Field f3 = cls3.getField("c1");

        Assert.assertEquals(cls2, f1.getType());
        Assert.assertEquals(cls3, f2.getType());
        Assert.assertEquals(cls1, f3.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test3() throws Exception{
        Clazz sc1 = clazzLoader.getClazz("LcyclicFields3/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields3/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields3/CircularReference3;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields3/CircularReference4;"));

        Clazz sc2 = clazzLoader.getClazz("LcyclicFields3/CircularReference2;");
        Clazz sc3 = clazzLoader.getClazz("LcyclicFields3/CircularReference3;");
        Clazz sc4 = clazzLoader.getClazz("LcyclicFields3/CircularReference4;");

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();
        Class cls4 = sc4.getMirroringClass();

        Field f1 = cls1.getField("c2");
        Field f2 = cls2.getField("c3");
        Field f3 = cls3.getField("c4");
        Field f4 = cls4.getField("c1");

        Assert.assertEquals(cls2, f1.getType());
        Assert.assertEquals(cls3, f2.getType());
        Assert.assertEquals(cls4, f3.getType());
        Assert.assertEquals(cls1, f4.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test4() throws Exception{
        Clazz sc1 = clazzLoader.getClazz("LcyclicFields4/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields4/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields4/CircularReference3;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicFields4/CircularReference4;"));

        Clazz sc2 = clazzLoader.getClazz("LcyclicFields4/CircularReference2;");
        Clazz sc3 = clazzLoader.getClazz("LcyclicFields4/CircularReference3;");
        Clazz sc4 = clazzLoader.getClazz("LcyclicFields4/CircularReference4;");

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();
        Class cls4 = sc4.getMirroringClass();

        Field f1 = cls1.getField("c2");
        Field f2 = cls2.getField("c3");
        Field f3 = cls3.getField("c4");
        Field f4 = cls4.getField("c1");

        Assert.assertEquals(cls2, f1.getType());
        Assert.assertEquals(cls3, f2.getType());
        Assert.assertEquals(cls4, f3.getType());
        Assert.assertEquals(cls1, f4.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test5() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodRetType/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicMethodRetType/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicMethodRetType/CircularReference3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodRetType/CircularReference2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodRetType/CircularReference3;");

        SmaliMethod sm1 = sc1.getSmaliMethod("test()LcyclicMethodRetType/CircularReference2;");
        SmaliMethod sm2 = sc2.getSmaliMethod("test()LcyclicMethodRetType/CircularReference3;");
        SmaliMethod sm3 = sc3.getSmaliMethod("test()LcyclicMethodRetType/CircularReference1;");

        Clazz retType1 = clazzLoader.getClazz(sm1.getReturnType());
        Clazz retType2 = clazzLoader.getClazz(sm2.getReturnType());
        Clazz retType3 = clazzLoader.getClazz(sm3.getReturnType());

        Assert.assertEquals(retType1, sc2);
        Assert.assertEquals(retType2, sc3);
        Assert.assertEquals(retType3, sc1);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        Assert.assertEquals(cls2, cls1.getDeclaredMethod("test").getReturnType());
        Assert.assertEquals(cls3, cls2.getDeclaredMethod("test").getReturnType());
        Assert.assertEquals(cls1, cls3.getDeclaredMethod("test").getReturnType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test6() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodArgType/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicMethodArgType/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicMethodArgType/CircularReference3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodArgType/CircularReference2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicMethodArgType/CircularReference3;");

        SmaliMethod sm1 = sc1.getSmaliMethod("test(LcyclicMethodArgType/CircularReference2;)V");
        SmaliMethod sm2 = sc2.getSmaliMethod("test(LcyclicMethodArgType/CircularReference3;)V");
        SmaliMethod sm3 = sc3.getSmaliMethod("test(LcyclicMethodArgType/CircularReference1;)V");

        Clazz argType1 = clazzLoader.getClazz(sm1.getArgumentTypes()[0]);
        Clazz argType2 = clazzLoader.getClazz(sm2.getArgumentTypes()[0]);
        Clazz argType3 = clazzLoader.getClazz(sm3.getArgumentTypes()[0]);

        Assert.assertEquals(argType1, sc2);
        Assert.assertEquals(argType2, sc3);
        Assert.assertEquals(argType3, sc1);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        cls1.getMethod("test", cls2);
        cls2.getMethod("test", cls3);
        cls3.getMethod("test", cls1);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test7() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicConstructor/CircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicConstructor/CircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicConstructor/CircularReference3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicConstructor/CircularReference2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicConstructor/CircularReference3;");

        SmaliMethod sm1 = sc1.getSmaliMethod("<init>(LcyclicConstructor/CircularReference2;)V");
        SmaliMethod sm2 = sc2.getSmaliMethod("<init>(LcyclicConstructor/CircularReference3;)V");
        SmaliMethod sm3 = sc3.getSmaliMethod("<init>(LcyclicConstructor/CircularReference1;)V");

        Clazz argType1 = clazzLoader.getClazz(sm1.getArgumentTypes()[0]);
        Clazz argType2 = clazzLoader.getClazz(sm2.getArgumentTypes()[0]);
        Clazz argType3 = clazzLoader.getClazz(sm3.getArgumentTypes()[0]);

        Assert.assertEquals(argType1, sc2);
        Assert.assertEquals(argType2, sc3);
        Assert.assertEquals(argType3, sc1);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        cls1.getDeclaredConstructor(cls2);
        cls2.getDeclaredConstructor(cls3);
        cls3.getDeclaredConstructor(cls1);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test8() throws Exception{
        Clazz i1 = clazzLoader.getClazz("LcyclicInterfaceField/ICircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceField/ICircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceField/ICircularReference3;"));

        Clazz i2 = clazzLoader.getClazz("LcyclicInterfaceField/ICircularReference2;");
        Clazz i3 = clazzLoader.getClazz("LcyclicInterfaceField/ICircularReference3;");

        Class cls1 = i1.getMirroringClass();
        Class cls2 = i2.getMirroringClass();
        Class cls3 = i3.getMirroringClass();

        Assert.assertEquals(cls2, cls1.getDeclaredField("ic2").getType());
        Assert.assertEquals(cls3, cls2.getDeclaredField("ic3").getType());
        Assert.assertEquals(cls1, cls3.getDeclaredField("ic1").getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test9() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodRetType/ICircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceMethodRetType/ICircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceMethodRetType/ICircularReference3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodRetType/ICircularReference2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodRetType/ICircularReference3;");

        SmaliMethod sm1 = sc1.getSmaliMethod("test()LcyclicInterfaceMethodRetType/ICircularReference2;");
        SmaliMethod sm2 = sc2.getSmaliMethod("test()LcyclicInterfaceMethodRetType/ICircularReference3;");
        SmaliMethod sm3 = sc3.getSmaliMethod("test()LcyclicInterfaceMethodRetType/ICircularReference1;");

        Clazz retType1 = clazzLoader.getClazz(sm1.getReturnType());
        Clazz retType2 = clazzLoader.getClazz(sm2.getReturnType());
        Clazz retType3 = clazzLoader.getClazz(sm3.getReturnType());

        Assert.assertEquals(retType1, sc2);
        Assert.assertEquals(retType2, sc3);
        Assert.assertEquals(retType3, sc1);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        Assert.assertEquals(cls2, cls1.getDeclaredMethod("test").getReturnType());
        Assert.assertEquals(cls3, cls2.getDeclaredMethod("test").getReturnType());
        Assert.assertEquals(cls1, cls3.getDeclaredMethod("test").getReturnType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test10() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodArgType/ICircularReference1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceMethodArgType/ICircularReference2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInterfaceMethodArgType/ICircularReference3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodArgType/ICircularReference2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicInterfaceMethodArgType/ICircularReference3;");

        SmaliMethod sm1 = sc1.getSmaliMethod("test(LcyclicInterfaceMethodArgType/ICircularReference2;)V");
        SmaliMethod sm2 = sc2.getSmaliMethod("test(LcyclicInterfaceMethodArgType/ICircularReference3;)V");
        SmaliMethod sm3 = sc3.getSmaliMethod("test(LcyclicInterfaceMethodArgType/ICircularReference1;)V");

        Clazz argType1 = clazzLoader.getClazz(sm1.getArgumentTypes()[0]);
        Clazz argType2 = clazzLoader.getClazz(sm2.getArgumentTypes()[0]);
        Clazz argType3 = clazzLoader.getClazz(sm3.getArgumentTypes()[0]);

        Assert.assertEquals(argType1, sc2);
        Assert.assertEquals(argType2, sc3);
        Assert.assertEquals(argType3, sc1);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        cls1.getDeclaredMethod("test", cls2);
        cls2.getDeclaredMethod("test", cls3);
        cls3.getDeclaredMethod("test", cls1);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test11() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritance1/CyclicInheritance1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritance1/CyclicInheritance2;"));
        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritance1/CyclicInheritance2;");

        Assert.assertEquals(clazzLoader.getClazz(sc2.getParentClassPath()), sc1);
        Clazz clazz = clazzLoader.getClazz(sc1.getFieldType("c1_2"));
        Assert.assertEquals(sc2, clazz);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();

        Assert.assertEquals(cls1, cls2.getSuperclass());
        Assert.assertEquals(cls2, cls1.getDeclaredField("c1_2").getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test12() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritance2/CyclicInheritance1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritance2/CyclicInheritance2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritance2/CyclicInheritance3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritance2/CyclicInheritance2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritance2/CyclicInheritance3;");
        Assert.assertEquals(clazzLoader.getClazz(sc2.getParentClassPath()), sc1);
        Assert.assertEquals(clazzLoader.getClazz(sc3.getParentClassPath()), sc2);

        Clazz c1_2= clazzLoader.getClazz(sc1.getFieldType("c1_2"));
        Clazz c1_3= clazzLoader.getClazz(sc1.getFieldType("c1_3"));

        Clazz c2_1= clazzLoader.getClazz(sc2.getFieldType("c2_1"));
        Clazz c2_3= clazzLoader.getClazz(sc2.getFieldType("c2_3"));

        Clazz c3_1= clazzLoader.getClazz(sc3.getFieldType("c3_1"));
        Clazz c3_2= clazzLoader.getClazz(sc3.getFieldType("c3_2"));

        Assert.assertEquals(sc1, c2_1);
        Assert.assertEquals(sc1, c3_1);

        Assert.assertEquals(sc2, c1_2);
        Assert.assertEquals(sc2, c3_2);

        Assert.assertEquals(sc3, c1_3);
        Assert.assertEquals(sc3, c2_3);

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();
        Class cls3 = sc3.getMirroringClass();

        Assert.assertEquals(cls2, cls1.getDeclaredField("c1_2").getType());
        Assert.assertEquals(cls3, cls1.getDeclaredField("c1_3").getType());

        Assert.assertEquals(cls1, cls2.getDeclaredField("c2_1").getType());
        Assert.assertEquals(cls3, cls2.getDeclaredField("c2_3").getType());

        Assert.assertEquals(cls1, cls3.getDeclaredField("c3_1").getType());
        Assert.assertEquals(cls2, cls3.getDeclaredField("c3_2").getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test13() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicEnum1/EnumWithCyclicTypes;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicEnum1/EnumWithCyclicTypesCompanion;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicEnum1/EnumWithCyclicTypesCompanion;");

        Assert.assertEquals(sc1, clazzLoader.getClazz(sc2.getFieldType("e")));

        Class cls1 = sc1.getMirroringClass();
        Class cls2 = sc2.getMirroringClass();

        Assert.assertNotNull(cls1.getMethod("test", cls2));
        Assert.assertNotNull(cls2.getMethod("test", cls1));

        Assert.assertTrue(cls1.isEnum());
        Assert.assertFalse(cls2.isEnum());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test14() throws Exception{
        SmaliClazz scCompanion = (SmaliClazz) clazzLoader.getClazz("LcyclicEnum2/EnumWithCyclicConstructorCompanion;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicEnum2/EnumWithCyclicConstructor;"));
        SmaliClazz scEnum = (SmaliClazz) clazzLoader.getClazz("LcyclicEnum2/EnumWithCyclicConstructor;");
        Class enumClass = scEnum.getMirroringClass();
        Class companionClass = scCompanion.getMirroringClass();

        Assert.assertTrue(enumClass.isEnum());
        Assert.assertFalse(companionClass.isEnum());

        Field f = enumClass.getDeclaredField("companions");
        Assert.assertEquals(companionClass, f.getType().getComponentType());

        Field f1 = companionClass.getField("state1");
        Field f2 = companionClass.getField("state2");
        Field f3 = companionClass.getField("state3");

        Assert.assertTrue(Modifier.isStatic(f1.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f2.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f3.getModifiers()));

        Assert.assertEquals(enumClass, f1.getType());
        Assert.assertEquals(enumClass, f2.getType());
        Assert.assertEquals(enumClass, f3.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

     @Test
    public void test15() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceWithOverriddenMethods/CyclicInheritance1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceWithOverriddenMethods/CyclicInheritance2;"));
        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceWithOverriddenMethods/CyclicInheritance2;");

        Class c1 = sc1.getMirroringClass();
        Class c2 = sc2.getMirroringClass();

        Assert.assertEquals(c2, c1.getDeclaredField("c1_2").getType());
        Assert.assertEquals(c1, c2.getDeclaredField("c2_1").getType());

        Assert.assertEquals(c1, c1.getDeclaredMethod("test", int.class).getDeclaringClass());
        Assert.assertEquals(c2, c2.getDeclaredMethod("test", int.class).getDeclaringClass());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test16() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceInInterfaces/TestCyclicInheritanceInInterfaces;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces1;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces2;"));
        SmaliClazz iFaceClazz1 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces1;");
        SmaliClazz iFaceClazz2 = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceInInterfaces/CyclicInheritanceInInterfaces2;");

        Class c1 = iFaceClazz1.getMirroringClass();
        Class c2 = iFaceClazz2.getMirroringClass();

        Method m1 = c1.getMethod("test1", c2);
        Assert.assertNotNull(m1);
        Method m2 = c2.getMethod("test2", c1);
        Assert.assertNotNull(m2);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test17() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef1/CyclicTypesWithArrayRef1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef1/CyclicTypesWithArrayRef1;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef1/CyclicTypesWithArrayRef2;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef1/CyclicTypesWithArrayRef2;");
        Class c1 = sc1.getMirroringClass();
        Class c2 = sc2.getMirroringClass();

        Class c2_field_type = c1.getDeclaredField("c2").getType();
        Class c1_array_field_type = c2.getDeclaredField("c1_array").getType();

        Assert.assertFalse(c2_field_type.isArray());
        Assert.assertTrue(c1_array_field_type.isArray());

        Assert.assertEquals(0, SimulationUtils.countChar(c2_field_type.getName(),'['));
        Assert.assertEquals(1, SimulationUtils.countChar(c1_array_field_type.getName(),'['));

        Assert.assertEquals(c2, c2_field_type);
        Assert.assertEquals(c1, c1_array_field_type.getComponentType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test18() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef2/CyclicTypesWithArrayRef1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef2/CyclicTypesWithArrayRef1;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef2/CyclicTypesWithArrayRef2;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef2/CyclicTypesWithArrayRef2;");

        Class c1 = sc1.getMirroringClass();
        Class c2 = sc2.getMirroringClass();

        Class c2_array_field_type = c1.getDeclaredField("c2_array").getType();
        Class c1_array_field_type = c2.getDeclaredField("c1_array").getType();

        Assert.assertTrue(c2_array_field_type.isArray());
        Assert.assertTrue(c1_array_field_type.isArray());

        Assert.assertEquals(1, SimulationUtils.countChar(c2_array_field_type.getName(),'['));
        Assert.assertEquals(1, SimulationUtils.countChar(c1_array_field_type.getName(),'['));

        Assert.assertEquals(c2, c2_array_field_type.getComponentType());
        Assert.assertEquals(c1, c1_array_field_type.getComponentType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test19() throws Exception{
        SmaliClazz sc1 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef3/CyclicTypesWithArrayRef1;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef3/CyclicTypesWithArrayRef1;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef3/CyclicTypesWithArrayRef2;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicArrayRef3/CyclicTypesWithArrayRef3;"));

        SmaliClazz sc2 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef3/CyclicTypesWithArrayRef2;");
        SmaliClazz sc3 = (SmaliClazz) clazzLoader.getClazz("LcyclicArrayRef3/CyclicTypesWithArrayRef3;");

        Class c1 = sc1.getMirroringClass();
        Class c2 = sc2.getMirroringClass();
        Class c3 = sc3.getMirroringClass();

        Class c2_array_field_type = c1.getDeclaredField("c2_array").getType();
        Class c3_array_field_type = c2.getDeclaredField("c3_array").getType();
        Class c1_array_field_type = c3.getDeclaredField("c1_array").getType();

        Assert.assertTrue(c2_array_field_type.isArray());
        Assert.assertTrue(c3_array_field_type.isArray());
        Assert.assertTrue(c1_array_field_type.isArray());

        Assert.assertEquals(1, SimulationUtils.countChar(c2_array_field_type.getName(),'['));
        Assert.assertEquals(1, SimulationUtils.countChar(c3_array_field_type.getName(),'['));
        Assert.assertEquals(1, SimulationUtils.countChar(c1_array_field_type.getName(),'['));

        Assert.assertEquals(c2, c2_array_field_type.getComponentType());
        Assert.assertEquals(c3, c3_array_field_type.getComponentType());
        Assert.assertEquals(c1, c1_array_field_type.getComponentType());

        SmaliArrayClazz arrayClazz = (SmaliArrayClazz) clazzLoader.getClazz("[LcyclicArrayRef3/CyclicTypesWithArrayRef2;");
        Assert.assertEquals(c2_array_field_type, arrayClazz.getMirroringClass());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test20() throws Exception{
        SmaliClazz parentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyParent;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;"));
        SmaliClazz childClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency/cyclicInheritanceCtorDependencyChild;");
        Class parentClass = parentClazz.getMirroringClass();
        Class childClass = childClazz.getMirroringClass();

        Assert.assertEquals(parentClass, childClass.getSuperclass());
        Constructor parentCtor = parentClass.getDeclaredConstructor(childClass);
        Assert.assertNotNull(parentCtor);

        Field f = parentClass.getDeclaredField("child");
        Assert.assertEquals(childClass, f.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test21() throws Exception{
        SmaliClazz parentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyParent;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild;"));

        SmaliClazz childClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency2/cyclicInheritanceCtorDependencyChild;");
        Class parentClass = parentClazz.getMirroringClass();
        Class childClass = childClazz.getMirroringClass();

        Assert.assertEquals(parentClass, childClass.getSuperclass());
        Constructor childCtor = childClass.getDeclaredConstructor(parentClass);
        Assert.assertNotNull(childCtor);
        Constructor childCtor2 = childClass.getDeclaredConstructor(childClass);
        Assert.assertNotNull(childCtor2);

        Constructor parentCtor = parentClass.getDeclaredConstructor(childClass);
        Assert.assertNotNull(parentCtor);

        Field f = parentClass.getDeclaredField("child");
        Assert.assertEquals(childClass, f.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test22() throws Exception{
        SmaliClazz parentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyParent;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild;"));
        SmaliClazz childClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency3/cyclicInheritanceCtorDependencyChild;");
        Class parentClass = parentClazz.getMirroringClass();
        Class childClass = childClazz.getMirroringClass();

        Assert.assertEquals(parentClass, childClass.getSuperclass());
        Class childArrayClass = Array.newInstance(childClass, 1).getClass();
        Constructor parentCtor = parentClass.getDeclaredConstructor(childArrayClass);
        Constructor parentCtor2 = parentClass.getDeclaredConstructor(int.class);
        Assert.assertNotNull(parentCtor);
        Assert.assertNotNull(parentCtor2);
        Field f1 = parentClass.getDeclaredField("childArray");
        Assert.assertEquals(childArrayClass, f1.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test23() throws Exception{
        SmaliClazz parentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyParent;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyChild;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;"));
        SmaliClazz childClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyChild;");
        SmaliClazz intermediateClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceCtorDependency4/cyclicInheritanceCtorDependencyIntermediate;");

        Class parentClass = parentClazz.getMirroringClass();
        Class childClass = childClazz.getMirroringClass();
        Class intermediateClass = intermediateClazz.getMirroringClass();

        Constructor parentCtor = parentClass.getDeclaredConstructor(intermediateClass);
        Constructor parentCtor2 = parentClass.getDeclaredConstructor(int.class);
        Assert.assertNotNull(parentCtor);
        Assert.assertNotNull(parentCtor2);

        Constructor intermediateCtor = intermediateClass.getDeclaredConstructor(childClass);
        Assert.assertNotNull(intermediateCtor);

        Assert.assertEquals(parentClass, childClass.getSuperclass());
        Constructor childCtor = childClass.getDeclaredConstructor(intermediateClass);
        Assert.assertNotNull(childCtor);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test24() throws Exception{
        SmaliClazz testClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency1/TestClass;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency1/TestClass;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency1/TestClassParent;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency1/TestClassChild;"));
        SmaliClazz testParentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency1/TestClassParent;");
        SmaliClazz testChildClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency1/TestClassChild;");

        Class testClass = testClazz.getMirroringClass();
        Class testParentClass = testParentClazz.getMirroringClass();
        Class testChildClass = testChildClazz.getMirroringClass();

        Assert.assertNotNull(testClass);
        Assert.assertNotNull(testParentClass);
        Assert.assertNotNull(testChildClass);

        Assert.assertEquals(Object.class, testParentClass.getSuperclass());
        Assert.assertEquals(testParentClass, testClass.getSuperclass());
        Assert.assertEquals(testClass, testChildClass.getSuperclass());

        Field f = testParentClass.getDeclaredField("child");
        Assert.assertEquals(testChildClass, f.getType());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test25() throws Exception{
        SmaliClazz testClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency2/TestClass;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency2/TestClass;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency2/TestClassParent;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency2/TestClassChild;"));
        SmaliClazz testParentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency2/TestClassParent;");
        SmaliClazz testChildClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency2/TestClassChild;");

        SmaliArrayClazz testParentArrayClazz = (SmaliArrayClazz) clazzLoader.getClazz("[LcyclicInheritanceParentGrandchildDependency2/TestClassParent;");
        SmaliArrayClazz testChildArrayClazz = (SmaliArrayClazz) clazzLoader.getClazz("[LcyclicInheritanceParentGrandchildDependency2/TestClassChild;");

        Class testClass = testClazz.getMirroringClass();
        Class testParentClass = testParentClazz.getMirroringClass();
        Class testChildClass = testChildClazz.getMirroringClass();

        Field f1 = testClass.getDeclaredField("child");
        Field f2 = testParentClass.getDeclaredField("children");
        Field f3 = testChildClass.getDeclaredField("parents");

        Assert.assertTrue(Modifier.isStatic(f1.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f2.getModifiers()));
        Assert.assertTrue(Modifier.isStatic(f3.getModifiers()));

        Assert.assertEquals(f1.getType(), testChildClass);
        Assert.assertEquals(f2.getType(), testChildArrayClazz.getMirroringClass());
        Assert.assertEquals(f3.getType(), testParentArrayClazz.getMirroringClass());

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test26() throws Exception{
        SmaliClazz testClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency3/TestClass;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency3/TestClass;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency3/TestClassParent;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency3/TestClassChild;"));

        SmaliClazz testParentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency3/TestClassParent;");
        SmaliClazz testChildClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency3/TestClassChild;");

        Class testClass = testClazz.getMirroringClass();
        Class testParentClass = testParentClazz.getMirroringClass();
        Class testChildClass = testChildClazz.getMirroringClass();

        Field testClassField1 = testClass.getDeclaredField("test");
        Field testClassField2 = testClass.getDeclaredField("testChild");
        Field testClassField3 = testClass.getDeclaredField("testParent");

        Field testParentClassField1 = testParentClass.getDeclaredField("test");
        Field testParentClassField2 = testParentClass.getDeclaredField("testChild");
        Field testParentClassField3 = testParentClass.getDeclaredField("testParent");

        Field testChildClassField1 = testChildClass.getDeclaredField("test");
        Field testChildClassField2 = testChildClass.getDeclaredField("testChild");
        Field testChildClassField3 = testChildClass.getDeclaredField("testParent");

        Assert.assertEquals(testClassField1.getType(),testClass);
        Assert.assertEquals(testClassField2.getType(),testChildClass);
        Assert.assertEquals(testClassField3.getType(),testParentClass);

        Assert.assertEquals(testParentClassField1.getType(),testClass);
        Assert.assertEquals(testParentClassField2.getType(),testChildClass);
        Assert.assertEquals(testParentClassField3.getType(),testParentClass);

        Assert.assertEquals(testChildClassField1.getType(),testClass);
        Assert.assertEquals(testChildClassField2.getType(),testChildClass);
        Assert.assertEquals(testChildClassField3.getType(),testParentClass);

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test27() throws Exception{
        SmaliClazz testClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency4/TestClass;");
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency4/TestClassParent;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency4/TestClassChild;"));
        Assert.assertTrue(clazzLoader.isClazzLoaded("LcyclicInheritanceParentGrandchildDependency4/TestIntermediateClass;"));

        SmaliClazz parentClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency4/TestClassParent;");
        SmaliClazz childClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency4/TestClassChild;");
        SmaliClazz intermediateClazz = (SmaliClazz) clazzLoader.getClazz("LcyclicInheritanceParentGrandchildDependency4/TestIntermediateClass;");

        Class testClass = testClazz.getMirroringClass();
        Class parentClass = parentClazz.getMirroringClass();
        Class childClass = childClazz.getMirroringClass();
        Class intermediateClass = intermediateClazz.getMirroringClass();

        Assert.assertEquals(parentClass, testClass.getSuperclass());
        Assert.assertEquals(testClass, childClass.getSuperclass());

        Assert.assertNotNull(testClass.getDeclaredConstructor(childClass));
        Assert.assertNotNull(intermediateClass.getDeclaredConstructor(childClass));
        Assert.assertNotNull(parentClass.getDeclaredConstructor());
        Assert.assertNotNull(parentClass.getDeclaredConstructor(intermediateClass));
        Assert.assertNotNull(childClass.getDeclaredConstructor(childClass));

        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test28() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LmultipleCyclicLoops1/A;");
        //TODO add tests
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test29() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LmultipleCyclicLoops2/A;");
        //TODO add tests
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }

    @Test
    public void test30() throws Exception{
        SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz("LmultipleCyclicLoops3/A;");
        //TODO add tests
        Assert.assertTrue(MockCreator.areAllInternalLogicMapsAndSetsEmpty());
    }
}
