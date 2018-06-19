package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.stands.Stand;
import sk.upjs.ics.diplomovka.storage.stands.closures.ConditionalStandClosure;

public class FeasibilityChecker extends FeasibilityCheckerBase {
    public FeasibilityChecker(GeneralStorage storage) {
        super(storage);
    }

    @Override
    public boolean checkChromosomeFeasibility(Chromosome chromosome) {
        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                int flightNo = chromosome.getGene(s, f);
                if (!checkFlightFeasibility(flightNo, s) || !checkConditionalClosures(f, s, chromosome)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int standNo) {
        Stand stand = standsStorage.getStandByNumber(standNo);
        return stand.checkFlight(flightStorage.getFlight(flightValue));
    }

    public boolean checkConditionalClosures(int flightIdx, int standNo, Chromosome chromosome) {
        Flight flight = flightStorage.getFlight(chromosome.getGene(standNo, flightIdx));

        for (ConditionalStandClosure closure : standsStorage.getConditionalClosuresForStand(standNo)) {
            if (!closure.checkFlight(flight, chromosome.getCurrentFlightStart(standNo, flightIdx), chromosome.getCurrentFlightEnd(standNo, flightIdx)))
                return false;
        }
        return true;
    }
}
