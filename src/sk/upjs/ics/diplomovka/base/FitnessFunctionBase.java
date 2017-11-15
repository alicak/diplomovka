package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class FitnessFunctionBase<FlightType, ChromosomeType extends ChromosomeBase> {

    private List<FlightType> flights;

    public FitnessFunctionBase(List<FlightType> flights) {
        this.flights = flights;
    }

    public abstract double calculateFitness(ChromosomeType chromosome);

}
