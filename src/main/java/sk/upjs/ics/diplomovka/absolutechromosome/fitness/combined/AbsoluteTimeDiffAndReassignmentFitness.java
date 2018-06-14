package sk.upjs.ics.diplomovka.absolutechromosome.fitness.combined;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.ReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.TimeDiffFitness;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;

public class AbsoluteTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private ReassignmentFitness reassignmentFitness;
    private TimeDiffFitness timeDiffFitness;

    public AbsoluteTimeDiffAndReassignmentFitness(GeneralStorage storage, FitnessFunctionWeights weights) {
        super(storage, weights);
        reassignmentFitness = new ReassignmentFitness(storage, weights);
        timeDiffFitness = new TimeDiffFitness(storage, weights);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) + reassignmentFitness.calculateFitness(chromosome);
    }

    @Override
    public double calculateNonWeightedFitness(Chromosome chromosome) { // doesn't make much sense here, but whatever
        return timeDiffFitness.calculateNonWeightedFitness(chromosome) + reassignmentFitness.calculateNonWeightedFitness(chromosome);
    }
}
