package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;

    public AbsoluteReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        this.standsStorage = standsStorage;
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return 0;
    }

    @Override
    public double calculateAndSetFitness(Chromosome chromosome) {
        return 0;
    }
}
