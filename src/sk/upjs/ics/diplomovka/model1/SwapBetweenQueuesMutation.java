package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class SwapBetweenQueuesMutation extends MutationBase {
    public SwapBetweenQueuesMutation(double probability) {
        super(probability);
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        if(Math.random() > probability) {
            return;
        }

        // TODO
    }
}
