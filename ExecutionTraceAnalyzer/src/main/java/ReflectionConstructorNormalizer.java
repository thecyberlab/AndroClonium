import java.util.ArrayList;

public class ReflectionConstructorNormalizer {

    public static boolean isReflectiveConstructorCall(TraceInstruction ti){
        // check instruction is invoke-virtual
        if(!ti.getInstruction().startsWith("invoke-virtual")) return false;
        String[] instructionSplits = ti.getInstructionSplits();
        String classMethodSig = instructionSplits[instructionSplits.length-2];
        // check that classMethodSig is correct
        if(!classMethodSig.contains("->")) throw new IllegalStateException();
        if(classMethodSig.equals("Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;")) return true;
        if(classMethodSig.equals("Ljava/lang/Class;->newInstance()Ljava/lang/Object;"))return true;
        return false;
    }

//    public static void replaceReflectiveConstructorCallWithNormalInstructions(TraceInstruction reflectiveCtorCall, ArrayList trace){
//        //TODO
//    }
}
