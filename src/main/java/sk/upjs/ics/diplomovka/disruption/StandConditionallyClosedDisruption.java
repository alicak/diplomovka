package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.StandConditionallyClosedDisruptionDataModel;
import sk.upjs.ics.diplomovka.data.parser.Types;
import sk.upjs.ics.diplomovka.disruption.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.ClosureCondition;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

public class StandConditionallyClosedDisruption implements Disruption {

    private int id;
    private StandsStorage standsStorage;
    private ConditionalStandClosure closure;
    private GeneralStorage storage;

    public StandConditionallyClosedDisruption(int standId, int start, int end, ClosureCondition condition, GeneralStorage storage, int id) {
        this.standsStorage = storage.getStandsStorage();
        this.storage = storage;
        this.closure = new ConditionalStandClosure(standId, start, end, condition, id);
        this.id = id;
    }

    public StandConditionallyClosedDisruption(StandConditionallyClosedDisruptionDataModel disruption, GeneralStorage storage) {
        this(disruption.getStandId(), disruption.getStart(), disruption.getEnd(), Types.getConditionFromModel(disruption.getCondition()), storage, disruption.getId());
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.applyConditionalStandClosure(closure, standsStorage.getNumberById(closure.getStandId()), storage.getFlightStorage());
    }

    @Override
    public void disruptStorage() {
        standsStorage.addConditionalClosure(closure);
    }

    @Override
    public void undisruptStorage() {
        standsStorage.removeConditionalClosure(closure.getId(), closure.getStandId());
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
                .append(" closed for: ")
                .append(closure.getCondition())
                .append(".");
        return sb.toString();
    }
}
