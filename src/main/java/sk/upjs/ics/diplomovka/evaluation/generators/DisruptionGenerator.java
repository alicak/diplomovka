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
    private Set<Integer> nonDisruptedFlights;

    private static final int KINDS_OF_STAND_DISRUPTIONS = 3;
    private static final int KINDS_OF_FLIGHT_DISRUPTIONS = 2;


    public DisruptionGenerator(GeneralStorage storage, int startTime) {
        super(storage);
        this.flightGenerator = new FlightGenerator(storage, startTime);
        this.conditionGenerator = new ClosureConditionGenerator(storage);
        this.startTime = startTime;
        this.nonDisruptedStands = new HashSet<>(storage.getStandsStorage().getStandsIds());
        this.nonDisruptedFlights = new HashSet<>(storage.getFlightStorage().getFlightIds());
    }

    public DisruptionDataModel generateStandDisruption() {
        int type = Utils.randomInt(1, KINDS_OF_STAND_DISRUPTIONS + 1);
        switch (type) {
            case 1:
                return generateStandClosedDisruption();
            case 2:
                return generateStandConditionallyClosedDisruption();
            case 3:
            default:
                return generateStandTemporarilyClosedDisruption();
        }
    }

    public DisruptionDataModel generateFlightDisruption() {
        int type = Utils.randomInt(1, KINDS_OF_FLIGHT_DISRUPTIONS + 1);
        switch (type) {
            case 1:
                return generateFlightDelayedDisruption();
            case 2:
            default:
                return generateFlightAddedDisruption();
        }
    }

    public DisruptionDataModel generateFlightDelayedDisruption() {
        return new FlightDelayedDisruptionDataModel(id++, randomFlightId(), Utils.randomInt(15, 300));
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

    private int randomFlightId() {
        int flightId = chooseFromSet(nonDisruptedFlights);
        nonDisruptedFlights.remove(flightId);
        return flightId;
    }

    private int randomStart() {
        return Utils.randomInt(startTime, Utils.MINUTES_IN_DAY - 240);
    }

    private int randomEnd(int start) {
        return Utils.randomInt(start, Utils.MINUTES_IN_DAY);
    }
}
