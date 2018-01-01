package sk.upjs.ics.diplomovka.data.flights;

public class FullFlight {

    private Flight flight;
    private int actual;
    private String gate;

    public FullFlight() {
    }

    public FullFlight(Flight flight, int actualTime, String gate) {
        this.flight = flight;
        this.actual = actualTime;
        this.gate = gate;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }
}
