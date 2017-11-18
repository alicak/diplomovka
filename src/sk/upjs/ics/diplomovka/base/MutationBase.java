package sk.upjs.ics.diplomovka.base;

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
}