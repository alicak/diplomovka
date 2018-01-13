package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;

    public SimpleReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        this.standsStorage = standsStorage;
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return 0;
    }

}
