package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;
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
        standsStorage.addStand(stand);
        chromosome.addGate();
    }
}
