package sk.upjs.ics.diplomovka.base;

public abstract class MutationBase<ChromosomeType extends ChromosomeBase> {

    private double probability;

    public MutationBase(double probability) {
        this.probability = probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public abstract ChromosomeType applyMutation(ChromosomeType chromosome);

}