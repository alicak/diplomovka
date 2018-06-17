package sk.upjs.ics.diplomovka.data.models.data.closureconditions;

import java.util.List;

public class EngineTypeClosureConditionDataModel extends ClosureConditionDataModel {
    private List<Integer> engineTypes;

    public EngineTypeClosureConditionDataModel(String type, List<Integer> engineTypes) {
        this.type = type;
        this.engineTypes = engineTypes;
    }

    public List<Integer> getEngineTypes() {
        return engineTypes;
    }
}
