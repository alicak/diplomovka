package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

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

    public AssignmentCreator(File arrivalsFile, File departuresFile, FlightCsvParser parser, StandsStorage standsStorage, FlightStorage flightStorage) {
        this.arrivalsFile = arrivalsFile;
        this.departuresFile = departuresFile;
        this.parser = parser;
        this.standsStorage = standsStorage;
        this.flightStorage = flightStorage;
    }

    public AbsolutePositionChromosome createOriginalAssignment(AbsolutePositionFeasibilityChecker feasibilityChecker) throws IOException {
        List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);
        FlightId.reset();

        int noOfFlights = arrivalsFull.size() + departuresFull.size();

        AbsolutePositionChromosome originalAssignment = new AbsolutePositionChromosome(standsStorage.getNoOfStands(), noOfFlights);
        originalAssignment.setFeasibilityChecker(feasibilityChecker);
        Integer[] genesArray = new Integer[standsStorage.getNoOfStands() * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        originalAssignment.setGenes(Arrays.asList(genesArray));

        List<FlightWithGate> flights = new ArrayList<>();

        for (FullArrival a : arrivalsFull) {
            Flight f = FullArrival.toFlight(a);
            flights.add(new FlightWithGate(f, a.getGate()));
        }

        for (FullDeparture d : departuresFull) {
            Flight f = FullDeparture.toFlight(d);
            flights.add(new FlightWithGate(f, d.getGate()));
        }

        Collections.sort(flights);

        for (FlightWithGate f : flights) {
            int standNo = standsStorage.getNumberById(standsStorage.getStandIdByGate(f.gate));
            int flightNo = flightStorage.getNumberById(f.flight.getId());
            originalAssignment.setGene(standNo, originalAssignment.getNoOfFlights(standNo), flightNo);
        }

        return originalAssignment;
    }

    private class FlightWithGate extends Flight {
        private String gate;
        private Flight flight;

        public FlightWithGate(Flight flight, String gate) {
            this.flight = flight;
            this.gate = gate;
            this.setStart(flight.getStart());
        }
    }
}
