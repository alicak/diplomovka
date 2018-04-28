package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
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
        AbsolutePositionChromosome c = (AbsolutePositionChromosome) chromosome;
        double result = 0;

        for (int g = 0; g < c.getNoOfGates(); g++) {
            for (int f = 0; f < c.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(c.getGene(g, f));
                int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());
                //int originalStandNo = flight.getOriginalStandId();
                if (g != originalStandNo) {
                    double weight = weighted ? calculateTotalWeights(flight) : 1;
                    result += weight;
                }
            }
        }
        return (-1) * result;
    }

    private double calculateTotalWeights(Flight flight) {
        return weights.getReassignmentWeight() // TODO can passenger and priority weight be multiplied only in the end?
                * weights.getPassengerWeight() * flight.getNoOfPassengers()
                * weights.getFlightPriorityWeight() * weights.getFlightPriorityValue(flight.getPriority());
    }

}
