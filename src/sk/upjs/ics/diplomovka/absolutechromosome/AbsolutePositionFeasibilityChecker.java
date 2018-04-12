package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;

public class AbsolutePositionFeasibilityChecker extends FeasibilityCheckerBase {
    public AbsolutePositionFeasibilityChecker(GeneralStorage storage) {
        super(storage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        AbsolutePositionChromosome c = (AbsolutePositionChromosome) chromosome;

        for (int g = 0; g < c.getNoOfGates(); g++) {
            for (int f = 0; f < c.getNoOfFlights(g); f++) {
                int flightNo = c.getGene(g, f);
                if (!checkFlightFeasibility(flightNo, g) || !checkConditionalClosures(f, g, c)) {
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

    public boolean checkConditionalClosures(int flightIdx, int gate, AbsolutePositionChromosome chromosome) {
        Flight flight = flightStorage.getFlightByNumber(chromosome.getGene(gate, flightIdx));

        for (ConditionalStandClosure closure: standsStorage.getConditionalClosuresForStand(gate)) {
            if(!closure.checkFlight(flight, chromosome.getCurrentFlightStart(gate, flightIdx), chromosome.getCurrentFlightEnd(gate, flightIdx)))
                return false;
        }
        return true;
    }
}
