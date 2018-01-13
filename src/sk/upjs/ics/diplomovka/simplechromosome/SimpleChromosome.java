package sk.upjs.ics.diplomovka.simplechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.List;

public class SimpleChromosome extends Chromosome {
    public SimpleChromosome(List<Integer> genes) {
        super(genes);
    }

    @Override
    public void removeFlight(int flight) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void removeGate(int gate) {
        throw new UnsupportedOperationException(); // TODO
    }
}
