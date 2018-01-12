package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosomeGenerator;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class PopulationCreator {

    public static AbsolutePositionPopulation createInitialPopulation(int generationSize, AbsolutePositionChromosome originalAssignment, AbsolutePositionFeasibilityChecker feasibilityChecker) {
        AbsolutePositionChromosomeGenerator generator = new AbsolutePositionChromosomeGenerator(originalAssignment.getNoOfGates(), originalAssignment.getMaxNoFlights(), feasibilityChecker);

        // first half are random assignments
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < generationSize / 2; i++) {
            generation.add(generator.generateChromosome());
        }

        // second half are mutations of original assignment
        for (int i = generationSize / 2; i < generationSize - 1; i++) {
            generation.add(generator.generateChromosomeFromExisting(originalAssignment));
        }

        generation.add(originalAssignment);

        return new AbsolutePositionPopulation(generation, generator);
    }
}
