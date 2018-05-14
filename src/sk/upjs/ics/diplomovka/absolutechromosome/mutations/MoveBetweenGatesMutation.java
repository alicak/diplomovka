package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class MoveBetweenGatesMutation extends AbsolutePositionMutation {
    public MoveBetweenGatesMutation(double probability) {
        this.probability = probability;
    }

    public boolean doAbsoluteMutation(Chromosome chromosome) {
        if (Math.random() > probability) {
            return false;
        }

        int gate1 = Utils.randomInt(chromosome.getNoOfGates());
        int gate2 = Utils.randomInt(chromosome.getNoOfGates());

        int noOfFlights1 = chromosome.getNoOfFlights(gate1);
        if (noOfFlights1 == 0) // there are no flights at the gate
            return false;

        int flightIdx = Utils.randomInt(noOfFlights1);
        int flight = chromosome.getGene(gate1, flightIdx);

        if (!chromosome.checkFlightFeasibility(flight, gate2))
            return false;

        chromosome.removeFlightFromGenes(gate1, flightIdx);
        chromosome.addNextFlight(gate2, flight);

        return true;
    }
}
