package sk.upjs.ics.diplomovka.data.flights;

import java.util.*;

public class FlightStorage {
    private Map<Integer, Flight> flightsMap;

    public FlightStorage(List<Flight> flightsList, Map<Integer, Flight> flightsMap) {
        this.flightsMap = flightsMap;
    }

    public Flight getFlight(int id) {
        return flightsMap.get(id);
    }

    public int getNoOfFlights() {
        return flightsMap.size();
    }

    public void addFlight(Flight flight) {
        flightsMap.put(flight.getId(), flight);
    }

    public List<Flight> getSortedFlights() {
        List<Flight> flights = new ArrayList<>(flightsMap.values());
        Collections.sort(flights);
        return flights;
    }

    // returns new FlightStorage which includes only flights that have (actual) starts after given time
    public FlightStorage flightsAfterTime(int startTime) {
        List<Flight> flights = new ArrayList<>();
        Map<Integer, Flight> flightsMap = new HashMap<>();

        for(Flight flight : getSortedFlights()) {
            if(flight.getStart() >= startTime) {
                flights.add(flight);
                flightsMap.put(flight.getId(), flight);
            }
        }

        return new FlightStorage(flights, flightsMap);
    }
}
