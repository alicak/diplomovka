package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class RelativePositionUtils {

    public static int getGene(Chromosome chromosome, int matrixSize, int row, int column) {
        return chromosome.getGene(row * matrixSize + column);
    }
}
