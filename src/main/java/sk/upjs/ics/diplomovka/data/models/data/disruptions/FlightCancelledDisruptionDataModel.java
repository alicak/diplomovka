package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.parser.Types;

public class FlightCancelledDisruptionDataModel extends DisruptionDataModel {
    private int flightId;

    public FlightCancelledDisruptionDataModel(int id, int flightId) {
        this.id = id;
        this.type = Types.Disruption.FLIGHT_CANCELLED;
        this.flightId = flightId;
    }

    public int getFlightId() {
        return flightId;
    }
}
