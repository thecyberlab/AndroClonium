package usask.cyberlab.smalisimulator.simsmali.emulator;

import android.content.Context;

import java.io.File;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class Simulator {
    private final ClazzLoader clazzLoader;
    private final File outputDir;

    // the basePath is basically the same as Java CLASSPATH
    public Simulator(String basePath, Context appContext, File outputDir) {
        this.clazzLoader = new ClazzLoader(basePath, appContext);
        if (outputDir.exists() && !outputDir.isDirectory()) throw new SmaliSimulationException();
        outputDir.mkdirs();
        this.outputDir = outputDir;
    }

    public Simulator(ClazzLoader loader, File outputDir) {
        this.clazzLoader = loader;
        if (outputDir.exists() && !outputDir.isDirectory()) throw new SmaliSimulationException();
        outputDir.mkdirs();
        this.outputDir = outputDir;
    }

    public ClazzLoader getClazzLoader() {
        return clazzLoader;
    }

    public void simulateStaticMethod(String classMethodSignature, Object[] args) {
        SmaliMethod smaliMethod = extractSmaliMethod(classMethodSignature);
        if (smaliMethod == null) throw new SmaliSimulationException("Method not found!");
        checkArgumentsTypes(smaliMethod, args);
        MultipleExecutionsManager.getPossibleExecutions(smaliMethod, clazzLoader, args, outputDir);
    }

    public void simulateInstanceMethod(String classMethodSignature, Objekt self, Object[] args) {
        SmaliMethod smaliMethod = extractSmaliMethod(classMethodSignature);
        if (smaliMethod == null) throw new SmaliSimulationException("Method not found!");
        checkArgumentsTypes(smaliMethod, args);
        MultipleExecutionsManager.getPossibleExecutions(smaliMethod, clazzLoader, args, self, outputDir);
    }

    public void simulateInstanceMethod(String classMethodSignature, AmbiguousValue self, Object[] args) {
        SmaliMethod smaliMethod = extractSmaliMethod(classMethodSignature);
        if (smaliMethod == null) throw new SmaliSimulationException("Method not found!");
        checkArgumentsTypes(smaliMethod, args);
        MultipleExecutionsManager.getPossibleExecutions(smaliMethod, clazzLoader, args, self, outputDir);
    }

    private SmaliMethod extractSmaliMethod(String methodSignature) {
        String classPath = methodSignature.split("->")[0];
        String methodName = methodSignature.split("->")[1];
        SmaliClass smaliClass = clazzLoader.getSmaliClass(classPath);
        SmaliMethod smaliMethod = smaliClass.getMethod(methodName);
        return smaliMethod;
    }

    private boolean isArgTypeOf(String argType, Object argument) {
        if ("I".equals(argType) && argument instanceof Integer) return true;
        else if ("F".equals(argType) && argument instanceof Float) return true;
        else if ("D".equals(argType) && argument instanceof Double) return true;
        else if ("J".equals(argType) && argument instanceof Long) return true;
        else if ("Z".equals(argType) && argument instanceof Boolean) return true;
        else if ("S".equals(argType) && argument instanceof Short) return true;
        else if ("B".equals(argType) && argument instanceof Byte) return true;
        else if ("C".equals(argType) && argument instanceof Character) return true;
        else if (argument instanceof AbstractObjekt) {
            AbstractObjekt o = (AbstractObjekt) argument;
            if (o == null) {
                return true;
            }
            return o.isInstanceOf(argType);
        }
        return false;
    }

    private void checkArgumentsTypes(SmaliMethod smaliMethod, Object[] args) {
        // check method arguments length
        int validArgCount = smaliMethod.getNumberOfArguments();
        int argsLen = 0;
        if (args != null) {
            argsLen = args.length;
        }
        if (argsLen != validArgCount) {
            throw new SmaliSimulationException("Invalid method arguments, expected array of length " + smaliMethod.getNumberOfArguments());
        }
        String[] argTypes = smaliMethod.getArgumentTypes();
        for (int i = 0; i < argTypes.length; i++) {
            if (args[i] instanceof AmbiguousValue) {
                AmbiguousValue ambiguousValue = (AmbiguousValue) args[i];
                //TODO instead of equal I sould check is instance of
                if (!ambiguousValue.getType().equals(argTypes[i])) {
                    throw new SmaliSimulationException("Argument " + i + " was expect to be instance of " + argTypes[i]);
                }
            } else if (!isArgTypeOf(argTypes[i], args[i])) {
                throw new SmaliSimulationException("Argument " + i + " was expect to be instance of " + argTypes[i]);
            }
        }
    }

}
