package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandToGateMapper {

    private GeneralStorage storage;

    public StandToGateMapper(GeneralStorage storage) {
        this.storage = storage;
    }

    public Map<Integer, String> mapFlightsToGates(Chromosome chromosome) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        Map<String, boolean[]> usedTimesOnGates = initalizeUsedTimesOnGates(standsStorage);

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {

                Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(g, f));
                String originalGate = flight.getOriginalGate();

                int start = flight.getStart();
                int end = flight.getEnd();

                // stand was not changed
                if (flight.getOriginalStandId() == standsStorage.getStandByNumber(g).getId()) {
                    // try original gate
                    if (checkUsedTime(start, end, usedTimesOnGates.get(originalGate))) {
                        setUsedTime(start,end,usedTimesOnGates.get(originalGate));
                        continue;
                    }
                }

                // new stand was assigned
                // skusim inu branu z originalnej alebo novej stojanky
                List<String> gatesForStand = standsStorage.getStandByNumber(g).getGates();


                String newGate = gatesForStand.get(0);

            }
        }

        return null; // TODO
    }


    private Map<String, boolean[]> initalizeUsedTimesOnGates(StandsStorage standsStorage) {
        Map<String, boolean[]> usedTimesOnGates = new HashMap<>();

        for (AircraftStand stand : standsStorage.getStands()) {
            for (String gate : stand.getGates()) {
                usedTimesOnGates.put(gate, new boolean[Utils.MINUTES_IN_DAY]);
            }
        }

        return usedTimesOnGates;
    }

    // returns true if whole interval is not used
    private boolean checkUsedTime(int start, int end, boolean[] usedTimes) {
        for (int i = start; i <= end; i++) {
            if (usedTimes[i])
                return false;
        }
        return true;
    }

    private void setUsedTime(int start, int end, boolean[] usedTimes) {
        for (int i = start; i <= end; i++) {
            usedTimes[i] = true;
        }
    }
}
