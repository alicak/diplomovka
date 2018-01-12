package sk.upjs.ics.diplomovka.model3.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.ChromosomeGenerator;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class UniformFlipMutation extends MutationBase {

    public UniformFlipMutation(double probability, ChromosomeGenerator generator) {
        super(probability, generator);
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getLength(); i++) {
            if (Math.random() < probability) {
                generator.generateGene(chromosome, i);
            }
        }
    }
}
