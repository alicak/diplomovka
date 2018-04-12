package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public abstract class FitnessFunctionBase {

    protected FlightStorage flightStorage;
    protected FitnessFunctionWeights weights;

    public FitnessFunctionBase(FlightStorage flightStorage, FitnessFunctionWeights weights) {
        this.flightStorage = flightStorage;
        this.weights = weights;
    }

    public abstract double calculateFitness(Chromosome chromosome);

    public double calculateAndSetFitness(Chromosome chromosome) {
        double fitness = calculateFitness(chromosome);
        chromosome.setFitness(fitness);
        return fitness;
    }

    public double calculateTotalWeights(Flight flight) {
        return weights.getWeightsProduct() * flight.getNoOfPassengers()
                * weights.getFlightPriorityValue(flight.getPriority());
    }

    // makes sense for basic fitness functions when we want concrete results (total delays, no of reassignments etc.)
    public abstract double calculateNonWeightedFitness(Chromosome chromosome);
}