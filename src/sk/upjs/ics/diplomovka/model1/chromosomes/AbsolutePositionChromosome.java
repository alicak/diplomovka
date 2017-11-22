package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class AbsolutePositionChromosome extends Chromosome {
    private final int noOfGates;
    private int[] noOfFlights; // number of flights per gate
    private final int maxNoFlights;

    public AbsolutePositionChromosome(int noOfGates, int maxNoFlights) {
        this.noOfGates = noOfGates;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[noOfGates];
    }

    public int getGene(int gate, int flight) {
        return getGene(gate * maxNoFlights + flight);
    }

    public void setGene(int gate, int flightIdx, int flight) {
        setGene(gate * maxNoFlights + flightIdx, flight);
        if (flightIdx > noOfFlights[gate]) {
            noOfFlights[gate]++;
        }
    }

    public RelativePositionChromosome relativePositionChromosome() {
        RelativePositionChromosome chromosome = new RelativePositionChromosome(noOfGates, maxNoFlights);

        // TODO

        return chromosome;
    }

    public int getNoOfFlights(int gate) {
        return noOfFlights[gate];
    }

    public void addGene(int gate, int flight, int value) {
        getGenes().add(gate * maxNoFlights + flight, value);
    }

    public void removeGene(int gate, int flight) {
        getGenes().remove(gate * maxNoFlights + flight);
    }

    public int getMaxNoFlights() {
        return maxNoFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }
}
