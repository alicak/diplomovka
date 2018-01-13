package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class MutationBase {

    protected double probability;

    public MutationBase(double probability) {
        this.probability = probability;
    }

    protected MutationBase() {
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    // true if mutation was performed
    public abstract boolean doMutation(Chromosome chromosome);

    public void doMutation(List<Chromosome> chromosomes) {
        for (Chromosome chromosome : chromosomes)
            doMutation(chromosome);
    }
}