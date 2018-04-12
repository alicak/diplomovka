package sk.upjs.ics.diplomovka.data;

public class FitnessFunctionWeights {

    private int reassignmentWeight;
    private int passengerWeight;
    private int flightPriorityWeight;
    private int timeChangedWeight;

    public int getReassignmentWeight() {
        return reassignmentWeight;
    }

    public FitnessFunctionWeights setReassignmentWeight(int reassignmentWeight) {
        this.reassignmentWeight = reassignmentWeight;
        return this;
    }

    public int getPassengerWeight() {
        return passengerWeight;
    }

    public FitnessFunctionWeights setPassengerWeight(int passengerWeight) {
        this.passengerWeight = passengerWeight;
        return this;
    }

    public int getFlightPriorityWeight() {
        return flightPriorityWeight;
    }

    public FitnessFunctionWeights setFlightPriorityWeight(int flightPriorityWeight) {
        this.flightPriorityWeight = flightPriorityWeight;
        return this;
    }

    public int getTimeChangedWeight() {
        return timeChangedWeight;
    }

    public FitnessFunctionWeights setTimeChangedWeight(int timeChangedWeight) {
        this.timeChangedWeight = timeChangedWeight;
        return this;
    }
}
