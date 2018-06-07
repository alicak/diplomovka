package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.*;

public class StandToGateMapper {

    private GeneralStorage storage;
    private static final int GATE_TIME_BEFORE_START = 60; // we need the gate one hour before the departure

    public StandToGateMapper(GeneralStorage storage) {
        this.storage = storage;
    }

    public Map<Integer, Integer> mapFlightsToGates(Chromosome chromosome) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        Map<Integer, Integer> flightsToGates = new HashMap<>();

        List<Integer> usedTimesOnGates = initalizeUsedTimesOnGates(standsStorage);

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            int currentStandId = standsStorage.getStandByNumber(g).getId();

            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(g, f));
                int originalGate = flight.getOriginalGateId();

                int start = Math.max(0, flight.getStart() - GATE_TIME_BEFORE_START); // 0 for the case that the flight is first
                int end = flight.getEnd();

                // stand was not changed
                if (flight.getOriginalStandId() == currentStandId) {
                    // try the original gate
                    if (usedTimesOnGates.get(originalGate) <= start) {
                        flightsToGates.put(flight.getId(), originalGate);
                        usedTimesOnGates.set(originalGate, end);
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
                        usedTimesOnGates.set(gate, end);
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


    private List<Integer> initalizeUsedTimesOnGates(StandsStorage standsStorage) {
        Integer[] times = new Integer[standsStorage.getNoOfGates()];
        return Arrays.asList(times);
    }
}
