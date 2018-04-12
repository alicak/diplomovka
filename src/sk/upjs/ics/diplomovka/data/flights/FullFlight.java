package sk.upjs.ics.diplomovka.data.flights;

public abstract class FullFlight {

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
}
