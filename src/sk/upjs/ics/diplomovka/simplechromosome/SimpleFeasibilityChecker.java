package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class SimpleFeasibilityChecker extends FeasibilityCheckerBase {
    public SimpleFeasibilityChecker(StandsStorage standsStorage, FlightStorage flightStorage) {
        super(standsStorage, flightStorage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        SimpleChromosome c = (SimpleChromosome) chromosome;
        for (int f = 0; f < c.getLength(); f++) {
            if (!checkFlightFeasibility(f, c.getGene(f)))
                return false;
        }
        return true;
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int gate) {
        AircraftStand stand = standsStorage.getStandByNumber(gate);
        return stand.checkFlight(flightStorage.getFlightByNumber(flightValue));
    }
}
