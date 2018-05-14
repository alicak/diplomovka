package sk.upjs.ics.diplomovka.data.flights;

import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class FullDeparture extends FullFlight {
    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft, Turnaround time, No of passengers

    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static Flight toFlight(FullDeparture departure, StandsStorage standsStorage) {
        return new Flight()
                .setId(FlightId.getId())
                .setType(Flight.FlightType.DEPARTURE)
                .setCategory(Flight.FlightCategory.SCHENGEN) // TODO
                .setPriority(Flight.FlightPriority.NORMAL) // TODO
                .setAircraft(departure.getAircraft())
                .setTurnaroundTime(departure.getTurnaroundTime())
                .setStart(departure.getScheduled() - departure.getTurnaroundTime()) // TODO consider something else than turnaround time?
                .setOriginalStart(departure.getScheduled())
                .setEnd(departure.getScheduled())
                .setNoOfPassengers(departure.getNoOfPassengers())
                .setDestination(departure.getTo())
                .setOriginalGate(departure.getGate())
                .setOriginalStandId(standsStorage.getStandIdByGate(departure.getGate()));
    }
}
