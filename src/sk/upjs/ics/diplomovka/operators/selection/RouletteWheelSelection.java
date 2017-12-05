package sk.upjs.ics.diplomovka.operators.selection;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.SelectionBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouletteWheelSelection extends SelectionBase {

    @Override
    public List<Chromosome> select(PopulationBase population, int numberOfChromosomes) {
        List<Chromosome> selected = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {
            selected.add(select(population));
        }

        return selected;
    }


    @Override
    public Chromosome select(PopulationBase population) {
        double r = Math.random() * sumOfFitnesses(population);
        double current = 0;

        for (Chromosome c : population.get()) {
            current += c.getFitness();
            if(r <= current)
                return c;
        }

        return null; // this should never happen
    }

    private double sumOfFitnesses(PopulationBase population) {
        double result = 0;
        for (Chromosome chromosome : population.get()) {
            result += chromosome.getFitness();
        }
        return result;
    }
}
