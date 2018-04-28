package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public abstract class FitnessFunctionBase {

    protected FlightStorage flightStorage;
    protected StandsStorage standsStorage;
    protected FitnessFunctionWeights weights;

    public FitnessFunctionBase(GeneralStorage storage, FitnessFunctionWeights weights) {
        this.flightStorage = storage.getFlightStorage();
        this.standsStorage = storage.getStandsStorage();
        this.weights = weights;
    }

    public abstract double calculateFitness(Chromosome chromosome);

    public double calculateAndSetFitness(Chromosome chromosome) {
        double fitness = calculateFitness(chromosome);
        chromosome.setFitness(fitness);
        return fitness;
    }

    // makes sense for basic fitness functions when we want concrete results (total delays, no of reassignments etc.)
    public abstract double calculateNonWeightedFitness(Chromosome chromosome);

    protected abstract double calculateTotalWeights(Flight flight);
}