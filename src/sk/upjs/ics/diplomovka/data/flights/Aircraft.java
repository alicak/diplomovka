package sk.upjs.ics.diplomovka.data.flights;

public class Aircraft {

    private String name;
    private double wingspan; // in meters

    public Aircraft(String name, double wingspan) {
        this.name = name;
        this.wingspan = wingspan;
    }

    public String getName() {
        return name;
    }

    public double getWingspan() {
        return wingspan;
    }

}
