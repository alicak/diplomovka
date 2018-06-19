package sk.upjs.ics.diplomovka.base;

public abstract class TerminationBase {
    protected int noOfIterations = 0;

    // what happens after one step in evolution
    public void onStepPerformed() {
        noOfIterations++;
    }

    public abstract boolean isTerminated();

    public int getNoOfIterations() {
        return noOfIterations;
    }
}
