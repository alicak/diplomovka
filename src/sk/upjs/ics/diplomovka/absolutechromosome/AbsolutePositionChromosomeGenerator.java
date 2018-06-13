package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;

public class AbsolutePositionChromosomeGenerator {

    private int noOfFlights;
    private int noOfStands;
    private AbsolutePositionMutation mutation;
    private AbsolutePositionFeasibilityChecker feasibilityChecker;

    public AbsolutePositionChromosomeGenerator(int noOfStands, int noOfFlights, AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.noOfStands = noOfStands;
        this.noOfFlights = noOfFlights;
        this.mutation = new AbsolutePositionMutation(1);
        this.feasibilityChecker = feasibilityChecker;
    }

    public Chromosome generateChromosome() {
        Chromosome chromosome = null;

        boolean feasible = false;
        while (!feasible) {
            chromosome = new Chromosome(noOfStands, noOfFlights);
            chromosome.setFeasibilityChecker(feasibilityChecker);
            Integer[] genesArray = new Integer[noOfStands * noOfFlights];
            chromosome.setGenes(Arrays.asList(genesArray));
            Arrays.fill(genesArray, Chromosome.EMPTY_GENE);

            for (int i = 0; i < noOfFlights; i++) {
                int gate = Utils.randomInt(noOfStands);
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
