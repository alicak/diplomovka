package sk.upjs.ics.diplomovka.disruption.closures.conditions;

import sk.upjs.ics.diplomovka.storage.flights.Flight;

public interface ClosureCondition {

    boolean checkFlight(Flight flight);
}
