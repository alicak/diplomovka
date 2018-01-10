package sk.upjs.ics.diplomovka.selection;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.SelectionBase;

import java.util.ArrayList;
import java.util.List;

public class RouletteWheelSelection extends SelectionBase {

    @Override
    public List<Chromosome> select(List<Chromosome> generation, int numberOfChromosomes) {
        List<Chromosome> selected = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {
            selected.add(select(generation));
        }

        return selected;
    }


    @Override
    public Chromosome select(List<Chromosome> generation) {
        double r = Math.random() * sumOfFitnesses(generation);
        double current = 0;

        for (Chromosome c : generation) {
            current += c.getFitness();
            if (r <= current)
                return c;
        }

        throw new IllegalStateException("roulette wheel has to choose something.");
    }

    private double sumOfFitnesses(List<Chromosome> generation) {
        double result = 0;
        for (Chromosome chromosome : generation) {
            result += chromosome.getFitness();
        }
        return result;
    }
}
