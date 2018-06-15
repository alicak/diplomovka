package sk.upjs.ics.diplomovka.data.models.data.disruptions;

public class FlightDelayedDisruptionDataModel extends DisruptionDataModel {

    private int flightId;
    private int delay;

    public int getFlightId() {
        return flightId;
    }

    public int getDelay() {
        return delay;
    }
}
