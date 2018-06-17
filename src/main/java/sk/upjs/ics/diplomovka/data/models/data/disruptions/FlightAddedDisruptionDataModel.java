package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;

public class FlightAddedDisruptionDataModel extends DisruptionDataModel {

    private FlightDataModel flight;

    public FlightAddedDisruptionDataModel(int id, FlightDataModel flight) {
        this.id = id;
        this.flight = flight;
    }

    public FlightDataModel getFlight() {
        return flight;
    }
}
