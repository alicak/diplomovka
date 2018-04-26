package sk.upjs.ics.diplomovka.data.flights;

public class FlightInfo {

    private String destination;
    private int originalStart;
    private int actualStart;
    private String originalGate;
    private String gate;
    private int delay;
    private int assignmentDelay;

    public boolean isGateChanged() {
        return !gate.equals(originalGate);
    }

    public int getTotalDelay() {
        return delay + assignmentDelay;
    }

    public String getDestination() {
        return destination;
    }

    public FlightInfo setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public int getOriginalStart() {
        return originalStart;
    }

    public FlightInfo setOriginalStart(int originalStart) {
        this.originalStart = originalStart;
        return this;
    }

    public int getActualStart() {
        return actualStart;
    }

    public FlightInfo setActualStart(int actualStart) {
        this.actualStart = actualStart;
        return this;
    }

    public String getOriginalGate() {
        return originalGate;
    }

    public FlightInfo setOriginalGate(String originalGate) {
        this.originalGate = originalGate;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public FlightInfo setGate(String gate) {
        this.gate = gate;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public FlightInfo setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public int getAssignmentDelay() {
        return assignmentDelay;
    }

    public FlightInfo setAssignmentDelay(int assignmentDelay) {
        this.assignmentDelay = assignmentDelay;
        return this;
    }

    public int compareTo(FlightInfo f) {
        if (this.getOriginalStart() == f.getOriginalStart())
            return 0;

        if (this.getOriginalStart() < f.getOriginalStart())
            return -1;

        return 1;
    }
}
