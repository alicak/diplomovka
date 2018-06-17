package sk.upjs.ics.diplomovka.storage;

import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sk.upjs.ics.diplomovka.utils.Utils.MINUTES_IN_DAY;

public class FitnessFunctionWeights {

    private static final double DEFAULT_WEIGHT = 1;

    private double reassignmentWeight = DEFAULT_WEIGHT;
    private double passengerWeight = DEFAULT_WEIGHT;
    private double flightPriorityWeight = DEFAULT_WEIGHT;
    private double timeChangedWeight = DEFAULT_WEIGHT;
    private double walkingDistanceWeight = DEFAULT_WEIGHT;

    private List<IntervalWeight> futureWeights = Arrays.asList(new IntervalWeight(0, MINUTES_IN_DAY, DEFAULT_WEIGHT));
    private static final int CLOSE_INTERVAL_LENGTH = 60;
    private static final int MEDIUM_INTERVAL_LENGTH = 180;
    private static final int CLOSE_FLIGHT_WEIGHT = 10;
    private static final int MEDIUM_FLIGHT_WEIGHT = 3;
    private static final int FAR_FLIGHT_WEIGHT = 1;

    public FitnessFunctionWeights() {
    }

    public FitnessFunctionWeights(ReassignmentParameters parameters) {
        if (parameters.considerPriority())
            flightPriorityWeight = parameters.getPriorityWeight();
        if (parameters.considerPassengers())
            passengerWeight = parameters.getPassengersWeight();
        if (parameters.optimizeWalking())
            walkingDistanceWeight = parameters.getWalkingWeight();
        if (parameters.optimizeTime())
            timeChangedWeight = parameters.getTimeWeight();
        if (parameters.optimizeReassignments())
            reassignmentWeight = parameters.getReassignmentsWeight();

        futureWeights = new ArrayList<>();
        int end1 = parameters.getStartTime() + CLOSE_INTERVAL_LENGTH;
        int end2 = end1 + MEDIUM_INTERVAL_LENGTH;

        futureWeights = Arrays.asList(
                new IntervalWeight(0, end1, CLOSE_FLIGHT_WEIGHT),
                new IntervalWeight(end1, end2, MEDIUM_FLIGHT_WEIGHT),
                new IntervalWeight(end2, Math.max(end2, Utils.MINUTES_IN_DAY), FAR_FLIGHT_WEIGHT));
    }

    public double getReassignmentWeight() {
        return reassignmentWeight;
    }

    public double getPassengerWeight() {
        return passengerWeight;
    }

    public double getFlightPriorityWeight() {
        return flightPriorityWeight;
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

    public FitnessFunctionWeights setFutureWeights(List<IntervalWeight> futureWeights) {
        this.futureWeights = futureWeights;
        return this;
    }

    public class IntervalWeight {

        private int start;
        private int end;
        private double weight;

        public IntervalWeight(int start, int end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public boolean contains(int number) {
            return number >= start && number < end;
        }

        public double getWeight() {
            return weight;
        }
    }
}
