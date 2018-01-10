package sk.upjs.ics.diplomovka.data.flights;

public class FlightId {
    private static int id = 0;

    public static int getId() {
        return id++;
    }

    public static void reset() {
        id = 0;
    }
}
