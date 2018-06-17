package sk.upjs.ics.diplomovka.evaluation.generators;

import sk.upjs.ics.diplomovka.data.models.data.disruptions.*;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class DisruptionGenerator extends TestDataGenerator {

    private int id = 1;
    private FlightGenerator flightGenerator;
    private ClosureConditionGenerator conditionGenerator;
    private int startTime;
    private Set<Integer> nonDisruptedStands;

    private static final int KINDS_OF_DISRUPTIONS = 5;

    public DisruptionGenerator(GeneralStorage storage, int startTime) {
        super(storage);
        this.flightGenerator = new FlightGenerator(storage, startTime);
        this.conditionGenerator = new ClosureConditionGenerator(storage);
        this.startTime = startTime;
        this.nonDisruptedStands = new HashSet<>(storage.getStandsStorage().getStandsIds());
    }

    public DisruptionDataModel generateDisruption() {
        int type = Utils.randomInt(1, KINDS_OF_DISRUPTIONS + 1);
        switch (type) {
            case 1:
                return generateFlightDelayedDisruption();
            case 2:
                return generateFlightAddedDisruption();
            case 3:
                return generateStandClosedDisruption();
            case 4:
                return generateStandConditionallyClosedDisruption();
            case 5:
            default:
                return generateStandTemporarilyClosedDisruption();
        }
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
        int standId = chooseFromSet(nonDisruptedStands);
        nonDisruptedStands.remove(standId);
        return standId;
    }

    private int randomStart() {
        return Utils.randomInt(startTime, Utils.MINUTES_IN_DAY - 240);
    }

    private int randomEnd(int start) {
        return Utils.randomInt(start, Utils.MINUTES_IN_DAY);
    }
}
