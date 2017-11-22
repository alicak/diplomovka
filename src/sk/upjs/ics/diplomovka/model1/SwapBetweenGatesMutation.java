package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapBetweenGatesMutation extends AbsolutePositionMutation {
    public SwapBetweenGatesMutation(double probability) {
        super(probability);
    }

    public void doMutation(AbsolutePositionChromosome chromosome) {
        if(Math.random() > probability) {
            return;
        }

        int gate1 = Utils.randomInt(chromosome.getGates());
        int gate2 = Utils.randomInt(chromosome.getGates());

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(gate2);

        int flight1 = chromosome.getGene(gate1, flightIdx1);
        int flight2 = chromosome.getGene(gate2, flightIdx2);

        chromosome.setGene(gate1, flightIdx1, flight2);
        chromosome.setGene(gate2, flightIdx2, flight1);
    }
}
