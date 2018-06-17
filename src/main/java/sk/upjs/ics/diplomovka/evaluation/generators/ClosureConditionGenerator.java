package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.closureconditions.ClosureConditionDataModel;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ClosureConditionGenerator extends Generator {

    public ClosureConditionGenerator(GeneralStorage storage) {
        super(storage);
    }

    public ClosureConditionDataModel generateCategoryCondition() {
        throw new NotImplementedException();
    }

    public ClosureConditionDataModel generateEngineTypeCondition() {
        throw new NotImplementedException();
    }

    public ClosureConditionDataModel generateWeightCondition() {
        throw new NotImplementedException();
    }

    public ClosureConditionDataModel generateWingspanCondition() {
        throw new NotImplementedException();
    }

}
