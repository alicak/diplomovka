package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class ChromosomeGenerator {

    private List<Flight> flights;
    private int noOfFlights;
    private int noOfStands;
    private AbsolutePositionMutation mutation;
    private FeasibilityChecker feasibilityChecker;

    public ChromosomeGenerator(int noOfStands, List<Flight> sortedFlights, FeasibilityChecker feasibilityChecker) {
        this.noOfStands = noOfStands;
        this.flights = sortedFlights;
        this.noOfFlights = flights.size();
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

            for (Flight flight : flights) {
                int stand = Utils.randomInt(noOfStands);
                chromosome.addNextFlight(stand, flight.getId());
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
