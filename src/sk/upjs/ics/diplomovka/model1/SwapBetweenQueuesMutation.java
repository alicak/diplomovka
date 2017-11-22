package sk.upjs.ics.diplomovka.model1;

public class SwapBetweenQueuesMutation extends RelativePositionMutation {
    public SwapBetweenQueuesMutation(double probability) {
        super(probability);
    }

    public void doMutation(AbsolutePositionChromosome chromosome) {
        if(Math.random() > probability) {
            return;
        }

        // TODO
    }
}
