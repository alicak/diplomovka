package sk.upjs.ics.diplomovka.data.models.data;

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

    public FlightDataModel(int id, String scheduled, String actual, int turnaroundTime,
                           int gateId, int standId, String code, int noOfPassengers,
                           List<Integer> transfersIds, int aircraftId, int priority,
                           int categoryId, String destination) {
        this.id = id;
        this.scheduled = scheduled;
        this.actual = actual;
        this.turnaroundTime = turnaroundTime;
        this.gateId = gateId;
        this.standId = standId;
        this.code = code;
        this.noOfPassengers = noOfPassengers;
        this.transfersIds = transfersIds;
        this.aircraftId = aircraftId;
        this.priority = priority;
        this.categoryId = categoryId;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public String getScheduled() {
        return scheduled;
    }

    public String getActual() {
        return actual;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getGateId() {
        return gateId;
    }

    public int getStandId() {
        return standId;
    }

    public String getCode() {
        return code;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public List<Integer> getTransfersIds() {
        return transfersIds;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public int getPriority() {
        return priority;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDestination() {
        return destination;
    }
}
