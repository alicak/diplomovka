package sk.upjs.ics.diplomovka.simplechromosome.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.ChromosomeGenerator;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class OnePointFlipMutation extends MutationBase {

    public OnePointFlipMutation(double probability, ChromosomeGenerator generator) {
        super(probability, generator);
    }

    @Override
    public boolean doMutation(Chromosome chromosome) {
        int position = Utils.randomInt(0, chromosome.getLength());
        return doMutation(chromosome, position);
    }

    public boolean doMutation(Chromosome chromosome, int position) {
        if (Math.random() < probability) {
            generator.generateGene(chromosome, position);
            return true;
        }
        return false;
    }
}
