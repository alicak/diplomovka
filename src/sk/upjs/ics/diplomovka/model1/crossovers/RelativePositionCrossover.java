package sk.upjs.ics.diplomovka.model1.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.model1.chromosomes.RelativePositionChromosome;

import java.util.ArrayList;
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
        assignGates(newChromosome);
        indicateInfeasibleGenes(newChromosome);
        produceOffspring(newChromosome);
        List<Chromosome> offspring = new ArrayList<>();
        offspring.add(newChromosome);
        return offspring;
    }

    // step 1
    private RelativePositionChromosome locateCommonGenes(RelativePositionChromosome chromosome1, RelativePositionChromosome chromosome2) {
        List<Integer> genes = new ArrayList<>();
        int flights = chromosome1.getNoFlights();

        RelativePositionChromosome result = new RelativePositionChromosome(chromosome1.getNoOfGates(), flights);

        for (int i = 0; i < flights; i++) {
            for (int j = 0; j < flights; j++) {
                int idx = result.getIndex(i, j);
                genes.set(idx, chromosome1.getGene(i, j) * chromosome2.getGene(i, j));
            }
        }

        int idx = result.getIndex(flights, 0);
        for (int j = idx; j < idx + flights; j++) {
            int value = 0;
            if (chromosome1.getGene(j) == chromosome2.getGene(j)) {
                value = chromosome1.getGene(j);
            }
            genes.set(j, value);
        }

        result.setGenes(genes);
        return result;
    }

    // step 2
    private void assignGates(RelativePositionChromosome chromosome) {
        // TODO
    }

    // step 3
    private void indicateInfeasibleGenes(RelativePositionChromosome chromosome) {
        // TODO
    }

    // step 4
    private void produceOffspring(RelativePositionChromosome chromosome) {
        // TODO
    }
}
