package usask.cyberlab.smalisimulator.simsmali.representations;

import org.jf.dexlib2.builder.BuilderTryBlock;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.writer.builder.BuilderMethod;
import org.jf.dexlib2.writer.builder.BuilderTypeList;
import org.jf.dexlib2.writer.builder.BuilderTypeReference;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import usask.cyberlab.smalisimulator.Utils;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeDirectInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.MoveInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstructionFactory;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;

/**
 * this class represents any filed defined in a smali file
 */
public class SmaliMethod extends SmaliItem {

    private final ArrayList<SmaliInstruction> instructionsList;
    private final boolean hasImplementation;
    private final String[] argumentTypes;
    private final String returnType;
    private final String classMethodSignature;
    private final SmaliTryBlock[] tryBlocks;
    private final int totalNumberOfRegistersUsed;
    private final SmaliClass containingClass;
    private final int numberOfRegistersNeededForPassedArguments;


    protected SmaliMethod(SmaliClass containingClass,BuilderMethod methodDef) {
        super(methodDef.getAccessFlags());
        this.containingClass = containingClass;
        MethodImplementation methodImplementation = methodDef.getImplementation();
        if(methodImplementation != null){
            this.hasImplementation = true;

            List<BuilderTryBlock> builderTryBlocks = (List<BuilderTryBlock>) methodImplementation.getTryBlocks();
            tryBlocks = new SmaliTryBlock[builderTryBlocks.size()];
            for (int i = 0, builderTryBlocksSize = builderTryBlocks.size(); i < builderTryBlocksSize; i++) {
                BuilderTryBlock tb = builderTryBlocks.get(i);
                tryBlocks[i] = new SmaliTryBlock(tb);
            }

            totalNumberOfRegistersUsed = methodImplementation.getRegisterCount();

            ArrayList<SmaliInstruction> instructionsList = new ArrayList<>();
            int instructionCounter = 0;
            Iterable<? extends Instruction> rawInstructions = methodImplementation.getInstructions();
            for (Instruction i : rawInstructions) {
                SmaliInstruction sa = SmaliInstructionFactory.buildSmaliInstruction(i, instructionCounter);
                if(sa == null){
                    throw new SmaliSimulationException();
                }
                instructionsList.add(sa);
                instructionCounter++;
            }
            this.instructionsList = instructionsList;
        }
        else {
            this.hasImplementation = false;
            tryBlocks = null;
            totalNumberOfRegistersUsed = 0;
            instructionsList = new ArrayList<>();
        }
        this.classMethodSignature = methodDef.toString();

        BuilderTypeList parameterTypes = methodDef.getParameterTypes();
        String[] argTypes = new String[parameterTypes.size()];
        for (int i = 0; i < argTypes.length; i++) {
            BuilderTypeReference typeReference = parameterTypes.get(i);
            argTypes[i] = typeReference.getType();
        }
        this.argumentTypes = argTypes;
        this.returnType = methodDef.getReturnType();

        int a = 0;
        for (String s: this.getArgumentTypes()) {
            if(s.equals("D") || s.equals("J")){
                a += 2;
            }
            else {
                a++;
            }
        }
        numberOfRegistersNeededForPassedArguments = a;

    }

    protected SmaliMethod(SmaliMethod smaliMethod){
        super(smaliMethod.flags);
        this.containingClass = smaliMethod.containingClass;
        this.hasImplementation = smaliMethod.hasImplementation;
        this.argumentTypes = smaliMethod.argumentTypes;
        this.returnType = smaliMethod.returnType;
        this.classMethodSignature = smaliMethod.classMethodSignature;
        this.instructionsList = smaliMethod.instructionsList;
        this.tryBlocks = smaliMethod.tryBlocks;
        this.totalNumberOfRegistersUsed = smaliMethod.totalNumberOfRegistersUsed;
        this.numberOfRegistersNeededForPassedArguments = smaliMethod.numberOfRegistersNeededForPassedArguments;
    }

    public boolean hasImplementation() {
        return hasImplementation;
    }

    public boolean isNative() {
        return Modifier.isNative(flags);
    }

    public boolean isAbstract(){
        return Modifier.isAbstract(flags);
    }

    public boolean isBridge(){
        int isBridge = (flags & 0x00000040) / 0x00000040;
        return isBridge == 1;
    }

    public ArrayList<SmaliInstruction> getInstructionList(){
        return instructionsList;
    }

    public String getClassMethodSignature(){
        return classMethodSignature;
    }

    public SmaliTryBlock[] getTryBlockList(){
        return tryBlocks;
    }

    public int getNumberOfArguments(){
        return argumentTypes.length;
    }

    public String[] getArgumentTypes(){
        return argumentTypes;
    }

    /**
     * @return the total number of registers used in a method which is equal to
     * sum of number of local registers needed, number of parameters in method signature
     * and in case of instance methods one more register to hold the reference to the object
     * containing the method
     */
    public int getTotalNumberOfRegistersUsed(){
        return this.totalNumberOfRegistersUsed;
    }

    /**
     * @return this method returns the number of registers needed for the passed arguments.
     * by passed arguments the arguments specified in the method signature are meant
     * meaning that the register pointing to "this" object is ignored.
     */
    public int getNumberOfRegistersNeededForPassedArguments(){
        return numberOfRegistersNeededForPassedArguments;
    }

    /**
     * @return this method returns the number of start register of passed arguments.
     * by passed arguments the arguments specified in the method signature are meant
     * meaning that the register pointing to "this" object is ignored.
     */
    public int getFirstPassedArgumentRegisterNumber(){
        return this.getTotalNumberOfRegistersUsed() - this.getNumberOfRegistersNeededForPassedArguments();
    }

    public int getNumberOfLocalRegisters(){
        if(this.isStatic()){
            return this.getTotalNumberOfRegistersUsed() - this.getNumberOfRegistersNeededForPassedArguments();
        }
        else {
            return this.getTotalNumberOfRegistersUsed() - this.getNumberOfRegistersNeededForPassedArguments() - 1;
        }
    }

    public String getReturnType(){
        return returnType;
    }

    public boolean isExecutionEntryPoint(ClazzLoader clazzLoader){
        if(this.classMethodSignature.contains("<init>")) return false;
        if(this.classMethodSignature.contains("<clinit>")) return false;
        if(this.getContainingClass().isAbstract()) return false;
        if(this.getContainingClass().isInterface()) return false;
        if(this.getContainingClass().isEnum()) return false;
        if(this.getContainingClass().isAnnotation()) return false;
        //TODO with the current list of entry points and callbacks
        // we don't have any static methods as execution entry points
        // but in general I am not sure if Android does not have any static method entry points
        if(this.isStatic()) return false;

        if(overridesMethodInAndroidEntryPointsMethods(clazzLoader)) return true;
        if(implementsAndroidCallBackInterface(clazzLoader)) return true;
        return false;
    }

    private boolean overridesMethodInAndroidEntryPointsMethods(ClazzLoader clazzLoader){
        for(String s: clazzLoader.getAndroidEntryPointMethods()){
            String entryPointClass = s.split("->")[0];
            String entryPointMethod = s.split("->")[1];
            String thisMethodName = classMethodSignature.split("->")[1];
            if(thisMethodName.equals(entryPointMethod)) {
                if (this.getContainingClass().extendsFrom(entryPointClass, clazzLoader)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean implementsAndroidCallBackInterface(ClazzLoader clazzLoader){
        HashSet<String> allImplementedInterfaces = getAllImplementedInterfacesInSmaliClass(this.getContainingClass(), clazzLoader);
        for(String ifaceType: allImplementedInterfaces){
            if(clazzLoader.getAndroidCallBackInterfaces().contains(ifaceType)) {
                String methodSig = this.classMethodSignature.split("->")[1];
                String thisMethodNameOnly = methodSig.substring(methodSig.indexOf('('));
                ReflectedClazz clazz = (ReflectedClazz) clazzLoader.getClazz(ifaceType);
                //TODO======================== @hide api, although I don't think
                // android @hide apis matter for callBackInterfaces
                Method[] methods = clazz.getMirroringClass().getDeclaredMethods();
                for(Method m: methods){
                    //TODO also check arguments to know if the method is
                    // same as the entryPoints in the list
                    if(m.getName().equals(thisMethodNameOnly)) return true;
                }


            }
        }
        return false;
    }

    private static HashSet<String> getAllImplementedInterfacesInSmaliClass(SmaliClass sc,
                                                                           ClazzLoader loader){
        HashSet<String> result = new HashSet<>();
        result.addAll(sc.getInterfaces());
        String parentClassPath = sc.getParentClassPath();
        // if user defined code
        if(!SimulationUtils.isJavaOrAndroidExistingClass(parentClassPath)){
            try {
                SmaliClass parentSmaliClass = loader.getSmaliClass(parentClassPath);
                result.addAll(getAllImplementedInterfacesInSmaliClass(parentSmaliClass, loader));
            }
            catch (Exception e){

            }
        }
        return result;
    }

    public SmaliClass getContainingClass() {
        return containingClass;
    }

    public String toString(){
        return classMethodSignature;
    }

    public static int getSuperConstructorCallPosition(SmaliMethod sm){
        InvokeDirectInstruction invokeDirectInstruction = getSuperConstructorCall(sm);
        int pos = sm.getInstructionList().indexOf(invokeDirectInstruction);
        if(pos == -1){
            throw new SmaliSimulationException();
        }
        return pos;
    }

    public static InvokeDirectInstruction getSuperConstructorCall(SmaliMethod sm){
//        System.out.println("Started getting super constructor call: " + sm.getClassMethodSignature() + " " + Utils.getNowDateTimeString());
        // check given SmaliMethod is for a constructor
        if(!sm.getClassMethodSignature().contains("<init>(")) {
            throw new SmaliSimulationException();
        }
        String selfClassPath = sm.getContainingClass().getClassPath();
        String parentClassPath = sm.getContainingClass().getParentClassPath();
        ArrayList<SmaliInstruction> instructions = sm.getInstructionList();

        //1- first we extract the list of potential super constructor calls
        ArrayList<InvokeDirectInstruction> possibleSuperConstructorCalls = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            SmaliInstruction instruction = instructions.get(i);
            if(instruction instanceof InvokeDirectInstruction){
                InvokeDirectInstruction invokeDirectInstruction = (InvokeDirectInstruction) instruction;

                if(invokeDirectInstruction.getMethodOnlySignature().startsWith("<init>(")
                    && (invokeDirectInstruction.getClassPath().equals(parentClassPath)
                        || invokeDirectInstruction.getClassPath().equals(selfClassPath))
                ){
                    possibleSuperConstructorCalls.add(invokeDirectInstruction);
                }
            }
        }

        if(possibleSuperConstructorCalls.size() == 0){
            throw new SmaliSimulationException("super constructor call not found in " + sm.getClassMethodSignature());
        }
        if(possibleSuperConstructorCalls.size() == 1){
//            System.out.println("Finished getting super constructor call: " + sm.getClassMethodSignature() + " " + Utils.getNowDateTimeString());
            return possibleSuperConstructorCalls.get(0);
        }

        // 2- if there are multiple instructions that can be super constructor call
        // we have to figure out which one points to the correct ref object
        ArrayList<InvokeDirectInstruction> superConstructorCallList = new ArrayList<>();
        List<StaticConstructorCodeTraverser.StaticExecution> staticExecutions = StaticConstructorCodeTraverser.getPossiblePaths(sm);
        for(StaticConstructorCodeTraverser.StaticExecution se: staticExecutions){
            ArrayList<SmaliInstruction> trace = se.executionTrace;
            for(SmaliInstruction tracedInstruction: trace){
                if(possibleSuperConstructorCalls.contains(tracedInstruction)){
                    InvokeDirectInstruction invokeDirectInstruction = (InvokeDirectInstruction) tracedInstruction;
                    if(isConstructorCallOnSelfObject(invokeDirectInstruction, sm, trace)
                        && !superConstructorCallList.contains(invokeDirectInstruction)){
                        superConstructorCallList.add(invokeDirectInstruction);
                        break;
                    }
                }
            }
        }
        if(superConstructorCallList.size() == 0){
            throw new SmaliSimulationException("super constructor call not found in " + sm.getClassMethodSignature());
        }
        else if(superConstructorCallList.size() > 1){
            throw new SmaliSimulationException("multiple super constructor call found in " + sm.getClassMethodSignature());
        }
//        System.out.println("Finished getting super constructor call: " + sm.getClassMethodSignature() + " " + Utils.getNowDateTimeString());
        return superConstructorCallList.get(0);
    }

    static boolean isConstructorCallOnSelfObject(InvokeDirectInstruction invokeDirectInstruction,
                                                         SmaliMethod sm,
                                                         ArrayList<SmaliInstruction> trace){
        int checkingRegisterNumber = invokeDirectInstruction.getRegisterNumbers()[0];
        int passedRefObjectToCtorRegisterNumber = sm.getNumberOfLocalRegisters();

        for(int i=trace.indexOf(invokeDirectInstruction); i>=0 ; i--){
            SmaliInstruction prevInst = trace.get(i);
            if(prevInst instanceof MoveInstruction){
                MoveInstruction moveInstruction = (MoveInstruction) prevInst;
                // if move instruction is writing to the register which is going to
                // the be the register used for the reference object of InvokeDirectInstruction
                if(moveInstruction.type == MoveInstruction.MoveInstructionType.OBJECT
                        && moveInstruction.destRegisterNumber == checkingRegisterNumber){
                    if(moveInstruction.srcRegisterNumber == passedRefObjectToCtorRegisterNumber){
                        return true;
                    }
                    else {
                        checkingRegisterNumber = moveInstruction.srcRegisterNumber;
                    }
                }
            }
        }
        if(checkingRegisterNumber == passedRefObjectToCtorRegisterNumber) return true;
        return false;
    }


//    public static int getConstructorCallPosition(SmaliMethod sm){
//        // check given SmaliMethod is for a constructor
//        if(!sm.getClassMethodSignature().contains("<init>(")) {
//            throw new SmaliSimulationException();
//        }
//        String selfClassPath = sm.getContainingClass().getClassPath();
//        String parentClassPath = sm.getContainingClass().getParentClassPath();
//
//        ArrayList<SmaliInstruction> instructions = sm.getSmaliInstructionList();
//        for (int i=0; i<instructions.size() ; i++) {
//            SmaliInstruction instruction = instructions.get(i);
//            if(instruction instanceof InvokeDirectInstruction){
//                InvokeDirectInstruction directInstruction = (InvokeDirectInstruction) instruction;
//
//                // this condition checks that the direct instruction is invoked on correct type
//                // for super constructor, type of self or parent
//                boolean c1 = directInstruction.getClassPath().equals(parentClassPath) || directInstruction.getClassPath().equals(selfClassPath);
//
//                // this condition checks that the invoked method is in fact a constructor
//                boolean c2 = directInstruction.getMethodOnlySignature().startsWith("<init>(");
//
//                // here we want to make sure that the invoked method is done the correct object
//                // since we can an example like this:
//                // new C(new C());
//                boolean c3 = isConstructorOnSelfObject(directInstruction, sm);
//
//                if(c1 && c2 && c3){
//                    return i;
//                }
//            }
//        }
//        throw new SmaliSimulationException(sm.getContainingClass()  + "|" + sm.getClassMethodSignature());
//    }
//
//    private static boolean isConstructorOnSelfObject(InvokeDirectInstruction directInstruction,
//                                                     SmaliMethod sm){
//        int refObjectRegisterNumber = sm.getNumberOfLocalRegisters();
//        int firstPassedArgRegNum = directInstruction.getRegisterNumbers()[0];
//
//
//        if(firstPassedArgRegNum == refObjectRegisterNumber) return true;
//        if(isInvokeDirectRefObjectTheConstructorSelfObject(directInstruction, sm)) return true;
//        return false;
//    }
//
//    private static boolean isInvokeDirectRefObjectTheConstructorSelfObject(InvokeDirectInstruction directInstruction,
//                                                                           SmaliMethod smaliMethod){
//        int constructorSelfObjectRegisterNumber = smaliMethod.getNumberOfLocalRegisters();
//        int passedReferenceObjectRegisterNumber = directInstruction.getRegisterNumbers()[0];
//        ArrayList<SmaliInstruction> instructions = smaliMethod.getSmaliInstructionList();
//
//        // from the directInstruction in the list of instruction we go back
//        // and find the move instructions
//        int ctorCallPos = instructions.indexOf(directInstruction);
//        for (int i = ctorCallPos-1; i >=0 ; i--) {
//            SmaliInstruction prevInst = instructions.get(i);
//            if(prevInst instanceof MoveInstruction){
//                MoveInstruction moveInstruction = (MoveInstruction) prevInst;
//
//                // if move instruction is writing to the register which is going to
//                // the be the register used for the reference object of InvokeDirectInstruction
//                if(moveInstruction.type == MoveInstruction.MoveInstructionType.OBJECT
//                        && moveInstruction.destRegisterNumber == passedReferenceObjectRegisterNumber){
//                    // if the written value is actually constructorSelfObjectRegisterNumber
//                    // then we know the passed object is self Object
//                    if(moveInstruction.srcRegisterNumber == constructorSelfObjectRegisterNumber){
//                        return true;
//                    }
//                    // if it is writing another register we need to
//                    // track that one from now
//                    else {
//                        passedReferenceObjectRegisterNumber = moveInstruction.srcRegisterNumber;
//                    }
//                }
//            }
//        }
//        return false;
//    }

}
