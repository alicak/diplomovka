package sk.upjs.ics.diplomovka.data.models.data;

import java.util.List;

public class StandDataModel {
    private int id;
    private double maxWingspan;
    private double maxWeight;
    private List<Integer> flightCategories;
    private List<Integer> engineTypes;
    private List<Integer> gates;

    public int getId() {
        return id;
    }

    public double getMaxWingspan() {
        return maxWingspan;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public List<Integer> getFlightCategories() {
        return flightCategories;
    }

    public List<Integer> getEngineTypes() {
        return engineTypes;
    }

    public List<Integer> getGates() {
        return gates;
    }
}
