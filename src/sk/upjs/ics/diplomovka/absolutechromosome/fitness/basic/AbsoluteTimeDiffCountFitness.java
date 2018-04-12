package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class AbsoluteTimeDiffCountFitness extends FitnessFunctionBase {
    public AbsoluteTimeDiffCountFitness(FlightStorage flightStorage, FitnessFunctionWeights weights) {
        super(flightStorage, weights);
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
        AbsolutePositionChromosome absChromosome = (AbsolutePositionChromosome) chromosome;
        double fitness = 0;

        for (int g = 0; g < absChromosome.getNoOfGates(); g++) {
            for (int f = 0; f < absChromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(absChromosome.getGene(g, f));
                int diff = absChromosome.getCurrentFlightStart(g, f) - flight.getStart();
                if (diff > 0) {
                    double weight = weighted ? calculateTotalWeights(flight) : 1;
                    fitness += weight;
                }
            }
        }

        return fitness;
    }
}
