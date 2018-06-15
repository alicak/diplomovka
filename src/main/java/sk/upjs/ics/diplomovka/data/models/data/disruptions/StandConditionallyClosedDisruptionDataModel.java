package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.ClosureConditionDataModel;

public class StandConditionallyClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;
    private int start;
    private int end;
    private ClosureConditionDataModel condition;

    public int getStandId() {
        return standId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public ClosureConditionDataModel getCondition() {
        return condition;
    }
}
