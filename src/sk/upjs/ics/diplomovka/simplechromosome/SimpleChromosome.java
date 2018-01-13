package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.List;

public class SimpleChromosome extends Chromosome {
    private int noOfGates;
    private SimpleFeasibilityChecker feasibilityChecker;

    public SimpleChromosome(List<Integer> genes, int noOfGates) {
        super(genes);
        this.noOfGates = noOfGates;
    }

    public SimpleFeasibilityChecker getFeasibilityChecker() {
        return feasibilityChecker;
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
}
