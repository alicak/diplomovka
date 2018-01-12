package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapSuccessiveFlightsMutation extends AbsolutePositionMutation {
    public SwapSuccessiveFlightsMutation(double probability) {
        this.probability = probability;
    }

    public boolean doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return false;
        }

        int gate = Utils.randomInt(chromosome.getNoOfGates());

        if (chromosome.getNoOfFlights(gate) <= 1) {
            return false;
        }

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(gate));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(gate);

        int flight1 = chromosome.getGene(gate, flightIdx1);
        int flight2 = chromosome.getGene(gate, flightIdx2);

        chromosome.setGene(gate, flightIdx1, flight2);
        chromosome.setGene(gate, flightIdx2, flight1);

        return true;
    }
}
