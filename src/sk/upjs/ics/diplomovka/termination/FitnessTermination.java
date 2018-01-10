package sk.upjs.ics.diplomovka.termination;

import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.TerminationBase;

public class FitnessTermination extends TerminationBase {
    private double targetFitness;
    private PopulationBase population;

    public FitnessTermination(double targetFitness, PopulationBase population) {
        this.targetFitness = targetFitness;
        this.population = population;
    }

    @Override
    public boolean isTerminated() {
        return targetFitness >= population.bestChromosome().getFitness();
    }
}