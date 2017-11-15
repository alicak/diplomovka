package sk.upjs.ics.diplomovka.flights;

public class Flight {

    private int id;
    private int start;
    private int end;
    private FlightType type;

    public enum FlightType {
        ARRIVAL,
        DEPARTURE
    }

    public Flight(int id, int start, int end, FlightType type) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public int getLength() {
        return end - start;
    }

    public int getId() {
        return id;
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

    public FlightType getType() {
        return type;
    }
}
