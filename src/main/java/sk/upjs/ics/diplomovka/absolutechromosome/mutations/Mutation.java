package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class Mutation extends MutationBase {
    private SwapBetweenGatesMutation swapBetweenGatesMutation;
    private MoveBetweenGatesMutation moveBetweenGatesMutation;
    private SwapFlightsMutation swapFlightsMutation;

    public Mutation(double probability) {
        super(probability);
        swapBetweenGatesMutation = new SwapBetweenGatesMutation(probability);
        moveBetweenGatesMutation = new MoveBetweenGatesMutation(probability);
        swapFlightsMutation = new SwapFlightsMutation(probability);
    }

    protected Mutation() {
    }

    @Override
    public boolean doMutation(Chromosome chromosome) {
        double p = Math.random();
        if (p < 1 / 3.0) {
            return swapBetweenGatesMutation.doAbsoluteMutation(chromosome);
        } else if (p < 2 / 3.0) {
            return swapFlightsMutation.doAbsoluteMutation(chromosome);
        } else {
            return moveBetweenGatesMutation.doAbsoluteMutation(chromosome);
        }
    }
}
