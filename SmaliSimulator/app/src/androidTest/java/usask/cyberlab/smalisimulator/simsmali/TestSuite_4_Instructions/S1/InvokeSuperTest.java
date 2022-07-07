package usask.cyberlab.smalisimulator.simsmali.TestSuite_4_Instructions.S1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import usask.cyberlab.smalisimulator.AssetCopier;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecutionResult;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class InvokeSuperTest {
       private static SmaliClass testSmaliClass;
       private static ClazzLoader loader;

       @BeforeClass
       public static void beforeClass() throws Exception {
           Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
           loader = new ClazzLoader(context.getCacheDir().getAbsolutePath() + "/instructions", context);
           AssetCopier.copyFile("instructions/InvokeSuper.smali");
           AssetCopier.copyFile("instructions/InvokeParent.smali");
           testSmaliClass = loader.getSmaliClass("LInvokeSuper;");
           loader.getClazz("LInvokeSuper;");
       }

       public static void afterClass(){
          testSmaliClass = null;
          loader = null;
       }

       @Test
       public void test1() {
           SmaliMethod sm = testSmaliClass.getMethod("test1()Ljava/lang/String;");
           MethodExecution methodExecution = new MethodExecution(sm,loader);
           methodExecution.execute();
           MethodExecutionResult mr = methodExecution.getExecutionResult();
           Objekt reflectedObjekt = (Objekt) mr.getResult();
           Assert.assertEquals("InvokeParent", reflectedObjekt.toString());
       }

       @Test
       public void test2(){
           SmaliMethod sm = testSmaliClass.getMethod("test2()I");
           MethodExecution methodExecution = new MethodExecution(sm,loader);
           methodExecution.execute();
           MethodExecutionResult mr = methodExecution.getExecutionResult();
           Assert.assertEquals(ResultType.INTEGER, mr.getType());
           Integer i = (Integer) mr.getResult();
           System.out.println(i);
       }
   }
