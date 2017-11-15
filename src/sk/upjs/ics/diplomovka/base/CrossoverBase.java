package sk.upjs.ics.diplomovka.base;

public abstract class CrossoverBase<ChromosomeType> {

    public abstract ChromosomePair<ChromosomeType> doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2);

}
