package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.StandTemporarilyClosedDisruptionDataModel;
import sk.upjs.ics.diplomovka.disruption.closures.TemporaryStandClosure;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

public class StandTemporarilyClosedDisruption implements Disruption {

    private int id;
    private StandsStorage standsStorage;
    private TemporaryStandClosure closure;

    public StandTemporarilyClosedDisruption(int standId, int start, int end, StandsStorage standsStorage, int id) {
        this.standsStorage = standsStorage;
        this.closure = new TemporaryStandClosure(standId, start, end, id);
        this.id = id;
    }

    public StandTemporarilyClosedDisruption(StandTemporarilyClosedDisruptionDataModel disruption, StandsStorage standsStorage) {
        this(disruption.getStandId(), disruption.getStart(), disruption.getEnd(), standsStorage, disruption.getId());
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.applyStandClosure(closure, standsStorage.getNumberById(closure.getStandId()));
    }

    @Override
    public void disruptStorage() {
        standsStorage.addClosure(closure);
    }

    @Override
    public void undisruptStorage() {
        standsStorage.removeClosure(closure.getId(), closure.getStandId());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("#")
                .append(id)
                .append(": Stand ")
                .append(closure.getStandId())
                .append(" closed from ")
                .append(Utils.minutesToTime(closure.getStart()))
                .append(" to ")
                .append(Utils.minutesToTime(closure.getEnd()))
                .append(".");
        return sb.toString();
    }
}
