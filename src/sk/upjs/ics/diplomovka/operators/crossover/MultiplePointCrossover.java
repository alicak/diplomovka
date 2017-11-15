package sk.upjs.ics.diplomovka.operators.crossover;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.ChromosomeGenesPair;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class MultiplePointCrossover<ChromosomeType extends ChromosomeBase> extends CrossoverBase<ChromosomeType> {

    int numberOfPoints;

    public MultiplePointCrossover(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    @Override
    public ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2) {
        List<Integer> positions = new LinkedList<>();
        int space = chromosome1.getLength() / numberOfPoints;
        for (int p = 1; p < numberOfPoints; p++) {
            positions.add(Utils.randomInt(space * (p - 1), space * p));
        }
        return doCrossover(chromosome1, chromosome2, positions);
    }

    private ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2, List<Integer> positions) {
        List<Integer> new1 = new LinkedList<>();
        List<Integer> new2 = new LinkedList<>();

        for (int p = 1; p < positions.size(); p++) {
            if (p % 2 == 0) {
                for (int g = positions.get(p - 1); g < positions.get(p); g++) {
                    new1.set(g, chromosome1.getGeneOnPosition(g));
                    new2.set(g, chromosome2.getGeneOnPosition(g));
                }
            } else {
                for (int g = positions.get(p - 1); g < positions.get(p); g++) {
                    new1.set(g, chromosome2.getGeneOnPosition(g));
                    new2.set(g, chromosome1.getGeneOnPosition(g));
                }
            }
        }
        return new ChromosomeGenesPair(new1, new2);
    }
}
