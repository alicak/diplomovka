package sk.upjs.ics.diplomovka.data.flights;

public class FullArrival extends FullFlight {

    /***
     * TODO: reflect changes in flight data
     */

    // TODO: specific values for every flight
    private static int BEFORE_ARRIVAL_SLOT = 5;
    private static int AFTER_ARRIVAL_SLOT = 10;

    // column structure: Scheduled, Actual, From, Terminal, Gate, Baggage claim, Status, Flight No, Aircraft

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

    public static Flight toFlight(FullArrival arrival) {
        return new Flight()
                .setId(FlightId.getId())
                .setType(Flight.FlightType.ARRIVAL)
                .setStart(arrival.getScheduled() - BEFORE_ARRIVAL_SLOT)
                .setEnd(arrival.getScheduled() + AFTER_ARRIVAL_SLOT)
                .setCategory(Flight.FlightCategory.SCHENGEN) // TODO
                .setAircraft(arrival.getAircraft())
                .setTurnaroundTime(-1); // TODO
    }
}
