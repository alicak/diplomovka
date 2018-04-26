package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.combined.AbsoluteTimeDiffAndReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.SolutionCreator;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.EngineTypeClosureCondition;
import sk.upjs.ics.diplomovka.disruption.*;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.termination.FitnessTermination;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class AbsoluteMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        // general part
        File aircraftsFile = new File("aircrafts.csv");
        File arrivalsFile = new File("arrivals.csv");
        File departuresFile = new File("departures.csv");
        File standsFile = new File("stands.csv");

        int generationSize = 30; // TODO

        FlightCsvParser parser = new FlightCsvParser(aircraftsFile, standsFile);
        StandsStorage standsStorage = parser.parseStands(standsFile);
        FlightStorage flightStorage = processFlights(arrivalsFile, departuresFile, parser, standsStorage);
        GeneralStorage storage = new GeneralStorage(flightStorage, standsStorage);

        Disruption gate5closed = new StandClosedDisruption(5, standsStorage);
        Disruption gate6closed = new StandClosedDisruption(6, standsStorage);
        Disruption gate9closed = new StandClosedDisruption(9, standsStorage);
        Disruption flight13cancelled = new FlightCancelledDisruption(13, flightStorage);
        Disruption flight0delayed = new FlightDelayedDisruption(180, 0, flightStorage); // no 2
        Disruption flight34delayed = new FlightDelayedDisruption(60, 34, flightStorage); // no 75
        Disruption gate1tempClosed = new StandTemporarilyClosedDisruption(1, 500, 1000, standsStorage);
        Disruption gate5tempClosed = new StandTemporarilyClosedDisruption(5, 300, 900, standsStorage);
        Disruption gate7condTempClosed = new StandConditionallyClosedDisruption(7, 0, 1439,
                new EngineTypeClosureCondition(Arrays.asList(Aircraft.EngineType.JET)), standsStorage);


        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1000);

        AssignmentCreator assignmentCreator = new AssignmentCreator(storage);
        PopulationCreator populationCreator = new PopulationCreator();

        // absolute position chromosome
        AbsolutePositionFeasibilityChecker feasibilityChecker = new AbsolutePositionFeasibilityChecker(storage);
        AbsolutePositionChromosome originalAssignment = assignmentCreator.createAbsoluteOriginalAssignment(feasibilityChecker);

        //System.out.println("original fitness 1: " + fitnessFunction.calculateFitness(originalAssignment));

//        gate6closed.disruptAssignment(originalAssignment);
//        gate9closed.disruptAssignment(originalAssignment);
        //flight13cancelled.disruptAssignment(originalAssignment);
//        flight0delayed.disruptAssignment(originalAssignment);
//        flight34delayed.disruptAssignment(originalAssignment);
//        gate5closed.disruptAssignment(originalAssignment);

        AbsolutePositionPopulation population = populationCreator.createAbsoluteInitialPopulation(generationSize, originalAssignment, feasibilityChecker, storage);

        gate1tempClosed.disruptAssignment(originalAssignment); // this has to be done after population creation
        gate5tempClosed.disruptAssignment(originalAssignment);
        gate7condTempClosed.disruptAssignment(originalAssignment);

        flight13cancelled.disruptAssignment(originalAssignment);
        flight0delayed.disruptAssignment(originalAssignment);
        flight34delayed.disruptAssignment(originalAssignment);

        for (Chromosome c : population.get()) {
            //gate5closed.disruptAssignment(c);
            //gate6closed.disruptAssignment(c);
            //gate9closed.disruptAssignment(c);
            gate1tempClosed.disruptAssignment(c);
            gate5tempClosed.disruptAssignment(c);
            gate7condTempClosed.disruptAssignment(c);

            flight13cancelled.disruptAssignment(c);
            flight0delayed.disruptAssignment(c);
            flight34delayed.disruptAssignment(c);

        }

        //gate6closed.disruptStorage();
        //gate9closed.disruptStorage();

        flight13cancelled.disruptStorage();
        flight0delayed.disruptStorage();
        flight34delayed.disruptStorage();

        gate1tempClosed.disruptStorage();
        gate5tempClosed.disruptStorage();
        gate7condTempClosed.disruptStorage();

        //AbsoluteTimeDiffFitness fitnessFunction = new AbsoluteTimeDiffFitness(flightStorage);
        //AbsoluteReassignmentFitness fitnessFunction = new AbsoluteReassignmentFitness(flightStorage, standsStorage);
        FitnessFunctionWeights weights = new FitnessFunctionWeights()
                .setReassignmentWeight(30)
                .setPassengerWeight(0.1);
        AbsoluteTimeDiffAndReassignmentFitness fitnessFunction = new AbsoluteTimeDiffAndReassignmentFitness(storage, weights);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        CrossoverBase crossover = new AbsolutePositionCrossover(1);
        MutationBase mutation = new AbsolutePositionMutation(0.1);

        //TerminationBase termination = new FitnessTermination(6000, population, fitnessFunction);

        // results
        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

       System.out.println("original fitness: " + fitnessFunction.calculateFitness(originalAssignment));
//        System.out.println("no of iterations: " + termination.getNoOfIterations());
//        System.out.println("fitness: " + result.getFitness());

        FitnessFunctionBase timeDiffFitness = new AbsoluteTimeDiffFitness(storage, weights);
        FitnessFunctionBase reassignmentFitness = new AbsoluteReassignmentFitness(storage, weights);
        System.out.println("original timediff fitness: " + timeDiffFitness.calculateNonWeightedFitness(originalAssignment));
        System.out.println("timediff fitness: " + timeDiffFitness.calculateNonWeightedFitness(result));
        System.out.println("reassignment fitness: " + reassignmentFitness.calculateNonWeightedFitness(result));

        List<FlightInfo> flightInfos = SolutionCreator.createSolutionFromChromosome((AbsolutePositionChromosome) result, storage);
        System.out.println(flightInfos);


        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
        writer.close();
    }

    private static FlightStorage processFlights(File arrivalsFile, File departuresFile, FlightCsvParser parser, StandsStorage standsStorage) throws IOException {
        //List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);
        FlightId.reset();

        List<Flight> flights = new ArrayList<>();
        Map<Integer, Flight> flightsMap = new HashMap<>();

//        for (FullArrival a : arrivalsFull) {
//            Flight f = FullArrival.toFlight(a);
//            f.setOriginalStandId(standsStorage.getStandIdByGate(a.getGate()));
//            flights.add(f);
//            flightsMap.put(f.getId(), f);
//        }

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
