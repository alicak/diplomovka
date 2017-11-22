package sk.upjs.ics.diplomovka.model1.crossovers;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;

import java.util.List;

public class RelativePositionCrossover extends CrossoverBase {
    public RelativePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        // TODO
        return null;
    }
}
