package sk.upjs.ics.diplomovka.operators.crossover;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.ChromosomeGenesPair;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MultiplePointCrossover<ChromosomeType extends ChromosomeBase> extends CrossoverBase<ChromosomeType> {

    private int numberOfPoints;

    public MultiplePointCrossover(int numberOfPoints, double probability) {
        super(probability);
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    public void doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2) {
        List<Integer> positions = new ArrayList<>();
        int space = chromosome1.getLength() / numberOfPoints;
        for (int p = 1; p < numberOfPoints; p++) {
            positions.add(Utils.randomInt(space * (p - 1), space * p));
        }
        doCrossover(chromosome1, chromosome2, positions);
    }

    public void doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2, List<Integer> positions) {
        if(Math.random() > probability) {
            return;
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
        chromosome1.setGenes(new1);
        chromosome2.setGenes(new2);
    }
}
