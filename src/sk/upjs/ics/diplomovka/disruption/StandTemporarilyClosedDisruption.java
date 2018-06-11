package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

public class StandTemporarilyClosedDisruption implements Disruption {

    private StandsStorage standsStorage;
    private StandClosure closure;

    public StandTemporarilyClosedDisruption(int standId, int start, int end, StandsStorage standsStorage) {
        this.standsStorage = standsStorage;
        this.closure = new StandClosure(standId, start, end);
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.applyStandClosure(closure, standsStorage.getNumberById(closure.getStandId()));
    }

    @Override
    public void disruptStorage() {
        standsStorage.addClosure(closure);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("Stand ")
                .append(closure.getStandId())
                .append(" closed from ")
                .append(Utils.minutesToTime(closure.getStart()))
                .append(" to ")
                .append(Utils.minutesToTime(closure.getEnd()))
                .append(".");
        return sb.toString();
    }
}
