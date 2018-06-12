package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandAddedDisruption implements Disruption {
    private AircraftStand stand;
    private StandsStorage standsStorage;
    private int id;

    public StandAddedDisruption(AircraftStand stand, StandsStorage standsStorage, int id) {
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
        standsStorage.addStand(stand);
    }

    @Override
    public void undisruptStorage() {
        standsStorage.removeStand(stand.getId());
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
