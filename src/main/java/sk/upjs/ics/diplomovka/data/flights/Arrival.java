package sk.upjs.ics.diplomovka.data.flights;

public class Arrival {
    private int originalGateId; // stand is the same, but gate can be different
    private int noOfPassengers; // redundant for now...
    // arrival time is the same as start time of parent Flight (departure)

    public int getOriginalGateId() {
        return originalGateId;
    }

    public void setOriginalGateId(int originalGateId) {
        this.originalGateId = originalGateId;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }
}