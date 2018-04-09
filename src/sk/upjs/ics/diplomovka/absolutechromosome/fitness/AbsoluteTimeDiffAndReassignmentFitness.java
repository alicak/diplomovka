package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private AbsoluteReassignmentFitness reassignmentFitness;
    private AbsoluteTimeDiffFitness timeDiffFitness;
    private AbsoluteTimeDiffCountFitness timeDiffCountFitness;

    public AbsoluteTimeDiffAndReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        reassignmentFitness = new AbsoluteReassignmentFitness(flightStorage, standsStorage);
        timeDiffFitness = new AbsoluteTimeDiffFitness(flightStorage);
        timeDiffCountFitness = new AbsoluteTimeDiffCountFitness(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) + 20 * reassignmentFitness.calculateFitness(chromosome);
//        return timeDiffFitness.calculateFitness(chromosome)
//                + 30 * timeDiffCountFitness.calculateFitness(chromosome)
//                + 60 * reassignmentFitness.calculateFitness(chromosome);
    }
}
