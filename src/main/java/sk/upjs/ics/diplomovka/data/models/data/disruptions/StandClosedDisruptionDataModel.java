package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.parser.Types;

public class StandClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;

    public StandClosedDisruptionDataModel(int id, int standId) {
        this.id = id;
        this.type = Types.Disruption.STAND_CLOSED;
        this.standId = standId;
    }

    public int getStandId() {
        return standId;
    }
}
