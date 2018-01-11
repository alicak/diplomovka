package sk.upjs.ics.diplomovka.base;

import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;

import java.util.List;

public abstract class FitnessFunctionBase {

    protected FlightStorage flightStorage;

    public FitnessFunctionBase(FlightStorage flightStorage) {
        this.flightStorage = flightStorage;
    }

    public abstract double calculateFitness(Chromosome chromosome);
}