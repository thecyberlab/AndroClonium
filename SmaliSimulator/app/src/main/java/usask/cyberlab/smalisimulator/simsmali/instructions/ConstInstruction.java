package usask.cyberlab.smalisimulator.simsmali.instructions;

import org.jf.dexlib2.builder.instruction.*;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.json.JSONException;


import java.net.URLEncoder;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

public class ConstInstruction extends SmaliInstruction {
    private final ConstInstructionType type;
    private int intValue;
    private long longValue;
    private String dereferencedValue;
    private final int destRegisterNumber;

    public enum ConstInstructionType {
        NORMAL,
        WIDE,
        STRING,
        CLASS,
    }

    public ConstInstruction(Instruction instructionDef, int instructionPositionNumber) {
        super(instructionDef, instructionPositionNumber);
        String opCodeName = instructionDef.getOpcode().name;
        switch (opCodeName) {
            case "const/4": {
                BuilderInstruction11n bi = (BuilderInstruction11n) instructionDef;
                type = ConstInstructionType.NORMAL;
                this.intValue = bi.getNarrowLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const/high16": {
                BuilderInstruction21ih bi = (BuilderInstruction21ih) instructionDef;
                type = ConstInstructionType.NORMAL;
                this.intValue = bi.getNarrowLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const/16": {
                BuilderInstruction21s bi = (BuilderInstruction21s) instructionDef;
                type = ConstInstructionType.NORMAL;
                this.intValue = bi.getNarrowLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-wide/16": {
                BuilderInstruction21s bi = (BuilderInstruction21s) instructionDef;
                type = ConstInstructionType.WIDE;
                this.longValue = bi.getWideLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-wide/32": {
                BuilderInstruction31i bi = (BuilderInstruction31i) instructionDef;
                type = ConstInstructionType.WIDE;
                this.longValue = bi.getWideLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const": {
                BuilderInstruction31i bi = (BuilderInstruction31i) instructionDef;
                type = ConstInstructionType.NORMAL;
                this.intValue = bi.getNarrowLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-wide": {
                BuilderInstruction51l bi = (BuilderInstruction51l) instructionDef;
                type = ConstInstructionType.WIDE;
                this.longValue = bi.getWideLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-wide/high16": {
                BuilderInstruction21lh bi = (BuilderInstruction21lh) instructionDef;
                type = ConstInstructionType.WIDE;
                this.longValue = bi.getWideLiteral();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-string/jumbo": {
                BuilderInstruction31c bi = (BuilderInstruction31c) instructionDef;
                type = ConstInstructionType.STRING;
                this.dereferencedValue = bi.getReference().toString();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-string": {
                BuilderInstruction21c bi = (BuilderInstruction21c) instructionDef;
                type = ConstInstructionType.STRING;
                this.dereferencedValue = bi.getReference().toString();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            case "const-class": {
                BuilderInstruction21c bi = (BuilderInstruction21c) instructionDef;
                type = ConstInstructionType.CLASS;
                this.dereferencedValue = bi.getReference().toString();
                this.destRegisterNumber = bi.getRegisterA();
                break;
            }
            default:
                throw new SmaliSimulationException();
        }
    }

    @Override
    protected void execute(MethodExecution methodExecution) {
        Register reg = methodExecution.getRegister(destRegisterNumber);
        if(type == ConstInstructionType.NORMAL) {
            reg.set(intValue);
        }
        else if(type == ConstInstructionType.WIDE){
            reg.set(longValue);
        }
        else if(type == ConstInstructionType.STRING){
            ReflectedClazz rc = (ReflectedClazz) methodExecution.getClazzLoader().getClazz("Ljava/lang/String;");
            AbstractObjekt so = new Objekt(rc, dereferencedValue);
            reg.set(so);
        }
        else if(type == ConstInstructionType.CLASS){
            Clazz clazz;
            try {
               clazz = methodExecution.getClazzLoader().getClazz(dereferencedValue);
            }
            catch (RuntimeException e){
                if(SimSmaliConfig.onClassLoadErrorUseAmbiguousValue){
                    reg.set(new AmbiguousValue("Ljava/lang/Class;"));
                    return;
                }
                else {
                    throw new SmaliSimulationException(e);
                }
            }
            reg.set(clazz.getClassObjekt());
        }
    }

    @Override
    public String toString() {
        if(this.type == ConstInstructionType.NORMAL){
            // like const vAA, #+BBBBBBBB
            return this.opCode + " v" +
                    destRegisterNumber + " " + intValue
                    + " &" + instructionPositionNumber;
        }
        else if(this.type == ConstInstructionType.WIDE){
            // like const-wide/32 vAA, #+BBBBBBBB
            return this.opCode + " v" + destRegisterNumber
                    + " " + longValue + " &" + instructionPositionNumber;
        }
        // same for const-class and const-string
        else{
            // const-string vAA, string@BBBB
            // const-string/jumbo vAA, string@BBBBBBBB
            if(this.type == ConstInstructionType.STRING){

                String escaped = URLEncoder.encode(dereferencedValue);
                return this.opCode + " v" + destRegisterNumber
                        + " \"" + escaped + "\"" + " &" + instructionPositionNumber;
            }
            // const-class vAA, type@BBBB
            else {
                return this.opCode + " v" + destRegisterNumber
                        + " " + dereferencedValue + " &" + instructionPositionNumber;
            }
        }
    }

    @Override
    public String getRegisterContents(MethodExecution methodExecution) throws JSONException {
        return "";
    }

    public ConstInstructionType getConstInstructionType(){
        return type;
    }

    public String getDereferencedValue() {
        return dereferencedValue;
    }

    public int getDestRegisterNumber() {
        return destRegisterNumber;
    }
}
