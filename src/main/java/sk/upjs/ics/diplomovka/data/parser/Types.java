package sk.upjs.ics.diplomovka.data.parser;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.*;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.*;
import sk.upjs.ics.diplomovka.disruption.*;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.CategoryClosureCondition;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.EngineTypeClosureCondition;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.WeightClosureCondition;
import sk.upjs.ics.diplomovka.disruption.closures.conditions.WingspanClosureCondition;

public final class Types {

    public static final String TYPE_FIELD = "type";

    public class ClosureCondition {
        public static final String ENGINE_TYPE = "engineType";
        public static final String CATEGORY = "category";
        public static final String WINGSPAN = "wingspan";
        public static final String WEIGHT = "weight";
    }

    public class Disruption {
        public static final String FLIGHT_ADDED = "flightAdded";
        public static final String FLIGHT_CANCELLED = "flightCancelled";
        public static final String FLIGHT_DELAYED = "flightDelayed";
        public static final String STAND_CLOSED = "standClosed";
        public static final String STAND_TEMPORARILY_CLOSED = "standTemporarilyClosed";
        public static final String STAND_CONDITIONALLY_CLOSED = "standConditionallyClosed";
    }

    public static sk.upjs.ics.diplomovka.disruption.closures.conditions.ClosureCondition getConditionFromModel(ClosureConditionDataModel model) {
        switch (model.getType()) {
            case ClosureCondition.ENGINE_TYPE:
                return new EngineTypeClosureCondition((EngineTypeClosureConditionDataModel) model);
            case ClosureCondition.CATEGORY:
                return new CategoryClosureCondition((CategoryClosureConditionDataModel) model);
            case ClosureCondition.WEIGHT:
                return new WeightClosureCondition((WeightClosureConditionDataModel) model);
            case ClosureCondition.WINGSPAN:
                return new WingspanClosureCondition((WingspanClosureConditionDataModel) model);
        }
        throw new IllegalArgumentException(model.getType());
    }

    public static sk.upjs.ics.diplomovka.disruption.Disruption getDisruptionFromModel(DisruptionDataModel model, GeneralStorage storage) {
        switch (model.getType()) {
            case Disruption.FLIGHT_ADDED:
                return new FlightAddedDisruption((FlightAddedDisruptionDataModel) model, storage);
            case Disruption.FLIGHT_CANCELLED:
                return new FlightCancelledDisruption((FlightCancelledDisruptionDataModel) model, storage.getFlightStorage());
            case Disruption.FLIGHT_DELAYED:
                return new FlightDelayedDisruption((FlightDelayedDisruptionDataModel) model, storage.getFlightStorage());
            case Disruption.STAND_CLOSED:
                return new StandClosedDisruption((StandClosedDisruptionDataModel) model, storage.getStandsStorage());
            case Disruption.STAND_CONDITIONALLY_CLOSED:
                return new StandConditionallyClosedDisruption((StandConditionallyClosedDisruptionDataModel) model, storage);
            case Disruption.STAND_TEMPORARILY_CLOSED:
                return new StandTemporarilyClosedDisruption((StandTemporarilyClosedDisruptionDataModel) model, storage.getStandsStorage());
        }
        throw new IllegalArgumentException(model.getType());
    }
}
