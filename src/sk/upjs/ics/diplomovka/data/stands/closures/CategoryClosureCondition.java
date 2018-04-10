package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.List;

public class CategoryClosureCondition implements ClosureCondition {

    private List<Flight.FlightCategory> flightCategories;

    public CategoryClosureCondition(List<Flight.FlightCategory> flightCategories) {
        this.flightCategories = flightCategories;
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return flightCategories.contains(flight.getCategory());
    }
}
