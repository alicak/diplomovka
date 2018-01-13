package sk.upjs.ics.diplomovka.simplechromosome.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.simplechromosome.SimpleChromosome;

public class SimpleTimeDiffFitness extends FitnessFunctionBase {
    public SimpleTimeDiffFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        SimpleChromosome simpleChromosome = (SimpleChromosome) chromosome;

        int[] actualStarts = scheduleFlights(simpleChromosome);
        double fitness = 0;

        for (int i = 0; i < chromosome.getNoOfFlights(); i++) {
            int diff = actualStarts[i] - flightStorage.getFlightByNumber(i).getStart();
            if (diff > 0) {
                fitness += diff;
            }
        }

        return fitness;
    }

    @Override
    public double calculateAndSetFitness(Chromosome chromosome) {
        double fitness = calculateFitness(chromosome);
        chromosome.setFitness(fitness);
        return fitness;
    }

    private int[] scheduleFlights(SimpleChromosome chromosome) {
        int[] actualStarts = new int[chromosome.getNoOfFlights()];
        int[] gateAvailabilityTimes = new int[chromosome.getNoOfGates()];

        for (int f = 0; f < chromosome.getNoOfFlights(); f++) {
            Flight flight = flightStorage.getFlightByNumber(f);

            int gate = chromosome.getGene(f);

            if (gateAvailabilityTimes[gate] <= flight.getStart()) { // gate is available sooner than needed
                actualStarts[f] = flight.getStart();
                gateAvailabilityTimes[gate] = flight.getEnd();
            } else {
                actualStarts[f] = gateAvailabilityTimes[gate];
                gateAvailabilityTimes[gate] += flight.getLength();
            }
        }
        return actualStarts;
    }
}
