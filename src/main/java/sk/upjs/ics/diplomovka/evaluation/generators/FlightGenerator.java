package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FlightGenerator extends Generator {
    public FlightGenerator(GeneralStorage storage) {
        super(storage);
    }

    public FlightDataModel generateFlight() {
        throw new NotImplementedException();
    }

}
