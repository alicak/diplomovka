package sk.upjs.ics.diplomovka.data.stands;

import java.util.Collections;
import java.util.Map;

public class StandAttributes {

    private Map<Integer, String> gates = Collections.emptyMap();
    private DistancesMatrix standsDistances;
    private DistancesMatrix gateDistances;

    public Map<Integer, String> getGates() {
        return gates;
    }

    public String getGateById(int id) {
        return gates.get(id);
    }

    public double getGatesDistance(int id1, int id2) {
        return gateDistances.getDistance(id1, id2);
    }

    public double getStandsDistance(int id1, int id2) {
        return standsDistances.getDistance(id1, id2);
    }

    public StandAttributes setGates(Map<Integer, String> gates) {
        this.gates = gates;
        return this;
    }

    public StandAttributes setStandsDistances(DistancesMatrix standsDistances) {
        this.standsDistances = standsDistances;
        return this;
    }

    public StandAttributes setGateDistances(DistancesMatrix gateDistances) {
        this.gateDistances = gateDistances;
        return this;
    }
}
