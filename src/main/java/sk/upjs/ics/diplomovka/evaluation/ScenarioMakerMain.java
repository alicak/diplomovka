package sk.upjs.ics.diplomovka.evaluation;

import com.google.gson.Gson;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.DisruptionDataModel;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.data.parser.Files;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ScenarioMakerMain {

    public static final String SCENARIOS_DATA_FOLDER = "data/scenarios/";
    private static final Gson GSON = new Gson();

    public static void main(String[] args) throws FileNotFoundException {
        DataParser parser = new DataParser();
        GeneralStorage storage = parser.parseDataFromJsons(Files.CATEGORIES, Files.AIRCRAFTS, Files.ENGINE_TYPES, Files.TRANSFERS,
                Files.GATES, Files.GATE_DISTANCES, Files.STAND_DISTANCES, Files.STANDS, Files.FLIGHTS);

        ScenarioMaker scenarioMaker = new ScenarioMaker(storage);

        for (ScenarioMaker.ScenarioType type: ScenarioMaker.ScenarioType.values()) {
            generateAndWriteScenariosSet(scenarioMaker, type,10);
        }
    }

    private static void generateAndWriteScenariosSet(ScenarioMaker scenarioMaker, ScenarioMaker.ScenarioType type, int count) throws FileNotFoundException {
        for (int i = 1; i < count + 1; i++) {
            List<DisruptionDataModel> scenario = scenarioMaker.generateScenario(type, 0);
            String serialisedScenario = GSON.toJson(scenario);

            String filename = SCENARIOS_DATA_FOLDER + type.name().toLowerCase() + "_scenario_" + i + ".json";

            PrintWriter pw = new PrintWriter(new File(filename));
            pw.write(serialisedScenario);
            pw.close();
        }
    }

}
