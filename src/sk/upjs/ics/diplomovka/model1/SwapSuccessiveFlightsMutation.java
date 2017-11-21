package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class SwapSuccessiveFlightsMutation extends MutationBase {
    public SwapSuccessiveFlightsMutation(double probability) {
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
