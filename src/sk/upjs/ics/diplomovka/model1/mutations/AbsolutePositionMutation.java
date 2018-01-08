package sk.upjs.ics.diplomovka.model1.mutations;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.MutationBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.AbsolutePositionChromosome;

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

    protected AbsolutePositionMutation() {
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        AbsolutePositionChromosome absCh = (AbsolutePositionChromosome) (chromosome);
        double p = Math.random();
        if (p < 1/3.0) {
            swapBetweenGatesMutation.doAbsoluteMutation(absCh);
        } else if (p < 2/3.0) {
            swapSuccessiveFlightsMutation.doAbsoluteMutation(absCh);
        } else {
            moveBetweenGatesMutation.doAbsoluteMutation(absCh);
        }
    }
}
