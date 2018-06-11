package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightDelayedDisruption implements Disruption {
    private int delay;
    private int flight;
    private FlightStorage flightStorage;

    public FlightDelayedDisruption(int delay, int flight, FlightStorage flightStorage) {
        this.delay = delay;
        this.flight = flight;
        this.flightStorage = flightStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        return;
    }

    @Override
    public void disruptStorage() {
        int start = flightStorage.getFlight(flight).getStart();
        flightStorage.getFlight(flight).setStart(start + delay).setDelay(delay);
        int end = flightStorage.getFlight(flight).getEnd();
        flightStorage.getFlight(flight).setEnd(end + delay);
    }

    @Override
    public String toString() {
        return "Flight " + flightStorage.getFlight(flight).getCode() + " delayed by " + delay + " minutes.";
    }
}
