package sk.upjs.ics.diplomovka.absolutechromosome.population;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosomeGenerator;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;

import java.util.ArrayList;
import java.util.List;

public class AbsolutePositionPopulation extends PopulationBase {

    private AbsolutePositionChromosomeGenerator generator;

    public AbsolutePositionPopulation(List<Chromosome> generation, AbsolutePositionChromosomeGenerator generator) {
        super(generation);
        this.generator = generator;
    }

    @Override
    public void generateAndSetRandom() {
        List<Chromosome> generation = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            generation.add(generator.generateChromosome());
        }
        this.generation = generation;
    }
}
