package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandAddedDisruption implements Disruption {
    private AircraftStand stand;
    private StandsStorage standsStorage;

    public StandAddedDisruption(AircraftStand stand, StandsStorage standsStorage) {
        this.stand = stand;
        this.standsStorage = standsStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addGate();
    }

    @Override
    public void disruptStorage() {
        standsStorage.addStand(stand, standsStorage.getNoOfStandsInUse());
    }

    @Override
    public String toString() {
        return "Stand " + stand + " opened.";
    }
}
