package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class FlightDelayedDisruption implements Disruption {

    private int id;
    private int delay;
    private Flight flight;

    public FlightDelayedDisruption(int delay, int flight, FlightStorage flightStorage, int id) {
        this.delay = delay;
        this.flight = flightStorage.getFlight(flight);
        this.id = id;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        return;
    }

    @Override
    public void disruptStorage() {
        flight.setStart(flight.getStart() + delay)
                .setDelay(delay)
                .setEnd(flight.getEnd() + delay);
    }

    @Override
    public void cancelDisruptionOnAssignment(Chromosome chromosome) {
        flight.setStart(flight.getStart() - delay)
                .setDelay(delay)
                .setEnd(flight.getEnd() - delay);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Flight " + flight.getCode() + " delayed by " + delay + " minutes.";
    }
}
