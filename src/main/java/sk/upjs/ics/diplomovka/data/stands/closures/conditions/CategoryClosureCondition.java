package sk.upjs.ics.diplomovka.data.stands.closures.conditions;

import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.models.data.closureconditions.CategoryClosureConditionDataModel;
import sk.upjs.ics.diplomovka.data.stands.closures.conditions.ClosureCondition;

import java.util.List;

public class CategoryClosureCondition implements ClosureCondition {

    private List<Integer> flightCategories;

    public CategoryClosureCondition(List<Integer> flightCategories) {
        this.flightCategories = flightCategories;
    }

    public CategoryClosureCondition(CategoryClosureConditionDataModel condition) {
        this.flightCategories = condition.getCategories();
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return flightCategories.contains(flight.getCategory());
    }

    @Override
    public String toString() {
        return "categories " + flightCategories;
    }
}
