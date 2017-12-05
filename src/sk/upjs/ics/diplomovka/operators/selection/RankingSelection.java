package sk.upjs.ics.diplomovka.operators.selection;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.SelectionBase;

import java.util.List;

public class RankingSelection extends SelectionBase {
    private PopulationBase population;

    public RankingSelection(PopulationBase population) {
        this.population = population;
    }

    @Override
    public List<Chromosome> select(PopulationBase population, int numberOfChromosomes) {
        // TODO
        return null;
    }

    public List<Chromosome> select(int numberOfChromosomes) {
        return select(population, numberOfChromosomes);
    }

    @Override
    public Chromosome select(PopulationBase population) {
        // TODO
        return null;
    }

    @Override
    public Chromosome bestChromosome(PopulationBase population) {
        return null;
    }

    public Chromosome select() {
        return select(population);
    }
}
