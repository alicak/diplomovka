package sk.upjs.ics.diplomovka.data.flights;

import java.util.List;
import java.util.Map;

public class FlightStorage {
    private List<Flight> flightsList;
    private Map<Integer, Flight> flightsMap;

    public FlightStorage(List<Flight> flightsList, Map<Integer, Flight> flightsMap) {
        this.flightsList = flightsList;
        this.flightsMap = flightsMap;
    }

    public Flight getFlightById(int id) {
        return flightsMap.get(id);
    }

    public List<Flight> getFlights() {
        return flightsList;
    }
}
