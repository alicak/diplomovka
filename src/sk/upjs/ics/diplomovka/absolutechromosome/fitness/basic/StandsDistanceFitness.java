package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.Map;

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

        int[] flightsToStands = new int[chromosome.getNoOfFlights()];
        for (int g = 0; g < chromosome.getNoOfStands(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                flightsToStands[chromosome.getGene(g, f)] = g;
            }
        }

        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                Flight flight = flightStorage.getFlight(chromosome.getGene(s, f));
                int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());

                double weight = weighted ? calculateTotalWeights(flight, weights.getWalkingDistanceWeight()) : 1;
                fitness += weight * standsStorage.getStandsDistance(s, originalStandNo);

                for (Map.Entry<Integer, Integer> entry : flight.getTransfers().entrySet()) {
                    fitness += weight * standsStorage.getStandsDistance(flightsToStands[entry.getKey()], originalStandNo);
                }
            }
        }

        return (-1) * fitness;
    }
}
