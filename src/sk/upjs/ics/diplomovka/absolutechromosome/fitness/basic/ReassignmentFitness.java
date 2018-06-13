package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class ReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;

    public ReassignmentFitness(GeneralStorage storage, FitnessFunctionWeights weights) {
        super(storage, weights);
        this.standsStorage = storage.getStandsStorage();
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
        double result = 0;

        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                Flight flight = flightStorage.getFlight(chromosome.getGene(s, f));
                int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());
                if (s != originalStandNo) {
                    double weight = weighted ? calculateTotalWeights(flight, weights.getReassignmentWeight()) : 1;
                    result += weight;
                }
            }
        }
        return (-1) * result;
    }
}
