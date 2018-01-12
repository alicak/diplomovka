package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapSuccessiveFlightsMutation extends AbsolutePositionMutation {
    public SwapSuccessiveFlightsMutation(double probability, FeasibilityCheckerBase feasibilityChecker) {
        this.probability = probability;
        this.feasibilityChecker = feasibilityChecker; // but we don't need to check feasibility yet
    }

    public void doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return;
        }

        int gate = Utils.randomInt(chromosome.getNoOfGates());

        if (chromosome.getNoOfFlights(gate) <= 1) {
            return;
        }

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(gate));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(gate);

        int flight1 = chromosome.getGene(gate, flightIdx1);
        int flight2 = chromosome.getGene(gate, flightIdx2);

        chromosome.setGene(gate, flightIdx1, flight2);
        chromosome.setGene(gate, flightIdx2, flight1);
    }
}
