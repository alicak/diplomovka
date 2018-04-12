package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.GeneralStorage;

import java.util.ArrayList;
import java.util.List;

public abstract class AlgorithmBase {

    protected MutationBase mutation;
    protected CrossoverBase crossover;
    protected FitnessFunctionBase fitnessFunction;
    protected PopulationBase population;
    protected SelectionBase selection;
    protected TerminationBase termination;
    protected GeneralStorage storage;

    public AlgorithmBase(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation, SelectionBase selection, TerminationBase termination, GeneralStorage storage) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
        this.termination = termination;
        this.storage = storage;
    }

    public abstract void evolveOneGeneration();

    protected void evolveOneGenerationSimple() {
        List<Chromosome> newGeneration = new ArrayList<>();

        while (newGeneration.size() < population.getNewGenerationSize()) {
            List<Chromosome> pair = selection.select(population.get(), 2);
            List<Chromosome> children = crossover.doCrossover(pair.get(0), pair.get(1));
            mutation.doMutation(children);
            newGeneration.addAll(children);
        }

        calculateAndSetFitness(newGeneration);
        population.set(newGeneration);
    }

    protected void calculateAndSetFitness(List<Chromosome> chromosomes) {
        for (Chromosome chromosome : chromosomes) {
            if (!chromosome.hasFitness()) {
                fitnessFunction.calculateAndSetFitness(chromosome);
            }
        }
    }

    public PopulationBase evolve() {
        while (!termination.isTerminated()) {
            evolveOneGeneration();
            termination.onStepPerformed();
        }
        return population;
    }
}
