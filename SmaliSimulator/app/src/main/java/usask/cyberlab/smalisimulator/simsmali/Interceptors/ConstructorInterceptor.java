package usask.cyberlab.smalisimulator.simsmali.Interceptors;

import net.bytebuddy.implementation.bind.annotation.This;

import java.util.Stack;

import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ConstructorInterceptor {

    private final SmaliMethod sm;
    private final ClazzLoader clazzLoader;

    // These two instance fields are needed to pass information between
    // beforeSuperConstructorCall logic and afterSuperConstructorCall logic
    private ExecutionTrace et;
    private ConstructorInterceptorHandler constructorInterceptorHandler;

    public static Objekt selfWrapper;
    public static Object[] argsWrappers;

//    public static ExecutionTrace constructorExecutionTrace;
    public static Stack<ExecutionTrace> executionTraceStack = new Stack<>();


    static boolean callerIsByteBuddyForChildConstructor = false;

    public ConstructorInterceptor(SmaliMethod sm, ClazzLoader clazzLoader){
        this.sm = sm;
        this.clazzLoader = clazzLoader;
    }

    public class CtorInterceptor1 {

        public void initiateConstruct() throws Throwable{

            // here we want to get the Class object for the class that contains
            // this constructor method.
            // The reason we need the Class object is that we need to set the values that
            // need to be passed around in the argument container fields that we created to be able to pass the args
            SmaliClazz smaliClazz = (SmaliClazz) clazzLoader.getClazz(sm.getContainingClass().getClassPath());
            Class<?> cls = smaliClazz.getMirroringClass();

            boolean callerIsByteBuddyForChildConstructor = ConstructorInterceptor.callerIsByteBuddyForChildConstructor;
            ConstructorInterceptor.callerIsByteBuddyForChildConstructor = false;

            // here we create the execution trace object that we want to keep the traces in
            et = new ExecutionTrace(sm, clazzLoader);

            if(executionTraceStack.empty() || !callerIsByteBuddyForChildConstructor){
                executionTraceStack.add(et);
            }
            else {
                ExecutionTrace tmp = executionTraceStack.peek();
                while (tmp.size() > 0 && tmp.get(tmp.size()-1) instanceof ExecutionTrace){
                    tmp = (ExecutionTrace) tmp.get(tmp.size()-1);
                }
                tmp.addExecutionTrace(et);
            }

            // because of keeping the objekts with Ambiguous value we need to use Objekt wrappers
            // as the actual arguments we need to use for the logic of our constructor
            // just to be safe if argsWrappers in null we use the java objects
            // that have been passed to the constructor

            Object[] toUseArgs = argsWrappers;
            argsWrappers = null;

            Objekt toUseSelfWrapper = selfWrapper;
            selfWrapper = null;

            constructorInterceptorHandler = new ConstructorInterceptorHandler();
            constructorInterceptorHandler.beforeSuperConstructorCall(sm, clazzLoader, toUseArgs, cls, et, toUseSelfWrapper);
        }
    }

    public class CtorInterceptor2 {

        public void resumeConstruct(@This Object self) throws Throwable{

//            if(constructorExecutionTrace != null){
//                // here constructorExecutionTrace is the executionTrace of previous nested constructor calls
//                et.addExecutionTrace(constructorExecutionTrace);
//            }
            constructorInterceptorHandler.afterSuperConstructorCall(sm, clazzLoader, et, self);
            constructorInterceptorHandler = null;
//            constructorExecutionTrace = et;

        }
    }

}
