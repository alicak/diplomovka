package sk.upjs.ics.diplomovka.operators;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.ChromosomeGenesPair;
import sk.upjs.ics.diplomovka.base.ChromosomePair;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class OnePointCrossover<ChromosomeType extends ChromosomeBase> extends CrossoverBase<ChromosomeType> {
    @Override
    public ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2) {
        int length = chromosome1.getLength();
        int position = Utils.randomInt(0, length);
        return doCrossover(chromosome1, chromosome2, position, length);
    }

    private ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2, int position, int length) {
        List<Integer> new1 = new LinkedList<>();
        List<Integer> new2 = new LinkedList<>();

        for (int g = 0; g < position; g++) {
            new1.set(g, chromosome1.getGeneOnPosition(g));
            new2.set(g, chromosome2.getGeneOnPosition(g));
        }

        for (int g = position; g < length; g++) {
            new1.set(g, chromosome2.getGeneOnPosition(g));
            new2.set(g, chromosome1.getGeneOnPosition(g));
        }

        return new ChromosomeGenesPair(new1, new2);
    }
}
