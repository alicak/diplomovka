package sk.upjs.ics.diplomovka.model1.chromosomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbsoluteRelativeChromosomeConverter {

    public static RelativePositionChromosome toRelativePositionChromosome(AbsolutePositionChromosome absChromosome) {
        int noOfGates = absChromosome.getNoOfGates();
        int maxNoFlights = absChromosome.getMaxNoFlights();
        RelativePositionChromosome chromosome = new RelativePositionChromosome(noOfGates, maxNoFlights);

        Integer[] genesArray = new Integer[maxNoFlights*maxNoFlights + maxNoFlights];
        Arrays.fill(genesArray, 0);
        List<Integer> genesList = Arrays.asList(genesArray);

        for (int g = 0; g < noOfGates; g++) {
            int flightNo = absChromosome.getGene(g,1);
            genesList.set(chromosome.getIndex(flightNo,flightNo), 1);
            genesList.set(chromosome.getIndex(maxNoFlights + 1, flightNo), g);

            for (int f = 0; f < absChromosome.getNoOfFlights(g) - 1; f++) {
                int flight1 = absChromosome.getGene(g, f);
                int flight2 = absChromosome.getGene(g, f+1);
                genesList.set(chromosome.getIndex(flight1, flight2), 1);
                genesList.set(chromosome.getIndex(maxNoFlights + 1, flight2), g);
            }
        }

        chromosome.setGenes(genesList);
        return chromosome;
    }

    public static AbsolutePositionChromosome toAbsolutePositionChromosome(RelativePositionChromosome relChromosome) {
        int noOfGates = relChromosome.getNoOfGates();
        int noOfFlights = relChromosome.getNoFlights();
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
            int nextGate = relChromosome.getGene(noOfFlights + 1, f);
            if (relChromosome.getGene(f, f) == 1) { // find first flight for gate
                firstFlights[nextGate] = f;
            } else {
                flightsForGates.get(nextGate).add(f);
            }
        }

        for (int g = 0; g < noOfGates; g++) {

            int currentFlight = firstFlights[g];
            int gate = relChromosome.getGene(currentFlight, noOfFlights + 1);
            int noOfFlightsOnGate = flightsForGates.get(gate).size();

            // loop through flight rank in queue
            for (int f = 0; f < noOfFlightsOnGate; f++) {

                // loop through potential successors
                for (Integer flightNo: flightsForGates.get(g)) {
                    if (flightNo == currentFlight) // don't consider first flight
                        continue;
                    else if (relChromosome.getGene(currentFlight, flightNo) == 1) { // we found the successor
                        genesList.set(chromosome.getIndex(g, f), flightNo);
                        currentFlight = flightNo;
                    }
                }
            }
        }

        chromosome.setGenes(genesList);
        return chromosome;
    }
}
