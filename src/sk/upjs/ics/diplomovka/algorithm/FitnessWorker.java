package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class FitnessWorker implements Callable {

    private FitnessFunctionBase fitnessFunction;
    private Queue<Chromosome> offspring;
    private AtomicInteger counter;

    public FitnessWorker(FitnessFunctionBase fitnessFunction, Queue<Chromosome> offspring, AtomicInteger counter) {
        this.fitnessFunction = fitnessFunction;
        this.offspring = offspring;
        this.counter = counter;
    }

    @Override
    public Object call() throws Exception {
        Chromosome chromosome;

        while (true) {
            if (counter.decrementAndGet() >= 0) {
                chromosome = offspring.poll();

                if (!chromosome.hasFitness()) {
                    fitnessFunction.calculateAndSetFitness(chromosome);
                }

                offspring.offer(chromosome);
            } else
                return 1;
        }
    }
}
