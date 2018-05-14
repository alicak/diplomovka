package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandClosedDisruption implements Disruption {
    private int stand;
    private StandsStorage standsStorage;

    public StandClosedDisruption(int stand, StandsStorage standsStorage) {
        this.stand = stand;
        this.standsStorage = standsStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeGate(standsStorage.getNumberById(stand));
    }

    @Override
    public void disruptStorage() {
        standsStorage.removeStand(stand);
    }
}
