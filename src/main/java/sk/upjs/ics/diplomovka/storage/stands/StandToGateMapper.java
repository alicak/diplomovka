package sk.upjs.ics.diplomovka.storage.stands;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandToGateMapper {

    private GeneralStorage storage;
    private static final int GATE_TIME_BEFORE_START = 0; // TODO: adjust when there are data

    public StandToGateMapper(GeneralStorage storage) {
        this.storage = storage;
    }

    public Map<Integer, Integer> mapFlightsToGates(Chromosome chromosome) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        Map<Integer, Integer> flightsToGates = new HashMap<>();

        Map<Integer, Integer> usedTimesOnGates = initalizeUsedTimesOnGates(standsStorage);

        for (int g = 0; g < chromosome.getNoOfStands(); g++) {
            int currentStandId = standsStorage.getStandByNumber(g).getId();

            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlight(chromosome.getGene(g, f));
                int originalGate = flight.getOriginalGateId();

                // 0 for the case that the flight is the first one
                int start = Math.max(0, chromosome.getCurrentFlightStart(g, f) + flight.getTurnaroundTime() - GATE_TIME_BEFORE_START);
                int end = chromosome.getCurrentFlightEnd(g, f);

                // stand was not changed
                if (flight.getOriginalStandId() == currentStandId) {
                    // try the original gate
                    if (usedTimesOnGates.get(originalGate) <= start) {
                        flightsToGates.put(flight.getId(), originalGate);
                        usedTimesOnGates.put(originalGate, end);
                        continue;
                    }
                }

                // new stand was assigned or original gate is not available - we need to assign a new gate
                List<Integer> gatesForStand = standsStorage.getStandByNumber(g).getGates();
                int noOfGates = gatesForStand.size();
                int randomGateIndex = Utils.randomInt(noOfGates); // we randomly choose starting point in the list of gates
                boolean control = false; // controls if new gate was assigned

                for (int i = 0; i < noOfGates; i++) {
                    int gate = gatesForStand.get((randomGateIndex + i) % noOfGates);
                    if (usedTimesOnGates.get(gate) <= start) {
                        flightsToGates.put(flight.getId(), gate);
                        usedTimesOnGates.put(gate, end);
                        control = true;
                        break;
                    }
                }

                if (!control) {
                    throw new IllegalStateException("Flight must be assigned a gate.");
                }
            }
        }

        return flightsToGates;
    }


    private Map<Integer, Integer>  initalizeUsedTimesOnGates(StandsStorage standsStorage) {
        Map<Integer, Integer> usedTimesOnGates = new HashMap<>();
        for(Integer gate: standsStorage.getGates()) {
            usedTimesOnGates.put(gate, 0);
        }
        return usedTimesOnGates;
    }
}
