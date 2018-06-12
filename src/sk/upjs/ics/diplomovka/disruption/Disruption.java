package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;

public interface Disruption {
    void disruptAssignment(Chromosome chromosome);

    void disruptStorage();

    void cancelDisruptionOnAssignment(Chromosome chromosome);

    int getId();
}
