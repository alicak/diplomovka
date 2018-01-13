package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;
    private SimpleReassignmentFitness reassignmentFitness;
    private SimpleTimeDiffFitness timeDiffFitness;

    public SimpleTimeDiffAndReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        this.standsStorage = standsStorage;
        reassignmentFitness = new SimpleReassignmentFitness(flightStorage, standsStorage);
        timeDiffFitness = new SimpleTimeDiffFitness(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) * reassignmentFitness.calculateFitness(chromosome);
    }
}
