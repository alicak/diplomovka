package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class MutationWorker implements Callable {

    private MutationBase mutation;
    private Queue<Chromosome> offspring;
    private AtomicInteger counter;

    public MutationWorker(MutationBase mutation, Queue<Chromosome> offspring, AtomicInteger counter) {
        this.mutation = mutation;
        this.offspring = offspring;
        this.counter = counter;
    }

    @Override
    public Object call() throws Exception {
        Chromosome chromosome;

        while (true) {
            if (counter.decrementAndGet() >= 0) {
                chromosome = offspring.poll();
                mutation.doMutation(chromosome);
                offspring.offer(chromosome);
            } else
                return 1;
        }
    }
}
