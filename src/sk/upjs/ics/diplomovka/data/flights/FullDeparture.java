package sk.upjs.ics.diplomovka.data.flights;

public class FullDeparture extends FullFlight {
    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft, Turnaround time

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
        flight.setCategory(Flight.FlightCategory.SCHENGEN); // TODO
        flight.setPriority(Flight.FlightPriority.NORMAL); // TODO
        flight.setAircraft(departure.getAircraft());
        flight.setTurnaroundTime(departure.getTurnaroundTime());
        flight.setStart(departure.getScheduled() - departure.getTurnaroundTime()); // TODO consider something else than turnaround time?
        flight.setEnd(departure.getScheduled());

        return flight;
    }
}
