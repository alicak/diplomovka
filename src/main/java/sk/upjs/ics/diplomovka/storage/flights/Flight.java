package sk.upjs.ics.diplomovka.storage.flights;

import sk.upjs.ics.diplomovka.data.models.data.Aircraft;
import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;
import sk.upjs.ics.diplomovka.data.models.data.Transfer;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.List;

public class Flight implements Comparable<Flight> {

    private String code;
    private int id;
    private int start; // actual departure time minus turnaround time - in minutes
    private int originalStart; // original departure time - in minutes
    private int delay;
    private int category;
    private int priority;
    private Aircraft aircraft;
    private int turnaroundTime; // in minutes
    private int originalStandId;
    private int originalGateId;
    private int noOfPassengers;
    private String destination;
    private List<Transfer> transfers;

    public Flight(FlightDataModel flightDataModel, FlightAttributes attributes) {
        this.code = flightDataModel.getCode();
        this.id = flightDataModel.getId();
        this.start = Utils.timeToMinutes(flightDataModel.getScheduled()) - flightDataModel.getTurnaroundTime();
        this.originalStart = Utils.timeToMinutes(flightDataModel.getScheduled());
        this.delay = 0;
        this.category = flightDataModel.getCategoryId();
        this.priority = flightDataModel.getPriority();
        this.aircraft = attributes.getAircraftById(flightDataModel.getAircraftId());
        this.turnaroundTime = flightDataModel.getTurnaroundTime();
        this.originalStandId = flightDataModel.getStandId();
        this.originalGateId = flightDataModel.getGateId();
        this.noOfPassengers = flightDataModel.getNoOfPassengers();
        this.destination = flightDataModel.getDestination();
        this.transfers = attributes.getTransfersByIds(flightDataModel.getTransfersIds());
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
        return turnaroundTime;
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

    public int getEnd() {
        return start + turnaroundTime;
    }

    public int getOriginalStart() {
        return originalStart;
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

    public void setOriginalStandId(int originalStandId) {
        this.originalStandId = originalStandId;
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

    public void setOriginalGateId(int originalGateId) {
        this.originalGateId = originalGateId;
    }

    public boolean hasTransfers() {
        return !transfers.isEmpty();
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

}
