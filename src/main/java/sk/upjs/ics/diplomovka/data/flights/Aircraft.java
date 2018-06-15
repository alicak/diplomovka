package sk.upjs.ics.diplomovka.data.flights;

import sk.upjs.ics.diplomovka.data.Attribute;

public class Aircraft extends Attribute {

    private String name;
    private double wingspan; // in meters
    private int engineType;
    private double weight; // in tones

    public Aircraft(int id, String name, double wingspan, int engineType, double weight) {
        this.id = id;
        this.name = name;
        this.wingspan = wingspan;
        this.engineType = engineType;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWingspan() {
        return wingspan;
    }

    public int getEngineType() {
        return engineType;
    }

    public double getWeight() {
        return weight;
    }
}
