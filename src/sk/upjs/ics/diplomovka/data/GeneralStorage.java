package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.util.HashMap;
import java.util.Map;

public class GeneralStorage {

    private FlightStorage flightStorage;
    private StandsStorage standsStorage;

    public GeneralStorage(FlightStorage flightStorage, StandsStorage standsStorage) {
        this.flightStorage = flightStorage;
        this.standsStorage = standsStorage;
    }

    public FlightStorage getFlightStorage() {
        return flightStorage;
    }

    public StandsStorage getStandsStorage() {
        return standsStorage;
    }

    public GeneralStorage getStorageWithOptionalStart(int startTime) {
        if(startTime > 24*60) {
            throw new IllegalArgumentException("Start time can't be more than number of minutes in one day.");
        }

        Map<Integer, Integer> availabilityTimes = new HashMap<>();

        for(Integer standId: standsStorage.getStandsIds()){
            availabilityTimes.put(standId, startTime);
        }

        for(Flight flight: flightStorage.getSortedFlights()) {
            if(flight.getStart() < startTime && flight.getEnd() > startTime) {
                availabilityTimes.put(flight.getOriginalStandId(), flight.getEnd());
            }
        }
        return new GeneralStorage(flightStorage.flightsAfterTime(startTime), standsStorage.storageWithNewAvailabilityTimes(availabilityTimes));
    }
}
