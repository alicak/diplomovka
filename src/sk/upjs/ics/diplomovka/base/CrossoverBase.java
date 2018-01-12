package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class CrossoverBase {

    protected double probability;
    protected FeasibilityCheckerBase feasibilityChecker;

    public CrossoverBase(double probability, FeasibilityCheckerBase feasibilityChecker) {
        this.probability = probability;
        this.feasibilityChecker = feasibilityChecker;
    }

    public abstract List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2);

}
