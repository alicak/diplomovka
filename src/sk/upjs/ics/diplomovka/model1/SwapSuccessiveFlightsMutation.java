package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapSuccessiveFlightsMutation extends RelativePositionMutation
{
    public SwapSuccessiveFlightsMutation(double probability) {
        super(probability);
    }

    public void doMutation(AbsolutePositionChromosome chromosome) {
        if(Math.random() > probability) {
            return;
        }

        int gate = Utils.randomInt(chromosome.getGates());
        int flightIdx1 = Utils.randomInt(chromosome.getFlights());
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getFlights();

        int flight1 = chromosome.getGene(gate, flightIdx1);
        int flight2 = chromosome.getGene(gate, flightIdx2);

        chromosome.setGene(gate, flightIdx1, flight2);
        chromosome.setGene(gate, flightIdx2, flight1);
    }
}
