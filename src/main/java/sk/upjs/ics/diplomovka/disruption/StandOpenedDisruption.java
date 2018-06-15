package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.Stand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandOpenedDisruption implements Disruption {
    private Stand stand;
    private StandsStorage standsStorage;
    private int id;

    public StandOpenedDisruption(Stand stand, StandsStorage standsStorage, int id) {
        this.stand = stand;
        this.standsStorage = standsStorage;
        this.id = id;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addGate();
    }

    @Override
    public void disruptStorage() {
        standsStorage.openStand(stand);
    }

    @Override
    public void undisruptStorage() {
        standsStorage.closeStand(stand.getId());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Stand " + stand + " opened.";
    }
}
