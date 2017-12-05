package sk.upjs.ics.diplomovka.operators.fitness;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class TimeDiffFitness extends FitnessFunctionBase {
    public TimeDiffFitness(List<Flight> flights) {
        super(flights);
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        return 0;
    }
}
