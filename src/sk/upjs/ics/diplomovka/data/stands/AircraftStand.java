package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.ArrayList;
import java.util.List;

public class AircraftStand {

    private int id;
    private double maxWingspan;
    private List<Flight.FlightCategory> flightCategories;
    private List<Aircraft.EngineType> engineTypes;
    private List<Integer> gates;

    public AircraftStand(int id, double maxWingspan, List<Flight.FlightCategory> flightCategories, List<Aircraft.EngineType> engineTypes) {
        this.id = id;
        this.maxWingspan = maxWingspan;
        this.flightCategories = flightCategories;
        this.engineTypes = engineTypes;
    }

    public double getMaxWingspan() {
        return maxWingspan;
    }

    public List<Flight.FlightCategory> getFlightCategories() {
        return flightCategories;
    }

    public boolean hasCategory(Flight.FlightCategory category) {
        return flightCategories.contains(category);
    }

    public boolean checkFlight(Flight flight) {
        return flight != null &&
                this.flightCategories.contains(flight.getCategory()) &&
                !(this.maxWingspan < flight.getAircraft().getWingspan()) &&
                this.engineTypes.contains(flight.getAircraft().getEngineType());
    }

    public int getId() {
        return id;
    }

    public List<Integer> getGates() {
        return gates;
    }

    public void setGates(List<Integer> gates) {
        this.gates = gates;
    }
}
