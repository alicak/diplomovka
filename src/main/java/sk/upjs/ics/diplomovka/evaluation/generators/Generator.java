package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;

public abstract class Generator {

    protected GeneralStorage storage;

    public Generator(GeneralStorage storage) {
        this.storage = storage;
    }
}
