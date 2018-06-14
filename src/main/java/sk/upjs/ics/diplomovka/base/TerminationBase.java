package sk.upjs.ics.diplomovka.base;

public abstract class TerminationBase {
    protected int noOfIterations = 0;

    public void onStepPerformed() {
        noOfIterations++;
    }

    public abstract boolean isTerminated();

    public int getNoOfIterations() {
        return noOfIterations;
    }
}
