package usask.cyberlab.smalisimulator.simsmali.emulator;

public class SimulationResult {
    private final MethodExecutionResult executionResult;
    private final String executionTrace;

    public SimulationResult(MethodExecutionResult executionResult, String executionTrace) {
        this.executionResult = executionResult;
        this.executionTrace = executionTrace;
    }

    public MethodExecutionResult getExecutionResult() {
        return executionResult;
    }

    public String getExecutionTrace() {
        return executionTrace;
    }
}
