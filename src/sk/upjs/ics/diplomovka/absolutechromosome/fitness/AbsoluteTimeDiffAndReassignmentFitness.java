package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteTimeDiffAndReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;
    private AbsoluteReassignmentFitness reassignmentFitness;
    private AbsoluteTimeDiffFitness timeDiffFitness;

    public AbsoluteTimeDiffAndReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        this.standsStorage = standsStorage;
        reassignmentFitness = new AbsoluteReassignmentFitness(flightStorage, standsStorage);
        timeDiffFitness = new AbsoluteTimeDiffFitness(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return timeDiffFitness.calculateFitness(chromosome) * reassignmentFitness.calculateFitness(chromosome);
    }
}
