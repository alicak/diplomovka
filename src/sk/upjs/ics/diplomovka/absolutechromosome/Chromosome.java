package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
    private static final int FITNESS_NOT_SET = -1;
    public static final int EMPTY_GENE = -1;

    private List<Integer> genes;
    private double fitness = -1;
    private int noOfGates;
    private int[] noOfFlights; // number of flights per gate
    private int maxNoFlights;
    private AbsolutePositionFeasibilityChecker feasibilityChecker;
    private Map<Integer, Integer> currentFlightStarts = new HashMap<>();
    private Map<Integer, Integer> currentFlightEnds = new HashMap<>();

    public Chromosome() {
        genes = new ArrayList<>();
    }

    public Chromosome(List<Integer> genes) {
        this.genes = genes;
    }

    public Chromosome(int noOfGates, int maxNoFlights) {
        this.noOfGates = noOfGates;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[noOfGates];
    }

    protected int getIndex(int gate, int flight) {
        return gate * maxNoFlights + flight;
    }

    public int getGene(int gate, int flight) {
        return getGene(getIndex(gate, flight));
    }

    public int getGene(int position) {
        return genes.get(position);
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public void setGene(int position, int gene) {
        genes.set(position, gene);
        resetFitness();
    }

    public void setGene(int gate, int flight, int flightValue) {
        int oldValue = getGene(gate, flight);
        if (flightValue != EMPTY_GENE && oldValue == EMPTY_GENE) {
            noOfFlights[gate]++;
        }
        setGene(getIndex(gate, flight), flightValue);
        resetFitness();
    }

    public void setGenes(List<Integer> genes) {
        this.genes = genes;
        resetFitness();
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public boolean hasFitness() {
        return fitness != FITNESS_NOT_SET;
    }

    public void resetFitness() {
        fitness = FITNESS_NOT_SET;
    }

    public void prepareForFitnessCalculation(GeneralStorage storage) {
        calculateCurrentFlightStarts(storage);
        applyAllClosures(storage);
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

    public int addFlight(int flightValue) {
        List<Integer> newGenes = new ArrayList<>(genes.size() + maxNoFlights + 1);

        for (int g = 0; g < noOfGates; g++) {
            for (int f = 0; f < maxNoFlights; f++) {
                newGenes.add(getGene(g, f));
            }
            newGenes.add(EMPTY_GENE);
        }
        setGenes(newGenes);
        maxNoFlights++;

        int gateForFlight = Utils.randomInt(noOfGates);
        addNextFlight(gateForFlight, flightValue);

        return gateForFlight;
    }

    public void removeFlight(int flightValue) {
        FlightPosition position = findPosition(flightValue);
        removeFlightFromGenes(position.getGate(), position.getFlight());

        Integer[] newGenes = new Integer[(maxNoFlights - 1) * noOfGates];
        for (int g = 0; g < noOfGates; g++) {
            for (int f = 0; f < maxNoFlights - 1; f++) {
                int idx = g * (maxNoFlights - 1) + f;
                int oldGene = getGene(g, f);
                newGenes[idx] = oldGene;
            }
        }
        setGenes(Arrays.asList(newGenes));

        maxNoFlights--;
    }

    public void removeFlightFromGenes(int gate, int flight) {
        for (int f = flight; f < noOfFlights[gate]; f++) {
            setGene(gate, f, getGene(gate, f + 1));
        }
        noOfFlights[gate]--;
        resetFitness();
    }

    public int addGate() {
        for (int i = 0; i < maxNoFlights; i++) {
            genes.add(EMPTY_GENE);
        }

        int newNumber = noOfGates;
        noOfGates++;

        noOfFlights = Arrays.copyOf(noOfFlights, noOfGates);
        noOfFlights[newNumber] = 0;

        return newNumber;
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

        int lastGate = noOfGates - 1;

        for (int i = 0; i < maxNoFlights; i++) {
            setGene(gate, getGene(lastGate, i));
        }
        genes = genes.subList(0, genes.size() - maxNoFlights);

        noOfFlights[gate] = noOfFlights[lastGate];
        noOfFlights = Arrays.copyOf(noOfFlights, noOfFlights.length - 1);

        noOfGates--;
        resetFitness();
    }

    public int getNoOfFlights(int gate) {
        return noOfFlights[gate];
    }

    public int getNoOfFlights() {
        return maxNoFlights;
    }

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

    private void setNoOfFlightsPerGate(int[] noOfFlights) {
        this.noOfFlights = noOfFlights;
    }

    public void setFeasibilityChecker(AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.feasibilityChecker = feasibilityChecker;
    }

    public boolean checkFlightFeasibility(int flightValue, int gate) {
        return feasibilityChecker.checkFlightFeasibility(flightValue, gate);
    }

    public boolean checkFeasibility() {
        return feasibilityChecker.checkChromosomeFeasibility(this);
    }

    /**
     * we save current start time for that particular flight on that particular stand in this assignment
     *
     * @param gate
     * @param flightIdx
     * @param amount
     */
    public void incrementCurrentStartAndEnd(int gate, int flightIdx, int amount) {
        int flightNo = getGene(gate, flightIdx);
        int newStart = currentFlightStarts.get(flightNo) + amount;
        currentFlightStarts.put(flightNo, newStart);
        int newEnd = currentFlightEnds.get(flightNo) + amount;
        currentFlightEnds.put(flightNo, newEnd);
    }

    /**
     * also calculates ends
     */
    public void calculateCurrentFlightStarts(GeneralStorage storage) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        int previousNo = -1;
        for (int gate = 0; gate < noOfGates; gate++) {

            for (int flightIdx = 0; flightIdx < noOfFlights[gate]; flightIdx++) {
                int flightNo = getGene(gate, flightIdx);
                Flight f = flightStorage.getFlight(flightNo);

                int availableTime = (flightIdx == 0) ? standsStorage.getStandAvailabilityTime(gate) : currentFlightEnds.get(previousNo);
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

    public int getCurrentFlightStart(int gate, int flightIdx) {
        return currentFlightStarts.get(getGene(gate, flightIdx));
    }

    public int getCurrentFlightEnd(int gate, int flightIdx) {
        return currentFlightEnds.get(getGene(gate, flightIdx));
    }

    public void setCurrentFlightStarts(Map<Integer, Integer> currentFlightStarts) {
        this.currentFlightStarts = currentFlightStarts;
    }

    public void setCurrentFlightEnds(Map<Integer, Integer> currentFlightEnds) {
        this.currentFlightEnds = currentFlightEnds;
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

    public void applyAllClosures(GeneralStorage storage) {
        for (int g = 0; g < noOfGates; g++) {
            Collection<StandClosure> closuresForStand = storage.getStandsStorage().getClosuresForStand(g);
            for (StandClosure closure : closuresForStand) {
                applyStandClosure(closure, g);
            }
            Collection<ConditionalStandClosure> conditionalClosuresForStand = storage.getStandsStorage().getConditionalClosuresForStand(g);
            for(ConditionalStandClosure closure: conditionalClosuresForStand) {
                applyConditionalStandClosure(closure, g, storage.getFlightStorage());
            }
        }
    }

    public void applyConditionalStandClosure(ConditionalStandClosure closure, int standNo, FlightStorage storage) {
        for (int f = 0; f < getNoOfFlights(standNo); f++) {
            int currentStart = getCurrentFlightStart(standNo, f);
            int currentEnd = getCurrentFlightEnd(standNo, f);
            Flight flight = storage.getFlight(f);

            if (!closure.checkFlight(flight, currentStart, currentEnd)) {
                int delay = closure.getEnd() - currentStart;
                for (int lateFlight = f; lateFlight < getNoOfFlights(standNo); lateFlight++) {
                    incrementCurrentStartAndEnd(standNo, lateFlight, delay);
                }
                break;
            }
        }
    }

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

    public Chromosome copy() {
        Chromosome chromosome = new Chromosome(noOfGates, maxNoFlights);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);
        chromosome.setNoOfFlightsPerGate(Arrays.copyOf(noOfFlights, noOfFlights.length));
        chromosome.setFeasibilityChecker(feasibilityChecker);

        chromosome.setCurrentFlightStarts(new HashMap<>(currentFlightStarts));
        chromosome.setCurrentFlightEnds(new HashMap<>(currentFlightEnds));

        return chromosome;
    }

    @Override
    public int compareTo(Chromosome c) {
        if (this.getFitness() == c.getFitness())
            return 0;

        if (this.getFitness() < c.getFitness())
            return 1; // this object is worse, thus later in order

        return -1;
    }
}
