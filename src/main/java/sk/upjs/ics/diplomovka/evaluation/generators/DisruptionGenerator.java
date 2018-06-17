package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.disruptions.DisruptionDataModel;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DisruptionGenerator extends Generator {

    public DisruptionGenerator(GeneralStorage storage) {
        super(storage);
    }

    public DisruptionDataModel generateDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateFlightDelayedDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateFlightAddedDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateStandClosedDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateStandConditionallyClosedDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateStandTemporarilyClosedDisruption() {
        throw new NotImplementedException();
    }
}
