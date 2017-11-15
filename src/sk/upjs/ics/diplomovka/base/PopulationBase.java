package sk.upjs.ics.diplomovka.base;

import java.util.ArrayList;
import java.util.List;

public abstract class PopulationBase<ChromosomeType extends ChromosomeBase> {

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

    public int getSize() {
        return population.size();
    }

    public abstract void generateRandom();

}