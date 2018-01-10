package sk.upjs.ics.diplomovka.data.flights;

public class Flight implements Comparable<Flight> {

    private int id;
    private int start;
    private int end;
    private FlightType type;
    private FlightCategory category;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes

    public enum FlightType {
        ARRIVAL,
        DEPARTURE
    }

    public enum FlightCategory {
        SCHENGEN,
        NON_SCHENGEN
    }

    public Flight() {
    }

    public Flight(int id, int start, int end, FlightType type, FlightCategory category, Aircraft aircraft, int turnaroundTime) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.category = category;
        this.aircraft = aircraft;
        this.turnaroundTime = turnaroundTime;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public FlightCategory getCategory() {
        return category;
    }

    public void setCategory(FlightCategory category) {
        this.category = category;
    }

    public FlightType getType() {
        return type;
    }

    public void setType(FlightType type) {
        this.type = type;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}
