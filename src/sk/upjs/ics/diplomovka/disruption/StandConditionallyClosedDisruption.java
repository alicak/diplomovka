package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.closures.ClosureCondition;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

public class StandConditionallyClosedDisruption implements Disruption {

    private int id;
    private StandsStorage standsStorage;
    private ConditionalStandClosure closure;
    private int standId;

    public StandConditionallyClosedDisruption(int standId, int start, int end, ClosureCondition condition, StandsStorage standsStorage, int id) {
        this.standsStorage = standsStorage;
        this.closure = new ConditionalStandClosure(standId, start, end, condition, id);
        this.id = id;
        this.standId = standId;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.applyConditionalStandClosure(closure, standsStorage.getNumberById(closure.getStandId()));
    }

    @Override
    public void disruptStorage() {
        standsStorage.addConditionalClosure(closure);
    }

    @Override
    public void cancelDisruptionOnAssignment(Chromosome chromosome) {
        standsStorage.removeConditionalClosure(id, standId);
        //  TODO
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
