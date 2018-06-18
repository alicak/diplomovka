package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.parser.Types;

public class StandTemporarilyClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;
    private int start;
    private int end;

    public StandTemporarilyClosedDisruptionDataModel(int id, int standId, int start, int end) {
        this.id = id;
        this.type = Types.Disruption.STAND_TEMPORARILY_CLOSED;
        this.standId = standId;
        this.start = start;
        this.end = end;
    }

    public int getStandId() {
        return standId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
