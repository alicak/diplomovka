package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.disruptions.*;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.utils.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DisruptionGenerator extends Generator {

    private int id = 1;
    private FlightGenerator flightGenerator;
    private ClosureConditionGenerator conditionGenerator;
    private int startTime;

    public DisruptionGenerator(GeneralStorage storage, int startTime) {
        super(storage);
        this.flightGenerator = new FlightGenerator(storage, startTime);
        this.conditionGenerator = new ClosureConditionGenerator(storage);
        this.startTime = startTime;
    }

    public DisruptionDataModel generateDisruption() {
        throw new NotImplementedException();
    }

    public DisruptionDataModel generateFlightDelayedDisruption() {
        return new FlightDelayedDisruptionDataModel(
                id++, chooseFromSet(storage.getFlightStorage().getFlightIds()), Utils.randomInt(15, 300));
    }

    public DisruptionDataModel generateFlightAddedDisruption() {
        return new FlightAddedDisruptionDataModel(id++, flightGenerator.generateFlight());
    }

    public DisruptionDataModel generateStandClosedDisruption() {
        return new StandClosedDisruptionDataModel(id++, randomStandId());
    }

    public DisruptionDataModel generateStandConditionallyClosedDisruption() {
        int start = randomStart();
        return new StandConditionallyClosedDisruptionDataModel(id++, randomStandId(), start, randomEnd(start),
                conditionGenerator.generateCondition());
    }

    public DisruptionDataModel generateStandTemporarilyClosedDisruption() {
        int start = randomStart();
        return new StandTemporarilyClosedDisruptionDataModel(id++, randomStandId(), start, randomEnd(start));
    }

    private int randomStandId() {
        return chooseFromSet(storage.getStandsStorage().getStandsIds());
    }

    private int randomStart() {
        return Utils.randomInt(startTime, Utils.MINUTES_IN_DAY - 240);
    }

    private int randomEnd(int start) {
        return Utils.randomInt(start, Utils.MINUTES_IN_DAY);
    }
}
