package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosomeGenerator;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;

import java.util.ArrayList;
import java.util.List;

public class PopulationCreator {

    public static AbsolutePositionPopulation createAbsoluteInitialPopulation(int generationSize, Chromosome originalAssignment,
                                                                             AbsolutePositionFeasibilityChecker feasibilityChecker,
                                                                             GeneralStorage storage) {
        AbsolutePositionChromosomeGenerator generator = new AbsolutePositionChromosomeGenerator(originalAssignment.getNoOfStands(),
                storage.getFlightStorage().getSortedFlights(), feasibilityChecker);

        // first half are random assignments
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < generationSize / 2; i++) {
            generation.add(generator.generateChromosome());
        }

        // second half are mutations of original assignment
        for (int i = generationSize / 2; i < generationSize - 1; i++) {
            generation.add(generator.generateChromosomeFromExisting(originalAssignment));
        }

        generation.add(originalAssignment.copy()); // we also add original assignment
        originalAssignment.calculateCurrentFlightStarts(storage);

        AbsolutePositionPopulation population = new AbsolutePositionPopulation(generation);

        for(Chromosome ch: population.get())
            ch.prepareForFitnessCalculation(storage);

        return population;
    }
}
