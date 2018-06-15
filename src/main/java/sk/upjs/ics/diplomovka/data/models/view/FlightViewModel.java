package sk.upjs.ics.diplomovka.data.models.view;

public class FlightViewModel {

    private String code;
    private String destination;
    private int originalStart;
    private int actualStart;
    private String originalGate;
    private String gate;
    private int delay;
    private int assignmentDelay;

    private static final int DELAY_TOLERANCE = 10;

    public boolean isGateChanged() {
        return !gate.equals(originalGate);
    }

    public boolean isDelayed() {
        return delay > DELAY_TOLERANCE;
    }

    public boolean isAssignmentDelayed() {
        return assignmentDelay > DELAY_TOLERANCE;
    }

    public int getTotalDelay() {
        return delay + assignmentDelay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public FlightViewModel setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public int getOriginalStart() {
        return originalStart;
    }

    public FlightViewModel setOriginalStart(int originalStart) {
        this.originalStart = originalStart;
        return this;
    }

    public int getActualStart() {
        return actualStart;
    }

    public FlightViewModel setActualStart(int actualStart) {
        this.actualStart = actualStart;
        return this;
    }

    public String getOriginalGate() {
        return originalGate;
    }

    public FlightViewModel setOriginalGate(String originalGate) {
        this.originalGate = originalGate;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public FlightViewModel setGate(String gate) {
        this.gate = gate;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public FlightViewModel setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public int getAssignmentDelay() {
        return assignmentDelay;
    }

    public FlightViewModel setAssignmentDelay(int assignmentDelay) {
        this.assignmentDelay = assignmentDelay;
        return this;
    }

    public int compareTo(FlightViewModel f) {
        if (this.getOriginalStart() == f.getOriginalStart())
            return 0;

        if (this.getOriginalStart() < f.getOriginalStart())
            return -1;

        return 1;
    }
}