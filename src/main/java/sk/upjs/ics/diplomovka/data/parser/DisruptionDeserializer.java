package sk.upjs.ics.diplomovka.data.parser;

import com.google.gson.*;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.*;

import java.lang.reflect.Type;

public class DisruptionDeserializer implements JsonDeserializer<DisruptionDataModel> {
    @Override
    public DisruptionDataModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonType = jsonObject.get(Types.TYPE_FIELD);

        String disruptionType = jsonType.getAsString();

        switch (disruptionType) {
            case Types.Disruption.FLIGHT_ADDED:
                return context.deserialize(jsonElement, FlightAddedDisruptionDataModel.class);
            case Types.Disruption.FLIGHT_CANCELLED:
                return context.deserialize(jsonElement, FlightCancelledDisruptionDataModel.class);
            case Types.Disruption.FLIGHT_DELAYED:
                return context.deserialize(jsonElement, FlightDelayedDisruptionDataModel.class);
            case Types.Disruption.STAND_CLOSED:
                return context.deserialize(jsonElement, StandClosedDisruptionDataModel.class);
            case Types.Disruption.STAND_CONDITIONALLY_CLOSED:
                return context.deserialize(jsonElement, StandConditionallyClosedDisruptionDataModel.class);
            case Types.Disruption.STAND_TEMPORARILY_CLOSED:
                return context.deserialize(jsonElement, StandTemporarilyClosedDisruptionDataModel.class);

        }

        throw new IllegalArgumentException("Type \"" + disruptionType + "\" is not valid disruption type.");
    }
}

