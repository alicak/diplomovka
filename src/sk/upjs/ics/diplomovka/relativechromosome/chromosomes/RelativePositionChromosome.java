package sk.upjs.ics.diplomovka.relativechromosome.chromosomes;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class RelativePositionChromosome extends Chromosome {
    private final int noOfGates;
    private final int noOfFlights;

    public RelativePositionChromosome(int gates, int flights) {
        this.noOfGates = gates;
        this.noOfFlights = flights;
    }

    public int getGene(int row, int column) {
        return getGene(row * noOfFlights + column);
    }

    public int getGate(int flight) {
        return getGene(noOfFlights, flight);
    }

    public void setGene(int row, int column, int value) {
        setGene(getIndex(row, column), value);
    }

    public void setGate(int flight, int value) {
        setGene(getIndex(noOfFlights, flight), value);
    }

    public int getNoFlights() {
        return noOfFlights;
    }

    public int getNoOfGates() {
        return noOfGates;
    }

    public int getIndex(int row, int column) {
        return row * noOfFlights + column;
    }

    public RelativePositionChromosome copy() {
        RelativePositionChromosome chromosome = new RelativePositionChromosome(noOfGates, noOfFlights);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);

        return chromosome;
    }

    @Override
    public void removeFlight(int flight) {
        throw new NotImplementedException();
    }

    @Override
    public void removeGate(int gate) {
        throw new NotImplementedException();
    }
}
