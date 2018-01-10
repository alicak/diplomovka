package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.model1.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;

public class AbsolutePositionChromosomeGenerator {

    private int noOfFlights;
    private int noOfGates;
    private AbsolutePositionMutation mutation;

    public AbsolutePositionChromosomeGenerator(int noOfGates, int noOfFlights) {
        this.noOfGates = noOfGates;
        this.noOfFlights = noOfFlights;
        this.mutation = new AbsolutePositionMutation(1);
    }

    public AbsolutePositionChromosome generateChromosome() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);

        Integer[] genesArray = new Integer[noOfGates * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        chromosome.setGenes(Arrays.asList(genesArray));

        int[] lastFlightIdx = new int[noOfGates];

        for (int i = 0; i < noOfFlights; i++) {
            int gate = Utils.randomInt(0, noOfGates);
            chromosome.setGene(gate, lastFlightIdx[gate]++, i);
        }

        return chromosome;
    }

    public AbsolutePositionChromosome generateChromosomeFromExisting(AbsolutePositionChromosome existingChromosome) {
        AbsolutePositionChromosome chromosome = existingChromosome.copy();
        mutation.doMutation(chromosome);
        return chromosome;
    }
}
