package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
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
        return calculateGeneralFitness(chromosome, true);
    }

    @Override
    public double calculateNonWeightedFitness(Chromosome chromosome) {
        return calculateGeneralFitness(chromosome, false);
    }

    private double calculateGeneralFitness(Chromosome chromosome, boolean weighted) {
        double fitness = 0;

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(g, f));
                int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());
                double weight = weighted ? calculateTotalWeights(flight, weights.getWalkingDistanceWeight()) : 1;
                fitness += weight * standsStorage.getStandsDistance(g, originalStandNo);
            }
        }

        return (-1) * fitness;
    }
}
