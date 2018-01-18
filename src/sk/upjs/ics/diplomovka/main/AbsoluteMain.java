package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.AbsoluteReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.AbsoluteTimeDiffAndReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.disruption.FlightCancelledDisruption;
import sk.upjs.ics.diplomovka.disruption.FlightDelayedDisruption;
import sk.upjs.ics.diplomovka.disruption.StandClosedDisruption;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.termination.FitnessTermination;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AbsoluteMain {
    public static void main(String[] args) throws IOException {
        // general part
        File aircraftsFile = new File("aircrafts.csv");
        File arrivalsFile = new File("arrivals.csv");
        File departuresFile = new File("departures.csv");
        File standsFile = new File("stands.csv");

        int generationSize = 30; // TODO

        FlightCsvParser parser = new FlightCsvParser(aircraftsFile, standsFile);
        StandsStorage standsStorage = parser.parseStands(standsFile);
        FlightStorage flightStorage = processFlights(arrivalsFile, departuresFile, parser, standsStorage);

        Disruption gate5closed = new StandClosedDisruption(5, standsStorage);
        Disruption gate6closed = new StandClosedDisruption(6, standsStorage);
        Disruption flight13cancelled = new FlightCancelledDisruption(13, flightStorage);
        Disruption flight0delayed = new FlightDelayedDisruption(180, 0, flightStorage); // no 2
        Disruption flight34delayed = new FlightDelayedDisruption(60, 34, flightStorage); // no 75

        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1000);

        AssignmentCreator assignmentCreator = new AssignmentCreator(standsStorage, flightStorage);
        PopulationCreator populationCreator = new PopulationCreator();

        // absolute position chromosome
        AbsolutePositionFeasibilityChecker feasibilityChecker = new AbsolutePositionFeasibilityChecker(standsStorage, flightStorage);
        AbsolutePositionChromosome originalAssignment = assignmentCreator.createAbsoluteOriginalAssignment(feasibilityChecker);

        //gate6closed.disruptAssignment(originalAssignment);
        //flight13cancelled.disruptAssignment(originalAssignment);
//        flight0delayed.disruptAssignment(originalAssignment);
        flight34delayed.disruptAssignment(originalAssignment);
        gate5closed.disruptAssignment(originalAssignment);

        PopulationBase population = populationCreator.createAbsoluteInitialPopulation(generationSize, originalAssignment, feasibilityChecker);
        for (Chromosome c : population.get()) {
            gate5closed.disruptAssignment(c);
        }

        //AbsoluteTimeDiffFitness fitnessFunction = new AbsoluteTimeDiffFitness(flightStorage);
        //AbsoluteReassignmentFitness fitnessFunction = new AbsoluteReassignmentFitness(flightStorage, standsStorage);
        AbsoluteTimeDiffAndReassignmentFitness fitnessFunction = new AbsoluteTimeDiffAndReassignmentFitness(flightStorage, standsStorage);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        CrossoverBase crossover = new AbsolutePositionCrossover(1);
        MutationBase mutation = new AbsolutePositionMutation(0.5);

        //TerminationBase termination = new FitnessTermination(2000, population, fitnessFunction);

        // results
        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        System.out.println("original fitness: " + fitnessFunction.calculateFitness(originalAssignment));
        System.out.println("no of iterations: " + termination.getNoOfIterations());
        System.out.println("fitness: " + result.getFitness());

        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
        writer.close();
    }

    private static FlightStorage processFlights(File arrivalsFile, File departuresFile, FlightCsvParser parser, StandsStorage standsStorage) throws IOException {
        List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);
        FlightId.reset();

        List<Flight> flights = new ArrayList<>();
        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (FullArrival a : arrivalsFull) {
            Flight f = FullArrival.toFlight(a);
            f.setOriginalStandId(standsStorage.getStandIdByGate(a.getGate()));
            flights.add(f);
            flightsMap.put(f.getId(), f);
        }

        for (FullDeparture d : departuresFull) {
            Flight f = FullDeparture.toFlight(d);
            f.setOriginalStandId(standsStorage.getStandIdByGate(d.getGate()));
            flights.add(f);
            flightsMap.put(f.getId(), f);
        }

        Collections.sort(flights);

        return new FlightStorage(flights, flightsMap);
    }

}
