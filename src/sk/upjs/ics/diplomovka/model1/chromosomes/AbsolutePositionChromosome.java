package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class AbsolutePositionChromosome extends Chromosome {
    private int gates;
    private int[] noOfFlights;
    private int maxNoFlights;

    public AbsolutePositionChromosome(int gates, int maxNoFlights) {
        this.gates = gates;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[gates];
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

    public int getGates() {
        return gates;
    }
}
