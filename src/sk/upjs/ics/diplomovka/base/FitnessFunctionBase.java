package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public abstract class FitnessFunctionBase {

    private List<Flight> flights;

    public FitnessFunctionBase(List<Flight> flights) {
        this.flights = flights;
    }

    public abstract double calculateFitness(Chromosome chromosome);
}