package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public class AbsoluteTimeDiffCountFitness extends FitnessFunctionBase {
    public AbsoluteTimeDiffCountFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        AbsolutePositionChromosome absChromosome = (AbsolutePositionChromosome) chromosome;

        int[] actualStarts = AbsoluteTimeDiffFitness.scheduleFlights(absChromosome, flightStorage);
        double fitness = 0;

        for (int i = 0; i < chromosome.getNoOfFlights(); i++) {
            int diff = actualStarts[i] - flightStorage.getFlightByNumber(i).getStart();
            if (diff > 0) {
                fitness++;
            }
        }

        return fitness;
    }
}
