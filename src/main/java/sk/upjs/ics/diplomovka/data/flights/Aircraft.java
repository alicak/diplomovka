package sk.upjs.ics.diplomovka.data.flights;

public class Aircraft {

    private int id;
    private String name;
    private double wingspan; // in meters
    private int engineType;
    private int weight; // in kilograms

    public Aircraft(int id, String name, double wingspan, int engineType, int weight) {
        this.id = id;
        this.name = name;
        this.wingspan = wingspan;
        this.engineType = engineType;
        this.weight = weight;
    }

    public int getId() {
        return id;
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

    public int getWeight() {
        return weight;
    }
}
