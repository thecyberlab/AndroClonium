package usask.cyberlab.smalisimulator.simsmali.exceptions;

public class SmaliSimulationException extends RuntimeException{

    public SmaliSimulationException(){
        super();
    }

    public SmaliSimulationException(String message){
        super(message);
    }

    public SmaliSimulationException(String message, Throwable cause){
        super(message, cause);
    }

    public SmaliSimulationException(Throwable cause){
        super(cause);
    }
}
