package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * performs actual evolution of population
 */
public class Algorithm extends AlgorithmBase {

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover,
                     MutationBase mutation, SelectionBase selection, TerminationBase termination, GeneralStorage storage) {
        super(population, fitnessFunction, crossover, mutation, selection, termination, storage);
    }

    @Override
    public void evolveOneGeneration() throws InterruptedException {
        BlockingQueue<Chromosome> offspring = new LinkedBlockingQueue<>();
        AtomicInteger counter = new AtomicInteger(population.size());

        // best chromosome is always in offspring
        Chromosome bestSoFar = population.bestChromosome().copy();

        // does crossover in parallel
        List<CrossoverWorker> crossoverWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            crossoverWorkers.add(new CrossoverWorker(crossover, population, offspring, counter));
        }
        executor.invokeAll(crossoverWorkers);

        offspring.addAll(population.get());
        counter.set(offspring.size());

        // does mutation in parallel
        List<MutationWorker> mutationWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            mutationWorkers.add(new MutationWorker(mutation, offspring, counter));
        }
        executor.invokeAll(mutationWorkers);

        offspring.offer(bestSoFar);
        counter.set(offspring.size());

        // calculates fitness in parallel
        List<FitnessWorker> fitnessWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            fitnessWorkers.add(new FitnessWorker(fitnessFunction, offspring, counter, storage));
        }
        executor.invokeAll(fitnessWorkers);

        List<Chromosome> offspringList = new ArrayList<>(offspring);
        List<Chromosome> newGeneration = selection.select(offspringList, population.size());

        population.set(newGeneration);
    }
}
