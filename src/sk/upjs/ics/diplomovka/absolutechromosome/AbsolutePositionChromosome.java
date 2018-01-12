package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbsolutePositionChromosome extends Chromosome {
    public static int EMPTY_GENE = -1;

    private int noOfGates;
    private int[] noOfFlights; // number of flights per gate
    private int maxNoFlights;
    private AbsolutePositionFeasibilityChecker feasibilityChecker;

    public AbsolutePositionChromosome(int noOfGates, int maxNoFlights) {
        this.noOfGates = noOfGates;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[noOfGates];
    }

    public int getGene(int gate, int flight) {
        return getGene(gate * maxNoFlights + flight);
    }

    public void setGene(int gate, int flight, int flightValue) {
        int oldValue = getGene(gate, flight);
        if (flightValue != EMPTY_GENE && oldValue == EMPTY_GENE) {
            noOfFlights[gate]++;
        }
        setGene(getIndex(gate, flight), flightValue);
        resetFitness();
    }

    public void addNextFlight(int gate, int flightValue) {
        setGene(getIndex(gate, noOfFlights[gate]), flightValue);
        noOfFlights[gate]++;
        resetFitness();
    }

    @Override
    public void removeFlight(int flightValue) {
        FlightPosition position = findPosition(flightValue);
        removeFlightFromGenes(position.getGate(), position.getFlight());
        for (int g = noOfGates - 1; g >= 0; g--) {
            getGenes().remove(g * (maxNoFlights - 1));
        }
        maxNoFlights--;
    }

    public void removeFlightFromGenes(int gate, int flight) {
        for (int f = flight; f < noOfFlights[gate]; f++) {
            setGene(gate, f, getGene(gate, f + 1));
        }
        noOfFlights[gate]--;
        resetFitness();
    }

    public void removeGate(int gate) {
        int nextGate = (gate + 1) % noOfGates;

        for (int i = 0; i < noOfFlights[gate]; i++) { // assign flights to remaining gates
            addNextFlight(nextGate, getGene(gate, i));
            nextGate = (nextGate + 1) % noOfGates;
            if (nextGate == gate) {
                nextGate = (nextGate + 1) % noOfGates;
            }
        }

        for (int i = getIndex(gate, 0); i < (noOfGates - 1) * maxNoFlights; i++) {
            setGene(i, getGene(i + maxNoFlights));
        }

        for (int i = gate; i < noOfGates - 1; i++) {
            noOfFlights[i] = noOfFlights[i + 1];
        }

        noOfFlights = Arrays.copyOf(noOfFlights, noOfFlights.length - 1);
        noOfGates--;
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
        return new FlightPosition(-1, -1); // flight was not found
    }

    protected int getIndex(int gate, int flight) {
        return gate * maxNoFlights + flight;
    }

    private void setNoOfFlightsPerGate(int[] noOfFlights) {
        this.noOfFlights = noOfFlights;
    }

    public void setFeasibilityChecker(AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.feasibilityChecker = feasibilityChecker;
    }

    public boolean checkFlightFeasibility(int flightValue, int gate) {
        return feasibilityChecker.checkFlightFeasibility(flightValue, gate);
    }

    public boolean checkFeasibility() {
        return feasibilityChecker.checkChromosomeFeasibility(this);
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
        chromosome.setFeasibilityChecker(feasibilityChecker);

        return chromosome;
    }
}
