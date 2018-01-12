package sk.upjs.ics.diplomovka.model1.crossovers;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.base.FeasibilityCheckerBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.AbsoluteRelativeChromosomeConverter;
import sk.upjs.ics.diplomovka.model1.chromosomes.RelativePositionChromosome;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.*;

public class RelativePositionCrossover extends CrossoverBase {
    private int flights;

    public RelativePositionCrossover(double probability, FeasibilityCheckerBase feasibilityChecker) {
        super(probability, feasibilityChecker);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        RelativePositionChromosome c1 = AbsoluteRelativeChromosomeConverter.toRelativePositionChromosome((AbsolutePositionChromosome) chromosome1);
        RelativePositionChromosome c2 = AbsoluteRelativeChromosomeConverter.toRelativePositionChromosome((AbsolutePositionChromosome) chromosome2);

        return doRelativeCrossover(c1, c2);
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

        Integer[] genesArray = new Integer[(flights + 1) * flights];
        Arrays.fill(genesArray, 0);
        result.setGenes(Arrays.asList(genesArray));

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                result.setGene(i, j, chromosome1.getGene(i, j) * chromosome2.getGene(i, j));
            }
        }

        for (int j = 0; j < flights; j++) {
            int value = 0;
            if (chromosome1.getGate(j) == chromosome2.getGate(j)) {
                value = chromosome1.getGate(j);
            }
            result.setGate(j, value);
        }

        return result;
    }

    // step 2
    private void assignGates(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2, RelativePositionChromosome result) {
        // every gate must have first dwelling
        Set<Integer> gatesWithoutStart = new HashSet<>();
        for (int g = 1; g <= result.getNoOfGates(); g++) {
            gatesWithoutStart.add(g);
        }
        for (int i = 0; i < flights; i++) {
            if (result.getGene(i, i) == 1)
                gatesWithoutStart.remove(result.getGate(i));
        }

        while (!gatesWithoutStart.isEmpty()) {
            for (int i = 0; i < flights; i++) {

                if (result.getGate(i) == 0) {
                    if (Math.random() < 0.5) {
                        result.setGate(i, chromosome1.getGate(i));
                    } else {
                        result.setGate(i, chromosome2.getGate(i));
                    }
                }

                if (!gatesWithoutStart.contains(result.getGate(i))) // the gate already has first dwelling
                    continue;

//                if (Math.random() < 0.5) {
//                    result.setGene(i, i, chromosome1.getGene(i, i));
//                } else {
//                    result.setGene(i, i, chromosome2.getGene(i, i));
//                }

                if (chromosome1.getGene(i, i) == 1 || chromosome2.getGene(i, i) == 1) {
                    result.setGene(i, i, 1);
                    gatesWithoutStart.remove(result.getGate(i)); // we assigned first dwelling
                }
            }
        }
    }

    // step 3
    private RelativePositionChromosome indicateInfeasibleGenes(RelativePositionChromosome c3) {
        RelativePositionChromosome c4 = c3.copy();

        for (int i = 0; i < flights; i++) {
            c4.setGene(i, i, -1);

            if (c3.getGene(i, i) == 1) {
                for (int m = 0; m < flights; m++) {
                    c4.setGene(m, i, -1);
                }
            }
        }

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                if (i == j)
                    continue;

                if (c3.getGene(i, j) == 1) {
                    for (int m = 0; m < flights; m++) {
                        c4.setGene(m, j, -1);
                        c4.setGene(i, m, -1);
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
        List<Integer> candidates = new ArrayList<>();
        for (int j = 0; j < c3.getNoFlights(); j++) {
            if (sumOfColumn(c3, j) == 0)
                candidates.add(j);
        }

        int idx = (int) (Math.random() * candidates.size());
        return candidates.get(idx);
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

                    boolean cond1 = c3.getGate(i1) == c3.getGate(j);
                    boolean cond2 = c3.getGate(i2) == c3.getGate(j);
                    boolean cond3 = c4.getGene(i1, j) == 0;
                    boolean cond4 = c4.getGene(i2, j) == 0;

                    if (cond1 && cond2 && cond3 && cond4) {
                        i3 = (Math.random() < 0.5) ? i1 : i2;
                    } else if (cond1 && cond3) {
                        i3 = i1;
                    } else if (cond2 && cond4) {
                        i3 = i2;
                    } else {
                        List<Integer> candidates = new ArrayList<>();
                        for (int i = 0; i < flights; i++) {
                            if (i == j)
                                continue;
                            if (c3.getGate(i) == c3.getGate(j) && c4.getGene(i, j) == 0)
                                candidates.add(i);
                        }
                        if (!candidates.isEmpty()) {
                            int idx = Utils.randomInt(candidates.size());
                            i3 = candidates.get(idx);
                        }
                    }
                }
            }
        }

        if (i3 == -1)
            throw new IllegalStateException("i3 must be positive or zero.");

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
