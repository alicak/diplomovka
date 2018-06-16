package sk.upjs.ics.diplomovka.data.models.view;

public class AssignmentStatistics {

    private int assignmentDelayAverage;
    private int assignmentDelayCount;
    private int assignmentDelayMax;

    private int regularDelayAverage;
    private int regularDelayCount;
    private int regularDelayMax;

    public int getAssignmentDelayAverage() {
        return assignmentDelayAverage;
    }

    public AssignmentStatistics setAssignmentDelayAverage(int assignmentDelayAverage) {
        this.assignmentDelayAverage = assignmentDelayAverage;
        return this;
    }

    public int getAssignmentDelayCount() {
        return assignmentDelayCount;
    }

    public AssignmentStatistics setAssignmentDelayCount(int assignmentDelayCount) {
        this.assignmentDelayCount = assignmentDelayCount;
        return this;
    }

    public int getAssignmentDelayMax() {
        return assignmentDelayMax;
    }

    public AssignmentStatistics setAssignmentDelayMax(int assignmentDelayMax) {
        this.assignmentDelayMax = assignmentDelayMax;
        return this;
    }

    public int getRegularDelayAverage() {
        return regularDelayAverage;
    }

    public AssignmentStatistics setRegularDelayAverage(int regularDelayAverage) {
        this.regularDelayAverage = regularDelayAverage;
        return this;
    }

    public int getRegularDelayCount() {
        return regularDelayCount;
    }

    public AssignmentStatistics setRegularDelayCount(int regularDelayCount) {
        this.regularDelayCount = regularDelayCount;
        return this;
    }

    public int getRegularDelayMax() {
        return regularDelayMax;
    }

    public AssignmentStatistics setRegularDelayMax(int regularDelayMax) {
        this.regularDelayMax = regularDelayMax;
        return this;
    }
}
