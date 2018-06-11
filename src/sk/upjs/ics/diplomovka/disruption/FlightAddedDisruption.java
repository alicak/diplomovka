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
        chromosome.addFlight(flight.getId());
    }

    @Override
    public void disruptStorage() { }

    @Override
    public String toString() {
        return "Flight " + flight.getCode() + " added.";
    }
}
