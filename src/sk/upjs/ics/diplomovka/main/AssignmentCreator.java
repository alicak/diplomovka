package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import java.io.IOException;
import java.util.Arrays;

public class AssignmentCreator {

    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public AssignmentCreator(GeneralStorage storage) throws IOException {
        this.standsStorage = storage.getStandsStorage();
        this.flightStorage = storage.getFlightStorage();
    }

    public AbsolutePositionChromosome createAbsoluteOriginalAssignment(AbsolutePositionFeasibilityChecker feasibilityChecker) throws IOException {
        int noOfFlights = flightStorage.getNoOfFlights();

        AbsolutePositionChromosome originalAssignment = new AbsolutePositionChromosome(standsStorage.getNoOfStands(), noOfFlights);
        originalAssignment.setFeasibilityChecker(feasibilityChecker);

        Integer[] genesArray = new Integer[standsStorage.getNoOfStands() * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        originalAssignment.setGenes(Arrays.asList(genesArray));

        for (Flight f : flightStorage.getSortedFlights()) {
            int standNo = standsStorage.getNumberById(f.getOriginalStandId());
            int flightNo = flightStorage.getNumberById(f.getId());
            originalAssignment.setGene(standNo, originalAssignment.getNoOfFlights(standNo), flightNo);
        }

        return originalAssignment;
    }
}
