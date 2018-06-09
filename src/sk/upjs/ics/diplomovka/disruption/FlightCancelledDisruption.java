package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightCancelledDisruption implements Disruption {
    private int flight;
    private FlightStorage flightStorage;

    public FlightCancelledDisruption(int flight, FlightStorage flightStorage) {
        this.flight = flight;
        this.flightStorage = flightStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeFlight(flight);
    }

    @Override
    public void disruptStorage() { }
}
