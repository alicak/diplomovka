package sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsoluteReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;

    public AbsoluteReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage, FitnessFunctionWeights weights) {
        super(flightStorage, weights);
        this.standsStorage = standsStorage;
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        AbsolutePositionChromosome c = (AbsolutePositionChromosome) chromosome;
        double result = 0;

        for (int g = 0; g < c.getNoOfGates(); g++) {
            for (int f = 0; f < c.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(c.getGene(g,f));
                int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());
                if (g != originalStandNo)
                    result += calculateTotalWeights(flight);
            }
        }

        return result;
    }

}
