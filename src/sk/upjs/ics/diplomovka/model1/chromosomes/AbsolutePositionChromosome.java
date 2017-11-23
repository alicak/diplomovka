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

    public void setGene(int gate, int flight, int flightValue) {
        setGene(getIndex(gate, flight), flightValue);
        if (flight > noOfFlights[gate]) {
            noOfFlights[gate]++;
        }
    }

    protected void incrementNoOfFlights(int gate) {
        noOfFlights[gate]++;
    }

    public int getNoOfFlights(int gate) {
        return noOfFlights[gate];
    }

    public void addGene(int gate, int flight, int value) {
        getGenes().add(getIndex(gate, flight), value);
    }

    public void removeGene(int gate, int flight) {
        getGenes().remove(getIndex(gate, flight));
    }

    public int getMaxNoFlights() {
        return maxNoFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }

    protected int getIndex(int gate, int flight) {
        return gate * maxNoFlights + flight;
    }
}
