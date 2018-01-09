package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm extends AlgorithmBase {

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover, MutationBase mutation, SelectionBase selection, TerminationBase termination) {
        super(population, fitnessFunction, crossover, mutation, selection, termination);
    }

    @Override
    public void evolveOneGeneration() {
        int size = population.size();

        List<Chromosome> offspring = new ArrayList<>();
        while(offspring.size() < size) {
            int c1 = Utils.randomInt(size);
            int c2 = Utils.randomInt(size);
            offspring.addAll(crossover.doCrossover(population.get(c1), population.get(c2)));
        }

        for (Chromosome c: offspring) {
            mutation.doMutation(c);
        }

        for(Chromosome c: population.get()) {
            mutation.doMutation(c);
        }

        offspring.addAll(population.get());
        Collections.sort(offspring);

        List<Chromosome> newGeneration = new ArrayList<>();
        for (int c = 0; c < size; c++) {
            newGeneration.add(offspring.get(c));
        }

        population.set(newGeneration);
    }
}
