package sk.upjs.ics.diplomovka.model1.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class RelativePositionChromosome extends Chromosome {
    private int size;

    public RelativePositionChromosome(int size) {
        this.size = size;
    }

    public int getGene(int row, int column) {
        return getGene(row * size + column);
    }

    public int getSize() {
        return size;
    }
}
