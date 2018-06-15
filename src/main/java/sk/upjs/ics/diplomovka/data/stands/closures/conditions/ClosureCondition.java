package sk.upjs.ics.diplomovka.data.stands.closures.conditions;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public interface ClosureCondition {

    boolean checkFlight(Flight flight);
}
