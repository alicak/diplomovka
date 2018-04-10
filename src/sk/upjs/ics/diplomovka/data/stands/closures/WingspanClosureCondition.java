package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public class WingspanClosureCondition implements ClosureCondition {

    private int wingspan;

    public WingspanClosureCondition(int wingspan) {
        this.wingspan = wingspan;
    }

    @Override
    public boolean checkFlight(Flight flight) {
        return flight.getAircraft().getWingspan() <= wingspan;
    }
}
