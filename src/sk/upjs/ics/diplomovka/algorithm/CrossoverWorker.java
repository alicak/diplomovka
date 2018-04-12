package sk.upjs.ics.diplomovka.algorithm;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class CrossoverWorker implements Callable {

    private CrossoverBase crossover;
    private PopulationBase population;
    private Queue<Chromosome> offspring;
    private AtomicInteger counter;

    public CrossoverWorker(CrossoverBase crossover, PopulationBase population, Queue<Chromosome> offspring, AtomicInteger counter) {
        this.crossover = crossover;
        this.population = population;
        this.offspring = offspring;
        this.counter = counter;
    }

    @Override
    public Object call() throws Exception {
        int size = population.size();

        while (true) {
            int c1 = Utils.randomInt(size);
            int c2 = Utils.randomInt(size);
            List<Chromosome> newChromosomes = crossover.doCrossover(population.get(c1), population.get(c2));

            for (Chromosome ch : newChromosomes) {
                if (counter.decrementAndGet() >= 0)
                    offspring.add(ch);
                else
                    return 1;
            }
        }
    }
}
