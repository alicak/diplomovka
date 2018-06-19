package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.Transfer;

import java.util.HashMap;
import java.util.Map;

/**
 * calculates fitness by summing distances between stands
 */
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

        Map<Integer, Integer> flightsToStands = new HashMap<>();
        for (int g = 0; g < chromosome.getNoOfStands(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                flightsToStands.put(chromosome.getGene(g, f), g);
            }
        }

        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                Flight flight = flightStorage.getFlight(chromosome.getGene(s, f));

                double weight = weighted ? calculateTotalWeights(flight, weights.getWalkingDistanceWeight()) : 1;
                fitness += weight * standsStorage.getStandsDistance(s, flight.getOriginalStandId());

                for (Transfer transfer : flight.getTransfers()) {
                    int transferArrivingStand = flightsToStands.get(transfer.getFlightId());
                    fitness += weight * standsStorage.getStandsDistance(s, standsStorage.getStandByNumber(transferArrivingStand).getId());
                }
            }
        }

        return (-1) * fitness;
    }
}
