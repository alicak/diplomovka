package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class AbsolutePositionMutation extends MutationBase {
    private SwapBetweenGatesMutation swapBetweenGatesMutation;
    private SwapSuccessiveFlightsMutation swapSuccessiveFlightsMutation;
    private MoveBetweenGatesMutation moveBetweenGatesMutation;
    private SwapFlightsMutation swapFlightsMutation;

    public AbsolutePositionMutation(double probability) {
        super(probability);
        swapBetweenGatesMutation = new SwapBetweenGatesMutation(probability);
        swapSuccessiveFlightsMutation = new SwapSuccessiveFlightsMutation(probability);
        moveBetweenGatesMutation = new MoveBetweenGatesMutation(probability);
        swapFlightsMutation = new SwapFlightsMutation(probability);
    }

    protected AbsolutePositionMutation() {
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
