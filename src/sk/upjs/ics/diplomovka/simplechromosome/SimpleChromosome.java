package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

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
        List<Integer> newGenes = new ArrayList<>();
        for (int g = 0; g < getGenes().size(); g++) {
            if (g == flight)
                continue;
            newGenes.add(getGene(g));
        }
        setGenes(newGenes);
    }

    @Override
    public void removeGate(int gate) {
        for (int f = 0; f < getGenes().size(); f++) {
            if (getGene(f) == gate) {
                boolean feasible = false;
                int newGate = -1;

                while (!feasible) {
                    newGate = Utils.randomInt(noOfGates);
                    if (newGate == gate)
                        continue;
                    feasible = checkFlightFeasibility(f, newGate);
                }

                setGene(f, newGate);
            }

            if (getGene(f) > gate)
                setGene(f, getGene(f) - 1);
        }
    }

    @Override
    public boolean checkFlightFeasibility(int flight, int gate) {
        return feasibilityChecker.checkFlightFeasibility(flight, gate);
    }

    @Override
    public boolean checkFeasibility() {
        return feasibilityChecker.checkChromosomeFeasibility(this);
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
