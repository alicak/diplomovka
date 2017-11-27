package sk.upjs.ics.diplomovka.model1.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.RelativePositionChromosome;

import java.util.*;

public class RelativePositionCrossover extends CrossoverBase {
    private int flights;

    public RelativePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        return doRelativeCrossover((RelativePositionChromosome) chromosome1, (RelativePositionChromosome) chromosome2);
    }

    private List<Chromosome> doRelativeCrossover(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2) {
        flights = chromosome1.getNoFlights();

        RelativePositionChromosome newChromosome = locateCommonGenes(chromosome1, chromosome2);
        assignGates(chromosome1, chromosome2, newChromosome);
        RelativePositionChromosome c4 = indicateInfeasibleGenes(newChromosome);
        produceOffspring(chromosome1, chromosome2, newChromosome, c4);

        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(newChromosome);
        return offspring;
    }

    // step 1
    private RelativePositionChromosome locateCommonGenes(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2) {
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
        for (int j = 0; j < flights; j++) {
            if (result.getGene(flights, j) != 0) {
                continue;
            }

            if (Math.random() < 0.5) {
                result.setGene(flights, j, chromosome1.getGene(flights, j));
            } else {
                result.setGene(flights, j, chromosome2.getGene(flights, j));
            }
        }

        // every gate must have first dwelling
        Set<Integer> gatesWithStart = new HashSet<>();
        for (int i = 0; i < flights; i++) {
            if (result.getGene(i, i) == 1)
                gatesWithStart.add(result.getGene(flights, i));
        }

        for (int i = 0; i < flights; i++) {
            if (result.getGene(i, i) == 0 && (chromosome1.getGene(i, i) == 1 || chromosome2.getGene(i, i) == 1)) {
                int gate = result.getGene(flights, i);
                if (gatesWithStart.contains(gate))
                    continue;
                else {
                    result.setGene(i, i, 1); // simplified version - we don't choose c1 or c2, just set it
                    gatesWithStart.add(gate);
                }
            }
        }
    }

    // step 3
    private RelativePositionChromosome indicateInfeasibleGenes(RelativePositionChromosome c3) {
        RelativePositionChromosome c4 = copyChromosome(c3);

        for (int i = 0; i < flights; i++) {
            int gene = c3.getGene(i, i);

            if (gene == 0) {
                c4.setGene(i, i, -1);
            } else if (gene == 1) {

                for (int m = 0; m < flights; m++) {
                    if (c3.getGene(m, i) == 0) {
                        c4.setGene(m, i, -1);
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

                if (c3.getGene(i, j) == 1) {

                    for (int m = 0; m < flights; m++) {

                        if (c3.getGene(m, j) == 0) {
                            c4.setGene(m, j, -1);
                        }

                        if (c3.getGene(i, m) == 0) {
                            c4.setGene(i, m, -1);
                        }
                    }

                }
            }
        }

        return c4;
    }

    // step 4
    private void produceOffspring(RelativePositionChromosome c1,
                                  RelativePositionChromosome c2,
                                  RelativePositionChromosome c3,
                                  RelativePositionChromosome c4) {

        while (sumOfMatrix(c3) < flights) {
            int j = chooseJ(c3);
            int i3 = chooseI3(c1, c2, c3, c4, j);
            setC3andC4(c3, c4, i3, j);
        }
    }

    // step 4.1
    private int chooseJ(RelativePositionChromosome c3) {
        List<Integer> candidateColumns = new ArrayList<>();
        for (int j = 0; j < c3.getNoFlights(); j++) {
            if (sumOfColumn(c3, j) == 0)
                candidateColumns.add(j);
        }

        int j = (int) (Math.random() * candidateColumns.size());
        return candidateColumns.get(j);
    }

    // step 4.2
    private int chooseI3(RelativePositionChromosome c1,
                         RelativePositionChromosome c2,
                         RelativePositionChromosome c3,
                         RelativePositionChromosome c4,
                         int j) {

        int i3 = -1;

        for (int i1 = 0; i1 < flights; i1++) {
            for (int i2 = 0; i2 < flights; i2++) {

                if (c1.getGene(i1, j) == 1 && c2.getGene(i2, j) == 1) {

                    boolean cond1 = c3.getGene(flights, i1) == c3.getGene(flights, i2);
                    boolean cond2 = c3.getGene(flights, i1) == c3.getGene(flights, j);
                    boolean cond3 = c3.getGene(flights, i2) == c3.getGene(flights, j);
                    boolean cond4 = c4.getGene(i1, j) == 0;
                    boolean cond5 = c4.getGene(i2, j) == 0;

                    if (cond1 && cond2 && cond3 && cond4 && cond5) {
                        i3 = (Math.random() < 0.5) ? i1 : i2;
                    } else if (cond2 && cond4) {
                        i3 = i1;
                    } else if (cond3 && cond5) {
                        i3 = i2;
                    } else {
                        List<Integer> candidates = new ArrayList<>();
                        for (int i = 0; i < flights; i++) {
                            if (i == j)
                                continue;
                            if (c3.getGene(flights, i) == c3.getGene(flights, j) && c4.getGene(i, j) == 0)
                                candidates.add(i);
                        }
                        int idx = (int) (Math.random() * candidates.size());
                        i3 = candidates.get(idx);
                    }
                }
            }
        }

        if (i3 == -1)
            throw new IllegalStateException("i3 must be set to 0 or 1.");

        return i3;
    }

    // step 4.3
    private void setC3andC4(RelativePositionChromosome c3, RelativePositionChromosome c4, int i3, int j) {
        c3.setGene(i3, j, 1);
        c4.setGene(i3, j, 1);
        for (int m = 0; m < c4.getNoFlights(); m++) {
            c4.setGene(m, i3, -1);
            c4.setGene(i3, m, -1);
        }
    }

    private RelativePositionChromosome copyChromosome(RelativePositionChromosome chromosome) {
        RelativePositionChromosome copy = new RelativePositionChromosome(chromosome.getNoOfGates(), chromosome.getNoFlights());
        List<Integer> newGenes = new ArrayList<>();

        List<Integer> genes = chromosome.getGenes();
        for (int i = 0; i < genes.size(); i++) {
            newGenes.add(genes.get(i));
        }

        copy.setGenes(newGenes);

        return copy;
    }

    private int sumOfMatrix(RelativePositionChromosome chromosome) {
        int sum = 0;

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                if (chromosome.getGene(i, j) == 1)
                    sum++;
            }

        }

        return sum;
    }

    private int sumOfColumn(RelativePositionChromosome chromosome, int column) {
        int sum = 0;

        for (int i = 0; i < flights; i++) {
            if (chromosome.getGene(i, column) == 1)
                sum++;
        }

        return sum;
    }
}
