package sk.upjs.ics.diplomovka.operators.termination;

import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.SelectionBase;
import sk.upjs.ics.diplomovka.base.TerminationBase;

public class FitnessTermination extends TerminationBase{
    private double targetFitness;
    private SelectionBase selection;
    private PopulationBase population;

    public FitnessTermination(double targetFitness, SelectionBase selection, PopulationBase population){
        this.targetFitness = targetFitness;
        this.selection = selection;
        this.population = population;
    }

    @Override
    public boolean isTerminated() {
        return targetFitness >= selection.bestChromosome(population).getFitness();
    }
}
