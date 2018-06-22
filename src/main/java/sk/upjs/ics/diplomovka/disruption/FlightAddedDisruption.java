package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.FlightAddedDisruptionDataModel;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.Stand;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

public class FlightAddedDisruption implements Disruption {
    private Flight flight;
    private int id;
    private FlightStorage flightStorage;
    private StandsStorage standsStorage;

    public FlightAddedDisruption(Flight flight, GeneralStorage storage, int id) {
        this.flight = flight;
        this.id = id;
        this.flightStorage = storage.getFlightStorage();
        this.standsStorage = storage.getStandsStorage();
    }

    public FlightAddedDisruption(FlightAddedDisruptionDataModel disruption, GeneralStorage storage) {
        this(new Flight(disruption.getFlight(), storage.getFlightStorage().getAttributes()), storage, disruption.getId());
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.addFlight(flight.getId());

        // flight was added with random stand and gate, so we have to set it to stand from newly assigned position,
        // as we are not interested in walking distance here
        Stand originalStand = standsStorage.getStandByNumber(chromosome.findPosition(flight.getId()).getStand());
        int standId = originalStand.getId();
        int gateId = originalStand.getGates().get(0);

        flight.setOriginalStandId(standId);
        flight.setOriginalGateId(gateId);
    }

    @Override
    public void disruptStorage() {
        if (flightStorage.getFlight(flight.getId()) == null)
            flightStorage.addFlight(flight);
    }

    @Override
    public void undisruptStorage() {
        flightStorage.removeFlight(flight);
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
