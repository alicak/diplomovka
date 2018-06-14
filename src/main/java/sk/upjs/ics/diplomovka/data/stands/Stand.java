package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class Stand {

    private int id;
    private double maxWingspan;
    private double maxWeight;
    private List<Integer> flightCategories;
    private List<Integer> engineTypes;
    private List<Integer> gates;

    public double getMaxWingspan() {
        return maxWingspan;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public List<Integer> getFlightCategories() {
        return flightCategories;
    }

    public List<Integer> getGates() {
        return gates;
    }

    public List<Integer> getEngineTypes() {
        return engineTypes;
    }

    public boolean hasCategory(int category) {
        return flightCategories.contains(category);
    }

    public boolean checkFlight(Flight flight) {
        return flight != null &&
                this.flightCategories.contains(flight.getCategory()) &&
                flight.getAircraft().getWingspan() <= this.maxWingspan &&
                flight.getAircraft().getWeight() <= this.maxWeight &&
                this.engineTypes.contains(flight.getAircraft().getEngineType());
    }

    public int getId() {
        return id;
    }

    public void setGates(List<Integer> gates) {
        this.gates = gates;
    }
}
