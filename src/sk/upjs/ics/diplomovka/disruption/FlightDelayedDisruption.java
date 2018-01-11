package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

import java.util.List;

public class FlightDelayedDisruption implements Disruption {
    private int delay;
    private int flight;
    private List<Flight> flights;

    public FlightDelayedDisruption(int delay, int flight, FlightStorage flightStorage) {
        this.delay = delay;
        this.flight = flight;
        this.flights = flightStorage.getFlights();
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        int start = flights.get(flight).getStart();
        flights.get(flight).setStart(start + delay);
        int end = flights.get(flight).getEnd();
        flights.get(flight).setEnd(end + delay);
    }
}
