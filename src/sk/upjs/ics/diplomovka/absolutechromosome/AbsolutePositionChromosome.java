package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.*;

public class AbsolutePositionChromosome extends Chromosome {
    public static int EMPTY_GENE = -1;

    private int noOfGates;
    private int[] noOfFlights; // number of flights per gate
    private int maxNoFlights;
    private AbsolutePositionFeasibilityChecker feasibilityChecker;
    private Map<Integer, Integer> currentFlightStarts = new HashMap<>();
    private Map<Integer, Integer> currentFlightEnds = new HashMap<>();

    public AbsolutePositionChromosome(int noOfGates, int maxNoFlights) {
        this.noOfGates = noOfGates;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[noOfGates];
    }

    public int getGene(int gate, int flight) {
        return getGene(getIndex(gate, flight));
    }

    public void setGene(int gate, int flight, int flightValue) {
        int oldValue = getGene(gate, flight);
        if (flightValue != EMPTY_GENE && oldValue == EMPTY_GENE) {
            noOfFlights[gate]++;
        }
        setGene(getIndex(gate, flight), flightValue);
        resetFitness();
    }

    public void addNextFlight(int gate, int flightValue) {
        setGene(gate, noOfFlights[gate], flightValue);
    }

    public void insertFlight(int gate, int flight, int flightValue) {
        boolean append = true;
        for (int f = noOfFlights[gate] - 1; f >= flight; f--) {
            setGene(getIndex(gate, f + 1), getGene(gate, f));
            append = false;
        }

        setGene(gate, flight, flightValue);
        if (!append) // if the flight was appended, the count was already incremented in previous line
            noOfFlights[gate]++;
    }

    @Override
    public int addFlight() {
        return 0; // TODO
    }

    @Override
    public void removeFlight(int flightValue) {
        FlightPosition position = findPosition(flightValue);
        removeFlightFromGenes(position.getGate(), position.getFlight());

        Integer[] newGenes = new Integer[(maxNoFlights - 1) * noOfGates];
        for (int g = 0; g < noOfGates; g++) {
            for (int f = 0; f < maxNoFlights - 1; f++) {
                int idx = g * (maxNoFlights - 1) + f;
                int oldGene = getGene(g, f);
                if (oldGene > flightValue)
                    oldGene--;
                newGenes[idx] = oldGene;
            }
        }
        setGenes(Arrays.asList(newGenes));

        maxNoFlights--;
    }

    @Override
    public int addGate() {
        return 0; // TODO
    }

    public void removeFlightFromGenes(int gate, int flight) {
        for (int f = flight; f < noOfFlights[gate]; f++) {
            setGene(gate, f, getGene(gate, f + 1));
        }
        noOfFlights[gate]--;
        resetFitness();
    }

    public void removeGate(int gate) {
        for (int i = 0; i < noOfFlights[gate]; i++) { // assign flights to remaining gates
            int gene = getGene(gate, i);
            int newGate = -1;
            boolean feasible = false;

            while (!feasible) {
                newGate = Utils.randomInt(noOfGates);
                if (newGate == gate)
                    continue;
                feasible = checkFlightFeasibility(gene, gate);
            }
            int position = Utils.randomInt(noOfFlights[newGate]);
            insertFlight(newGate, position, gene);
        }

        for (int i = getIndex(gate, 0); i < (noOfGates - 1) * maxNoFlights; i++) { // we remove all positions for that gate
            setGene(i, getGene(i + maxNoFlights));
        }

        for (int i = gate; i < noOfGates - 1; i++) {
            noOfFlights[i] = noOfFlights[i + 1];
        }

        noOfFlights = Arrays.copyOf(noOfFlights, noOfFlights.length - 1);
        noOfGates--;
        resetFitness();
    }

    public int getNoOfFlights(int gate) {
        return noOfFlights[gate];
    }

    @Override
    public int getNoOfFlights() {
        return maxNoFlights;
    }

    @Override
    public int getNoOfGates() {
        return noOfGates;
    }

    public FlightPosition findPosition(int flightValue) {
        for (int g = 0; g < noOfGates; g++) {
            for (int f = 0; f < noOfFlights[g]; f++) {
                if (getGene(g, f) == flightValue)
                    return new FlightPosition(g, f);
            }
        }
        return new FlightPosition(-1, -1); // flight was not found
    }

    protected int getIndex(int gate, int flight) {
        return gate * maxNoFlights + flight;
    }

    private void setNoOfFlightsPerGate(int[] noOfFlights) {
        this.noOfFlights = noOfFlights;
    }

    public void setFeasibilityChecker(AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.feasibilityChecker = feasibilityChecker;
    }

    @Override
    public boolean checkFlightFeasibility(int flightValue, int gate) {
        return feasibilityChecker.checkFlightFeasibility(flightValue, gate);
    }

    @Override
    public boolean checkFeasibility() {
        return feasibilityChecker.checkChromosomeFeasibility(this);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int g = 0; g < noOfGates; g++) {
            result.append(g + ": ");
            for (int f = 0; f < maxNoFlights; f++) {
                result.append(getGene(g, f) + ", ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public AbsolutePositionChromosome copy() {
        AbsolutePositionChromosome chromosome = new AbsolutePositionChromosome(noOfGates, maxNoFlights);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);
        chromosome.setNoOfFlightsPerGate(Arrays.copyOf(noOfFlights, noOfFlights.length));
        chromosome.setFeasibilityChecker(feasibilityChecker);

        chromosome.setCurrentFlightStarts(new HashMap<>(currentFlightStarts));
        chromosome.setCurrentFlightEnds(new HashMap<>(currentFlightEnds));

        return chromosome;
    }

    // we save current start time for that particular flight on that particular stand in this assignment
    public void incrementCurrentStartAndEnd(int gate, int flightIdx, int amount) {
        int flightNo = getGene(gate, flightIdx);
        int newStart = currentFlightStarts.get(flightNo) + amount;
        currentFlightStarts.put(flightNo, newStart);
        int newEnd = currentFlightEnds.get(flightNo) + amount;
        currentFlightEnds.put(flightNo, newEnd);
    }

    public int getCurrentFlightStart(int gate, int flightIdx) {
        return currentFlightStarts.get(getGene(gate, flightIdx));
    }

    public int getCurrentFlightEnd(int gate, int flightIdx) {
        return currentFlightEnds.get(getGene(gate, flightIdx));
    }

    public void calculateCurrentFlightStarts(FlightStorage flightStorage) {
        int previousNo = -1;
        Flight previousFlight = null;
        for (int gate = 0; gate < noOfGates; gate++) {
            for (int flightIdx = 0; flightIdx < noOfFlights[gate]; flightIdx++) {
                int flightNo = getGene(gate, flightIdx);
                Flight f = flightStorage.getFlightByNumber(flightNo);

                if (flightIdx == 0) {
                    currentFlightStarts.put(flightNo, f.getStart());
                    currentFlightEnds.put(flightNo, f.getEnd());
                    previousNo = flightNo;
                    previousFlight = f;
                } else {
                    int availableTime = currentFlightStarts.get(previousNo) + previousFlight.getLength();
                    int start = f.getStart();

                    if (availableTime > start) {
                        currentFlightStarts.put(flightNo, availableTime);
                        currentFlightEnds.put(flightNo, availableTime + f.getLength());
                    } else {
                        currentFlightStarts.put(flightNo, start);
                        currentFlightEnds.put(flightNo, start + f.getLength());
                    }
                    previousNo = flightNo;
                }
            }
        }
    }

    public void applyStandClosure(StandClosure closure, int standNo) {
        for (int f = 0; f < getNoOfFlights(standNo); f++) {
            int currentEnd = getCurrentFlightEnd(standNo, f);

            if (currentEnd >= closure.getStart()) {
                int currentStart = getCurrentFlightStart(standNo, f);
                int delay = closure.getLength();

                if (currentStart < closure.getStart())
                    delay = delay + (closure.getStart() - currentStart); // we also have to consider the gap between planned start and closure

                for (int lateFlight = f; lateFlight < getNoOfFlights(standNo); lateFlight++) {
                    incrementCurrentStartAndEnd(standNo, lateFlight, delay);
                }
                break;
            }
        }
    }

    public void setCurrentFlightStarts(Map<Integer, Integer> currentFlightStarts) {
        this.currentFlightStarts = currentFlightStarts;
    }

    public void setCurrentFlightEnds(Map<Integer, Integer> currentFlightEnds) {
        this.currentFlightEnds = currentFlightEnds;
    }

    public void applyConditionalStandClosure(ConditionalStandClosure conditionalClosure, int standNo) {
        StandClosure closure = new StandClosure(conditionalClosure.getStandId(), conditionalClosure.getStart(), conditionalClosure.getEnd());
        applyStandClosure(closure, standNo);
    }
}
