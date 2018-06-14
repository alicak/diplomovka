package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public class WingspanClosureCondition implements ClosureCondition {

    private double wingspan;

    public WingspanClosureCondition(double wingspan) {
        this.wingspan = wingspan;
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
