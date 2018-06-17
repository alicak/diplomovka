package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.CombinedFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.ReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.TimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.disruption.*;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.SolutionCreator;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;
import sk.upjs.ics.diplomovka.storage.stands.closures.conditions.EngineTypeClosureCondition;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class AbsoluteMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        // general part
        int generationSize = 30; // TODO

        DataParser parser = new DataParser();

        GeneralStorage storage = parser.parseDataFromJsons("categories.json", "aircrafts.json",
                "engineTypes.json", "transfers.json", "gates.json", "gateDistances.json",
                "standDistances.json", "stands.json", "departures.json");
        StandsStorage standsStorage = storage.getStandsStorage();
        FlightStorage flightStorage = storage.getFlightStorage();

        List<Disruption> disruptions = parser.parseDisruptions("disruptionsExample.json", storage);

        Disruption gate6closed = new StandClosedDisruption(6, standsStorage, 2);
        Disruption flight13cancelled = new FlightCancelledDisruption(13, flightStorage, 4);
        Disruption flight0delayed = new FlightDelayedDisruption(180, 1, flightStorage, 5); // no 2
        Disruption gate5tempClosed = new StandTemporarilyClosedDisruption(5, 300, 900, standsStorage, 8);
        Disruption gate7condTempClosed = new StandConditionallyClosedDisruption(7, 0, 1439,
                new EngineTypeClosureCondition(Arrays.asList(1)), storage, 9);


        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1000);

        // here we would do that...
        GeneralStorage storage2 = storage.getStorageWithOptionalStart(100);

        AbsolutePositionFeasibilityChecker feasibilityChecker = new AbsolutePositionFeasibilityChecker(storage);
        AssignmentCreator assignmentCreator = new AssignmentCreator(storage);
        Chromosome originalAssignment = assignmentCreator.createAbsoluteOriginalAssignment(feasibilityChecker);
        AbsolutePositionPopulation population = PopulationCreator.createAbsoluteInitialPopulation(generationSize, originalAssignment, feasibilityChecker, storage);

        // only if we need original assignment - otherwise it's already in population
        gate6closed.disruptAssignment(originalAssignment);
        gate5tempClosed.disruptAssignment(originalAssignment);
        gate7condTempClosed.disruptAssignment(originalAssignment);
        flight13cancelled.disruptAssignment(originalAssignment);
        flight0delayed.disruptAssignment(originalAssignment);

        for (Chromosome c : population.get()) {
            gate6closed.disruptAssignment(c);
            gate5tempClosed.disruptAssignment(c);
            gate7condTempClosed.disruptAssignment(c);

            flight13cancelled.disruptAssignment(c);
            flight0delayed.disruptAssignment(c);

        }

        gate6closed.disruptStorage();
        gate5tempClosed.disruptStorage();
        gate7condTempClosed.disruptStorage();
        flight13cancelled.disruptStorage();
        flight0delayed.disruptStorage();


        //TimeDiffFitness fitnessFunction = new TimeDiffFitness(flightStorage);
        //ReassignmentFitness fitnessFunction = new ReassignmentFitness(flightStorage, standsStorage);
        FitnessFunctionWeights weights = new FitnessFunctionWeights()
                .setReassignmentWeight(30)
                .setPassengerWeight(0.1);

        CombinedFitness fitnessFunction = new CombinedFitness(storage,
                Arrays.asList(new ReassignmentFitness(storage, weights), new TimeDiffFitness(storage, weights)));
        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        CrossoverBase crossover = new AbsolutePositionCrossover(1);
        MutationBase mutation = new AbsolutePositionMutation(0.1);

        // results
        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        System.out.println("original fitness: " + fitnessFunction.calculateFitness(originalAssignment));

        FitnessFunctionBase timeDiffFitness = new TimeDiffFitness(storage, weights);
        FitnessFunctionBase reassignmentFitness = new ReassignmentFitness(storage, weights);
        System.out.println("original timediff fitness: " + timeDiffFitness.calculateNonWeightedFitness(originalAssignment));
        System.out.println("timediff fitness: " + timeDiffFitness.calculateNonWeightedFitness(result));
        System.out.println("reassignment fitness: " + reassignmentFitness.calculateNonWeightedFitness(result));

        List<FlightViewModel> flightViewModels = SolutionCreator.createSolutionFromChromosome(result, storage);

        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
        writer.close();
    }
}
