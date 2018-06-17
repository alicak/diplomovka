package sk.upjs.ics.diplomovka.data.models.data.disruptions;

public class StandClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;

    public StandClosedDisruptionDataModel(int id, int standId) {
        this.id = id;
        this.standId = standId;
    }

    public int getStandId() {
        return standId;
    }
}
