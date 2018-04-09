package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private SimpleReassignmentFitness reassignmentFitness;
    private SimpleTimeDiffFitness timeDiffFitness;
    private SimpleTimeDiffCountFitness timeDiffCountFitness;

    public SimpleTimeDiffAndReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        reassignmentFitness = new SimpleReassignmentFitness(flightStorage, standsStorage);
        timeDiffFitness = new SimpleTimeDiffFitness(flightStorage);
        timeDiffCountFitness = new SimpleTimeDiffCountFitness(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) + 20 * reassignmentFitness.calculateFitness(chromosome);
//        return timeDiffFitness.calculateFitness(chromosome)
//                + 30 * timeDiffCountFitness.calculateFitness(chromosome)
//                + 60 * reassignmentFitness.calculateFitness(chromosome);
    }
}
