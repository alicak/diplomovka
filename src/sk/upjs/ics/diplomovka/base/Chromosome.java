package sk.upjs.ics.diplomovka.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Chromosome implements Comparable<Chromosome> {

    private List<Integer> genes;
    private double fitness = -1;
    private static final int FITNESS_NOT_SET = -1;

    public Chromosome() {
        genes = new ArrayList<>();
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
        return fitness != FITNESS_NOT_SET;
    }

    public void resetFitness() {
        fitness = FITNESS_NOT_SET;
    }

    public abstract void removeFlight(int flight);

    public abstract void removeGate(int gate);

    public abstract boolean checkFlightFeasibility(int flightNo, int gate);

    public abstract boolean checkFeasibility();

    public abstract int getNoOfGates();

    public abstract int getNoOfFlights();

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
