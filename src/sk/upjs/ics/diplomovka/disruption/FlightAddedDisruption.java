package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightAddedDisruption implements Disruption {
    private Flight flight;
    private int id;

    public FlightAddedDisruption(Flight flight, int id) {
        this.flight = flight;
        this.id = id;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addFlight(flight.getId());
    }

    @Override
    public void disruptStorage() {
    }

    @Override
    public void undisruptStorage() {
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
