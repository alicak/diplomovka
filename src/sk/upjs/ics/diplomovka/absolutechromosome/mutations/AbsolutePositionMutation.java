package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.base.MutationBase;

public class AbsolutePositionMutation extends MutationBase {
    private SwapBetweenGatesMutation swapBetweenGatesMutation;
    private SwapSuccessiveFlightsMutation swapSuccessiveFlightsMutation;
    private MoveBetweenGatesMutation moveBetweenGatesMutation;

    public AbsolutePositionMutation(double probability, FeasibilityCheckerBase feasibilityChecker) {
        super(probability, feasibilityChecker);
        swapBetweenGatesMutation = new SwapBetweenGatesMutation(probability, feasibilityChecker);
        swapSuccessiveFlightsMutation = new SwapSuccessiveFlightsMutation(probability, feasibilityChecker);
        moveBetweenGatesMutation = new MoveBetweenGatesMutation(probability, feasibilityChecker);
    }

    protected AbsolutePositionMutation() {
    }

    @Override
    public void doMutation(Chromosome chromosome) {
        AbsolutePositionChromosome absCh = (AbsolutePositionChromosome) (chromosome);
        double p = Math.random();
        if (p < 1 / 3.0) {
            swapBetweenGatesMutation.doAbsoluteMutation(absCh);
        } else if (p < 2 / 3.0) {
            swapSuccessiveFlightsMutation.doAbsoluteMutation(absCh);
        } else {
            moveBetweenGatesMutation.doAbsoluteMutation(absCh);
        }
    }
}
