package sk.upjs.ics.diplomovka.absolutechromosome.mutations;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

public class SwapSuccessiveFlightsMutation extends AbsolutePositionMutation {
    public SwapSuccessiveFlightsMutation(double probability) {
        this.probability = probability;
    }

    public boolean doAbsoluteMutation(Chromosome chromosome) {
        if (Math.random() > probability) {
            return false;
        }

        int stand = Utils.randomInt(chromosome.getNoOfStands());

        if (chromosome.getNoOfFlights(stand) <= 1) {
            return false;
        }

        int flightIdx1 = Utils.randomInt(chromosome.getNoOfFlights(stand));
        int flightIdx2 = (flightIdx1 + 1) % chromosome.getNoOfFlights(stand);

        int flight1 = chromosome.getGene(stand, flightIdx1);
        int flight2 = chromosome.getGene(stand, flightIdx2);

        chromosome.setGene(stand, flightIdx1, flight2);
        chromosome.setGene(stand, flightIdx2, flight1);

        return true;
    }
}
