package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Set;

public abstract class TestDataGenerator {

    protected GeneralStorage storage;

    public TestDataGenerator(GeneralStorage storage) {
        this.storage = storage;
    }

    protected int chooseFromSet(Set<Integer> set) {
        Integer[] ids = set.toArray(new Integer[set.size()]);
        return ids[Utils.randomInt(ids.length)];
    }
}
