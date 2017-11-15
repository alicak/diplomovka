package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.ChromosomePair;

import java.util.List;

public class PopulationBase<ChromosomeType extends ChromosomeBase> {

    private List<ChromosomeType> population;

    public PopulationBase(List<ChromosomeType> population) {
        this.population = population;
    }

    public void set(List<ChromosomeType> population) {
        this.population = population;
    }

    public List<ChromosomeType> getAll() {
        return population;
    }

    public ChromosomeType get(int index) {
        return population.get(index);
    }

    public void add(ChromosomeType chromosome) {
        population.add(chromosome);
    }

    public void add(ChromosomePair<ChromosomeType> pair) {
        population.add(pair.getFirst());
        population.add(pair.getSecond());
    }

    public int getSize() {
        return population.size();
    }

}