package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public interface ClosureCondition {

    boolean checkFlight(Flight flight);
}
