package sk.upjs.ics.diplomovka.base;

public interface ChromosomeGenerator {
    int generateGene(Chromosome chromosome, int position);

    Chromosome generateChromosome();
}
