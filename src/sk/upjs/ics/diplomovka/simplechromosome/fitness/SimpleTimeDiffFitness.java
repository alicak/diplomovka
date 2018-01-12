package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class SimpleTimeDiffFitness extends FitnessFunctionBase{
    public SimpleTimeDiffFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public double calculateAndSetFitness(Chromosome chromosome) {
        throw new UnsupportedOperationException(); // TODO
    }
}
