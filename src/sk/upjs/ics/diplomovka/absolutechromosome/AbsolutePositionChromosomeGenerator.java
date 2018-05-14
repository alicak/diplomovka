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

    public Chromosome generateChromosome() {
        Chromosome chromosome = null;

        boolean feasible = false;
        while (!feasible) {
            chromosome = new Chromosome(noOfGates, noOfFlights);
            chromosome.setFeasibilityChecker(feasibilityChecker);
            Integer[] genesArray = new Integer[noOfGates * noOfFlights];
            chromosome.setGenes(Arrays.asList(genesArray));
            Arrays.fill(genesArray, Chromosome.EMPTY_GENE);

            for (int i = 0; i < noOfFlights; i++) {
                int gate = Utils.randomInt(noOfGates);
                chromosome.addNextFlight(gate, i);
            }

            feasible = chromosome.checkFeasibility();
        }

        return chromosome;
    }

    public Chromosome generateChromosomeFromExisting(Chromosome existingChromosome) {
        Chromosome chromosome = existingChromosome.copy();
        boolean mutated = false;
        while (!mutated) {
            mutated = mutation.doMutation(chromosome);
        }
        return chromosome;
    }
}
