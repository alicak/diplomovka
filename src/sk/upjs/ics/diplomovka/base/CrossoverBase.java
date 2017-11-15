package sk.upjs.ics.diplomovka.base;

public abstract class CrossoverBase<ChromosomeType extends ChromosomeBase> {

    public abstract ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2);

}
