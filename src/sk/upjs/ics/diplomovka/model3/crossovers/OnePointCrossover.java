package sk.upjs.ics.diplomovka.model3.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class OnePointCrossover extends CrossoverBase {
    public OnePointCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        int length = chromosome1.getLength();
        int position = Utils.randomInt(0, length);
        return doCrossover(chromosome1, chromosome2, position, length);
    }

    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2, int position, int length) {
        if (Math.random() > probability) {
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

        List<Chromosome> result = new ArrayList<>();
        result.add(new Chromosome(new1));
        result.add(new Chromosome(new2));
        return result;
    }
}
