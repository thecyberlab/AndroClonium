package usask.cyberlab.smalisimulator.simsmali.exceptions;

public class SmaliFileNotFoundException extends SmaliFileParseException{

    public SmaliFileNotFoundException(){
        super();
    }

    public SmaliFileNotFoundException(String message){
        super(message);
    }

    public SmaliFileNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public SmaliFileNotFoundException(Throwable cause){
        super(cause);
    }

}
