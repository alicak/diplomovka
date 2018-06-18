package sk.upjs.ics.diplomovka.data.models.data.disruptions;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.ClosureConditionDataModel;
import sk.upjs.ics.diplomovka.data.parser.Types;

public class StandConditionallyClosedDisruptionDataModel extends DisruptionDataModel {

    private int standId;
    private int start;
    private int end;
    private ClosureConditionDataModel condition;

    public StandConditionallyClosedDisruptionDataModel(int id, int standId, int start, int end, ClosureConditionDataModel condition) {
        this.id = id;
        this.type = Types.Disruption.STAND_CONDITIONALLY_CLOSED;

        this.standId = standId;
        this.start = start;
        this.end = end;
        this.condition = condition;
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

    public ClosureConditionDataModel getCondition() {
        return condition;
    }
}
