package sk.upjs.ics.diplomovka.data.flights;

public class Flight implements Comparable<Flight> {

    private int id;
    private int start;
    private int end;
    private FlightType type;
    private FlightCategory category;
    private FlightPriority priority;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes
    private int originalStandId; // TODO
    private int noOfPassengers;

    public enum FlightType {
        ARRIVAL,
        DEPARTURE
    }

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
            return -1; // the earlier, the better

        return 1;
    }

    public int getLength() {
        return end - start;
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

    public int getEnd() {
        return end;
    }

    public Flight setEnd(int end) {
        this.end = end;
        return this;
    }

    public FlightCategory getCategory() {
        return category;
    }

    public Flight setCategory(FlightCategory category) {
        this.category = category;
        return this;
    }

    public FlightType getType() {
        return type;
    }

    public Flight setType(FlightType type) {
        this.type = type;
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
}
