package sk.upjs.ics.diplomovka.data.flights;

import java.util.*;

public class FlightStorage {
    private Map<Integer, Flight> flightsMap;
    private int[] flightIds;

    public FlightStorage(List<Flight> flightsList, Map<Integer, Flight> flightsMap) {
        this.flightsMap = flightsMap;
        initializeIds(flightsList);
    }

    public Flight getFlightById(int id) {
        return flightsMap.get(id);
    }

    public Flight getFlightByNumber(int number) {
        return flightsMap.get(flightIds[number]);
    }

    public int getNumberById(int id) {
        for (int i = 0; i < flightIds.length; i++) {
            if (flightIds[i] == id)
                return i;
        }
        return -1; // flight not found
    }

    public int getNoOfFlights() {
        return flightsMap.size();
    }

    public int addFlight(Flight flight) {
        return 0;  // TODO
    }

    public void removeFlight(int id) {
        if(!flightsMap.containsKey(id))
            return;

        flightsMap.remove(id);

        int length = flightIds.length;
        for (int i = 0; i < length; i++) {
            if (flightIds[i] == id) {

                for (int j = i; j < length - 1; j++) {
                    flightIds[j] = flightIds[j + 1];
                }

                flightIds = Arrays.copyOf(flightIds, length - 1);
                return;
            }
        }
    }

    public List<Flight> getSortedFlights() {
        List<Flight> flights = new ArrayList<>(flightsMap.values());
        Collections.sort(flights);
        return flights;
    }

    private int[] initializeIds(List<Flight> flights) {
        flightIds = new int[flights.size()];

        for (int j = 0; j < flightIds.length; j++) {
            flightIds[j] = flights.get(j).getId();
        }

        return flightIds;
    }
}
