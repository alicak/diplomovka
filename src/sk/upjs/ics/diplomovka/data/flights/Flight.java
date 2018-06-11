package sk.upjs.ics.diplomovka.data.flights;

import java.util.HashMap;
import java.util.Map;

public class Flight implements Comparable<Flight> {

    private String code;
    private int id;
    private int start;
    private int originalStart;
    private int end;
    private int delay;
    private FlightCategory category;
    private FlightPriority priority;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes
    private int originalStandId;
    private int noOfPassengers;
    private String destination;
    private int originalGateId;
    private Arrival arrival;
    private Map<Integer, Integer> transfers = new HashMap<>(); // key is id of departure that has this arrival, value is no of passengers

    public enum FlightCategory {
        SCHENGEN,
        NON_SCHENGEN
    }

    public enum FlightPriority {
        NORMAL,
        HIGH,
        LOW
    }

    @Override
    public int compareTo(Flight o) {
        if (this.getStart() == o.getStart())
            return 0;

        if (this.getStart() < o.getStart())
            return -1; // the earlier in list, the better

        return 1;
    }

    public int getLength() {
        return end - start;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public Flight setId(int id) {
        this.id = id;
        return this;
    }

    public int getStart() {
        return start;
    }

    public Flight setStart(int start) {
        this.start = start;
        return this;
    }

    public int getOriginalStart() {
        return originalStart;
    }

    public Flight setOriginalStart(int originalStart) {
        this.originalStart = originalStart;
        return this;
    }

    public int getEnd() {
        return end;
    }

    public Flight setEnd(int end) {
        this.end = end;
        return this;
    }

    public int getDelay() {
        return delay;
    }

    public Flight setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public FlightCategory getCategory() {
        return category;
    }

    public Flight setCategory(FlightCategory category) {
        this.category = category;
        return this;
    }

    public FlightPriority getPriority() {
        return priority;
    }

    public Flight setPriority(FlightPriority priority) {
        this.priority = priority;
        return this;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Flight setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public Flight setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
        return this;
    }

    public Flight setOriginalStandId(int originalStandId) {
        this.originalStandId = originalStandId;
        return this;
    }

    public int getOriginalStandId() {
        return originalStandId;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public Flight setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Flight setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public int getOriginalGateId() {
        return originalGateId;
    }

    public Flight setOriginalGateId(int originalGateId) {
        this.originalGateId = originalGateId;
        return this;
    }

    public boolean hasTransfers() {
        return !transfers.isEmpty();
    }

    public Map<Integer, Integer> getTransfers() {
        return transfers;
    }

    public Flight setTransfers(Map<Integer, Integer> transfers) {
        this.transfers = transfers;
        return this;
    }

    public boolean hasArrival() {
        return arrival != null;
    }

    public Flight setArrival(Arrival arrival) {
        this.arrival = arrival;
        return this;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public class Arrival {
        private int originalGateId; // stand is the same, but gate can be different
        private int noOfPassengers; // redundant for now...
        // arrival time is the same as start time of Flight

        public int getOriginalGateId() {
            return originalGateId;
        }

        public void setOriginalGateId(int originalGateId) {
            this.originalGateId = originalGateId;
        }

        public int getNoOfPassengers() {
            return noOfPassengers;
        }

        public void setNoOfPassengers(int noOfPassengers) {
            this.noOfPassengers = noOfPassengers;
        }
    }
}
