package usask.cyberlab.smalisimulator;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import usask.cyberlab.smalisimulator.pureMethodSeparator.PureMethodExtractor;
import usask.cyberlab.smalisimulator.safeClassSeparation.Config;
import usask.cyberlab.smalisimulator.safeClassSeparation.InstanceSafeClassExtractor;
import usask.cyberlab.smalisimulator.safeClassSeparation.StaticSafeClassSeparator;
import usask.cyberlab.smalisimulator.safeClassSeparation.TypeHierarchyExtractor;
import usask.cyberlab.smalisimulator.safeClassSeparation.Utils;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;

public class Main {
    int t = 0;
    @Test
    public void test() throws Exception{
        System.out.println("!!!!!!!!");
        for (int i = 0; i < 20; i++) {
            System.out.println(SimulationUtils.getRandomNumberInRange(-1,1));
        }
        System.out.println("!!!!!!!!");
    }

    @Test
    public void separateStaticSafeClasses() throws Exception{
        HashSet<String> initialSafeClasses = Utils.getInitialSafeClasses();
        HashSet<String>[] result =  StaticSafeClassSeparator.start(27,
                initialSafeClasses,
                new HashSet<String>());

        BufferedWriter writer;

        writer = new BufferedWriter(new FileWriter(new File(Config.staticallySafeClassesFilePath)));
        for (String s: result[0]) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();

        writer = new BufferedWriter(new FileWriter(new File(Config.staticallyPartialSafeClassesFilePath)));
        for (String s: result[1]) {
            String classPath = s.split("->")[0];
            if(result[0].contains(classPath)) continue;
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();


    }

    @Test
    public void extractTypeHierarchy() throws Exception{
        TypeHierarchyExtractor.start(27);
    }

    @Test
    public void separateInstanceSafeClasses() throws Exception{
        HashSet<String>[] result = InstanceSafeClassExtractor.start(27);

        BufferedWriter writer;

        writer = new BufferedWriter(new FileWriter(new File("instanceSafeClasses.txt")));
        for (String s: result[0]) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();

        writer = new BufferedWriter(new FileWriter(new File("instancePartialSafeClasses.txt")));
        for (String s: result[1]) {
            String classPath = s.split("->")[0];
            if(result[0].contains(classPath)) continue;
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();
    }

    @Test
    public void separatePureMethods() throws Exception{
        HashSet<String> p = PureMethodExtractor.start(27);
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Config.extractedPureMethodsFilePath)));
        for (String s: p) {
            writer.write(s + "\n");
        }
        writer.flush();
        writer.close();


    }

}
