package sk.upjs.ics.diplomovka.operators.selection;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.base.SelectionBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingSelection extends SelectionBase {
    @Override
    public List<Chromosome> select(List<Chromosome> generation, int numberOfChromosomes) {
        Collections.sort(generation);
        List<Chromosome> result = new ArrayList<>();
        for (int c = 0; c < numberOfChromosomes; c++) {
            result.add(generation.get(c));
        }
        return result;
    }

    @Override
    public Chromosome select(List<Chromosome> generation) { // returns only the best chromosome
        Collections.sort(generation);
        return generation.get(0);
    }
}
