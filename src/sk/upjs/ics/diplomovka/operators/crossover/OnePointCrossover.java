package sk.upjs.ics.diplomovka.operators.crossover;

import sk.upjs.ics.diplomovka.base.ChromosomeBase;
import sk.upjs.ics.diplomovka.base.ChromosomeGenesPair;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OnePointCrossover<ChromosomeType extends ChromosomeBase> extends CrossoverBase<ChromosomeType> {
    public OnePointCrossover(double probability) {
        super(probability);
    }

    @Override
    public ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2) {
        int length = chromosome1.getLength();
        int position = Utils.randomInt(0, length);
        return doCrossover(chromosome1, chromosome2, position, length);
    }

    public ChromosomeGenesPair doCrossover(ChromosomeType chromosome1, ChromosomeType chromosome2, int position, int length) {
        if(Math.random() > probability) {
            return null;
        }

        List<Integer> new1 = new ArrayList<>();
        List<Integer> new2 = new ArrayList<>();

        for (int g = 0; g < position; g++) {
            new1.set(g, chromosome1.getGene(g));
            new2.set(g, chromosome2.getGene(g));
        }

        for (int g = position; g < length; g++) {
            new1.set(g, chromosome2.getGene(g));
            new2.set(g, chromosome1.getGene(g));
        }

        return new ChromosomeGenesPair(new1, new2);
    }
}
