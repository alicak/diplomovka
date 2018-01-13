package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleReassignmentFitness extends FitnessFunctionBase {
    private StandsStorage standsStorage;

    public SimpleReassignmentFitness(FlightStorage flightStorage, StandsStorage standsStorage) {
        super(flightStorage);
        this.standsStorage = standsStorage;
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        double result = 0;

        for (int f = 0; f < chromosome.getNoOfFlights(); f++) {
            Flight flight = flightStorage.getFlightByNumber(f);
            int originalStandNo = standsStorage.getNumberById(flight.getOriginalStandId());
            if(chromosome.getGene(f) != originalStandNo)
                result++;
        }

        return result;
    }

}
