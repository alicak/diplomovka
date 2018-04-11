package sk.upjs.ics.diplomovka.data.flights;

public class Aircraft {

    private String name;
    private double wingspan; // in meters
    private EngineType engineType;

    public enum EngineType {
        TURBOPROP,
        JET
    }

    public Aircraft(String name, double wingspan, EngineType engineType) {
        this.name = name;
        this.wingspan = wingspan;
        this.engineType = engineType;
    }

    public String getName() {
        return name;
    }

    public double getWingspan() {
        return wingspan;
    }

    public EngineType getEngineType() {
        return engineType;
    }
}
