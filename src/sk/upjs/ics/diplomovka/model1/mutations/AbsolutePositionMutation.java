package sk.upjs.ics.diplomovka.model1.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class AbsolutePositionMutation extends MutationBase {
    private SwapBetweenGatesMutation swapBetweenGatesMutation;
    private SwapSuccessiveFlightsMutation swapSuccessiveFlightsMutation;
    private MoveBetweenGatesMutation moveBetweenGatesMutation;

    public AbsolutePositionMutation(double probability) {
        super(probability);
        swapBetweenGatesMutation = new SwapBetweenGatesMutation(probability);
        swapSuccessiveFlightsMutation = new SwapSuccessiveFlightsMutation(probability);
        moveBetweenGatesMutation = new MoveBetweenGatesMutation(probability);
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        double p = Math.random();
        if (p < 1/3.0) {
            swapBetweenGatesMutation.doMutation(chromosome);
        } else if (p < 2/3.0) {
            swapSuccessiveFlightsMutation.doMutation(chromosome);
        } else {
            moveBetweenGatesMutation.doMutation(chromosome);
        }
    }
}
