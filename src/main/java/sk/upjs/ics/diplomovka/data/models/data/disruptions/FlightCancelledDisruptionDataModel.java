package sk.upjs.ics.diplomovka.data.models.data.disruptions;

public class FlightCancelledDisruptionDataModel extends DisruptionDataModel {
    private int flightId;

    public FlightCancelledDisruptionDataModel(int id, int flightId) {
        this.id = id;
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }
}
