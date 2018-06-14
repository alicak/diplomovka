package sk.upjs.ics.diplomovka.data.flights;

import java.util.List;

public class FlightDataModel {

    private int id;
    private String scheduled;
    private String actual;
    private int turnaroundTime;
    private int gateId;
    private int standId;
    private String code;
    private int noOfPassengers;
    private List<Integer> transfersIds;
    private int aircraftId;
    private int priority;
    private int categoryId;
    private String destination;

    public int getId() {
        return id;
    }

    public FlightDataModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getScheduled() {
        return scheduled;
    }

    public FlightDataModel setScheduled(String scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public String getActual() {
        return actual;
    }

    public FlightDataModel setActual(String actual) {
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

    public int getGateId() {
        return gateId;
    }

    public FlightDataModel setGateId(int gateId) {
        this.gateId = gateId;
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

    public List<Integer> getTransfersIds() {
        return transfersIds;
    }

    public FlightDataModel setTransfersIds(List<Integer> transfersIds) {
        this.transfersIds = transfersIds;
        return this;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public FlightDataModel setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public FlightDataModel setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public FlightDataModel setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
