package sk.upjs.ics.diplomovka.data.flights;

public class FullDeparture extends FullFlight {

    private static int BEFORE_DEPARTURE_SLOT = 60;
    private static int AFTER_DEPARTURE_SLOT = 0;

    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft

    private String to;
    private String gate;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public Flight toFlight(FullDeparture departure) {
        // TODO
        return null;
    }
}
