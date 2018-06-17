package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlightGenerator extends Generator {

    private int maxId;
    private int startTime;
    private List<String> destinations = Arrays.asList(
            "Amsterdam", "Brussels", "Madrid", "Barcelona", "Bratislava", "Kosice",
            "Paris", "Nice", "Budapest", "Stockholm", "Copenhagen", "Warszaw", "Katowice",
            "Berlin", "Dusseldorf", "Frankfurt", "Hamburg");

    public FlightGenerator(GeneralStorage storage, int startTime) {
        super(storage);
        this.maxId = Collections.max(storage.getFlightStorage().getFlightIds()) + 1;
        this.startTime = startTime;
    }

    public FlightDataModel generateFlight() {
        StandsStorage standsStorage = storage.getStandsStorage();
        FlightStorage flightStorage = storage.getFlightStorage();

        int id = maxId++;
        String scheduled = Utils.minutesToTime(Utils.randomInt(startTime + 60, Utils.MINUTES_IN_DAY - 180)); // TODO startTime constraint
        String actual = scheduled;
        int turnaround = Utils.randomInt(30, 50);
        int standId = chooseFromSet(standsStorage.getStandsIds());
        List<Integer> gates = standsStorage.getStandById(standId).getGates();
        int gateId = gates.get(Utils.randomInt(gates.size()));
        String code = Utils.randomString(Utils.NUM_CHARS, 3, 4);
        int noOfPassengers = Utils.randomInt(80, 120);
        List<Integer> transferIds = Collections.emptyList();
        int aircraftId = chooseFromSet(flightStorage.getAttributes().getAircrafts().keySet());
        int priority = Utils.randomInt(1, 3);
        int categoryId = chooseFromSet(flightStorage.getAttributes().getCategories().keySet());
        String destination = destinations.get(Utils.randomInt(destinations.size()));

        return new FlightDataModel(id, scheduled, actual, turnaround, gateId, standId, code, noOfPassengers, transferIds, aircraftId,
                priority, categoryId, destination);
    }

}
