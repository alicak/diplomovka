package sk.upjs.ics.diplomovka.storage;

import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

import java.util.HashMap;
import java.util.Map;

import static sk.upjs.ics.diplomovka.utils.Utils.MINUTES_IN_DAY;

public class GeneralStorage {

    private FlightStorage flightStorage;
    private StandsStorage standsStorage;
    private int startTime = 0;

    public GeneralStorage(FlightStorage flightStorage, StandsStorage standsStorage, int startTime) {
        this.flightStorage = flightStorage;
        this.standsStorage = standsStorage;
        standsStorage.initializeAvailabilityTimes(startTime);
        this.startTime = startTime;
    }

    public FlightStorage getFlightStorage() {
        return flightStorage;
    }

    public StandsStorage getStandsStorage() {
        return standsStorage;
    }

    // creates storage with new start time - but not a copy
    public GeneralStorage getStorageWithOptionalStart(int startTime) {
        if (startTime > MINUTES_IN_DAY) {
            throw new IllegalArgumentException("Start time can't be more than number of minutes in one day.");
        }

        // times for stands form which we can start assigning flights
        Map<Integer, Integer> availabilityTimes = new HashMap<>();

        for (Integer standId : standsStorage.getStandsIds()) {
            availabilityTimes.put(standId, startTime);
        }

        // if a flight overlaps start time, we set it as end of that flight
        for (Flight flight : flightStorage.getSortedFlights()) {
            if (flight.getStart() < startTime && flight.getEnd() > startTime) {
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
