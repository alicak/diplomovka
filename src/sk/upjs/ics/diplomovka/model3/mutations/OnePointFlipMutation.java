package sk.upjs.ics.diplomovka.model3.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.ChromosomeGenerator;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class OnePointFlipMutation extends MutationBase {

    public OnePointFlipMutation(double probability, ChromosomeGenerator generator, FeasibilityCheckerBase feasibilityChecker) {
        super(probability, generator, feasibilityChecker);
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        int position = Utils.randomInt(0, chromosome.getLength());
        doMutation(chromosome, position);
    }

    public void doMutation(Chromosome chromosome, int position) {
        if (Math.random() < probability) {
            generator.generateGene(chromosome, position);
        }
    }
}
