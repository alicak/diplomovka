package sk.upjs.ics.diplomovka.base;

public abstract class MutationBase<ChromosomeType extends ChromosomeBase> {

    protected double probability = 0.05;

    public MutationBase(double probability) {
        this.probability = probability;
    }

    public double getProbability(){
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public abstract void doMutation(ChromosomeType chromosome);

}