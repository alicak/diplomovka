package sk.upjs.ics.diplomovka.data.models.data.disruptions;

public class StandTemporarilyClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;
    private int start;
    private int end;

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
