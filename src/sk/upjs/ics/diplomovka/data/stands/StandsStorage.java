package sk.upjs.ics.diplomovka.data.stands;

import java.util.*;

public class StandsStorage {

    public static final int STAND_NOT_FOUND = -1;

    private Map<Integer, AircraftStand> stands;
    private int[] standsIds;
    private Map<String, AircraftStand> gatesToStands;
    private Map<Integer, List<StandClosure>> closures; // ids to closures

    public StandsStorage(Map<Integer, AircraftStand> stands, Map<String, AircraftStand> gatesToStands) {
        this.stands = stands;
        this.gatesToStands = gatesToStands;
        initializeStands(stands);
        initializeClosures();
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

    private int[] initializeStands(Map<Integer, AircraftStand> stands) {
        standsIds = new int[stands.size()];

        for (int j = 0; j < standsIds.length; j++) {
            standsIds[j] = j;
        }

        return standsIds;
    }

    private void initializeClosures() {
        closures = new HashMap<>();
        for (int i = 0; i < standsIds.length; i++) {
            closures.put(standsIds[i], new LinkedList<>());
        }
    }

    public List<StandClosure> getClosuresForStand(int standNo) {
        return closures.get(getStandByNumber(standNo).getId());
    }

    public void addClosure(StandClosure closure) { // TODO: What about cancelled closures? Shall we use some ids?
        closures.get(closure.getStandId()).add(closure);
    }
}
