package sk.upjs.ics.diplomovka.storage.stands;

import java.util.Map;

public class DistancesMatrix {
    private Map<Integer, Map<Integer, Double>> distances;

    public DistancesMatrix(Map<Integer, Map<Integer, Double>> distances) {
        this.distances = distances;
    }

    public double getDistance(int id1, int id2) {
        return distances.get(id1).get(id2);
    }

}
