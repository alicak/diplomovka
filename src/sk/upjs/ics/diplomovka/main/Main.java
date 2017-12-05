package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.model1.crossovers.RelativePositionCrossover;
import sk.upjs.ics.diplomovka.model1.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.operators.fitness.TimeDiffFitness;
import sk.upjs.ics.diplomovka.operators.selection.RankingSelection;
import sk.upjs.ics.diplomovka.operators.termination.IterationsTermination;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PopulationBase population = null; // TODO
        List<Flight> flights = null; // TODO
        FitnessFunctionBase fitnessFunction = new TimeDiffFitness(flights);
        CrossoverBase crossover = new RelativePositionCrossover(0.8);
        MutationBase mutation = new AbsolutePositionMutation(0.05);
        SelectionBase selection = new RankingSelection(population);
        TerminationBase termination = new IterationsTermination(100);

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = selection.bestChromosome(finalPopulation);
        System.out.println(result.toString());
        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
    }
}
