package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {

    private MutationBase mutation;
    private CrossoverBase crossover;
    private FitnessFunctionBase fitnessFunction;
    private PopulationBase population;

    private static int NUMBER_OF_UNMUTATED;

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation) {
        this.population = population;
        this.fitnessFunction = fitnessFunction;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    public PopulationBase evolve() {
        int size = population.getSize();

        for (int i = 0; i < size / 2; i++) {
            int c1 = Utils.randomInt(0, size);
            int c2 = Utils.randomInt(0, size);
            ChromosomeGenesPair newPair = crossover.doCrossover(population.get(c1), population.get(c2));
            population.add(newPair.getFirst());
            population.add(newPair.getSecond());
        }

        population.getAll().sort(new ChromosomeComparator());

        for (int i = NUMBER_OF_UNMUTATED - 1; i < population.getSize(); i++) {
            mutation.doMutation(population.get(i));
        }

        List<ChromosomeBase> newPopulation = new ArrayList<>();
        for (int c = 0; c < size; c++) {
            newPopulation.add(population.get(c));
        }

        population.set(newPopulation);
        return population;
    }

}
