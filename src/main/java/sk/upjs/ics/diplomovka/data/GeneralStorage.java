package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightAttributes;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.util.HashMap;
import java.util.Map;

import static sk.upjs.ics.diplomovka.utils.Utils.MINUTES_IN_DAY;

public class GeneralStorage {

    private FlightStorage flightStorage;
    private StandsStorage standsStorage;
    private int startTime;

    public GeneralStorage(FlightStorage flightStorage, StandsStorage standsStorage, int startTime) {
        this.flightStorage = flightStorage;
        this.standsStorage = standsStorage;
        this.startTime = startTime;
    }

    public FlightStorage getFlightStorage() {
        return flightStorage;
    }

    public StandsStorage getStandsStorage() {
        return standsStorage;
    }

    public GeneralStorage getStorageWithOptionalStart(int startTime) {
        if(startTime > MINUTES_IN_DAY) {
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
        return new GeneralStorage(flightStorage.flightsAfterTime(startTime),
                standsStorage.storageWithNewAvailabilityTimes(availabilityTimes), startTime);
    }

    public int getStartTime() {
        return startTime;
    }
}
