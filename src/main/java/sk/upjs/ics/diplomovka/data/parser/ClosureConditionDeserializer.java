package sk.upjs.ics.diplomovka.data.parser;

import com.google.gson.*;
import sk.upjs.ics.diplomovka.data.models.data.closureconditions.*;

import java.lang.reflect.Type;

public class ClosureConditionDeserializer implements JsonDeserializer<ClosureConditionDataModel> {
    @Override
    public ClosureConditionDataModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonType = jsonObject.get(Types.TYPE_FIELD);

        String conditionType = jsonType.getAsString();

        switch (conditionType) {
            case Types.ClosureCondition.ENGINE_TYPE:
                return context.deserialize(jsonElement, EngineTypeClosureConditionDataModel.class);
            case Types.ClosureCondition.CATEGORY:
                return context.deserialize(jsonElement, CategoryClosureConditionDataModel.class);
            case Types.ClosureCondition.WINGSPAN:
                return context.deserialize(jsonElement, WingspanClosureConditionDataModel.class);
            case Types.ClosureCondition.WEIGHT:
                return context.deserialize(jsonElement, WeightClosureConditionDataModel.class);

        }

        throw new IllegalArgumentException("Type \"" + conditionType + "\" is not valid closure condition type.");
    }
}

