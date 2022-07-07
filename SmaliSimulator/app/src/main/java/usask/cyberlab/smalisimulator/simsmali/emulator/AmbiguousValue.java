package usask.cyberlab.smalisimulator.simsmali.emulator;


/**
 * This class represents an unknown value during execution. Each ambiguous value
 * has a type and a objectID which is constant during lifetime of an Ambiguous value
 */
public class AmbiguousValue {
    private String type;
    private final int objectID;

    public AmbiguousValue(String type, int objectID) {
        this.type = type;
        this.objectID = objectID;
    }

    public AmbiguousValue(String type) {
        this.type = type;
        this.objectID = System.identityHashCode(new Object());
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AmbiguousValue{ type='" + type + '\'' + '}';
    }

    public int getObjectID(){
        return objectID;
    }

    public void setType(String newType){
        this.type = newType;
    }

}
