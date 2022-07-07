package usask.cyberlab.smalisimulator.simsmali.exceptions;

import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;

public class AmbiguousExceptionThrownException extends Exception{

    private final AmbiguousValue ambiguousValue;

    public AmbiguousExceptionThrownException(AmbiguousValue ambiguousValue) {
        this.ambiguousValue = ambiguousValue;
    }

    public AmbiguousValue getAmbiguousValue() {
        return ambiguousValue;
    }
}
