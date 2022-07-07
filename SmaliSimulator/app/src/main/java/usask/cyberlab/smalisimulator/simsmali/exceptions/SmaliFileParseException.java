package usask.cyberlab.smalisimulator.simsmali.exceptions;

public class SmaliFileParseException extends RuntimeException {

    public SmaliFileParseException(){
        super();
    }

    public SmaliFileParseException(String message){
        super(message);
    }

    public SmaliFileParseException(String message, Throwable cause){
        super(message, cause);
    }

    public SmaliFileParseException(Throwable cause){
        super(cause);
    }
}
