package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.disruption.StandClosedDisruption;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // general part
        File aircraftsFile = new File("aircrafts.csv");
        File arrivalsFile = new File("arrivals.csv");
        File departuresFile = new File("departures.csv");
        File standsFile = new File("stands.csv");

        int generationSize = 20; // TODO

        FlightCsvParser parser = new FlightCsvParser(aircraftsFile, standsFile);
        StandsStorage standsStorage = parser.parseStands(standsFile);
        FlightStorage flightStorage = processFlights(arrivalsFile, departuresFile, parser);

        Disruption gate3closed = new StandClosedDisruption(5, standsStorage);

        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(10000);

        AssignmentCreator assignmentCreator = new AssignmentCreator(arrivalsFile, departuresFile, parser, standsStorage);
        PopulationCreator populationCreator = new PopulationCreator();

        // chromosome-specific part
        AbsolutePositionFeasibilityChecker feasibilityChecker = new AbsolutePositionFeasibilityChecker(standsStorage, flightStorage);
        AbsolutePositionChromosome originalAssignment = assignmentCreator.createOriginalAssignment(feasibilityChecker);

        gate3closed.disruptAssignment(originalAssignment);

        PopulationBase population = populationCreator.createInitialPopulation(generationSize, originalAssignment, feasibilityChecker);

        AbsoluteTimeDiffFitness fitnessFunction = new AbsoluteTimeDiffFitness(flightStorage);
        fitnessFunction.calculateAndSetFitness(originalAssignment);
        double originalFitness = originalAssignment.getFitness();

        CrossoverBase crossover = new AbsolutePositionCrossover(0.8);
        MutationBase mutation = new AbsolutePositionMutation(0.05);

        // results
        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        System.out.println("original fitness: " + originalFitness);
        System.out.println("no of iterations: " + termination.getNoOfIterations());
        System.out.println("fitness: " + result.getFitness());

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

}
