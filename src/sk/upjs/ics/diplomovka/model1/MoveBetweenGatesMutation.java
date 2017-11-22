package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.utils.Utils;

public class MoveBetweenGatesMutation extends AbsolutePositionMutation {
    public MoveBetweenGatesMutation(double probability) {
        super(probability);
    }

    public void doMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return;
        }

        int gate1 = Utils.randomInt(chromosome.getGates());
        int gate2 = Utils.randomInt(chromosome.getGates());

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flightIdx2 = chromosome.getNoOfFlights(gate2) + 1; // last flight idx + 1

        int flight = chromosome.getGene(gate1, flightIdx1);

        // remove flight, move next flights and add zero to the end
        chromosome.removeGene(gate1, flightIdx1);
        chromosome.addGene(gate1, chromosome.getNoOfFlights(gate1), 0);

        chromosome.setGene(gate2, flightIdx2, flight);
    }
}
