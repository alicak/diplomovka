package sk.upjs.ics.diplomovka.data.flights;

public class Aircraft {

    private double wingspan; // in meters
    private int turnaroundTime; // in minutes

    public Aircraft(double wingspan, int turnaroundTime) {
        this.wingspan = wingspan;
        this.turnaroundTime = turnaroundTime;
    }

    public double getWingspan() {
        return wingspan;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }
}
