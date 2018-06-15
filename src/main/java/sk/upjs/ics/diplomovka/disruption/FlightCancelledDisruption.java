package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.FlightCancelledDisruptionDataModel;

public class FlightCancelledDisruption implements Disruption {
    private int flight;
    private FlightStorage flightStorage;
    private int id;

    public FlightCancelledDisruption(int flight, FlightStorage flightStorage, int id) {
        this.flight = flight;
        this.flightStorage = flightStorage;
        this.id = id;
    }

    public FlightCancelledDisruption(FlightCancelledDisruptionDataModel disruption, FlightStorage flightStorage) {
        this(disruption.getFlightId(), flightStorage, disruption.getId());
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeFlight(flight);
    }

    @Override
    public void disruptStorage() { }

    @Override
    public void undisruptStorage() { }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "#" + id + ": Flight " + flightStorage.getFlight(flight).getCode() + " cancelled.";
    }
}
