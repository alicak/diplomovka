package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;

import java.util.List;

public abstract class CrossoverBase {

    protected double probability;

    public CrossoverBase(double probability) {
        this.probability = probability;
    }

    public abstract List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2);

}
