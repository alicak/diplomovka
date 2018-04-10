package sk.upjs.ics.diplomovka.data.flights;

import java.util.Random;

public class Flight implements Comparable<Flight> {

    private int id;
    private int start;
    private int end;
    private FlightType type;
    private FlightCategory category;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes
    private int originalStandId; // TODO

    private int currentStart;

    private static Random random = new Random();
    private static final double TURNAROUND_TIME_MEAN = 38.65;
    private static final double TURNAROUND_TIME_DEVIATION = 10.44;

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

    public Flight(int id, int start, int end, FlightType type, FlightCategory category, Aircraft aircraft, int turnaroundTime, int originalStandId) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
        this.category = category;
        this.aircraft = aircraft;
        this.turnaroundTime = turnaroundTime;
        this.originalStandId = originalStandId;
        this.turnaroundTime = generateTurnaroundTime();
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

    public void setOriginalStandId(int originalStandId) {
        this.originalStandId = originalStandId;
    }

    public int getOriginalStandId() {
        return originalStandId;
    }

    public static int generateTurnaroundTime() {
        return (int) (random.nextGaussian() * TURNAROUND_TIME_DEVIATION + TURNAROUND_TIME_MEAN);
    }
}
