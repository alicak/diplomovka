package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.simplechromosome.mutations.SimpleChromosomeMutation;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SimpleChromosomeGenerator {
    private int noOfGates;
    private int noOfFlights;
    private SimpleChromosomeMutation mutation;
    private SimpleFeasibilityChecker feasibilityChecker;

    public SimpleChromosomeGenerator(int noOfGates, int noOfFlights, SimpleFeasibilityChecker feasibilityChecker) {
        this.noOfGates = noOfGates;
        this.noOfFlights = noOfFlights;
        this.mutation = new SimpleChromosomeMutation(1);
        this.feasibilityChecker = feasibilityChecker;
    }

    public SimpleChromosome generateChromosome() {
        List<Integer> genes;
        SimpleChromosome chromosome = null;

        boolean feasible = false;
        while (!feasible) {
            genes = new ArrayList<>();
            chromosome = new SimpleChromosome(genes, noOfGates);
            chromosome.setFeasibilityChecker(feasibilityChecker);

            for (int f = 0; f < noOfFlights; f++) {
                genes.add(Utils.randomInt(noOfGates));
            }
            feasible = chromosome.checkFeasibility();
        }

        return chromosome;
    }

    public SimpleChromosome generateFromExisting(SimpleChromosome existingChromosome) {
        SimpleChromosome chromosome = existingChromosome.copy();

        boolean mutated = false;
        while (!mutated) {
            mutated = mutation.doMutation(chromosome);
        }

        return chromosome;
    }
}
