package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.Arrays;
import java.util.List;

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

    public RelativePositionChromosome relativePositionChromosome() {
        RelativePositionChromosome chromosome = new RelativePositionChromosome(noOfGates, maxNoFlights);

        Integer[] genesArray = new Integer[maxNoFlights*maxNoFlights + maxNoFlights];
        Arrays.fill(genesArray, 0);
        List<Integer> genesList = Arrays.asList(genesArray);

        for (int g = 0; g < noOfGates; g++) {
            int flightNo = getGene(g,1);
            genesList.set(chromosome.getIndex(flightNo,flightNo), 1);
            genesList.set(chromosome.getIndex(maxNoFlights + 1, flightNo), g);

            for (int f = 0; f < noOfFlights[g] - 1; f++) {
                int flight1 = getGene(g, f);
                int flight2 = getGene(g, f+1);
                genesList.set(chromosome.getIndex(flight1, flight2), 1);
                genesList.set(chromosome.getIndex(maxNoFlights + 1, flight2), g);
            }
        }

        chromosome.setGenes(genesList);
        return chromosome;
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
