package sk.upjs.ics.diplomovka.data.models.view;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;

public class FlightViewModel implements Comparable<FlightViewModel> {

    private String code;
    private String destination;
    private int originalStart;
    private int actualStart;
    private String originalGate;
    private String gate;
    private int standId;
    private int delay; // regular delay
    private int assignmentDelay;

    private static final int DELAY_TOLERANCE = 10; // if the flight is delayed less than 10 minutes, it's not cosidered a delay

    public FlightViewModel() {
    }

    public FlightViewModel(Flight flight, GeneralStorage storage) {
        this.code = flight.getCode();
        this.destination = flight.getDestination();
        this.originalStart = flight.getOriginalStart();
        this.actualStart = flight.getStart() + flight.getLength();
        this.originalGate = storage.getStandsStorage().getGateById(flight.getOriginalGateId());
        this.gate = originalGate;
        this.standId = flight.getOriginalStandId();
        this.delay = flight.getDelay();
        this.assignmentDelay = 0;
    }

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

    public FlightViewModel setCode(String code) {
        this.code = code;
        return this;
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

    public int getStandId() {
        return standId;
    }

    public FlightViewModel setStandId(int standId) {
        this.standId = standId;
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
        if (this.getActualStart() == f.getActualStart())
            return 0;

        if (this.getActualStart() < f.getActualStart())
            return -1;

        return 1;
    }
}