package sk.upjs.ics.diplomovka.ui.models;

public class ReassignmentParameters {

    private boolean considerPassengers;
    private boolean considerPriority;
    private boolean optimizeReassignments;
    private boolean optimizeTime;
    private boolean optimizeWalking;

    private double passengersWeight;
    private double priorityWeight;
    private double reassignmentsWeight;
    private double timeWeight;
    private double walkingWeight;

    private int startTime = 0;

    public ReassignmentParameters(boolean considerPassengers,boolean considerPriority, boolean optimizeReassignments,
                                  boolean optimizeTime, boolean optimizeWalking) {
        this.considerPassengers = considerPassengers;
        this.considerPriority = considerPriority;
        this.optimizeReassignments = optimizeReassignments;
        this.optimizeTime = optimizeTime;
        this.optimizeWalking = optimizeWalking;
    }

    public boolean considerPassengers() {
        return considerPassengers;
    }

    public boolean considerPriority() {
        return considerPriority;
    }

    public boolean optimizeReassignments() {
        return optimizeReassignments;
    }

    public boolean optimizeTime() {
        return optimizeTime;
    }

    public boolean optimizeWalking() {
        return optimizeWalking;
    }

    public double getPassengersWeight() {
        return passengersWeight;
    }

    public ReassignmentParameters setPassengersWeight(double passengersWeight) {
        this.passengersWeight = passengersWeight;
        return this;
    }

    public double getPriorityWeight() {
        return priorityWeight;
    }

    public ReassignmentParameters setPriorityWeight(double priorityWeight) {
        this.priorityWeight = priorityWeight;
        return this;
    }

    public double getReassignmentsWeight() {
        return reassignmentsWeight;
    }

    public ReassignmentParameters setReassignmentsWeight(double reassignmentsWeight) {
        this.reassignmentsWeight = reassignmentsWeight;
        return this;
    }

    public double getTimeWeight() {
        return timeWeight;
    }

    public ReassignmentParameters setTimeWeight(double timeWeight) {
        this.timeWeight = timeWeight;
        return this;
    }

    public double getWalkingWeight() {
        return walkingWeight;
    }

    public ReassignmentParameters setWalkingWeight(double walkingWeight) {
        this.walkingWeight = walkingWeight;
        return this;
    }

    public int getStartTime() {
        return startTime;
    }

    public ReassignmentParameters setStartTime(int startTime) {
        this.startTime = startTime;
        return this;
    }
}
