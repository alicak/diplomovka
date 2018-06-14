package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.GeneralStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Algorithm extends AlgorithmBase {

    public Algorithm(PopulationBase population, FitnessFunctionBase fitnessFunction, CrossoverBase crossover,
                     MutationBase mutation, SelectionBase selection, TerminationBase termination, GeneralStorage storage) {
        super(population, fitnessFunction, crossover, mutation, selection, termination, storage);
    }

    @Override
    public void evolveOneGeneration() throws InterruptedException {
        BlockingQueue<Chromosome> offspring = new LinkedBlockingQueue<>();
        AtomicInteger counter = new AtomicInteger(population.size());

        List<CrossoverWorker> crossoverWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            crossoverWorkers.add(new CrossoverWorker(crossover, population, offspring, counter));
        }
        executor.invokeAll(crossoverWorkers);

        offspring.addAll(population.get());
        counter.set(offspring.size());

        List<MutationWorker> mutationWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            mutationWorkers.add(new MutationWorker(mutation, offspring, counter));
        }
        executor.invokeAll(mutationWorkers);

        counter.set(offspring.size());

        List<FitnessWorker> fitnessWorkers = new LinkedList<>();
        for (int w = 0; w < noOfCores; w++) {
            fitnessWorkers.add(new FitnessWorker(fitnessFunction, offspring, counter, storage));
        }
        executor.invokeAll(fitnessWorkers);

        List<Chromosome> offspringList = new ArrayList<>(offspring);
        Collections.sort(offspringList);

        List<Chromosome> newGeneration = selection.select(offspringList, population.size());

        // TODO: remove that
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
