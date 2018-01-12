package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class MoveBetweenGatesMutation extends AbsolutePositionMutation {
    public MoveBetweenGatesMutation(double probability, FeasibilityCheckerBase feasibilityChecker) {
        this.probability = probability;
        this.feasibilityChecker = feasibilityChecker;
    }

    public void doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return;
        }

        int gate1 = Utils.randomInt(chromosome.getNoOfGates());
        int gate2 = Utils.randomInt(chromosome.getNoOfGates());

        int flightIdx = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flight = chromosome.getGene(gate1, flightIdx);

        if(!feasibilityChecker.checkFlightFeasibility(flight, gate2))
            return;

        chromosome.removeFlightFromGenes(gate1, flightIdx);
        chromosome.addNextFlight(gate2, flight);
    }
}
