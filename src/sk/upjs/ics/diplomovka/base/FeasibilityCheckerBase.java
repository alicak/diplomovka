package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public abstract class FeasibilityCheckerBase {
    protected StandsStorage standsStorage;
    protected FlightStorage flightStorage;

    public FeasibilityCheckerBase(StandsStorage standsStorage, FlightStorage flightStorage) {
        this.standsStorage = standsStorage;
        this.flightStorage = flightStorage;
    }

    public abstract boolean checkChromosomeFeasibility(Chromosome chromosome);

    public abstract boolean checkFlightFeasibility(int flightValue, int gate);

}
