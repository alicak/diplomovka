package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandToGateMapper {

    private GeneralStorage storage;
    private static final int GATE_TIME_BEFORE_START = 60; // we need the gate one hour before the departure

    public StandToGateMapper(GeneralStorage storage) {
        this.storage = storage;
    }

    public Map<Integer, String> mapFlightsToGates(Chromosome chromosome) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        Map<String, Integer> usedTimesOnGates = initalizeUsedTimesOnGates(standsStorage);
        Map<Integer, String> flightsToGates = new HashMap<>();

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            int currentStandId = standsStorage.getStandByNumber(g).getId();

            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(g, f));
                String originalGate = flight.getOriginalGate();

                int start = Math.max(0, flight.getStart() - GATE_TIME_BEFORE_START); // 0 for the case that the flight is first
                int end = flight.getEnd();

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
                List<String> gatesForStand = standsStorage.getStandByNumber(g).getGates();
                int noOfGates = gatesForStand.size();
                int randomGateIndex = Utils.randomInt(noOfGates); // we randomly choose starting point in the list of gates
                boolean control = false; // controls if new gate was assigned

                for (int i = 0; i < noOfGates; i++) {
                    String gate = gatesForStand.get((randomGateIndex + i) % noOfGates);
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


    private Map<String, Integer> initalizeUsedTimesOnGates(StandsStorage standsStorage) {
        Map<String, Integer> usedTimesOnGates = new HashMap<>();

        for (AircraftStand stand : standsStorage.getStands()) {
            for (String gate : stand.getGates()) {
                usedTimesOnGates.put(gate, 0);
            }
        }

        return usedTimesOnGates;
    }
}
