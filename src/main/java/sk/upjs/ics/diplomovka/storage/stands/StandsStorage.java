package sk.upjs.ics.diplomovka.storage.stands;

import sk.upjs.ics.diplomovka.storage.stands.closures.ConditionalStandClosure;
import sk.upjs.ics.diplomovka.storage.stands.closures.StandClosure;

import java.util.*;

public class StandsStorage {

    public static final int STAND_NOT_FOUND = -1;

    private StandAttributes attributes;
    private Map<Integer, Stand> stands;
    private int[] standsIds;
    private Map<Integer, Stand> gatesToStands;
    private Map<Integer, Map<Integer, StandClosure>> closures; // stand ids to closures
    private Map<Integer, Map<Integer, ConditionalStandClosure>> conditionalClosures;
    private Map<Integer, Integer> availabilityTimes;
    private int noOfStandsInUse;

    public StandsStorage(Map<Integer, Stand> stands, Map<Integer, Stand> gatesToStands, StandAttributes attributes) {
        this.stands = stands;
        this.gatesToStands = gatesToStands;
        this.attributes = attributes;
        noOfStandsInUse = stands.size();
        initializeStands();
        initializeClosures();
        initializeAvailabilityTimes();
    }

    private StandsStorage(Map<Integer, Stand> stands, int[] standsIds, Map<Integer, Stand> gatesToStands,
                          StandAttributes attributes,
                          Map<Integer, Map<Integer, StandClosure>> closures,
                          Map<Integer, Map<Integer, ConditionalStandClosure>> conditionalClosures,
                          Map<Integer, Integer> availabilityTimes, int noOfStandsInUse) {
        this(stands, gatesToStands, attributes);
        this.standsIds = standsIds;
        this.closures = closures;
        this.conditionalClosures = conditionalClosures;
        this.availabilityTimes = availabilityTimes;
        this.noOfStandsInUse = noOfStandsInUse;
    }

    public Collection<Stand> getStands() {
        return stands.values();
    }

    public Stand getStandById(int id) {
        return stands.get(id);
    }

    public Stand getStandByNumber(int number) {
        return stands.get(standsIds[number]);
    }

    public int getNumberById(int id) {
        for (int i = 0; i < standsIds.length; i++) {
            if (standsIds[i] == id)
                return i;
        }
        return STAND_NOT_FOUND;
    }

    public void openStand(Stand stand) {
        standsIds[noOfStandsInUse] = stand.getId();
        noOfStandsInUse++;
    }

    public void closeStand(int standId) {
        int length = standsIds.length;

        for (int i = 0; i < length; i++) {
            if (standsIds[i] == standId) {
                standsIds[i] = standsIds[noOfStandsInUse - 1];
                noOfStandsInUse--;
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

    private int[] initializeStands() {
        standsIds = new int[stands.size()];

        int j = 0;
        for(Stand stand: stands.values()) {
            standsIds[j] = stand.getId();
            j++;
        }

        return standsIds;
    }

    private void initializeClosures() {
        closures = new HashMap<>();
        conditionalClosures = new HashMap<>();
        for (int i = 0; i < standsIds.length; i++) {
            closures.put(standsIds[i], new HashMap<>());
            conditionalClosures.put(standsIds[i], new HashMap<>());
        }
    }

    private void initializeAvailabilityTimes() {
        availabilityTimes = new HashMap<>();
        for (int i = 0; i < standsIds.length; i++) {
            availabilityTimes.put(standsIds[i], 0);
        }
    }

    public Collection<StandClosure> getClosuresForStand(int standNo) {
        return closures.get(getStandByNumber(standNo).getId()).values();
    }

    public Collection<ConditionalStandClosure> getConditionalClosuresForStand(int standNo) {
        return conditionalClosures.get(getStandByNumber(standNo).getId()).values();
    }

    public void addClosure(StandClosure closure) {
        closures.get(closure.getStandId()).put(closure.getId(), closure);
    }

    public void removeClosure(int id, int standId) {
        closures.get(standId).remove(id);
    }

    public void addConditionalClosure(ConditionalStandClosure closure) {
        conditionalClosures.get(closure.getStandId()).put(closure.getId(), closure);
    }

    public void removeConditionalClosure(int id, int standId) {
        conditionalClosures.get(standId).remove(id);
    }

    public StandsStorage storageWithNewAvailabilityTimes(Map<Integer, Integer> availabilityTimes) {
        return new StandsStorage(stands, standsIds, gatesToStands, attributes, closures, conditionalClosures,
                availabilityTimes, noOfStandsInUse);
    }

    public int getStandAvailabilityTime(int standNo) {
        return availabilityTimes.get(getStandByNumber(standNo).getId());
    }

    public double getGatesDistance(int id1, int id2) {
        return attributes.getGatesDistance(id1, id2);
    }

    public double getStandsDistance(int no1, int no2) {
        int id1 = standsIds[no1];
        int id2 = standsIds[no2];
        return attributes.getStandsDistance(id1, id2);
    }

    public String getGateById(int gateId) {
        return attributes.getGateById(gateId);
    }

    public Set<Integer> getGates() {
        return attributes.getGates().keySet();
    }
}
