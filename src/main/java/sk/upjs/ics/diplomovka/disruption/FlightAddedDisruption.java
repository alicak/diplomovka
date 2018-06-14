package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightAddedDisruption implements Disruption {
    private Flight flight;
    private int id;
    private FlightStorage flightStorage;

    public FlightAddedDisruption(Flight flight, FlightStorage flightStorage, int id) {
        this.flight = flight;
        this.id = id;
        this.flightStorage = flightStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addFlight(flight.getId());
    }

    @Override
    public void disruptStorage() {
        if (flightStorage.getFlight(flight.getId()) == null)
            flightStorage.addFlight(flight);
    }

    @Override
    public void undisruptStorage() {
        flightStorage.removeFlight(flight);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Flight " + flight.getCode() + " added.";
    }
}
