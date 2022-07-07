package usask.cyberlab.smalisimulator.simsmali.Interceptors;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.*;
import usask.cyberlab.smalisimulator.simsmali.exceptions.*;
import usask.cyberlab.smalisimulator.simsmali.instructions.FakeSuperConstructorCallInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.PartialSmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ConstructorInterceptorHandler {

    private HashMap<Integer, Register> savedRegsMap;
    private Objekt selfWrapper;

    // The chain of constructor invocations is called by byte-buddy but logically
    // the rest of instructions in the constructor SmaliMethod needs to be executed
    // this method is responsible for executing the instructions before the super constructor call
    void beforeSuperConstructorCall(SmaliMethod sm, ClazzLoader loader,
                                                  Object[] argWrappers,
                                                  Class cls, ExecutionTrace et,
                                                  Objekt selfWrapper) throws Throwable{
        //TODO what if java/android calls a constructor of smali object internally
        // and thus selfWrapper would be null??
        // This only could happen if java/Android api would use reflection
        // which in that case that method should probably be unsafe
        if(selfWrapper == null){
            throw new SmaliSimulationException("self wrapper should not be null");
        }


        // the sub-set of instructions that should be executed before super method call
        PartialSmaliMethod partialSmaliMethod = new PartialSmaliMethod(sm, SmaliMethod.getSuperConstructorCallPosition(sm));

        MethodExecution methodExecution = new MethodExecution(partialSmaliMethod, loader, selfWrapper);
        SimulationUtils.setMethodExecutionWrappedArguments(methodExecution, argWrappers);

//        int superConstructorCallPosition = SmaliMethod.getSuperConstructorCallPosition(sm);
//        InvokeInstruction superConstructorCall = (InvokeInstruction) sm.getSmaliInstructionList().get(superConstructorCallPosition);

        InvokeInstruction superConstructorCall = SmaliMethod.getSuperConstructorCall(sm);

        // if there is no instruction before the superConstructor call we don't need to execute anything
        SmaliInstruction o = partialSmaliMethod.getSmaliInstructionListWithoutSuperConstructorCall().get(0);
        if(!( o instanceof FakeSuperConstructorCallInstruction)){
            methodExecution.execute();
            MethodExecutionResult executionResult = methodExecution.getExecutionResult();
            ExecutionTrace executionTrace = methodExecution.getExecutionTrace();
            // if there has been no exception before the super constructor call
            // we add the super constructor call to the executionTrace
            if(! (executionResult != null && executionResult.getType().equals(ResultType.EXCEPTION)) ) {
                executionTrace.addInstruction(superConstructorCall, methodExecution);
            }

            et.extendExecutionTrace(executionTrace);

            //throw exception if the constructor has resulted in exception
            // the null check is there since if an exception is not thrown
            // then MethodExecutionResult should be null since the return instruction
            // has not been executed yet
            if(executionResult != null && executionResult.getType().equals(ResultType.EXCEPTION)){
                if(executionResult.getResult() instanceof AmbiguousValue){
                    AmbiguousValue av = (AmbiguousValue) executionResult.getResult();
                    throw new AmbiguousExceptionThrownException(av);
                }
                else {
                    Objekt exceptionObjekt = (Objekt) executionResult.getResult();
                    Throwable t = (Throwable) exceptionObjekt.getMirroringObject();
                    throw t;
                }
            }


            int[] registersUsedForSuperConstructor = superConstructorCall.getRegisterNumbers();
            Clazz superConstructorClazz = loader.getClazz(superConstructorCall.getClassPath());

            // we check to not call the super constructor call
            // with ambiguous value if the super constructor call is java/android class
            boolean argumentsHaveAmbiguity = false;
            HashMap<Integer, Register> allRegisters = methodExecution.getAllRegisters();
            for(int i = 1; i < registersUsedForSuperConstructor.length ; i++){
                int regNumber = registersUsedForSuperConstructor[i];
                Register r = allRegisters.get(regNumber);
                if(r!= null && r.containsAmbiguousValue()){
                    argumentsHaveAmbiguity = true;
                }
            }

            if(argumentsHaveAmbiguity && superConstructorClazz instanceof ReflectedClazz){
                throw new AmbiguousInternalConstructorInvocationException();
            }
        }
        else {
            et.addInstruction(superConstructorCall, methodExecution);
        }
        this.savedRegsMap = methodExecution.getAllRegisters();
        this.selfWrapper = selfWrapper;

        String superConstructorType = superConstructorCall.getClassPath();
        Clazz clazz = loader.getClazz(superConstructorType);
        // we have to do the saving no matter what, but setting the wrappers
        // depends if the what super constructor is
        Object[] res = saveAndGetSuperConstructorCallArgument(superConstructorCall, savedRegsMap, sm, cls);
        if(clazz instanceof SmaliClazz) {
            ConstructorInterceptor.callerIsByteBuddyForChildConstructor = true;
            // we need to set the selfWrapper and argWrappers since we need the wrappers for the next
            // ConstructorInterceptor.initiateConstruct() in case the nested super constructor call
            ConstructorInterceptor.selfWrapper = selfWrapper;
            ConstructorInterceptor.argsWrappers = res;
        }

    }

    void afterSuperConstructorCall(SmaliMethod sm,
                                   ClazzLoader loader,
                                   ExecutionTrace et,
                                   Object self) throws Throwable{
        // now that we have the self object we can set it in selfWrapper
        // if is not filled yet. we need this if since in case of nested
        // constructor calls, multiple resumeConstruct methods are executed
        if(selfWrapper.getMirroringObject() == null){
            selfWrapper.setMirroringObject(self);
        }
        HashMap<Integer, Register> savedRegs = savedRegsMap;
        savedRegsMap = null;
        PartialSmaliMethod partialSmaliMethod = new PartialSmaliMethod(sm, SmaliMethod.getSuperConstructorCallPosition(sm));
        MethodExecution methodExecution = new MethodExecution(partialSmaliMethod, loader, selfWrapper);
        methodExecution.setInstructionPointer(partialSmaliMethod.getSuperConstructorCallPos()+1);
        methodExecution.setAllRegisters(savedRegs);

        int refObjektRegisterNumber = sm.getNumberOfLocalRegisters();
        methodExecution.getRegister(refObjektRegisterNumber).set(selfWrapper);

        methodExecution.execute();

        ExecutionTrace secondPartExecutionTrace = methodExecution.getExecutionTrace();
        et.extendExecutionTrace(secondPartExecutionTrace);

        MethodExecutionResult executionResult = methodExecution.getExecutionResult();
        if(executionResult.getType().equals(ResultType.EXCEPTION)){
            if(executionResult.getResult() instanceof AmbiguousValue){
                AmbiguousValue av = (AmbiguousValue) executionResult.getResult();
                throw new AmbiguousExceptionThrownException(av);
            }
            else {
                Objekt exceptionObjekt = (Objekt) executionResult.getResult();
                Throwable t = (Exception) exceptionObjekt.getMirroringObject();
                throw t;
            }
        }
    }


    private static Object[] saveAndGetSuperConstructorCallArgument(InvokeInstruction invokeInstruction, HashMap<Integer,
            Register> allRegisters,SmaliMethod ctorSmaliMethod, Class cls) throws Exception {

        int regPosCounter = 1;
        int filedNameCounter = 0;
        int[] registerNumbers = invokeInstruction.getRegisterNumbers();
        String[] superConstructorArgTypes = invokeInstruction.getArgTypes();
        Object[] newArgWrappers = new Object[superConstructorArgTypes.length];
        for(int i=0; i<superConstructorArgTypes.length; i++){
            String argType = superConstructorArgTypes[i];
            Register r = allRegisters.get(registerNumbers[regPosCounter]);
            if(r == null){
                throw new SmaliSimulationException();
            }
            String fieldName = MockCreator.getConstructorArgHolderName(ctorSmaliMethod, filedNameCounter);
            Field f = cls.getField(fieldName);
            if(r.containsAmbiguousValue()){
                AmbiguousValue av = r.getAmbiguousValue();
                if(SimulationUtils.isPrimitiveType(argType)){
                    f.set(cls, SimulationUtils.getDefaultPrimitiveValue(argType.toCharArray()[0]));
                }
                else {
                    f.set(cls, null);
                }
                newArgWrappers[i] = av;
                if(argType.equals("D") || argType.equals("J")){
                    regPosCounter++;
                }
            }
            else if(argType.equals("I")){
                int integerValue = r.getIntValue();
                f.setInt(cls, integerValue);
                newArgWrappers[i] = integerValue;
            }
            else if(argType.equals("F")){
                float floatValue = r.getFloatValue();
                f.setFloat(cls, floatValue);
                newArgWrappers[i] = floatValue;
            }
            else if(argType.equals("D")){
                double d = r.getDoubleValue();
                f.setDouble(cls, d);
                newArgWrappers[i] = d;
                regPosCounter++;
            }
            else if(argType.equals("J")){
                long j = r.getLongValue();
                f.setLong(cls, j);
                newArgWrappers[i] = j;
                regPosCounter++;
            }
            else if(argType.equals("Z")){
                boolean z = r.getBooleanValue();
                newArgWrappers[i] = z;
                f.setBoolean(cls, z);
            }
            else if(argType.equals("B")){
                byte b = r.getByteValue();
                f.setByte(cls, b);
                newArgWrappers[i] = b;
            }
            else if(argType.equals("S")){
                short s = r.getShortValue();
                f.setShort(cls, s);
                newArgWrappers[i] = s;
            }
            else if(argType.equals("C")){
                char c = r.getCharValue();
                newArgWrappers[i] = c;
                f.setChar(cls, c);
            }
            else {
                AbstractObjekt abstractObjekt = r.getReferencedObjectValue();
                if (abstractObjekt == null) {
                    f.set(cls, null);
                    newArgWrappers[i] = null;
                } else {
                    Object mirrorValue = abstractObjekt.getMirroringObject();
                    f.set(cls, mirrorValue);
                    newArgWrappers[i] = abstractObjekt;
                }
            }
            regPosCounter++;
            filedNameCounter++;
        }

        return newArgWrappers;
    }
}
