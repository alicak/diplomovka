package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class CrossoverBase {

    protected double probability;

    public CrossoverBase(double probability) {
        this.probability = probability;
    }

    public abstract List<ChromosomeBase> doCrossover(ChromosomeBase chromosome1, ChromosomeBase chromosome2);

}
