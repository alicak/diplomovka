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

        int stand1 = Utils.randomInt(chromosome.getNoOfStands());
        int stand2 = Utils.randomInt(chromosome.getNoOfStands());

        int noOfFlights1 = chromosome.getNoOfFlights(stand1);
        if (noOfFlights1 == 0) // there are no flights at the gate
            return false;

        int flightIdx = Utils.randomInt(noOfFlights1);
        int flight = chromosome.getGene(stand1, flightIdx);

        if (!chromosome.checkFlightFeasibility(flight, stand2))
            return false;

        chromosome.removeFlightFromGenes(stand1, flightIdx);
        chromosome.appendFlight(stand2, flight);

        return true;
    }
}
