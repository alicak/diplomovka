package sk.upjs.ics.diplomovka.model1.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.RelativePositionChromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RelativePositionCrossover extends CrossoverBase {
    public RelativePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        return doRelativeCrossover((RelativePositionChromosome) chromosome1, (RelativePositionChromosome) chromosome2);
    }

    private List<Chromosome> doRelativeCrossover(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2) {
        RelativePositionChromosome newChromosome = locateCommonGenes(chromosome1, chromosome2);
        assignGates(chromosome1, chromosome2, newChromosome);
        indicateInfeasibleGenes(newChromosome);
        produceOffspring(newChromosome);
        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(newChromosome);
        return offspring;
    }

    // step 1
    private RelativePositionChromosome locateCommonGenes(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2) {
        int flights = chromosome1.getNoFlights();
        RelativePositionChromosome result = new RelativePositionChromosome(chromosome1.getNoOfGates(), flights);

        Integer[] genesArray = new Integer[flights * flights + flights];
        Arrays.fill(genesArray, 0);
        result.setGenes(Arrays.asList(genesArray));

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                result.setGene(i, j, chromosome1.getGene(i, j) * chromosome2.getGene(i, j));
            }
        }

        for (int j = 0; j < flights; j++) {
            int value = 0;
            if (chromosome1.getGene(flights, j) == chromosome2.getGene(flights, j)) {
                value = chromosome1.getGene(flights, j);
            }
            result.setGene(flights, j, value);
        }


        return result;
    }

    // step 2
    private void assignGates(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2, RelativePositionChromosome result) {
        int flights = result.getNoFlights();

        for (int j = 0; j < flights; j++) {
            if (result.getGene(flights, j) == 0) {
                continue;
            }

            if (Math.random() < 0.5) {
                result.setGene(flights, j, chromosome1.getGene(flights, j));
            } else {
                result.setGene(flights, j, chromosome2.getGene(flights, j));
            }
        }

        // TODO: every gate must have first dwelling
    }

    // step 3
    private void indicateInfeasibleGenes(RelativePositionChromosome chromosome) {
        int flights = chromosome.getNoFlights();

        for (int i = 0; i < flights; i++) {
            int gene = chromosome.getGene(i, i);

            if (gene == 0) {
                chromosome.setGene(i, i, -1);
            } else if (gene == 1) {

                for (int m = 0; m < flights; m++) {
                    if (chromosome.getGene(m, i) == 0) {
                        chromosome.setGene(m, i, -1);
                    }
                }

            } else {
                throw new IllegalStateException("Value in gene can be only 0 or 1.");
            }
        }

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                if (i == j)
                    continue;

                if (chromosome.getGene(i, j) == 1) {

                    for (int m = 0; m < flights; m++) {

                        if (chromosome.getGene(m, j) == 0) {
                            chromosome.setGene(m, j, -1);
                        }

                        if (chromosome.getGene(i, m) == 0) {
                            chromosome.setGene(i, m, -1);
                        }
                    }

                }
            }
        }
    }

    // step 4
    private void produceOffspring(RelativePositionChromosome chromosome) {
        // TODO
    }
}
