package usask.cyberlab.smalisimulator.simsmali.emulator;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimSmaliConfig {
    public static int sdk_version = 27;

    // This value shows if the <clinit> method should be executed
    // at the end of mock class loading.
    // Setting this to false is useful for testing.
    public static boolean executeClinit = true;
    public static boolean logClinitExecutionTraceInSmaliClazz = false;

    // This value shows the maximum depth of method invocation stack
    public static int maxMethodDepth = 200;

    public static boolean canThrowNullPointerExceptionOnAmbiguity = false;
    public static boolean canThrowArithmeticExceptionOnAmbiguity = false;
    public static boolean canThrowCheckCastExceptionOnAmbiguity = false;
    public static boolean canThrowArrayIndexOutOfBoundsExceptionOnAmbiguity = false;

    // This value shows if execution trace of constructors should be captured
    public static boolean shouldCaptureConstructorCalls = true;

    // This value shows that if during the execution of an instruction
    // a class could be properly loaded, the instruction should fall back
    // to using Ambiguous value
    public static boolean onClassLoadErrorUseAmbiguousValue = false;

    public static boolean useAmbiguousValueOnMethodInvocationDepthExceed = true;

    public static boolean logTimeForSimulation = false;
    public static boolean logExecutionTraceOfMethodWithErrors = false;


    public static boolean addToStringOfObjectsInExecutionLog = true;
}
