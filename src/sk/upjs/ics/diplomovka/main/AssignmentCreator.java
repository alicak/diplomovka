package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleFeasibilityChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AssignmentCreator {

    private File arrivalsFile;
    private File departuresFile;
    private FlightCsvParser parser;
    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public AssignmentCreator(File arrivalsFile, File departuresFile, FlightCsvParser parser, StandsStorage standsStorage, FlightStorage flightStorage) throws IOException {
        this.arrivalsFile = arrivalsFile;
        this.departuresFile = departuresFile;
        this.parser = parser;
        this.standsStorage = standsStorage;
        this.flightStorage = flightStorage;
    }

    public AbsolutePositionChromosome createAbsoluteOriginalAssignment(AbsolutePositionFeasibilityChecker feasibilityChecker) throws IOException {
        int noOfFlights = flightStorage.getNoOfFlights();

        AbsolutePositionChromosome originalAssignment = new AbsolutePositionChromosome(standsStorage.getNoOfStands(), noOfFlights);
        originalAssignment.setFeasibilityChecker(feasibilityChecker);

        Integer[] genesArray = new Integer[standsStorage.getNoOfStands() * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        originalAssignment.setGenes(Arrays.asList(genesArray));

        for (Flight f : flightStorage.getFlights()) {
            int standNo = standsStorage.getNumberById(f.getOriginalStandId());
            int flightNo = flightStorage.getNumberById(f.getId());
            originalAssignment.setGene(standNo, originalAssignment.getNoOfFlights(standNo), flightNo);
        }

        return originalAssignment;
    }

    public SimpleChromosome createSimpleOriginalAssignment(SimpleFeasibilityChecker feasibilityChecker) throws IOException {
        SimpleChromosome originalAssignment = new SimpleChromosome(standsStorage.getNoOfStands());
        originalAssignment.setFeasibilityChecker(feasibilityChecker);

        Integer[] genesArray = new Integer[flightStorage.getNoOfFlights()];
        Arrays.fill(genesArray, SimpleChromosome.EMPTY_GENE);
        originalAssignment.setGenes(Arrays.asList(genesArray));

        for (Flight f : flightStorage.getFlights()) {
            int standNo = standsStorage.getNumberById(f.getOriginalStandId());
            int flightNo = flightStorage.getNumberById(f.getId());
            originalAssignment.setGene(flightNo, standNo);
        }

        return originalAssignment;
    }

}
