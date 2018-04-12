package sk.upjs.ics.diplomovka.absolutechromosome.fitness.combined;

import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteTimeDiffCountFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private AbsoluteReassignmentFitness reassignmentFitness;
    private AbsoluteTimeDiffFitness timeDiffFitness;

    public AbsoluteTimeDiffAndReassignmentFitness(GeneralStorage storage, FitnessFunctionWeights weights) {
        super(storage, weights);
        reassignmentFitness = new AbsoluteReassignmentFitness(storage, weights);
        timeDiffFitness = new AbsoluteTimeDiffFitness(storage, weights);
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
