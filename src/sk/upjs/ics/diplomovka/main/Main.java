package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosomeGenerator;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.absolutechromosome.population.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.model2.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File aircraftsFile = new File("aircrafts.csv");
        File arrivalsFile = new File("arrivals.csv");
        File departuresFile = new File("departures.csv");
        File standsFile = new File("stands.csv");

        FlightCsvParser parser = new FlightCsvParser(aircraftsFile, standsFile);
        StandsStorage standsStorage = parser.parseStands(standsFile);
        FlightStorage flightStorage = processFlights(arrivalsFile, departuresFile, parser);

        int generationSize = 20; // TODO

        AbsolutePositionChromosome originalAssignment = createOriginalAssignment(arrivalsFile, departuresFile, parser, standsStorage);
        PopulationBase population = initialPopulation(generationSize, originalAssignment);

        AbsoluteTimeDiffFitness fitnessFunction = new AbsoluteTimeDiffFitness(flightStorage);
        CrossoverBase crossover = new AbsolutePositionCrossover(0.8);
        MutationBase mutation = new AbsolutePositionMutation(0.05);
        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1);

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        System.out.println(result.toString());
        System.out.println(termination.getNoOfIterations());

        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
        writer.close();
    }



    private static FlightStorage processFlights(File arrivalsFile, File departuresFile, FlightCsvParser parser) throws IOException {
        List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);
        FlightId.reset();

        List<Flight> flights = new ArrayList<>();
        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (FullArrival a : arrivalsFull) {
            Flight f = FullArrival.toFlight(a);
            flights.add(f);
            flightsMap.put(f.getId(), f);
        }

        for (FullDeparture d : departuresFull) {
            Flight f = FullDeparture.toFlight(d);
            flights.add(f);
            flightsMap.put(f.getId(), f);
        }

        Collections.sort(flights);

        return new FlightStorage(flights, flightsMap);
    }

    private static AbsolutePositionPopulation initialPopulation(int generationSize, AbsolutePositionChromosome originalAssignment) {
        AbsolutePositionChromosomeGenerator generator = new AbsolutePositionChromosomeGenerator(originalAssignment.getNoOfGates(), originalAssignment.getMaxNoFlights());

        // first half are random assignments
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < generationSize / 2; i++) {
            generation.add(generator.generateChromosome());
        }

        // second half are mutations of original assignment
        for (int i = generationSize / 2; i < generationSize; i++) {
            generation.add(generator.generateChromosomeFromExisting(originalAssignment));
        }

        return new AbsolutePositionPopulation(generation, generator);
    }

    private static AbsolutePositionChromosome createOriginalAssignment(File arrivalsFile, File departuresFile, FlightCsvParser parser, StandsStorage standsStorage) throws IOException {
        List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);
        FlightId.reset();

        int noOfFlights = arrivalsFull.size() + departuresFull.size();

        AbsolutePositionChromosome originalAssignment = new AbsolutePositionChromosome(standsStorage.getNoOfStands(), noOfFlights);
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
            int standNo = standsStorage.getStandNoByGate(f.gate);
            originalAssignment.setGene(standNo, originalAssignment.getNoOfFlights(standNo), f.flight.getId());
        }

        return originalAssignment;
    }

    private static class FlightWithGate extends Flight {
        private String gate;
        private Flight flight;

        public FlightWithGate(Flight flight, String gate) {
            this.flight = flight;
            this.gate = gate;
            this.setStart(flight.getStart());
        }
    }
}
