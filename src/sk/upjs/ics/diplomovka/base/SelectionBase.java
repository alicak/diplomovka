package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class SelectionBase {

    protected PopulationBase population;

    public SelectionBase(PopulationBase population) {
        this.population = population;
    }

    public abstract List<ChromosomeBase> select(int numberOfChromosomes);

    public abstract ChromosomeBase select();

}
