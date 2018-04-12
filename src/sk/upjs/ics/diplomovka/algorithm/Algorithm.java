package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm extends AlgorithmBase {

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover,
                     MutationBase mutation, SelectionBase selection, TerminationBase termination, GeneralStorage storage) {
        super(population, fitnessFunction, crossover, mutation, selection, termination, storage);
    }

    @Override
    public void evolveOneGeneration() {
        int size = population.size();

        List<Chromosome> offspring = new ArrayList<>();
        while (offspring.size() < size) {
            int c1 = Utils.randomInt(size);
            int c2 = Utils.randomInt(size);
            offspring.addAll(crossover.doCrossover(population.get(c1), population.get(c2)));
        }

        offspring.addAll(population.get());

        for (Chromosome c : offspring) {
            mutation.doMutation(c);
            c.prepareForFitnessCalculation(storage);
        }

        calculateAndSetFitness(offspring);
        Collections.sort(offspring);

        List<Chromosome> newGeneration = selection.select(offspring, size);

//        try {
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("absolute_fitness.txt", true)));
//            out.println(newGeneration.get(0).getFitness());
//            out.close();
//        } catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }

        population.set(newGeneration);
    }
}
