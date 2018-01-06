package sk.upjs.ics.diplomovka.data.flights;

public class FullArrival extends FullFlight {

    // TODO: specific values for every flight
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

    public static Flight toFlight(FullArrival arrival) {
        Flight flight = new Flight();

        flight.setId(FlightId.getId());
        flight.setType(Flight.FlightType.ARRIVAL);
        flight.setStart(arrival.getScheduled() - BEFORE_ARRIVAL_SLOT);
        flight.setEnd(arrival.getScheduled() - AFTER_ARRIVAL_SLOT);
        flight.setCategory(Flight.FlightCategory.SCHENGEN); // TODO
        flight.setAircraft(arrival.getAircraft());
        flight.setTurnaroundTime(-1); // TODO

        return flight;
    }
}
