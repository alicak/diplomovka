package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class SelectionBase {

    public abstract List<Chromosome> select(PopulationBase population, int numberOfChromosomes);

    public abstract Chromosome select(PopulationBase population);

}
