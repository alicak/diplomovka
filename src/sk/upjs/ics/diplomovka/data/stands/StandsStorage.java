package sk.upjs.ics.diplomovka.data.stands;

import java.util.List;
import java.util.Map;

public class StandsStorage {

    private List<AircraftStand> stands; // TODO: we assume stands are numbered from 0
    private int[] standsIds;
    private Map<String, AircraftStand> gatesToStands;

    public StandsStorage(List<AircraftStand> stands, int[] standsIds, Map<String, AircraftStand> gatesToStands) {
        this.stands = stands;
        this.standsIds = standsIds;
        this.gatesToStands = gatesToStands;
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

    public int getStandNoByGate(String gate) {
        return stands.indexOf(getStandByGate(gate));
    }


    public int getNoOfStands() {
        return stands.size();
    }
}
