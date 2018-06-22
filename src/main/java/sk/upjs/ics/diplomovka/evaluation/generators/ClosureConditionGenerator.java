package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.*;
import sk.upjs.ics.diplomovka.data.parser.Types;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.FlightAttributes;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClosureConditionGenerator extends TestDataGenerator {

    private FlightAttributes attributes;

    private static final int MIN_WEIGHT = 40;
    private static final int MAX_WEIGHT = 100;
    private static final int MIN_WINGSPAN = 28;
    private static final int MAX_WINGSPAN = 40;

    public ClosureConditionGenerator(GeneralStorage storage) {
        super(storage);
        attributes = storage.getFlightStorage().getAttributes();
    }

    public ClosureConditionDataModel generateCondition() {
        int condType = Utils.randomInt(1, 4);
        switch (condType) {
            case 1:
                return generateCategoryCondition();
            case 2:
                return generateEngineTypeCondition();
            case 3:
            default:
                return generateWeightCondition();
        }
    }

    public ClosureConditionDataModel generateCategoryCondition() {
        return new CategoryClosureConditionDataModel(Types.ClosureCondition.CATEGORY,
                chooseListFromSet(attributes.getCategories().keySet()));
    }

    public ClosureConditionDataModel generateEngineTypeCondition() {
        return new EngineTypeClosureConditionDataModel(Types.ClosureCondition.ENGINE_TYPE,
                chooseListFromSet(attributes.getEngineTypes().keySet()));
    }

    public ClosureConditionDataModel generateWeightCondition() {
        return new WeightClosureConditionDataModel(Types.ClosureCondition.WEIGHT, Utils.randomInt(MIN_WEIGHT, MAX_WEIGHT));
    }

    public ClosureConditionDataModel generateWingspanCondition() {
        return new WingspanClosureConditionDataModel(Types.ClosureCondition.WEIGHT, Utils.randomInt(MIN_WINGSPAN, MAX_WINGSPAN));
    }

    private List<Integer> chooseListFromSet(Set<Integer> set) {
        List<Integer> list = new ArrayList<>(set);

        int count = Math.max(1, Utils.randomInt(1, list.size() - 1)); // minus one because all would mean normal stand closure
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int position = Utils.randomInt(0, list.size());
            result.add(list.get(position));
            list.remove(position);
            count--;
        }

        return result;
    }

}
