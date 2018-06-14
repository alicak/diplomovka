package sk.upjs.ics.diplomovka.data.flights;

import java.util.*;

public class FlightStorage {

    private FlightAttributes attributes;
    private Map<Integer, Flight> flightsMap;
    private List<Flight> sortedFlights;

    public FlightStorage(Map<Integer, Flight> flightsMap, FlightAttributes attributes) {
        this.flightsMap = flightsMap;
        this.attributes = attributes;
    }

    public Flight getFlight(int id) {
        return flightsMap.get(id);
    }

    public int getNoOfFlights() {
        return flightsMap.size();
    }

    public void addFlight(Flight flight) {
        flightsMap.put(flight.getId(), flight);
        sortedFlights = null;
    }

    public void removeFlight(Flight flight) {
        flightsMap.remove(flight.getId());
        sortedFlights = null;
    }

    public List<Flight> getSortedFlights() {
        if (sortedFlights == null) {
            sortedFlights = new ArrayList<>(flightsMap.values());
            Collections.sort(sortedFlights);
        }
        return sortedFlights;
    }

    // returns new FlightStorage which includes only flights that have (actual) starts after given time
    public FlightStorage flightsAfterTime(int startTime) {
        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (Flight flight : getSortedFlights()) {
            if (flight.getStart() >= startTime) {
                flightsMap.put(flight.getId(), flight);
            }
        }

        return new FlightStorage(flightsMap, attributes);
    }

    public void flightTimeChanged() {
        sortedFlights = null;
    }
}
