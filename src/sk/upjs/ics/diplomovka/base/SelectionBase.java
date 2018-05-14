package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;

import java.util.List;

public abstract class SelectionBase {

    public abstract List<Chromosome> select(List<Chromosome> generation, int numberOfChromosomes);

    public abstract Chromosome select(List<Chromosome> generation);
}
