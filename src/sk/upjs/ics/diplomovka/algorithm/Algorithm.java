package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm extends AlgorithmBase {

    private static int NUMBER_OF_UNMUTATED;
    private static final double PORTION_UNMUTATED = 0.1;

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation, SelectionBase selection) {
        super(population, fitnessFunction, crossover, mutation, selection);
        NUMBER_OF_UNMUTATED = (int) (population.size() * PORTION_UNMUTATED);
    }

    @Override
    public void evolveOneGeneration() {
        int size = population.size();

        for (int i = 0; i < size / 2; i++) {
            int c1 = Utils.randomInt(0, size);
            int c2 = Utils.randomInt(0, size);
            crossover.doCrossover(population.get(c1), population.get(c2));
            //population.add(newPair.getFirst());
            //population.add(newPair.getSecond());
        }

        Collections.sort(population.get());

        for (int i = NUMBER_OF_UNMUTATED - 1; i < population.size(); i++) {
            mutation.doMutation(population.get(i));
        }

        List<Chromosome> newPopulation = new ArrayList<>();
        for (int c = 0; c < size; c++) {
            newPopulation.add(population.get(c));
        }

        population.set(newPopulation);
    }
}
