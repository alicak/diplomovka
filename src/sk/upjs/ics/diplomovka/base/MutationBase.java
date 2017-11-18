package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class MutationBase {

    protected double probability;
    protected ChromosomeGenerator generator;

    public MutationBase(double probability, ChromosomeGenerator generator) {
        this.probability = probability;
        this.generator = generator;
    }

    public double getProbability(){
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public abstract void doMutation(ChromosomeBase chromosome);

    public void doMutation(List<ChromosomeBase> chromosomes) {
        for(ChromosomeBase chromosome : chromosomes)
            doMutation(chromosome);
    }
}