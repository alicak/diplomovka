package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class EngineTypeClosureCondition implements ClosureCondition {

    private List<Aircraft.EngineType> engineTypes;

    public EngineTypeClosureCondition(List<Aircraft.EngineType> engineTypes) {
        this.engineTypes = engineTypes;
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return engineTypes.contains(flight.getAircraft().getEngineType());
    }
}
