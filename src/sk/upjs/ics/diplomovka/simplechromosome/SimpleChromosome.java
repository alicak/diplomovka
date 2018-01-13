package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class SimpleChromosome extends Chromosome {
    private int noOfGates;
    private SimpleFeasibilityChecker feasibilityChecker;

    public SimpleChromosome(int noOfGates) {
        this.noOfGates = noOfGates;
    }

    public SimpleChromosome(List<Integer> genes, int noOfGates) {
        super(genes);
        this.noOfGates = noOfGates;
    }

    public void setFeasibilityChecker(SimpleFeasibilityChecker feasibilityChecker) {
        this.feasibilityChecker = feasibilityChecker;
    }

    @Override
    public void removeFlight(int flight) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void removeGate(int gate) {
        throw new UnsupportedOperationException(); // TODO
    }

    public boolean checkFlightFeasibility(int flight, int gate) {
        throw new UnsupportedOperationException(); // TODO
    }

    public boolean checkFeasibility() {
        throw new UnsupportedOperationException(); // TODO
    }

    public int getNoOfGates() {
        return noOfGates;
    }

    public SimpleChromosome copy() {
        SimpleChromosome chromosome = new SimpleChromosome(noOfGates);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);
        chromosome.setFeasibilityChecker(feasibilityChecker);

        return chromosome;
    }
}
