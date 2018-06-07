package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.stands.StandToGateMapper;

import java.util.Map;

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
        Map<Integer, String> flightsToGates = standToGateMapper.mapFlightsToGates(chromosome);

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(g, f));
                String originalGate = flight.getOriginalGate();

                double weight = weighted ? calculateTotalWeights(flight, weights.getWalkingDistanceWeight()) : 1;
                fitness += weight * standsStorage.getGatesDistance(originalGate, flightsToGates.get(flight.getId()));
            }
        }

        return (-1) * fitness;
    }
}
