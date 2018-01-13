package sk.upjs.ics.diplomovka.simplechromosome.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SimpleChromosomeMutation extends MutationBase {
    private OnePointFlipMutation onePointFlipMutation;
    private UniformFlipMutation uniformFlipMutation;

    protected SimpleChromosomeMutation() {
    }

    public SimpleChromosomeMutation(double probability) {
        super(probability);
        onePointFlipMutation = new OnePointFlipMutation(probability);
        uniformFlipMutation = new UniformFlipMutation(probability);
    }

    @Override
    public boolean doMutation(Chromosome chromosome) {
        SimpleChromosome sCh = (SimpleChromosome) (chromosome);
        double p = Math.random();
        if (p < 0.5) {
            return onePointFlipMutation.doMutation(sCh);
        } else {
            return uniformFlipMutation.doMutation(sCh);
        }
    }

    protected int generateRandomGene(SimpleChromosome chromosome, int position) {
        boolean feasible = false;
        int gene = -1;
        while(!feasible) {
            gene = Utils.randomInt(chromosome.getNoOfGates());
            feasible = chromosome.checkFlightFeasibility(position, gene);
        }
        return gene;
    }
}
