package sk.upjs.ics.diplomovka.model1;

import sk.upjs.ics.diplomovka.base.Chromosome;

public class AbsolutePositionChromosome extends Chromosome {
    private int gates;
    private int noOfFlights = 0;
    private int maxNoFlights;

    public AbsolutePositionChromosome(int gates, int maxNoFlights) {
        this.gates = gates;
        this.maxNoFlights = maxNoFlights;
    }

    public int getGene(int gate, int flight) {
        return getGene(gate * maxNoFlights + flight);
    }

    public void setGene(int gate, int flightIdx, int flight) {
        setGene(gate * maxNoFlights + flightIdx, flight);
        if(flightIdx > noOfFlights) {
            noOfFlights++;
        }
    }

    public int getNoOfFlights() {
        return noOfFlights;
    }

    public int getMaxNoFlights() {
        return maxNoFlights;
    }

    public int getGates() {
        return gates;
    }
}
