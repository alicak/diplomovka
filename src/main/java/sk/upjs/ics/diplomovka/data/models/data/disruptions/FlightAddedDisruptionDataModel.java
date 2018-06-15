package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;

public class FlightAddedDisruptionDataModel extends DisruptionDataModel {

    private FlightDataModel flight;

    public FlightDataModel getFlight() {
        return flight;
    }
}
