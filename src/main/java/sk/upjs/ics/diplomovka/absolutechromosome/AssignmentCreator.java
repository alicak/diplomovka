package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

import java.util.Arrays;

/**
 * creates original chromosome - from data in storage
 */

public class AssignmentCreator {

    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public AssignmentCreator(GeneralStorage storage) {
        this.standsStorage = storage.getStandsStorage();
        this.flightStorage = storage.getFlightStorage();
    }

    public Chromosome createOriginalAssignment(FeasibilityChecker feasibilityChecker) {
        int noOfFlights = flightStorage.getNoOfFlights();

        Chromosome originalAssignment = new Chromosome(standsStorage.getNoOfStands(), noOfFlights);
        originalAssignment.setFeasibilityChecker(feasibilityChecker);

        Integer[] genesArray = new Integer[standsStorage.getNoOfStands() * noOfFlights];
        Arrays.fill(genesArray, Chromosome.EMPTY_GENE);
        originalAssignment.setGenes(Arrays.asList(genesArray));

        for (Flight f : flightStorage.getSortedFlights()) {
            int standNo = standsStorage.getNumberById(f.getOriginalStandId());
            originalAssignment.setGene(standNo, originalAssignment.getNoOfFlights(standNo), f.getId());
        }

        return originalAssignment;
    }
}
