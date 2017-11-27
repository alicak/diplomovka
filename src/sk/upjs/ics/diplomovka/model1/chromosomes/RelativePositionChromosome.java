package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class RelativePositionChromosome extends Chromosome {
    private final int noOfGates;
    private final int noOfFlights;

    public RelativePositionChromosome(int gates, int flights) {
        this.noOfGates = gates;
        this.noOfFlights = flights;
    }

    public int getGene(int row, int column) {
        return getGene(row * noOfFlights + column);
    }

    public void setGene(int row, int column, int value) {
        setGene(getIndex(row, column), value);
    }

    public int getNoFlights() {
        return noOfFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }

    public int getIndex(int row, int column) {
        return row * noOfFlights + column;
    }
}
