package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;

import java.util.ArrayList;
import java.util.List;

public class PopulationCreator {

    public static Population createInitialPopulation(int generationSize, Chromosome originalAssignment,
                                                     FeasibilityChecker feasibilityChecker,
                                                     GeneralStorage storage) {
        ChromosomeGenerator generator = new ChromosomeGenerator(originalAssignment.getNoOfStands(),
                storage.getFlightStorage().getSortedFlights(), feasibilityChecker);

        // first half are random assignments
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < generationSize / 2; i++) {
            generation.add(generator.generateChromosome());
        }

        // second half are mutations of original assignment
        for (int i = generationSize / 2; i < generationSize - 1; i++) { // minus 1 because original assignment is added later
            generation.add(generator.generateChromosomeFromExisting(originalAssignment));
        }

        originalAssignment.calculateCurrentFlightStarts(storage);

        Population population = new Population(generation);

        for (Chromosome ch : population.get())
            ch.prepareForFitnessCalculation(storage);

        return population;
    }
}
