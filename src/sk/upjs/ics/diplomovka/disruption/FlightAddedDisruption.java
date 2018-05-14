package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightAddedDisruption implements Disruption {
    private Flight flight;
    private FlightStorage flightStorage;

    public FlightAddedDisruption(Flight flight, FlightStorage flightStorage) {
        this.flight = flight;
        this.flightStorage = flightStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addFlight();
    }

    @Override
    public void disruptStorage() {
        flightStorage.addFlight(flight);
    }
}
