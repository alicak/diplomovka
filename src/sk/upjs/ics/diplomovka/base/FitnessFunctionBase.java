package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

public abstract class FitnessFunctionBase {

    protected FlightStorage flightStorage;

    public FitnessFunctionBase(FlightStorage flightStorage) {
        this.flightStorage = flightStorage;
    }

    public abstract double calculateFitness(Chromosome chromosome);

    public abstract double calculateAndSetFitness(Chromosome chromosome);

}