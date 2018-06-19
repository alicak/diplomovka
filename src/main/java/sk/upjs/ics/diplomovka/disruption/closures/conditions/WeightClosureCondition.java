package sk.upjs.ics.diplomovka.disruption.closures.conditions;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.WeightClosureConditionDataModel;
import sk.upjs.ics.diplomovka.storage.flights.Flight;

public class WeightClosureCondition implements ClosureCondition {
    private double weight;

    public WeightClosureCondition(double weight) {
        this.weight = weight;
    }

    public WeightClosureCondition(WeightClosureConditionDataModel condition) {
        this.weight = condition.getWeight();
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return flight.getAircraft().getWeight() <= weight;
    }

    @Override
    public String toString() {
        return "weight larger than " + weight + " tones";
    }
}
