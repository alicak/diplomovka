package sk.upjs.ics.diplomovka.disruption.closures.conditions;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.WingspanClosureConditionDataModel;
import sk.upjs.ics.diplomovka.storage.flights.Flight;

public class WingspanClosureCondition implements ClosureCondition {

    private double wingspan;

    public WingspanClosureCondition(double wingspan) {
        this.wingspan = wingspan;
    }

    public WingspanClosureCondition(WingspanClosureConditionDataModel condition) {
        this.wingspan = condition.getWingspan();
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return flight.getAircraft().getWingspan() <= wingspan;
    }

    @Override
    public String toString() {
        return "wingspan larger than " + wingspan + " meters";
    }
}
