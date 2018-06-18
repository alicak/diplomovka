package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.parser.Types;

public class FlightDelayedDisruptionDataModel extends DisruptionDataModel {

    private int flightId;
    private int delay;

    public FlightDelayedDisruptionDataModel(int id, int flightId, int delay) {
        this.id = id;
        this.type = Types.Disruption.FLIGHT_DELAYED;
        this.flightId = flightId;
        this.delay = delay;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getDelay() {
        return delay;
    }
}
