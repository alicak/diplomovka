package sk.upjs.ics.diplomovka.data.stands.closures;

import sk.upjs.ics.diplomovka.data.flights.Flight;

public class WeightClosureCondition implements ClosureCondition {
    private double weight;

    public WeightClosureCondition(double weight) {
        this.weight = weight;
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
