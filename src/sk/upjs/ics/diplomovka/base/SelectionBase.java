package sk.upjs.ics.diplomovka.base;

import java.util.List;

public abstract class SelectionBase {

    public abstract List<ChromosomeBase> select(List<ChromosomeBase> population, int numberOfChromosomes);

}
