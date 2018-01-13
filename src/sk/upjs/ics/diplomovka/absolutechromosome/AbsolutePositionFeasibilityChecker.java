package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class AbsolutePositionFeasibilityChecker extends FeasibilityCheckerBase {
    public AbsolutePositionFeasibilityChecker(StandsStorage standsStorage, FlightStorage flightStorage) {
        super(standsStorage, flightStorage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        AbsolutePositionChromosome c = (AbsolutePositionChromosome) chromosome;

        for (int g = 0; g < c.getNoOfGates(); g++) {
            for (int f = 0; f < c.getNoOfFlights(g); f++) {
                int flightNo = c.getGene(g, f);
                if (!checkFlightFeasibility(flightNo, g)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int gate) {
        AircraftStand stand = standsStorage.getStandByNumber(gate);
        return stand.checkFlight(flightStorage.getFlightByNumber(flightValue));
    }
}
