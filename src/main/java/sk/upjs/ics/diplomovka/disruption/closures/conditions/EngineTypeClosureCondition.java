package sk.upjs.ics.diplomovka.disruption.closures.conditions;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.EngineTypeClosureConditionDataModel;
import sk.upjs.ics.diplomovka.storage.flights.Flight;

import java.util.List;

public class EngineTypeClosureCondition implements ClosureCondition {

    private List<Integer> engineTypes;

    public EngineTypeClosureCondition(List<Integer> engineTypes) {
        this.engineTypes = engineTypes;
    }

    public EngineTypeClosureCondition(EngineTypeClosureConditionDataModel condition) {
        this.engineTypes = condition.getEngineTypes();
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
