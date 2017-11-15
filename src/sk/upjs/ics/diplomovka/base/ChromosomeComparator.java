package sk.upjs.ics.diplomovka.base;

import java.util.Comparator;

public class ChromosomeComparator implements Comparator<ChromosomeBase> {

    @Override
    public int compare(ChromosomeBase c1, ChromosomeBase c2) {
        if (c1.getFitness() == c2.getFitness())
            return 0;

        if (c1.getFitness() < c2.getFitness())
            return 1;

        return -1;
    }
}
