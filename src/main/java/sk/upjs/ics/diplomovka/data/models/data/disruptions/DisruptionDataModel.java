package sk.upjs.ics.diplomovka.data.models.data.disruptions;

public abstract class DisruptionDataModel {
    protected int id;
    protected String type;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
