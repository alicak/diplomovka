package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;

public class StandsDistanceFitness extends FitnessFunctionBase {
    public StandsDistanceFitness(GeneralStorage storage, FitnessFunctionWeights weights) {
        super(storage, weights);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return 0; // TODO
    }

    @Override
    public double calculateNonWeightedFitness(Chromosome chromosome) {
        return 0; // TODO
    }

    @Override
    protected double calculateTotalWeights(Flight flight) {
        return 0; // TODO
    }
}
