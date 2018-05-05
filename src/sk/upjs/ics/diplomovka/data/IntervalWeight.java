package sk.upjs.ics.diplomovka.data;

public class IntervalWeight {

    private int start;
    private int end;
    private double weight;

    public IntervalWeight(int start, int end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public boolean contains(int number) {
        return number >= start && number < end;
    }

    public double getWeight() {
        return weight;
    }
}
