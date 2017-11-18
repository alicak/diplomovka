package sk.upjs.ics.diplomovka.base;

import java.util.List;

public class ChromosomeBase implements Comparable<ChromosomeBase> {

    private List<Integer> genes;
    private double fitness;

    public ChromosomeBase() {
    }

    public ChromosomeBase(List<Integer> genes) {
        this.genes = genes;
    }

    public int getLength() {
        return genes.size();
    }

    public int getGene(int position) {
        return genes.get(position);
    }

    public void setGene(int position, int gene) {
        genes.set(position, gene);
    }

    public void setGenes(List<Integer> genes) {
        this.genes = genes;
    }

    public void setGenes(int start, int[] genes) {
        for (int i = 0; i < genes.length; i++) {
            setGene(i + start, genes[i]);
        }
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public String toString() {
        return genes.toString();
    }

    @Override
    public int compareTo(ChromosomeBase c) {
        if (this.getFitness() == c.getFitness())
            return 0;

        if (this.getFitness() < c.getFitness())
            return 1;

        return -1;
    }
}
