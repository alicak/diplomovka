package sk.upjs.ics.diplomovka.base;

public interface ChromosomeGenerator {
    int generateGene(int position);

    Chromosome generateChromosome();
}
