package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class PopulationBase {

    private List<ChromosomeBase> generation;

    protected int newGenerationSize;


    public PopulationBase(List<ChromosomeBase> generation) {
        this.generation = generation;
        this.newGenerationSize = generation.size();
    }

    public void set(List<ChromosomeBase> population) {
        this.generation = population;
    }

    public List<ChromosomeBase> get() {
        return generation;
    }

    public ChromosomeBase get(int index) {
        return generation.get(index);
    }

    public void add(ChromosomeBase chromosome) {
        generation.add(chromosome);
    }

    public int size() {
        return generation.size();
    }

    public abstract void generateRandom();

    public int getNewGenerationSize() {
        return newGenerationSize;
    }

    public void setNewGenerationSize(int newGenerationSize) {
        this.newGenerationSize = newGenerationSize;
    }
}