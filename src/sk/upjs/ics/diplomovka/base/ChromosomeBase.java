package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class ChromosomeBase<GeneType> implements Comparable<ChromosomeBase> {

    private List<GeneType> genes;
    private double fitness;

    public ChromosomeBase() { }

    public ChromosomeBase(List<GeneType> genes) {
        this.genes = genes;
    }

    public int getLength() {
        return genes.size();
    }

    public GeneType getGeneOnPosition(int position) {
        return genes.get(position);
    }

    public void setGeneOnPosition(int position, GeneType gene) {
        genes.set(position, gene);
    }

    public List<GeneType> getGenes() {
        return genes;
    }

    public void setGenes(List<GeneType> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(ChromosomeBase chromosome) {
        if(fitness == chromosome.fitness)
            return 0;
        if(fitness < chromosome.fitness)
            return 1;
        return -1;
    }
}
