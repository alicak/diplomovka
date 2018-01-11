package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class FlightCancelledDisruption implements Disruption {
    private int flight;
    private List<Flight> flights;

    public FlightCancelledDisruption(int flight, List<Flight> flights) {
        this.flight = flight;
        this.flights = flights;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        flights.remove(flight);
        chromosome.removeFlight(flight);
    }
}
