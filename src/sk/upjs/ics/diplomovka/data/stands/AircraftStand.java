package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.ArrayList;
import java.util.List;

public class AircraftStand {

    private int id;
    private double maxWingspan;
    private List<Flight.FlightCategory> flightCategories;
    private AircraftStandType type;
    private List<String> gates;

    // http://www.aixm.aero/sites/aixm.aero/files/imce/AIXM511HTML/AIXM/DataType_CodeAircraftStandType.html
    public enum AircraftStandType {
        ANG_NI,     // Angled nose-in parking position
        ANG_NO,     // Angled nose-out parking position
        ISOL,       // Isolated parking position
        NI,         // Nose-in parking position
        OTHER,      // Other
        PARL,       // Parallel (to building) parking position
        RMT         // Remote parking position
    }

    public AircraftStand(int id, double maxWingspan, Flight.FlightCategory flightCategory, AircraftStandType type, List<String> gates) {
        this.id = id;
        this.maxWingspan = maxWingspan;
        this.type = type;
        this.flightCategories = new ArrayList<>();
        this.flightCategories.add(flightCategory);
        this.gates = gates;
    }

    public AircraftStand(int id, double maxWingspan, List<Flight.FlightCategory> flightCategories, AircraftStandType type, List<String> gates) {
        this.id = id;
        this.maxWingspan = maxWingspan;
        this.flightCategories = flightCategories;
        this.type = type;
        this.gates = gates;
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
        if(flight == null) {
            return false;
        }
        if (!this.flightCategories.contains(flight.getCategory())) {
            return false;
        }
        if (this.maxWingspan < flight.getAircraft().getWingspan()) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public List<String> getGates() {
        return gates;
    }
}
