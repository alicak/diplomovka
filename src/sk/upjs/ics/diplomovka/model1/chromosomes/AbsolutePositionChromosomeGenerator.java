package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.ChromosomeGenerator;

public class AbsolutePositionChromosomeGenerator implements ChromosomeGenerator {
    @Override
    public int generateGene(Chromosome chromosome, int position) {
        return 0;
    }

    @Override
    public Chromosome generateChromosome() {
        return null;
    }
}
