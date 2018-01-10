package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbsoluteRelativeChromosomeConverter {

    public static RelativePositionChromosome toRelativePositionChromosome(AbsolutePositionChromosome absChromosome) {
        int noOfGates = absChromosome.getNoOfGates();
        int maxNoFlights = absChromosome.getMaxNoFlights();
        RelativePositionChromosome chromosome = new RelativePositionChromosome(noOfGates, maxNoFlights);

        Integer[] genesArray = new Integer[maxNoFlights * maxNoFlights + +maxNoFlights];
        Arrays.fill(genesArray, 0);
        List<Integer> genesList = Arrays.asList(genesArray);
        chromosome.setGenes(genesList);

        for (int g = 0; g < noOfGates; g++) {
            int flightNo = absChromosome.getGene(g, 0);
            chromosome.setGene(flightNo, flightNo, 1);
            chromosome.setGene(maxNoFlights, flightNo, g + 1); // we number gates starting with 1

            for (int f = 0; f < absChromosome.getNoOfFlights(g) - 1; f++) {
                int flight1 = absChromosome.getGene(g, f);
                int flight2 = absChromosome.getGene(g, f + 1);
                chromosome.setGene(flight1, flight2, 1);
                chromosome.setGene(maxNoFlights, flight2, g + 1);
            }
        }

        return chromosome;
    }

    public static AbsolutePositionChromosome toAbsolutePositionChromosome(RelativePositionChromosome relChromosome) {
        int noOfGates = relChromosome.getNoOfGates();
        int noOfFlights = relChromosome.getNoFlights();
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, noOfFlights);

        Integer[] genesArray = new Integer[noOfGates * noOfFlights];
        Arrays.fill(genesArray, AbsolutePositionChromosome.EMPTY_GENE);
        chromosome.setGenes(Arrays.asList(genesArray));

        List<List<Integer>> flightsForGates = new ArrayList<>();
        int[] firstFlights = new int[noOfGates];

        // initialize list of lists
        for (int g = 0; g < noOfGates; g++) {
            List<Integer> flightsForGate = new ArrayList<>();
            flightsForGates.add(flightsForGate);
        }

        // populate list for each gate
        for (int f = 0; f < noOfFlights; f++) {
            int nextGate = relChromosome.getGene(noOfFlights + 1, f) - 1;
            if (relChromosome.getGene(f, f) == 1) { // find first flight for gate
                firstFlights[nextGate] = f;
            } else {
                flightsForGates.get(nextGate).add(f);
            }
        }

        for (int g = 0; g < noOfGates; g++) {

            int currentFlight = firstFlights[g];
            int gate = relChromosome.getGene(noOfFlights, currentFlight) - 1;
            int noOfFlightsOnGate = flightsForGates.get(gate).size();

            // loop through flight rank in queue
            for (int f = 0; f < noOfFlightsOnGate; f++) {

                // loop through potential successors
                for (Integer flightNo : flightsForGates.get(g)) {
                    if (flightNo == currentFlight) // don't consider first flight
                        continue;
                    else if (relChromosome.getGene(currentFlight, flightNo) == 1) { // we found the successor
                        chromosome.addNextFlight(g, flightNo);
                        currentFlight = flightNo;
                    }
                }
            }
        }

        return chromosome;
    }
}
