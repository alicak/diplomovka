package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public abstract class FeasibilityCheckerBase {
    protected StandsStorage standsStorage;
    protected FlightStorage flightStorage;

    public FeasibilityCheckerBase(GeneralStorage storage) {
        this.standsStorage = storage.getStandsStorage();
        this.flightStorage = storage.getFlightStorage();
    }

    public abstract boolean checkChromosomeFeasibility(Chromosome chromosome);

    public abstract boolean checkFlightFeasibility(int flightValue, int gate);

}
