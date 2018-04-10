package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosomeGenerator;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosomeGenerator;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleFeasibilityChecker;
import sk.upjs.ics.diplomovka.simplechromosome.SimplePopulation;

import java.util.ArrayList;
import java.util.List;

public class PopulationCreator {

    public static AbsolutePositionPopulation createAbsoluteInitialPopulation(int generationSize, AbsolutePositionChromosome originalAssignment, AbsolutePositionFeasibilityChecker feasibilityChecker, FlightStorage flightStorage, StandsStorage standsStorage) {
        AbsolutePositionChromosomeGenerator generator = new AbsolutePositionChromosomeGenerator(originalAssignment.getNoOfGates(), originalAssignment.getNoOfFlights(), feasibilityChecker);

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

        AbsolutePositionPopulation population = new AbsolutePositionPopulation(generation, flightStorage, standsStorage);
        population.prepareForFitnessCalculation(generation);

        return population;
    }

    public static SimplePopulation createSimpleInitialPopulation(int generationSize, SimpleChromosome originalAssignment, SimpleFeasibilityChecker feasibilityChecker) {
        SimpleChromosomeGenerator generator = new SimpleChromosomeGenerator(originalAssignment.getNoOfGates(), originalAssignment.getNoOfFlights(), feasibilityChecker);

        // first half are random assignments
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < generationSize / 2; i++) {
            generation.add(generator.generateChromosome());
        }

        // second half are mutations of original assignment
        for (int i = generationSize / 2; i < generationSize - 1; i++) {
            generation.add(generator.generateFromExisting(originalAssignment));
        }

        generation.add(originalAssignment.copy()); // we also add original assignment

        return new SimplePopulation(generation);
    }
}
