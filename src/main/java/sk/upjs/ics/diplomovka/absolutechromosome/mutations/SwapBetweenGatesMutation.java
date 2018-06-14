package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapBetweenGatesMutation extends AbsolutePositionMutation {
    public SwapBetweenGatesMutation(double probability) {
        this.probability = probability;
    }

    public boolean doAbsoluteMutation(Chromosome chromosome) {
        if (Math.random() > probability) {
            return false;
        }

        int stand1 = Utils.randomInt(chromosome.getNoOfStands());
        int stand2 = Utils.randomInt(chromosome.getNoOfStands());

        if (chromosome.getNoOfFlights(stand1) == 0 || chromosome.getNoOfFlights(stand2) == 0) {
            return false;
        }

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(stand1));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(stand2);

        int flight1 = chromosome.getGene(stand1, flightIdx1);
        int flight2 = chromosome.getGene(stand2, flightIdx2);

        if (!chromosome.checkFlightFeasibility(flight1, stand2) || !chromosome.checkFlightFeasibility(flight2, stand1))
            return false;

        chromosome.setGene(stand1, flightIdx1, flight2);
        chromosome.setGene(stand2, flightIdx2, flight1);

        return true;
    }
}
