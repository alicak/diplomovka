package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

public abstract class FitnessFunctionBase {

    protected GeneralStorage storage;
    protected FlightStorage flightStorage;
    protected StandsStorage standsStorage;
    protected FitnessFunctionWeights weights;

    public FitnessFunctionBase(GeneralStorage storage, FitnessFunctionWeights weights) {
        this.storage = storage;
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

    protected double calculateTotalWeights(Flight flight, double fitnessSpecificWeight) {
        double weight = fitnessSpecificWeight;

        if (weights.getPassengerWeight() != 0) {
            weight *= weights.getPassengerWeight() * flight.getNoOfPassengers();
        }
        if (weights.getFlightPriorityWeight() != 0) {
            weight *= weights.getFutureWeight(flight.getStart() - storage.getStartTime());
        }

        // flights get their weights depending on their starts
        weight *= weights.getFutureWeight(flight.getStart() - storage.getStartTime());

        return weight;
    }
}