package sk.upjs.ics.diplomovka.model1.population;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.model1.chromosomes.AbsolutePositionChromosomeGenerator;

import java.util.ArrayList;
import java.util.List;

public class AbsolutePositionPopulation extends PopulationBase {

    private AbsolutePositionChromosomeGenerator generator;
    private List<AbsolutePositionChromosome> generation;

    public AbsolutePositionPopulation(int size, int noOfGates, int noOfFlights) {
        super(size);
        generator = new AbsolutePositionChromosomeGenerator(noOfGates, noOfFlights);
    }

    public AbsolutePositionPopulation(List<AbsolutePositionChromosome> generation) {
        super(generation.size());
        this.generation = generation;
        AbsolutePositionChromosome firstChromosome = generation.get(0);
        generator = new AbsolutePositionChromosomeGenerator(firstChromosome.getNoOfGates(), firstChromosome.getMaxNoFlights());
    }

    @Override
    public void generateAndSetRandom() {
        List<AbsolutePositionChromosome> generation = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            generation.add(generator.generateChromosome());
        }
        this.generation = generation;
    }
}
