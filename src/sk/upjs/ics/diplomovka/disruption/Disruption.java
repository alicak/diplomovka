package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;

public interface Disruption {
    void changeAssignment(Chromosome chromosome);
}
