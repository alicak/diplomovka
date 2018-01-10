package sk.upjs.ics.diplomovka.operators.selection;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.SelectionBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingSelection extends SelectionBase {
    @Override
    public List<Chromosome> select(PopulationBase population, int numberOfChromosomes) {
        Collections.sort(population.get());
        List<Chromosome> result = new ArrayList<>();
        for (int c = 0; c < numberOfChromosomes; c++) {
            result.add(population.get(c));
        }
        return result;
    }

    @Override
    public Chromosome select(PopulationBase population) { // returns only the best chromosome
        Collections.sort(population.get());
        return population.get(0);
    }
}
