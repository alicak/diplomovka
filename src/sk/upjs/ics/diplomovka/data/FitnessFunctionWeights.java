package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.Arrays;
import java.util.List;

public class FitnessFunctionWeights {

    private double reassignmentWeight = 1;
    private double passengerWeight = 1;
    private double flightPriorityWeight = 1;
    private double timeChangedWeight = 1;
    private List<Double> priorityValues = Arrays.asList(0.5, 1.0, 1.5);

    public double getReassignmentWeight() {
        return reassignmentWeight;
    }

    public FitnessFunctionWeights setReassignmentWeight(double reassignmentWeight) {
        this.reassignmentWeight = reassignmentWeight;
        return this;
    }

    public double getPassengerWeight() {
        return passengerWeight;
    }

    public FitnessFunctionWeights setPassengerWeight(double passengerWeight) {
        this.passengerWeight = passengerWeight;
        return this;
    }

    public double getFlightPriorityWeight() {
        return flightPriorityWeight;
    }

    public double getFlightPriorityValue(Flight.FlightPriority priority) {
        switch (priority) {
            case LOW:
                return priorityValues.get(0);
            case HIGH:
                return priorityValues.get(2);
            case NORMAL:
            default:
                return priorityValues.get(1);
        }
    }

    public FitnessFunctionWeights setFlightPriorityWeight(double flightPriorityWeight) {
        this.flightPriorityWeight = flightPriorityWeight;
        return this;
    }

    public double getTimeChangedWeight() {
        return timeChangedWeight;
    }

    public FitnessFunctionWeights setTimeChangedWeight(double timeChangedWeight) {
        this.timeChangedWeight = timeChangedWeight;
        return this;
    }

    public FitnessFunctionWeights setPriorityValues(List<Double> priorityValues) {
        this.priorityValues = priorityValues;
        return this;
    }

    public double getWeightsProduct() {
        return reassignmentWeight * passengerWeight * flightPriorityWeight * timeChangedWeight;
    }
}
