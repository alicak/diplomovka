package sk.upjs.ics.diplomovka.simplechromosome.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;

public class UniformFlipMutation extends SimpleChromosomeMutation {

    public UniformFlipMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public boolean doMutation(Chromosome chromosome) {
        SimpleChromosome c = (SimpleChromosome) chromosome;
        boolean mutated = false;

        for (int i = 0; i < chromosome.getLength(); i++) {
            if (Math.random() < probability) {
                int geneValue = generateRandomGene(c, i);
                chromosome.setGene(i, geneValue);
                mutated = true;
            }
        }
        return mutated;
    }
}
