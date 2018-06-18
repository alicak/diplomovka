package sk.upjs.ics.diplomovka.data.models.view;

public class ReassignmentStatistics {

    private double originalFitness;
    private double newFitness;

    private int noOfReassignments;
    private int totalWalkingDistance;
    private int totalTimeDiff;

    public ReassignmentStatistics(double originalFitness, double newFitness, int noOfReassignments, int totalWalkingDistance, int totalTimeDiff) {
        this.originalFitness = originalFitness;
        this.newFitness = newFitness;
        this.noOfReassignments = noOfReassignments;
        this.totalWalkingDistance = totalWalkingDistance;
        this.totalTimeDiff = totalTimeDiff;
    }

    public int getNoOfReassignments() {
        return noOfReassignments;
    }

    public int getTotalWalkingDistance() {
        return totalWalkingDistance;
    }

    public int getTotalTimeDiff() {
        return totalTimeDiff;
    }

    public double getOriginalFitness() {
        return originalFitness;
    }

    public double getNewFitness() {
        return newFitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("no of reassignments: ").append(noOfReassignments).append("\n")
                .append("total time difference: ").append(totalTimeDiff).append("\n")
                .append("total walking distance: ").append(totalWalkingDistance);
        return sb.toString();
    }

}
