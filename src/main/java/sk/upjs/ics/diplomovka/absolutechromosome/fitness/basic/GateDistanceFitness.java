package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.models.data.Transfer;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.stands.StandToGateMapper;

import java.util.Map;

/**
 * calculates fitness by summing distances between gates
 */
public class GateDistanceFitness extends FitnessFunctionBase {
    private StandToGateMapper standToGateMapper;

    public GateDistanceFitness(GeneralStorage storage, FitnessFunctionWeights weights) {
        super(storage, weights);
        standToGateMapper = new StandToGateMapper(storage);
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
        Map<Integer, Integer> flightsToGates = standToGateMapper.mapFlightsToGates(chromosome);

        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                Flight flight = flightStorage.getFlight(chromosome.getGene(s, f));
                int originalGate = flight.getOriginalGateId();

                double weight = weighted ? calculateTotalWeights(flight, weights.getWalkingDistanceWeight()) : 1;
                int newGate = flightsToGates.get(flight.getId());
                fitness += weight * standsStorage.getGatesDistance(newGate, originalGate);

                for (Transfer transfer : flight.getTransfers()) {
                    int arrivingGate = flightsToGates.get(transfer.getFlightId());
                    fitness += weight * standsStorage.getGatesDistance(newGate, arrivingGate);
                }
            }
        }

        return (-1) * fitness;
    }
}
