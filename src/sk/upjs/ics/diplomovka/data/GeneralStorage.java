package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class GeneralStorage {

    private FlightStorage flightStorage;
    private StandsStorage standsStorage;

    public GeneralStorage(FlightStorage flightStorage, StandsStorage standsStorage) {
        this.flightStorage = flightStorage;
        this.standsStorage = standsStorage;
    }

    public FlightStorage getFlightStorage() {
        return flightStorage;
    }

    public StandsStorage getStandsStorage() {
        return standsStorage;
    }
}
