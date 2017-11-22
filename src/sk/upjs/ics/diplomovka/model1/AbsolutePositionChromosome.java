package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class AbsolutePositionChromosome extends Chromosome {
    private int gates;
    private int flights;

    public AbsolutePositionChromosome(int gates, int flights) {
        this.gates = gates;
        this.flights = flights;
    }

    public int getGene(int gate, int flight) {
        return getGene(gate * flights + flight);
    }

    public void setGene(int gate, int flightIdx, int flight) {
        setGene(gate*flights + flightIdx, flight);
    }

    public int getFlights() {
        return flights;
    }

    public int getGates() {
        return gates;
    }
}
