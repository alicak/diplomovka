package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class SelectionBase {

    public abstract List<Chromosome> select(List<Chromosome> generation, int numberOfChromosomes);

    public abstract Chromosome select(List<Chromosome> generation);
}
