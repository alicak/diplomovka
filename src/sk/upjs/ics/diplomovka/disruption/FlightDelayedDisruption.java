package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;
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
        int start = flightStorage.getFlightById(flight).getStart();
        flightStorage.getFlightById(flight).setStart(start + delay).setDelay(delay);
        int end = flightStorage.getFlightById(flight).getEnd();
        flightStorage.getFlightById(flight).setEnd(end + delay);
    }
}
