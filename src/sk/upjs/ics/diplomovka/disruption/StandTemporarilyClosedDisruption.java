package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandTemporarilyClosedDisruption implements Disruption {

    private StandsStorage standsStorage;
    private StandClosure closure;

    public StandTemporarilyClosedDisruption(int standId, int start, int end, StandsStorage standsStorage) {
        this.standsStorage = standsStorage;
        this.closure = new StandClosure(standId, start, end);
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        AbsolutePositionChromosome absCh = (AbsolutePositionChromosome) (chromosome); // TODO: somehow remove casting
        absCh.applyStandClosure(closure, standsStorage.getNumberById(closure.getStandId()));
    }

    @Override
    public void disruptStorage() {
        standsStorage.addClosure(closure);
    }
}
