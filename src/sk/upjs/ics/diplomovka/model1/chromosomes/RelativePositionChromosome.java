package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class RelativePositionChromosome extends Chromosome {
    private final int noOfGates;
    private final int noOfFlights;

    public RelativePositionChromosome(int gates, int flights) {
        this.noOfGates = gates;
        this.noOfFlights = flights;
    }

    public AbsolutePositionChromosome absolutePositionChromosome() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);

        // TODO

        return chromosome;
    }

    public int getGene(int row, int column) {
        return getGene(row * noOfFlights + column);
    }

    public int getNoFlights() {
        return noOfFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }
}
