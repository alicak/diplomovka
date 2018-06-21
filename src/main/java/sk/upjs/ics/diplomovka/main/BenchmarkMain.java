package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AssignmentCreator;
import sk.upjs.ics.diplomovka.absolutechromosome.FeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.Population;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.PopulationCreator;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.Crossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.CombinedFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.Mutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.disruption.*;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.EngineTypeClosureCondition;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.IOException;
import java.util.Arrays;

public class BenchmarkMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            run();
        }
        long time = 0;
        for (int i = 0; i < 20; i++) {
            time += run();
        }
        System.out.println(time / 20);
    }

    public static long run() throws IOException, InterruptedException {
        int generationSize = 30; // TODO

        DataParser parser = new DataParser();
        GeneralStorage storage = parser.parseDataFromJsons(null, null, null, null, null, null, null, null, null); // TODO
        StandsStorage standsStorage = storage.getStandsStorage();
        FlightStorage flightStorage = storage.getFlightStorage();

        Disruption flight13cancelled = new FlightCancelledDisruption(13, flightStorage, 1);
        Disruption flight0delayed = new FlightDelayedDisruption(180, 0, flightStorage, 2); // no 2
        Disruption flight34delayed = new FlightDelayedDisruption(60, 34, flightStorage, 3); // no 75

        Disruption gate1tempClosed = new StandTemporarilyClosedDisruption(1, 500, 1000, standsStorage, 4);
        Disruption gate5tempClosed = new StandTemporarilyClosedDisruption(5, 300, 900, standsStorage, 5);
        Disruption gate7condTempClosed = new StandConditionallyClosedDisruption(7, 0, 1439,
                new EngineTypeClosureCondition(Arrays.asList(1)), storage, 6);

        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1000);

        AssignmentCreator assignmentCreator = new AssignmentCreator(storage);
        PopulationCreator populationCreator = new PopulationCreator();

        // absolute position chromosome
        FeasibilityChecker feasibilityChecker = new FeasibilityChecker(storage);

        //long startTime = System.nanoTime();

        Chromosome originalAssignment = assignmentCreator.createOriginalAssignment(feasibilityChecker);

        Population population = PopulationCreator.createInitialPopulation(generationSize, originalAssignment, feasibilityChecker, storage);

        gate1tempClosed.disruptAssignment(originalAssignment); // this has to be done after population creation
        gate5tempClosed.disruptAssignment(originalAssignment);
        gate7condTempClosed.disruptAssignment(originalAssignment);

//        flight13cancelled.disruptAssignment(originalAssignment);
//        flight0delayed.disruptAssignment(originalAssignment);
//        flight34delayed.disruptAssignment(originalAssignment);

        for (Chromosome c : population.get()) {
            //gate5closed.disruptAssignment(c);
            //gate6closed.disruptAssignment(c);
            //gate9closed.disruptAssignment(c);
            gate1tempClosed.disruptAssignment(c);
            gate5tempClosed.disruptAssignment(c);
            gate7condTempClosed.disruptAssignment(c);

//            flight13cancelled.disruptAssignment(c);
//            flight0delayed.disruptAssignment(c);
//            flight34delayed.disruptAssignment(c);

        }

        //gate6closed.disruptStorage();
        //gate9closed.disruptStorage();

//        flight13cancelled.disruptStorage();
//        flight0delayed.disruptStorage();
//        flight34delayed.disruptStorage();

        gate1tempClosed.disruptStorage();
        gate5tempClosed.disruptStorage();
        gate7condTempClosed.disruptStorage();

        FitnessFunctionWeights weights = new FitnessFunctionWeights()
                .setReassignmentWeight(10)
                .setPassengerWeight(0.5);
        //AbsoluteTimeDiffAndReassignmentFitness fitnessFunction = new AbsoluteTimeDiffAndReassignmentFitness(storage, weights);
        CombinedFitness fitnessFunction = new CombinedFitness(null, null);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        CrossoverBase crossover = new Crossover(1);
        MutationBase mutation = new Mutation(0.1);

        // results
        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);

        long startTime = System.nanoTime(); // and what about creating the population? :-o

        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000;
    }

}
