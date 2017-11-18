package sk.upjs.ics.diplomovka.base;

import java.util.ArrayList;
import java.util.List;

public abstract class AlgorithmBase {

    private MutationBase mutation;
    private CrossoverBase crossover;
    private FitnessFunctionBase fitnessFunction;
    private PopulationBase population;
    private SelectionBase selection;

    public AlgorithmBase(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation, SelectionBase selection) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
    }

    public abstract void evolveOneGeneration();

    public void evolveOneGenerationSimple() {
        List<Chromosome> oldGeneration = population.get();
        List<Chromosome> newGeneration = new ArrayList<>();

        while (newGeneration.size() < population.getNewGenerationSize()) {
            List<Chromosome> pair = selection.select(2);
            List<Chromosome> newPair = crossover.doCrossover(pair.get(0), pair.get(1));
            mutation.doMutation(newPair);
            newGeneration.addAll(newPair);
        }

        population.set(newGeneration);
    }

    // TODO: termination strategies
}
