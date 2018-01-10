package sk.upjs.ics.diplomovka.model1.mutations;

import sk.upjs.ics.diplomovka.model1.chromosomes.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class MoveBetweenGatesMutation extends AbsolutePositionMutation {
    public MoveBetweenGatesMutation(double probability) {
        this.probability = probability;
    }

    public void doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return;
        }

        int gate1 = Utils.randomInt(chromosome.getNoOfGates());
        int gate2 = Utils.randomInt(chromosome.getNoOfGates());

        int flightIdx = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flight = chromosome.getGene(gate1, flightIdx);

        chromosome.removeFlight(gate1, flightIdx);
        chromosome.addNextFlight(gate2, flight);
    }
}
