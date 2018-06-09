package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;

public class AbsolutePositionFeasibilityChecker extends FeasibilityCheckerBase {
    public AbsolutePositionFeasibilityChecker(GeneralStorage storage) {
        super(storage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                int flightNo = chromosome.getGene(g, f);
                if (!checkFlightFeasibility(flightNo, g) || !checkConditionalClosures(f, g, chromosome)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int gate) {
        AircraftStand stand = standsStorage.getStandByNumber(gate);
        return stand.checkFlight(flightStorage.getFlight(flightValue));
    }

    public boolean checkConditionalClosures(int flightIdx, int gate, Chromosome chromosome) {
        Flight flight = flightStorage.getFlight(chromosome.getGene(gate, flightIdx));

        for (ConditionalStandClosure closure: standsStorage.getConditionalClosuresForStand(gate)) {
            if(!closure.checkFlight(flight, chromosome.getCurrentFlightStart(gate, flightIdx), chromosome.getCurrentFlightEnd(gate, flightIdx)))
                return false;
        }
        return true;
    }
}
