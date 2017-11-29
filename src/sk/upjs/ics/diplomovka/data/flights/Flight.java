package sk.upjs.ics.diplomovka.data.flights;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;

public class Flight {

    private int id;
    private int start;
    private int end;
    private FlightType type;
    private FlightCategory category;
    private Aircraft aircraft;

    public enum FlightType {
        ARRIVAL,
        DEPARTURE
    }

    public enum FlightCategory {
        SCHENGEN,
        NON_SCHENGEN
    }

    public Flight(int id, int start, int end, FlightType type, FlightCategory category, Aircraft aircraft) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.category = category;
        this.aircraft = aircraft;
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

    public FlightCategory getCategory() {
        return category;
    }

    public FlightType getType() {
        return type;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }
}
