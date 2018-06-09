package sk.upjs.ics.diplomovka.data.flights;

import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class FullFlight {

    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft, Turnaround time, No of passengers

    private int scheduled;
    private int actual;
    private int terminal;
    private String gate;
    private String status;
    private String flightNo;
    private Aircraft aircraft;
    private int turnaroundTime;
    private int noOfPassengers;

    public int getScheduled() {
        return scheduled;
    }

    public FullFlight setScheduled(int scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public int getActual() {
        return actual;
    }

    public FullFlight setActual(int actual) {
        this.actual = actual;
        return this;
    }

    public int getTerminal() {
        return terminal;
    }

    public FullFlight setTerminal(int terminal) {
        this.terminal = terminal;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public FullFlight setGate(String gate) {
        this.gate = gate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FullFlight setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public FullFlight setFlightNo(String flightNo) {
        this.flightNo = flightNo;
        return this;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public FullFlight setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public FullFlight setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
        return this;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public FullFlight setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
        return this;
    }

    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static Flight toFlight(FullFlight departure, StandsStorage standsStorage) {
        return new Flight()
                .setId(FlightId.getId())
                .setCategory(Flight.FlightCategory.SCHENGEN) // TODO
                .setPriority(Flight.FlightPriority.NORMAL) // TODO
                .setAircraft(departure.getAircraft())
                .setTurnaroundTime(departure.getTurnaroundTime())
                .setStart(departure.getScheduled() - departure.getTurnaroundTime()) // TODO consider something else than turnaround time?
                .setOriginalStart(departure.getScheduled())
                .setEnd(departure.getScheduled())
                .setNoOfPassengers(departure.getNoOfPassengers())
                .setDestination(departure.getTo())
                .setOriginalGateId(standsStorage.getGateId(departure.getGate()))
                .setOriginalStandId(standsStorage.getStandIdByGate(departure.getGate()));
                // TODO: arrival data
    }
}
