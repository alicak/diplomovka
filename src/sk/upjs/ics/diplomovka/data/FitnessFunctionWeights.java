package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.util.Arrays;
import java.util.List;

import static sk.upjs.ics.diplomovka.utils.Utils.MINUTES_IN_DAY;

public class FitnessFunctionWeights {

    private static final double DEFAULT_WEIGHT = 1;

    private double reassignmentWeight = DEFAULT_WEIGHT;
    private double passengerWeight = DEFAULT_WEIGHT;
    private double flightPriorityWeight = DEFAULT_WEIGHT;
    private double timeChangedWeight = DEFAULT_WEIGHT;
    private List<Double> priorityValues = Arrays.asList(DEFAULT_WEIGHT - 0.5, DEFAULT_WEIGHT, DEFAULT_WEIGHT + 0.5);
    private double walkingDistanceWeight = DEFAULT_WEIGHT;
    private List<IntervalWeight> futureWeights = Arrays.asList(new IntervalWeight(0, MINUTES_IN_DAY, DEFAULT_WEIGHT));

    public double getReassignmentWeight() {
        return reassignmentWeight;
    }

    public double getPassengerWeight() {
        return passengerWeight;
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

    public double getTimeChangedWeight() {
        return timeChangedWeight;
    }

    public double getWalkingDistanceWeight() {
        return walkingDistanceWeight;
    }

    public double getFutureWeight(int timeFromReassignmentStart) {
        for (IntervalWeight interval : futureWeights) {
            if (interval.contains(timeFromReassignmentStart)) {
                return interval.getWeight();
            }
        }
        return DEFAULT_WEIGHT;
    }

    public FitnessFunctionWeights setPassengerWeight(double passengerWeight) {
        this.passengerWeight = passengerWeight;
        return this;
    }

    public FitnessFunctionWeights setReassignmentWeight(double reassignmentWeight) {
        this.reassignmentWeight = reassignmentWeight;
        return this;
    }

    public FitnessFunctionWeights setWalkingDistanceWeight(double walkingDistanceWeight) {
        this.walkingDistanceWeight = walkingDistanceWeight;
        return this;
    }

    public FitnessFunctionWeights setFlightPriorityWeight(double flightPriorityWeight) {
        this.flightPriorityWeight = flightPriorityWeight;
        return this;
    }

    public FitnessFunctionWeights setTimeChangedWeight(double timeChangedWeight) {
        this.timeChangedWeight = timeChangedWeight;
        return this;
    }

    public FitnessFunctionWeights setPriorityValues(List<Double> priorityValues) {
        this.priorityValues = priorityValues;
        return this;
    }

    public FitnessFunctionWeights setFutureWeights(List<IntervalWeight> futureWeights) {
        this.futureWeights = futureWeights;
        return this;
    }
}
