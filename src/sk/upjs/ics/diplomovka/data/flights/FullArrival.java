package sk.upjs.ics.diplomovka.data.flights;

public class FullArrival extends FullFlight {

    private static int BEFORE_ARRIVAL_SLOT = 30;
    private static int AFTER_ARRIVAL_SLOT = 30;

    // column structure: Scheduled, Actual, From, Terminal, Baggage claim, Status, Flight No, Aircraft

    private String from;
    private int baggageClaim;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getBaggageClaim() {
        return baggageClaim;
    }

    public void setBaggageClaim(int baggageClaim) {
        this.baggageClaim = baggageClaim;
    }

    public Flight toFlight(FullArrival arrival) {
        // TODO
        return null;
    }
}
