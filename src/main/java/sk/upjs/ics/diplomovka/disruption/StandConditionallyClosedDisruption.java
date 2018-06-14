package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.ClosureCondition;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;

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
