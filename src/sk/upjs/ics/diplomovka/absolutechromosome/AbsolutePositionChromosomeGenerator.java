package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;

public class AbsolutePositionChromosomeGenerator {

    private int noOfFlights;
    private int noOfGates;
    private AbsolutePositionMutation mutation;

    public AbsolutePositionChromosomeGenerator(int noOfGates, int noOfFlights, FeasibilityCheckerBase feasibilityChecker) {
        this.noOfGates = noOfGates;
        this.noOfFlights = noOfFlights;
        this.mutation = new AbsolutePositionMutation(1, feasibilityChecker);
    }

    public AbsolutePositionChromosome generateChromosome() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);

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
