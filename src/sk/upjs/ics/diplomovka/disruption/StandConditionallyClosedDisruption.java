package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.closures.ClosureCondition;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandConditionallyClosedDisruption implements Disruption {

    private StandsStorage standsStorage;
    private ConditionalStandClosure closure;

    public StandConditionallyClosedDisruption(int standId, int start, int end, ClosureCondition condition, StandsStorage standsStorage) {
        this.standsStorage = standsStorage;
        this.closure = new ConditionalStandClosure(standId, start, end, condition);
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.applyConditionalStandClosure(closure, standsStorage.getNumberById(closure.getStandId()));
    }

    @Override
    public void disruptStorage() {
        standsStorage.addConditionalClosure(closure);
    }
}
