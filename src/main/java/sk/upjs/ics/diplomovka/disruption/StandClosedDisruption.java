package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandClosedDisruption implements Disruption {
    private int stand;
    private StandsStorage standsStorage;
    private int id;

    public StandClosedDisruption(int stand, StandsStorage standsStorage, int id) {
        this.stand = stand;
        this.standsStorage = standsStorage;
        this.id = id;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeStand(standsStorage.getNumberById(stand));
    }

    @Override
    public void disruptStorage() {
        standsStorage.closeStand(stand);
    }

    @Override
    public void undisruptStorage() {
        standsStorage.openStand(standsStorage.getStandById(stand));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Stand " + stand + " closed.";
    }
}
