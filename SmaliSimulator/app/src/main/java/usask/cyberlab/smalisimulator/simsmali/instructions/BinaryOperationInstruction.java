package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22b;
import org.jf.dexlib2.builder.instruction.BuilderInstruction22s;
import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;
import org.json.JSONObject;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;


public class BinaryOperationInstruction extends SmaliInstruction{
    private final int destRegisterNumber;
    private final int firstSrcRegisterNumber;
    private int secondSrcRegisterNumber;
    private int literalConstant;

    private final BinaryOperationInstructionType opType;
    private final BinaryOperationFamilyType familyType;

    private enum BinaryOperationFamilyType{
        LITERAL,
        TWO_ADDR,
        NORMAL,
    }

    public BinaryOperationInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        if(instructionDef instanceof BuilderInstruction23x){
            BuilderInstruction23x bi = (BuilderInstruction23x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();
            this.firstSrcRegisterNumber = bi.getRegisterB();
            this.secondSrcRegisterNumber = bi.getRegisterC();
            this.familyType = BinaryOperationFamilyType.NORMAL;
        }
        else if(instructionDef instanceof BuilderInstruction12x){
            BuilderInstruction12x bi = (BuilderInstruction12x) instructionDef;
            this.destRegisterNumber = bi.getRegisterA();
            this.firstSrcRegisterNumber = bi.getRegisterA();
            this.secondSrcRegisterNumber = bi.getRegisterB();
            this.familyType = BinaryOperationFamilyType.TWO_ADDR;
        }
        else if(instructionDef instanceof BuilderInstruction22s){
            BuilderInstruction22s bi = (BuilderInstruction22s) instructionDef;
            this.familyType = BinaryOperationFamilyType.LITERAL;
            this.destRegisterNumber = bi.getRegisterA();
            this.firstSrcRegisterNumber = bi.getRegisterB();
            this.literalConstant = bi.getNarrowLiteral();
        }
        else if(instructionDef instanceof BuilderInstruction22b){
            BuilderInstruction22b bi = (BuilderInstruction22b) instructionDef;
            this.familyType = BinaryOperationFamilyType.LITERAL;
            this.destRegisterNumber = bi.getRegisterA();
            this.firstSrcRegisterNumber = bi.getRegisterB();
            this.literalConstant = bi.getNarrowLiteral();
        }
        else {
            throw new SmaliSimulationException("Bad InstructionDef!");
        }
        String opCodeName = instructionDef.getOpcode().name;
        if(opCodeName.contains("add-int")){
            this.opType = BinaryOperationInstructionType.ADD_INT;
        }
        else if(opCodeName.contains("rsub-int")){
            this.opType = BinaryOperationInstructionType.RSUB_INT;
        }
        else if(opCodeName.contains("sub-int")){
            this.opType = BinaryOperationInstructionType.SUB_INT;
        }
        else if(opCodeName.contains("mul-int")){
            this.opType = BinaryOperationInstructionType.MUL_INT;
        }
        else if(opCodeName.contains("div-int")){
            this.opType = BinaryOperationInstructionType.DIV_INT;
        }
        else if(opCodeName.contains("rem-int")){
            this.opType = BinaryOperationInstructionType.REM_INT;
        }
        else if(opCodeName.contains("and-int")){
            this.opType = BinaryOperationInstructionType.AND_INT;
        }
        else if(opCodeName.contains("xor-int")){
            this.opType = BinaryOperationInstructionType.XOR_INT;
        }
        else if(opCodeName.contains("or-int")){
            this.opType = BinaryOperationInstructionType.OR_INT;
        }
        else if(opCodeName.contains("shl-int")){
            this.opType = BinaryOperationInstructionType.SHL_INT;
        }
        else if(opCodeName.contains("ushr-int")){
            this.opType = BinaryOperationInstructionType.USHR_INT;
        }
        else if(opCodeName.contains("shr-int")){
            this.opType = BinaryOperationInstructionType.SHR_INT;
        }
        else if(opCodeName.contains("add-long")){
            this.opType = BinaryOperationInstructionType.ADD_LONG;
        }
        else if(opCodeName.contains("sub-long")){
            this.opType = BinaryOperationInstructionType.SUB_LONG;
        }
        else if(opCodeName.contains("mul-long")){
            this.opType = BinaryOperationInstructionType.MUL_LONG;
        }
        else if(opCodeName.contains("div-long")){
            this.opType = BinaryOperationInstructionType.DIV_LONG;
        }
        else if(opCodeName.contains("rem-long")){
            this.opType = BinaryOperationInstructionType.REM_LONG;
        }
        else if(opCodeName.contains("and-long")){
            this.opType = BinaryOperationInstructionType.AND_LONG;
        }
        else if(opCodeName.contains("xor-long")){
            this.opType = BinaryOperationInstructionType.XOR_LONG;
        }
        else if(opCodeName.contains("or-long")){
            this.opType = BinaryOperationInstructionType.OR_LONG;
        }
        else if(opCodeName.contains("shl-long")){
            this.opType = BinaryOperationInstructionType.SHL_LONG;
        }
        else if(opCodeName.contains("ushr-long")){
            this.opType = BinaryOperationInstructionType.USHR_LONG;
        }
        else if(opCodeName.contains("shr-long")){
            this.opType = BinaryOperationInstructionType.SHR_LONG;
        }
        else if(opCodeName.contains("add-float")){
            this.opType = BinaryOperationInstructionType.ADD_FLOAT;
        }
        else if(opCodeName.contains("sub-float")){
            this.opType = BinaryOperationInstructionType.SUB_FLOAT;
        }
        else if(opCodeName.contains("mul-float")){
            this.opType = BinaryOperationInstructionType.MUL_FLOAT;
        }
        else if(opCodeName.contains("div-float")){
            this.opType = BinaryOperationInstructionType.DIV_FLOAT;
        }
        else if(opCodeName.contains("rem-float")){
            this.opType = BinaryOperationInstructionType.REM_FLOAT;
        }
        else if(opCodeName.contains("add-double")){
            this.opType = BinaryOperationInstructionType.ADD_DOUBLE;
        }
        else if(opCodeName.contains("sub-double")){
            this.opType = BinaryOperationInstructionType.SUB_DOUBLE;
        }
        else if(opCodeName.contains("mul-double")){
            this.opType = BinaryOperationInstructionType.MUL_DOUBLE;
        }
        else if(opCodeName.contains("div-double")){
            this.opType = BinaryOperationInstructionType.DIV_DOUBLE;
        }
        else if(opCodeName.contains("rem-double")){
            this.opType = BinaryOperationInstructionType.REM_DOUBLE;
        }
        else {
            throw new SmaliSimulationException("Invalid instruction variant!");
        }
    }

    private boolean hasAmbiguousValues(Register src_reg1, Register src_reg2){
        // if the instructions operates on two inputs and
        // if either one is ambiguous then the result would be ambiguous
        if(this.familyType == BinaryOperationFamilyType.NORMAL ||
                this.familyType == BinaryOperationFamilyType.TWO_ADDR){

            return src_reg1.containsAmbiguousValue() || src_reg2.containsAmbiguousValue();
        }
        // if the register operates on one input and the input is ambiguous
        // then the result would be ambiguous
        else if(this.familyType == BinaryOperationFamilyType.LITERAL){
            return src_reg1.containsAmbiguousValue();
        }
        // this statement should never be reached
        throw new SmaliSimulationException();
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register rd = methodExecution.getRegister(destRegisterNumber);
        Register r1 = methodExecution.getRegister(firstSrcRegisterNumber);
        Register r2 = methodExecution.getRegister(secondSrcRegisterNumber);

        if(hasAmbiguousValues(r1, r2)){
            handleAmbiguousValueOutcome(rd,r2, methodExecution);
            return;
        }

        if(opType == BinaryOperationInstructionType.ADD_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() + literalConstant);
            }
            else {
                rd.set(r1.getIntValue() + r2.getIntValue());
            }

        }
        else if(opType == BinaryOperationInstructionType.SUB_INT){
            rd.set(r1.getIntValue() - r2.getIntValue());
        }
        else if(opType == BinaryOperationInstructionType.RSUB_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(literalConstant - r1.getIntValue());
            }
            else {
                // this should never happen
                throw new SmaliSimulationException();
            }
        }
        else if(opType == BinaryOperationInstructionType.MUL_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() * literalConstant);
            }
            else {
                rd.set(r1.getIntValue() * r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.DIV_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                if(literalConstant == 0){
//                    throwArithmeticExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArithmeticException.class);
                }
                else {
                    rd.set(r1.getIntValue() / literalConstant);
                }
            }
            else {
                if(r2.getIntValue() == 0){
//                    throwArithmeticExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArithmeticException.class);
                }
                else {
                    rd.set(r1.getIntValue() / r2.getIntValue());
                }
            }
        }
        else if(opType == BinaryOperationInstructionType.REM_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                if(literalConstant == 0){
//                    throwArithmeticExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArithmeticException.class);
                }
                else {
                    rd.set(r1.getIntValue() % literalConstant);
                }
            }
            else {
                if(r2.getIntValue() == 0){
//                    throwArithmeticExceptionOn(methodExecution);
                    throwExceptionOn(methodExecution, ArithmeticException.class);
                }
                else {
                    rd.set(r1.getIntValue() % r2.getIntValue());
                }
            }
        }
        else if(opType == BinaryOperationInstructionType.AND_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() & literalConstant);
            }
            else {
                rd.set(r1.getIntValue() & r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.OR_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() | literalConstant);
            }
            else {
                rd.set(r1.getIntValue() | r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.XOR_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() ^ literalConstant);
            }
            else {
                rd.set(r1.getIntValue() ^ r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.SHL_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() << literalConstant);
            }
            else {
                // masking with 0x1f is itself done in Java so there is no need to simulate it
                rd.set(r1.getIntValue() << r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.SHR_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() >> literalConstant);
            }
            else {
                rd.set(r1.getIntValue() >> r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.USHR_INT){
            if(familyType == BinaryOperationFamilyType.LITERAL){
                rd.set(r1.getIntValue() >>> literalConstant);
            }
            else {
                rd.set(r1.getIntValue() >>> r2.getIntValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.ADD_LONG){
            rd.set(r1.getLongValue() + r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.SUB_LONG){
            rd.set(r1.getLongValue() - r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.MUL_LONG){
            rd.set(r1.getLongValue() * r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.DIV_LONG){
            if(r2.getLongValue() == 0){
//                throwArithmeticExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, ArithmeticException.class);
            }
            else {
                rd.set(r1.getLongValue() / r2.getLongValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.REM_LONG){
            if(r2.getLongValue() == 0){
//                throwArithmeticExceptionOn(methodExecution);
                throwExceptionOn(methodExecution, ArithmeticException.class);
            }
            else {
                rd.set(r1.getLongValue() % r2.getLongValue());
            }
        }
        else if(opType == BinaryOperationInstructionType.AND_LONG){
            rd.set(r1.getLongValue() & r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.OR_LONG){
            rd.set(r1.getLongValue() | r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.XOR_LONG){
            rd.set(r1.getLongValue() ^ r2.getLongValue());
        }
        else if(opType == BinaryOperationInstructionType.SHL_LONG){
            rd.set(r1.getLongValue() << r2.getIntValue());
        }
        else if(opType == BinaryOperationInstructionType.SHR_LONG){
            rd.set(r1.getLongValue() >> r2.getIntValue());
        }
        else if(opType == BinaryOperationInstructionType.USHR_LONG){
            rd.set(r1.getLongValue() >>> r2.getIntValue());
        }
        else if(opType == BinaryOperationInstructionType.ADD_FLOAT){
            rd.set(r1.getFloatValue() + r2.getFloatValue());
        }
        else if(opType == BinaryOperationInstructionType.SUB_FLOAT){
            rd.set(r1.getFloatValue() - r2.getFloatValue());
        }
        else if(opType == BinaryOperationInstructionType.MUL_FLOAT){
            rd.set(r1.getFloatValue() * r2.getFloatValue());
        }
        else if(opType == BinaryOperationInstructionType.DIV_FLOAT){
            rd.set(r1.getFloatValue() / r2.getFloatValue());
        }
        else if(opType == BinaryOperationInstructionType.REM_FLOAT){
            rd.set(r1.getFloatValue() % r2.getFloatValue());
        }
        else if(opType == BinaryOperationInstructionType.ADD_DOUBLE){
            rd.set(r1.getDoubleValue() + r2.getDoubleValue());
        }
        else if(opType == BinaryOperationInstructionType.SUB_DOUBLE){
            rd.set(r1.getDoubleValue() - r2.getDoubleValue());
        }
        else if(opType == BinaryOperationInstructionType.MUL_DOUBLE){
            rd.set(r1.getDoubleValue() * r2.getDoubleValue());
        }
        else if(opType == BinaryOperationInstructionType.DIV_DOUBLE){
            rd.set(r1.getDoubleValue() / r2.getDoubleValue());
        }
        else if(opType == BinaryOperationInstructionType.REM_DOUBLE){
            rd.set(r1.getDoubleValue() % r2.getDoubleValue());
        }
        else{
            throw new SmaliSimulationException("Invalid instruction type!" + methodExecution.getSmaliMethod().getClassMethodSignature());
        }
    }

    private void handleAmbiguousValueOutcome(Register dest_reg,
                                             Register r2,
                                             MethodExecution methodExecution){


        if(this.opType == BinaryOperationInstructionType.DIV_INT
                || this.opType == BinaryOperationInstructionType.REM_INT){
            handleAmbiguousOutComeOfDivOrRemInt(dest_reg, r2, methodExecution);
        }
        else if(this.opType == BinaryOperationInstructionType.DIV_LONG
           || this.opType == BinaryOperationInstructionType.REM_LONG){
            handleAmbiguousOutComeOfDivOrRemLong(dest_reg, r2, methodExecution);
        }
        else if(this.opType == BinaryOperationInstructionType.REM_FLOAT
                || this.opType == BinaryOperationInstructionType.DIV_FLOAT){
            handleAmbiguousOutComeOfDivOrRemFloat(dest_reg, r2, methodExecution);
        }
        else if(this.opType == BinaryOperationInstructionType.DIV_DOUBLE
                || this.opType == BinaryOperationInstructionType.REM_DOUBLE){
            handleAmbiguousOutComeOfDivOrRemDouble(dest_reg, r2, methodExecution);
        }
        else {
            String opTypeStr = this.opType.toString();
            if(opTypeStr.toLowerCase().endsWith("int")){
                dest_reg.set(new AmbiguousValue("I"));
            }
            else if(opTypeStr.toLowerCase().endsWith("long")){
                dest_reg.set(new AmbiguousValue("J"));
            }
            else if(opTypeStr.toLowerCase().endsWith("float")){
                dest_reg.set(new AmbiguousValue("F"));
            }
            else if(opTypeStr.toLowerCase().endsWith("double")){
                dest_reg.set(new AmbiguousValue("D"));
            }
            else {
                throw new SmaliSimulationException();
            }


        }
    }

    private void handleAmbiguousOutComeOfDivOrRemInt(Register dest_reg,
                                                     Register r2,
                                                     MethodExecution methodExecution){
        boolean arithmeticBranchCouldBeThrown = false;
        // if the number used for div/rem is zero the outcome is not ambiguous
        // and a normal ArithmeticException should be thrown
        if((this.familyType == BinaryOperationFamilyType.LITERAL && literalConstant == 0)
                || (this.familyType != BinaryOperationFamilyType.LITERAL && r2.containsNumericData() && r2.getIntValue() == 0)){
//            throwArithmeticExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ArithmeticException.class);
            return;
        }
        // if the number used for div/rem is ambiguous then an ArithmeticException can be thrown
        else if(this.familyType != BinaryOperationFamilyType.LITERAL && r2.containsAmbiguousValue()){
            arithmeticBranchCouldBeThrown = true;
        }
        finishHandleAmbiguousValueOutcome(arithmeticBranchCouldBeThrown, methodExecution, dest_reg, "I");

    }

    private void handleAmbiguousOutComeOfDivOrRemLong(Register dest_reg,
                                                     Register r2,
                                                     MethodExecution methodExecution){
        boolean arithmeticBranchCouldBeThrown = false;
        // if the number used for div/rem is zero the outcome is not ambiguous
        // and a normal ArithmeticException should be thrown
        if(r2.containsNumericData() && r2.getLongValue() == 0L){
//            throwArithmeticExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ArithmeticException.class);
            return;
        }
        // if the second register is not ambiguous then an ArithmeticException will never be thrown
        else if(r2.containsAmbiguousValue()){
            arithmeticBranchCouldBeThrown = true;
        }
        finishHandleAmbiguousValueOutcome(arithmeticBranchCouldBeThrown, methodExecution, dest_reg, "L");
    }

    private void handleAmbiguousOutComeOfDivOrRemFloat(Register dest_reg,
                                                       Register r2,
                                                       MethodExecution methodExecution){
        boolean arithmeticBranchCouldBeThrown = false;
        // if the number used for div/rem is zero the outcome is not ambiguous
        // and a normal ArithmeticException should be thrown
        if(r2.containsNumericData() && r2.getFloatValue() == 0F){
//            throwArithmeticExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ArithmeticException.class);
            return;
        }
        // if the second register is not ambiguous then an ArithmeticException will never be thrown
        else if(r2.containsAmbiguousValue()){
            arithmeticBranchCouldBeThrown = true;
        }
        finishHandleAmbiguousValueOutcome(arithmeticBranchCouldBeThrown, methodExecution, dest_reg, "F");
    }

    private void handleAmbiguousOutComeOfDivOrRemDouble(Register dest_reg,
                                                       Register r2,
                                                       MethodExecution methodExecution){
        boolean arithmeticBranchCouldBeThrown = false;
        // if the number used for div/rem is zero the outcome is not ambiguous
        // and a normal ArithmeticException should be thrown
        if(r2.containsNumericData() && r2.getDoubleValue() == 0D){
//            throwArithmeticExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ArithmeticException.class);
            return;
        }
        // if the second register is not ambiguous then an ArithmeticException will never be thrown
        else if(r2.containsAmbiguousValue()){
            arithmeticBranchCouldBeThrown = true;
        }
        finishHandleAmbiguousValueOutcome(arithmeticBranchCouldBeThrown, methodExecution, dest_reg, "D");
    }


    private void finishHandleAmbiguousValueOutcome(boolean arithmeticBranchCouldBeThrown, MethodExecution methodExecution,
                     Register dest_reg, String ambiguousValueType){
        int r = SimulationUtils.getRandomNumberInRange(0, 1);
        if(arithmeticBranchCouldBeThrown && SimSmaliConfig.canThrowArithmeticExceptionOnAmbiguity && r==0){
//            throwArithmeticExceptionOn(methodExecution);
            throwExceptionOn(methodExecution, ArithmeticException.class);
        }
        else {
            dest_reg.set(new AmbiguousValue(ambiguousValueType));
        }
    }

    @Override
    public String toString() {
        // binop/lit16 or binop/lit8 vA, vB, #+CCCC
        if(this.familyType == BinaryOperationFamilyType.LITERAL){
            return this.opCode + " v" + destRegisterNumber
                    + " v" + firstSrcRegisterNumber + " "
                    + literalConstant + " &" + instructionPositionNumber;
        }
        // binop vAA, vBB, vCC
        else if(this.familyType == BinaryOperationFamilyType.NORMAL){
            return this.opCode + " v" + destRegisterNumber
                    + " v" + firstSrcRegisterNumber + " v"
                    + secondSrcRegisterNumber + " &" + instructionPositionNumber;
        }
        //binop/2addr vA, vB
        else if(this.familyType == BinaryOperationFamilyType.TWO_ADDR){
            return this.opCode + " v" + firstSrcRegisterNumber
                    + " v" + secondSrcRegisterNumber + " &" + instructionPositionNumber;
        }
        else {
            throw new SmaliSimulationException();
        }
    }


    public enum BinaryOperationInstructionType{
        ADD_INT,
        SUB_INT,
        RSUB_INT,
        MUL_INT,
        DIV_INT,
        REM_INT,
        AND_INT,
        OR_INT,
        XOR_INT,
        SHL_INT,
        SHR_INT,
        USHR_INT,
        ADD_LONG,
        SUB_LONG,
        MUL_LONG,
        DIV_LONG,
        REM_LONG,
        AND_LONG,
        OR_LONG,
        XOR_LONG,
        SHL_LONG,
        SHR_LONG,
        USHR_LONG,
        ADD_FLOAT,
        SUB_FLOAT,
        MUL_FLOAT,
        DIV_FLOAT,
        REM_FLOAT,
        ADD_DOUBLE,
        SUB_DOUBLE,
        MUL_DOUBLE,
        DIV_DOUBLE,
        REM_DOUBLE,
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        Register rd = methodExecution.getRegister(destRegisterNumber);
        Register r1 = methodExecution.getRegister(firstSrcRegisterNumber);
        Register r2 = methodExecution.getRegister(secondSrcRegisterNumber);

        JSONObject jo = new JSONObject();

        if(familyType.equals(BinaryOperationFamilyType.LITERAL)){
            jo.put("v" + destRegisterNumber, getContentInfo(rd));
            jo.put("v" + firstSrcRegisterNumber, getContentInfo(r1));

        }
        else if(familyType.equals(BinaryOperationFamilyType.TWO_ADDR)){
            jo.put("v" + firstSrcRegisterNumber, getContentInfo(r1));
            jo.put("v" + secondSrcRegisterNumber, getContentInfo(r2));
        }
        else {
            jo.put("v" + destRegisterNumber, getContentInfo(rd));
            jo.put("v" + firstSrcRegisterNumber, getContentInfo(r1));
            jo.put("v" + secondSrcRegisterNumber, getContentInfo(r2));
        }

        return jo.toString();
    }
}
