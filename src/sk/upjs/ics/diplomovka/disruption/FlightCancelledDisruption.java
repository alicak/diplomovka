package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightCancelledDisruption implements Disruption {
    private int flight;
    private FlightStorage flightStorage;
    private int id;

    public FlightCancelledDisruption(int flight, FlightStorage flightStorage, int id) {
        this.flight = flight;
        this.flightStorage = flightStorage;
        this.id = id;

    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeFlight(flight);
    }

    @Override
    public void disruptStorage() { }

    @Override
    public void cancelDisruptionOnAssignment(Chromosome chromosome) {
        chromosome.addFlight(flight);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Flight " + flightStorage.getFlight(flight).getCode() + " cancelled.";
    }
}
