package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class RelativePositionMutation extends MutationBase {
    private SwapBetweenQueuesMutation swapBetweenQueuesMutation;
    private SwapSuccessiveFlightsMutation swapSuccessiveFlightsMutation;

    public RelativePositionMutation(double probability) {
        super(probability);
        swapBetweenQueuesMutation = new SwapBetweenQueuesMutation(probability);
        swapSuccessiveFlightsMutation = new SwapSuccessiveFlightsMutation(probability);
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        if (Math.random() < 0.5) {
            swapBetweenQueuesMutation.doMutation(chromosome);
        } else {
            swapSuccessiveFlightsMutation.doMutation(chromosome);
        }
    }
}
