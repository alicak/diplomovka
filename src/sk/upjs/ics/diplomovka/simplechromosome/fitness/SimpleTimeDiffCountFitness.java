package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;

public class SimpleTimeDiffCountFitness extends FitnessFunctionBase {
    public SimpleTimeDiffCountFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        SimpleChromosome simpleChromosome = (SimpleChromosome) chromosome;

        int[] actualStarts = SimpleTimeDiffFitness.scheduleFlights(simpleChromosome, flightStorage);
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
