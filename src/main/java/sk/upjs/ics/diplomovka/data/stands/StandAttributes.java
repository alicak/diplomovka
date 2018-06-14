package sk.upjs.ics.diplomovka.data.stands;

import java.util.Collections;
import java.util.Map;

public class StandAttributes {

    private Map<Integer, String> gates = Collections.emptyMap();

    public Map<Integer, String> getGates() {
        return gates;
    }

    public String getGateById(int id) {
        return gates.get(id);
    }

    public StandAttributes setGates(Map<Integer, String> gates) {
        this.gates = gates;
        return this;
    }

}
