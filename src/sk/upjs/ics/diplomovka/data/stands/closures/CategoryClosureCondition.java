package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class CategoryClosureCondition implements ClosureCondition {

    private List<Integer> flightCategories;

    public CategoryClosureCondition(List<Integer> flightCategories) {
        this.flightCategories = flightCategories;
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
