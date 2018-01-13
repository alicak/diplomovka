package sk.upjs.ics.diplomovka.simplechromosome.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
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
        if (Math.random() > probability) {
            return null;
        }

        List<Integer> new1 = new ArrayList<>();
        List<Integer> new2 = new ArrayList<>();

        for (int p = 1; p < positions.size(); p++) {
            if (p % 2 == 0) {
                for (int g = positions.get(p - 1); g < positions.get(p); g++) {
                    new1.set(g, chromosome1.getGene(g));
                    new2.set(g, chromosome2.getGene(g));
                }
            } else {
                for (int g = positions.get(p - 1); g < positions.get(p); g++) {
                    new1.set(g, chromosome2.getGene(g));
                    new2.set(g, chromosome1.getGene(g));
                }
            }
        }

        List<Chromosome> result = new ArrayList<>();
        result.add(new SimpleChromosome(new1, chromosome1.getNoOfGates()));
        result.add(new SimpleChromosome(new2, chromosome2.getNoOfGates()));
        return result;
    }
}
