package usask.cyberlab.smalisimulator.simsmali.representations;

import org.jf.dexlib2.builder.BuilderTryBlock;

public class SmaliTryBlock {
    private final int startingFromInstruction;
    private final int endingOnInstruction;
    private final String exceptionType;
    private final int jumpLocation;

    public SmaliTryBlock(BuilderTryBlock tb) {
        this.startingFromInstruction = tb.start.getLocation().getIndex();
        this.endingOnInstruction = tb.end.getLocation().getIndex();
        this.exceptionType = tb.exceptionHandler.getExceptionType();
        this.jumpLocation = tb.exceptionHandler.getHandler().getLocation().getIndex();
    }

    public int getStartingFromInstruction() {
        return startingFromInstruction;
    }

    public int getEndingOnInstruction() {
        return endingOnInstruction;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public int getJumpLocation() {
        return jumpLocation;
    }
}
