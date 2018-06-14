package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class EngineTypeClosureCondition implements ClosureCondition {

    private List<Integer> engineTypes;

    public EngineTypeClosureCondition(List<Integer> engineTypes) {
        this.engineTypes = engineTypes;
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return engineTypes.contains(flight.getAircraft().getEngineType());
    }

    @Override
    public String toString() {
        return "engine types " + engineTypes;
    }
}
