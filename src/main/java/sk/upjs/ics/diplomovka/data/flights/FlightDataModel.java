package sk.upjs.ics.diplomovka.data.flights;

import java.util.Map;

public class FlightDataModel {

    private int id;
    private int scheduled;
    private int actual;
    private int turnaroundTime;
    private int gate;
    private int standId;
    private String code;
    private int noOfPassengers;
    private Map<Integer, Integer> transfers;
    private String aircraftName;
    private int priorityId;
    private int categoryId;
    private Arrival arrival;
    private String destination;

    public int getId() {
        return id;
    }

    public FlightDataModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getScheduled() {
        return scheduled;
    }

    public FlightDataModel setScheduled(int scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public int getActual() {
        return actual;
    }

    public FlightDataModel setActual(int actual) {
        this.actual = actual;
        return this;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public FlightDataModel setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
        return this;
    }

    public int getGate() {
        return gate;
    }

    public FlightDataModel setGate(int gate) {
        this.gate = gate;
        return this;
    }

    public int getStandId() {
        return standId;
    }

    public FlightDataModel setStandId(int standId) {
        this.standId = standId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public FlightDataModel setCode(String code) {
        this.code = code;
        return this;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public FlightDataModel setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
        return this;
    }

    public Map<Integer, Integer> getTransfers() {
        return transfers;
    }

    public FlightDataModel setTransfers(Map<Integer, Integer> transfers) {
        this.transfers = transfers;
        return this;
    }

    public String getAircraftName() {
        return aircraftName;
    }

    public FlightDataModel setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
        return this;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public FlightDataModel setPriorityId(int priorityId) {
        this.priorityId = priorityId;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public FlightDataModel setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public FlightDataModel setArrival(Arrival arrival) {
        this.arrival = arrival;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
