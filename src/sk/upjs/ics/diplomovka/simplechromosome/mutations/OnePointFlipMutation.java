package sk.upjs.ics.diplomovka.simplechromosome.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class OnePointFlipMutation extends SimpleChromosomeMutation {

    public OnePointFlipMutation(double probability) {
        this.probability = probability;
    }

    @Override
    public boolean doMutation(Chromosome chromosome) {
        int position = Utils.randomInt(0, chromosome.getLength());
        return doMutation((SimpleChromosome) chromosome, position);
    }

    public boolean doMutation(SimpleChromosome chromosome, int position) {
        if (Math.random() < probability) {
            int geneValue = generateRandomGene(chromosome, position);
            chromosome.setGene(position, geneValue);
            return true;
        }
        return false;
    }
}
