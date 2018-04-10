package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class AbsoluteTimeDiffFitness extends FitnessFunctionBase {
    public AbsoluteTimeDiffFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        AbsolutePositionChromosome absChromosome = (AbsolutePositionChromosome) chromosome;
        double fitness = 0;

        for (int g = 0; g < absChromosome.getNoOfGates(); g++) {
            for (int f = 0; f < absChromosome.getNoOfFlights(g); f++) {
                Flight flight = flightStorage.getFlightByNumber(absChromosome.getGene(g,f));
                int diff = absChromosome.getCurrentFlightStart(g,f) - flight.getStart();
                if (diff > 0) {
                    fitness += diff;
                }
            }
        }

        return fitness;
    }
}
