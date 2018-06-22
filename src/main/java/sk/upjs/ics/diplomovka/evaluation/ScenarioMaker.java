package sk.upjs.ics.diplomovka.evaluation;

import sk.upjs.ics.diplomovka.data.models.data.disruptions.DisruptionDataModel;
import sk.upjs.ics.diplomovka.evaluation.generators.DisruptionGenerator;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * creates a test scenario (list of disruptions)
 */
public class ScenarioMaker {

    private static final int EASY_NO_OF_DISRUPTIONS = 6;
    private static final int MEDIUM_NO_OF_DISRUPTIONS = 11;
    private static final int HARD_NO_OF_DISRUPTIONS = 21;
    private static final int EXTREME_NO_OF_DISRUPTIONS = 36;

    private DisruptionGenerator disruptionGenerator;
    private GeneralStorage storage;

    public enum ScenarioType {
        EASY, MEDIUM, HARD, EXTREME
    }

    public ScenarioMaker(GeneralStorage storage) {
        this.storage = storage;
        this.disruptionGenerator = new DisruptionGenerator(storage, 0);
    }

    public List<DisruptionDataModel> generateScenario(ScenarioType type, int startTime) {
        disruptionGenerator = new DisruptionGenerator(storage, startTime);

        int noOfDisruptions = 0;
        switch (type) {
            case EASY:
                noOfDisruptions = Utils.randomInt(0, EASY_NO_OF_DISRUPTIONS);
                break;
            case MEDIUM:
                noOfDisruptions = Utils.randomInt(EASY_NO_OF_DISRUPTIONS, MEDIUM_NO_OF_DISRUPTIONS);
                break;
            case HARD:
                noOfDisruptions = Utils.randomInt(MEDIUM_NO_OF_DISRUPTIONS, HARD_NO_OF_DISRUPTIONS);
                break;
            case EXTREME:
                noOfDisruptions = Utils.randomInt(HARD_NO_OF_DISRUPTIONS, EXTREME_NO_OF_DISRUPTIONS);
                break;
        }

        List<DisruptionDataModel> disruptions = new ArrayList<>();

        // to prevent too many stand closures
        int noOfStandDisruptions = Math.min(noOfDisruptions / 2, storage.getStandsStorage().getNoOfStands() / 2);

        for (int i = 0; i < noOfStandDisruptions; i++) {
            disruptions.add(disruptionGenerator.generateStandDisruption());
        }

        for (int i = noOfStandDisruptions; i < noOfDisruptions; i++) {
            disruptions.add(disruptionGenerator.generateFlightDisruption());
        }

        return disruptions;
    }
}
