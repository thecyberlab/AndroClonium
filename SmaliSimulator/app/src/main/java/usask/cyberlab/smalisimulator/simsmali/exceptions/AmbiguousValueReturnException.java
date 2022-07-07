package usask.cyberlab.smalisimulator.simsmali.exceptions;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;

/**
 * This exception is used in MethodInterceptors in case execution of a instrumented
 * smali method has returned an Ambiguous value which we can't be normally returned
 * since it will break the type system, so we have to return it using an exception.
 */
public class AmbiguousValueReturnException extends RuntimeException{
    private final AmbiguousValue ambiguousValue;

    public AmbiguousValueReturnException(AmbiguousValue ambiguousValue){
        this.ambiguousValue = ambiguousValue;
    }

    public AmbiguousValue getAmbiguousValue() {
        return ambiguousValue;
    }

}
