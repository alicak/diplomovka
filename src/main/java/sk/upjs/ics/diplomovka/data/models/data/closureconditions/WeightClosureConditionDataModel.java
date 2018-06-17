package sk.upjs.ics.diplomovka.data.models.data.closureconditions;

public class WeightClosureConditionDataModel extends ClosureConditionDataModel {

    private double weight;

    public WeightClosureConditionDataModel(String type, double weight) {
        this.type = type;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
