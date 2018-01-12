package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleFeasibilityChecker extends FeasibilityCheckerBase {
    public SimpleFeasibilityChecker(StandsStorage standsStorage, FlightStorage flightStorage) {
        super(standsStorage, flightStorage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int gate) {
        throw new UnsupportedOperationException(); // TODO
    }
}
