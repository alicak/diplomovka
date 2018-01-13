package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class MoveBetweenGatesMutation extends AbsolutePositionMutation {
    public MoveBetweenGatesMutation(double probability) {
        this.probability = probability;
    }

    public boolean doAbsoluteMutation(AbsolutePositionChromosome chromosome) {
        if (Math.random() > probability) {
            return false;
        }

        int gate1 = Utils.randomInt(chromosome.getNoOfGates());
        int gate2 = Utils.randomInt(chromosome.getNoOfGates());

        int flightIdx = Utils.randomInt(chromosome.getNoOfFlights(gate1));
        int flight = chromosome.getGene(gate1, flightIdx);

        if (!chromosome.checkFlightFeasibility(flight, gate2))
            return false;

        chromosome.removeFlightFromGenes(gate1, flightIdx);
        chromosome.addNextFlight(gate2, flight);

        return true;
    }
}
