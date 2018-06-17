package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Set;

public abstract class Generator {

    protected GeneralStorage storage;

    public Generator(GeneralStorage storage) {
        this.storage = storage;
    }

    protected int chooseFromSet(Set<Integer> set) {
        Integer[] ids = {};
        set.toArray(ids);
        return ids[Utils.randomInt(ids.length)];
    }
}
