package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

import java.util.List;

public class AbsoluteTimeDiffFitness extends FitnessFunctionBase {
    public AbsoluteTimeDiffFitness(FlightStorage flightStorage) {
        super(flightStorage);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        AbsolutePositionChromosome absChromosome = (AbsolutePositionChromosome) chromosome;

        int[] actualStarts = scheduleFlights(absChromosome);
        double fitness = 0;

        for (int i = 0; i < flightStorage.getFlights().size(); i++) {
            int diff = actualStarts[i] - flightStorage.getFlightById(i).getStart();
            if (diff > 0) {
                fitness += diff;
            }
        }

        return fitness;
    }

    private int[] scheduleFlights(AbsolutePositionChromosome chromosome) {
        int[] actualStarts = new int[chromosome.getMaxNoFlights()];
        int[] gateAvailabilityTimes = new int[chromosome.getNoOfGates()];

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                int flightIdx = chromosome.getGene(g, f);
                Flight flight = flightStorage.getFlightById(flightIdx);

                if (gateAvailabilityTimes[g] <= flight.getStart()) { // gate is available sooner than needed
                    actualStarts[flightIdx] = flight.getStart();
                    gateAvailabilityTimes[g] = flight.getEnd();
                } else {
                    actualStarts[flightIdx] = gateAvailabilityTimes[g];
                    gateAvailabilityTimes[g] += flight.getLength();
                }
            }
        }

        return actualStarts;
    }
}
