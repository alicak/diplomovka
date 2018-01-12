package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class MutationBase {

    protected double probability;
    protected ChromosomeGenerator generator;
    protected FeasibilityCheckerBase feasibilityChecker;

    public MutationBase(double probability, FeasibilityCheckerBase feasibilityChecker) {
        this.probability = probability;
        this.feasibilityChecker = feasibilityChecker;
    }

    public MutationBase(double probability, ChromosomeGenerator generator, FeasibilityCheckerBase feasibilityChecker) {
        this.probability = probability;
        this.generator = generator;
        this.feasibilityChecker = feasibilityChecker;
    }

    protected MutationBase() {

    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public abstract void doMutation(Chromosome chromosome);

    public void doMutation(List<Chromosome> chromosomes) {
        for (Chromosome chromosome : chromosomes)
            doMutation(chromosome);
    }
}