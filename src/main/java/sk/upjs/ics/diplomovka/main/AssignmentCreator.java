package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.io.IOException;
import java.util.Arrays;

public class AssignmentCreator {

    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public AssignmentCreator(GeneralStorage storage) {
        this.standsStorage = storage.getStandsStorage();
        this.flightStorage = storage.getFlightStorage();
    }

    public Chromosome createAbsoluteOriginalAssignment(AbsolutePositionFeasibilityChecker feasibilityChecker) {
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
