package sk.upjs.ics.diplomovka.data.models.data.closureconditions;

public class WingspanClosureConditionDataModel extends ClosureConditionDataModel {

    private double wingspan;

    public WingspanClosureConditionDataModel(String type, double wingspan) {
        this.type = type;
        this.wingspan = wingspan;
    }

    public double getWingspan() {
        return wingspan;
    }
}
