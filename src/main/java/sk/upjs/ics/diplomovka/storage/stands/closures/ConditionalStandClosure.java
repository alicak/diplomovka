package sk.upjs.ics.diplomovka.storage.stands.closures;

import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.stands.closures.conditions.ClosureCondition;

public class ConditionalStandClosure extends StandClosure {

    private ClosureCondition condition;

    public ConditionalStandClosure(int standId, int start, int end, ClosureCondition condition, int id) {
        super(standId, start, end, id);
        this.condition = condition;
    }

    public boolean checkFlight(Flight flight, int currentStart, int currentEnd) {
        if (currentEnd <= getStart() || currentStart >= getEnd()) // flight is outside the closure
            return true;

        return condition.checkFlight(flight);
    }

    public ClosureCondition getCondition() {
        return condition;
    }
}
