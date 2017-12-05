package sk.upjs.ics.diplomovka.base;

import java.util.Collections;
import java.util.List;

public abstract class PopulationBase {

    private List<Chromosome> generation;

    protected int newGenerationSize;


    public PopulationBase(List<Chromosome> generation) {
        this.generation = generation;
        this.newGenerationSize = generation.size();
    }

    public void set(List<Chromosome> population) {
        this.generation = population;
    }

    public List<Chromosome> get() {
        return generation;
    }

    public Chromosome get(int index) {
        return generation.get(index);
    }

    public void add(Chromosome chromosome) {
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

    public Chromosome bestChromosome(PopulationBase population) {
        Collections.sort(generation);
        return generation.get(0);
    }
}