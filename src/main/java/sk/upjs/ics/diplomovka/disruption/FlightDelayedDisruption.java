package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.FlightDelayedDisruptionDataModel;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;

public class FlightDelayedDisruption implements Disruption {

    private int id;
    private int delay;
    private Flight flight;
    private FlightStorage flightStorage;

    public FlightDelayedDisruption(int delay, int flight, FlightStorage flightStorage, int id) {
        this.delay = delay;
        this.flight = flightStorage.getFlight(flight);
        this.flightStorage = flightStorage;
        this.id = id;
    }

    public FlightDelayedDisruption(FlightDelayedDisruptionDataModel disruption, FlightStorage flightStorage) {
        this(disruption.getDelay(), disruption.getFlightId(), flightStorage, disruption.getId());
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
        flightStorage.flightTimeChanged();
    }

    @Override
    public void undisruptStorage() {
        flight.setStart(flight.getStart() - delay)
                .setDelay(0)
                .setEnd(flight.getEnd() - delay);
        flightStorage.flightTimeChanged();
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
