package sk.upjs.ics.diplomovka.simplechromosome.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiplePointCrossover extends CrossoverBase {

    private int numberOfPoints;

    public MultiplePointCrossover(int numberOfPoints, double probability) {
        super(probability);
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        List<Integer> positions = new ArrayList<>();
        int space = chromosome1.getLength() / numberOfPoints;
        for (int p = 1; p < numberOfPoints; p++) {
            positions.add(Utils.randomInt(space * (p - 1), space * p));
        }
        return doSimpleCrossover((SimpleChromosome) chromosome1, (SimpleChromosome) chromosome2, positions);
    }

    public List<Chromosome> doSimpleCrossover(SimpleChromosome chromosome1, SimpleChromosome chromosome2, List<Integer> positions) {
        if (Math.random() > probability)
            return Collections.emptyList();

        SimpleChromosome new1 = chromosome1.copy();
        SimpleChromosome new2 = chromosome2.copy();

        for (int p = 1; p < positions.size(); p++) {
            if (p % 2 == 1) {
                for (int f = positions.get(p - 1); f < positions.get(p); f++) {
                    new1.setGene(f, chromosome2.getGene(f));
                    new2.setGene(f, chromosome1.getGene(f));
                }
            }
        }

        List<Chromosome> result = new ArrayList<>();
        result.add(new1);
        result.add(new2);
        return result;
    }
}
