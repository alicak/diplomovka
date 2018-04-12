package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public abstract class FitnessFunctionBase {

    protected FlightStorage flightStorage;
    protected FitnessFunctionWeights weights;

    public FitnessFunctionBase(FlightStorage flightStorage, FitnessFunctionWeights weights) {
        this.flightStorage = flightStorage;
    }

    public abstract double calculateFitness(Chromosome chromosome);

    public double calculateAndSetFitness(Chromosome chromosome) {
        double fitness = calculateFitness(chromosome);
        chromosome.setFitness(fitness);
        return fitness;
    }

}