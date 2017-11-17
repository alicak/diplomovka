package sk.upjs.ics.diplomovka.base;

public abstract class CrossoverBase<ChromosomeType extends ChromosomeBase> {

    protected double probability = 0.8;

    public CrossoverBase(double probability) {
        this.probability = probability;
    }

    public abstract ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2);

}
