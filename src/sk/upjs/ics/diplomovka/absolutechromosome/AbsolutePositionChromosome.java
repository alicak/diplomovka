package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbsolutePositionChromosome extends Chromosome {
    public static int EMPTY_GENE = -1;

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
        if (flightValue != EMPTY_GENE && flight >= noOfFlights[gate]) {
            noOfFlights[gate]++;
        }
    }

    public void addNextFlight(int gate, int flightValue) {
        setGene(getIndex(gate, noOfFlights[gate]), flightValue);
        noOfFlights[gate]++;
        resetFitness();
    }

    public void removeFlight(int gate, int flight) {
        for (int f = flight; f < noOfFlights[gate]; f++) {
            setGene(gate, f, getGene(gate, f + 1));
        }
        noOfFlights[gate]--;
        resetFitness();
    }

    public int getNoOfFlights(int gate) {
        return noOfFlights[gate];
    }

    public int getMaxNoFlights() {
        return maxNoFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }

    public FlightPosition findPosition(int flightValue) {
        for (int g = 0; g < noOfGates; g++) {
            for (int f = 0; f < noOfFlights[g]; f++) {
                if (getGene(g, f) == flightValue)
                    return new FlightPosition(g, f);
            }
        }
        return new FlightPosition(-1, -1);
    }

    protected int getIndex(int gate, int flight) {
        return gate * maxNoFlights + flight;
    }

    private void setNoOfFlightsPerGate(int[] noOfFlights) {
        this.noOfFlights = noOfFlights;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int g = 0; g < noOfGates; g++) {
            result.append(g + ": ");
            for (int f = 0; f < maxNoFlights; f++) {
                result.append(getGene(g, f) + ", ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public AbsolutePositionChromosome copy() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, maxNoFlights);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);
        chromosome.setNoOfFlightsPerGate(Arrays.copyOf(noOfFlights, noOfFlights.length));

        return chromosome;
    }
}
