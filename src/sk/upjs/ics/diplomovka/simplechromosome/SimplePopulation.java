package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;

import java.util.List;

public class SimplePopulation extends PopulationBase {
    public SimplePopulation(int size) {
        super(size);
    }

    public SimplePopulation(List<Chromosome> generation) {
        super(generation);
    }

    @Override
    public void prepareForFitnessCalculation(List<Chromosome> chromosomes) {
        // nothing here
    }

}
