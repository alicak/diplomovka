package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public class ConditionalStandClosure extends StandClosure {

    private ClosureCondition condition;

    public ConditionalStandClosure(int standId, int start, int end, ClosureCondition condition) {
        super(standId, start, end);
        this.condition = condition;
    }

    public boolean checkFlight(Flight flight, int currentStart, int currentEnd) {
        if(currentEnd <= getStart() || currentStart >= getEnd()) // flight is outside the closure
            return true;

        return condition.checkFlight(flight);
    }
}
