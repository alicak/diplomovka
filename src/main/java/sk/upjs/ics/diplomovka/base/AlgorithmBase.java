package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.GeneralStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AlgorithmBase {

    protected MutationBase mutation;
    protected CrossoverBase crossover;
    protected FitnessFunctionBase fitnessFunction;
    protected PopulationBase population;
    protected SelectionBase selection;
    protected TerminationBase termination;
    protected GeneralStorage storage;

    protected ExecutorService executor;
    protected int noOfCores;

    public AlgorithmBase(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation, SelectionBase selection, TerminationBase termination, GeneralStorage storage) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
        this.termination = termination;
        this.storage = storage;

        this.noOfCores = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(noOfCores);
    }

    public abstract void evolveOneGeneration() throws InterruptedException;

    public PopulationBase evolve() throws InterruptedException {
        while (!termination.isTerminated()) {
            evolveOneGeneration();
            termination.onStepPerformed();
        }
        executor.shutdownNow();
        return population;
    }
}
