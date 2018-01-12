package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;

public class AbsolutePositionChromosomeGenerator {

    private int noOfFlights;
    private int noOfGates;
    private AbsolutePositionMutation mutation;
    private AbsolutePositionFeasibilityChecker feasibilityChecker;

    public AbsolutePositionChromosomeGenerator(int noOfGates, int noOfFlights, AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.noOfGates = noOfGates;
        this.noOfFlights = noOfFlights;
        this.mutation = new AbsolutePositionMutation(1);
        this.feasibilityChecker = feasibilityChecker;
    }

    public AbsolutePositionChromosome generateChromosome() {
        AbsolutePositionChromosome chromosome = null;

        boolean feasible = false;
        while (!feasible) {
            chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);
            chromosome.setFeasibilityChecker(feasibilityChecker);
            Integer[] genesArray = new Integer[noOfGates * noOfFlights];
            chromosome.setGenes(Arrays.asList(genesArray));
            Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);

            for (int i = 0; i < noOfFlights; i++) {
                int gate = Utils.randomInt(noOfGates);
                chromosome.addNextFlight(gate, i);
            }

            feasible = chromosome.checkFeasibility();
        }

        return chromosome;
    }

    public AbsolutePositionChromosome generateChromosomeFromExisting(AbsolutePositionChromosome existingChromosome) {
        AbsolutePositionChromosome chromosome = existingChromosome.copy();
        boolean mutated = false;
        while (!mutated) {
            mutated = mutation.doMutation(chromosome);
        }
        return chromosome;
    }
}
