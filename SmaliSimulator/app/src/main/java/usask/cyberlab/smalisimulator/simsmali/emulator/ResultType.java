package usask.cyberlab.smalisimulator.simsmali.emulator;

// this enum captures the evaluation value of a method
// a method can return void, primitives, Objects or lead to an exception
public enum ResultType {
    VOID,
    INTEGER,
    FLOAT,
    DOUBLE,
    LONG,
    BOOLEAN,
    SHORT,
    BYTE,
    CHAR,
    OBJECT,
    EXCEPTION,
    AMBIGUOUS_VALUE
}
