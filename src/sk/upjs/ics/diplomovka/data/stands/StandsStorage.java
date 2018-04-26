package sk.upjs.ics.diplomovka.data.stands;

import sk.upjs.ics.diplomovka.data.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.data.stands.closures.StandClosure;

import java.util.*;

public class StandsStorage {

    public static final int STAND_NOT_FOUND = -1;

    private Map<Integer, AircraftStand> stands;
    private int[] standsIds;
    private Map<String, AircraftStand> gatesToStands;
    private Map<Integer, List<StandClosure>> closures; // ids to closures
    private Map<Integer, List<ConditionalStandClosure>> conditionalClosures;
    private Map<Integer, Integer> availabilityTimes;

    public StandsStorage(Map<Integer, AircraftStand> stands, Map<String, AircraftStand> gatesToStands) {
        this.stands = stands;
        this.gatesToStands = gatesToStands;
        initializeStands(stands);
        initializeClosures();
        initializeAvailabilityTimes();
    }

    private StandsStorage(Map<Integer, AircraftStand> stands, int[] standsIds, Map<String, AircraftStand> gatesToStands, Map<Integer,
            List<StandClosure>> closures, Map<Integer, List<ConditionalStandClosure>> conditionalClosures, Map<Integer, Integer> availabilityTimes) {
        this.stands = stands;
        this.standsIds = standsIds;
        this.gatesToStands = gatesToStands;
        this.closures = closures;
        this.conditionalClosures = conditionalClosures;
        this.availabilityTimes = availabilityTimes;
    }

    public AircraftStand getStandById(int id) {
        return stands.get(id);
    }

    public AircraftStand getStandByNumber(int number) {
        return stands.get(standsIds[number]);
    }

    public AircraftStand getStandByGate(String gate) {
        return gatesToStands.get(gate);
    }

    public int getStandIdByGate(String gate) {
        return getStandByGate(gate).getId();
    }

    public int getNumberById(int id) {
        for (int i = 0; i < standsIds.length; i++) {
            if (standsIds[i] == id)
                return i;
        }
        return STAND_NOT_FOUND;
    }

    public int addStand(AircraftStand stand) {
        return 0; // TODO
    }

    public void removeStand(int standId) {
        if (stands.remove(standId) == null)
            return;

        int length = standsIds.length;

        for (int i = 0; i < length; i++) {
            if (standsIds[i] == standId) {

                for (int j = i; j < length - 1; j++) {
                    standsIds[j] = standsIds[j + 1];
                }

                standsIds = Arrays.copyOf(standsIds, length - 1);
                return;
            }
        }
    }

    public int getNoOfStands() {
        return stands.size();
    }

    public Set<Integer> getStandsIds() {
        return stands.keySet();
    }

    private int[] initializeStands(Map<Integer, AircraftStand> stands) {
        standsIds = new int[stands.size()];

        for (int j = 0; j < standsIds.length; j++) {
            standsIds[j] = j;
        }

        return standsIds;
    }

    private void initializeClosures() {
        closures = new HashMap<>();
        conditionalClosures = new HashMap<>();
        for (int i = 0; i < standsIds.length; i++) {
            closures.put(standsIds[i], new LinkedList<>());
            conditionalClosures.put(standsIds[i], new LinkedList<>());
        }
    }

    private void initializeAvailabilityTimes() {
        availabilityTimes = new HashMap<>();
        for (int i = 0; i < standsIds.length; i++) {
            availabilityTimes.put(standsIds[i], 0);
        }
    }

    public List<StandClosure> getClosuresForStand(int standNo) {
        return closures.get(getStandByNumber(standNo).getId());
    }

    public List<ConditionalStandClosure> getConditionalClosuresForStand(int standNo) {
        return conditionalClosures.get(getStandByNumber(standNo).getId());
    }

    public void addClosure(StandClosure closure) { // TODO: What about cancelled closures? Shall we use some ids?
        closures.get(closure.getStandId()).add(closure);
    }

    public void addConditionalClosure(ConditionalStandClosure closure) { // TODO: What about cancelled closures? Shall we use some ids?
        conditionalClosures.get(closure.getStandId()).add(closure);
    }

    public StandsStorage storageWithNewAvailabilityTimes(Map<Integer, Integer> availabilityTimes) {
        return new StandsStorage(stands, standsIds, gatesToStands, closures, conditionalClosures, availabilityTimes);
    }

    public int getStandAvailabilityTime(int standNo){
        return availabilityTimes.get(getStandByNumber(standNo).getId());
    }
}
