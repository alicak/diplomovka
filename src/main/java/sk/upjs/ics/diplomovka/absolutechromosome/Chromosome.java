package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;
import sk.upjs.ics.diplomovka.storage.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.storage.stands.closures.StandClosure;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.*;

public class Chromosome implements Comparable<Chromosome> {
    private static final int FITNESS_NOT_SET = -1;
    public static final int EMPTY_GENE = -1;

    private List<Integer> genes;
    private double fitness = -1;
    private int noOfStands;
    private int[] noOfFlights; // number of flights per stand
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

    public Chromosome(int noOfStands, int maxNoFlights) {
        this.noOfStands = noOfStands;
        this.maxNoFlights = maxNoFlights;
        this.noOfFlights = new int[noOfStands];
    }

    protected int getIndex(int stand, int flight) {
        return stand * maxNoFlights + flight;
    }

    public int getGene(int stand, int flight) {
        return getGene(getIndex(stand, flight));
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

    public void setGene(int stand, int flight, int flightValue) {
        int oldValue = getGene(stand, flight);
        if (flightValue != EMPTY_GENE && oldValue == EMPTY_GENE) {
            noOfFlights[stand]++;
        }
        setGene(getIndex(stand, flight), flightValue);
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

    public void addNextFlight(int stand, int flightValue) {
        setGene(stand, noOfFlights[stand], flightValue);
    }

    public void insertFlight(int stand, int flight, int flightValue) {
        boolean append = true;
        for (int f = noOfFlights[stand] - 1; f >= flight; f--) {
            setGene(getIndex(stand, f + 1), getGene(stand, f));
            append = false;
        }

        setGene(stand, flight, flightValue);
        if (!append) // if the flight was appended, the count was already incremented in previous line
            noOfFlights[stand]++;
    }

    public int addFlight(int flightValue) {
        List<Integer> newGenes = new ArrayList<>(genes.size() + maxNoFlights + 1);

        for (int s = 0; s < noOfStands; s++) {
            for (int f = 0; f < maxNoFlights; f++) {
                newGenes.add(getGene(s, f));
            }
            newGenes.add(EMPTY_GENE);
        }
        setGenes(newGenes);
        maxNoFlights++;

        currentFlightStarts.put(flightValue, 0);
        currentFlightEnds.put(flightValue,0);

        int standForFlight = -1;
        boolean feasible = false;
        while (!feasible) {
            standForFlight = Utils.randomInt(noOfStands);
            feasible = checkFlightFeasibility(flightValue, standForFlight);
        }

        addNextFlight(standForFlight, flightValue);
        return standForFlight;
    }

    public void removeFlight(int flightValue) {
        FlightPosition position = findPosition(flightValue);
        removeFlightFromGenes(position.getStand(), position.getFlight());

        Integer[] newGenes = new Integer[(maxNoFlights - 1) * noOfStands];
        for (int s = 0; s < noOfStands; s++) {
            for (int f = 0; f < maxNoFlights - 1; f++) {
                int idx = s * (maxNoFlights - 1) + f;
                int oldGene = getGene(s, f);
                newGenes[idx] = oldGene;
            }
        }
        setGenes(Arrays.asList(newGenes));

        maxNoFlights--;
    }

    public void removeFlightFromGenes(int stand, int flight) {
        for (int f = flight; f < noOfFlights[stand]; f++) {
            setGene(stand, f, getGene(stand, f + 1));
        }
        noOfFlights[stand]--;
        resetFitness();
    }

    public void removeStand(int stand) {
        for (int i = 0; i < noOfFlights[stand]; i++) { // assign flights to remaining gates
            int gene = getGene(stand, i);
            int newStand = -1;
            boolean feasible = false;

            while (!feasible) {
                newStand = Utils.randomInt(noOfStands);
                if (newStand == stand)
                    continue;
                feasible = checkFlightFeasibility(gene, stand);
            }
            int position = Utils.randomInt(noOfFlights[newStand]);
            insertFlight(newStand, position, gene);
        }

        int lastStand = noOfStands - 1;

        for (int i = 0; i < maxNoFlights; i++) {
            setGene(getIndex(stand, i), getGene(lastStand, i));
        }
        genes = genes.subList(0, genes.size() - maxNoFlights);

        noOfFlights[stand] = noOfFlights[lastStand];
        noOfFlights = Arrays.copyOf(noOfFlights, noOfFlights.length - 1);

        noOfStands--;
        resetFitness();
    }

    public int getNoOfFlights(int stand) {
        return noOfFlights[stand];
    }

    public int getNoOfFlights() {
        return maxNoFlights;
    }

    public int getNoOfStands() {
        return noOfStands;
    }

    public FlightPosition findPosition(int flightValue) {
        for (int s = 0; s < noOfStands; s++) {
            for (int f = 0; f < noOfFlights[s]; f++) {
                if (getGene(s, f) == flightValue)
                    return new FlightPosition(s, f);
            }
        }
        return new FlightPosition(-1, -1); // flight was not found
    }

    private void setNoOfFlightsPerStand(int[] noOfFlights) {
        this.noOfFlights = noOfFlights;
    }

    public void setFeasibilityChecker(AbsolutePositionFeasibilityChecker feasibilityChecker) {
        this.feasibilityChecker = feasibilityChecker;
    }

    public boolean checkFlightFeasibility(int flightValue, int stand) {
        return feasibilityChecker.checkFlightFeasibility(flightValue, stand);
    }

    public boolean checkFeasibility() {
        return feasibilityChecker.checkChromosomeFeasibility(this);
    }

    // we save current start time for that particular flight on that particular stand in this assignment
    public void incrementCurrentStartAndEnd(int stand, int flightIdx, int amount) {
        int flightNo = getGene(stand, flightIdx);
        int newStart = currentFlightStarts.get(flightNo) + amount;
        currentFlightStarts.put(flightNo, newStart);
        int newEnd = currentFlightEnds.get(flightNo) + amount;
        currentFlightEnds.put(flightNo, newEnd);
    }

    // also the ends
    public void calculateCurrentFlightStarts(GeneralStorage storage) {
        FlightStorage flightStorage = storage.getFlightStorage();
        StandsStorage standsStorage = storage.getStandsStorage();

        int previousNo = -1;
        for (int stand = 0; stand < noOfStands; stand++) {

            for (int flightIdx = 0; flightIdx < noOfFlights[stand]; flightIdx++) {
                int flightNo = getGene(stand, flightIdx);
                Flight f = flightStorage.getFlight(flightNo);

                int availableTime = (flightIdx == 0) ? standsStorage.getStandAvailabilityTime(stand) : currentFlightEnds.get(previousNo);
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

    public int getCurrentFlightStart(int stand, int flightIdx) {
        return currentFlightStarts.get(getGene(stand, flightIdx));
    }

    public int getCurrentFlightEnd(int stand, int flightIdx) {
        return currentFlightEnds.get(getGene(stand, flightIdx));
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
        for (int s = 0; s < noOfStands; s++) {
            Collection<StandClosure> closuresForStand = storage.getStandsStorage().getClosuresForStand(s);
            for (StandClosure closure : closuresForStand) {
                applyStandClosure(closure, s);
            }
            Collection<ConditionalStandClosure> conditionalClosuresForStand = storage.getStandsStorage().getConditionalClosuresForStand(s);
            for (ConditionalStandClosure closure : conditionalClosuresForStand) {
                applyConditionalStandClosure(closure, s, storage.getFlightStorage());
            }
        }
    }

    public void applyConditionalStandClosure(ConditionalStandClosure closure, int standNo, FlightStorage storage) {
        for (int f = 0; f < getNoOfFlights(standNo); f++) {
            int currentStart = getCurrentFlightStart(standNo, f);
            int currentEnd = getCurrentFlightEnd(standNo, f);
            Flight flight = storage.getFlight(getGene(standNo, f));

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
        for (int s = 0; s < noOfStands; s++) {
            result.append(s + ": ");
            for (int f = 0; f < maxNoFlights; f++) {
                result.append(getGene(s, f) + ", ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Chromosome copy() {
        Chromosome chromosome = new Chromosome(noOfStands, maxNoFlights);

        chromosome.setFitness(getFitness());
        List<Integer> genes = new ArrayList<>(getGenes());
        chromosome.setGenes(genes);
        chromosome.setNoOfFlightsPerStand(Arrays.copyOf(noOfFlights, noOfFlights.length));
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
