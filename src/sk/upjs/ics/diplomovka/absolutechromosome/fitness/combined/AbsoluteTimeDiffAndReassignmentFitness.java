package sk.upjs.ics.diplomovka.absolutechromosome.fitness.combined;

import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteTimeDiffCountFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.AbsoluteTimeDiffFitness;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private AbsoluteReassignmentFitness reassignmentFitness;
    private AbsoluteTimeDiffFitness timeDiffFitness;

    public AbsoluteTimeDiffAndReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage, FitnessFunctionWeights weights) {
        super(flightStorage, weights);
        reassignmentFitness = new AbsoluteReassignmentFitness(flightStorage, standsStorage, weights);
        timeDiffFitness = new AbsoluteTimeDiffFitness(flightStorage, weights);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) + reassignmentFitness.calculateFitness(chromosome);
    }
}
