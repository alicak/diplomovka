package sk.upjs.ics.diplomovka.data.flights;

public class FullDeparture extends FullFlight {

    // TODO: specific values for every flight
    private static int BEFORE_DEPARTURE_SLOT = 15;
    private static int AFTER_DEPARTURE_SLOT = 5;

    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft

    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static Flight toFlight(FullDeparture departure) {
        Flight flight = new Flight();

        flight.setId(FlightId.getId());
        flight.setType(Flight.FlightType.DEPARTURE);
        flight.setStart(departure.getScheduled() - BEFORE_DEPARTURE_SLOT);
        flight.setEnd(departure.getScheduled() + AFTER_DEPARTURE_SLOT);
        flight.setCategory(Flight.FlightCategory.SCHENGEN); // TODO
        flight.setAircraft(departure.getAircraft());
        flight.setTurnaroundTime(-1); // TODO

        return flight;
    }
}
