package sk.upjs.ics.diplomovka.termination;

import sk.upjs.ics.diplomovka.base.TerminationBase;

public class TimeTermination extends TerminationBase {

    private int secondsToRun;
    private long start;

    public TimeTermination(int secondsToRun) {
        this.start = System.currentTimeMillis() / 1000;
        this.secondsToRun = secondsToRun;
    }

    @Override
    public boolean isTerminated() {
        return System.currentTimeMillis() / 1000 - start < secondsToRun;
    }


}
