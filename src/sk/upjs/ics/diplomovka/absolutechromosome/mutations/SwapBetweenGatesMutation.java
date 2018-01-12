package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapBetweenGatesMutation extends AbsolutePositionMutation {
    public SwapBetweenGatesMutation(double probability) {
        this.probability = probability;
    }

    public void doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return;
        }

        int gate1 = Utils.randomInt(chromosome.getNoOfGates());
        int gate2 = Utils.randomInt(chromosome.getNoOfGates());

        if (chromosome.getNoOfFlights(gate1) == 0 || chromosome.getNoOfFlights(gate2) == 0) {
            return;
        }

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(gate2);

        int flight1 = chromosome.getGene(gate1, flightIdx1);
        int flight2 = chromosome.getGene(gate2, flightIdx2);

        if(!chromosome.checkFlightFeasibility(flight1, gate2) || !chromosome.checkFlightFeasibility(flight2, gate1))
            return;

        chromosome.setGene(gate1, flightIdx1, flight2);
        chromosome.setGene(gate2, flightIdx2, flight1);
    }
}
