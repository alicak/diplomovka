package sk.upjs.ics.diplomovka.operators.termination;

import sk.upjs.ics.diplomovka.base.TerminationBase;

public class IterationsTermination extends TerminationBase {
    private int maxNoOfIterations;

    public IterationsTermination(int maxNoOfIterations) {
        this.maxNoOfIterations = maxNoOfIterations;
    }

    @Override
    public boolean isTerminated() {
        return noOfIterations < maxNoOfIterations;
    }
}
