package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class MutationWorker implements Callable<Integer> {

    private MutationBase mutation;
    private BlockingQueue<Chromosome> offspring;
    private AtomicInteger counter;

    public MutationWorker(MutationBase mutation, BlockingQueue<Chromosome> offspring, AtomicInteger counter) {
        this.mutation = mutation;
        this.offspring = offspring;
        this.counter = counter;
    }

    @Override
    public Integer call() throws Exception {
        Chromosome chromosome;

        while (true) {
            if (counter.decrementAndGet() >= 0) {
                chromosome = offspring.take();
                mutation.doMutation(chromosome);
                offspring.offer(chromosome);
            } else
                return 1;
        }
    }
}
