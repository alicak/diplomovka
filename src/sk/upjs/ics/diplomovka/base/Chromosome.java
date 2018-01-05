package sk.upjs.ics.diplomovka.base;

import java.util.List;

public class Chromosome implements Comparable<Chromosome> {

    private List<Integer> genes;
    private double fitness = -1;

    public Chromosome() {
    }

    public Chromosome(List<Integer> genes) {
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
        resetFitness();
    }

    public void setGenes(List<Integer> genes) {
        this.genes = genes;
        resetFitness();
    }

    public void setGenes(int start, int[] genes) {
        for (int i = 0; i < genes.length; i++) {
            setGene(i + start, genes[i]);
        }
        resetFitness();
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

    public boolean hasFitness() {
        return fitness != -1;
    }

    public void resetFitness() {
        fitness = -1;
    }

    public String toString() {
        return "{genes=" + genes.toString() + ", fitness=" + fitness + "}";
    }

    @Override
    public int compareTo(Chromosome c) {
        if (this.getFitness() == c.getFitness())
            return 0;

        if (this.getFitness() < c.getFitness())
            return -1; // this object is better, thus earlier in order

        return 1;
    }
}
