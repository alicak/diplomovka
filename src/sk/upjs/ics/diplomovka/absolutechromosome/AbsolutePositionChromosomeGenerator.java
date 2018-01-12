package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
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
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);
        chromosome.setFeasibilityChecker(feasibilityChecker);

        Integer[] genesArray = new Integer[noOfGates * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        chromosome.setGenes(Arrays.asList(genesArray));

        for (int i = 0; i < noOfFlights; i++) {
            int gate = Utils.randomInt(noOfGates);
            chromosome.addNextFlight(gate, i);
        }

        return chromosome;
    }

    public AbsolutePositionChromosome generateChromosomeFromExisting(AbsolutePositionChromosome existingChromosome) {
        AbsolutePositionChromosome chromosome = existingChromosome.copy();
        mutation.doMutation(chromosome);
        return chromosome;
    }
}
