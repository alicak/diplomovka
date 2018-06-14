package sk.upjs.ics.diplomovka.data.flights;

import java.util.Map;

public class Flight implements Comparable<Flight> {

    private String code;
    private int id;
    private int start;
    private int originalStart;
    private int end;
    private int delay;
    private int category;
    private int priority;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes
    private int originalStandId;
    private int originalGateId;
    private int noOfPassengers;
    private String destination;
    private Arrival arrival;
    private Map<Integer, Integer> transfers; // key is id of departure that has this arrival, value is no of passengers

    public Flight(FlightDataModel flightDataModel, FlightAttributes attributes) {
        this.code = flightDataModel.getCode();
        this.id = flightDataModel.getId();
        this.start = flightDataModel.getScheduled() - flightDataModel.getTurnaroundTime();
        this.originalStart = flightDataModel.getScheduled();
        this.end = flightDataModel.getScheduled();
        this.delay = 0;
        this.category = flightDataModel.getCategoryId();
        this.priority = flightDataModel.getPriorityId();
        this.aircraft = attributes.getAircraftByName(flightDataModel.getAircraftName());
        this.turnaroundTime = flightDataModel.getTurnaroundTime();
        this.originalStandId = flightDataModel.getStandId();
        this.originalGateId = flightDataModel.getGate();
        this.noOfPassengers = flightDataModel.getNoOfPassengers();
        this.destination = flightDataModel.getDestination();
        this.arrival = flightDataModel.getArrival();
        this.transfers = flightDataModel.getTransfers();
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

    public int getId() {
        return id;
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

    public int getCategory() {
        return category;
    }

    public int getPriority() {
        return priority;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getOriginalStandId() {
        return originalStandId;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public String getDestination() {
        return destination;
    }

    public int getOriginalGateId() {
        return originalGateId;
    }

    public boolean hasTransfers() {
        return !transfers.isEmpty();
    }

    public Map<Integer, Integer> getTransfers() {
        return transfers;
    }

    public boolean hasArrival() {
        return arrival != null;
    }

    public Arrival getArrival() {
        return arrival;
    }


}
