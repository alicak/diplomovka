package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelativePositionChromosome extends Chromosome {
    private final int noOfGates;
    private final int noOfFlights;

    public RelativePositionChromosome(int gates, int flights) {
        this.noOfGates = gates;
        this.noOfFlights = flights;
    }

    public AbsolutePositionChromosome absolutePositionChromosome() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);

        Integer[] genesArray = new Integer[noOfGates * noOfFlights];
        Arrays.fill(genesArray, 0);
        List<Integer> genesList = Arrays.asList(genesArray);

        List<List<Integer>> flightsForGates = new ArrayList<>();
        int[] firstFlights = new int[noOfGates];

        // initialize list of lists
        for (int g = 0; g < noOfGates; g++) {
            List<Integer> flightsForGate = new ArrayList<>();
            flightsForGates.add(flightsForGate);
        }

        // populate list for each gate
        for (int f = 0; f < noOfFlights; f++) {
            int nextGate = getGene(noOfFlights + 1, f);
            if (getGene(f, f) == 1) { // find first flight for gate
                firstFlights[nextGate] = f;
            } else {
                flightsForGates.get(nextGate).add(f);
            }
        }

        for (int g = 0; g < noOfGates; g++) {

            int currentFlight = firstFlights[g];
            int gate = getGene(currentFlight, noOfFlights + 1);
            int noOfFlightsOnGate = flightsForGates.get(gate).size();

            // loop through flight rank in queue
            for (int f = 0; f < noOfFlightsOnGate; f++) {

                // loop through potential successors
                for (Integer flightNo: flightsForGates.get(g)) {
                    if (flightNo == currentFlight) // don't consider first flight
                        continue;
                    else if (getGene(currentFlight, flightNo) == 1) { // we found the successor
                        genesList.set(chromosome.getIndex(g, f), flightNo);
                        currentFlight = flightNo;
                    }
                }
            }
        }

        chromosome.setGenes(genesList);
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

    protected int getIndex(int row, int column) {
        return row * noOfFlights + column;
    }
}
