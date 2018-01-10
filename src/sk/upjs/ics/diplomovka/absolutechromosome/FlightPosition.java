package sk.upjs.ics.diplomovka.absolutechromosome;

public class FlightPosition {

    private int gate;
    private int flight;

    public FlightPosition(int gate, int flight) {
        this.gate = gate;
        this.flight = flight;
    }

    public int getGate() {
        return gate;
    }

    public int getFlight() {
        return flight;
    }
}
